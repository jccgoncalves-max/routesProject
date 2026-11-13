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
     * Time Complexity:
     * Best Case: O(1) - Allocates an array of fixed default capacity (50) and sets counter to 0 in constant time without loops.
     * Worst Case: O(1) - Constant time allocation of a fixed-size array.
     * Constructs an empty list backed by an array with the default capacity.
     */
    @SuppressWarnings("unchecked")
    public ArrayClass() {
        elems = (E[]) new Object[SIZE];
        counter = 0;
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Direct array allocation and counter initialization in constant time without loops.
     * Worst Case: O(1) - Constant time allocation and field assignment without explicit loops.
     * Constructs an empty list backed by an array with the specified initial dimension.
     *
     * @param dimension initial capacity of the underlying array
     */
    @SuppressWarnings("unchecked")
    public ArrayClass(int dimension) {
        elems = (E[]) new Object[dimension];
        counter = 0;
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Instantiates ArrayIterator and initializes cursor indices directly in constant time without loops.
     * Worst Case: O(1) - Constant time object creation without traversing the array.
     * Returns a two-way iterator of the elements in the list.
     *
     * @return two-way iterator over the elements in the list
     */
    @Override
    public TwoWayIterator<E> twoWayiterator() {
        return new ArrayIterator<>(elems,counter);

    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Direct integer comparison on counter in constant time.
     * Worst Case: O(1) - Direct comparison without loops.
     * Returns true if the list contains no elements.
     *
     * @return true if the list is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return (counter == 0);

    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Direct attribute access in constant time.
     * Worst Case: O(1) - Direct attribute access without loops.
     * Returns the number of elements in the list.
     *
     * @return number of elements currently in the list
     */
    @Override
    public int size() {
        return counter;

    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Instantiates ArrayIterator and initializes cursor indices directly in constant time without loops.
     * Worst Case: O(1) - Constant time object creation without traversing the array.
     * Returns an iterator of the elements in the list.
     *
     * @return iterator over the elements in the list
     */
    @Override
    public Iterator<E> iterator() {
        return new ArrayIterator<>(elems,counter);

    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Direct array access at index 0 in constant time without loops.
     * Worst Case: O(1) - Direct array indexing in constant time.
     * Returns the first element of the list.
     *
     * @return first element in the list
     * @throws NoSuchElementException if the list is empty
     */
    @Override
    public E getFirst() throws NoSuchElementException {
        if(size()==0)
            throw new NoSuchElementException();
        return elems[0];
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Direct array access at index counter - 1 in constant time without loops.
     * Worst Case: O(1) - Direct array indexing in constant time.
     * Returns the last element of the list.
     *
     * @return last element in the list
     * @throws NoSuchElementException if the list is empty
     */
    @Override
    public E getLast() throws NoSuchElementException {
        if(size()==0){
            throw new NoSuchElementException();
        }
        return elems[counter-1];
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Direct array access by index in constant time without loops.
     * Worst Case: O(1) - Direct array indexing in constant time.
     * Returns the element at the specified position in the list.
     *
     * @param position index of the element to return
     * @return element at the specified position
     * @throws InvalidPositionException if position is out of valid bounds
     */
    @Override
    public E get(int position) throws InvalidPositionException {
        if (position < 0 || position > size()-1) {
            throw new InvalidPositionException();
        }
        return elems[position];
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Finds the matching element at index 0 on the first iteration of the loop.
     * Worst Case: O(n) - Uses a loop that traverses all n elements of the array when the element is at the last position or not present.
     * Returns the index of the first occurrence of the specified element in the list,
     * or NOT_FOUND (-1) if the list does not contain the element.
     *
     * @param element element to search for
     * @return index of the element in the list, or NOT_FOUND if absent
     */
    @Override
    public int indexOf(E element) {
        for(int i = 0;i<size();i++){
            if(elems[i].equals(element))
                return i;
        }
        return NOT_FOUND;
    }
    /**
     * Time Complexity:
     * Best Case: O(n) - Uses a loop to shift all n existing elements one position to the right (O(1) only if the list is empty).
     * Worst Case: O(n) - Resizes the array by copying all n elements and shifts all n elements to the right using loops.
     * Inserts the specified element at the first position of the list.
     *
     * @param element element to be inserted at the front
     */
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

    /**
     * Time Complexity:
     * Best Case: O(1) - Array is not full, inserting the element directly at index counter in constant time without loops.
     * Worst Case: O(n) - Array is full, requiring resize to copy all n elements in a loop before inserting at the end.
     * Appends the specified element to the end of the list.
     *
     * @param element element to be appended
     */
    @Override
    public void addLast(E element) {
        if(isFull())
            resize();
        elems[counter++] = element;
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Position is size() and the array is not full, inserting directly at the end via addLast without shifting elements.
     * Worst Case: O(n) - Position is 0 (or array is full), requiring a loop to shift (and possibly resize/copy) all n elements.
     * Inserts the specified element at the specified position in the list.
     *
     * @param position index where the element should be inserted
     * @param element element to be inserted
     * @throws InvalidPositionException if position is out of valid bounds
     */
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
    /**
     * Time Complexity:
     * Best Case: O(n) - Uses a loop to shift all n - 1 remaining elements one position to the left (O(1) only if size() == 1).
     * Worst Case: O(n) - Uses a loop to shift all n - 1 remaining elements one position to the left.
     * Removes and returns the first element of the list.
     *
     * @return element removed from the first position
     * @throws NoSuchElementException if the list is empty
     */
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
    /**
     * Time Complexity:
     * Best Case: O(1) - Direct array access at index size() - 1 in constant time without shifting elements.
     * Worst Case: O(1) - Direct array access and null assignment in constant time without loops.
     * Removes and returns the last element of the list.
     *
     * @return element removed from the last position
     * @throws NoSuchElementException if the list is empty
     */
    @Override
    public E removeLast() throws NoSuchElementException {
        if(size() == 0)
            throw new NoSuchElementException();

        E removedE = elems[size()-1];
        elems[size()-1] = null;
        counter--;
        return removedE;
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Position is size() - 1, delegating directly to removeLast in constant time without shifting elements.
     * Worst Case: O(n) - Position is 0, delegating to removeFirst which uses a loop to shift all n - 1 remaining elements to the left.
     * Removes and returns the element at the specified position in the list.
     *
     * @param position index of the element to be removed
     * @return element removed from the specified position
     * @throws InvalidPositionException if position is out of valid bounds
     */
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
    /**
     * Time Complexity:
     * Best Case: O(1) - Direct integer comparison between array length and size in constant time.
     * Worst Case: O(1) - Direct comparison without loops.
     * Checks whether the underlying array has reached its full capacity.
     *
     * @return true if the array is full, false otherwise
     */
    @SuppressWarnings("unchecked")
    private boolean isFull(){
        return (elems.length==size());
    }

    /**
     * Time Complexity:
     * Best Case: O(n) - Uses a loop that iterates n times to copy all n elements into the larger array.
     * Worst Case: O(n) - Uses a loop that traverses and copies all n elements into the resized array.
     * Doubles the capacity of the underlying array and copies all existing elements over.
     */
    @SuppressWarnings("unchecked")
    private void resize(){
        E[] tmp;
        tmp = (E[]) new Object[elems.length * GROWTH_FACTOR];
        for(int i = 0; i<counter;i++)
            tmp[i] = elems[i];
        elems = tmp;
    }
}
