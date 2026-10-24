/**
 * Week 6 Practice - Problem 4 (Intermediate / Advanced): The Nightly Ticket Announcer
 *
 *  - batchPrint() calls printTicket() polymorphically -- no if-else chain on types.
 *  - A single StringBuilder collects the whole report.
 *  - The workshop-only detail (track) is reached with a downcast that is always
 *    guarded by instanceof, so it can never throw ClassCastException.
 */
public class NightlyTicketAnnouncer {

    static class EventTicket {
        static final String WALK_IN_ID = "WALK-IN";

        private final String attendeeId;
        private double balanceDue;

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

        double getBalanceDue() {
            return balanceDue;
        }

        String printTicket() {
            return "Standard | Balance: " + getBalanceDue();
        }
    }

    static class WorkshopTicket extends EventTicket {
        private final String track;

        public WorkshopTicket(double basePrice, String track) {
            super(basePrice);
            this.track = track;
        }

        String getTrack() {
            return track;
        }

        @Override
        String printTicket() {
            return "Workshop | Track: " + track + " | Balance: " + getBalanceDue();
        }
    }

    static String batchPrint(EventTicket[] tickets) {
        StringBuilder report = new StringBuilder();
        for (EventTicket ticket : tickets) {
            report.append(ticket.printTicket()); // polymorphic call

            if (ticket instanceof WorkshopTicket) {
                WorkshopTicket workshop = (WorkshopTicket) ticket; // safe: guarded
                report.append(" [Track via downcast: ").append(workshop.getTrack()).append(']');
            }
            report.append(" | ");
        }
        return report.toString();
    }

    public static void main(String[] args) {
        EventTicket[] tickets = {
            new EventTicket(500),
            new WorkshopTicket(1200, "AI/ML")
        };
        System.out.println("\"" + batchPrint(tickets) + "\"");

        // An unguarded downcast compiles, but fails when it runs.
        EventTicket plain = new EventTicket(500);
        try {
            WorkshopTicket bad = (WorkshopTicket) plain;
            System.out.println(bad.getTrack());
        } catch (ClassCastException e) {
            System.out.println("Unguarded cast -> ClassCastException at runtime");
        }
    }
}
