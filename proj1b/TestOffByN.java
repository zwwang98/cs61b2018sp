import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    static CharacterComparator offBy2 = new OffByN(2);
    static CharacterComparator offBy4 = new OffByN(4);
    static CharacterComparator offBy6 = new OffByN(6);
    
    @Test
    public void testOffBy2() {
        assertTrue(offBy2.equalChars('a', 'c'));
        assertTrue(offBy2.equalChars('c', 'a'));
        assertFalse(offBy2.equalChars('a', 'a'));
        assertFalse(offBy2.equalChars('a', 'b'));
        assertFalse(offBy2.equalChars('b', 'a'));
        assertFalse(offBy2.equalChars('a', 'd'));
        assertFalse(offBy2.equalChars('d', 'a'));
    }

    @Test
    public void testOffBy4() {
        assertTrue(offBy4.equalChars('a', 'e'));
        assertTrue(offBy4.equalChars('e', 'a'));
        assertFalse(offBy4.equalChars('a', 'a'));
        assertFalse(offBy4.equalChars('c', 'a'));
        assertFalse(offBy4.equalChars('a', 'c'));
        assertFalse(offBy4.equalChars('f', 'a'));
        assertFalse(offBy4.equalChars('a', 'f'));
    }

    @Test
    public void testOffBy6() {
        assertTrue(offBy6.equalChars('a', 'g'));
        assertTrue(offBy6.equalChars('g', 'a'));
        assertFalse(offBy6.equalChars('a', 'a'));
        assertFalse(offBy6.equalChars('a', 'c'));
        assertFalse(offBy6.equalChars('c', 'a'));
        assertFalse(offBy6.equalChars('a', 'h'));
        assertFalse(offBy6.equalChars('h', 'a'));
    }
}
