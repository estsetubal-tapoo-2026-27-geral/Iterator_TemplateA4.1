package pt.unips.estsetubal.tapoo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pt.unips.estsetubal.tapoo.adt.*;

class BaseOperationsTest {
    @Test
    void bothStacksImplementLifoAndDetectEmptyAccess() {
        checkStack(new StackArray<>());
        checkStack(new StackLinked<>());
    }

    private void checkStack(Stack<String> stack) {
        assertTrue(stack.isEmpty());
        assertThrows(EmptyStackException.class, stack::pop);
        assertThrows(EmptyStackException.class, stack::peek);
        stack.push("A");
        stack.push("B");
        assertEquals(2, stack.size());
        assertEquals("B", stack.peek());
        assertEquals("B", stack.pop());
        assertEquals("A", stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void arrayCapacityIsEnforced() {
        assertThrows(IllegalArgumentException.class, () -> new StackArray<>(0));
        Stack<Integer> stack = new StackArray<>(1);
        stack.push(3);
        assertThrows(FullStackException.class, () -> stack.push(4));
        assertEquals(3, stack.pop());
        stack.push(5);
        assertEquals(5, stack.peek());
    }

    @Test
    void emptyTreeHasNoElementsOrPositions() {
        Tree<String> tree = new TreeLinked<>();
        assertEquals(0, tree.size());
        assertTrue(tree.isEmpty());
        assertThrows(EmptyTreeException.class, tree::root);
        assertIterableEquals(List.of(), tree.positions());
        assertIterableEquals(List.of(), tree.elements());
    }

    @Test
    void insertionPreservesChildOrderAndClassification() {
        Tree<String> tree = new TreeLinked<>();
        Position<String> root = tree.insert(null, "A");
        Position<String> c = tree.insert(root, "C");
        Position<String> b = tree.insert(root, "B", 0);
        Position<String> d = tree.insert(b, "D");
        assertEquals(4, tree.size());
        assertIterableEquals(List.of(b, c), tree.children(root));
        assertSame(root, tree.parent(b));
        assertTrue(tree.isRoot(root));
        assertTrue(tree.isInternal(root));
        assertTrue(tree.isExternal(d));
        assertThrows(BoundaryViolationException.class, () -> tree.parent(root));
        assertThrows(BoundaryViolationException.class, () -> tree.insert(root, "X", 3));
    }

    @Test
    void onlyLeavesCanBeRemovedAndTheirPositionsBecomeInvalid() {
        Tree<String> tree = new TreeLinked<>();
        Position<String> root = tree.insert(null, "A");
        Position<String> leaf = tree.insert(root, "B");
        assertThrows(IllegalStateException.class, () -> tree.remove(root));
        assertEquals("B", tree.remove(leaf));
        assertThrows(InvalidPositionException.class, leaf::element);
        assertThrows(InvalidPositionException.class, () -> tree.remove(leaf));
        assertEquals(1, tree.size());
        assertEquals("A", tree.remove(root));
        assertTrue(tree.isEmpty());
        assertThrows(InvalidPositionException.class, root::element);
    }

    @Test
    void invalidForeignAndFabricatedPositionsAreRejected() {
        Tree<String> first = new TreeLinked<>("A");
        Tree<String> second = new TreeLinked<>("B");
        assertThrows(InvalidPositionException.class, () -> first.isRoot(null));
        assertThrows(InvalidPositionException.class, () -> first.children(second.root()));
        Position<String> fabricated = () -> "X";
        assertThrows(InvalidPositionException.class, () -> first.parent(fabricated));
        assertThrows(InvalidPositionException.class, () -> first.insert(second.root(), "X"));
    }

    @Test
    void replacingAnElementKeepsThePosition() {
        Tree<String> tree = new TreeLinked<>("A");
        Position<String> root = tree.root();
        assertEquals("A", tree.replace(root, "Z"));
        assertEquals("Z", root.element());
        assertSame(root, tree.root());
    }

    @Test
    void elementsAndPositionsUseBreadthFirstOrderInThisImplementation() {
        Tree<String> tree = Examples.createTree();
        assertIterableEquals(List.of("A", "B", "C", "D", "E", "F"), tree.elements());
        List<String> fromPositions = new ArrayList<>();
        for (Position<String> position : tree.positions()) {
            fromPositions.add(position.element());
        }
        assertEquals(List.of("A", "B", "C", "D", "E", "F"), fromPositions);
    }
}
