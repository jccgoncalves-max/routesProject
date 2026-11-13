package dataStructures;

import dataStructures.exceptions.InvalidPositionException;
import dataStructures.exceptions.NoSuchElementException;

import java.io.Serial;
import java.io.Serializable;

public class ArrayClass<E> implements TwoWayList<E>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final int SIZE = 50;
    private static final  int GROWTH_FACTOR = 2 ;


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
        return new ArrayIterator<>(elems,counter);

    }

    @Override
    public boolean isEmpty() {
        return (counter == 0);

    }

    @Override
    public int size() {
        return counter;

    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayIterator<>(elems,counter);

    }

    @Override
    public E getFirst() throws NoSuchElementException {
        if(size()==0)
            throw new NoSuchElementException();
        return elems[0];
    }

    @Override
    public E getLast() throws NoSuchElementException {
        if(size()==0){
            throw new NoSuchElementException();
        }
        return elems[counter-1];
    }

    @Override
    public E get(int position) throws InvalidPositionException {
        if (position < 0 || position > size()-1) {
            throw new InvalidPositionException();
        }
        return elems[position];
    }

    @Override
    public int indexOf(E element) {
        for(int i = 0;i<size();i++){
            if(elems[i].equals(element))
                return i;
        }
        return NOT_FOUND;
    }

    @Override
    public void addFirst(E element) {
        if(isFull())
            resize();

        for(int i = size(); i>0;i--){
            elems[i] = elems[i-1];
        }
        elems[0] = element;
        counter++;
    }

    @Override
    public void addLast(E element) {
        if(isFull())
            resize();
        elems[counter++] = element;
    }

    @Override
    public void add(int position, E element) throws InvalidPositionException {
        if(position<0 || position>size()){
            throw new InvalidPositionException();
        }

        if(position==0) {
            addFirst(element);

        }else if (position==size()) {
            addLast(element);

        }else {
            if(isFull())
                resize();

            for (int i = size()-1; i>=position; i--) {
                elems[i+1] = elems[i];
            }
            elems[position] = element;
            counter++;
        }
    }

    @Override
    public E removeFirst() throws NoSuchElementException {
        if(size()==0)
            throw new NoSuchElementException();

        E removedE = elems[0];
        for(int i = 1; i<size();i++){
            elems[i-1] = elems[i];
        }
        elems[size()-1] = null;
        counter--;
        return removedE;
    }

    @Override
    public E removeLast() throws NoSuchElementException {
        if(size() == 0)
            throw new NoSuchElementException();

        E removedE = elems[size()-1];
        elems[size()-1] = null;
        counter--;
        return removedE;
    }

    @Override
    public E remove(int position) throws InvalidPositionException {
        E removedE = null;

        if (position < 0 || position > size()-1)
            throw new InvalidPositionException();

        if(position == 0) {
            return removeFirst();
        }

        else if(position == size()-1){
            return removeLast() ;
        }
        else {
            removedE = elems[position];
            for (int i = position; i < size()-1; i++) {
                elems[i] = elems[i + 1];
            }
            elems[size()-1] = null;
            counter--;
        }
        return removedE;
    }

    @SuppressWarnings("unchecked")
    private boolean isFull(){
        return (elems.length==size());
    }
    @SuppressWarnings("unchecked")
    private void resize(){
        E[] tmp;
        tmp = (E[]) new Object[elems.length * GROWTH_FACTOR];
        for(int i = 0; i<counter;i++)
            tmp[i] = elems[i];
        elems = tmp;
    }
}
