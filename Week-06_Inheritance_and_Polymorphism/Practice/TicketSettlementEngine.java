/**
 * Week 6 Practice - Problem 5 (Advanced):
 * Fest-Wide Ticket Issuance, Promo Codes & Nightly Settlement Engine
 *
 *  - ticketId  : public but FINAL -- readable as t1.ticketId, never reassignable.
 *                It comes from a static counter incremented inside the constructor,
 *                after validation, so only successful tickets consume a number.
 *  - isValidPromoCode : "F" + 3 digits + 1 uppercase letter via charAt(); the length
 *                is checked first so charAt() can never go out of range.
 *  - pay(amount, mode) prints the mode and delegates to pay(amount).
 *  - processNightlySettlement() splits GroupTicket vs individual with instanceof and
 *    skips nulls safely.
 */
public class TicketSettlementEngine {

    static class EventTicket {
        private static int ticketsIssued = 0;
        private static final int ID_BASE = 1000;

        public final String ticketId;
        private double balanceDue;

        public EventTicket(double basePrice) {
            if (basePrice <= 0) {
                throw new IllegalArgumentException("basePrice must be positive");
            }
            ticketsIssued++;
            this.ticketId = "TCK-" + (ID_BASE + ticketsIssued);
            this.balanceDue = basePrice;
        }

        void pay(double amount) {
            if (amount <= 0 || amount > balanceDue) {
                throw new IllegalArgumentException("payment must be between 0 and the balance due");
            }
            balanceDue -= amount;
        }

        void pay(double amount, String mode) {
            System.out.println("Paying via " + mode);
            pay(amount);
        }

        static boolean isValidPromoCode(String code) {
            if (code == null || code.length() != 5) {
                return false;
            }
            if (code.charAt(0) != 'F') {
                return false;
            }
            for (int i = 1; i <= 3; i++) {
                if (!Character.isDigit(code.charAt(i))) {
                    return false;
                }
            }
            return Character.isUpperCase(code.charAt(4));
        }

        static int getTicketsIssued() {
            return ticketsIssued;
        }

        double getBalanceDue() {
            return balanceDue;
        }
    }

    static class GroupTicket extends EventTicket {
        private final int groupSize;

        public GroupTicket(double basePrice, int groupSize) {
            super(basePrice);
            if (groupSize <= 0) {
                throw new IllegalArgumentException("groupSize must be positive");
            }
            this.groupSize = groupSize;
        }

        int getGroupSize() {
            return groupSize;
        }
    }

    static String processNightlySettlement(EventTicket[] tickets) {
        int processed = 0;
        int nullSkipped = 0;
        int group = 0;
        int individual = 0;

        if (tickets != null) {
            for (EventTicket ticket : tickets) {
                if (ticket == null) {
                    nullSkipped++;
                    continue;
                }
                if (ticket instanceof GroupTicket) {
                    GroupTicket g = (GroupTicket) ticket;
                    System.out.println("  " + g.ticketId + " group of " + g.getGroupSize()
                            + " -> outstanding " + g.getBalanceDue());
                    group++;
                } else {
                    System.out.println("  " + ticket.ticketId + " individual -> outstanding " + ticket.getBalanceDue());
                    individual++;
                }
                processed++;
            }
        }
        return processed + " processed | " + nullSkipped + " null skipped | "
                + group + " group | " + individual + " individual";
    }

    public static void main(String[] args) {
        EventTicket t1 = new EventTicket(500);
        System.out.println("t1.ticketId -> \"" + t1.ticketId + "\"");
        System.out.println("EventTicket.getTicketsIssued() -> " + EventTicket.getTicketsIssued());
        // t1.ticketId = "TCK-9999";  // does not compile: ticketId is final
        System.out.println();

        System.out.println("isValidPromoCode(\"F123A\") -> " + EventTicket.isValidPromoCode("F123A"));
        System.out.println("isValidPromoCode(\"F12A\")  -> " + EventTicket.isValidPromoCode("F12A"));
        System.out.println("isValidPromoCode(\"X123A\") -> " + EventTicket.isValidPromoCode("X123A"));
        System.out.println();

        t1.pay(200);
        t1.pay(200, "UPI");
        System.out.println("t1.getBalanceDue() -> " + t1.getBalanceDue());
        System.out.println();

        EventTicket[] nightly = {new GroupTicket(2000, 5), null, new EventTicket(500)};
        System.out.println(processNightlySettlement(nightly));
        System.out.println("Tickets issued so far -> " + EventTicket.getTicketsIssued());
    }
}
