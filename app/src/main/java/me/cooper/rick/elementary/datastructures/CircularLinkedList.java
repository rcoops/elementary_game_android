package me.cooper.rick.elementary.datastructures;

public class CircularLinkedList<T> {

    private Node<T> tail;
    private Node<T> current;

    @SafeVarargs
    public CircularLinkedList(T... ts) {
        for (T t : ts) add(t);
    }

    public void add(T t) {
        Node<T> newNode = new Node<>(t);
        if (tail == null) {
            newNode.next = tail = current = newNode;
        } else {
            newNode.next = tail.next;
            tail.next = newNode;
        }
    }

    public T getNext() {
        if(current == null) {
            return null;
        }
        return current.data;
    }

    public T cycleNext() {
        T data = getNext();
        current = current.next;
        return data;
    }

    class Node<T1> {
        private final T1 data;
        private Node<T1> next;

        Node(T1 data) {
            this.data = data;
        }
    }

}
