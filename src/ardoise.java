import javax.swing.*;
import java.awt.*;

public class ardoise extends JPanel {
    public JLabel label ;
    public String Saisi = "" ;
    public ardoise() {
        setPreferredSize(new Dimension(100,100));
        this.setBackground(Color.LIGHT_GRAY);
        setForeground(Color.BLUE);
    }

    public void paintComponent(Graphics g){


        int largeur = getSize().width;
        int longueur = getSize().height;

        super.paintComponent(g);
        if(Saisi != "") {
            g.drawString(Saisi, 400, 10);
            g.drawLine(410, 15, 360, 50);
            g.drawLine(410, 15, 460, 50);
        }

    }
}
