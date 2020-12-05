import java.util.List;

enum OP{
    and,or,implication,equivalence
}

public class Operateur {
    private OP op;
    public Operateur(String s) throws InstantiationException {
        switch (s){
            case "∨": this.op = OP.or;break;
            case "⇒": this.op = OP.implication;break;
            case "∧": this.op = OP.and;break;
            case "⇔": this.op = OP.equivalence;break;
            default: throw new InstantiationException("Problème d'opérateur");
        }
    }

    public OP getOp() {
        return op;
    }

    public void setOp(OP op) {
        this.op = op;
    }
}