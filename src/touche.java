import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class touche extends JPanel {
    ardoise A ;
    int i = 0;
    String Saisi = "";
    JButton bouton = new JButton("Enregistrer");
    JLabel label ;
    JTextField textField = new JTextField();
    touche()throws InstantiationException {




        //setLayout(new BorderLayout(3,3));
        JPanel indications = new JPanel();
        label = new JLabel("Point : " + i);
        indications.setLayout(new GridLayout(3,1));
        indications.add(textField);
        indications.add(bouton);
        indications.add(label);
        A = new ardoise();
        add(indications , BorderLayout.NORTH);
        add(A , BorderLayout.SOUTH);
        //A.ListExpression();
        bouton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {

                A.Mots = textField.getText();
                ++i;
                label.setText("Point : " + i);
                A.dessin();
                repaint();

            }
        });

    }
}
