import java.lang.reflect.Array;
import java.util.ArrayList;

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

    public void stringToExpression(String formule) throws InstantiationException {
        String sousExpression = "";
        int compteurDeparenthese = 0;
        ArrayList<Expression> list = new ArrayList<>();
        ArrayList<Operateur> opera = new ArrayList<>();
        for (int i = 0; i < formule.length(); i++) {
            if(formule.charAt(i) == '(' || isOperateur(formule.charAt(i))){
                if(formule.charAt(i) == '(') compteurDeparenthese++;
                for (int j = i; j < formule.length(); j++) {
                    sousExpression+= formule.charAt(j);
                    if(isOperateur(formule.charAt(j))) {
                        if(formule.charAt(j-1) == ')'){
                            opera.add(new Operateur(Character.toString(formule.charAt(j))));
                        }
                    }

                    if(formule.charAt(j) == ')') {
                        compteurDeparenthese--;
                        list.add(stringToSousExpression(sousExpression));
                        i=j;
                        sousExpression="";
                        break;
                    }
                }
            }
        }
        this.a = list.remove(0);
        this.b = list.remove(0);
        this.op = opera.remove(0);
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

    public Expression stringToSousExpression(String sousformule) throws InstantiationException {
        char op =' ';
        ArrayList<Character> li = new ArrayList<Character>();
        for (int i = 0; i < sousformule.length(); i++) {
            switch (sousformule.charAt(i)) {
                case '∨' -> op = '∨';
                case '⇒' -> op = '⇒';
                case '∧' -> op = '∧';
                case '⇔' -> op = '⇔';
            }
            if(sousformule.charAt(i) == 'p' || sousformule.charAt(i)=='q'){
                li.add(sousformule.charAt(i));
            }
        }
        if(li.size() ==1){ return new Litteraux(Character.toString(li.get(0)));}
        else return new ExpressionComplexe(new Operateur(Character.toString(op)), new Litteraux(Character.toString(li.get(0))), new Litteraux(Character.toString(li.get(1))));
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
        e.stringToExpression("((p⇒q)⇒q)⇒q");
        System.out.println("((p⇒q)⇒q)");
        System.out.println(e.toString());
        System.out.println(e.getVerite());
        System.out.println(e.toString());
        System.out.println(e.b.verite);
    }
}
