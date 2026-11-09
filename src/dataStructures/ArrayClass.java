package dataStructures;

import dataStructures.exceptions.InvalidPositionException;
import dataStructures.exceptions.NoSuchElementException;

public class ArrayClass<E> implements TwoWayList<E> {
    private static final int SIZE = 50;

    /**
     * Array of elements E.
     */
    private E[] elems;

    /**
     * Number of elements on an array.
     */
    private int counter;

    /**
     *
     */
    @SuppressWarnings("unchecked")
    public ArrayClass() {
        elems = (E[]) new Object[SIZE];
        counter = 0;
    }

    /**
     *
     * @param dimension
     */
    @SuppressWarnings("unchecked")
    public ArrayClass(int dimension) {
        elems = (E[]) new Object[dimension];
        counter = 0;
    }

    @Override
    public TwoWayIterator<E> twoWayiterator() {
        return null;
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
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public E getFirst() throws NoSuchElementException {
        return null;
    }

    @Override
    public E getLast() throws NoSuchElementException {
        return null;
    }

    @Override
    public E get(int position) throws InvalidPositionException {
        return null;
    }

    @Override
    public int indexOf(E element) {
        return 0;
    }

    @Override
    public void addFirst(E element) {

    }

    @Override
    public void addLast(E element) {

    }

    @Override
    public void add(int position, E element) throws InvalidPositionException {

    }

    @Override
    public E removeFirst() throws NoSuchElementException {
        return null;
    }

    @Override
    public E removeLast() throws NoSuchElementException {
        return null;
    }

    @Override
    public E remove(int position) throws InvalidPositionException {
        return null;
    }
    //TODO: Left as an exercise.
}
