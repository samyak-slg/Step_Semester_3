/**
 * Week 4 - Problem 1 (Basic): Ghost Order Validator
 *
 * A FoodOrder can never exist with a missing student name or dish name: the only
 * constructor validates both fields and throws if either is null/blank/whitespace.
 * processBatch() feeds raw attempts through that constructor and counts results.
 */
public class GhostOrderValidator {

    static class FoodOrder {
        private final String studentName;
        private final String dishName;
        private boolean delivered;

        // There is deliberately NO no-argument constructor. Because we declare a
        // parameterized one, Java does not generate a default constructor either.
        public FoodOrder(String studentName, String dishName) {
            if (isBlank(studentName)) {
                throw new IllegalArgumentException("student name is missing");
            }
            if (isBlank(dishName)) {
                throw new IllegalArgumentException("dish name is missing");
            }
            this.studentName = studentName.trim();
            this.dishName = dishName.trim();
        }

        private static boolean isBlank(String value) {
            // trim() first so "   " is treated exactly like ""
            return value == null || value.trim().isEmpty();
        }

        void markDelivered() {
            if (delivered) {
                System.out.println("WARNING: " + dishName + " for " + studentName
                        + " was already marked delivered -- possible double-serve!");
                return;
            }
            delivered = true;
            System.out.println(dishName + " delivered to " + studentName);
        }

        static void processBatch(String[][] rawOrders) {
            int valid = 0;
            int rejected = 0;

            if (rawOrders != null) {
                for (int i = 0; i < rawOrders.length; i++) {
                    String[] raw = rawOrders[i];
                    String student = (raw != null && raw.length > 0) ? raw[0] : null;
                    String dish = (raw != null && raw.length > 1) ? raw[1] : null;
                    try {
                        new FoodOrder(student, dish);
                        valid++;
                    } catch (IllegalArgumentException e) {
                        rejected++;
                        System.out.println("  Entry " + (i + 1) + " rejected: " + e.getMessage());
                    }
                }
            }

            System.out.println("Valid: " + valid + " | Rejected: " + rejected);
        }
    }

    public static void main(String[] args) {
        String[][] rawOrders = {
            {"Ravi", "Paneer Butter Masala"},
            {"", "Chole Bhature"},
            {"Meera", " "},
            {"Divya", "Veg Biryani"}
        };
        FoodOrder.processBatch(rawOrders);

        System.out.println();
        FoodOrder order = new FoodOrder("Ravi", "Paneer Butter Masala");
        order.markDelivered();
        order.markDelivered(); // second call must be flagged
    }
}
