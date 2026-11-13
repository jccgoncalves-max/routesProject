package dataStructures;

import dataStructures.exceptions.*;

/**
 *  Sequence Linked List
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
abstract class SequenceLinkedList<E> extends LinkedList<E> implements List<E>{
       /**
     * Time Complexity:
     * Best Case: O(1) - Constant time initialization of structure attributes.
     * Worst Case: O(1) - Constant time initialization.
     * Constructor of an empty singly linked list.
     * head and tail are initialized as null.
     * currentSize is initialized as 0.
     */
    public SequenceLinkedList(){
        super();
    }
    /**
     * Returns the first element of the list.
     * Time Complexity:
     * Best Case: O(1) - Accesses head directly.
     * Worst Case: O(1) - Direct pointer access to head.
     * @return first element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getFirst() {
        if ( this.isEmpty() )
            throw new NoSuchElementException();
        return head.getElement();
    }
    /**
     * Returns the last element of the list.
     * Time Complexity:
     * Best Case: O(1) - Accesses tail directly
     * Worst Case: O(1) - Direct pointer access to tail.
     * @return last element in the list
     * @throws NoSuchElementException - if size() == 0
     */

    public E getLast() {
        if ( this.isEmpty() )
            throw new NoSuchElementException();
        return tail.getElement();
    }
    /**
     * Returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, it corresponds to getFirst.
     * If the specified position is size()-1, get corresponds to getLast.
     *Time Complexity:
     * Best Case: O(1) - Position is 0 or size() - 1, accessing head or tail directly in constant time.
     * Worst Case: O(n) - Position is size() - 2, requiring to traverse n - 2 nodes.
     * @param position - position of the element to be returned
     * @return element at position
     * @throws InvalidPositionException if position is not valid in the list
     */

    public E get(int position) {
        if ( position < 0 || position >= currentSize )
            throw new InvalidPositionException();
        if (position == 0)
            return getFirst();
        if (position == currentSize-1)
            return getLast();
        return getNode(position).getElement();
    }

    /**
     * Return the node in the given position
     * Time Complexity:
     * Best Case: O(1) - Position is 0, so the loop executes 0 times and returns head directly.
     * Worst Case: O(n) - Position is near size() - 1, traversing n - 1 nodes
     * @param position position of the node in the list
     * @return node at the specified position
     */
    LinkedNode<E> getNode(int position) {
        LinkedNode<E> node = head;
        for ( int i = 0; i < position; i++)
            node = node.getNext();
        return node;
    }
    /**
     * Returns the position of the specified element in the list
     * if the list contains the element.
     * Otherwise, returns -1.
     * Time Complexity:
     * Best Case: O(1) - Finds the element at head on the first iteration of the loop.
     * Worst Case: O(n) - Traverses all n nodes of the list when the element is at tail or not present.
     * @param element - element to be searched in the list
     * @return position of the element in the list (or -1)
     */
    public int indexOf(E element) {
        LinkedNode<E> node = head;
        int index = 0;
        while (node != null){
            if (node.getElement().equals(element)) {
                return index;
            }
            node = node.getNext();
            index++;
        }

        return  NOT_FOUND;
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Inserts directly at head by updating node references in constant time.
     * Worst Case: O(1) - Direct pointer update at head without traversing the list.
     * Inserts the specified element at the first position in the list.
     *
     * @param element to be inserted
     */
    public abstract void addFirst(E element);

    /**
     * Time Complexity:
     * Best Case: O(1) - Inserts directly at tail by updating node references in constant time.
     * Worst Case: O(1) - Direct pointer update at tail without traversing the list.
     * Inserts the specified element at the last position in the list.
     *
     * @param element to be inserted
     */
    public abstract void addLast(E element) ;

     /**
     * Inserts the specified element at the specified position in the list.
     * Range of valid positions: 0, ..., size().
     * If the specified position is 0, add corresponds to addFirst.
     * If the specified position is size(), add corresponds to addLast.
     * Time Complexity:
     * Best Case: O(1) - Position is 0 or size()
     * Worst Case: O(n) - Position is size() - 1
     * @param position - position where to insert the element
     * @param element  - element to be inserted
     * @throws InvalidPositionException - if position is not valid in the list
     */

    public void add(int position, E element) {
        if ( position < 0 || position > currentSize )
            throw new InvalidPositionException();
        if ( position == 0 )
            addFirst(element);
        else if ( position == currentSize )
            addLast(element);
        else
            addMiddle(position, element);
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Inserts right after head (position == 1), accessing the previous node in constant time.
     * Worst Case: O(n) - Inserts right before tail (position == size() - 1), traversing n - 2 nodes from head.
     * Inserts the specified element at an intermediate position in the list.
     *
     * @param position position where to insert the element
     * @param element  element to be inserted
     */
    abstract void addMiddle(int position, E element);


    /**
     * Time Complexity:
     * Best Case: O(1) - Position is 1, so returns head directly without looping.
     * Worst Case: O(n) - Position is size() - 1, requiring to traverse n - 2 nodes from head.
     * Returns the previous node and the node at this position.
     *
     * @param position position of the target node
     * @return pair containing the previous node and the node at the given position
     */
    pairNode<E> getNodes(int position){
        LinkedNode<E> prevNode = this.getNode(position - 1);
        return new pairNode<>(prevNode,prevNode.getNext());
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Removes head directly by updating pointer references in constant time.
     * Worst Case: O(1) - Direct pointer update at head without traversing the list.
     * Removes and returns the element at the first position in the list.
     *
     * @return element removed from the first position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        return super.removeFirstNode();
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - The list has a single element (size() == 1)
     * Worst Case: O(n) - traversing n - 2 nodes from head to find the node before tail.
     * Removes and returns the element at the last position in the list.
     *
     * @return element removed from the last position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        if ( size() == 1 )
            return removeFirst();
        pairNode<E> pair= this.getNodes(size() - 1);
        return super.removeLastNode(pair);
    }
    /**
     * Removes and returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, remove corresponds to removeFirst.
     * If the specified position is size()-1, remove corresponds to removeLast.
     *Time Complexity:
     * Best Case: O(1) - Position is 0
     * Worst Case: O(n) - Position is size() - 1 or size() - 2, traversing n - 2 nodes from head to reach the previous node.
     * @param position - position of the element to be removed
     * @return element removed at position
     * @throws InvalidPositionException - if position is not valid in the list
     */
    @Override
    public E remove(int position) {
        if ( position < 0 || position >= currentSize )
            throw new InvalidPositionException();
        if ( position == 0 )
            return removeFirst();
        if ( position == currentSize - 1 )
            return removeLast();
        return removeMiddle(position);
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Position is 1, accessing head directly.
     * Worst Case: O(n) - Position is size() - 2, traversing n - 3 nodes
     * Removes and returns the element at an intermediate position in the list.
     *
     * @param position position of the element to be removed
     * @return element removed at the given position
     */
    E removeMiddle(int position) {
        return removeMiddleNode(position).node().getElement();
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Position is 1
     * Worst Case: O(n) - Position is size() - 2
     * Removes the node at the given intermediate position and returns the pair of previous and removed nodes.
     *
     * @param position position of the node to be removed
     * @return pair containing the previous node and the removed node
     */
    pairNode<E> removeMiddleNode(int position) {
        pairNode<E> pair=getNodes(position);
        super.removeMiddleNode(pair);
        return pair;
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Appends directly to tail
     * Worst Case: O(1) - Direct pointer update at tail without traversing the list.
     * Appends a new element to the end of the list.
     *
     * @param element element to be added
     */
    void addElem(E element){
        LinkedNode<E> newNode = new SinglyListNode<>(element,(null));
        super.addLastNode(newNode);
    }
}
