package pt.unips.estsetubal.tapoo.adt;

/** An abstract reference to a valid position in a data structure. */
public interface Position<E> {
    E element() throws InvalidPositionException;
}
