/**
 * Week 5 - Problem 1 (Basic / Intermediate): Membership Field Reach Checker
 *
 * A small "pre-compile linter" that answers whether a field with a given access
 * modifier can be reached from a given place, following Java's real rules:
 *
 *                     SAME_CLASS   SAME_PACKAGE   DIFFERENT_PACKAGE
 *   private              yes           no               no
 *   default (none)       yes           yes              no
 *   protected            yes           yes              no  (non-subclass)
 *   public               yes           yes              yes
 *
 * Also defines LibraryMember with a deliberate access level for each field and a
 * constructor that refuses a blank or too-short membershipId.
 */
public class MembershipFieldReachChecker {

    static final String ALLOWED = "ALLOWED";
    static final String DENIED = "DENIED";
    private static final String[] MODIFIERS = {"private", "default", "protected", "public"};

    static String classifyAccess(String fieldModifier, String accessorContext) {
        if (fieldModifier == null || accessorContext == null) {
            throw new IllegalArgumentException("modifier and context are required");
        }

        boolean sameClass = accessorContext.equals("SAME_CLASS");
        boolean samePackage = accessorContext.equals("SAME_PACKAGE");
        boolean differentPackage = accessorContext.equals("DIFFERENT_PACKAGE");
        if (!sameClass && !samePackage && !differentPackage) {
            throw new IllegalArgumentException("unknown accessor context: " + accessorContext);
        }

        switch (fieldModifier) {
            case "private":
                return sameClass ? ALLOWED : DENIED;
            case "default":
            case "protected": // without a subclass relationship, protected behaves like default here
                return (sameClass || samePackage) ? ALLOWED : DENIED;
            case "public":
                return ALLOWED;
            default:
                throw new IllegalArgumentException("unknown modifier: " + fieldModifier);
        }
    }

    /** Groups each {modifier, context} attempt by modifier and counts allowed/denied. */
    static String summarizeByModifier(String[][] attempts) {
        int[] allowed = new int[MODIFIERS.length];
        int[] denied = new int[MODIFIERS.length];
        boolean[] seen = new boolean[MODIFIERS.length];

        if (attempts != null) {
            for (String[] attempt : attempts) {
                if (attempt == null || attempt.length < 2) {
                    continue;
                }
                int index = indexOfModifier(attempt[0]);
                if (index < 0) {
                    continue;
                }
                seen[index] = true;
                if (ALLOWED.equals(classifyAccess(attempt[0], attempt[1]))) {
                    allowed[index]++;
                } else {
                    denied[index]++;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < MODIFIERS.length; i++) {
            if (!seen[i]) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append(" | ");
            }
            sb.append(MODIFIERS[i]).append(": ")
              .append(allowed[i]).append(" allowed / ")
              .append(denied[i]).append(" denied");
        }
        return sb.toString();
    }

    private static int indexOfModifier(String modifier) {
        for (int i = 0; i < MODIFIERS.length; i++) {
            if (MODIFIERS[i].equals(modifier)) {
                return i;
            }
        }
        return -1;
    }

    static class LibraryMember {
        private static final int MIN_ID_LENGTH = 4;

        // private   : the ID is identity data -- only LibraryMember itself may touch it.
        private final String membershipId;
        // default   : branch routing is used by other classes in the same library package only.
        String branchCode;
        // protected : subclasses (e.g. premium membership in another package) must adjust fines.
        protected double finesOwed;
        // public    : the display name is shown everywhere, nothing sensitive about it.
        public String displayName;

        // No no-argument constructor exists, so a member can never be built without an ID.
        public LibraryMember(String membershipId, String branchCode, double finesOwed, String displayName) {
            String id = (membershipId == null) ? "" : membershipId.trim();
            if (id.length() < MIN_ID_LENGTH) {
                throw new IllegalArgumentException(
                        "membershipId must be at least " + MIN_ID_LENGTH + " non-blank characters");
            }
            this.membershipId = id;
            this.branchCode = branchCode;
            this.finesOwed = finesOwed;
            this.displayName = displayName;
        }

        public String getMembershipId() {
            return membershipId;
        }
    }

    private static void tryCreate(String id) {
        try {
            LibraryMember m = new LibraryMember(id, "BR1", 0, "Priya Nair");
            System.out.println("new LibraryMember(\"" + id + "\", ...) -> created " + m.getMembershipId());
        } catch (IllegalArgumentException e) {
            System.out.println("new LibraryMember(\"" + id + "\", ...) -> construction rejected");
        }
    }

    public static void main(String[] args) {
        System.out.println("classifyAccess(\"private\", \"SAME_CLASS\") -> "
                + classifyAccess("private", "SAME_CLASS"));
        System.out.println("classifyAccess(\"protected\", \"DIFFERENT_PACKAGE\") -> "
                + classifyAccess("protected", "DIFFERENT_PACKAGE"));

        String[][] attempts = {
            {"private", "SAME_CLASS"},
            {"private", "SAME_PACKAGE"},
            {"default", "SAME_PACKAGE"},
            {"default", "DIFFERENT_PACKAGE"},
            {"protected", "SAME_PACKAGE"},
            {"protected", "SAME_CLASS"},
            {"public", "DIFFERENT_PACKAGE"}
        };
        System.out.println(summarizeByModifier(attempts));

        tryCreate("LB9");
        tryCreate("LB94");
        tryCreate("    ");
    }
}
