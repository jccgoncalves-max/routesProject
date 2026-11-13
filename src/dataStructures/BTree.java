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

    @SuppressWarnings("unchecked")
    public BTree(){
      super();
    }

    /**
     * Returns the height of the tree.
     */
    public int getHeight() {
        if(isEmpty())
            return -1;
        return ((BTNode<E>)root).getHeight();
    }

    /**
     * Return the further left node of the tree
     * @return
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
     * Return the further right node of the tree
     * @return
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
