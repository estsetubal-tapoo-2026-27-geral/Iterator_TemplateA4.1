package pt.unips.estsetubal.tapoo.adt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Linked implementation of the general tree studied in A2.
 * All tree operations are complete. Only iterator() is left as an exercise.
 */
public class TreeLinked<E> implements Tree<E> {
    private TreeNode root;

    public TreeLinked() {
    }

    public TreeLinked(E rootElement) {
        root = new TreeNode(rootElement, null);
    }

    @Override
    public int size() {
        return countNodes(root);
    }

    private int countNodes(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int count = 1;
        for (TreeNode child : node.children) {
            count += countNodes(child);
        }
        return count;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public Position<E> root() {
        if (isEmpty()) {
            throw new EmptyTreeException();
        }
        return root;
    }

    @Override
    public Position<E> parent(Position<E> position) {
        TreeNode node = checkPosition(position);
        if (node == root) {
            throw new BoundaryViolationException("The root has no parent.");
        }
        return node.parent;
    }

    @Override
    public Iterable<Position<E>> children(Position<E> position) {
        return new ArrayList<Position<E>>(checkPosition(position).children);
    }

    @Override
    public boolean isInternal(Position<E> position) {
        return !checkPosition(position).children.isEmpty();
    }

    @Override
    public boolean isExternal(Position<E> position) {
        return checkPosition(position).children.isEmpty();
    }

    @Override
    public boolean isRoot(Position<E> position) {
        return checkPosition(position) == root;
    }

    @Override
    public Position<E> insert(Position<E> parent, E element) {
        if (isEmpty()) {
            return insertRoot(parent, element, 0);
        }
        TreeNode parentNode = checkPosition(parent);
        TreeNode node = new TreeNode(element, parentNode);
        parentNode.children.add(node);
        return node;
    }

    @Override
    public Position<E> insert(Position<E> parent, E element, int order) {
        if (isEmpty()) {
            return insertRoot(parent, element, order);
        }
        TreeNode parentNode = checkPosition(parent);
        if (order < 0 || order > parentNode.children.size()) {
            throw new BoundaryViolationException("Invalid child index.");
        }
        TreeNode node = new TreeNode(element, parentNode);
        parentNode.children.add(order, node);
        return node;
    }

    private Position<E> insertRoot(Position<E> parent, E element, int order) {
        if (parent != null) {
            throw new InvalidPositionException("An empty tree requires a null parent.");
        }
        if (order != 0) {
            throw new BoundaryViolationException("The root requires index zero.");
        }
        root = new TreeNode(element, null);
        return root;
    }

    @Override
    public E replace(Position<E> position, E element) {
        TreeNode node = checkPosition(position);
        E previous = node.element;
        node.element = element;
        return previous;
    }

    @Override
    public E remove(Position<E> position) {
        TreeNode node = checkPosition(position);
        if (!node.children.isEmpty()) {
            throw new IllegalStateException("Only leaves can be removed.");
        }
        E previous = node.element;
        if (node == root) {
            root = null;
        } else {
            node.parent.children.remove(node);
        }
        node.parent = null;
        node.valid = false;
        return previous;
    }

    /** Returns an independent list of positions, in breadth-first order. */
    @Override
    public Iterable<Position<E>> positions() {
        List<Position<E>> result = new ArrayList<>();
        if (isEmpty()) {
            return result;
        }
        Queue<TreeNode> pending = new LinkedList<>();
        pending.offer(root);
        while (!pending.isEmpty()) {
            TreeNode node = pending.poll();
            result.add(node);
            for (TreeNode child : node.children) {
                pending.offer(child);
            }
        }
        return result;
    }

    /** Returns an independent list of elements, in breadth-first order. */
    @Override
    public Iterable<E> elements() {
        List<E> result = new ArrayList<>();
        for (Position<E> position : positions()) {
            result.add(position.element());
        }
        return result;
    }

    @Override
    public Iterator<E> iterator() {
        // TODO A4.1-B: create a fresh pre-order iterator.
        // Respect the stored order of children and handle an empty tree.
        // Do not return elements().iterator(): its order here is breadth-first.
        throw new UnsupportedOperationException("TreeLinked iterator not implemented yet.");
    }

    @SuppressWarnings("unchecked")
    private TreeNode checkPosition(Position<E> position) {
        if (position == null || position.getClass() != TreeNode.class) {
            throw new InvalidPositionException("Invalid position type.");
        }
        TreeNode node = (TreeNode) position;
        if (node.owner != this || !node.valid) {
            throw new InvalidPositionException("Foreign or removed position.");
        }
        return node;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (!isEmpty()) {
            appendTree(root, 0, result);
        }
        return result.toString();
    }

    private void appendTree(TreeNode node, int level, StringBuilder result) {
        result.append("  ".repeat(level)).append(node.element)
                .append(System.lineSeparator());
        for (TreeNode child : node.children) {
            appendTree(child, level + 1, result);
        }
    }

    private class TreeNode implements Position<E> {
        private E element;
        private TreeNode parent;
        private final List<TreeNode> children = new ArrayList<>();
        private final TreeLinked<E> owner = TreeLinked.this;
        private boolean valid = true;

        private TreeNode(E element, TreeNode parent) {
            this.element = element;
            this.parent = parent;
        }

        @Override
        public E element() {
            if (!valid) {
                throw new InvalidPositionException("This position has been removed.");
            }
            return element;
        }
    }
}
