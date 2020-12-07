import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ardoise extends JPanel  implements MouseListener {
    private static final long serialVersionUID = 6335221551069472985L;
    // private static JTextField textField;
    private static JTextArea a;
    private static JLabel b;
    public String Mots = "";
    private JLayeredPane pane;
    JButton bouton;
    private GridBagConstraints paneConstraints;
    private static int gridY = 1;
    int i = 1;
    private Map<JTextArea , Integer> textAreaToGridX = new HashMap<>();
    private Map<JTextArea, Integer> textAreaToGridY = new HashMap<>();
    private static int maxBranchs = 0;
    private Map<JTextArea, JTextArea> childrenToParent = new HashMap<>();
    private JTextArea rootArea;

    //ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
    public String valide ;
    public ArrayList<String> tb = new ArrayList<>();

    public JTextArea formulesDeveloppees;
    public ardoise() {

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.NORTH;

        pane = new JLayeredPane();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets = new Insets(10, 0, 0, 0);
        pane.setPreferredSize(new Dimension(1000, 500));
        pane.setBorder(BorderFactory.createTitledBorder("Arbre"));

        pane.setLayout(new GridBagLayout());
        paneConstraints = new GridBagConstraints();
        paneConstraints.fill = GridBagConstraints.NORTH;
        add(pane, constraints);



    }

    public ArrayList<ArrayList<String>> ListExpression()throws InstantiationException {

        ExpressionComplexe e = new ExpressionComplexe();
        //String form = "¬pq∨¬q∧q∨";
        String form = e.stringToExpression2(Mots);

        System.out.println("debut " + form);
        int n = form.length() - 1;
       for(int i = form.length() - 1; i >0 ; --i){
            if(form.charAt(i) == '∨' || i==1) {

                String mots = form.substring(i-1,n);
                n = i-1;
                System.out.println(mots);
               mots= mots.replaceAll("∨" , "");
                mots= mots.replaceAll(" " , "");

                tb.add(mots);
            }

        }
        /*
String po = "";
        for(int i = form.length() - 1; i >0 ; --i) {
            if (form.charAt(i) == '∨' || i == 1) {
                tb.add(form.substring(i - 1, n) +po);
                System.out.println(tb);
                n = i-1;

            }

            if (form.charAt(i) == '∧' ) {
                if(form.charAt(i-1) == '¬' ) {
                    po = po + form.substring(i,i - 2);
                }else {
                    po = po+ form.charAt(i-1);

                }

            }
        }
        System.out.println("mots " + tb);

*/

        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();

        for (int i = 0 ; i < tb.size();++i){
            String lettre = tb.get(i).replaceAll("∧" , "");
            ArrayList<String> tam =new ArrayList<String>();
            for(int j = 0 ; j < lettre.length() ; ++j){
                if(lettre.charAt(j) == '¬'){
                    tam.add(lettre.substring(j,j+2));
                    ++j;


                }else{
                    tam.add(lettre.substring(j,j+1));
                }

            };
            result.add(tam);
        }
        System.out.println("fin " +result);

        for(int i = 0 ; i<result.size();++i){
            boolean m = true;
            for (int j = 0 ; j<result.get(i).size();++j) {
                for (int o = 0; o < result.get(i).size(); ++o) {
                   // System.out.println("lettre : " + result.get(1));

                    if (result.get(i).get(j).charAt(0) == '¬') {

                        if (result.get(i).get(o).charAt(0) == result.get(i).get(j).charAt(1) && m){

                            result.get(i).add("⟂");
                            m = false;
                            valide = "Valide";

                        }
                    }
                }
            }
            if(m){
                result.get(i).add("✕");
                valide = "Non Valide";

            }

        }
        return result;


    }
    public void dessin() {





        maxBranchs = countSymbolesPropositionnels(Mots);

        for ( Component comp : pane.getComponents() ) {
            pane.remove(comp);
        }

         formulesDeveloppees = new JTextArea();
        formulesDeveloppees.setEditable(false);
        formulesDeveloppees.setText(Mots);
        formulesDeveloppees.setBounds(5, 30, 990, 30);
        if ( maxBranchs % 2 == 1 )
            paneConstraints.gridx = maxBranchs/2+1;
        else
            paneConstraints.gridx = maxBranchs/2;
        System.out.println("rootGridX: " + paneConstraints.gridx);
        paneConstraints.gridy = 0;
        paneConstraints.insets = new Insets(10, 0, 0, 0);
        pane.add(formulesDeveloppees, paneConstraints);

        //addMouseListener(formulesDeveloppees);

        textAreaToGridX.clear();
        textAreaToGridX.put(formulesDeveloppees, 0);
        textAreaToGridY.put(formulesDeveloppees, paneConstraints.gridy);
        rootArea = formulesDeveloppees;
        System.out.println(textAreaToGridX.get(formulesDeveloppees));

        //pane.updateUI();
        pane.revalidate();
        pane.repaint();
        for (Component comp : pane.getComponents()) {
            pane.remove(comp);
        }


        formulesDeveloppees.setBounds(5, 30, 990, 30);
        paneConstraints.gridx = 0;
        paneConstraints.gridy = 0;
        paneConstraints.insets = new Insets(10, 0, 0, 0);
        pane.add(formulesDeveloppees, paneConstraints);

        addMouseListener(formulesDeveloppees);

        //pane.updateUI();
        pane.revalidate();
        pane.repaint();
				/*updateUI();
				repaint();
				revalidate();
				doLayout();*/

        //pane.doLayout();

        //TODO RESET POINTS


    }



    private int countSymbolesPropositionnels(String str) {
        int count = 0;
        for ( int i = 0 ; i < str.length() ; i++ ) {
            if ( str.charAt(i) == '∧' || str.charAt(i) == '∨' || str.charAt(i) == '¬' || ((int) str.charAt(i)) == 8594) {
                count++;
            }
        }
        return count;
    }

    private void addMouseListener(JTextArea area){
        area.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseClicked(MouseEvent e) {

                    //System.out.println(e.getComponent().getClass());

                    if (e.getComponent() instanceof JTextArea) {
                       // System.out.println((((JTextArea) e.getComponent()).getText()));
                        //  couple<String> formules = new formule(((JTextArea) e.getComponent().getText()).split();
                        System.out.println(textAreaToGridX.get(e.getComponent()));

                        try {
                            createChildren(1, (JTextArea) e.getComponent());
                        } catch (InstantiationException instantiationException) {
                            instantiationException.printStackTrace();
                        }


                        //fix GUI and add listener to the textArea generated
                        pane.revalidate();
                        //addMouseListener(firstSplit);


                        //remove listener for this area
                        for (MouseListener m : e.getComponent().getMouseListeners()) {
                            e.getComponent().removeMouseListener(m);
                        }
                    }
                }

        });
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /*
     * Get the nearest left grid x by going up in the tree
     */
    private int getNearestLeftGridX(JTextArea directFather) {

        JTextArea actual = directFather;
        boolean gridXHasOnlyReduced = true;

        while ( childrenToParent.get(actual) != null ) { //tant qu'il y a un père
            if ( textAreaToGridX.get(actual) < textAreaToGridX.get(childrenToParent.get(actual)) ) {
                actual = childrenToParent.get(actual);
            } else {
                actual = childrenToParent.get(actual);
                gridXHasOnlyReduced = false;
                break;
            }
        }

        System.out.println("actual = directFather: "+ actual.equals(directFather));
        System.out.println("actual = rootArea : "+ actual.equals(rootArea) );

        if ( actual.equals(directFather) || (actual.equals(rootArea) && gridXHasOnlyReduced) )
            return 0;

        System.out.println("nearestLeftGridX: "+textAreaToGridX.get(actual));
        return textAreaToGridX.get(actual);
    }

    /*
     * Get the nearest right grid x by going up in the tree
     */
    private int getNearestRightGridX(JTextArea directFather) {

        JTextArea actual = directFather;

        while ( childrenToParent.get(actual) != null ) { //tant qu'il y a un père
            if ( textAreaToGridX.get(childrenToParent.get(actual)) < textAreaToGridX.get(actual) ) {
                actual = childrenToParent.get(actual);
            } else {
                actual = childrenToParent.get(actual);
                System.out.println("breaked at the parent of : "+actual.getText());
                break;
            }
        }


        if ( actual.equals(directFather) )
            return maxBranchs;

        return textAreaToGridX.get(actual);
    }



    /*
     * Add a textArea on the bottom right of the father
     */

    private void createRightChild(String text, JTextArea father) {

        //int fatherGridX = textAreaToGridX.get(father);
        int fatherGridX = textAreaToGridX.get(father)-100;
        JTextArea secondSplit = new JTextArea();
        secondSplit.setEditable(false);
        secondSplit.setText(text);
        secondSplit.setBounds(5, 30, 990, 30);
        System.out.println(Math.ceil((getNearestRightGridX(father) - fatherGridX) / 2.0 ) + fatherGridX);
     //   paneConstraints.gridx = (int) (Math.ceil((getNearestRightGridX(father) - fatherGridX) / 2.0 ) + fatherGridX) ;
        paneConstraints.gridy = textAreaToGridY.get(father)+1;
        System.out.println(fatherGridX);
        paneConstraints.insets = new Insets(10, 0, 0, fatherGridX +200) ;
        pane.add(secondSplit, paneConstraints);


        textAreaToGridX.put(secondSplit, fatherGridX * 2);
        textAreaToGridY.put(secondSplit, paneConstraints.gridy);
        childrenToParent.put(secondSplit, father);
        addMouseListener(secondSplit);


        System.out.println("RIGHT CHILD /// fatherGridX: "+ fatherGridX + " gridX: "+ paneConstraints.gridx + " NearestRight: "+ getNearestRightGridX(father) );


    }

    /*
     * Add a textArea on the bottom left of the father
     */
    private void createLeftChild(String text, JTextArea father) {

        //int fatherGridX = textAreaToGridX.get(father);
        int fatherGridX = textAreaToGridX.get(father) +100;
        JTextArea firstSplit = new JTextArea();
        firstSplit.setEditable(false);
        firstSplit.setText(text);
        firstSplit.setBounds(5, 30, 990, 30);
      //  paneConstraints.gridx = (int) (Math.ceil((fatherGridX - getNearestLeftGridX(father)) / 2) + getNearestLeftGridX(father));
        paneConstraints.gridy = textAreaToGridY.get(father)+1;
        System.out.println(fatherGridX);
        paneConstraints.insets = new Insets(10, fatherGridX , 0, 0);
        pane.add(firstSplit, paneConstraints);


        textAreaToGridX.put(firstSplit, paneConstraints.gridx);
        textAreaToGridY.put(firstSplit, paneConstraints.gridy);
        childrenToParent.put(firstSplit, father);
        addMouseListener(firstSplit);



        System.out.println("LEFT CHILD /// fatherGridX: "+ fatherGridX + " gridX: "+ paneConstraints.gridx + " Nearest Left: " + getNearestLeftGridX(father));

    }

    /*
     * Add a textArea below the fatherGridX
     */
    private void createAloneChild(String text, JTextArea father) throws InstantiationException {
        ArrayList<ArrayList<String>> result = this.ListExpression();

        int tampon =  paneConstraints.gridy;
       paneConstraints.gridy = 0;
        int tamponX =  paneConstraints.gridx;
        paneConstraints.gridx = 0;
        int o = 0 ;
        System.out.println(result);
        for (int i = 0; i < result.size();++i){
            JTextArea split2 = new JTextArea();
            split2.setEditable(false);
            split2.setText("↓");
            split2.setBounds(5, 30, 990, 30);
           // paneConstraints.gridx =  o+1 ;
            System.out.println(paneConstraints.gridx);
            paneConstraints.gridy = 0 +1;



            paneConstraints.gridx = 0;

            paneConstraints.insets = new Insets(10, 50*o, 0, 0);


            pane.add(split2, paneConstraints);
            for (int j = 0; j < result.get(i).size(); ++j) {
                System.out.println(result.get(i).get(j));


                //   System.out.println(textAreaToGridX.get(father));
                //  System.out.println(textAreaToGridX.get(formulesDeveloppees));
                //  int fatherGridX = textAreaToGridX.get(father);

                JTextArea split = new JTextArea();
                split.setEditable(false);
                split.setText(result.get(i).get(j));
                split.setBounds(5, 30, 990, 30);
                //paneConstraints.gridx = fatherGridX;
                paneConstraints.gridy = paneConstraints.gridy +1;

                paneConstraints.insets = new Insets(10, 50*o, 0, 0);
                pane.add(split, paneConstraints);


             //   textAreaToGridX.put(split, paneConstraints.gridx);
            //    textAreaToGridY.put(split, paneConstraints.gridy);
            //    childrenToParent.put(split, father);
               // addMouseListener(split);
            }
            ++o;

        }
        paneConstraints.gridy = paneConstraints.gridy +1;
        paneConstraints.gridy = paneConstraints.gridy +1;
        paneConstraints.gridy = paneConstraints.gridy +1;
        JTextArea split = new JTextArea();
        split.setEditable(false);
        split.setText(valide);
        split.setBounds(5, 30, 990, 30);
        pane.add(split, paneConstraints);




    }




    private void createChildren(int couple, JTextArea father) throws InstantiationException {
        if ( couple == 1 ) //un seul couple est retourné
            createAloneChild("test1", father);
        else {
            createLeftChild("test2", father);
            createRightChild("test3", father);
        }

    }


}



