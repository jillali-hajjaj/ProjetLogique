public class Litteraux extends Expression {

    private String label;
    private int priority;
    public Litteraux(String s) {
        super(true,null);
        label = s;
        priority = 0;
    }

    public boolean getVerite() {
        return verite;
    }
    public void setVerite(boolean b) {
         verite=b;
    }

    @Override
    protected Operateur getOp() {
        return op;
    }

    @Override
    protected void setOp(Operateur b) {
        op = b;
    }

    public String getLabel() {
        return label;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
