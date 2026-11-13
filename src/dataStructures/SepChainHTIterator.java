package dataStructures;

import dataStructures.exceptions.NoSuchElementException;
import dataStructures.Map;
/**
 * SepChain Hash Table Iterator
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */

/**
 */
class SepChainHTIterator<K,V> implements Iterator<Map.Entry<K,V>> {
    private static final long serialVersionUID = 1L;

    private Set<Map.Entry<K,V>>[] table;
    private int current;
    private Iterator<Map.Entry<K,V>> currentIterator;

    /**
     * Time Complexity:
     * Best Case: O(1) -  finds a non-empty bucket at index 0 on the first check without scanning further.
     * Worst Case: O(n) - scans all n buckets of the table array when only the last bucket is occupied (or the table is empty)
     * Constructs an iterator for the given separate chaining hash table and positions it at the first available entry.
     *
     * @param table array of sets representing the buckets of the hash table
     */

    @SuppressWarnings("unchecked")
    public SepChainHTIterator(Set<Map.Entry<K, V>>[] table) {
        this.table = table;
        rewind();
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Performs a single integer comparison
     * Worst Case: O(1) - Direct comparison in constant time without loops.
     * Returns true if the iteration has more elements.
     *
     * @return true if there is at least one more entry to iterate over, false otherwise
     */
    @Override
    public boolean hasNext() {
        return current != table.length;
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Retrieves the next entry in constant time and the current bucket still has elements (or the immediately next bucket is non-empty).
     * Worst Case: O(n) - Current bucket is exhausted
     * Returns the next entry in the hash table iteration.
     *
     * @return the next in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public Map.Entry<K, V> next() throws NoSuchElementException {

        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Map.Entry<K, V> entry = currentIterator.next();

        if (!currentIterator.hasNext()) {
            current++;
            advanceToNextPosTable();

        }
        return entry;
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Resets the index to 0
     * Worst Case: O(n) - Resets the index to 0
     * Restarts the iteration from the first non-empty bucket of the hash table.
     */

    @Override
    public void rewind() {
        current = 0;
        currentIterator = null;
        advanceToNextPosTable();
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - The bucket at the current index is non-empty, terminating the loop on the first iteration.
     * Worst Case: O(n) - Uses a while loop that traverses all remaining buckets up to when intermediate buckets are empty.
     * Advances to the next index  that contains at least one entry.
     */
 private void advanceToNextPosTable(){
     while (current < table.length){
         Iterator<Map.Entry<K,V>> it = table[current].iterator();
         if (it.hasNext()){
             currentIterator = it;
             return;
         }
         current++;
     }
 }

}

