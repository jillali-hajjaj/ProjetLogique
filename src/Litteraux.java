public class Litteraux extends Expression {
    private String litteral;
    private boolean verite;

    public Litteraux(String litteral) {
        this.litteral = litteral;
    }

    public boolean getVerite() {
        return verite;
    }


    String eval() {
        return null;
    }
}
