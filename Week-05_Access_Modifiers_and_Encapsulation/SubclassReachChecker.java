/**
 * Week 5 - Problem 2 (Intermediate): Reference Desk Subclass Reach
 *
 * Extends the access checker with Java's subtle protected rule for a subclass in a
 * DIFFERENT package (JLS 6.6.2):
 *
 *   class PremiumMember extends LibraryMember {        // in another package
 *       void demo(PremiumMember own, LibraryMember parent) {
 *           own.finesOwed    = 0;  // OK      - accessed through the subclass's own type
 *           parent.finesOwed = 0;  // ERROR   - accessed through the parent type
 *       }
 *   }
 *
 * The compiler decides using the DECLARED (compile-time) type of the reference,
 * not the runtime type of the object -- even if "parent" really points to a
 * PremiumMember at runtime, the access is still rejected.
 */
public class SubclassReachChecker {

    static final String ALLOWED = "ALLOWED";
    static final String DENIED = "DENIED";

    static String classifyAccess(String fieldModifier, String accessorContext) {
        if (fieldModifier == null || accessorContext == null) {
            throw new IllegalArgumentException("modifier and context are required");
        }

        switch (accessorContext) {
            case "SAME_CLASS":
                return ALLOWED; // every modifier is visible inside its own class
            case "SAME_PACKAGE":
                return fieldModifier.equals("private") ? DENIED : ALLOWED;
            case "DIFFERENT_PACKAGE":
                return fieldModifier.equals("public") ? ALLOWED : DENIED;
            case "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE":
                return (fieldModifier.equals("public") || fieldModifier.equals("protected")) ? ALLOWED : DENIED;
            case "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE":
                // protected does NOT help here: the reference's declared type is the parent.
                return fieldModifier.equals("public") ? ALLOWED : DENIED;
            default:
                throw new IllegalArgumentException("unknown accessor context: " + accessorContext);
        }
    }

    /** "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE" -> "Subclass Different Package Own Type" */
    static String describeContext(String accessorContext) {
        if (accessorContext == null || accessorContext.trim().isEmpty()) {
            return "";
        }
        String[] parts = accessorContext.trim().split("_");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (part.isEmpty()) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append(' ');
            }
            sb.append(Character.toUpperCase(part.charAt(0)))
              .append(part.substring(1).toLowerCase());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String[] modifiers = {"private", "default", "protected", "public"};
        String[] contexts = {
            "SAME_CLASS",
            "SAME_PACKAGE",
            "DIFFERENT_PACKAGE",
            "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE",
            "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"
        };

        System.out.println("classifyAccess(\"protected\", \"SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE\") -> "
                + classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));
        System.out.println("classifyAccess(\"protected\", \"SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE\") -> "
                + classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));
        System.out.println("describeContext(\"SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE\") -> \""
                + describeContext("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE") + "\"");

        System.out.println();
        System.out.println("Full access table:");
        for (String context : contexts) {
            StringBuilder row = new StringBuilder(String.format("  %-40s",describeContext(context)));
            for (String modifier : modifiers) {
                row.append(String.format("%-10s: %-8s", modifier, classifyAccess(modifier, context)));
            }
            System.out.println(row);
        }
    }
}
