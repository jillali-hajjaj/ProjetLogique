 enum OP{
    and,or,implication,equivalence
}

public class Operateur {
    private OP op;
    private boolean negation;
    public Operateur(String s) {
        switch (s){
            case ""
        }
        this.op = op;
        negation = false;
    }

    public void setNegation(boolean negation) {
        this.negation = negation;
    }

    public OP getOp() {
        return op;
    }

    public boolean isNegation() {
        return negation;
    }
}