/**
 * Week 6 Assignment - Problem 5 (Advanced):
 * Race-Wide Bib Issuance, Discount Codes & Nightly Settlement Engine
 *
 *  - entryCode : final, built from a static counter inside the base constructor.
 *                Every subclass funnels through super(...), so the counter goes up
 *                exactly once per successful entry. Validation runs BEFORE the
 *                increment, so a rejected construction never consumes a number.
 *  - isValidDiscountCode : "M" + 3 digits + 1 uppercase letter, checked with charAt()
 *                -- length is checked first so charAt() can never go out of range.
 *  - pay(...) is overloaded; the mode-aware version delegates to the flat version.
 *  - settleNight() separates relay teams from individuals with instanceof and
 *    skips null entries without throwing.
 */
public class RaceSettlementEngine {

    static class RaceEntry {
        private static int bibCounter = 0;
        private static final int CODE_BASE = 1000;

        private final String entryCode;
        private final String bibNumber;
        private double balanceDue;

        public RaceEntry(String bibNumber, double entryFee) {
            if (bibNumber == null || bibNumber.trim().length() < 4) {
                throw new IllegalArgumentException("bibNumber must be at least 4 non-blank characters");
            }
            if (entryFee <= 0) {
                throw new IllegalArgumentException("entryFee must be positive");
            }
            bibCounter++;
            this.entryCode = "ENT-" + (CODE_BASE + bibCounter);
            this.bibNumber = bibNumber.trim();
            this.balanceDue = entryFee;
        }

        void pay(double amount) {
            if (amount <= 0 || amount > balanceDue) {
                throw new IllegalArgumentException("payment must be between 0 and the balance due");
            }
            balanceDue -= amount;
        }

        void pay(double amount, String mode) {
            System.out.println("Paying via " + mode);
            pay(amount); // reuse the flat version -- no duplicated logic
        }

        static boolean isValidDiscountCode(String code) {
            if (code == null || code.length() != 5) {
                return false;
            }
            if (code.charAt(0) != 'M') {
                return false;
            }
            for (int i = 1; i <= 3; i++) {
                if (!Character.isDigit(code.charAt(i))) {
                    return false;
                }
            }
            return Character.isUpperCase(code.charAt(4));
        }

        static int getBibCounter() {
            return bibCounter;
        }

        String getEntryCode() {
            return entryCode;
        }

        String getBibNumber() {
            return bibNumber;
        }

        double getBalanceDue() {
            return balanceDue;
        }
    }

    static class RunnerEntry extends RaceEntry {
        private final String category;

        public RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);
            this.category = category;
        }
    }

    static class EliteRunnerEntry extends RunnerEntry {
        private final double sponsorBonus;

        public EliteRunnerEntry(String bibNumber, double entryFee, String category, double sponsorBonus) {
            super(bibNumber, entryFee, category);
            this.sponsorBonus = sponsorBonus;
        }
    }

    static class RelayTeamEntry extends RaceEntry {
        private final int teamSize;

        public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
            super(bibNumber, entryFee);
            if (teamSize <= 0) {
                throw new IllegalArgumentException("teamSize must be positive");
            }
            this.teamSize = teamSize;
        }

        int getTeamSize() {
            return teamSize;
        }
    }

    static String settleNight(RaceEntry[] entries) {
        int processed = 0;
        int nullSkipped = 0;
        int relay = 0;
        int individual = 0;

        if (entries != null) {
            for (RaceEntry entry : entries) {
                if (entry == null) {
                    nullSkipped++;
                    continue;
                }
                if (entry instanceof RelayTeamEntry) {
                    RelayTeamEntry team = (RelayTeamEntry) entry;
                    System.out.println("  " + team.getEntryCode() + " relay team of " + team.getTeamSize()
                            + " -> outstanding " + team.getBalanceDue());
                    relay++;
                } else {
                    System.out.println("  " + entry.getEntryCode() + " individual " + entry.getBibNumber()
                            + " -> outstanding " + entry.getBalanceDue());
                    individual++;
                }
                processed++;
            }
        }
        return processed + " processed | " + nullSkipped + " null skipped | "
                + relay + " relay | " + individual + " individual";
    }

    public static void main(String[] args) {
        System.out.println("isValidDiscountCode(\"M123A\") -> " + RaceEntry.isValidDiscountCode("M123A"));
        System.out.println("isValidDiscountCode(\"M12A\")  -> " + RaceEntry.isValidDiscountCode("M12A"));
        System.out.println("isValidDiscountCode(\"X123A\") -> " + RaceEntry.isValidDiscountCode("X123A"));
        System.out.println();

        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");
        EliteRunnerEntry eliteEntry = new EliteRunnerEntry("BIB3001", 150, "Elite Full Marathon", 500);
        RelayTeamEntry relayEntry = new RelayTeamEntry("BIB4001", 300, 4);
        RaceEntry walkIn = new RaceEntry("BIB5001", 50);

        try {
            new RaceEntry("B1", 50); // rejected -> counter must NOT move
        } catch (IllegalArgumentException e) {
            System.out.println("new RaceEntry(\"B1\", 50) -> construction rejected");
        }

        r.pay(10, "UPI");
        System.out.println(r.getEntryCode() + " balance after UPI payment -> " + r.getBalanceDue());
        System.out.println();

        System.out.println(settleNight(new RaceEntry[]{eliteEntry, null, relayEntry}));
        System.out.println();
        System.out.println("RaceEntry.getBibCounter() -> " + RaceEntry.getBibCounter()
                + " (last code issued: " + walkIn.getEntryCode() + ")");
    }
}
