/**
 * Week 3 - F4: Designing the Instance/Static Boundary for a Library Membership System
 *
 * Part 1 reproduces the bug where every field is static (all members share one
 * copy of the data). Part 2 is the corrected design.
 */
public class LibraryMembershipSystem {

    // ------------------------------------------------------------------
    // BROKEN VERSION -- every field is static
    // ------------------------------------------------------------------
    static class BrokenLibraryMember {
        /*
         * Why each static field is wrong here:
         *  - name        : every member has their own name. A static field exists only
         *                  ONCE for the whole class, so the 2nd member's name replaces the 1st.
         *  - memberId    : an ID must uniquely identify ONE member. One shared static ID
         *                  means all members end up with the same (latest) ID.
         *  - booksIssued : each member borrows a different number of books. A shared
         *                  counter mixes everyone's borrowing into one number.
         */
        static String name;
        static String memberId;
        static int booksIssued;

        BrokenLibraryMember(String name, String memberId, int booksIssued) {
            BrokenLibraryMember.name = name;
            BrokenLibraryMember.memberId = memberId;
            BrokenLibraryMember.booksIssued = booksIssued;
        }

        String getName() {
            return name;
        }
    }

    // ------------------------------------------------------------------
    // FIXED VERSION -- correct instance/static split
    // ------------------------------------------------------------------
    static class LibraryMember {
        // Shared by the whole library -> static
        static String libraryName = "City Central Library";
        static int memberCount = 0;

        // Different for every member -> instance
        private final String name;
        private final String memberId;
        private int booksIssued;

        LibraryMember(String name, int booksIssued) {
            memberCount++;
            this.name = name;
            this.memberId = "LM-" + (1000 + memberCount); // derived automatically
            this.booksIssued = booksIssued;
        }

        void printMemberCard() {
            System.out.println(name + " | " + memberId);
        }

        static void printTotalMembers() {
            System.out.println("Total members: " + memberCount);
        }

        int getBooksIssued() {
            return booksIssued;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Broken version (all fields static) ===");
        BrokenLibraryMember aditiBroken = new BrokenLibraryMember("Aditi", "LM-1001", 2);
        BrokenLibraryMember rohanBroken = new BrokenLibraryMember("Rohan", "LM-1002", 5);
        System.out.println(aditiBroken.getName());
        System.out.println(rohanBroken.getName());
        System.out.println("(Aditi's data was overwritten -- both members now show \"Rohan\")");

        System.out.println();
        System.out.println("=== Fixed version (instance + static split) ===");
        LibraryMember aditi = new LibraryMember("Aditi", 2);
        LibraryMember rohan = new LibraryMember("Rohan", 5);
        aditi.printMemberCard();
        rohan.printMemberCard();
        LibraryMember.printTotalMembers();
    }
}
