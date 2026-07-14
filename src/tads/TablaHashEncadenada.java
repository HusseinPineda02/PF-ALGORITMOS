package tads;

public final class TablaHashEncadenada<K, V> {
    private final ListaEnlazada<K, V>[] cubetas;
    private int cantidad;
    private int colisiones;

    @SuppressWarnings("unchecked")
    public TablaHashEncadenada(int capacidad) {
        cubetas = (ListaEnlazada<K, V>[]) new ListaEnlazada[capacidad];
        int i = 0;
        while (i < capacidad) { cubetas[i] = new ListaEnlazada<K, V>(); i++; }
    }

    public void poner(K clave, V valor) {
        validarClave(clave);
        ListaEnlazada<K, V> lista = cubetas[indice(clave)];
        boolean habiaElementos = lista.tamano() > 0;
        if (lista.insertarOReemplazar(clave, valor)) {
            cantidad++;
            if (habiaElementos) colisiones++;
        }
    }
    public V obtener(K clave) { return clave == null ? null : cubetas[indice(clave)].obtener(clave); }
    public V eliminar(K clave) {
        if (clave == null) return null;
        ListaEnlazada<K, V> lista = cubetas[indice(clave)];
        V eliminado = lista.eliminar(clave);
        if (eliminado != null) { cantidad--; if (lista.tamano() > 0 && colisiones > 0) colisiones--; }
        return eliminado;
    }
    public boolean contiene(K clave) { return obtener(clave) != null; }
    public int tamano() { return cantidad; }
    public int capacidad() { return cubetas.length; }
    public int colisiones() { return colisiones; }
    public double factorCarga() { return (double) cantidad / cubetas.length; }
    @SuppressWarnings("unchecked")
    public void limpiar() {
        int i=0; while(i<cubetas.length){ cubetas[i]=new ListaEnlazada<K,V>(); i++; }
        cantidad=0; colisiones=0;
    }
    public void recorrer(Visitante<K, V> visitante) {
        int i = 0; while (i < cubetas.length) { cubetas[i].recorrer(visitante); i++; }
    }
    private int indice(K clave) {
        String texto = String.valueOf(clave);
        int hash = 0;
        int i = 0;
        while (i < texto.length()) { hash = 31 * hash + texto.charAt(i); i++; }
        return (hash & 0x7fffffff) % cubetas.length;
    }
    private void validarClave(K clave) { if (clave == null) throw new IllegalArgumentException("La clave no puede ser nula."); }
}
