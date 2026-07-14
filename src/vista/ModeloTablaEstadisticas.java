package vista;

import javax.swing.table.DefaultTableModel;
import modelo.EstadisticaRegistro;
import tads.ListaEnlazada;
import tads.Visitante;

public final class ModeloTablaEstadisticas extends DefaultTableModel {
    public ModeloTablaEstadisticas(){super(new String[]{"Categoría","Total de investigadores"},0);}
    public boolean isCellEditable(int fila,int columna){return false;}
    public void cargar(ListaEnlazada<String,EstadisticaRegistro> lista){setRowCount(0);lista.recorrer(new Visitante<String,EstadisticaRegistro>(){public void visitar(String k,EstadisticaRegistro r){addRow(new Object[]{r.getCategoria(),r.getTotal()});}});}
}
