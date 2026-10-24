/**
 * Week 3 - F3: Object References, Null Safety, and a Mutating Method
 *
 * The allocation service must never crash when every slot is full.
 * findAvailableSlot() returns null in that case, and safeAllot() always checks
 * for null before using the result.
 */
public class ParkingAllocation {

    static class ParkingSlot {
        private final String slotNo;
        private final int capacity;
        private int occupiedCount;

        ParkingSlot(String slotNo, int capacity, int occupiedCount) {
            this.slotNo = slotNo;
            this.capacity = capacity;
            this.occupiedCount = occupiedCount;
        }

        boolean hasSpace() {
            return occupiedCount < capacity;
        }

        /** Fills one spot if free. Returns true when the vehicle was parked. */
        boolean allot(String vehicleNo) {
            if (!hasSpace()) {
                return false;
            }
            occupiedCount++;
            return true;
        }

        String getSlotNo() {
            return slotNo;
        }

        @Override
        public String toString() {
            return slotNo + " (" + occupiedCount + "/" + capacity + ")";
        }
    }

    /*
     * Why passing the array does NOT copy the slots:
     *   In Java, a ParkingSlot[] variable holds a REFERENCE to an array, and each
     *   element is itself a reference to a ParkingSlot object. When the array is
     *   passed to a method, only the reference is copied (pass-by-value of the
     *   reference). The method therefore sees the very same ParkingSlot objects as
     *   the caller, so allot() changes occupiedCount on the caller's objects too --
     *   which is exactly why A1 shows 4/4 after the first call in main().
     */
    static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {
        if (slots == null) {
            return null;
        }
        for (ParkingSlot slot : slots) {
            if (slot != null && slot.hasSpace()) {
                return slot;
            }
        }
        return null;
    }

    static void safeAllot(ParkingSlot[] slots, String vehicleNo) {
        ParkingSlot slot = findAvailableSlot(slots);
        if (slot == null) {
            System.out.println("No slots available for " + vehicleNo);
            return;
        }
        slot.allot(vehicleNo);
        System.out.println(vehicleNo + " allotted to slot " + slot.getSlotNo());
    }

    private static void printSlots(ParkingSlot[] slots) {
        StringBuilder sb = new StringBuilder("Slots: ");
        for (int i = 0; i < slots.length; i++) {
            sb.append(slots[i]);
            if (i < slots.length - 1) {
                sb.append(", ");
            }
        }
        System.out.println(sb);
    }

    public static void main(String[] args) {
        ParkingSlot[] slots = {
            new ParkingSlot("A1", 4, 3),
            new ParkingSlot("A2", 5, 5)
        };

        // Path 1: a slot is free.
        printSlots(slots);
        safeAllot(slots, "TN09AB1234");

        // Path 2: the same array -- now every slot is full (A1 was filled by the call above).
        printSlots(slots);
        safeAllot(slots, "TN09AB1234");

        // Extra safety checks: null array and array of nulls never crash.
        safeAllot(null, "KA01XY0001");
        safeAllot(new ParkingSlot[]{null, null}, "KA01XY0002");
    }
}
