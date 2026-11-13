package dataStructures;

public class SetLinkedList<E> extends LinkedList<E> implements Set<E> {

    public SetLinkedList(){
        super();
    }

    @Override
    void addElem(E element) {
        if (!contains(element)) {
            put(element);
        }
    }

    /**
     * If there is an element in the set equals to a specified element,
     * returns the element; otherwise, returns null.
     *
     * @param element
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
     *
     * @param element
     * @return
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
     * @param element to be added
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
     *
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
     *
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


    private void invariant(){
        assert allDif();
    }

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
