public class OffByN implements CharacterComparator {
    // off by n
    private int n;

    public OffByN(int N) {
        this.n = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        // spec says that "char values in Java are really just integers"
        return Math.abs(x - y) == n;
    }

}
