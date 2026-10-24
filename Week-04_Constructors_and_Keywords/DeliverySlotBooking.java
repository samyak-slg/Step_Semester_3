/**
 * Week 4 - Problem 2 (Basic): ASAP or Scheduled -- Delivery Slot Booking
 *
 * Two constructors linked with this(...): the one-argument constructor chains to
 * the two-argument one, so all field-setting logic lives in a single place and the
 * literal "ASAP" appears only once in the class.
 */
public class DeliverySlotBooking {

    static class DeliverySlot {
        private static final String ASAP = "ASAP";
        private static final String[] PEAK_SLOTS = {
            "12:00-13:00", "13:00-14:00", "19:00-20:00", "20:00-21:00"
        };

        private final String orderId;
        private final String timeSlot;

        public DeliverySlot(String orderId, String timeSlot) {
            this.orderId = orderId;
            this.timeSlot = timeSlot;
        }

        public DeliverySlot(String orderId) {
            this(orderId, ASAP); // must be the first statement
        }

        boolean isPeakHour() {
            for (String peak : PEAK_SLOTS) {
                if (peak.equals(timeSlot)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return orderId + " @ " + timeSlot;
        }
    }

    public static void main(String[] args) {
        DeliverySlot[] bookings = {
            new DeliverySlot("ORD101", "13:00-14:00"),
            new DeliverySlot("ORD102"),
            new DeliverySlot("ORD103", "16:00-17:00"),
            new DeliverySlot("ORD104", "20:00-21:00")
        };

        for (DeliverySlot slot : bookings) {
            System.out.println(slot + " -> isPeakHour() = " + slot.isPeakHour());
        }
    }
}
