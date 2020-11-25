public class ExpressionComplexe extends Expression {
    private Operateur operateur;
    private Expression a;
    private Expression b;

    public ExpressionComplexe(Operateur operateur, Expression a, Expression b) {
        this.operateur = operateur;
        this.a = a;
        this.b = b;
    }

    @Override
    String eval() {
        return null;
    }

    public void transformeStringToExpression(String s){
        // (Litteral opérateur Literral)
    }
}
