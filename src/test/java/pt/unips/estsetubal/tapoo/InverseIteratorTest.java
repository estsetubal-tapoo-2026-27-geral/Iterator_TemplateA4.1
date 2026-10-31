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

@Disabled("Additional exercise C: remove after implementing iteratorInverse() in both stacks.")
class InverseIteratorTest {
    @Test
    void bothImplementationsVisitBottomToTopWithoutChangingDefaultOrder() {
        checkOrder(new StackArray<>());
        checkOrder(new StackLinked<>());
    }

    private void checkOrder(Stack<Integer> stack) {
        Examples.fillStack(stack);
        assertEquals(List.of(10, 20, 30), collect(stack.iteratorInverse()));
        assertIterableEquals(List.of(30, 20, 10), stack);
        assertEquals(3, stack.size());
        assertEquals(30, stack.peek());
    }

    @Test
    void emptyAndExhaustedIteratorsFollowTheContract() {
        checkBounds(new StackArray<>());
        checkBounds(new StackLinked<>());
    }

    private void checkBounds(Stack<Integer> stack) {
        Iterator<Integer> empty = stack.iteratorInverse();
        assertFalse(empty.hasNext());
        assertThrows(NoSuchElementException.class, empty::next);
        stack.push(1);
        Iterator<Integer> iterator = stack.iteratorInverse();
        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
        assertThrows(UnsupportedOperationException.class, iterator::remove);
    }

    @Test
    void independentIteratorsPreserveNullsAndDuplicates() {
        checkIndependence(new StackArray<>());
        checkIndependence(new StackLinked<>());
    }

    private void checkIndependence(Stack<Integer> stack) {
        stack.push(1);
        stack.push(null);
        stack.push(1);
        Iterator<Integer> first = stack.iteratorInverse();
        Iterator<Integer> second = stack.iteratorInverse();
        assertNotSame(first, second);
        assertEquals(1, first.next());
        assertNull(first.next());
        assertEquals(1, second.next());
        assertEquals(Arrays.asList(1, null, 1), collect(stack.iteratorInverse()));
    }

    private <E> List<E> collect(Iterator<E> iterator) {
        List<E> values = new ArrayList<>();
        while (iterator.hasNext()) {
            values.add(iterator.next());
        }
        return values;
    }
}
