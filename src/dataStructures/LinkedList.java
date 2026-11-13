package dataStructures;

import java.io.*;

/**
 * Linked List
 * Includes description of general methods to be implemented by linked lists.
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
abstract class LinkedList<E> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     *  Node at the head of the list.
     */
    transient LinkedNode<E> head;
    /**
     * Node at the tail of the list.
     */
    transient LinkedNode<E> tail;
    /**
     * Number of elements in the list.
     */
    transient int currentSize;
    /**
     * Time Complexity:
     * Best Case: O(1) - Constant time initialization of head, tail, and currentSize.
     * Worst Case: O(1) - Constant time initialization without loops.
     * Constructor of an empty singly linked list.
     * head and tail are initialized as null.
     * currentSize is initialized as 0.
     */
    public LinkedList(){
        head=null;
        tail=null;
        currentSize=0;
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Performs a single integer comparison on currentSize.
     * Worst Case: O(1) - Direct comparison in constant time without traversing nodes.
     * Returns true iff the list contains no elements.
     *
     * @return true if the list is empty
     */
    public boolean isEmpty() {
        return currentSize == 0;

    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Accesses currentSize directly in constant time.
     * Worst Case: O(1) - Direct attribute access without loops.
     * Returns the number of elements in the list.
     *
     * @return number of elements in the list
     */
    public int size() {
        return currentSize;
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Instantiates LinkedIterator initialized at head in constant time.
     * Worst Case: O(1) - Constant time object creation without traversing the list.
     * Returns an iterator of the elements in the list (in a proper sequence).
     *
     * @return Iterator of the elements in the list
     */
    public Iterator<E> iterator() {
        return new LinkedIterator<>(head);
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Updates head (and tail if empty) directly by pointer assignment.
     * Worst Case: O(1) - Direct pointer updates in constant time without loops.
     * Inserts a node on the head of list.
     *
     * @param newNode node to be inserted at the head
     */
    void addFirstNode(LinkedNode<E> newNode){
        newNode.setNext(head);
        head = newNode;
        if (isEmpty()){
            tail = newNode;
        }
        currentSize++;
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Updates tail (and head if empty) directly by pointer assignment.
     * Worst Case: O(1) - Direct pointer updates in constant time without traversing the list.
     * Inserts a node on the tail of list.
     *
     * @param newNode node to be inserted at the tail
     */
    void addLastNode(LinkedNode<E> newNode){
        if(isEmpty()){
            head = newNode;

        }else {
            tail.setNext(newNode);
        }
        tail = newNode;
        currentSize++;

    }
    /**
     * Record with two nodes (prev, node).
     *
     * @param <E> Generic Element
     * @param prev previous node
     * @param node target node
     */
    record pairNode<E>(LinkedNode<E> prev, LinkedNode<E> node){}

    /**
     * Time Complexity:
     * Best Case: O(1) - Updates next pointers of newNode and pair.prev() directly in constant time.
     * Worst Case: O(1) - Direct pointer reassignments without loops.
     * Inserts node (newNode) between pair.prev() and pair.node().
     * Precondition: pair.prev() != null &amp;&amp; pair.node() != null.
     *
     * @param pair pair containing the previous and next nodes
     * @param newNode node to be inserted in between
     */
    void addMiddleNode(pairNode<E> pair,LinkedNode<E> newNode){

        newNode.setNext(pair.node());

        pair.prev().setNext(newNode);
        currentSize++;
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Updates head pointer directly in constant time.
     * Worst Case: O(1) - Direct pointer reassignment without traversing the list.
     * Removes the first node in the list.
     * Precondition: !isEmpty().
     *
     * @return element removed from the first node
     */
    E removeFirstNode(){
        E element = head.getElement();
        head = head.getNext();
        currentSize --;
        if (isEmpty()){
            tail= null;
        }
        return element;
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Updates tail directly using pair.prev() in constant time.
     * Worst Case: O(1) - Direct pointer reassignment without loops.
     * Removes the last node (pair.node()) of the list.
     *
     * @param pair pair containing the second-to-last node and the last node
     * @return element removed from the last node
     */
    E removeLastNode(pairNode<E> pair){
        E element = pair.node().getElement();
        tail = pair.prev();
        tail.setNext(null);
        currentSize--;
        return element;
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Updates pair.prev().next directly to bypass pair.node() in constant time.
     * Worst Case: O(1) - Direct pointer reassignment without traversing nodes.
     * Removes the node pair.node().
     * Precondition: pair.prev() != null &amp;&amp; pair.node() != null.
     *
     * @param pair pair containing the previous node and the node to remove
     */
    void removeMiddleNode(pairNode<E> pair) {
        pair.prev().setNext(pair.node().getNext());

        currentSize--;
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Finds the matching element at head on the first iteration of the while loop.
     * Worst Case: O(n) - Traverses all n nodes from head to tail when the element is at the end or not in the list.
     * Searches for the given element in the list and returns its node along with the preceding node.
     *
     * @param element element to search for
     * @return pair with the previous node and the element node, or null if no element is found
     */
    pairNode<E>  nodeOf(E element){
        LinkedNode<E> prev = null;
        LinkedNode<E> node = head;
        while (node != null) {
            if (node.getElement().equals(element)) {
                return new pairNode<>(prev, node);
            }
            prev = node;
            node = node.getNext();
        }

        return null;
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Returns head reference directly in constant time.
     * Worst Case: O(1) - Direct attribute access without loops.
     * Returns the first node of the list.
     *
     * @return head node of the list
     */
    LinkedNode<E> getFirstNode(){
        return head;
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Returns tail reference directly in constant time.
     * Worst Case: O(1) - Direct attribute access without loops.
     * Returns the last node of the list.
     *
     * @return tail node of the list
     */
    LinkedNode<E> getLastNode(){
        return tail;
    }
     // MANUAL SERIALIZATION
    /**
     * Time Complexity:
     * Best Case: O(n) - Uses a while loop that traverses all n nodes from head to tail to serialize each element.
     * Worst Case: O(n) - Always iterates through all n elements in the list to write them to the stream.
     * Serializes the list size and all elements to the output stream.
     *
     * @param oos output stream to write data to
     * @throws IOException if an I/O error occurs
     */
    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        writeData(oos);
        oos.writeInt(currentSize);
        LinkedNode<E> node = head;
        while(node!=null){
            oos.writeObject(node.getElement());
            node = node.getNext();
        }
    }

    // MANUAL DESERIALIZATION
    /**
     * Time Complexity:
     * Best Case: O(n) - Loops through all n serialized elements and appends each via addElem in O(1) (for SequenceLinkedList).
     * Worst Case: O(n^2) - Loops n times calling addElem, which takes O(n) per element if duplicate checking is performed (for SetLinkedList).
     * Deserializes the list elements from the input stream and inserts them into the list.
     *
     * @param ois input stream to read data from
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     */
    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        readData(ois);
        int size = ois.readInt();
        for(int i =0; i<size;i++){
            @SuppressWarnings("unchecked")
            E element = (E) ois.readObject();
            addElem(element);
        }
    }

    void writeData(ObjectOutputStream out) throws IOException{
    }

    void readData(ObjectInputStream in) throws IOException, ClassNotFoundException {
    }

    abstract void addElem(E element);
}
