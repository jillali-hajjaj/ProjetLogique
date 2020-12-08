
public enum Operateur {
    AND('&', '∧'),
    OR('|', '∨'),
    NOT('!', '¬'),
    IMPLICATION('>', '→'),
    EQUIVALENCE('~', '↔');

    private Character label;
    private Character print;


    Operateur(Character label, Character print) {
        this.label = label;
        this.print = print;
    }

    public Character getLabel() {
        return label;
    }
    public Character getPrint() {
        return print;
    }


    public static Operateur parse(Character c) {
        if(c.equals(Operateur.AND.getPrint()) || c.equals(Operateur.AND.getLabel())){
            return Operateur.AND;
        }
        if(c.equals(Operateur.OR.getPrint()) || c.equals(Operateur.OR.getLabel())){
            return Operateur.OR;
        }
        if(c.equals(Operateur.IMPLICATION.getPrint()) || c.equals(Operateur.IMPLICATION.getLabel())){
            return Operateur.IMPLICATION;
        }
        if(c.equals(Operateur.EQUIVALENCE.getPrint()) || c.equals(Operateur.EQUIVALENCE.getLabel())){
            return Operateur.EQUIVALENCE;
        }
        return null;
    }
}