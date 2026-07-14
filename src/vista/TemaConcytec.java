package vista;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.UIManager;

public final class TemaConcytec {
    public static final Color AZUL_OSCURO=new Color(0,82,117), AZUL=new Color(0,132,180), CELESTE=new Color(68,183,215), FONDO=new Color(244,248,250), TEXTO=new Color(31,45,54), BORDE=new Color(202,217,224);
    private TemaConcytec(){}
    public static void aplicar(){
        UIManager.put("Panel.background",FONDO); UIManager.put("Label.foreground",TEXTO); UIManager.put("Button.font",new Font("Segoe UI",Font.BOLD,13)); UIManager.put("Table.font",new Font("Segoe UI",Font.PLAIN,13)); UIManager.put("Table.rowHeight",28); UIManager.put("Table.selectionBackground",new Color(195,231,242)); UIManager.put("Table.selectionForeground",TEXTO); UIManager.put("TableHeader.background",AZUL_OSCURO); UIManager.put("TableHeader.foreground",Color.WHITE); UIManager.put("TableHeader.font",new Font("Segoe UI",Font.BOLD,12)); UIManager.put("TextField.font",new Font("Segoe UI",Font.PLAIN,13));
    }
    public static JButton boton(String texto){JButton b=new JButton(texto); b.setBackground(AZUL_OSCURO);b.setForeground(Color.WHITE);b.setFocusPainted(false);b.setBorder(BorderFactory.createEmptyBorder(9,14,9,14));return b;}
    public static void tarjeta(JComponent c){c.setBackground(Color.WHITE);c.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDE),BorderFactory.createEmptyBorder(14,14,14,14)));}
}
