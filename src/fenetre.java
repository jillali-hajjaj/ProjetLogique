import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class fenetre extends JFrame {

    public static void main(String[] ardv){
    JFrame monCadre = new JFrame("Logique");

    monCadre.setContentPane(new touche());
    monCadre.setPreferredSize(new Dimension(800 ,600));

    monCadre.addWindowFocusListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e){System.exit(0);}
        public void WindowActivated(WindowEvent e){monCadre.getContentPane().requestFocus();}
    });
        monCadre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    monCadre.pack();
    monCadre.setVisible(true);

    }
}
