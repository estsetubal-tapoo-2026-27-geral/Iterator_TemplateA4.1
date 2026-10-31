package pt.unips.estsetubal.tapoo;

import java.util.Iterator;
import java.util.StringJoiner;
import java.util.function.Supplier;
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
        show("Explicit iterator", array::iterator);
        Iterator<Integer> first = array.iterator();
        Iterator<Integer> second = array.iterator();
        System.out.println("Independent iterators: " + first.next() + ", "
                + first.next() + ", " + second.next());
        System.out.println("Stack preserved: size=" + array.size() + ", top=" + array.peek());

        Stack<Integer> linked = new StackLinked<>();
        Examples.fillStack(linked);
        System.out.println("\n=== Exercise A: StackLinked ===");
        show("Top to bottom", linked::iterator);

        Tree<String> tree = Examples.createTree();
        System.out.println("\n=== Exercise B: TreeLinked ===");
        System.out.print(tree);
        show("Existing elements() in breadth-first order", () -> tree.elements().iterator());
        show("New iterator in pre-order", tree::iterator);

        System.out.println("\n=== Additional exercise C ===");
        show("StackArray bottom to top", array::iteratorInverse);
        show("StackLinked bottom to top", linked::iteratorInverse);
    }

    private static <E> void show(String label, Supplier<Iterator<E>> factory) {
        try {
            Iterator<E> iterator = factory.get();
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
