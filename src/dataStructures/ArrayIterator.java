package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

public class ArrayIterator<T> implements TwoWayIterator<T> {
	private static final int DIFF=2;

	private final T[] elems;
	private final int counter;
	private int currentN;
	private int currentP;
    /**
     * Time Complexity:
     * Best Case: O(1) - Direct field assignments and index initialization in constant time without loops.
     * Worst Case: O(1) - Constant time initialization.
     * Constructs a two-way iterator for the given array up to the specified element count.
     *
     * @param elems array containing the elements to iterate over
     * @param counter number of valid elements in the array
     */
	public ArrayIterator(T[] elems, int counter) {
		this.elems = elems;
		this.counter = counter;
		rewind();
	}
    /**
     * Time Complexity:
     * Best Case: O(1) - Single integer comparison in constant time without loops.
     * Worst Case: O(1) - Direct comparison in constant time.
     * Returns true if there is a previous element in the iteration.
     *
     * @return true if there is a previous element, false otherwise
     */
	@Override
	public boolean hasPrevious() {
		return (currentP>=0);
	}
    /**
     * Time Complexity:
     * Best Case: O(1) - Direct array access by index and arithmetic updates in constant time without loops.
     * Worst Case: O(1) - Constant time array indexing and pointer update.
     * Returns the previous element in the iteration and moves the cursor backwards.
     *
     * @return previous element in the iteration
     * @throws NoSuchElementException if there is no previous element
     */
	@Override
	public T previous() throws NoSuchElementException {
		if(!hasPrevious())
			throw new NoSuchElementException();

		int currentE = currentP;
		currentN = currentE + 1;
		currentP = currentN-DIFF;
		return elems[currentE];
	}
    /**
     * Time Complexity:
     * Best Case: O(1) - Direct integer assignments to position the cursor at the end in constant time.
     * Worst Case: O(1) - Constant time index assignment without traversing the array.
     * Positions the iterator after the last element to allow reverse traversal.
     */
	@Override
	public void fullForward() {
		this.currentN = counter;
		this.currentP = counter-1;
	}
    /**
     * Time Complexity:
     * Best Case: O(1) - Single integer comparison in constant time without loops.
     * Worst Case: O(1) - Direct comparison in constant time.
     * Returns true if there is a next element in the iteration.
     *
     * @return true if there is a next element, false otherwise
     */
	@Override
	public boolean hasNext() {
		return currentN<counter;
	}
    /**
     * Time Complexity:
     * Best Case: O(1) - Direct array access by index and arithmetic updates in constant time without loops.
     * Worst Case: O(1) - Constant time array indexing and pointer update.
     * Returns the next element in the iteration and advances the cursor forward.
     *
     * @return next element in the iteration
     * @throws NoSuchElementException if there is no next element
     */
	@Override
	public T next() throws NoSuchElementException {
		if(!hasNext()) {
			throw new NoSuchElementException();
		}
		int currentE = currentN;
		currentP = currentE - 1;
		currentN = currentP + DIFF;
		return elems[currentE];
	}
    /**
     * Time Complexity:
     * Best Case: O(1) - Direct integer assignments to reset the cursor to the start in constant time.
     * Worst Case: O(1) - Constant time index assignment without traversing the array.
     * Restarts the iteration from the first position of the array.
     */
	@Override
	public void rewind() {
		this.currentN = 0;
		this.currentP = -1;
	}
}
