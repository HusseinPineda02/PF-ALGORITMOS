package modelo;

public final class EstadisticaRegistro {
    private final String categoria; private int total;
    public EstadisticaRegistro(String categoria) { this.categoria = categoria == null || categoria.trim().length()==0 ? "Sin especificar" : categoria.trim(); }
    public String getCategoria(){return categoria;} public int getTotal(){return total;} public void incrementar(){total++;}
}
