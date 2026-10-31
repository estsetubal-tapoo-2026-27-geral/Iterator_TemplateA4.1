package pt.unips.estsetubal.tapoo.adt;

import java.util.Iterator;

/** Singly linked stack. Its operations are complete; its iterator is an exercise. */
public class StackLinked<E> implements Stack<E> {
    private Node top;
    private int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return top == null;
    }

    @Override
    public void push(E element) {
        top = new Node(element, top);
        size++;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return top.element;
    }

    @Override
    public E pop() {
        E element = peek();
        top = top.next;
        size--;
        return element;
    }

    @Override
    public Iterator<E> iterator() {
        // TODO A4.1-A: return a new instance of a private iterator class.
        // Keep the current node in the iterator, not in the stack.
        throw new UnsupportedOperationException("StackLinked iterator not implemented yet.");
    }

    // TODO A4.1-C: add iteratorInverse() and its private iterator class.

    private class Node {
        private final E element;
        private final Node next;

        private Node(E element, Node next) {
            this.element = element;
            this.next = next;
        }
    }
}
