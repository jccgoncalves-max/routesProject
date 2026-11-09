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
 * funcionamento da classe: aqui temos iteradores por niveis o primeiro para percorer a tabela
 * o segundo para percorer a lista da tabela.
 *
 *
 */
class SepChainHTIterator<K,V> implements Iterator<Map.Entry<K,V>> {

    private Set<Map.Entry<K,V>>[] table;
    private int current;
    private Iterator<Map.Entry<K,V>> currentIterator;

    public SepChainHTIterator(Set<Map.Entry<K, V>>[] table) {
        this.table = table;
        rewind(); // para posicionar o iterador no primeiro elemento valido
    }

    @Override
    public boolean hasNext() {
        return currentIterator != null && currentIterator.hasNext();
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

    //metodo auxiliar
 private void advanceToNextPosTable(){

        // aqui devolve um iterador para a posição da tabela
     while (current < table.length){
         Iterator<Map.Entry<K,V>> it = table[current].iterator();
         if (it.hasNext()){
             currentIterator = it;
             return;
         }
         current++;
     }
     currentIterator = null;
 }

}

