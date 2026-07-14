package tads;

public final class Nodo<K, V> {
    private final K clave;
    private V valor;
    private Nodo<K, V> siguiente;

    public Nodo(K clave, V valor) { this.clave = clave; this.valor = valor; }
    public K getClave() { return clave; }
    public V getValor() { return valor; }
    public void setValor(V valor) { this.valor = valor; }
    public Nodo<K, V> getSiguiente() { return siguiente; }
    public void setSiguiente(Nodo<K, V> siguiente) { this.siguiente = siguiente; }
}
