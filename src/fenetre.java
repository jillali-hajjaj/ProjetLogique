import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class fenetre extends JFrame {
    public fenetre()throws InstantiationException  {


        super("Logique");

        Dimension d = new Dimension(800, 600);

        setContentPane(new touche());
        setPreferredSize(d);

            addWindowFocusListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

            public void WindowActivated(WindowEvent e) {
                getContentPane().requestFocus();
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

    }
    public static void main(String[] args) throws InstantiationException{
      //  SwingUtilities.invokeLater(new Runnable(){
        //    public void run() {
                new fenetre();
          //  }
        //});
    }

}
