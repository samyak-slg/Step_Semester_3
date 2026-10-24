import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Week 2 - Problem 5: Stop-Word-Filtered Word Frequency Report
 *
 * Counts meaningful words in a feedback paragraph, skipping filler words, and
 * prints them sorted by frequency (highest first).
 *
 * Approach : lowercase -> strip punctuation with replace() -> split("\\s+")
 *            -> skip stop words -> count in a LinkedHashMap (keeps first-seen order,
 *            so ties print in a predictable order) -> stable sort by count desc.
 * Time     : O(n + u log u) where u = number of unique words    Space: O(u)
 */
public class FeedbackWordFrequency {

    private static final String[] STOP_WORDS = {"the", "was", "and", "a", "is", "of", "in"};

    private static boolean isStopWord(String word) {
        for (String stop : STOP_WORDS) {
            if (stop.equals(word)) {
                return true;
            }
        }
        return false;
    }

    static void printFilteredWordFrequency(String feedback) {
        if (feedback == null || feedback.trim().isEmpty()) {
            System.out.println("(no words to report)");
            return;
        }

        String cleaned = feedback.toLowerCase()
                .replace(".", "")
                .replace(",", "")
                .replace("!", "")
                .replace("?", "")
                .replace(";", "")
                .replace(":", "")
                .trim();

        Map<String, Integer> frequency = new LinkedHashMap<>();
        for (String word : cleaned.split("\\s+")) {
            if (word.isEmpty() || isStopWord(word)) {
                continue;
            }
            frequency.put(word, frequency.getOrDefault(word, 0) + 1);
        }

        List<Map.Entry<String, Integer>> entries = new ArrayList<>(frequency.entrySet());
        entries.sort((a, b) -> b.getValue() - a.getValue()); // List.sort is stable

        for (Map.Entry<String, Integer> entry : entries) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        String[] paragraphs = {
            "The mentor was great, the session was great and clear.",
            "Placement prep is useful. Mock interviews were useful, and the mentors were helpful!"
        };

        for (String paragraph : paragraphs) {
            System.out.println("Input: \"" + paragraph + "\"");
            printFilteredWordFrequency(paragraph);
            System.out.println();
        }
    }
}
