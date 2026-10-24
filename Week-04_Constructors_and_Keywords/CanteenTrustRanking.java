/**
 * Week 4 - Problem 3 (Intermediate): Canteen Trust-Score Ranking Engine
 *
 * Ordering rule (decided BEFORE writing compareTo):
 *   1. Higher trustScore ranks first.
 *   2. Tie -> canteenCode compared case-INSENSITIVELY, A to Z
 *      ("hb1-c" and "HB1-C" are treated as the same code for ordering only;
 *       the stored/displayed code keeps its original case).
 *   3. Still tied -> shorter canteenName first.
 *   4. Still tied -> canteenName alphabetically, so the order is always total
 *      and two runs on the same input can never disagree.
 *
 * rankCanteens() uses a hand-written insertion sort (stable, O(n^2)) and returns a
 * new array, leaving the caller's array untouched.
 */
public class CanteenTrustRanking {

    static class Canteen implements Comparable<Canteen> {
        private static final int DEFAULT_TRUST_SCORE = 3;

        private final String canteenCode;
        private final String canteenName;
        private final int trustScore;

        public Canteen(String canteenCode, String canteenName, int trustScore) {
            // "this." resolves the clash between the fields and the parameters
            this.canteenCode = canteenCode;
            this.canteenName = canteenName;
            this.trustScore = trustScore;
        }

        public Canteen(String canteenCode, String canteenName) {
            this(canteenCode, canteenName, DEFAULT_TRUST_SCORE);
        }

        /**
         * Negative -> this canteen ranks BEFORE other; positive -> after; 0 -> same rank.
         */
        @Override
        public int compareTo(Canteen other) {
            if (this.trustScore != other.trustScore) {
                return Integer.compare(other.trustScore, this.trustScore); // descending
            }
            int byCode = this.canteenCode.compareToIgnoreCase(other.canteenCode);
            if (byCode != 0) {
                return byCode;
            }
            int byNameLength = Integer.compare(this.canteenName.length(), other.canteenName.length());
            if (byNameLength != 0) {
                return byNameLength;
            }
            return this.canteenName.compareTo(other.canteenName);
        }

        static Canteen[] rankCanteens(Canteen[] canteens) {
            if (canteens == null) {
                return new Canteen[0];
            }
            Canteen[] ranked = canteens.clone();

            // Insertion sort -- no built-in sort utility used.
            for (int i = 1; i < ranked.length; i++) {
                Canteen key = ranked[i];
                int j = i - 1;
                while (j >= 0 && ranked[j].compareTo(key) > 0) {
                    ranked[j + 1] = ranked[j];
                    j--;
                }
                ranked[j + 1] = key;
            }
            return ranked;
        }

        String getCanteenCode() {
            return canteenCode;
        }

        @Override
        public String toString() {
            return canteenCode + " (" + canteenName + ", " + trustScore + "/5)";
        }
    }

    private static String codesOf(Canteen[] canteens) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < canteens.length; i++) {
            sb.append('"').append(canteens[i].getCanteenCode()).append('"');
            if (i < canteens.length - 1) {
                sb.append(", ");
            }
        }
        return sb.append(']').toString();
    }

    public static void main(String[] args) {
        Canteen[] canteens = {
            new Canteen("HB3-C", "Spice Junction", 3),
            new Canteen("hb1-c", "Grand Mess", 5),
            new Canteen("HB2-C", "Southern Treats") // defaults to trustScore 3
        };

        Canteen[] ranked = Canteen.rankCanteens(canteens);
        System.out.println(codesOf(ranked));

        for (int i = 0; i < ranked.length; i++) {
            System.out.println((i + 1) + ". " + ranked[i]);
        }

        // Running the ranking again on the same input gives exactly the same order.
        System.out.println("Stable across refreshes: "
                + codesOf(ranked).equals(codesOf(Canteen.rankCanteens(canteens))));
    }
}
