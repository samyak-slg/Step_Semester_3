/**
 * Week 6 Practice - Problem 2 (Intermediate): Three Shapes of One Family Tree
 *
 *              EventTicket
 *              /         \
 *     WorkshopTicket   HackathonTicket       <- hierarchical (siblings)
 *            |
 *   PremiumWorkshopTicket                    <- multilevel (3 generations deep)
 *
 *  - classifyGeneration() uses instanceof only, most specific type first.
 *  - getTotalBalanceDue() trusts polymorphism: no type checks inside the loop.
 */
public class TicketFamilyClassifier {

    static class EventTicket {
        private final String attendeeId;
        private final double basePrice;
        private double balanceDue;

        public EventTicket(String attendeeId, double basePrice) {
            if (attendeeId == null || attendeeId.trim().length() < 4) {
                throw new IllegalArgumentException("attendeeId must be at least 4 non-blank characters");
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

        String printTicket() {
            return "Standard Event Ticket | Balance Due: " + getBalanceDue();
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

        @Override
        String printTicket() {
            return "Workshop Ticket | Track: " + track + " | Balance Due: " + getBalanceDue();
        }
    }

    static class PremiumWorkshopTicket extends WorkshopTicket {
        private final double kitFee;

        public PremiumWorkshopTicket(String attendeeId, double basePrice, String track, double kitFee) {
            super(attendeeId, basePrice, track);
            this.kitFee = kitFee;
        }

        @Override
        String printTicket() {
            return "Premium Workshop Ticket | Track: " + getTrack() + " | Kit Fee: " + kitFee
                    + " | Balance Due: " + getBalanceDue();
        }
    }

    static class HackathonTicket extends EventTicket {
        private final String teamName;

        public HackathonTicket(String attendeeId, double basePrice, String teamName) {
            super(attendeeId, basePrice);
            this.teamName = teamName;
        }

        @Override
        String printTicket() {
            return "Hackathon Ticket | Team: " + teamName + " | Balance Due: " + getBalanceDue();
        }
    }

    static String classifyGeneration(EventTicket ticket) {
        if (ticket == null) {
            return "No ticket";
        }
        if (ticket instanceof PremiumWorkshopTicket) {
            return "Multilevel descendant (3 generations deep)";
        }
        if (ticket instanceof HackathonTicket) {
            return "Hierarchical sibling (independent branch)";
        }
        if (ticket instanceof WorkshopTicket) {
            return "Single-inheritance child (2 generations deep)";
        }
        return "Base ticket (root of the family)";
    }

    static double getTotalBalanceDue(EventTicket[] tickets) {
        double total = 0;
        if (tickets == null) {
            return total;
        }
        for (EventTicket ticket : tickets) {
            if (ticket != null) {
                total += ticket.getBalanceDue();
            }
        }
        return total;
    }

    public static void main(String[] args) {
        EventTicket standardTicket = new EventTicket("STU1", 500);
        WorkshopTicket workshopTicket = new WorkshopTicket("STU2", 1200, "AI/ML");
        PremiumWorkshopTicket premiumTicket = new PremiumWorkshopTicket("STU3", 2000, "Cloud Native", 300);
        HackathonTicket hackathonTicket = new HackathonTicket("STU4", 800, "Byte Force");

        EventTicket[] all = {standardTicket, workshopTicket, premiumTicket, hackathonTicket};
        for (EventTicket ticket : all) {
            System.out.println(ticket.printTicket());
        }

        System.out.println();
        System.out.println("classifyGeneration(premiumTicket)   -> " + classifyGeneration(premiumTicket));
        System.out.println("classifyGeneration(hackathonTicket) -> " + classifyGeneration(hackathonTicket));
        System.out.println("classifyGeneration(workshopTicket)  -> " + classifyGeneration(workshopTicket));

        System.out.println();
        System.out.println("getTotalBalanceDue -> " + getTotalBalanceDue(all));
    }
}
