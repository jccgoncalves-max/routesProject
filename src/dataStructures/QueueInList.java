package dataStructures;

import dataStructures.exceptions.EmptyQueueException;

public class QueueInList<E> implements Queue<E> {

    // Memory of the queue: a list.
    private final List<E> list;

    public QueueInList( ){
        list = new SinglyLinkedList<>();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void enqueue(E element) {

    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public E dequeue() {
        return null;
    }

    //TODO: Left as an exercise.
}
