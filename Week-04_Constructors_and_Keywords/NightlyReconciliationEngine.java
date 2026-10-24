/**
 * Week 4 - Problem 5 (Advanced): Nightly Multi-Kitchen Reconciliation Engine
 *
 * Design decisions:
 *  - Surge fee: REUSES Problem 4's tiered model (0.5% / 1% / 2% per minute with a
 *    minimum floor that applies only when delayed). It is a final method, so no
 *    subclass (including PremiumDeliveryAccount) can change how the fee is computed.
 *  - Premium settlement: premium members get PREMIUM_WAIVER (50%) of the surge fee
 *    waived. This is decided by the processor with instanceof, not by overriding.
 *  - Null entries: skipped and counted -- one bad entry never crashes the run.
 *  - Mismatched array lengths: the WHOLE batch is rejected before anything runs.
 *    If the arrays do not line up, we cannot know which amount belongs to which
 *    student. Failing loudly up front is far safer than quietly charging the wrong
 *    amount to the wrong student, which would then have to be found and reversed.
 *
 * Complexity: a single pass, O(n) time, O(1) extra space (a few running counters).
 */
public class NightlyReconciliationEngine {

    static class DeliveryAccount {
        static final String CAMPUS_NAME;
        static final double MIN_SURGE_PERCENT;
        static final double PREMIUM_WAIVER;

        // One-time, class-level setup runs once when the class is first loaded.
        static {
            CAMPUS_NAME = "Campus Food Delivery";
            MIN_SURGE_PERCENT = 1.0;
            PREMIUM_WAIVER = 0.5;
        }

        private final String studentId;
        private double orderValue;

        public DeliveryAccount(String studentId, double orderValue) {
            if (studentId == null || studentId.trim().isEmpty()) {
                throw new IllegalArgumentException("studentId is required");
            }
            if (orderValue < 0) {
                throw new IllegalArgumentException("orderValue cannot be negative");
            }
            this.studentId = studentId.trim();
            this.orderValue = orderValue;
        }

        /** Provisional account: order value is filled in when the night's batch is processed. */
        public DeliveryAccount(String studentId) {
            this(studentId, 0.0);
        }

        void recordOrderValue(double amount) {
            if (amount < 0) {
                throw new IllegalArgumentException("amount cannot be negative");
            }
            this.orderValue = amount;
        }

        final double calculateSurgeFee(int delayMinutes) {
            if (delayMinutes < 0) {
                throw new IllegalArgumentException("delayMinutes cannot be negative");
            }
            if (delayMinutes == 0) {
                return 0.0;
            }
            int tier1 = Math.min(delayMinutes, 5);
            int tier2 = Math.max(0, Math.min(delayMinutes, 15) - 5);
            int tier3 = Math.max(0, delayMinutes - 15);
            double percent = tier1 * 0.5 + tier2 * 1.0 + tier3 * 2.0;

            double tiered = orderValue * percent / 100.0;
            double floor = orderValue * MIN_SURGE_PERCENT / 100.0;
            return Math.max(tiered, floor);
        }

        String getStudentId() {
            return studentId;
        }
    }

    static class PremiumDeliveryAccount extends DeliveryAccount {
        public PremiumDeliveryAccount(String studentId, double orderValue) {
            super(studentId, orderValue);
        }

        public PremiumDeliveryAccount(String studentId) {
            super(studentId);
        }
    }

    // ---- The processor: holds the running counters for one nightly run ----
    private int processed;
    private int nullSkipped;
    private int premiumCount;
    private int regularCount;
    private int failed;
    private double grandTotal;

    void processAccount(DeliveryAccount account, double amount, int delayMinutes) {
        if (account == null) {
            nullSkipped++;
            return;
        }
        try {
            account.recordOrderValue(amount);
            double fee = account.calculateSurgeFee(delayMinutes);

            String type;
            if (account instanceof PremiumDeliveryAccount) {
                fee = fee * (1 - DeliveryAccount.PREMIUM_WAIVER);
                premiumCount++;
                type = "premium";
            } else {
                regularCount++;
                type = "regular";
            }

            fee = Math.round(fee * 100.0) / 100.0;
            grandTotal += fee;
            processed++;
            System.out.printf(java.util.Locale.US, "  %s (%s): order Rs %.1f, delay %d min -> surge Rs %.2f%n",
                    account.getStudentId(), type, amount, delayMinutes, fee);
        } catch (IllegalArgumentException e) {
            failed++;
            System.out.println("  " + account.getStudentId() + " not processed: " + e.getMessage());
        }
    }

    String summary() {
        String result = processed + " processed | " + nullSkipped + " null skipped | "
                + premiumCount + " premium | " + regularCount + " regular | grand total surge fees = Rs "
                + String.format(java.util.Locale.US, "%.2f", grandTotal);
        if (failed > 0) {
            result += " | " + failed + " failed validation";
        }
        return result;
    }

    static void processBatch(DeliveryAccount[] accounts, double[] amounts, int[] delayMinutesArray) {
        if (accounts == null || amounts == null || delayMinutesArray == null) {
            System.out.println("Batch rejected: input arrays must not be null");
            return;
        }
        if (accounts.length != amounts.length || accounts.length != delayMinutesArray.length) {
            System.out.println("Batch rejected: array lengths differ (accounts=" + accounts.length
                    + ", amounts=" + amounts.length + ", delays=" + delayMinutesArray.length
                    + "). Nothing was processed.");
            return;
        }

        System.out.println(DeliveryAccount.CAMPUS_NAME + " -- nightly reconciliation");
        NightlyReconciliationEngine engine = new NightlyReconciliationEngine();
        for (int i = 0; i < accounts.length; i++) {
            engine.processAccount(accounts[i], amounts[i], delayMinutesArray[i]);
        }
        System.out.println(engine.summary());
    }

    public static void main(String[] args) {
        DeliveryAccount[] accounts = {
            new PremiumDeliveryAccount("STU001", 500),
            null,
            new DeliveryAccount("STU002", 300)
        };
        double[] amounts = {500, 400, 300};
        int[] delays = {10, 5, 0};
        processBatch(accounts, amounts, delays);

        System.out.println();
        // A provisional account plus a bad (negative) amount -- still no crash.
        DeliveryAccount[] secondRun = {
            new DeliveryAccount("STU003"),
            new PremiumDeliveryAccount("STU004"),
            new DeliveryAccount("STU005")
        };
        processBatch(secondRun, new double[]{250, 800, -50}, new int[]{20, 3, 4});

        System.out.println();
        // Mismatched lengths -> rejected up front.
        processBatch(accounts, new double[]{500, 400}, delays);
    }
}
