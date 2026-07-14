package tads;

public final class ListaEnlazada<K, V> {
    private Nodo<K, V> cabeza;
    private int cantidad;

    public V obtener(K clave) {
        Nodo<K, V> actual = cabeza;
        while (actual != null) {
            if (iguales(actual.getClave(), clave)) return actual.getValor();
            actual = actual.getSiguiente();
        }
        return null;
    }
    
    public boolean insertarOReemplazar(K clave, V valor) {
        Nodo<K, V> actual = cabeza;
        while (actual != null) {
            if (iguales(actual.getClave(), clave)) { actual.setValor(valor); return false; }
            actual = actual.getSiguiente();
        }
        Nodo<K, V> nuevo = new Nodo<K, V>(clave, valor);
        nuevo.setSiguiente(cabeza);
        cabeza = nuevo;
        cantidad++;
        return true;
    }

    public V eliminar(K clave) {
        Nodo<K, V> anterior = null;
        Nodo<K, V> actual = cabeza;
        while (actual != null) {
            if (iguales(actual.getClave(), clave)) {
                if (anterior == null) cabeza = actual.getSiguiente();
                else anterior.setSiguiente(actual.getSiguiente());
                cantidad--;
                return actual.getValor();
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }
        return null;
    }

    public void recorrer(Visitante<K, V> visitante) {
        Nodo<K, V> actual = cabeza;
        while (actual != null) { visitante.visitar(actual.getClave(), actual.getValor()); actual = actual.getSiguiente(); }
    }
    public int tamano() { return cantidad; }
    private boolean iguales(K a, K b) { return a == null ? b == null : a.equals(b); }
}
