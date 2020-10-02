public class OffByOne implements CharacterComparator {

    /** equalChars returns true for characters that are different by exactly one.
     * For example the following calls to obo should return true.
     *    OffByOne obo = new OffByOne();
     *    obo.equalChars('a', 'b');
     *    obo.equalChars('r', 'q');
     * However, the three calls below should return false:
     *    obo.equalChars('a', 'e');
     *    obo.equalChars('z', 'a');
     *    obo.equalChars('a', 'a');*/
    @Override
    public boolean equalChars(char x, char y) {
        // spec says that "char values in Java are really just integers"
        return Math.abs(x - y) == 1;
    }
}
