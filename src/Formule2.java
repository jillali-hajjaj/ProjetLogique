
import java.util.ArrayList;

public class Formule2 {

    public static Boolean DEBUG = false;

    boolean isNegative;
    Formule2 f1;
    Operateur op;
    Formule2 f2;


    public Formule2(String formule) {
        //   On fais une copie du paramètre, puis on lui retire tout les espaces " "
        String copie = new String(formule) ;
        copie = copie.replaceAll(" ", "") ;


        //   Simplifier les parenthèses
        int countParentheses = 0;
        for (int i = 0; i < copie.length(); i++) {
            if (copie.charAt(i) == '(') {countParentheses++;}
            if (copie.charAt(i) == ')') {countParentheses--;}
        }
        if (countParentheses > 0) {copie = copie.substring(countParentheses, copie.length());}
        if (countParentheses < 0) {copie = copie.substring(0, copie.length()- (countParentheses*-1));}
        if (countParentheses == 0 && copie.charAt(0) == '(' && copie.charAt(copie.length()-1) == ')') {copie = copie.substring(1, copie.length()-1);}
        if(DEBUG){System.out.print("Parenthèses : " + countParentheses); System.out.println("  -> Rectifié : "+copie);}


        //   Si il y a moins de 3 caractères ce n'est pas une formule mais un litéral (positif ou négatif)
        if (copie.length() < 3) {
            System.out.println("Litéral : " + copie);
            // dans ce cas on a un litéral

        }else {
            //   Dans ce cas on a une formule

            //   Est-ce que notre formule commence par l'operateur 'non', si oui la formule est négative.
            Character negative = copie.charAt(0);
            Character next = copie.charAt(1);
            if( (negative.equals(Operateur.NOT.getPrint()) || negative.equals(Operateur.NOT.getLabel())) && next.equals('(') ){
                this.isNegative = true;
                copie = copie.substring(2, copie.length() - 1);

            } else {
                this.isNegative = false;
            }

            // DEBUG
            if(DEBUG) {System.out.println("-=-=-=-=-=-==-=-=-=-=-=-=-=");System.out.println("Négative ? :" + isNegative);System.out.println(copie + "\n");}


            //   Ensuite on cherche l'opérateur principal de la formule

            int taille = copie.length() ;

            ArrayList<Integer> index = new ArrayList<>() ;
            ArrayList<Integer> priorite = new ArrayList<>() ;
            ArrayList<Operateur> operateursTrouve = new ArrayList<>() ;

            int prio = 0;
            for (int i = 0; i < taille; i++) {
                if (copie.charAt(i) == '(') {  prio++ ;}
                if (copie.charAt(i) == ')') {  prio-- ;}

                if (Operateur.parse(copie.charAt(i)) != null) {
                    Operateur op = Operateur.parse(copie.charAt(i)) ;
                    if (op != Operateur.NOT) {
                        index.add(i);
                        priorite.add(prio);
                        operateursTrouve.add(op);
                    }
                }
            }
            int minPrio = priorite.get(0) ;
            int minIndex = 0 ;
            for (int i = 0; i < priorite.size(); i++) {
                if (priorite.get(i) < minPrio) {
                    minPrio = priorite.get(i) ;
                    minIndex = i;
                }
            }
            /*    DEBUG */
            if(DEBUG) {System.out.println("-=-=-=-=-=-==-=-=-=-=-=-=-=");System.out.println("Indexs " + index);System.out.println("Priorites " + priorite);System.out.println("Operateurs " + operateursTrouve);
                System.out.println("Op Prin: " + operateursTrouve.get(minIndex) + " [ i: " + index.get(minIndex) + ", p: " + minPrio + "]");}

            String p1 = copie.substring(0, index.get(minIndex)) ;
            String p2 = copie.substring(index.get(minIndex)+1, taille) ;

            if (DEBUG) {System.out.println("\n" + p1 + " " + operateursTrouve.get(minIndex) + " " +p2);}


            this.op = operateursTrouve.get(minIndex) ;
            this.f1 = new Formule2(p1);
            this.f2 = new Formule2(p2);
        }
    }

    public Formule2(boolean isNegative, Formule2 f1, Operateur op, Formule2 f2) {
        this.isNegative = isNegative;
        this.f1 = f1;
        this.op = op;
        this.f2 = f2;
    }

    public Formule2(Formule2 f) {
        this.isNegative = f.isNegative;
        this.f1 = f.f1;
        this.op = f.op;
        this.f2 = f.f2;    }


    @Override
    public String toString() {
        if (isNegative) {
            return Operateur.NOT.getPrint() + "(" + f1 + op.getPrint() + f2 + ")";
        } else {
            return "(" + f1 + ") " + op.getPrint() + " (" + f2 + ")" ;
        }
    }

    public Formule2 split() {
        evalNegative();
        return this;
    }

    private void evalNegative() {
        if (isNegative) {
            switch (op) {
                case AND : {
                    f1.isNegative = !f1.isNegative;
                    f2.isNegative = !f2.isNegative;
                    op = Operateur.OR ;
                }
                case OR : {
                    f1.isNegative = !f1.isNegative;
                    f2.isNegative = !f2.isNegative;
                    op = Operateur.AND ;
                }
                case IMPLICATION : {
                    f1.isNegative = !f1.isNegative;
                    //    f2.isNegative = f2.isNegative;
                    op = Operateur.OR ;
                }
            }
        }
    }

}