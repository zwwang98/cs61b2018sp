package synthesizer;

/**
 * The purpose of AbstractBoundedQueue will be to simply
 * provide a protected fillCount and capacity variable that all subclasses will inherit,
 * as well as so called “getter” methods capacity() and fillCount() that
 * return capacity and fillCount, respectively.
 * */
public class AbstractBoundedQueue {
    protected int fillCount;
    protected int capacity;

    public int capacity() {
        return capacity;
    }

    public int fillCount() {
        return fillCount;
    }
}
