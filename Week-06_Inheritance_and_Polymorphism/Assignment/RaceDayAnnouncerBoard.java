/**
 * Week 6 Assignment - Problem 4 (Intermediate / Advanced): The Race-Day Announcer Board
 *
 *  - announceAll() calls announce() polymorphically -- no if-else chain on types.
 *  - One StringBuilder is reused across the whole loop.
 *  - The relay-only detail (team size) is reached through a downcast that is
 *    ALWAYS guarded by instanceof first, so a ClassCastException can never happen.
 */
public class RaceDayAnnouncerBoard {

    static class RaceEntry {
        private final String bibNumber;
        private double balanceDue;
        private final double[] lateFeeHistory = new double[10];
        private int lateFeeCount;

        public RaceEntry(String bibNumber, double entryFee) {
            if (bibNumber == null || bibNumber.trim().length() < 4) {
                throw new IllegalArgumentException("bibNumber must be at least 4 non-blank characters");
            }
            if (entryFee <= 0) {
                throw new IllegalArgumentException("entryFee must be positive");
            }
            this.bibNumber = bibNumber.trim();
            this.balanceDue = entryFee;
        }

        void pay(double amount) {
            if (amount <= 0 || amount > balanceDue) {
                throw new IllegalArgumentException("payment must be between 0 and the balance due");
            }
            balanceDue -= amount;
        }

        protected void applyLateFee(double amount) {
            if (amount <= 0 || lateFeeCount == lateFeeHistory.length) {
                throw new IllegalArgumentException("late fee rejected");
            }
            balanceDue += amount;
            lateFeeHistory[lateFeeCount++] = amount;
        }

        double getBalanceDue() {
            return balanceDue;
        }

        String getBibNumber() {
            return bibNumber;
        }

        String announce() {
            return "Race Entry | Bib: " + bibNumber + " | Balance: " + balanceDue;
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
            super.applyLateFee(amount * 2);
        }

        @Override
        String announce() {
            return "Runner Entry | Bib: " + getBibNumber() + " | Category: " + category
                    + " | Balance: " + getBalanceDue();
        }
    }

    static class RelayTeamEntry extends RaceEntry {
        private final int teamSize;

        public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
            super(bibNumber, entryFee);
            this.teamSize = teamSize;
        }

        int getTeamSize() {
            return teamSize;
        }

        @Override
        String announce() {
            return "Relay Team | Bib: " + getBibNumber() + " | Team Size: " + teamSize
                    + " | Balance: " + getBalanceDue();
        }
    }

    static String announceAll(RaceEntry[] entries) {
        StringBuilder report = new StringBuilder();
        for (RaceEntry entry : entries) {
            report.append(entry.announce()); // polymorphic call

            if (entry instanceof RelayTeamEntry) {
                RelayTeamEntry relay = (RelayTeamEntry) entry; // safe: guarded by instanceof
                report.append(" [Team size via downcast: ").append(relay.getTeamSize()).append(']');
            }
            report.append(" | ");
        }
        return report.toString();
    }

    public static void main(String[] args) {
        RunnerEntry runnerEntry = new RunnerEntry("BIB2001", 80, "Open 10K");
        runnerEntry.pay(30);
        runnerEntry.applyLateFee(20); // doubled to 40 -> balance 90.0
        RelayTeamEntry relayEntry = new RelayTeamEntry("BIB4001", 300, 4);

        RaceEntry[] fleet = {runnerEntry, relayEntry};
        System.out.println("\"" + announceAll(fleet) + "\"");

        // Why the guard matters: an unguarded downcast compiles, but fails at runtime.
        RaceEntry plain = new RaceEntry("BIB5001", 50);
        try {
            RelayTeamEntry bad = (RelayTeamEntry) plain;
            System.out.println(bad.getTeamSize());
        } catch (ClassCastException e) {
            System.out.println("Unguarded cast -> ClassCastException at runtime");
        }
    }
}
