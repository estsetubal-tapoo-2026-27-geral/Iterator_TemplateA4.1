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

@Disabled("Exercise B: remove this annotation after implementing TreeLinked.iterator().")
class TreeLinkedIteratorTest {
    @Test
    void visitsPreOrderRatherThanBreadthFirstAndPreservesTheTree() {
        Tree<String> tree = Examples.createTree();
        assertIterableEquals(List.of("A", "B", "D", "E", "C", "F"), tree);
        assertEquals(6, tree.size());
        assertIterableEquals(List.of("A", "B", "C", "D", "E", "F"), tree.elements());
    }

    @Test
    void emptyAndSingleRootTreesFollowTheIteratorContract() {
        Tree<String> tree = new TreeLinked<>();
        Iterator<String> empty = tree.iterator();
        assertFalse(empty.hasNext());
        assertThrows(NoSuchElementException.class, empty::next);
        tree.insert(null, "A");
        Iterator<String> iterator = tree.iterator();
        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasNext());
        assertEquals("A", iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void respectsExplicitChildOrderAndRetainsDuplicateElements() {
        Tree<String> tree = new TreeLinked<>();
        Position<String> a = tree.insert(null, "A");
        Position<String> c = tree.insert(a, "C");
        Position<String> b = tree.insert(a, "B", 0);
        tree.insert(b, "X");
        tree.insert(c, "X");
        assertIterableEquals(List.of("A", "B", "X", "C", "X"), tree);
    }

    @Test
    void iteratorsAreIndependentAndCanBeRestarted() {
        Tree<String> tree = Examples.createTree();
        Iterator<String> first = tree.iterator();
        Iterator<String> second = tree.iterator();
        assertNotSame(first, second);
        assertEquals("A", first.next());
        assertEquals("B", first.next());
        assertEquals("A", second.next());
        assertIterableEquals(List.of("A", "B", "D", "E", "C", "F"), tree);
    }

    @Test
    void nullIsAValidElementAndRemoveIsUnsupported() {
        Tree<String> tree = new TreeLinked<>();
        Position<String> root = tree.insert(null, null);
        tree.insert(root, "B");
        assertIterableEquals(Arrays.asList(null, "B"), tree);
        Iterator<String> iterator = tree.iterator();
        iterator.next();
        assertThrows(UnsupportedOperationException.class, iterator::remove);
    }
}
