package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import controlador.ServicioConcytec;
import modelo.CentroAutorizado;
import modelo.Investigador;
import tads.ListaEnlazada;
import tads.Visitante;

public final class FrmBusqueda extends JDialog {
    private final ServicioConcytec servicio;
    private final JComboBox<String> campo=new JComboBox<String>(new String[]{"Código RENACYT","Investigador","Fecha constancia"}); private final JTextField texto=new JTextField(32), centro=new JTextField(14), provincia=new JTextField(14), distrito=new JTextField(14);
    private final JCheckBox chkCentro=new JCheckBox("Centro"),chkProvincia=new JCheckBox("Provincia"),chkDistrito=new JCheckBox("Distrito"); private final ModeloTablaInvestigadores modelo=new ModeloTablaInvestigadores(); private ListaEnlazada<String,Investigador> resultados=new ListaEnlazada<String,Investigador>();
    public FrmBusqueda(Frame padre,ServicioConcytec servicio){super(padre,"Búsquedas CONCYTEC",true);this.servicio=servicio;JTabbedPane tabs=new JTabbedPane();tabs.addTab("Investigadores",panelInvestigadores());tabs.addTab("Centros autorizados",panelCentros());add(tabs);setSize(1050,650);setLocationRelativeTo(padre);actualizar();}
    private JPanel panelInvestigadores(){JPanel base=new JPanel(new BorderLayout(8,8));base.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));JPanel filtros=new JPanel(new BorderLayout(0,10));TemaConcytec.tarjeta(filtros);texto.setPreferredSize(new Dimension(340,30));JPanel consulta=new JPanel(new FlowLayout(FlowLayout.LEFT,8,0));consulta.setBackground(Color.WHITE);consulta.add(new JLabel("Buscar por"));consulta.add(campo);consulta.add(new JLabel("Texto:"));consulta.add(texto);JButton exportar=TemaConcytec.boton("Exportar CSV / PDF");consulta.add(exportar);JPanel filtrosSecundarios=new JPanel(new GridLayout(1,3,14,0));filtrosSecundarios.setBackground(Color.WHITE);filtrosSecundarios.add(crearFiltro(chkCentro,centro));filtrosSecundarios.add(crearFiltro(chkProvincia,provincia));filtrosSecundarios.add(crearFiltro(chkDistrito,distrito));filtros.add(consulta,BorderLayout.NORTH);filtros.add(filtrosSecundarios,BorderLayout.CENTER);
        JTable tabla=new JTable(modelo);tabla.setAutoCreateRowSorter(true);base.add(filtros,BorderLayout.NORTH);base.add(new JScrollPane(tabla),BorderLayout.CENTER);DocumentListener dl=new DocumentListener(){public void insertUpdate(DocumentEvent e){actualizar();}public void removeUpdate(DocumentEvent e){actualizar();}public void changedUpdate(DocumentEvent e){actualizar();}};texto.getDocument().addDocumentListener(dl);centro.getDocument().addDocumentListener(dl);provincia.getDocument().addDocumentListener(dl);distrito.getDocument().addDocumentListener(dl);campo.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){actualizar();}});chkCentro.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){actualizar();}});chkProvincia.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){actualizar();}});chkDistrito.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){actualizar();}});exportar.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){exportarInvestigadores();}});return base;}
    private JPanel crearFiltro(JCheckBox selector,JTextField campoFiltro){JPanel filtro=new JPanel(new BorderLayout(8,0));filtro.setBackground(Color.WHITE);campoFiltro.setPreferredSize(new Dimension(180,30));filtro.add(selector,BorderLayout.WEST);filtro.add(campoFiltro,BorderLayout.CENTER);return filtro;}
    private JPanel panelCentros(){final JComboBox<String> f=new JComboBox<String>(new String[]{"Nombre institución","Área científica","Subárea científica","Disciplina científica"});final JTextField q=new JTextField(25);final javax.swing.table.DefaultTableModel m=new javax.swing.table.DefaultTableModel(new String[]{"Código","Institución","Área","Subárea","Disciplina"},0){public boolean isCellEditable(int a,int b){return false;}};JPanel p=new JPanel(new BorderLayout(8,8));JPanel top=new JPanel(new FlowLayout(FlowLayout.LEFT));top.add(new JLabel("Buscar por"));top.add(f);top.add(q);p.add(top,BorderLayout.NORTH);p.add(new JScrollPane(new JTable(m)),BorderLayout.CENTER);ActionListener buscar=new ActionListener(){public void actionPerformed(ActionEvent e){m.setRowCount(0);servicio.buscarCentros(String.valueOf(f.getSelectedItem()),q.getText()).recorrer(new Visitante<String,CentroAutorizado>(){public void visitar(String k,CentroAutorizado c){m.addRow(new Object[]{c.getCodigo(),c.getInstitucion(),c.getArea(),c.getSubarea(),c.getDisciplina()});}});}};f.addActionListener(buscar);q.addActionListener(buscar);buscar.actionPerformed(null);return p;}
    private void actualizar(){if(modelo==null)return;resultados=servicio.buscarInvestigadores(String.valueOf(campo.getSelectedItem()),texto.getText(),chkCentro.isSelected(),centro.getText(),chkProvincia.isSelected(),provincia.getText(),chkDistrito.isSelected(),distrito.getText());modelo.cargar(resultados);}
    private void exportarInvestigadores(){JFileChooser fc=new JFileChooser();fc.setSelectedFile(new File("busqueda_investigadores.csv"));if(fc.showSaveDialog(this)!=JFileChooser.APPROVE_OPTION)return;final String[] filas=new String[resultados.tamano()];final int[] n={0};resultados.recorrer(new Visitante<String,Investigador>(){public void visitar(String k,Investigador i){filas[n[0]++]=i.getCodigo()+";"+i.getNombre()+";"+i.getCondicion()+";"+i.getDepartamento()+";"+i.getProvincia()+";"+i.getDistrito()+";"+i.getCentroId();}});try{servicio.getArchivos().exportarTexto(fc.getSelectedFile(),"codigo;investigador;condicion;departamento;provincia;distrito;centro",filas,n[0]);File pdf=new File(fc.getSelectedFile().getParentFile(),fc.getSelectedFile().getName().replaceAll("(?i)\\.csv$","")+".pdf");servicio.getArchivos().exportarPdf(pdf,"Resultados de búsqueda CONCYTEC",filas,n[0]);javax.swing.JOptionPane.showMessageDialog(this,"Exportación CSV y PDF completada.");}catch(IOException ex){javax.swing.JOptionPane.showMessageDialog(this,"Error de exportación: "+ex.getMessage());}}
}
