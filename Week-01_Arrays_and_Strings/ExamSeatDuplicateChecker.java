/**
 * Week 1 - Problem 1: The Exam Hall Seat Duplication Checker
 *
 * Scans the list of assigned seat numbers and flags every seat number that was
 * given to more than one student. Uses only arrays and nested loops (no Collections).
 *
 * Approach : For each seat i, check whether it already appeared before i (so each
 *            duplicate is reported only once), then check whether it appears again after i.
 * Time     : O(n^2)    Space: O(1)
 */
public class ExamSeatDuplicateChecker {

    static void checkDuplicateSeats(int[] seatNumbers) {
        if (seatNumbers == null || seatNumbers.length == 0) {
            System.out.println("No seat numbers provided");
            return;
        }

        boolean foundAny = false;

        for (int i = 0; i < seatNumbers.length; i++) {
            // Skip seats we have already reported, e.g. {102, 102, 102} prints 102 once.
            boolean alreadyReported = false;
            for (int j = 0; j < i; j++) {
                if (seatNumbers[j] == seatNumbers[i]) {
                    alreadyReported = true;
                    break;
                }
            }
            if (alreadyReported) {
                continue;
            }

            for (int k = i + 1; k < seatNumbers.length; k++) {
                if (seatNumbers[k] == seatNumbers[i]) {
                    System.out.println("Duplicate Seat Number Found: " + seatNumbers[i]);
                    foundAny = true;
                    break;
                }
            }
        }

        if (!foundAny) {
            System.out.println("No Duplicate Seats Found");
        }
    }

    public static void main(String[] args) {
        int[][] testCases = {
            {101, 102, 103, 102, 105},
            {101, 102, 103, 104, 105},
            {201, 202, 201, 203, 202, 201}
        };

        for (int[] seats : testCases) {
            System.out.println("Input: " + java.util.Arrays.toString(seats));
            checkDuplicateSeats(seats);
            System.out.println();
        }
    }
}
