/**
 * Week 4 - Problem 4 (Intermediate): Exam-Week Surge Fee Calculator
 *
 * Tiered (marginal) surge rates on the DELAY minutes, like income-tax slabs:
 *   minutes  1-5  : 0.5% of order value per minute
 *   minutes  6-15 : 1%   of order value per minute
 *   minutes 16+   : 2%   of order value per minute
 * The configured minimumSurgePercent is a floor that applies only when the order is
 * actually delayed (delayMinutes > 0). An on-time order is always Rs 0.
 *
 * Where "final" is used and why:
 *   - final class SurgeFeeCalculator : nobody can subclass it and change the pricing.
 *   - final field minimumSurgePercent: the floor is fixed once the calculator is built.
 *   - final method calculateSurgeFee : the rule itself cannot be overridden. (Inside a
 *     final class this is already implied; it is kept to match the required signature
 *     and to make the intent explicit to readers.)
 *
 * Complexity: O(1) closed form -- no minute-by-minute loop.
 */
public class SurgeFeeCalculatorDemo {

    static final class SurgeFeeCalculator {
        private static final int TIER1_END = 5;
        private static final int TIER2_END = 15;
        // Rates in "percent per minute": 0.5, 1 and 2 are exact in binary floating point.
        private static final double TIER1_RATE = 0.5;
        private static final double TIER2_RATE = 1.0;
        private static final double TIER3_RATE = 2.0;

        private final double minimumSurgePercent;

        public SurgeFeeCalculator(double minimumSurgePercent) {
            if (minimumSurgePercent < 0) {
                throw new IllegalArgumentException("minimumSurgePercent cannot be negative");
            }
            this.minimumSurgePercent = minimumSurgePercent;
        }

        final double calculateSurgeFee(double orderValue, int delayMinutes) {
            if (orderValue < 0) {
                throw new IllegalArgumentException("orderValue cannot be negative");
            }
            if (delayMinutes < 0) {
                throw new IllegalArgumentException("delayMinutes cannot be negative");
            }
            if (delayMinutes == 0) {
                return 0.0; // on time: the floor never applies
            }

            int tier1Minutes = Math.min(delayMinutes, TIER1_END);
            int tier2Minutes = Math.max(0, Math.min(delayMinutes, TIER2_END) - TIER1_END);
            int tier3Minutes = Math.max(0, delayMinutes - TIER2_END);

            double totalPercent = tier1Minutes * TIER1_RATE
                    + tier2Minutes * TIER2_RATE
                    + tier3Minutes * TIER3_RATE;

            double tieredFee = orderValue * totalPercent / 100.0;
            double floorFee = orderValue * minimumSurgePercent / 100.0;

            return round2(Math.max(tieredFee, floorFee));
        }

        private static double round2(double value) {
            return Math.round(value * 100.0) / 100.0;
        }
    }

    public static void main(String[] args) {
        SurgeFeeCalculator calculator = new SurgeFeeCalculator(1.0); // 1% floor

        int[] delays = {0, 1, 5, 6, 15, 16};
        for (int delay : delays) {
            System.out.println("orderValue = 500, delayMinutes = " + delay
                    + " -> Rs " + calculator.calculateSurgeFee(500, delay));
        }

        // Negative inputs are rejected at calculation time.
        try {
            calculator.calculateSurgeFee(-100, 3);
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected: " + e.getMessage());
        }
        try {
            calculator.calculateSurgeFee(500, -2);
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected: " + e.getMessage());
        }
    }
}
