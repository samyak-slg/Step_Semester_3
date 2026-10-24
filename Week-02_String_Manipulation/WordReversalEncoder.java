/**
 * Week 2 - Problem 2: Word Reversal Encoder
 *
 * "Mirror text" mini-game: reverses every word individually while keeping the
 * word order the same, e.g. "hello club" -> "olleh bulc".
 *
 * Approach : split(" ") into words, build each reversed word with a backwards
 *            loop into a StringBuilder, and join the words back with spaces.
 * Time     : O(n)    Space: O(n)
 */
public class WordReversalEncoder {

    static String reverseEachWord(String sentence) {
        if (sentence == null || sentence.isEmpty()) {
            return "";
        }

        String[] words = sentence.split(" ");
        StringBuilder result = new StringBuilder();

        for (int w = 0; w < words.length; w++) {
            String word = words[w];
            for (int i = word.length() - 1; i >= 0; i--) {
                result.append(word.charAt(i));
            }
            if (w < words.length - 1) {
                result.append(' ');
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        String[] sentences = {"hello club", "Java is fun", "racecar level"};

        for (String sentence : sentences) {
            System.out.println("Input: \"" + sentence + "\" -> " + reverseEachWord(sentence));
        }
    }
}
