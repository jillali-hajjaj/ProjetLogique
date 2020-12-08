import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ardoise extends JPanel  {
    private static final long serialVersionUID = 6335221551069472985L;
    // private static JTextField textField;
    private static JTextArea a;
    private static JLabel b;
    public String Mots = "";
    public int compteur = 0 ;
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
    JLabel Valide = new JLabel();

    public String valide ;
    public ArrayList<String> tb = new ArrayList<>();

    public JTextArea formulesDeveloppees = new JTextArea();
    public ardoise() {
        compteur = 0;
        maxBranchs = 100;

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.NORTH;

        pane = new JLayeredPane();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets = new Insets(10, 0, 0, 0);
        pane.setPreferredSize(new Dimension(1000, 500));
        pane.setBorder(BorderFactory.createTitledBorder("Dessin"));

        pane.setLayout(new GridBagLayout());
        paneConstraints = new GridBagConstraints();
        paneConstraints.fill = GridBagConstraints.NORTH;
        add(pane, constraints);


        // OptionsPanel.setPoints(0);

    }
    /*
        public ArrayList<ArrayList<String>> ListExpression()throws InstantiationException {

            String form = "e.stringToExpression2(Mots)";

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
        */
    public void dessin() {

        compteur = 0 ;


        JTextArea formulesDeveloppees = new JTextArea();
        formulesDeveloppees.setEditable(false);
        formulesDeveloppees.setText(Mots);
        formulesDeveloppees.setBounds(5, 30, 990, 30);
        maxBranchs = 100; /*countSymbolesPropositionnels(textField.getText())*2; *///x2 au cas où ce soit seulement des OU

        for ( Component comp : pane.getComponents() ) {
            pane.remove(comp);
        }

        if ( maxBranchs % 2 == 1 )
            paneConstraints.gridx = maxBranchs/2+1;
        else
            paneConstraints.gridx = maxBranchs/2;

        paneConstraints.gridy = 0;
        paneConstraints.insets = new Insets(10, 0, 0, 0);
        pane.add(formulesDeveloppees, paneConstraints);

        addMouseListener(formulesDeveloppees);

        textAreaToGridX.clear();
        textAreaToGridX.put(formulesDeveloppees, paneConstraints.gridx);
        textAreaToGridY.put(formulesDeveloppees, paneConstraints.gridy);
        rootArea = formulesDeveloppees;


        Valide.setLocation(50,40);
        add(Valide);
        pane.revalidate();
        pane.repaint();



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
    public int ML = 0;
    private void addMouseListener(JTextArea area){
        area.addMouseListener(new MouseListener() {


            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getComponent() instanceof JTextArea) {

                    if (e.getButton() == 1) { //clic gauche

                        SplitFormule formules = new Formule(((JTextArea) e.getComponent()).getText()).split();
                        //System.out.println(e.getComponent().getClass());

                        if (e.getComponent() instanceof JTextArea) {
                            // System.out.println((((JTextArea) e.getComponent()).getText()));
                            //  couple<String> formules = new formule(((JTextArea) e.getComponent().getText()).split();
                            System.out.println(textAreaToGridX.get(e.getComponent()));

                            createChildren(formules, (JTextArea) e.getComponent());


                            //fix GUI and add listener to the textArea generated
                            pane.revalidate();
                            //addMouseListener(firstSplit);


                            //remove listener for this area
                            for (MouseListener m : e.getComponent().getMouseListeners()) {
                                e.getComponent().removeMouseListener(m);
                            }
                            compteur++;
                            touche.redessiner();
                            Valide.setText("");

                            add(Valide);
                        }
                    }

                    else if ( e.getButton() == 3 ) { //clic droit
                        String str = ((JTextArea) e.getComponent()).getText().replaceAll(" ", "");

                        if (containsContradiction(str)) {
                            JTextArea contra = new JTextArea();
                            contra.setEditable(false);
                            contra.setText("⊥");
                            contra.setBounds(5, 30, 990, 30);

                            Font font = new Font("helvetica", Font.PLAIN, 12);
                            Map attributes = font.getAttributes();
                            attributes.put(TextAttribute.FONT, TextAttribute.FONT);
                            Font newFont = new Font(attributes);

                            int test = textAreaToGridY.get((JTextArea)e.getComponent())+1;
                            paneConstraints.gridx = textAreaToGridX.get((JTextArea)e.getComponent());
                            paneConstraints.gridy = textAreaToGridY.get((JTextArea)e.getComponent()) + 1;
                            double X = ((JTextArea) e.getComponent()).getLocation().getX();
                            double Y = ((JTextArea) e.getComponent()).getLocation().getY();
                            System.out.println("Position du X " +(int) X);
                            System.out.println("Position du Y " +(int) Y);
                            contra.setLocation((int) X, (int) Y + 10);
                            System.out.println(contra.getLocation());
                            pane.add(contra , paneConstraints);
                            ((JTextArea) e.getComponent()).setFont(newFont);

                            for (MouseListener m : e.getComponent().getMouseListeners()) {
                                e.getComponent().removeMouseListener(m);
                            }
                        } else {
                            JTextArea contra = new JTextArea();
                            contra.setEditable(false);
                            contra.setText("X");
                            contra.setBounds(5, 30, 990, 30);

                            Font font = new Font("helvetica", Font.PLAIN, 12);
                            Map attributes = font.getAttributes();
                            attributes.put(TextAttribute.FONT, TextAttribute.FONT);
                            Font newFont = new Font(attributes);

                            int test = textAreaToGridY.get((JTextArea)e.getComponent())+1;
                            paneConstraints.gridx = textAreaToGridX.get((JTextArea)e.getComponent());
                            paneConstraints.gridy = textAreaToGridY.get((JTextArea)e.getComponent()) + 1;
                            double X = ((JTextArea) e.getComponent()).getLocation().getX();
                            double Y = ((JTextArea) e.getComponent()).getLocation().getY();
                            System.out.println("Position du X " +(int) X);
                            System.out.println("Position du Y " +(int) Y);
                            contra.setLocation((int) X, (int) Y + 10);
                            System.out.println(contra.getLocation());
                            pane.add(contra , paneConstraints);
                            ((JTextArea) e.getComponent()).setFont(newFont);

                            for (MouseListener m : e.getComponent().getMouseListeners()) {
                                e.getComponent().removeMouseListener(m);
                            }
                        }
                    }
                }
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

        });
    }



	/*private int countSymbolesPropositionnels(String str) {
		int count = 0;
		for ( int i = 0 ; i < str.length() ; i++ ) {
			if ( str.charAt(i) == '∧' || str.charAt(i) == '∨' || str.charAt(i) == '¬' || ((int) str.charAt(i)) == 8594) {
				count++;
			}
		}
		return count;
	}*/


    /*
     * Get the nearest left grid x by going up in the tree
     */
    private int getNearestLeftGridX(JTextArea directFather) {

        //System.out.println("-------NEAREST LEFT----------");

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

		/*System.out.println("actual = directFather: "+ actual.equals(directFather));
		System.out.println("actual = rootArea : "+ actual.equals(rootArea) +" && hasOnlyReduced: "+ gridXHasOnlyReduced);

		System.out.println("---------------------");*/
        if ( actual.equals(directFather) || (actual.equals(rootArea) && gridXHasOnlyReduced) )
            return 0;

        //System.out.println("nearestLeftGridX: "+textAreaToGridX.get(actual));
        return textAreaToGridX.get(actual);
    }

    /*
     * Get the nearest right grid x by going up in the tree
     */
    private int getNearestRightGridX(JTextArea directFather) {

        //System.out.println("-------NEAREST RIGHT----------");

        JTextArea actual = directFather;
        boolean gridXHasOnlyReduced = true;

        while ( childrenToParent.get(actual) != null ) { //tant qu'il y a un père
            if ( textAreaToGridX.get(childrenToParent.get(actual)) < textAreaToGridX.get(actual) ) {
                actual = childrenToParent.get(actual);
            } else {
                actual = childrenToParent.get(actual);
                gridXHasOnlyReduced = false;
                //System.out.println("broke at the parent of : "+actual.getText());
                break;
            }
        }

		/*System.out.println("actual = directFather: "+ actual.equals(directFather));
		System.out.println("actual = rootArea : "+ actual.equals(rootArea) +" && hasOnlyReduced: "+ gridXHasOnlyReduced);

		System.out.println("---------------------");*/
        if ( actual.equals(directFather) || (actual.equals(rootArea) && gridXHasOnlyReduced) )
            return maxBranchs;

        return textAreaToGridX.get(actual);
    }



    /*
     * Add a textArea on the bottom right of the father
     */
    private void createRightChild(String text, JTextArea father) {
        JTextArea fleche = new JTextArea();
        fleche.setEditable(false);
        fleche.setText("↘");
        fleche.setBounds(5, 30, 990, 30);

        int fatherGridX = textAreaToGridX.get(father);


        JTextArea secondSplit = new JTextArea();
        secondSplit.setEditable(false);
        secondSplit.setText(text);
        secondSplit.setBounds(5, 30, 990, 30);
        paneConstraints.gridx = (int) (Math.ceil((getNearestRightGridX(father) - fatherGridX) / 2.0 ) + fatherGridX);
        paneConstraints.gridy = textAreaToGridY.get(father)+1;

        paneConstraints.insets = new Insets(10, 0, 0, 0);
        pane.add(fleche, paneConstraints);
        paneConstraints.gridx = paneConstraints.gridx +1;
        paneConstraints.gridy = paneConstraints.gridy +1;
        pane.add(secondSplit, paneConstraints);


        textAreaToGridX.put(secondSplit, paneConstraints.gridx);
        textAreaToGridY.put(secondSplit, paneConstraints.gridy);
        childrenToParent.put(secondSplit, father);
        addMouseListener(secondSplit);


        //System.out.println("RIGHT CHILD /// fatherGridX: "+ fatherGridX + " gridX: "+ paneConstraints.gridx + " NearestRight: "+ getNearestRightGridX(father) );


    }

    /*
     * Add a textArea on the bottom left of the father
     */
    private void createLeftChild(String text, JTextArea father) {

        int fatherGridX = textAreaToGridX.get(father);

        JTextArea fleche = new JTextArea();
        fleche.setEditable(false);
        fleche.setText("↙");
        fleche.setBounds(5, 30, 990, 30);

        JTextArea firstSplit = new JTextArea();
        firstSplit.setEditable(false);
        firstSplit.setText(text);
        firstSplit.setBounds(5, 30, 990, 30);
        paneConstraints.gridx = (int) (Math.ceil((fatherGridX - getNearestLeftGridX(father)) / 2) + getNearestLeftGridX(father)) ;
        paneConstraints.gridy = textAreaToGridY.get(father)+1;

        paneConstraints.insets = new Insets(10, 0, 0, 0);
        pane.add(fleche, paneConstraints);
        paneConstraints.gridx = paneConstraints.gridx -1;
        paneConstraints.gridy = paneConstraints.gridy +1;
        pane.add(firstSplit, paneConstraints);


        textAreaToGridX.put(firstSplit, paneConstraints.gridx);
        textAreaToGridY.put(firstSplit, paneConstraints.gridy);
        childrenToParent.put(firstSplit, father);
        addMouseListener(firstSplit);

    }

    /*
     * Add a textArea below the fatherGridX
     */
    private void createAloneChild(SplitFormule formule, JTextArea father) {

        int fatherGridX = textAreaToGridX.get(father);

        JTextArea fleche = new JTextArea();
        fleche.setEditable(false);
        fleche.setText("↓");
        fleche.setBounds(5, 30, 990, 30);


        JTextArea split = new JTextArea();
        split.setEditable(false);
        split.setText(formule.getF1().toString()+"\n\n"+formule.getF2().toString());
        split.setBounds(5, 30, 990, 30);
        paneConstraints.gridx = fatherGridX;
        paneConstraints.gridy = textAreaToGridY.get(father)+1;

        paneConstraints.insets = new Insets(10, 0, 0, 0);
        pane.add(fleche, paneConstraints);
        paneConstraints.gridy = paneConstraints.gridy +1;
        pane.add(split, paneConstraints);


        textAreaToGridX.put(split, paneConstraints.gridx);
        textAreaToGridY.put(split, paneConstraints.gridy);
        childrenToParent.put(split, father);
        addMouseListener(split);
    }




    private void createChildren(SplitFormule formule, JTextArea father) {
        if ( formule.getOp() == Operateur.AND ) {
            createAloneChild(formule, father);
        }
        else {
            createLeftChild(formule.getF1().toString(), father);
            createRightChild(formule.getF2().toString(), father);
        }

    }



    private boolean containsContradiction(String str) {
        String[] parts = str.split("\n\n");
        String[] tab = new String[parts.length];

        for ( int i = 0 ; i < parts.length ; i++ ) {
            parts[i] = parts[i].replaceAll(" ", "");
            parts[i] = parts[i].replaceAll("\\(", "");
            parts[i] = parts[i].replaceAll("\\)", "");

            tab[i]= parts[i];
        }


        for ( String part : tab ) {

            //System.out.println("length : "+part.length()+ " ("+part+")");

            if ( part.length() < 3 ) {
                for ( String otherPart : parts ) {

                    //System.out.println("part: "+part+" / otherPart: "+otherPart);

                    if ( !part.equals(otherPart) ) {

                        if ( part.contains("¬") && !otherPart.contains("¬") ) {

                            char c = part.charAt(part.indexOf("¬")+1);
                            if ( otherPart.contains(c+"") )
                                return true;

                        } else if ( otherPart.contains("¬") && !part.contains("¬") ) {

                            char c = otherPart.charAt(part.indexOf("¬")+1);
                            if ( part.contains(c+"") )
                                return true;

                        }

                    }
                }
            }
        }

        return false;
    }





}



