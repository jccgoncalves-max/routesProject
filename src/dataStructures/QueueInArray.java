package dataStructures;

import dataStructures.exceptions.EmptyQueueException;
import dataStructures.exceptions.FullQueueException;

class QueueInArray<E> implements Queue<E> {
    // Default capacity of the stack.
    static final int DEFAULTCAPACITY = 1000;
    private final List<E> elements;
    private final int capacity;

    public QueueInArray( ){
        elements = new ArrayClass<>(DEFAULTCAPACITY);
        capacity=DEFAULTCAPACITY;
    }

    public QueueInArray(int capacity){
        elements = new ArrayClass<>(capacity);
        this.capacity=capacity;
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
