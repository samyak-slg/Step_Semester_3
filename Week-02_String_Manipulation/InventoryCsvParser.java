/**
 * Week 2 - Problem 3: Product Inventory CSV Parser
 *
 * Splits an inventory CSV line "ProductName,SKU,Quantity" into its fields and
 * prints a formatted record, rejecting lines that do not have exactly 3 fields.
 *
 * Approach : split(",", -1) so trailing empty fields are kept and counted
 *            (e.g. "Mouse,WM-1," is rejected instead of silently passing).
 * Time     : O(n)    Space: O(n)
 */
public class InventoryCsvParser {

    static void parseInventoryRecord(String csvLine) {
        if (csvLine == null) {
            System.out.println("Invalid Record");
            return;
        }

        String[] fields = csvLine.split(",", -1);
        if (fields.length != 3) {
            System.out.println("Invalid Record");
            return;
        }

        for (String field : fields) {
            if (field.trim().isEmpty()) {
                System.out.println("Invalid Record");
                return;
            }
        }

        System.out.println("Product: " + fields[0].trim()
                + " | SKU: " + fields[1].trim()
                + " | Qty: " + fields[2].trim());
    }

    public static void main(String[] args) {
        String[] lines = {
            "Wireless Mouse,WM-2201,150",
            "Wireless Mouse,150",
            "USB-C Hub,UC-7710,42",
            "Keyboard,KB-1001,"
        };

        for (String line : lines) {
            System.out.print("Input: \"" + line + "\" -> ");
            parseInventoryRecord(line);
        }
    }
}
