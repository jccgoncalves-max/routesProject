package dataStructures;

/**
 * Implementation of Singly Linked List
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
public class SinglyLinkedList<E> extends SequenceLinkedList<E> {

    /**
     * Constructs an empty SinglyLinkedList.
     * Time Complexity:
     *  Best Case: O(1) - Constant time initialization.
     *  Worst Case: O(1) - Constant time initialization.
     */
    public SinglyLinkedList( ) {
        super();
    }

    /**
     * Inserts the specified element at the first position in the list.
     *Time complexity:
     * Best case: 0(1)-Creates a new node and updates head reference in constant time.
     * Worse case: 0(1)-Direct pointer insertion at head in constant time.
     * @param element to be inserted
     */
    @Override
    public void addFirst(E element) {
        LinkedNode<E> newNode = new SinglyListNode<>(element,(SinglyListNode<E>)head);
        super.addFirstNode(newNode);
    }

    /**
     * Inserts the specified element at the last position in the list.
     * Utilizes the tail reference to perform direct constant-time insertion.
     * Time complexity
     * Best case -O(1)-Appends node directly using tail reference.
     * worse case- O(n) - Appends node directly using tail reference in constant time.
     * Inserts the specified element at the last position in the list.
     *
     * @param element to be inserted
     */
    @Override
    public void addLast(E element) {
        LinkedNode<E> newNode = new SinglyListNode<>(element,null);
        super.addLastNode(newNode);
    }

    /**
     * Inserts the specified element at the specified middle position in the list.
     * Time complexity:
     * Best case- O(1) Target position is 1, requiring minimal node traversal in getNodes.
     * Worse case -O(n) Target position is near the end, requiring linear traversal up to position n.
     * @param position position to be added
     * @param element element to be added
     */
    void addMiddle(int position, E element) {
        pairNode<E> pair = super.getNodes(position);
        LinkedNode<E> newNode = new SinglyListNode<>(element,(SinglyListNode<E>)(pair.node()));
        super.addMiddleNode(pair,newNode);
    }
}
