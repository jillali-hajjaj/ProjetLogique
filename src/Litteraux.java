public class Litteraux extends Expression {
    private String litteral;

    @Override
    String eval() {
        return litteral;
    }
}
