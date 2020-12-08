
public class CannotParseOperatorException extends RuntimeException {

    private static final long serialVersionUID = -2832539791065707294L;
    private Character op;
    public CannotParseOperatorException(Character operator) {
        op = operator;
    }

    @Override
    public String getMessage() {
        return "Nous n'avons pas pu transformer " + op + " en un opérateur valide !";
    }
}