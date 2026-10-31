package pt.unips.estsetubal.tapoo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pt.unips.estsetubal.tapoo.adt.*;

class StackArrayIteratorTest {
    @Test
    void visitsTopToBottomWithoutChangingTheStack() {
        Stack<Integer> stack = new StackArray<>();
        Examples.fillStack(stack);
        assertIterableEquals(List.of(30, 20, 10), stack);
        assertEquals(3, stack.size());
        assertEquals(30, stack.peek());
        assertEquals(30, stack.pop());
        assertEquals(20, stack.pop());
        assertEquals(10, stack.pop());
    }

    @Test
    void emptyIteratorFollowsTheContract() {
        Iterator<Integer> iterator = new StackArray<Integer>().iterator();
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void singleElementAndRepeatedHasNext() {
        Stack<Integer> stack = new StackArray<>();
        stack.push(7);
        Iterator<Integer> iterator = stack.iterator();
        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasNext());
        assertEquals(7, iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void nextCanBeCalledWithoutHasNext() {
        Stack<Integer> stack = new StackArray<>();
        Examples.fillStack(stack);
        Iterator<Integer> iterator = stack.iterator();
        assertEquals(30, iterator.next());
        assertEquals(20, iterator.next());
        assertEquals(10, iterator.next());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void iteratorsHaveIndependentState() {
        Stack<Integer> stack = new StackArray<>();
        Examples.fillStack(stack);
        Iterator<Integer> first = stack.iterator();
        Iterator<Integer> second = stack.iterator();
        assertNotSame(first, second);
        assertEquals(30, first.next());
        assertEquals(20, first.next());
        assertEquals(30, second.next());
        assertIterableEquals(List.of(30, 20, 10), stack);
    }

    @Test
    void nullIsAnElementAndDoesNotMeanEndOfTraversal() {
        Stack<Integer> stack = new StackArray<>();
        stack.push(10);
        stack.push(null);
        assertIterableEquals(Arrays.asList(null, 10), stack);
    }

    @Test
    void removeIsUnsupported() {
        Stack<Integer> stack = new StackArray<>();
        stack.push(1);
        Iterator<Integer> iterator = stack.iterator();
        iterator.next();
        assertThrows(UnsupportedOperationException.class, iterator::remove);
        assertEquals(1, stack.size());
    }
}
