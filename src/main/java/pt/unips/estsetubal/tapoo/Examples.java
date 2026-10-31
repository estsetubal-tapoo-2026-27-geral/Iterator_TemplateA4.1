package pt.unips.estsetubal.tapoo;

import pt.unips.estsetubal.tapoo.adt.Position;
import pt.unips.estsetubal.tapoo.adt.Stack;
import pt.unips.estsetubal.tapoo.adt.Tree;
import pt.unips.estsetubal.tapoo.adt.TreeLinked;

/** Small, repeatable examples shared by the demonstrations. */
public final class Examples {
    private Examples() {
    }

    public static void fillStack(Stack<Integer> stack) {
        stack.push(10);
        stack.push(20);
        stack.push(30);
    }

    public static Tree<String> createTree() {
        Tree<String> tree = new TreeLinked<>();
        Position<String> a = tree.insert(null, "A");
        Position<String> b = tree.insert(a, "B");
        Position<String> c = tree.insert(a, "C");
        tree.insert(b, "D");
        tree.insert(b, "E");
        tree.insert(c, "F");
        return tree;
    }
}
