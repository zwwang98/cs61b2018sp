public class ArrayDeque<T> {
    private T[] arr;
    private int size;

    private double R;
    // the empty box right before the first item
    private int front;
    // the empty box right after the last item
    private int end;

    /** Creates an empty array deque. */
    public ArrayDeque() {
        // The instructions said that "The starting size of your array should be 8.".
        arr = (T[]) new Object[8];
        end = 1;
    }

    private void resize(int capacity) {
        T[] newArr = (T[]) new Object[capacity];
        if (front < end) {
            System.arraycopy(arr, front + 1, newArr, 0, size);
            arr = newArr;
            front = arr.length - 1;
            end = size;
        } else {
            System.arraycopy(arr, front + 1, newArr, 0, arr.length - front - 1);
            System.arraycopy(arr, 0, newArr, arr.length - front - 1, end);
            arr = newArr;
            front = arr.length - 1;
            end = size;
        }

    }

    /**
     * Adds an item of type T to the front of the deque.
     * */
    public void addFirst(T item) {
        if (isEmpty()) {
            arr[0] = item;
            front = arr.length - 1;
            end = 1;
        } else {
            arr[front] = item;
            // treat array as circular
            if (front == 0) {
                front = arr.length - 1;
            } else {
                front--;
            }
        }
        size++;
        // resize
        if (size > arr.length / 2) {
            resize(arr.length * 2);
        }
    }

    /**
     * Adds an item of type T to the back of the deque.
     * */
    public void addLast(T item) {
        if (isEmpty()) {
            arr[0] = item;
            front = arr.length - 1;
            end = 1;
        } else {
            arr[end] = item;
            // treat array as circular
            if (end == arr.length - 1) {
                end = 0;
            } else {
                end++;
            }
        }
        size++;
        // resize
        if (size > arr.length / 2) {
            resize(arr.length * 2);
        }
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

    /** Prints the items in the deque from first to last, separated by a space. */
    public void printDeque() {
        if (front < end) { // treat arr as a normal array
            for (int i = front + 1; i < end; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
        } else { // arr is circular
            for (int i = front + 1; i < arr.length; i++) {
                System.out.print(arr[i] + " ");
            }
            for (int i = 0; i < end; i++) {
                System.out.print(arr[i] + " ");
            }
        }
    }

    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     * */
    public T removeFirst() {
        // if isEmpty, return null
        if (isEmpty()) {
            return null;
        }
        T result = null;
        // find the first item's index
        if (front == arr.length - 1) {
            result = arr[0];
            arr[0] = null;
            front = 0;
        } else {
            result = arr[front + 1];
            arr[front + 1] = null;
            front++;
        }
        size--;
        // resize if necessary
        if (size < arr.length / 4 && arr.length != 8) {
            resize(arr.length / 2);
        }
        return result;
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     * */
    public T removeLast() {
        // if isEmpty, return null
        if (isEmpty()) {
            return null;
        }
        T result = null;
        if (end == 0) {
            result = arr[arr.length - 1];
            arr[arr.length - 1] = null;
            end = arr.length - 1;
        } else {
            result = arr[end - 1];
            arr[end - 1] = null;
            end--;
        }
        size--;
        // resize if necessary
        if (size < arr.length / 4 && arr.length != 8) {
            resize(arr.length / 2);
        }
        return result;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!
     * */
    public T get(int index) {
        int actualIndex = index + front + 1;
        if (actualIndex > arr.length - 1) {
            actualIndex = actualIndex - arr.length;
        }
        return arr[actualIndex];
    }


    /*public static void main(String[] args) {
        ArrayDeque<Integer> intDeque = new ArrayDeque<>();


        // Report: https://www.gradescope.com/courses/20666/assignments/85897/submissions/46779393
        // d003) AD-basic: Random addFirst/removeLast/isEmpty tests. (0.0/1.176)
        // sequence of ArrayDeque operations was as below
        intDeque.isEmpty();
        intDeque.addFirst(1);
        intDeque.removeLast();
        intDeque.addFirst(3);
        intDeque.isEmpty();
        intDeque.removeLast(); // expected:<3> but was:<null>


        // Report: https://www.gradescope.com/courses/20666/assignments/85897/submissions/46779393
        // d005) AD-basic: Random addFirst/removeLast/isEmpty tests. (0.0/1.176)
        // sequence of ArrayDeque operations was as below
        intDeque.isEmpty();
        intDeque.addLast(1);
        intDeque.removeFirst();
        intDeque.addLast(3);
        intDeque.removeFirst(); // expected:<3> but was:<null>


        // Report: https://www.gradescope.com/courses/20666/assignments/85897/submissions/46779393
        // d006) AD-basic: Random addFirst/removeLast/isEmpty tests. (0.0/1.176)
        // sequence of ArrayDeque operations was as below
        intDeque.addFirst(0);
        intDeque.addLast(1);
        intDeque.removeLast();
        intDeque.removeLast();
        intDeque.addFirst(4);
        intDeque.addFirst(5);
        intDeque.removeLast(); // expected:<4> but was:<null>

        // Report:
        // https://www.gradescope.com/courses/20666/
        assignments/85897/submissions/46784180#AD-basic:%20get.
        // d011) AD-basic: get. (0.0/1.176)
        // sequence of ArrayDeque operations was as below
        intDeque.addLast(0);
        intDeque.get(0);
        intDeque.get(0);
        intDeque.removeLast();
        intDeque.addLast(4);
        intDeque.removeFirst();
        intDeque.addFirst(6);
        intDeque.addLast(7);
        intDeque.addLast(8);
        intDeque.addLast(9);
        intDeque.get(2);
        intDeque.addLast(11);
        intDeque.removeLast();
        intDeque.addFirst(13);
        intDeque.addFirst(14);
        intDeque.removeLast();
        intDeque.removeFirst();
        intDeque.removeLast();
        intDeque.removeLast();
        intDeque.addLast(19);
        intDeque.get(2); // expected:<19> but was:<null>




        for (int i = 10; i >= 0; i--) {
            intDeque.addFirst(i);
        }

        for (int i = 0; i < 5; i++) {
            System.out.println(intDeque.get(i));
        }

        for (int i = 9; i < 18; i++) {
            intDeque.addLast(i);
        }

        for (int i = 0; i < 5; i++) {
            System.out.println(intDeque.get(i));
        }

        for (int i = 8; i >= 0; i--) {
            intDeque.removeFirst();
        }
        for (int i = 9; i < 18; i++) {
            intDeque.removeLast();
        }

    }*/
}
