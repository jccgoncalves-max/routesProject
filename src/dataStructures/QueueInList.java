package dataStructures;

import dataStructures.exceptions.EmptyQueueException;

public class QueueInList<E> implements Queue<E> {

    // Memory of the queue: a list.
    private final List<E> list;

    /**
     * Time Complexity:
     * Best Case: O(1) - Constant time initialization of the underlying singly linked list.
     * Worst Case: O(1) - Constant time initialization without loops.
     * Constructs an empty queue backed by a singly linked list.
     */
    public QueueInList( ){
        list = new SinglyLinkedList<>();
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Checks the list size directly in constant time without traversing elements.
     * Worst Case: O(1) - Direct integer comparison in constant time.
     * Returns true if the queue contains no elements.
     *
     * @return true if the queue is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Accesses the list size attribute directly in constant time.
     * Worst Case: O(1) - Direct attribute access without loops.
     * Returns the number of elements in the queue.
     *
     * @return number of elements currently in the queue
     */
    @Override
    public int size() {
        return list.size();
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Appends the element directly to the tail of the singly linked list in constant time.
     * Worst Case: O(1) - Direct pointer update at the tail without traversing the list.
     * Inserts the specified element at the rear of the queue.
     *
     * @param element element to be added to the queue
     */
    @Override
    public void enqueue(E element) {
        list.addLast(element);

    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Accesses the head element of the singly linked list directly in constant time.
     * Worst Case: O(1) - Direct pointer access to the first element without traversing nodes.
     * Returns, without removing, the element at the front of the queue.
     *
     * @return element at the front of the queue
     * @throws EmptyQueueException if the queue is empty
     */
    @Override
    public E peek()throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException();
        }
        return list.getFirst();
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Removes and returns the head element directly by updating pointer references in constant time.
     * Worst Case: O(1) - Direct pointer update at the head without traversing the list.
     * Removes and returns the element at the front of the queue.
     *
     * @return element removed from the front of the queue
     * @throws EmptyQueueException if the queue is empty
     */
    @Override
    public E dequeue()throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException();
        }
        return list.removeFirst();
    }


}
