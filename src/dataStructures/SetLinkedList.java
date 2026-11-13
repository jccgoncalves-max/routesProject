package dataStructures;

public class SetLinkedList<E> extends LinkedList<E> implements Set<E> {
    /**
     * Time Complexity:
     * Best Case: O(1) - Constant time initialization of structure attributes.
     * Worst Case: O(1) - Constant time initialization.
     * Constructor of an empty set backed by a singly linked list.
     */
    public SetLinkedList(){
        super();
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Finds the element at the head of the list on the first comparison.
     * Worst Case: O(n) - Traverses all n nodes of the list when the element is at the tail or not present.
     * Adds the specified element to the set if it is not already present.
     *
     * @param element element to be added to the set
     */
    @Override
    void addElem(E element) {
        if (!contains(element)) {
            put(element);
        }
    }

    /**
     * If there is an element in the set equals to a specified element,
     * returns the element; otherwise, returns null.
     * Time Complexity:
     * Best Case: O(1) - Finds the element directly at the head node of the list.
     * Worst Case: O(n) - Traverses all n nodes when the element is at the tail or absent.
     * @param element element to search for
     * @return an element in the set equals to a specified element,
     * or null otherwise
     */
    @Override
    public E get(E element) {
        if (element == null) {
            return null;
        }
        pairNode<E> pair = nodeOf(element);
        if (pair == null) {
            return null;
        }
        return pair.node().getElement();

    }

    /**
     * If there is an element in the set equals to a specified element,
     * returns true; otherwise, returns false.
     * Time Complexity:
     * Best Case: O(1) - Finds the element directly at the head node of the list.
     * Worst Case: O(n) - Traverses all n nodes when the element is at the tail or absent.
     * @param element element whose presence is to be tested
     * @return true if the set contains the specified element, false otherwise
     */
    @Override
    public boolean contains(E element) {
        if (isEmpty())
            return false;
        return get(element)!=null;
    }

    /**
     * Inserts the element,
     * if there is not an element in the set equals to the specified element and return true.
     * otherwise returns false
     * Time Complexity:
     * Best Case: O(1) - Detects that the element already exists at the head node (or inserts into an empty list).
     * Worst Case: O(n) - traverses all n nodes to confirm no duplicate exists, and then appends directly to the tail in O(1).
     * @return true if insert the element, otherwise false
     */
    public boolean put(E element){
        if (element == null || contains(element)) {
            return false;
        }
        addLastNode(new SinglyListNode<>(element));
        invariant();
        return true;


    }

    /**
     * If there is an element in the set equals to the specified element
     * replace the element and return old element.
     * otherwise returns null
     *Time Complexity:
     * Best Case: O(1) - Finds and replaces the element directly at the head node.
     * Worst Case: O(n) - Traverses all n nodes when the element is at the tail or absent.
     * @param element to be added
     * @return old element, otherwise null
     */
    @Override
    public E replace(E element) {

        if (element == null) {
            return null;
        }
        pairNode<E> pair = nodeOf(element);
        if (pair == null) {
            return null;
        }
        LinkedNode<E> node = pair.node();
        E oldElement = node.getElement();
        node.setElement(element);
        invariant();
        return oldElement;
    }

    /**
     * If there is an element in the set equals to the specified element,
     * removes it from the set and returns the element;
     * otherwise, returns null.
     * Time Complexity:
     * Best Case: O(1) - Finds the element at the head node.
     * Worst Case: O(n) - Traverses all n nodes to reach the tail before removing.
     * @param element to be removed
     * @return element removed,
     * or null if the set does not an element equals
     */
    @Override
    public E remove(E element) {

        if (element == null) {
            return null;
        }
        pairNode<E> pair = nodeOf(element);
        if (pair == null) {
            return null;
        }
        if (pair.prev() == null) {
            E removedElem = removeFirstNode();
            invariant();
            return removedElem;

        }
        if (pair.node() == tail) {
            E removedElem = removeLastNode(pair);
            invariant();
            return removedElem;
        }
        E removedElem = pair.node().getElement();
        removeMiddleNode(pair);
        invariant();
        return removedElem;
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Delegates to  terminating immediately if duplicates are at the start.
     * Worst Case: O(n^2) - Delegates to comparing all pairs of the n elements.
     * Verifies the set invariant that all elements are distinct.
     */
    private void invariant(){
        assert allDif();
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Finds duplicate elements between the first two nodes on the first iteration.
     * Worst Case: O(n^2) - Uses two nested loops to compare every pair among all n nodes when all elements are distinct.
     * Checks whether all elements currently stored in the list are distinct.
     *
     * @return true if all elements are distinct, false otherwise
     */
    private boolean allDif() {

        LinkedNode<E> current = head;
        while (current != null) {
            LinkedNode<E> runner = current.getNext();
            while (runner != null) {
                if (current.getElement().equals(runner.getElement())) {
                    return false;
                }
                runner = runner.getNext();
            }
            current = current.getNext();
        }
        return true;
    }
}
