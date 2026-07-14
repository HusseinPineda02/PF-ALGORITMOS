package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import controlador.ServicioConcytec;

public final class FrmPrincipal extends JFrame {
    private final ServicioConcytec servicio;
    private final JLabel totalInvestigadores=new JLabel(), totalCentros=new JLabel(), totalColisiones=new JLabel(), carga=new JLabel();
    public FrmPrincipal(ServicioConcytec servicio){this.servicio=servicio;setTitle("CONCYTEC | Sistema de Búsquedas y Estadísticas");setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);construir();setSize(1080,650);setLocationRelativeTo(null);}
    private void construir(){setJMenuBar(menu());setLayout(new BorderLayout());JPanel cabecera=new JPanel(new BorderLayout());cabecera.setBackground(TemaConcytec.AZUL_OSCURO);cabecera.setBorder(BorderFactory.createEmptyBorder(22,30,22,30));JLabel titulo=new JLabel("CONCYTEC");titulo.setFont(new Font("Segoe UI",Font.BOLD,30));titulo.setForeground(Color.WHITE);JLabel subtitulo=new JLabel("  Consejo Nacional de Ciencia, Tecnología e Innovación");subtitulo.setFont(new Font("Segoe UI",Font.PLAIN,16));subtitulo.setForeground(new Color(202,237,248));cabecera.add(titulo,BorderLayout.WEST);cabecera.add(subtitulo,BorderLayout.CENTER);add(cabecera,BorderLayout.NORTH);
        JPanel centro=new JPanel(new BorderLayout(20,20));centro.setBorder(BorderFactory.createEmptyBorder(28,32,28,32));JLabel bienvenida=new JLabel("Gestión de investigadores RENACYT");bienvenida.setFont(new Font("Segoe UI",Font.BOLD,22));bienvenida.setForeground(TemaConcytec.AZUL_OSCURO);centro.add(bienvenida,BorderLayout.NORTH);JPanel tarjetas=new JPanel(new GridLayout(1,4,16,0));tarjetas.add(tarjeta("INVESTIGADORES",totalInvestigadores));tarjetas.add(tarjeta("CENTROS",totalCentros));tarjetas.add(tarjeta("COLISIONES",totalColisiones));tarjetas.add(tarjeta("FACTOR DE CARGA",carga));centro.add(tarjetas,BorderLayout.CENTER);JPanel accesos=new JPanel(new GridLayout(1,3,16,0));accesos.add(acceso("Mantenimiento", "Registrar, actualizar y eliminar",new ActionListener(){public void actionPerformed(ActionEvent e){new FrmMantenimiento(FrmPrincipal.this,servicio).setVisible(true);refrescar();}}));accesos.add(acceso("Búsquedas", "Consultas y filtros instantáneos",new ActionListener(){public void actionPerformed(ActionEvent e){new FrmBusqueda(FrmPrincipal.this,servicio).setVisible(true);}}));accesos.add(acceso("Estadísticas", "Totales por ubicación y centro",new ActionListener(){public void actionPerformed(ActionEvent e){new FrmEstadisticas(FrmPrincipal.this,servicio).setVisible(true);}}));centro.add(accesos,BorderLayout.SOUTH);add(centro,BorderLayout.CENTER);refrescar();}
    private JPanel tarjeta(String titulo,JLabel valor){JPanel p=new JPanel(new BorderLayout(5,5));TemaConcytec.tarjeta(p);JLabel t=new JLabel(titulo);t.setForeground(TemaConcytec.AZUL);t.setFont(new Font("Segoe UI",Font.BOLD,12));valor.setFont(new Font("Segoe UI",Font.BOLD,28));valor.setForeground(TemaConcytec.TEXTO);p.add(t,BorderLayout.NORTH);p.add(valor,BorderLayout.CENTER);return p;}
    private JButton acceso(String titulo,String detalle,ActionListener accion){JButton b=new JButton("<html><b>"+titulo+"</b><br/><span style='font-size:10px'>"+detalle+"</span></html>");b.setHorizontalAlignment(SwingConstants.LEFT);b.setBackground(Color.WHITE);b.setForeground(TemaConcytec.AZUL_OSCURO);b.setFont(new Font("Segoe UI",Font.PLAIN,15));b.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(TemaConcytec.BORDE),BorderFactory.createEmptyBorder(18,18,18,18)));b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));b.addActionListener(accion);return b;}
    private JMenuBar menu(){JMenuBar barra=new JMenuBar();JMenu archivo=new JMenu("Archivo");JMenuItem impInv=new JMenuItem("Importar investigadores CSV...");JMenuItem impCen=new JMenuItem("Importar centros CSV...");JMenuItem salir=new JMenuItem("Salir");impInv.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){importar(true);}});impCen.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){importar(false);}});salir.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){dispose();}});archivo.add(impInv);archivo.add(impCen);archivo.addSeparator();archivo.add(salir);JMenu modulos=new JMenu("Módulos");JMenuItem man=new JMenuItem("Mantenimiento");JMenuItem bus=new JMenuItem("Búsquedas");JMenuItem est=new JMenuItem("Estadísticas");man.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){new FrmMantenimiento(FrmPrincipal.this,servicio).setVisible(true);refrescar();}});bus.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){new FrmBusqueda(FrmPrincipal.this,servicio).setVisible(true);}});est.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){new FrmEstadisticas(FrmPrincipal.this,servicio).setVisible(true);}});modulos.add(man);modulos.add(bus);modulos.add(est);barra.add(archivo);barra.add(modulos);return barra;}
    private void importar(boolean investigadores){JFileChooser fc=new JFileChooser();if(fc.showOpenDialog(this)!=JFileChooser.APPROVE_OPTION)return;try{int n=investigadores?servicio.importarInvestigadores(fc.getSelectedFile()):servicio.importarCentros(fc.getSelectedFile());refrescar();JOptionPane.showMessageDialog(this,n+" registros importados a memoria correctamente.","Importación",JOptionPane.INFORMATION_MESSAGE);}catch(IOException ex){JOptionPane.showMessageDialog(this,"No se pudo importar el archivo: "+ex.getMessage(),"Archivo",JOptionPane.ERROR_MESSAGE);}}
    private void refrescar(){totalInvestigadores.setText(String.valueOf(servicio.totalInvestigadores()));totalCentros.setText(String.valueOf(servicio.totalCentros()));totalColisiones.setText(String.valueOf(servicio.colisiones()));carga.setText(String.format("%.2f%%",servicio.carga()*100));}
}
