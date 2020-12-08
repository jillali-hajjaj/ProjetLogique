import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class OptionPanel extends JPanel{


    /**
     *
     */
    private static final long serialVersionUID = -2562596013158468156L;
    private static JTextArea points;

    public OptionPanel() {

        setBackground(new Color(196, 147, 105));

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.NORTH;


        JButton bouton = new JButton("Formule Aléatoire");
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(bouton, constraints);

        bouton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                FormulePanel.setTextInTextField(getRandomFormule());
            }
        });


        points = new JTextArea("Point(s) : 0");
        points.setBackground(new Color(196, 147, 105));
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(points, constraints);

    }




    private String getRandomFormule() {
        int rdm = new Random().nextInt(20)+1;
        switch( rdm ) {
            case 1:
                return "p → ((p → q) → q)";
            case 2:
                return "p → (¬q → ¬(p → q))";
            case 3:
                return "(¬p → ¬q) → (q → p)";
            case 4:
                return "(p ∧ (p → q) ∧ ((p → q) → r)) → (p ∧ q ∧ r)";
            case 5:
                return "(p → q) → ((q → r) → (p → r))";
            case 6:
                return " (((s ∧ p) → (q ∧ r)) ∧ (¬r ∨ ¬q) ∧ p) → ¬s";
            case 7:
                return "((p → q) ∧ ((r ∧ s) → p) ∧ (t → r) ∧ (s ∧ t)) → q";
            case 8:
                return "(p → (q → r)) → ((p → q) → (p → r))";
            case 9:
                return "(q → p) → ((¬q → p) → p)";
            case 10:
                return "p → (q → r) → ((p → r) ∨ (q → r))";
            case 11:
                return "((p → r) ∨ (q → r)) → (p → (q → r))";
            case 12:
                return "((p → q) ∧ (q → p)) ∨ (p ∧ ¬q) ∨ (¬p ∧ q)";
            case 13:
                return "(¬(a ∧ b) → (¬a ∨ ¬b))";
            case 14:
                return "((¬a ∨ ¬b) → ¬(a ∧ b))";
            case 15:
                return "((a ∨ b ∨ ¬c) ∧ (a ∨ b ∨ c) ∧ (a ∨ ¬b)) → a";
            case 16:
                return "(u ∧ (w → v) ∧ (t → v) ∧ (u → (w ∨ t))) → v";
            case 17:
                return "r ∨ ((p ∧ (p → q) ∧ ((p → q) → r)) → (p ∧ q ∧ r)) ∧ (t → ¬r)";
            case 18:
                return "(p ∨ (q → ¬p)) ∨ ((p ∧ (p → q) ∧ ((p → q) → r)) → (p ∧ q ∧ r))";
            case 19:
                return "((p → (q → r)) → ((q → r) ∨ (q → r))) → ((¬(¬q → ¬p)) ∨ ¬q ∨ q)";
            default:
                return "((((s ∧ p) → (q ∧ r)) ∧ (¬r ∨ ¬q) ∧ p) ∧ (t ∧ (s → ¬t))) → ¬s";

        }
    }



    public static int getPoints() {
        return Integer.parseInt(points.getText().replace("Point(s) : ", ""));
    }


    public static void setPoints(int points) {
        OptionPanel.points.setText("Point(s) : "+points);
    }

}