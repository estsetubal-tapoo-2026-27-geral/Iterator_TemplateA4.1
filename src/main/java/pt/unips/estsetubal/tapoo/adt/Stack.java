package pt.unips.estsetubal.tapoo.adt;

import java.util.Iterator;

/** A LIFO stack whose default traversal is top to bottom. */
public interface Stack<E> extends Iterable<E> {
    int size();
    boolean isEmpty();
    void push(E element) throws FullStackException;
    E pop() throws EmptyStackException;
    E peek() throws EmptyStackException;

    /** Returns a fresh iterator. The stack must not change during traversal. */
    @Override
    Iterator<E> iterator();

    /**
     * Returns a fresh bottom-to-top iterator.
     * The default body is a placeholder for the additional exercise.
     */
    default Iterator<E> iteratorInverse() {
        // TODO A4.1-C: override this method in both stack implementations.
        throw new UnsupportedOperationException("Inverse iterator not implemented yet.");
    }
}
