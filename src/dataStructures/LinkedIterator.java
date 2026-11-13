package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Implementation of an Iterator for Linked List
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */

class LinkedIterator<E>  implements Iterator<E> {
    /**
     * First node of the list.
     */
    private final LinkedNode<E> first;
    /**
     * Node with the next element in the iteration.
     */
    LinkedNode<E> nextToReturn;

    /**
     * Time Complexity:
     * Best Case: O(1) - Direct pointer assignments in constant time without loops.
     * Worst Case: O(1) - Constant time initialization.
     * SinglyIterator constructor.
     *
     * @param first Node with the first element of the iteration
     */
    public LinkedIterator(LinkedNode<E> first) {
        this.first=first;
        nextToReturn=first;
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Direct null check on nextToReturn in constant time.
     * Worst Case: O(1) - Constant time reference comparison without traversing nodes.
     * Returns true if the iteration has more elements.
     *
     * @return true if there is a next element in the iteration, false otherwise
     */
    @Override
    public boolean hasNext() {
        return nextToReturn!= null;
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Retrieves the current element and advances the pointer to the next node in constant time.
     * Worst Case: O(1) - Direct pointer advancement in constant time without loops.
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public E next() throws NoSuchElementException {
        if(!hasNext()){
            throw new NoSuchElementException();
        }
        E elem = nextToReturn.getElement();
        nextToReturn = nextToReturn.getNext();
        return elem;
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Resets nextToReturn directly to the first node reference in constant time.
     * Worst Case: O(1) - Direct pointer assignment without traversing the list.
     * Restarts the iteration from the first node of the list.
     */
    @Override
    public void rewind() {
    nextToReturn = first;
    }

}
