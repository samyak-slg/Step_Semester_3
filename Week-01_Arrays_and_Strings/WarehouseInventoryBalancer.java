/**
 * Week 1 - Problem 4: The Warehouse Inventory Balancer
 *
 * Checks whether Section A and Section B hold the same total quantity and finds
 * the single highest-quantity item across the whole warehouse.
 *
 * Approach : One loop over both arrays accumulates both totals. The maximum is
 *            tracked with its section and index; Section A is scanned first and a
 *            strictly-greater comparison is used, so the first occurrence wins a tie.
 * Time     : O(n)    Space: O(1)
 */
public class WarehouseInventoryBalancer {

    static void analyzeInventory(int[] sectionA, int[] sectionB) {
        if (sectionA == null || sectionB == null || sectionA.length != sectionB.length || sectionA.length == 0) {
            System.out.println("Invalid input: both sections must be non-empty and of equal length");
            return;
        }

        int totalA = 0;
        int totalB = 0;
        for (int i = 0; i < sectionA.length; i++) {
            totalA += sectionA[i];
            totalB += sectionB[i];
        }

        int highest = sectionA[0];
        String highestSection = "A";
        int highestIndex = 0;

        for (int i = 0; i < sectionA.length; i++) {
            if (sectionA[i] > highest) {
                highest = sectionA[i];
                highestSection = "A";
                highestIndex = i;
            }
        }
        for (int i = 0; i < sectionB.length; i++) {
            if (sectionB[i] > highest) {
                highest = sectionB[i];
                highestSection = "B";
                highestIndex = i;
            }
        }

        String status = (totalA == totalB) ? "Balanced" : "Not Balanced";

        System.out.println("Section A Total: " + totalA
                + " | Section B Total: " + totalB
                + " | Status: " + status
                + " | Highest Quantity: " + highest
                + " (Section " + highestSection + ", Item " + (highestIndex + 1) + ")");
    }

    public static void main(String[] args) {
        analyzeInventory(new int[]{20, 15, 30}, new int[]{25, 10, 30});
        analyzeInventory(new int[]{12, 40, 8}, new int[]{10, 15, 55});
    }
}
