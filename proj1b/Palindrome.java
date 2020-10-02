public class Palindrome {

    /**
     * Given a String, wordToDeque should return a Deque
     * where the characters appear in the same order as in the String.
     *
     * For example, if the word is “persiflage”, then the returned Deque
     * should have ‘p’ at the front, followed by ‘e’, and so forth.
     * ==> {p, e, r, s, i, f, l, a, g, e}
     *
     * @source: got to know how to get string character by index
     * https://stackoverflow.com/questions/11229986/get-string-character-by-index-java
     * */
    public Deque<Character> wordToDeque(String word) {
        int n = word.length();
        // use our own Deque implementation
        Deque<Character> chars = new LinkedListDeque<>();
        for (int i = 0; i < n; i++) {
            chars.addLast(word.charAt(i));
        }
        return chars;
    }
}
