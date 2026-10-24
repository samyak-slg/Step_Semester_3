import java.util.Locale;

/**
 * Week 1 - Problem 2: The Typing Speed Test Accuracy Checker
 *
 * Compares the user's typed text against the original passage character by
 * character, then reports the accuracy percentage and the first mismatch.
 *
 * Approach : Walk both strings with one index, count matches with charAt(),
 *            and remember the first index where they differ.
 * Time     : O(n)    Space: O(1)
 */
public class TypingAccuracyChecker {

    static void checkTypingAccuracy(String original, String typed) {
        if (original == null || typed == null || original.length() != typed.length()) {
            System.out.println("Invalid input: both strings must be non-null and of equal length");
            return;
        }
        if (original.isEmpty()) {
            System.out.println("Nothing to compare: the passage is empty");
            return;
        }

        int total = original.length();
        int matched = 0;
        int firstMismatch = -1;

        for (int i = 0; i < total; i++) {
            if (original.charAt(i) == typed.charAt(i)) {
                matched++;
            } else if (firstMismatch == -1) {
                firstMismatch = i;
            }
        }

        double accuracy = (matched * 100.0) / total;
        String result = String.format(Locale.US, "Matched: %d/%d | Accuracy: %.2f%%", matched, total, accuracy);

        if (firstMismatch == -1) {
            result += " | No Mismatches";
        } else {
            // Positions are reported 1-based, the way a user would count them.
            result += String.format(" | First Mismatch at position %d ('%c' vs '%c')",
                    firstMismatch + 1, original.charAt(firstMismatch), typed.charAt(firstMismatch));
        }
        System.out.println(result);
    }

    public static void main(String[] args) {
        checkTypingAccuracy("hello world", "hello worlt");
        checkTypingAccuracy("coding", "coding");
        checkTypingAccuracy("java", "jvaa");
        checkTypingAccuracy("short", "longer text");
    }
}
