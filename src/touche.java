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
    touche(){




        setLayout(new BorderLayout(5,5));
        JPanel indications = new JPanel();
        label = new JLabel("Point : " + i);
        indications.setLayout(new GridLayout(4,1));
        indications.add(textField);
        indications.add(bouton);
        indications.add(label);
        A = new ardoise();
        add(indications , BorderLayout.NORTH);
        add(A , BorderLayout.CENTER);
        bouton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {

                A.Saisi = textField.getText();
                ++i;
                label.setText("Point : " + i);
                A.setForeground(Color.RED);
                repaint();

            }
        });

    }
}
