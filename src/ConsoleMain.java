

import java.util.Random;
import java.util.Scanner;

public class ConsoleMain {

    public static void main(String[] args) {
        String formule = getRandomFormule();
        System.out.println("Formule = " + "¬(" + formule  + ")");
        System.out.println("-*=                     =*- \n");


        Formule f = new Formule("¬(" + formule  + ")") ;
        System.out.println("La formule est : " + f.toString());


        Formule last = null;
        Scanner sc = new Scanner(System.in);
        int scan = 0;
        do{
            SplitFormule sF = f.split();
            System.out.println(sF);
            System.out.println("1: Diviser " + sF.getF1());
            System.out.println("2: Diviser " + sF.getF2());
            System.out.println("3: Retour ");

            scan = sc.nextInt();
            if (scan == 1) {
                last = f;
                f = sF.getF1();
            }
            if (scan == 2) {
                last = f;
                f  = sF.getF2();
            }
            if (scan == 3) {
                f  = last;
            }
        }while(scan != 0) ;

    }

    private static String getRandomFormule() {
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
                return "(((s ∧ p) → (q ∧ r)) ∧ (¬r ∨ ¬q) ∧ p) → ¬s";
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
}