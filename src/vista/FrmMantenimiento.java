package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import controlador.ServicioConcytec;
import modelo.Investigador;

public final class FrmMantenimiento extends JDialog {
    private final ServicioConcytec servicio;
    private final JTextField codigo=new JTextField(14), nombre=new JTextField(26), fecha=new JTextField(12), departamento=new JTextField(16), provincia=new JTextField(16), distrito=new JTextField(16), centro=new JTextField(8);
    private final JComboBox<String> genero=new JComboBox<String>(new String[]{"Femenino","Masculino","No especificado"}), condicion=new JComboBox<String>(new String[]{"Activo","Inactivo"}), nivel=new JComboBox<String>(new String[]{"I","II","III","IV","V","VI","VII","No especificado"});
    public FrmMantenimiento(Frame padre, ServicioConcytec servicio) { super(padre,"Mantenimiento de investigadores",true);this.servicio=servicio; construir(); }
    private void construir(){
        setLayout(new BorderLayout(12,12)); JPanel formulario=new JPanel(new GridBagLayout()); TemaConcytec.tarjeta(formulario); GridBagConstraints g=new GridBagConstraints();g.insets=new Insets(5,5,5,5);g.anchor=GridBagConstraints.WEST;g.fill=GridBagConstraints.HORIZONTAL;
        agregar(formulario,g,0,"Código RENACYT *",codigo); agregar(formulario,g,1,"Investigador *",nombre); agregar(formulario,g,2,"Género",genero); agregar(formulario,g,3,"Condición",condicion); agregar(formulario,g,4,"Fecha constancia",fecha); agregar(formulario,g,5,"Nivel 2021",nivel); agregar(formulario,g,6,"Departamento",departamento);agregar(formulario,g,7,"Provincia",provincia);agregar(formulario,g,8,"Distrito",distrito);agregar(formulario,g,9,"Centro autorizado",centro);
        JPanel botones=new JPanel(new FlowLayout(FlowLayout.RIGHT)); JButton buscar=TemaConcytec.boton("Consultar");JButton guardar=TemaConcytec.boton("Registrar");JButton actualizar=TemaConcytec.boton("Actualizar");JButton eliminar=TemaConcytec.boton("Eliminar"); JButton limpiar=TemaConcytec.boton("Limpiar"); botones.add(buscar);botones.add(guardar);botones.add(actualizar);botones.add(eliminar);botones.add(limpiar);
        buscar.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){consultar();}});guardar.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){operar("registrar");}});actualizar.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){operar("actualizar");}});eliminar.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){operar("eliminar");}});limpiar.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){limpiar();}});
        add(formulario,BorderLayout.CENTER);add(botones,BorderLayout.SOUTH);setSize(600,520);setLocationRelativeTo(getOwner());
    }
    private void agregar(JPanel p,GridBagConstraints g,int fila,String etiqueta,java.awt.Component campo){g.gridx=0;g.gridy=fila;g.weightx=0;p.add(new JLabel(etiqueta),g);g.gridx=1;g.weightx=1;p.add(campo,g);}
    private Investigador leer(){return new Investigador(codigo.getText(),nombre.getText(),String.valueOf(genero.getSelectedItem()),"","Reglamento_2021",String.valueOf(condicion.getSelectedItem()),fecha.getText(),String.valueOf(nivel.getSelectedItem()),"","","","","","PERÚ",departamento.getText(),provincia.getText(),distrito.getText(),centro.getText());}
    private void consultar(){Investigador i=servicio.obtenerInvestigador(codigo.getText());if(i==null){JOptionPane.showMessageDialog(this,"No se encontró el código RENACYT.","Consulta",JOptionPane.INFORMATION_MESSAGE);return;}nombre.setText(i.getNombre());genero.setSelectedItem(i.getGenero());condicion.setSelectedItem(i.getCondicion());fecha.setText(i.getFechaConstancia());nivel.setSelectedItem(i.getNivel2021());departamento.setText(i.getDepartamento());provincia.setText(i.getProvincia());distrito.setText(i.getDistrito());centro.setText(i.getCentroId());}
    private void operar(String accion){try{if("eliminar".equals(accion)){int r=JOptionPane.showConfirmDialog(this,"¿Eliminar el investigador seleccionado?","Confirmación",JOptionPane.YES_NO_OPTION);if(r!=JOptionPane.YES_OPTION)return;servicio.eliminar(codigo.getText());}else if("registrar".equals(accion))servicio.registrar(leer());else servicio.actualizar(leer());JOptionPane.showMessageDialog(this,"Operación realizada y persistida en el CSV.","CONCYTEC",JOptionPane.INFORMATION_MESSAGE);if(!"actualizar".equals(accion))limpiar();}catch(IllegalArgumentException ex){JOptionPane.showMessageDialog(this,ex.getMessage(),"Validación",JOptionPane.WARNING_MESSAGE);}catch(IOException ex){JOptionPane.showMessageDialog(this,"No fue posible guardar: "+ex.getMessage(),"Archivo",JOptionPane.ERROR_MESSAGE);}}
    private void limpiar(){codigo.setText("");nombre.setText("");fecha.setText("");departamento.setText("");provincia.setText("");distrito.setText("");centro.setText("");codigo.requestFocus();}
}
