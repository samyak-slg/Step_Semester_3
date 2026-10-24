/**
 * Week 5 - Problem 3 (Intermediate): Book Copy Circulation Guard
 *
 * Invariant that holds at EVERY moment:   0 <= copiesAvailable <= copiesTotal
 *
 * Both counters are private, there is no setter for copiesAvailable, and the only
 * way to change it is through checkOut()/checkIn(), which silently refuse any move
 * that would break the invariant.
 */
public class BookCirculationGuard {

    static class BookInventory {
        private final int copiesTotal;
        private int copiesAvailable;

        BookInventory(int copiesTotal) {
            if (copiesTotal <= 0) {
                throw new IllegalArgumentException("copiesTotal must be positive");
            }
            this.copiesTotal = copiesTotal;
            this.copiesAvailable = copiesTotal;
        }

        void checkOut() {
            if (copiesAvailable > 0) {
                copiesAvailable--;
            }
        }

        void checkIn() {
            if (copiesAvailable < copiesTotal) {
                copiesAvailable++;
            }
        }

        int getCopiesAvailable() {
            return copiesAvailable;
        }

        int getCopiesTotal() {
            return copiesTotal;
        }
    }

    public static void main(String[] args) {
        for (int total : new int[]{0, -3}) {
            try {
                new BookInventory(total);
            } catch (IllegalArgumentException e) {
                System.out.println("new BookInventory(" + total + ") -> construction rejected");
            }
        }

        BookInventory b = new BookInventory(3);
        b.checkOut();
        b.checkOut();
        b.checkOut();
        b.checkOut(); // nothing left -- ignored
        System.out.println("After 4 check-outs: " + b.getCopiesAvailable());

        b.checkIn();
        b.checkIn();
        b.checkIn();
        b.checkIn(); // already full -- ignored
        System.out.println("After 4 check-ins : " + b.getCopiesAvailable());
    }
}
