package pt.unips.estsetubal.tapoo.adt;

import java.util.Iterator;

/**
 * An ordered, general tree using the Position-based contract studied in A2.
 * This activity adds Iterable: iterator() must visit elements in pre-order.
 * positions() and elements() do not impose that traversal on the interface.
 */
public interface Tree<E> extends Iterable<E> {
    int size();
    boolean isEmpty();
    Position<E> root() throws EmptyTreeException;
    Position<E> parent(Position<E> position)
            throws InvalidPositionException, BoundaryViolationException;
    Iterable<Position<E>> children(Position<E> position)
            throws InvalidPositionException;
    boolean isInternal(Position<E> position) throws InvalidPositionException;
    boolean isExternal(Position<E> position) throws InvalidPositionException;
    boolean isRoot(Position<E> position) throws InvalidPositionException;
    Iterable<Position<E>> positions();
    Iterable<E> elements();
    Position<E> insert(Position<E> parent, E element)
            throws InvalidPositionException;
    Position<E> insert(Position<E> parent, E element, int order)
            throws InvalidPositionException, BoundaryViolationException;
    E replace(Position<E> position, E element) throws InvalidPositionException;

    /** Removes a leaf and invalidates its position. */
    E remove(Position<E> position)
            throws InvalidPositionException, IllegalStateException;

    /**
     * Returns a fresh pre-order iterator, respecting the order of children.
     * The tree must not change during traversal. Iterator.remove() is unsupported.
     */
    @Override
    Iterator<E> iterator();
}
