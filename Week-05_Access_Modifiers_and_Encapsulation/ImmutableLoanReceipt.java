/**
 * Week 5 - Problem 5 (Advanced): Immutable Loan Receipt & Nightly Circulation Ledger
 *
 * Immutability checklist:
 *  - every field is private and final
 *  - bookIds is defensively copied IN (constructor) and OUT (getter), independently
 *  - no setters; "correcting" a receipt returns a brand-new object (wither pattern)
 *  - nobody outside can create another subclass that breaks these guarantees
 *
 * Note on "the class itself final": the problem also requires a
 * ReferenceOnlyLoanReceipt variant that is processed alongside regular receipts in a
 * LoanReceipt[] array, and a final class cannot have ANY subclass. So LoanReceipt
 * is declared "sealed ... permits ReferenceOnlyLoanReceipt" (Java 17+), and
 * ReferenceOnlyLoanReceipt is final. This keeps the same lock as "final" -- no
 * other class can ever extend it -- while allowing exactly the one variant needed.
 */
public class ImmutableLoanReceipt {

    static sealed class LoanReceipt permits ReferenceOnlyLoanReceipt {
        static final String BOOK_ID_PREFIX;
        static final int BOOK_ID_DIGITS;
        static final int MAX_BOOKS;

        // One-time shared configuration, set up when the class is first loaded.
        static {
            BOOK_ID_PREFIX = "BK-";
            BOOK_ID_DIGITS = 3;
            MAX_BOOKS = 20;
        }

        private final String memberId;
        private final String[] bookIds;

        public LoanReceipt(String memberId, String[] bookIds) {
            if (memberId == null || memberId.trim().isEmpty()) {
                throw new IllegalArgumentException("memberId is required");
            }
            if (bookIds == null || bookIds.length == 0 || bookIds.length > MAX_BOOKS) {
                throw new IllegalArgumentException("a receipt needs 1 to " + MAX_BOOKS + " book IDs");
            }
            // Copy FIRST, then validate the copy, so the caller cannot change the
            // array between our check and our store.
            String[] copy = bookIds.clone();
            for (String id : copy) {
                if (!isValidBookId(id)) {
                    throw new IllegalArgumentException("invalid book ID: " + id);
                }
            }
            this.memberId = memberId.trim();
            this.bookIds = copy;
        }

        /** "BK-" followed by exactly 3 digits, checked character by character. */
        static boolean isValidBookId(String id) {
            if (id == null || id.length() != BOOK_ID_PREFIX.length() + BOOK_ID_DIGITS) {
                return false;
            }
            if (!id.startsWith(BOOK_ID_PREFIX)) {
                return false;
            }
            for (int i = BOOK_ID_PREFIX.length(); i < id.length(); i++) {
                if (!Character.isDigit(id.charAt(i))) {
                    return false;
                }
            }
            return true;
        }

        public String getMemberId() {
            return memberId;
        }

        public String[] getBookIds() {
            return bookIds.clone(); // the caller gets a copy, never our internal array
        }

        public int getBookCount() {
            return bookIds.length;
        }

        public LoanReceipt withCorrectedBookId(int index, String newId) {
            return new LoanReceipt(memberId, correctedIds(index, newId));
        }

        protected final String[] correctedIds(int index, String newId) {
            if (index < 0 || index >= bookIds.length) {
                throw new IndexOutOfBoundsException("no book at index " + index);
            }
            String[] corrected = bookIds.clone();
            corrected[index] = newId; // validated again by the constructor
            return corrected;
        }

        @Override
        public String toString() {
            return "LoanReceipt{" + memberId + ", " + String.join(",", bookIds) + "}";
        }
    }

    static final class ReferenceOnlyLoanReceipt extends LoanReceipt {
        private final String roomNumber;

        public ReferenceOnlyLoanReceipt(String memberId, String[] bookIds, String roomNumber) {
            super(memberId, bookIds);
            if (roomNumber == null || roomNumber.trim().isEmpty()) {
                throw new IllegalArgumentException("roomNumber is required");
            }
            this.roomNumber = roomNumber.trim();
        }

        public String getRoomNumber() {
            return roomNumber;
        }

        @Override
        public ReferenceOnlyLoanReceipt withCorrectedBookId(int index, String newId) {
            return new ReferenceOnlyLoanReceipt(getMemberId(), correctedIds(index, newId), roomNumber);
        }

        @Override
        public String toString() {
            return "ReferenceOnlyLoanReceipt{" + getMemberId() + ", " + String.join(",", getBookIds())
                    + ", room=" + roomNumber + "}";
        }
    }

    static String processNightlyCirculation(LoanReceipt[] receipts) {
        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        if (receipts != null) {
            for (LoanReceipt receipt : receipts) {
                if (receipt == null) {
                    nullSkipped++;
                    continue;
                }
                if (receipt instanceof ReferenceOnlyLoanReceipt) {
                    ReferenceOnlyLoanReceipt ref = (ReferenceOnlyLoanReceipt) receipt;
                    System.out.println("  " + ref.getMemberId() + ": " + ref.getBookCount()
                            + " book(s) for in-library use in " + ref.getRoomNumber() + " -- reshelve at closing");
                    referenceOnly++;
                } else {
                    System.out.println("  " + receipt.getMemberId() + ": " + receipt.getBookCount()
                            + " book(s) checked out for home loan");
                    regular++;
                }
                processed++;
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | "
                + referenceOnly + " reference-only | " + regular + " regular";
    }

    public static void main(String[] args) {
        try {
            new LoanReceipt("LIB-8841", new String[]{"BK-100", "bad"});
        } catch (IllegalArgumentException e) {
            System.out.println("new LoanReceipt(..., {\"BK-100\", \"bad\"}) -> construction rejected");
        }

        // Defensive copy on the way OUT
        LoanReceipt r = new LoanReceipt("LIB-8841", new String[]{"BK-100", "BK-101"});
        String[] ids = r.getBookIds();
        ids[0] = "HACKED";
        System.out.println("r.getBookIds()[0] -> \"" + r.getBookIds()[0] + "\"");

        // Defensive copy on the way IN
        String[] source = {"BK-300"};
        LoanReceipt r2 = new LoanReceipt("LIB-0007", source);
        source[0] = "HACKED";
        System.out.println("After changing the caller's array -> " + r2);

        // Wither pattern: a correction returns a new object, the original is untouched
        LoanReceipt corrected = r.withCorrectedBookId(1, "BK-999");
        System.out.println("Original : " + r);
        System.out.println("Corrected: " + corrected);

        System.out.println();
        LoanReceipt[] nightly = {
            new ReferenceOnlyLoanReceipt("LIB-001", new String[]{"BK-200"}, "Reading Room 3"),
            null,
            new LoanReceipt("LIB-002", new String[]{"BK-201"})
        };
        System.out.println(processNightlyCirculation(nightly));
    }
}
