package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Breadth First Search Binary Tree iterator
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */

/**
 *  Explicação sobre a classe: o iterador da arvore funciona por niveis o root no nivel 1 os filhos da root nivel 2 etc
 *  o iterador avança da esquerda para a direita então como a queue é FIFO a ideia é ler e colocar na queue que é so devolver
 *
 */

class BreadthIterator<E> implements Iterator<E> {

    private Queue<BTNode<E>> queue; //
    private BTNode<E> root;


    public BreadthIterator(BTNode<E> root) {
        this.root = root;
        rewind(); // inicia a queue na rewind para evitar repetir codigo
    }
    @Override
    public boolean hasNext() {
        return !queue.isEmpty();
    }

    @Override
    public E next() throws NoSuchElementException {

        if (!hasNext()){
            throw new NoSuchElementException();
        }

        BTNode<E> current = queue.dequeue();


        if (current.getLeftChild() != null){
            queue.enqueue((BTNode<E>) current.getLeftChild());
        }
        if (current.getRightChild() != null){
            queue.enqueue((BTNode<E>) current.getRightChild());
        }

        return current.getElement();
    }

    @Override
    public void rewind() {
        this.queue = new QueueInList<>();

        if(root != null)
          queue.enqueue(root);


    }

}
