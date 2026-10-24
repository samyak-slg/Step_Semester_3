/**
 * Week 2 - Problem 1: ATM PIN Length Validator
 *
 * Checks that an entered PIN is exactly 4 characters long, using nothing more
 * than length() and a single if / else (no loop needed).
 *
 * Time: O(1)    Space: O(1)
 */
public class AtmPinLengthValidator {

    static void checkPinLength(String pin) {
        if (pin == null || pin.length() != 4) {
            System.out.println("Invalid PIN -- must be exactly 4 digits.");
        } else {
            System.out.println("PIN length OK.");
        }
    }

    public static void main(String[] args) {
        String[] pins = {"482", "4820", "48201", ""};

        for (String pin : pins) {
            System.out.print("Input: \"" + pin + "\" -> ");
            checkPinLength(pin);
        }
    }
}
