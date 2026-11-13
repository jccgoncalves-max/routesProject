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

    @SuppressWarnings("unchecked")
    public SepChainHTIterator(Set<Map.Entry<K, V>>[] table) {
        this.table = table;
        rewind();
    }

    @Override
    public boolean hasNext() {
        return current != table.length;
    }

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

    @Override
    public void rewind() {
        current = 0;
        currentIterator = null;
        advanceToNextPosTable();
    }

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

