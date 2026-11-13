package dataStructures;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * SepChain Hash Table
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class SepChainHashTable<K,V> extends HashTable<K,V> {
    private static final long serialVersionUID = 1L;

    //Load factors
    static final float IDEAL_LOAD_FACTOR =0.75f;
    static final float MAX_LOAD_FACTOR =0.9f;
    private static final int NUMCOLLISION = 3;

    // The array of set.
    private transient Set<Entry<K,V>>[] table;

    public SepChainHashTable( ){
        this(DEFAULT_CAPACITY);
    }

    /**
     * Time Complexity:
     * Best Case: O(n) - Linear because createTable uses a loop to initialize all n buckets.
     * Worst Case: O(n) - Linear because it always initializes every bucket in the array.
     * Constructs an empty separate chaining hash table with the specified initial capacity.
     *
     * @param capacity initial capacity of the hash table
     */

    @SuppressWarnings("unchecked")
   public SepChainHashTable( int capacity ){
       super();
       table = createTable(capacity);
    }
    /**
     * Time Complexity:
     * Best Case: O(n) - Linear in the resulting array size n because the loop runs n times to instantiate each bucket.
     * Worst Case: O(n) - Linear in the resulting array size n because it traverses all n positions to initialize each set.
     * Creates and initializes the internal array of buckets for the given capacity.
     *
     * @param capacity desired capacity used to compute the prime array size
     * @return initialized array of empty sets
     */
    @SuppressWarnings("unchecked")
    private Set<Entry<K,V>>[] createTable(int capacity){
        Set<Entry<K,V>>[] aux;
        int arraySize = HashTable.nextPrime((int) (capacity / IDEAL_LOAD_FACTOR));
        maxSize = (int)(arraySize * MAX_LOAD_FACTOR);
        // Compiler gives a warning.
        aux = (Set<Entry<K,V>>[]) new Set[arraySize];
        for ( int i = 0; i < arraySize; i++ )
            aux[i] = new SetLinkedList<>();
        return aux;
    }
    /**
     * Time Complexity:
     * Best Case: O(n) - Linear in the table capacity C and number of entries n when entries distribute evenly across the new buckets without collisions.
     * Worst Case: O(n^2) - Quadratic in n if all n entries collide into the same bucket in the new table, since put checks for duplicates linearly on each insertion.
     * Resizes the hash table to a larger prime capacity and reinserts all existing entries.
     */
    private void rehash() {
     Set<Entry<K,V>>[] oldTable = table;

     table = createTable(table.length*FACTOR);
        currentSize = 0;

            // para percorer a tabela
        for (int i = 0; i < oldTable.length; i++) {
            Iterator<Entry<K, V>> it = oldTable[i].iterator();

            while (it.hasNext()){
                Entry<K,V> entry = it.next();
                int index = hash(entry.key());
                table[index].put(entry);
                currentSize++;
            }
        }


    }

    // Returns the hash value of the specified key.
    int hash( K key ){
        return Math.abs(key.hashCode()) % table.length;
    }
    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     * Time Complexity:
     * Best Case: O(1) - Computes the hash index directly and finds the entry at the head of the bucket's list on the first comparison
     * Worst Case: O(n) - All n entries collide into the same bucket, requiring a linear traversal of all n nodes in that bucket's list.
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V get(K key) {

        int index = hash(key);

        Entry<K,V> tmp = new Entry<>(key,null);
        Entry<K,V> entry = table[index].get(tmp);

        if (entry != null){
            return entry.value();
        }
        return null;
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * replaces its value by the specified value and returns the old value;
     * otherwise, inserts the entry (key, value) and returns null.
     * Time Complexity:
     * Best Case: O(1) - Table is not full (no rehash)
     * Worst Case: O(n^2) - Table is full
     * @param key   with which the specified value is to be associated
     * @param value to be associated with the specified key
     * @return previous value associated with the key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V put(K key, V value) {
        if (isFull())
            rehash();

        int index = hash(key);

        Entry<K, V> newEntry = new Entry<>(key, value);

        if (table[index].contains(newEntry)){
            Entry<K,V> oldEntry = table[index].get(newEntry);
            table[index].replace(newEntry);
           return oldEntry.value();

        }else{

            table[index].put(newEntry);
            currentSize ++;

        }

        return null;
    }


    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * removes it from the dictionary and returns its value;
     * otherwise, returns null.
     * Time Complexity:
     * Best Case: O(1) - Computes the hash index directly and finds and removes the entry at the head of the bucket's list in constant time (no collisions).
     * Worst Case: O(n) - All n entries collide into the same bucket, so contains, get, and remove each traverse all n nodes of that bucket's list linearly.
     * @param key whose entry is to be removed from the map
     * @return previous value associated with the key,
     * or null if the dictionary does not an entry with that key
     */
    @Override
    public V remove(K key) {

        int index = hash(key);
        Entry<K,V> tmp = new Entry<>(key,null);

        if (table[index].contains(tmp)){
            Entry<K,V> entryRemove = table[index].get(tmp);
            table[index].remove(tmp);
            currentSize --;

            return entryRemove.value();
        }


        return null;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *Time Complexity:
     * Best Case: O(1) - Instantiates the iterator and finds a non-empty bucket at index 0 immediately.
     * Worst Case: O(n) - Instantiates the iterator and scans all n buckets of the table array until finding a non-empty bucket or reaching the end.
     * @return iterator of the entries in the dictionary
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new SepChainHTIterator<>(table);
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Writes the table capacity integer directly to the output stream in constant time.
     * Worst Case: O(1) - Direct stream write operation without loops.
     * Writes the capacity of the underlying bucket array to the output stream.
     *
     * @param oos output stream to write data to
     * @throws IOException if an I/O error occurs while writing to the stream
     */
    void writeData(ObjectOutputStream oos)
            throws IOException {
        oos.writeInt(table.length);
    }

    /**
     * Time Complexity:
     * Best Case: O(n) - Linear in the read capacity n because it uses a loop to initialize all n buckets with empty sets.
     * Worst Case: O(n) - Linear in the read capacity n because it traverses all n array positions to instantiate each bucket.
     * Reads the bucket array capacity from the input stream and initializes the empty buckets.
     *
     * @param ios input stream to read data from
     * @throws IOException if an I/O error occurs while reading from the stream
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     */

    @SuppressWarnings("unchecked")
    void readData(ObjectInputStream ios)
            throws IOException, ClassNotFoundException {
        int capacity = ios.readInt();
        table = (Set<Entry<K,V>>[]) new Set[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new SetLinkedList<>();
        }
    }
   
}
