/**
 * Week 6 Assignment - Problem 1 (Basic / Intermediate):
 * Race Entry Foundation & Batch Bib Validator
 *
 *  - RaceEntry is the base class and the ONLY place the bib validation rule lives.
 *  - RunnerEntry extends RaceEntry (single inheritance) and forwards the shared
 *    fields with super(...) instead of re-declaring them.
 *  - registerBatch() never pre-checks the strings: it just tries to build each entry
 *    and counts the IllegalArgumentExceptions as rejections.
 */
public class RaceEntryBatchValidator {

    static class RaceEntry {
        private static final int MIN_BIB_LENGTH = 4;

        private final String bibNumber;
        private final double entryFee;
        private double balanceDue;

        public RaceEntry(String bibNumber, double entryFee) {
            if (bibNumber == null || bibNumber.trim().length() < MIN_BIB_LENGTH) {
                throw new IllegalArgumentException("bibNumber must be at least " + MIN_BIB_LENGTH + " non-blank characters");
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

        double getBalanceDue() {
            return balanceDue;
        }

        String getBibNumber() {
            return bibNumber;
        }

        double getEntryFee() {
            return entryFee;
        }

        static String registerBatch(String[] bibNumbers, double entryFee) {
            int registered = 0;
            int rejected = 0;
            if (bibNumbers != null) {
                for (String bib : bibNumbers) {
                    try {
                        new RaceEntry(bib, entryFee);
                        registered++;
                    } catch (IllegalArgumentException e) {
                        rejected++;
                    }
                }
            }
            return "Registered: " + registered + " | Rejected: " + rejected;
        }
    }

    static class RunnerEntry extends RaceEntry {
        private final String category;

        public RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee); // shared fields live in RaceEntry only
            this.category = category;
        }

        String getCategory() {
            return category;
        }
    }

    public static void main(String[] args) {
        try {
            new RaceEntry("B1", 50);
        } catch (IllegalArgumentException e) {
            System.out.println("new RaceEntry(\"B1\", 50) -> construction rejected");
        }

        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");
        r.pay(30);
        System.out.println("RunnerEntry balance after paying 30 -> " + r.getBalanceDue());

        System.out.println(RaceEntry.registerBatch(new String[]{"BIB1", "B1", "BIB2"}, 80));
        System.out.println(RaceEntry.registerBatch(new String[]{"BIB10", "    ", null, "BIB11"}, 80));
    }
}
