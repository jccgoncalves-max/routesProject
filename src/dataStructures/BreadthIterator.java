package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Breadth First Search Binary Tree iterator
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */

/**

 *
 */

class BreadthIterator<E> implements Iterator<E> {

    private Queue<BTNode<E>> queue;
    private BTNode<E> root;

    /**
     * Time Complexity:
     * Best Case: O(1) - Initializes the underlying queue and enqueues the root node directly in constant time without loops.
     * Worst Case: O(1) - Constant time queue initialization and single enqueue operation.
     * Constructs a breadth-first iterator starting at the specified root node.
     *
     * @param root root node of the binary tree to traverse
     */
    public BreadthIterator(BTNode<E> root) {
        this.root = root;
        rewind();
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Checks whether the queue is empty in constant time without traversing elements.
     * Worst Case: O(1) - Direct empty check on the underlying queue in constant time.
     * Returns true if there are more nodes to visit in the breadth-first traversal.
     *
     * @return true if the iteration has more elements, false otherwise
     */
    @Override
    public boolean hasNext() {
        return !queue.isEmpty();
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Dequeues the front node from the linked list queue in constant time without loops.
     * Worst Case: O(1) - Dequeues one node and enqueues at most two child nodes, each taking constant time.
     * Returns the next element in level-order traversal and enqueues its non-null children.
     *
     * @return next element in the breadth-first traversal
     * @throws NoSuchElementException if there are no more elements in the iteration
     */
    @Override
    public E next() throws NoSuchElementException {

        if (!hasNext()){
            throw new NoSuchElementException();
        }

        BTNode<E> current = queue.dequeue();


        if (current.getLeftChild() != null){
            queue.enqueue((BTNode<E>) current.getLeftChild());
        }
        if (current.getRightChild() != null){
            queue.enqueue((BTNode<E>) current.getRightChild());
        }

        return current.getElement();
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Instantiates a new empty queue and enqueues the root node in constant time without loops.
     * Worst Case: O(1) - Constant time queue creation and single enqueue operation.
     * Restarts the breadth-first traversal from the root node.
     */

    @Override
    public void rewind() {
        this.queue = new QueueInList<>();

        if(root != null)
          queue.enqueue(root);


    }

}
