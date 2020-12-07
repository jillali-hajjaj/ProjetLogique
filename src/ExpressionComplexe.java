import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.stream.Collectors;

public class ExpressionComplexe extends Expression {
    private Expression a;
    private Expression b;

    public ExpressionComplexe(Operateur operateur, Expression a, Expression b) throws InstantiationException {
        super(true,operateur);
        this.a = a;
        this.b = b;
        setVerite(this.getVerite());

    }

    public ExpressionComplexe() {
        super(true,null);
    }

    @Override
    protected boolean getVerite() throws InstantiationException {
        switch (op.getOp()){
            case and:
            if(a.getVerite() && b.getVerite()) {
                this.setVerite(true);
                return true;
            }else {this.setVerite(true); return true;}
            case or:
                 if(a.getVerite() || b.getVerite()) {
                     this.setVerite(true);
                     return true;
                 }else {this.setVerite(true); return true;}
            case implication:
                a.setVerite(false); op.setOp(OP.or); return this.getVerite();
            case equivalence:
                a = new ExpressionComplexe(new Operateur("⇒"),a,b); op.setOp(OP.and); b=new ExpressionComplexe(new Operateur("⇒"),b,a); this.getVerite();
            default: throw new InstantiationException("evaluation fausse");
        }
    }



    @Override
    protected void setVerite(boolean b) {
        verite = b;
    }

    @Override
    protected Operateur getOp() {
        return op;
    }

    @Override
    protected void setOp(Operateur b) {
        op = b;
    }


    public ArrayList<Litteraux> stringToLiterauxWithPriority(String formule){
        ArrayList<Litteraux> litteraux = new ArrayList<>();
        int prio = 0;
        for (int i = 0; i <formule.length(); i++) {
            if(formule.charAt(i) == '('){
                prio++;
            }
            if(formule.charAt(i) == ')'){
                prio--;
            }
            if(formule.charAt(i) == 'p'){
                Litteraux l = new Litteraux("p");
                l.setPriority(prio);
                litteraux.add(l);
            }
            else if(formule.charAt(i) == 'q'){
                Litteraux l = new Litteraux("q");
                l.setPriority(prio);
                litteraux.add(l);
            }
        }
        return litteraux;
    }

    static int precedence(char c){
        switch (c){
            case '∨':
                return 1;
            case '⇒':
            case '∧':
            case '⇔':
                return 2;
        }
        return -1;
    }

