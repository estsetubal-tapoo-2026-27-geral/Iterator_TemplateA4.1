package pt.unips.estsetubal.tapoo.adt;

import java.util.Iterator;
import java.util.NoSuchElementException;

/** Array-based stack with a complete top-to-bottom iterator. */
public class StackArray<E> implements Stack<E> {
    private static final int DEFAULT_CAPACITY = 500;
    private final E[] storage;
    private int size;

    public StackArray() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public StackArray(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive.");
        }
        storage = (E[]) new Object[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void push(E element) {
        if (size == storage.length) {
            throw new FullStackException();
        }
        storage[size++] = element;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return storage[size - 1];
    }

    @Override
    public E pop() {
        E element = peek();
        storage[--size] = null;
        return element;
    }

    @Override
    public Iterator<E> iterator() {
        return new IteratorStack();
    }

    // TODO A4.1-C: add iteratorInverse() and its private iterator class.

    private class IteratorStack implements Iterator<E> {
        private int pos = size - 1;

        @Override
        public boolean hasNext() {
            return pos >= 0;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return storage[pos--];
        }
    }
}
