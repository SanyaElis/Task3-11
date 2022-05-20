package ru.vsu.cs.eliseev;


public class SimpleQueue<T> {

    private static class Node<T> {

        final T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

    }

    private Node<T> head;
    private Node<T> tail;

    public SimpleQueue() {
        head = null;
        tail = null;
    }

    /**
     * @return empty Queue
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Inserts the specified element into this queue
     */
    public void add(T data) {
        Node<T> temp = new Node<>(data);
        if (isEmpty()) {
            head = temp;
            tail = temp;
            tail.setNext(null);
        } else {
            tail.setNext(temp);
            temp.setNext(null);
            tail = temp;
        }
    }

    /**
     * @return Retrieves and removes the head of this queue, or returns null if this queue is empty.
     */
    public T poll() {
        if (isEmpty()) {
            return null;
        }
        T temp = head.getData();
        head = head.getNext();
        return temp;

    }

    /**
     * @return Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
     */
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return head.getData();
    }

    /**
     * @param a
     * @return позиция элемента сверху
     */
    public int search(T a) {
        Node<T> temp = head;
        int i = 1;
        while (temp != null) {
            if (temp.getData().equals(a)) {
                return i;
            }
            temp = temp.getNext();
            i++;
        }
        return -1;
    }

    public void print() {
        Node<T> temp = head;

        while (temp != null) {
            System.out.println(temp.getData());
            temp = temp.getNext();
        }
    }

}
