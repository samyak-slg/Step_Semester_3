/**
 * Week 2 - Problem 4: Library ISBN Normalizer & Validator
 *
 * A valid code is exactly 13 characters: 3 letters (publisher) + 4 digits (year)
 * + 6 digits (catalog number). Raw scans may contain stray spaces or a
 * lower/mixed-case publisher code.
 *
 * Stage 1 (normalize): trim(), then uppercase only the first 3 characters using
 *                      substring() + concatenation.
 * Stage 2 (validate) : length check, letter check, digit check -- each with its own
 *                      error message. No regular expressions are used.
 * Stage 3 (format)   : build "[PUB] YEAR: yyyy | CATALOG: cccccc" with StringBuilder.
 *
 * Time: O(n)    Space: O(n)
 */
public class LibraryIsbnValidator {

    private static final int CODE_LENGTH = 13;
    private static final int PUBLISHER_LENGTH = 3;
    private static final int YEAR_END = 7; // characters 3..6 hold the year

    static String normalizeCode(String raw) {
        if (raw == null) {
            return "";
        }
        String trimmed = raw.trim();
        if (trimmed.length() <= PUBLISHER_LENGTH) {
            return trimmed.toUpperCase();
        }
        return trimmed.substring(0, PUBLISHER_LENGTH).toUpperCase() + trimmed.substring(PUBLISHER_LENGTH);
    }

    static String validateAndFormat(String code) {
        if (code == null || code.length() != CODE_LENGTH) {
            int found = (code == null) ? 0 : code.length();
            return "Invalid: code must be exactly 13 characters (found " + found + ")";
        }

        for (int i = 0; i < PUBLISHER_LENGTH; i++) {
            if (!Character.isLetter(code.charAt(i))) {
                return "Invalid: publisher code must be 3 letters";
            }
        }

        for (int i = PUBLISHER_LENGTH; i < CODE_LENGTH; i++) {
            if (!Character.isDigit(code.charAt(i))) {
                return "Invalid: body must be 10 digits (non-digit '" + code.charAt(i) + "' at position " + (i + 1) + ")";
            }
        }

        StringBuilder display = new StringBuilder();
        display.append('[').append(code, 0, PUBLISHER_LENGTH).append(']')
               .append(" YEAR: ").append(code, PUBLISHER_LENGTH, YEAR_END)
               .append(" | CATALOG: ").append(code, YEAR_END, CODE_LENGTH);
        return display.toString();
    }

    public static void main(String[] args) {
        String[] rawCodes = {
            " pen2026004251 ",
            "12N2026004251",
            "oRe2025123",
            "HAR20241234X6"
        };

        for (String raw : rawCodes) {
            String normalized = normalizeCode(raw);
            System.out.println("Input: \"" + raw + "\" -> " + validateAndFormat(normalized));
        }
    }
}
