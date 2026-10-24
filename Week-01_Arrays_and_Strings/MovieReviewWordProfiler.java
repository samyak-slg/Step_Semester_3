/**
 * Week 1 - Problem 5: The Movie Review Word Length Profiler
 *
 * Splits a review into words and counts how many are Short (1-4 letters),
 * Medium (5-8 letters) or Long (9+ letters), to help moderators spot spam.
 *
 * Approach : split("\\s+") on the trimmed review, count only the letters in each
 *            word (so "great!" is 5 letters, not 6) and bucket it.
 * Time     : O(n)    Space: O(w) for the array of words
 */
public class MovieReviewWordProfiler {

    static void classifyWordLengths(String review) {
        if (review == null || review.trim().isEmpty()) {
            System.out.println("Short: 0 | Medium: 0 | Long: 0");
            return;
        }

        String[] words = review.trim().split("\\s+");
        int shortCount = 0;
        int mediumCount = 0;
        int longCount = 0;

        for (String word : words) {
            int letters = 0;
            for (int i = 0; i < word.length(); i++) {
                if (Character.isLetter(word.charAt(i))) {
                    letters++;
                }
            }

            if (letters == 0) {
                continue; // tokens like "-" or "!!!" are not words
            } else if (letters <= 4) {
                shortCount++;
            } else if (letters <= 8) {
                mediumCount++;
            } else {
                longCount++;
            }
        }

        System.out.println("Short: " + shortCount + " | Medium: " + mediumCount + " | Long: " + longCount);
    }

    public static void main(String[] args) {
        String[] reviews = {
            "This movie was absolutely fantastic and thrilling",
            "Great acting, but the storyline was predictable!",
            "   "
        };

        for (String review : reviews) {
            System.out.println("Review: \"" + review + "\"");
            classifyWordLengths(review);
            System.out.println();
        }
    }
}
