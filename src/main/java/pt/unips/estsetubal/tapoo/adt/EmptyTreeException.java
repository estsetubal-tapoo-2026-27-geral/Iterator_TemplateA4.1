package pt.unips.estsetubal.tapoo.adt;

public class EmptyTreeException extends RuntimeException {
    public EmptyTreeException() {
        super("The tree is empty.");
    }
}
