package dataStructures;

import dataStructures.exceptions.EmptyMapException;
import dataStructures.Map.Entry;

import java.io.*;

/**
 * Binary Search Tree Sorted Map
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class BSTree<K extends Comparable< K>,V> extends BTree<Entry<K,V>> implements SortedMap<K,V>,Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * Comparator
     */
    private Comparator<K> comparator;
    /**
     * Default comparator
     * @param <K>
     */
    @SuppressWarnings("unchecked")
    static class DefaultComparator<K extends Comparable<K>> implements Comparator<K>, Serializable {

        @Serial
        private static final long serialVersionUID = 1L;
        /**
         * Time Complexity:
         * Best Case: O(1) - Direct comparison between two keys using compareTo.
         * Worst Case: O(1) - Constant time key comparison without loops.
         * Compares two keys according to their natural ordering.
         *
         * @param a first key to be compared
         * @param b second key to be compared
         * @return negative integer, zero, or positive integer as a is less than, equal to, or greater than b
         */
        public int compare(K a, K b)  {
            return a.compareTo(b);
        }
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Constant time initialization of tree attributes and default comparator.
     * Worst Case: O(1) - Constant time initialization without loops.
     * Constructs an empty binary search tree using the natural ordering of keys.
     */
    @SuppressWarnings("unchecked")
    public BSTree(){
        super();
        comparator= new DefaultComparator<K>();
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Constant time initialization of tree attributes and custom comparator.
     * Worst Case: O(1) - Constant time initialization without loops.
     * Constructs an empty binary search tree ordered according to the specified comparator.
     *
     * @param compare comparator used to order keys in the tree
     */
    @SuppressWarnings("unchecked")
    public BSTree(Comparator<K> compare){
        super();
        comparator = compare;
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Root has no left child, so furtherLeftElement returns the root directly without looping.
     * Worst Case: O(n) - furtherLeftElement traverses left child pointers down to the leftmost leaf, visiting n nodes
     * Returns the entry with the smallest key in the map.
     *
     * @return entry with the smallest key
     * @throws EmptyMapException if the map is empty
     */
    public Entry<K, V> minEntry() {
        if (isEmpty())
            throw new EmptyMapException();
        return furtherLeftElement().getElement();
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Root has no right child, so furtherRightElement returns the root directly without looping.
     * Worst Case: O(n) - furtherRightElement traverses right child pointers down to the rightmost leaf, visiting n nodes.
     * Returns the entry with the largest key in the map.
     *
     * @return entry with the largest key
     * @throws EmptyMapException if the map is empty
     */
    public Entry<K, V> maxEntry() {
        if (isEmpty())
            throw new EmptyMapException();
        return furtherRightElement().getElement();
    }
    /**
     * Record storing the parent node, target node, and last comparison result of a search.
     *
     * @param <K> Generic Key
     * @param <V> Generic Value
     * @param parent parent of the found or insertion node
     * @param node matching node if found, or null otherwise
     * @param cmp result of the last key comparison
     */
    record tri<K,V>(
            BTNode<Entry<K,V>> parent,
            BTNode<Entry<K,V>> node,
            int cmp
    ){}
    /**
     * Time Complexity:
     * Best Case: O(1) - Finds the matching key at the root node on the first iteration of the loop.
     * Worst Case: O(n) - Traverses down a single branch from root to leaf, performing up to n comparisons
     * Searches for the node containing the specified key and returns it along with its parent and comparison result.
     *
     * @param key key to search for
     * @return triple containing the parent node, matching node (or null), and last comparison value
     */
    tri<K,V> find(K key){

        BTNode<Entry<K,V>> current = (BTNode<Entry<K, V>>) root;
        BTNode<Entry<K,V>> parent = null;
        int cmp = 0;

        while (current != null){

            cmp = comparator.compare(key,current.getElement().key());

            if (cmp == 0){
                return new tri<>(parent,current,cmp);
            }
            parent = current;
            if (cmp < 0){
                current = (BTNode<Entry<K, V>>) current.getLeftChild();

            }else{
                current= (BTNode<Entry<K, V>>) current.getRightChild();
            }
        }

        return new tri<>(parent,null,cmp);
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Key is located at the root node, found by find on the first comparison.
     * Worst Case: O(n) - Key is at the deepest leaf (or absent), requiring find to traverse n nodes.
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     *
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    public V get(K key) {
        tri<K,V> triple = find(key);

        if (triple.node == null)
            return null;

        return triple.node().getElement().value();
    }


    /**
     * Time Complexity:
     * Best Case: O(1) - Tree is empty or key matches the root node, so find finishes on the first comparison and inserts/updates directly.
     * Worst Case: O(n) - find traverses n nodes down to the deepest leaf level
     * If there is an entry in the dictionary whose key is the specified key,
     * replaces its value by the specified value and returns the old value;
     * otherwise, inserts the entry (key, value) and returns null.
     *
     * @param key   with which the specified value is to be associated
     * @param value to be associated with the specified key
     * @return previous value associated with key,
     * or null if the dictionary does not have an entry with that key
     */
    public V put(K key, V value) {

        tri<K,V> triple = find(key);

        if (triple.node() != null){
            V oldValue = triple.node().getElement().value();

            triple.node().setElement(new Entry<>(key,value));
            return oldValue;
        }
        if (triple.parent == null) {
            root = new BTNode<>(new Entry<>(key, value));

        }else{
            BTNode<Entry<K,V>> newNode = new BTNode<>(new Entry<>(key,value),triple.parent());

            if (triple.cmp < 0)
                triple.parent().setLeftChild(newNode);
            else
                triple.parent().setRightChild(newNode);
        }

        currentSize++;

        return null;
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Target key is at the root node with at most one child, so find locates it on the first comparison and updates pointers directly.
     * Worst Case: O(n) - find traverses up to n nodes to locate the key, and if it has two children, furtherRightElement traverses down to its predecessor leaf within the height n
     * If there is an entry in the map whose key is the specified key,
     * removes it from the map and returns its value;
     * otherwise, returns null.
     *
     * @param key whose entry is to be removed from the map
     * @return previous value associated with key,
     * or null if the dictionary does not contain an entry with that key
     */
    public V remove(K key) {

        tri<K,V> triple = find(key);

        if (triple.node() == null) {
            return null;
        }

        V oldNode = triple.node().getElement().value();
        if (triple.node().getRightChild() == null && triple.node().getLeftChild() == null){

            if (triple.parent() == null){
                root = null;

            }else {
                if (triple.parent().getLeftChild()==triple.node())
                    triple.parent().setLeftChild(null);
                else
                    triple.parent().setRightChild(null);
            }
            currentSize --;
            return oldNode;
        }
        if (triple.node().getRightChild() == null || triple.node().getLeftChild() == null) {

            BTNode<Entry<K, V>> child = (BTNode<Entry<K, V>>) triple.node().getLeftChild();
            if (child == null) {
                child = (BTNode<Entry<K, V>>) triple.node().getRightChild();
            }
            child.setParent(triple.parent());
            if (triple.parent() == null) {
                root = child;

            } else {
                if (triple.parent().getLeftChild()==triple.node()) {
                    triple.parent().setLeftChild(child);
                } else {
                    triple.parent().setRightChild(child);
                }
            }
            currentSize--;
            return oldNode;
        }
        if (triple.node().getLeftChild() != null && triple.node().getRightChild() != null){

            BTNode<Entry<K,V>> nodeLeft = (BTNode<Entry<K, V>>) triple.node().getLeftChild();
            BTNode<Entry<K,V>> nodeRight = nodeLeft.furtherRightElement();

            remove(nodeRight.getElement().key());

            triple.node().setElement(nodeRight.getElement());

            return oldNode;


        }

        return null;
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Root has no left child, so InOrderIterator initialization pushes only the root onto its internal stack.
     * Worst Case: O(n) - InOrderIterator initialization pushes all n nodes along the leftmost path from root onto its stack
     * Returns an iterator of the entries in the dictionary in ascending key order.
     *
     * @return iterator of the entries in the dictionary
     */
    public Iterator<Entry<K, V>> iterator() {
        return new InOrderIterator<>((BTNode<Entry<K,V>>) root);
    }

    //Manual Serialization
    /**
     * Time Complexity:
     * Best Case: O(1) - Instantiates BreadthIterator and enqueues only the root node in constant time.
     * Worst Case: O(1) - Constant time initialization without traversing the tree.
     * Returns a breadth-first (level-order) iterator of the entries in the dictionary.
     *
     * @return level-order iterator of the entries in the dictionary
     */
    Iterator<Entry<K, V>> iteratorLevel() {
    return new BreadthIterator<>((BTNode<Entry<K,V>>)root);

    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Tree is empty or key matches root, inserting/updating in constant time via put.
     * Worst Case: O(n) - Traverses n nodes down to the deepest leaf
     * Inserts an entry into the tree during deserialization.
     *
     * @param element entry to be added
     */
    void addElem(Entry<K, V> element) {
        put(element.key(),element.value());
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Writes the comparator object directly to the output stream in constant time.
     * Worst Case: O(1) - Direct stream write operation without loops.
     * Writes the comparator metadata to the output stream during serialization.
     *
     * @param oos output stream to write data to
     * @throws IOException if an I/O error occurs
     */
    void writeData(ObjectOutputStream oos) throws IOException {
        oos.writeObject(comparator);
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Reads the comparator object directly from the input stream in constant time.
     * Worst Case: O(1) - Direct stream read operation without loops.
     * Reads the comparator metadata from the input stream during deserialization.
     *
     * @param ois input stream to read data from
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the comparator class cannot be found
     */
    @SuppressWarnings("unchecked")
    void readData(ObjectInputStream ois)
            throws IOException, ClassNotFoundException {
        comparator = (Comparator<K>) ois.readObject();
    }

}
