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


    public BreadthIterator(BTNode<E> root) {
        this.root = root;
        rewind();
    }
    @Override
    public boolean hasNext() {
        return !queue.isEmpty();
    }

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

    @Override
    public void rewind() {
        this.queue = new QueueInList<>();

        if(root != null)
          queue.enqueue(root);


    }

}
