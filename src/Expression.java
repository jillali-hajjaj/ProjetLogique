public abstract class Expression {
    protected boolean verite;
    protected Operateur op;
    public Expression(boolean b,Operateur o) {
         verite = b;
         this.op = o;
    }


    protected abstract boolean getVerite() throws InstantiationException;

    protected abstract void setVerite(boolean b);

    protected abstract Operateur getOp();

    protected abstract void setOp(Operateur b);

}