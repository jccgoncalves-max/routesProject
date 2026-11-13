package dataStructures;

import java.io.Serial;
import java.io.Serializable;

/**
 * Binary Tree Node
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */
class BTNode<E> implements InfoNode<E>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    // Element stored in the node.
    private E element;
    // (Pointer to) the father.
    private InfoNode<E> parent;
    // (Pointer to) the left child.
    private InfoNode<E> leftChild;
    // (Pointer to) the right child.
    private InfoNode<E> rightChild;

    /**
     * Time Complexity:
     * Best Case: O(1) - Direct field assignments in constant time without loops.
     * Worst Case: O(1) - Constant time initialization.
     * Constructor of a leaf binary tree node with no parent or children.
     *
     * @param elem element to be stored in the node
     */
    @SuppressWarnings("unchecked")
    BTNode(E elem){
        this(elem,null,null,null);
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Direct field assignments in constant time without loops.
     * Worst Case: O(1) - Constant time initialization.
     * Constructor of a binary tree node with a specified parent and no children.
     *
     * @param elem element to be stored in the node
     * @param parent parent node reference
     */
    @SuppressWarnings("unchecked")
    BTNode(E elem, BTNode<E> parent) {
        this(elem,parent,null,null);
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Direct assignment of element and node references in constant time.
     * Worst Case: O(1) - Constant time initialization without loops.
     * Constructor of a binary tree node with specified parent, left child, and right child.
     *
     * @param elem element to be stored in the node
     * @param parent parent node reference
     * @param leftChild left child node reference
     * @param rightChild right child node reference
     */

    @SuppressWarnings("unchecked")
    BTNode(E elem, BTNode<E> parent, BTNode<E> leftChild, BTNode<E> rightChild){
        element=elem;
        this.parent=parent;
        this.leftChild=leftChild;
        this.rightChild=rightChild;
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Direct attribute access in constant time.
     * Worst Case: O(1) - Direct attribute access without loops.
     * Returns the element of the node.
     *
     * @return element stored in the node
     */
    public E getElement() {
        return element;
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Direct attribute access in constant time.
     * Worst Case: O(1) - Direct attribute access without loops.
     * Returns the left child of the node.
     *
     * @return left child node, or null if none exists
     */
    InfoNode<E> getLeftChild(){
        return leftChild;
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Direct attribute access in constant time.
     * Worst Case: O(1) - Direct attribute access without loops.
     * Returns the right child of the node.
     *
     * @return right child node, or null if none exists
     */
    InfoNode<E> getRightChild(){
        return rightChild;
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Direct attribute access in constant time.
     * Worst Case: O(1) - Direct attribute access without loops.
     * Returns the parent of the node.
     *
     * @return parent node, or null if this node is the root
     */
    InfoNode<E> getParent(){
        return parent;
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Direct attribute assignment in constant time.
     * Worst Case: O(1) - Direct attribute assignment without loops.
     * Updates the element stored in the node.
     *
     * @param elem new element to store in the node
     */
    public void setElement(E elem) {
        element=elem;
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Direct pointer assignment in constant time.
     * Worst Case: O(1) - Direct pointer assignment without loops.
     * Updates the left child of the node.
     *
     * @param node new left child node
     */
    void setLeftChild(InfoNode<E> node) {
        leftChild=node;
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Direct pointer assignment in constant time.
     * Worst Case: O(1) - Direct pointer assignment without loops.
     * Updates the right child of the node.
     *
     * @param node new right child node
     */
    void setRightChild(InfoNode<E> node) {
        rightChild=node;
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Direct pointer assignment in constant time.
     * Worst Case: O(1) - Direct pointer assignment without loops.
     * Updates the parent of the node.
     *
     * @param node new parent node
     */
    void setParent(InfoNode<E> node) {
        parent=node;
    }


    /**
     * Time Complexity:
     * Best Case: O(n) - Recursively visits all n nodes in the subtree rooted at this node to compute left and right heights.
     * Worst Case: O(n) - Recursively traverses every node in the subtree rooted at this node.
     * Returns the height of the subtree rooted at this node.
     *
     * @return height of the subtree rooted at this node
     */

    int getHeight() {

        int leftHeight = -1;
        int rightHeight = -1;

        if (leftChild != null){
            leftHeight = ((BTNode<E>) leftChild).getHeight();
        }
        if (rightChild != null){
            rightHeight = ((BTNode<E>) rightChild).getHeight();
        }

        return 1+ Math.max(leftHeight,rightHeight);
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - This node has no left child, so the while loop executes 0 times and returns this node directly.
     * Worst Case: O(h) - Traverses left child pointers down to the leftmost leaf, visiting h nodes (O(n) in a left-skewed subtree), where h is the height of the subtree.
     * Returns the leftmost node in the subtree rooted at this node.
     *
     * @return leftmost node in the subtree
     */
    BTNode<E> furtherLeftElement() {
        BTNode<E> current = this;
        while(current.getLeftChild() != null){
            current = (BTNode<E>) current.getLeftChild();
        }
        return current;
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - This node has no right child, so the while loop executes 0 times and returns this node directly.
     * Worst Case: O(h) - Traverses right child pointers down to the rightmost leaf, visiting h nodes (O(n) in a right-skewed subtree), where h is the height of the subtree.
     * Returns the rightmost node in the subtree rooted at this node.
     *
     * @return rightmost node in the subtree
     */
    BTNode<E> furtherRightElement() {
        BTNode<E> current = this;
        while(current.getRightChild() != null){
            current = (BTNode<E>) current.getRightChild();
        }
        return current;
    }

}
