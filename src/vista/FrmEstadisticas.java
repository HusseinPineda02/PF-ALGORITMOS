package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import controlador.ServicioConcytec;
import modelo.EstadisticaRegistro;
import tads.ListaEnlazada;
import tads.Visitante;

public final class FrmEstadisticas extends JDialog {
    private final ServicioConcytec servicio;
    private final JComboBox<String> tipo=new JComboBox<String>(new String[]{"Departamento","Provincia","Distrito","Institución","Área científica","Subárea científica","Disciplina científica","Investigación científica","Desarrollo tecnológico","Innovación tecnológica"});
    private final ModeloTablaEstadisticas modelo=new ModeloTablaEstadisticas(); private ListaEnlazada<String,EstadisticaRegistro> resultados;
    public FrmEstadisticas(Frame padre,ServicioConcytec servicio){super(padre,"Estadísticas de investigadores",true);this.servicio=servicio;construir();calcular();}
    private void construir(){setLayout(new BorderLayout(10,10));JPanel barra=new JPanel(new FlowLayout(FlowLayout.LEFT));barra.add(new JLabel("Agrupar por:"));barra.add(tipo);JButton calcular=TemaConcytec.boton("Calcular");JButton exportar=TemaConcytec.boton("Exportar CSV / PDF");barra.add(calcular);barra.add(exportar);add(barra,BorderLayout.NORTH);JTable tabla=new JTable(modelo);tabla.setAutoCreateRowSorter(true);add(new JScrollPane(tabla),BorderLayout.CENTER);calcular.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){calcular();}});tipo.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){calcular();}});exportar.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){exportar();}});setSize(720,540);setLocationRelativeTo(getOwner());}
    private void calcular(){resultados=servicio.calcularEstadistica(String.valueOf(tipo.getSelectedItem()));modelo.cargar(resultados);}
    private void exportar(){JFileChooser fc=new JFileChooser();fc.setSelectedFile(new File("estadistica_concytec.csv"));if(fc.showSaveDialog(this)!=JFileChooser.APPROVE_OPTION)return;final String[] filas=new String[resultados.tamano()];final int[] p={0};resultados.recorrer(new Visitante<String,EstadisticaRegistro>(){public void visitar(String k,EstadisticaRegistro r){filas[p[0]++]=r.getCategoria()+";"+r.getTotal();}});try{servicio.getArchivos().exportarTexto(fc.getSelectedFile(),"categoria;total_investigadores",filas,p[0]);File pdf=new File(fc.getSelectedFile().getParentFile(),fc.getSelectedFile().getName().replaceAll("(?i)\\.csv$","")+".pdf");servicio.getArchivos().exportarPdf(pdf,"Estadística CONCYTEC: "+tipo.getSelectedItem(),filas,p[0]);javax.swing.JOptionPane.showMessageDialog(this,"Exportación CSV y PDF completada.");}catch(IOException ex){javax.swing.JOptionPane.showMessageDialog(this,"Error de exportación: "+ex.getMessage());}}
}
