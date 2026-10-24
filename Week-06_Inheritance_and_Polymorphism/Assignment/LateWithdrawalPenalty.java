import java.util.Arrays;

/**
 * Week 6 Assignment - Problem 3 (Intermediate):
 * The Late-Withdrawal Penalty Override & Audit Trail
 *
 *  - RaceEntry.applyLateFee() adds the penalty to the balance AND records it in a
 *    private history array. That logic exists in exactly one place.
 *  - RunnerEntry overrides it with @Override and simply calls
 *    super.applyLateFee(amount * 2) -- reusing both the charge and the recording.
 *  - getLateFeeHistory() returns a fresh copy every time, so callers can never
 *    tamper with the real audit trail.
 */
public class LateWithdrawalPenalty {

    static class RaceEntry {
        private static final int MAX_LATE_FEES = 10;

        private final String bibNumber;
        private final double entryFee;
        private double balanceDue;
        private final double[] lateFeeHistory = new double[MAX_LATE_FEES];
        private int lateFeeCount;

        public RaceEntry(String bibNumber, double entryFee) {
            if (bibNumber == null || bibNumber.trim().length() < 4) {
                throw new IllegalArgumentException("bibNumber must be at least 4 non-blank characters");
            }
            if (entryFee <= 0) {
                throw new IllegalArgumentException("entryFee must be positive");
            }
            this.bibNumber = bibNumber.trim();
            this.entryFee = entryFee;
            this.balanceDue = entryFee;
        }

        void pay(double amount) {
            if (amount <= 0 || amount > balanceDue) {
                throw new IllegalArgumentException("payment must be between 0 and the balance due");
            }
            balanceDue -= amount;
        }

        protected void applyLateFee(double amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("late fee must be positive");
            }
            if (lateFeeCount == MAX_LATE_FEES) {
                throw new IllegalStateException("late-fee history is full (max " + MAX_LATE_FEES + ")");
            }
            balanceDue += amount;
            lateFeeHistory[lateFeeCount++] = amount;
        }

        double[] getLateFeeHistory() {
            return Arrays.copyOf(lateFeeHistory, lateFeeCount); // defensive copy
        }

        double getBalanceDue() {
            return balanceDue;
        }

        String getBibNumber() {
            return bibNumber;
        }
    }

    static class RunnerEntry extends RaceEntry {
        private final String category;

        public RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);
            this.category = category;
        }

        @Override
        protected void applyLateFee(double amount) {
            super.applyLateFee(amount * 2); // runners pay double; parent does the work
        }
    }

    public static void main(String[] args) {
        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");
        r.pay(30);
        r.applyLateFee(20);
        System.out.println("Balance due -> " + r.getBalanceDue());

        double[] history = r.getLateFeeHistory();
        System.out.println("History     -> " + Arrays.toString(history));
        history[0] = 999; // tampering with the copy
        System.out.println("After edit  -> " + Arrays.toString(r.getLateFeeHistory()));

        RaceEntry plain = new RaceEntry("BIB5001", 50);
        plain.applyLateFee(20); // base class: no doubling
        System.out.println("Plain entry -> balance " + plain.getBalanceDue()
                + ", history " + Arrays.toString(plain.getLateFeeHistory()));
    }
}
