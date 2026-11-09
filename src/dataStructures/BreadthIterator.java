package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Breadth First Search Binary Tree iterator
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */

class BreadthIterator<E> implements Iterator<E> {
    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public E next() throws NoSuchElementException {
        return null;
    }

    @Override
    public void rewind() {

    }
    //TODO: Left as an exercise.
}
