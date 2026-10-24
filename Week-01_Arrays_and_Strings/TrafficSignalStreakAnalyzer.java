/**
 * Week 1 - Problem 3: The Traffic Signal Streak Analyzer
 *
 * Finds the longest run of the same signal colour ('R', 'Y', 'G') in a day's log,
 * so engineers know which signal may be stuck.
 *
 * Approach : Single pass. Track the length of the current streak and keep a
 *            running maximum (colour + length). On a tie, the earliest streak wins.
 * Time     : O(n)    Space: O(1)
 */
public class TrafficSignalStreakAnalyzer {

    static void findLongestStreak(String signalLog) {
        if (signalLog == null || signalLog.isEmpty()) {
            System.out.println("Signal log is empty");
            return;
        }

        char bestColor = signalLog.charAt(0);
        int bestLength = 1;
        int currentLength = 1;

        for (int i = 1; i < signalLog.length(); i++) {
            if (signalLog.charAt(i) == signalLog.charAt(i - 1)) {
                currentLength++;
            } else {
                currentLength = 1;
            }

            if (currentLength > bestLength) {
                bestLength = currentLength;
                bestColor = signalLog.charAt(i);
            }
        }

        System.out.println("Longest Streak: '" + bestColor + "' repeated " + bestLength + " times");
    }

    public static void main(String[] args) {
        String[] logs = {"RRGGGYRR", "RRRRYYGG", "GYRGYR", "YYYYYYY"};

        for (String log : logs) {
            System.out.print("Input: \"" + log + "\" -> ");
            findLongestStreak(log);
        }
    }
}
