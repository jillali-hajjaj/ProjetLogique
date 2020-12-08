
public class Literal {

    boolean isNegative;
    Character literal;

    public Literal(boolean isNegative, Character literal) {
        this.isNegative = isNegative;
        this.literal = literal;
    }

    @Override
    public String toString() {
        if (isNegative) {
            return Operateur.NOT.toString() + literal;
        } else {
            return literal+"";
        }
    }
}