package pt.unips.estsetubal.tapoo.adt;

public class EmptyStackException extends RuntimeException {
    public EmptyStackException() {
        super("The stack is empty.");
    }
}
