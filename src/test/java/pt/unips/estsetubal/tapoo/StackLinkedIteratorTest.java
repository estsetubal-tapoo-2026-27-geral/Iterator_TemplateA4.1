package pt.unips.estsetubal.tapoo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import static org.junit.jupiter.api.Assertions.*;
import pt.unips.estsetubal.tapoo.adt.*;

@Disabled("Exercise A: remove this annotation after implementing StackLinked.iterator().")
class StackLinkedIteratorTest {
    @Test
    void iteratesInLifoOrderAndPreservesEveryElement() {
        Stack<Integer> stack = new StackLinked<>();
        Examples.fillStack(stack);
        assertIterableEquals(List.of(30, 20, 10), stack);
        assertEquals(3, stack.size());
        assertEquals(30, stack.pop());
        assertEquals(20, stack.pop());
        assertEquals(10, stack.pop());
    }

    @Test
    void emptyAndExhaustedIteratorsThrowTheRequiredException() {
        Stack<Integer> stack = new StackLinked<>();
        Iterator<Integer> empty = stack.iterator();
        assertFalse(empty.hasNext());
        assertThrows(NoSuchElementException.class, empty::next);
        stack.push(7);
        Iterator<Integer> iterator = stack.iterator();
        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasNext());
        assertEquals(7, iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void iteratorsAreIndependentAndSupportDirectNext() {
        Stack<Integer> stack = new StackLinked<>();
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
    void nullElementsAreVisitedAndRemoveIsUnsupported() {
        Stack<Integer> stack = new StackLinked<>();
        stack.push(1);
        stack.push(null);
        assertIterableEquals(Arrays.asList(null, 1), stack);
        Iterator<Integer> iterator = stack.iterator();
        iterator.next();
        assertThrows(UnsupportedOperationException.class, iterator::remove);
    }
}
