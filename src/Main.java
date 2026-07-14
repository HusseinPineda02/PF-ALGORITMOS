import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import controlador.ServicioConcytec;
import vista.FrmPrincipal;
import vista.TemaConcytec;

public final class Main {
    public static void main(String[] args) {
        TemaConcytec.aplicar();
        SwingUtilities.invokeLater(new Runnable() { public void run() {
            try { new FrmPrincipal(new ServicioConcytec(new File("data"))).setVisible(true); }
            catch (Exception ex) { JOptionPane.showMessageDialog(null,"No se pudo iniciar el sistema: "+ex.getMessage(),"CONCYTEC",JOptionPane.ERROR_MESSAGE); }
        }});
    }
}
