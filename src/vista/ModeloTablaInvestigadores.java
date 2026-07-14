package vista;

import javax.swing.table.DefaultTableModel;
import modelo.Investigador;
import tads.ListaEnlazada;
import tads.Visitante;

public final class ModeloTablaInvestigadores extends DefaultTableModel {
    private final String[] columnas={"Código RENACYT","Investigador","Género","Condición","Nivel","Departamento","Provincia","Distrito","Centro"};
    public ModeloTablaInvestigadores(){super();setColumnIdentifiers(columnas);}
    public boolean isCellEditable(int fila,int columna){return false;}
    public void cargar(ListaEnlazada<String,Investigador> lista){setRowCount(0);lista.recorrer(new Visitante<String,Investigador>(){public void visitar(String k,Investigador i){addRow(new Object[]{i.getCodigo(),i.getNombre(),i.getGenero(),i.getCondicion(),i.getNivel2021(),i.getDepartamento(),i.getProvincia(),i.getDistrito(),i.getCentroId()});}});}
}
