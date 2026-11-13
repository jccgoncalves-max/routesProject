package dataStructures;

import java.io.Serial;

/**
 * Binary Tree
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */
abstract class BTree<E> extends LinkedTree<E> {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * Time Complexity:
     * Best Case: O(1) - Constant time initialization of tree attributes without loops.
     * Worst Case: O(1) - Constant time initialization.
     * Constructs an empty binary tree.
     */
    @SuppressWarnings("unchecked")
    public BTree(){
      super();
    }

    /**
     * Time Complexity:
     * Best Case: O(n) - Recursively traverses all n nodes in both left and right subtrees to compute the height.
     * Worst Case: O(n) - Must visit every node in the tree to determine the deepest path from the root.
     * Returns the height of the tree.
     *
     * @return height of the tree, or -1 if the tree is empty
     */
    public int getHeight() {
        if(isEmpty())
            return -1;
        return ((BTNode<E>)root).getHeight();
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Root has no left child, so the while loop executes 0 times and returns root directly.
     * Worst Case: O(n) - Traverses left child pointers down to the leftmost leaf, visiting n nodes
     * Returns the further left node of the tree.
     *
     * @return leftmost node of the tree, or null if the tree is empty
     */
    BTNode<E> furtherLeftElement() {
        if( root == null)
            return null;

        BTNode<E> current = (BTNode<E>) root;

        while (current.getLeftChild() != null){
            current = (BTNode<E>) current.getLeftChild();
        }

        return current;

    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Root has no right child, so the while loop executes 0 times and returns root directly.
     * Worst Case: O(n) - Traverses right child pointers down to the rightmost leaf, visiting n nodes
     * Returns the further right node of the tree.
     *
     * @return rightmost node of the tree, or null if the tree is empty
     */
    BTNode<E> furtherRightElement() {
        if (root == null)
            return null;

        BTNode<E> current = (BTNode<E>) root;

        while (current.getRightChild() != null){
            current = (BTNode<E>) current.getRightChild();
        }

        return  current;

    }

}
