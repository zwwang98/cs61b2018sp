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
        // I think it will be much easier to use ArrayDeque to implement isPalindrome
        Deque<Character> chars = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            chars.addLast(word.charAt(i));
        }
        return chars;
    }

    /**
     * The isPalindrome method should return true if the given word is a palindrome, and false otherwise.
     * A palindrome is defined as a word that is the same whether it is read forwards or backwards.
     * For example:
     *    1.“a”, “racecar”, and “noon” are all palindromes.
     *    2.“horse”, “rancor”, and “aaaaab” are not palindromes.
     * Any word of length 1 or 0 is a palindrome.
     * ‘A’ and ‘a’ should not be considered equal.
     * */
    public boolean isPalindrome(String word) {
        /* n is the number of necessary comparisons to decide
        * whether the word is a palindrome or not.
        * To better explain why n is like that, for example:
        * word = noon, length = 4, comparisons = 2
        * word = after, length = 5, comparisons = 2
        * to sum up, it doesn't matter the word's length is even or odd,
        * we can just use the integer division, it's simple and clear */
        int n = word.length();
        // "Any word of length 1 or 0 is a palindrome."
        if (n == 0 || n == 1) {
            return true;
        }
        int numOfCmp = n / 2;
        Deque<Character> chars = wordToDeque(word);
        /* in the for loop below, we read the input word forwards and backwards at the same time
        * to check if it matches the definition of a palindrome */
        for (int i = 0; i < numOfCmp; i++) {
            // j is the corresponding index moving from the end to the head
            int j = n - 1 - i;
            if (!chars.get(i).equals(chars.get(j))) {
                return false;
            }
        }
        return true;
    }

    /** The method will return true if the word is a palindrome
     * according to the character comparison test provided
     * by the CharacterComparator passed in as argument cc. */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        return false;
    }

}
