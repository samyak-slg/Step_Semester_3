/**
 * Week 6 Practice - Problem 1 (Basic / Intermediate):
 * Ticket Hierarchy Foundation & Batch Registration Validator
 *
 *  - EventTicket is the base class and the ONLY place the attendeeId rule lives.
 *  - WorkshopTicket extends EventTicket (single inheritance) and forwards the shared
 *    fields with super(...).
 *  - registerBatch() just tries to construct each ticket and counts the
 *    IllegalArgumentExceptions -- it never pre-validates the strings itself.
 */
public class TicketBatchRegistration {

    static class EventTicket {
        private static final int MIN_ID_LENGTH = 4;

        private final String attendeeId;
        private final double basePrice;
        private double balanceDue;

        public EventTicket(String attendeeId, double basePrice) {
            if (attendeeId == null || attendeeId.trim().length() < MIN_ID_LENGTH) {
                throw new IllegalArgumentException("attendeeId must be at least " + MIN_ID_LENGTH + " non-blank characters");
            }
            if (basePrice <= 0) {
                throw new IllegalArgumentException("basePrice must be positive");
            }
            this.attendeeId = attendeeId.trim();
            this.basePrice = basePrice;
            this.balanceDue = basePrice;
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

        String getAttendeeId() {
            return attendeeId;
        }

        double getBasePrice() {
            return basePrice;
        }

        static String registerBatch(String[] attendeeIds, double basePrice) {
            int registered = 0;
            int rejected = 0;
            if (attendeeIds != null) {
                for (String id : attendeeIds) {
                    try {
                        new EventTicket(id, basePrice);
                        registered++;
                    } catch (IllegalArgumentException e) {
                        rejected++;
                    }
                }
            }
            return "Registered: " + registered + " | Rejected: " + rejected;
        }
    }

    static class WorkshopTicket extends EventTicket {
        private final String track;

        public WorkshopTicket(String attendeeId, double basePrice, String track) {
            super(attendeeId, basePrice);
            this.track = track;
        }

        String getTrack() {
            return track;
        }
    }

    public static void main(String[] args) {
        try {
            new EventTicket("ST1", 500);
        } catch (IllegalArgumentException e) {
            System.out.println("new EventTicket(\"ST1\", 500) -> construction rejected");
        }

        WorkshopTicket w = new WorkshopTicket("STU2", 1200, "AI/ML");
        w.pay(500);
        System.out.println("WorkshopTicket balance after paying 500 -> " + w.getBalanceDue());

        System.out.println(EventTicket.registerBatch(new String[]{"STU1", "ST1", "STU2", " ", "STU3"}, 500));
    }
}
