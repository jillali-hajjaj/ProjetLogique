public class ExpressionComplexe extends Expression {
    private Operateur operateur;
    private Expression a;
    private Expression b;

    public ExpressionComplexe(Operateur operateur, Expression a, Expression b) {
        this.operateur = operateur;
        this.a = a;
        this.b = b;
    }


    public boolean eval(Litteraux a, Litteraux b,Operateur op) throws InstantiationException {
        switch (op.getOp()){
            case and:
                return a.getVerite() && b.getVerite();
            case or:
                return a.getVerite() || b.getVerite();
            case implication:
                return !a.getVerite() || b.getVerite();
            case equivalence:
                return eval(a,b,new Operateur("⇒")) && eval(b,a,new Operateur("⇒"));
            default: throw new InstantiationException("evaluation fausse");
        }
    }

    public void transformeStringToExpression(String s){
        // (Litteral opérateur Literral)
    }
}
