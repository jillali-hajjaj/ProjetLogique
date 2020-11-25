import java.util.List;

enum OP{
    and,or,implication,equivalence
}

public class Operateur {
    private OP op;
    public Operateur(String s) throws InstantiationException {
        switch (s){
            case "∨": this.op = OP.or;
            case "⇒": this.op = OP.implication;
            case "∧": this.op = OP.and;
            case "⇔": this.op = OP.equivalence;
            default: throw new InstantiationException("Problème d'opérateur");
        }
    }

    public OP getOp() {
        return op;
    }

}