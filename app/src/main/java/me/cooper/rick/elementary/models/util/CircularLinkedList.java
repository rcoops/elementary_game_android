package me.cooper.rick.elementary.models.util;

// Very basic implementation to allow cycling of items
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
            tail = newNode;
        }
    }

    public T getCurrent() {
        if(current == null) {
            return null;
        }
        return current.data;
    }

    public T getNext() {
        if(current == null) {
            return null;
        }
        return current.next.data;
    }

    public T cycleCurrentToNext() {
        current = current.next;

        return current.data;
    }

    class Node<T1> {
        private final T1 data;
        private Node<T1> next;

        Node(T1 data) {
            this.data = data;
        }
    }

}
