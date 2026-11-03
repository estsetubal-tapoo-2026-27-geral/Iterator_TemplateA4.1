package pt.unips.estsetubal.tapoo;

import java.util.Iterator;
import java.util.StringJoiner;

import pt.unips.estsetubal.tapoo.adt.Stack;
import pt.unips.estsetubal.tapoo.adt.StackArray;
import pt.unips.estsetubal.tapoo.adt.StackLinked;
import pt.unips.estsetubal.tapoo.adt.Tree;

/** Runs successfully before and after the iterator exercises. */
public class Main {
    public static void main(String[] args) {
        Stack<Integer> array = new StackArray<>();
        Examples.fillStack(array);

        System.out.println("=== StackArray: complete example ===");

        System.out.print("For-each: ");
        for (Integer value : array) {
            System.out.print(value + " ");
        }
        System.out.println();

        show("Explicit iterator", array.iterator());

        Iterator<Integer> first = array.iterator();
        Iterator<Integer> second = array.iterator();

        System.out.println("Independent iterators: "
                + first.next() + ", "
                + first.next() + ", "
                + second.next());

        System.out.println("Stack preserved: size="
                + array.size() + ", top=" + array.peek());

        // Exercise A: linked stack iterator.
        Stack<Integer> linked = new StackLinked<>();
        Examples.fillStack(linked);

        System.out.println("\n=== Exercise A: StackLinked ===");

        try {
            show("Top to bottom", linked.iterator());
        } catch (UnsupportedOperationException exception) {
            System.out.println("Top to bottom: [PENDING] "
                    + exception.getMessage());
        }

        // Exercise B: tree iterator in pre-order.
        Tree<String> tree = Examples.createTree();

        System.out.println("\n=== Exercise B: TreeLinked ===");
        System.out.print(tree);

        show("Existing elements() in breadth-first order",
                tree.elements().iterator());

        try {
            show("New iterator in pre-order", tree.iterator());
        } catch (UnsupportedOperationException exception) {
            System.out.println("New iterator in pre-order: [PENDING] "
                    + exception.getMessage());
        }

        // Additional exercise C: inverse stack iterators.
        System.out.println("\n=== Additional exercise C ===");

        try {
            show("StackArray bottom to top", array.iteratorInverse());
        } catch (UnsupportedOperationException exception) {
            System.out.println("StackArray bottom to top: [PENDING] "
                    + exception.getMessage());
        }

        try {
            show("StackLinked bottom to top", linked.iteratorInverse());
        } catch (UnsupportedOperationException exception) {
            System.out.println("StackLinked bottom to top: [PENDING] "
                    + exception.getMessage());
        }
    }

    private static <E> void show(String label, Iterator<E> iterator) {
        try {

            StringJoiner output = new StringJoiner(", ");
            while (iterator.hasNext()) {
                output.add(String.valueOf(iterator.next()));
            }
            System.out.println(label + ": " + output);
        } catch (UnsupportedOperationException exception) {
            System.out.println(label + ": [PENDING] " + exception.getMessage());
        }
    }
}
