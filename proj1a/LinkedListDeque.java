

public class LinkedListDeque<T> {

    private Node sentinel;
    private int size;

    /**
     * private inner class representing a Node in LinkedList
     * A Node has three components:
     *   1. its item
     *   2. a reference pointing to previous Node
     *   3. a reference pointing to next Node
     * */
    private class Node {
        private T item;
        private Node prev;
        private Node next;

        public Node(T i, Node p, Node n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    /** Creates an empty linked list deque. */
    public LinkedListDeque() {
        // circular sentinel
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }


    /**
     * Adds an item of type T to the front of the deque.
     * */
    public void addFirst(T item) {
        sentinel.next = new Node(item, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size++;
    }

    /**
     * Adds an item of type T to the back of the deque.
     * */
    public void addLast(T item) {
        sentinel.prev = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size++;
    }

    /**
     * Returns true if deque is empty, false otherwise.
     * */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of items in the deque.
     * */
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last,
     * separated by a space. */
    public void printDeque() {
        Node p = sentinel.next;
        while (p.item != null) {
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.println();
    }

    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     * */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T result = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size--;
        return result;
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     * */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T result = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size--;
        return result;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!
     *
     * Besides, the instructions said that the get operation must use iteration.
     * https://sp18.datastructur.es/materials/proj/proj1a/proj1a
     * */
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        int i = 0;
        Node p = sentinel.next;
        while (i < index) {
            p = p.next;
            i++;
        }
        return p.item;
    }

    /**
     * Same as get, but uses recursion.
     * */
    public T getRecursive(int index) {
        return getRecursiveHelper(index, sentinel.next);
    }

    private T getRecursiveHelper(int index, Node p) {
        // base case
        if (index == 0) {
            return p.item;
        } else {
            return getRecursiveHelper(index - 1, p.next);
        }
    }

    /*
    public static void main(String[] args) {
        LinkedListDeque<Integer> intDeque = new LinkedListDeque<>();

        // test isEmpty, expected return value: true
        assert (intDeque.isEmpty());
        System.out.println("The method isEmpty() works properly.");

        // form a Deque {1, 2, 3, 4, 5, 6}
        intDeque.addFirst(3);
        intDeque.addFirst(2);
        intDeque.addFirst(1);
        intDeque.addLast(4);
        intDeque.addLast(5);
        intDeque.addLast(6);

        // test isEmpty(), expected return value: false
        assert (!intDeque.isEmpty());
        System.out.println("The method isEmpty() works properly.");

        // test size(), expected return value : 6
        assert (intDeque.size() == 6);
        System.out.println("The size() works properly.");


        System.out.println(intDeque.get(0));
        System.out.println(intDeque.get(1));
        System.out.println(intDeque.get(2));

        System.out.println(intDeque.getRecursive(0));
        System.out.println(intDeque.getRecursive(1));
        System.out.println(intDeque.getRecursive(2));

        // test printDeque()
        intDeque.printDeque();

        System.out.println(intDeque.removeLast());
        System.out.println(intDeque.removeLast());

        // test size(), expected return value : 4
        assert (intDeque.size() == 4);
        System.out.println("The size() works properly.");

        System.out.println(intDeque.removeFirst());
        System.out.println(intDeque.removeFirst());

        // test size(), expected return value : 2
        assert (intDeque.size() == 2);
        System.out.println("The size() works properly.");

    }*/
}