    static String infixToPostFix(String expression){

        String result = "";
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i <expression.length() ; i++) {
            char c = expression.charAt(i);

            //check if char is operator
            if(precedence(c)>0){
                while(stack.isEmpty()==false && precedence(stack.peek())>=precedence(c)){
                    result += stack.pop();
                }
                stack.push(c);
            }else if(c==')'){
                char x = stack.pop();
                while(x!='('){
                    result += x;
                    x = stack.pop();
                }
            }else if(c=='('){
                stack.push(c);
            }else{
                //character is neither operator nor (
                result += c;
            }
        }
        for (int i = 0; i <=stack.size() ; i++) {
            result += stack.pop();
        }
        return result;
    }

    public void stringToExpression(String formule) throws InstantiationException {
       String postfixe = infixToPostFix(formule);
       ArrayList<String> litteraux = new ArrayList<>();
       ExpressionComplexe e = new ExpressionComplexe();
        System.out.println(postfixe);
        ArrayList<String> etapes = new ArrayList<>();
       String result = "";
           for (int i = 0; i < postfixe.length(); i++) {
               if (!isOperateur(postfixe.charAt(i))) {
                   litteraux.add(Character.toString(postfixe.charAt(i)));
               } else if (postfixe.charAt(i) == '⇒') {
                   if (!litteraux.contains("∨") && !litteraux.contains("∧")) {
                       litteraux.add(0, "¬");
                       litteraux.add("∨");
                   } else {
                       String dernier = "";
                       dernier = litteraux.get(litteraux.size() - 1);
                       ArrayList<String> sousFormule = new ArrayList<>();
                       for (int j = 0; j < litteraux.size() - 1; j++) {
                           sousFormule.add(litteraux.get(j));
                       }
                       for (int j = 0; j < sousFormule.size(); j++) {
                           if (sousFormule.get(j).equals("¬")) {
                               sousFormule.set(j, "");
                               j++;
                           } else {
                               switch (sousFormule.get(j)) {
                                   case "∧":
                                       sousFormule.set(j, "∨");
                                       break;
                                   case "∨":
                                       sousFormule.set(j, "∧");
                                       break;
                                   case "⇒":
                                       break;
                                   default:
                                       sousFormule.add(j, "¬");
                                       j++;
                               }
                           }
                       }
                       litteraux = sousFormule;
                       litteraux.add(dernier);
                       litteraux = (ArrayList<String>) litteraux.stream().filter(s -> !s.equals("")).collect(Collectors.toList());
                       litteraux.add("∨");
                       dernier ="";
                       for (int j = 0; j < litteraux.size(); j++) {
                           dernier+= litteraux.get(j);
                       }
                       etapes.add(dernier);
                   }
               }
           }
           postfixe = "";
           for (int i = 0; i < litteraux.size(); i++) {
               postfixe+= litteraux.get(i);
           }

        for (int i = 0; i < litteraux.size(); i++) {
            result+= litteraux.get(i);
        }
        System.out.println(result);
        System.out.println(etapes);
    }

    public String stringToExpression2(String formule) throws InstantiationException {
        String postfixe = infixToPostFix(formule);
        ArrayList<String> litteraux = new ArrayList<>();
        ExpressionComplexe e = new ExpressionComplexe();
        System.out.println(postfixe);
        ArrayList<String> etapes = new ArrayList<>();
        String result = "";
        for (int i = 0; i < postfixe.length(); i++) {
            if (!isOperateur(postfixe.charAt(i))) {
                litteraux.add(Character.toString(postfixe.charAt(i)));
            } else if (postfixe.charAt(i) == '⇒') {
                if (!litteraux.contains("∨") && !litteraux.contains("∧")) {
                    litteraux.add(0, "¬");
                    litteraux.add("∨");
                } else {
                    String dernier = "";
                    dernier = litteraux.get(litteraux.size() - 1);
                    ArrayList<String> sousFormule = new ArrayList<>();
                    for (int j = 0; j < litteraux.size() - 1; j++) {
                        sousFormule.add(litteraux.get(j));
                    }
                    for (int j = 0; j < sousFormule.size(); j++) {
                        if (sousFormule.get(j).equals("¬")) {
                            sousFormule.set(j, "");
                            j++;
                        } else {
                            switch (sousFormule.get(j)) {
                                case "∧":
                                    sousFormule.set(j, "∨");
                                    break;
                                case "∨":
                                    sousFormule.set(j, "∧");
                                    break;
                                case "⇒":
                                    break;
                                default:
                                    sousFormule.add(j, "¬");
                                    j++;
                            }
                        }
                    }
                    litteraux = sousFormule;
                    litteraux.add(dernier);
                    litteraux = (ArrayList<String>) litteraux.stream().filter(s -> !s.equals("")).collect(Collectors.toList());
                    litteraux.add("∨");
                    dernier ="";
                    for (int j = 0; j < litteraux.size(); j++) {
                        dernier+= litteraux.get(j);
                    }
                    etapes.add(dernier);
                }
            }
        }
        postfixe = "";
        for (int i = 0; i < litteraux.size(); i++) {
            postfixe+= litteraux.get(i);
        }

        for (int i = 0; i < litteraux.size(); i++) {
            result+= litteraux.get(i);
        }
      //  System.out.println(result);
        System.out.println(etapes);
        return result;
    }
    public boolean isOperateur(char c){
        return switch (c) {
            case '∨' -> true;
            case '⇒' -> true;
            case '∧' -> true;
            case '⇔' -> true;
            default -> false;
        };
    }


    @Override
    public String toString() {
        return "ExpressionComplexe{" +
                "op=" + op.getOp() +
                ", a=" + a +
                ", b=" + b +
                '}';
    }

    public void transformeStringToExpression(String s){
        // (Litteral opérateur Literral)
    }

    public static void main(String[] args) throws InstantiationException {
        ExpressionComplexe e = new ExpressionComplexe();
        System.out.println(("((p⇒q)⇒q)⇒q"));
        e.stringToExpression("(u∧(w⇒v)∧(t⇒v)∧(u⇒(w∨t)))⇒v");
        System.out.println(e.stringToExpression2("(u∧(w⇒v)∧(t⇒v)∧(u⇒(w∨t)))⇒v"));
    }
}
