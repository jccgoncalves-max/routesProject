package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

public class ArrayIterator<T> implements TwoWayIterator<T> {
	private static final int DIFF=2;

	private final T[] elems;
	private final int counter;
	private int currentN;
	private int currentP;
	
	public ArrayIterator(T[] elems, int counter) {
		this.elems = elems;
		this.counter = counter;
		rewind();
	}

	@Override
	public boolean hasPrevious() {
		return (currentP>=0);
	}

	@Override
	public T previous() throws NoSuchElementException {
		if(!hasPrevious())
			throw new NoSuchElementException();

		int currentE = currentP;
		currentN = currentE + 1;
		currentP = currentN-DIFF;
		return elems[currentE];
	}

	@Override
	public void fullForward() {
		this.currentN = counter;
		this.currentP = counter-1;
	}

	@Override
	public boolean hasNext() {
		return currentN<counter;
	}

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

	@Override
	public void rewind() {
		this.currentN = 0;
		this.currentP = -1;
	}
}
