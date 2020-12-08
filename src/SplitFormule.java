
public class SplitFormule {

    private Formule f1, f2;
    private Operateur op;

    public SplitFormule(Formule f1, Operateur op, Formule f2) {
        this.f1 = f1;
        this.op = op;
        this.f2 = f2;
    }

    @Override
    public String toString() {
        return f1 +" "+ op.getPrint() +" "+ f2;
    }

    public Formule getF1() {
        return f1;
    }

    public Formule getF2() {
        return f2;
    }

    public Operateur getOp() {
        return op;
    }
}