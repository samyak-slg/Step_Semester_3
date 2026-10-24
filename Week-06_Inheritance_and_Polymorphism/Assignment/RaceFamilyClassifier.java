/**
 * Week 6 Assignment - Problem 2 (Intermediate): Three Shapes of One Race Family
 *
 *            RaceEntry
 *            /       \
 *     RunnerEntry   RelayTeamEntry      <- hierarchical (siblings)
 *          |
 *   EliteRunnerEntry                     <- multilevel (3 generations deep)
 *
 *  - classifyGeneration() relies on instanceof only (no "type" field anywhere).
 *    The most specific type is checked first, because an EliteRunnerEntry is ALSO
 *    a RunnerEntry and a RaceEntry.
 *  - getTotalBalanceDue() never checks types: polymorphism runs each object's own
 *    getBalanceDue().
 */
public class RaceFamilyClassifier {

    static class RaceEntry {
        private final String bibNumber;
        private final double entryFee;
        private double balanceDue;

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

        double getBalanceDue() {
            return balanceDue;
        }

        String getBibNumber() {
            return bibNumber;
        }

        String announce() {
            return "Race Entry | Bib: " + bibNumber + " | Balance: " + getBalanceDue();
        }
    }

    static class RunnerEntry extends RaceEntry {
        private final String category;

        public RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);
            this.category = category;
        }

        String getCategory() {
            return category;
        }

        @Override
        String announce() {
            return "Runner Entry | Bib: " + getBibNumber() + " | Category: " + category
                    + " | Balance: " + getBalanceDue();
        }
    }

    static class EliteRunnerEntry extends RunnerEntry {
        private final double sponsorBonus;

        public EliteRunnerEntry(String bibNumber, double entryFee, String category, double sponsorBonus) {
            super(bibNumber, entryFee, category);
            this.sponsorBonus = sponsorBonus;
        }

        @Override
        String announce() {
            return "Elite Runner | Bib: " + getBibNumber() + " | Category: " + getCategory()
                    + " | Sponsor Bonus: " + sponsorBonus + " | Balance: " + getBalanceDue();
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

        @Override
        String announce() {
            return "Relay Team | Bib: " + getBibNumber() + " | Team Size: " + teamSize
                    + " | Balance: " + getBalanceDue();
        }
    }

    static String classifyGeneration(RaceEntry entry) {
        if (entry == null) {
            return "No entry";
        }
        if (entry instanceof EliteRunnerEntry) {
            return "Multilevel descendant (3 generations deep)";
        }
        if (entry instanceof RelayTeamEntry) {
            return "Hierarchical sibling (independent branch)";
        }
        if (entry instanceof RunnerEntry) {
            return "Single-inheritance child (2 generations deep)";
        }
        return "Base entry (root of the family)";
    }

    static double getTotalBalanceDue(RaceEntry[] entries) {
        double total = 0;
        if (entries == null) {
            return total;
        }
        for (RaceEntry entry : entries) {
            if (entry != null) {
                total += entry.getBalanceDue(); // dynamic dispatch -- no type checks
            }
        }
        return total;
    }

    public static void main(String[] args) {
        RunnerEntry runnerEntry = new RunnerEntry("BIB2001", 80, "Open 10K");
        EliteRunnerEntry eliteEntry = new EliteRunnerEntry("BIB3001", 150, "Elite Full Marathon", 500);
        RelayTeamEntry relayEntry = new RelayTeamEntry("BIB4001", 300, 4);

        System.out.println(runnerEntry.announce());
        System.out.println(eliteEntry.announce());
        System.out.println(relayEntry.announce());

        System.out.println();
        System.out.println("classifyGeneration(eliteEntry)  -> " + classifyGeneration(eliteEntry));
        System.out.println("classifyGeneration(relayEntry)  -> " + classifyGeneration(relayEntry));
        System.out.println("classifyGeneration(runnerEntry) -> " + classifyGeneration(runnerEntry));

        System.out.println();
        RaceEntry[] field = {runnerEntry, eliteEntry, relayEntry};
        System.out.println("getTotalBalanceDue -> " + getTotalBalanceDue(field));
    }
}
