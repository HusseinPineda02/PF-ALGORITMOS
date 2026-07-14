package tads;

public interface Visitante<K, V> {
    void visitar(K clave, V valor);
}
