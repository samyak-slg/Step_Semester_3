import java.util.Arrays;

/**
 * Week 6 Practice - Problem 3 (Intermediate):
 * The Late-Registration Penalty Override & Audit Trail
 *
 *  - EventTicket.applyLateFee() charges the penalty and records it in a private array.
 *  - WorkshopTicket overrides it (@Override) and only calls
 *    super.applyLateFee(amount * 2) -- no second copy of the logic.
 *  - getLateFeeHistory() always returns a fresh copy of the history.
 */
public class LateRegistrationPenalty {

    static class EventTicket {
        static final String WALK_IN_ID = "WALK-IN";
        private static final int MAX_LATE_FEES = 10;

        private final String attendeeId;
        private double balanceDue;
        private final double[] lateFeeHistory = new double[MAX_LATE_FEES];
        private int lateFeeCount;

        public EventTicket(String attendeeId, double basePrice) {
            if (attendeeId == null || attendeeId.trim().length() < 4) {
                throw new IllegalArgumentException("attendeeId must be at least 4 non-blank characters");
            }
            if (basePrice <= 0) {
                throw new IllegalArgumentException("basePrice must be positive");
            }
            this.attendeeId = attendeeId.trim();
            this.balanceDue = basePrice;
        }

        public EventTicket(double basePrice) {
            this(WALK_IN_ID, basePrice);
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
            return Arrays.copyOf(lateFeeHistory, lateFeeCount);
        }

        double getBalanceDue() {
            return balanceDue;
        }
    }

    static class WorkshopTicket extends EventTicket {
        private static final String DEFAULT_TRACK = "General";

        private final String track;

        public WorkshopTicket(String attendeeId, double basePrice, String track) {
            super(attendeeId, basePrice);
            this.track = track;
        }

        public WorkshopTicket(double basePrice) {
            this(WALK_IN_ID, basePrice, DEFAULT_TRACK);
        }

        @Override
        protected void applyLateFee(double amount) {
            super.applyLateFee(amount * 2);
        }
    }

    public static void main(String[] args) {
        WorkshopTicket w = new WorkshopTicket(1200);
        w.pay(1200);
        w.applyLateFee(100);
        System.out.println("Balance due -> " + w.getBalanceDue());

        double[] history = w.getLateFeeHistory();
        System.out.println("History     -> " + Arrays.toString(history));
        history[0] = 999;
        System.out.println("After edit  -> " + Arrays.toString(w.getLateFeeHistory()));

        EventTicket standard = new EventTicket(500);
        standard.applyLateFee(100); // base class: not doubled
        System.out.println("Standard    -> balance " + standard.getBalanceDue()
                + ", history " + Arrays.toString(standard.getLateFeeHistory()));
    }
}
