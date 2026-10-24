/**
 * Week 3 - F1: From Procedural Mess to a Working Library Fine System
 *
 * Replaces five sets of parallel variables with one BookIssue class. Each object
 * knows its own fine and overdue status; a static method totals the fines across
 * many books.
 */
public class LibraryFineSystem {

    static class BookIssue {
        private static final double FINE_PER_DAY = 5.0;
        private static final int SEVERE_THRESHOLD_DAYS = 14;

        private final String title;
        private final String borrowerName;
        private final int daysOverdue;

        BookIssue(String title, String borrowerName, int daysOverdue) {
            this.title = title;
            this.borrowerName = borrowerName;
            this.daysOverdue = daysOverdue;
        }

        /*
         * Why fineAmount() is an INSTANCE method:
         *   The fine depends on the data of ONE particular book (its own daysOverdue).
         *   It only makes sense when called on a specific BookIssue object.
         *
         * Why totalFineCollected() is a STATIC method:
         *   It does not belong to any single book -- it works across a whole array of
         *   books and needs no "this" object. It is a class-level utility, so it is
         *   called through the class name: BookIssue.totalFineCollected(issues).
         */
        double fineAmount() {
            return daysOverdue > 0 ? daysOverdue * FINE_PER_DAY : 0;
        }

        boolean isSeverelyOverdue() {
            return daysOverdue > SEVERE_THRESHOLD_DAYS;
        }

        static double totalFineCollected(BookIssue[] issues) {
            double total = 0;
            if (issues == null) {
                return total;
            }
            for (BookIssue issue : issues) {
                if (issue != null) {
                    total += issue.fineAmount();
                }
            }
            return total;
        }

        String getTitle() {
            return title;
        }

        String getBorrowerName() {
            return borrowerName;
        }

        int getDaysOverdue() {
            return daysOverdue;
        }
    }

    public static void main(String[] args) {
        BookIssue[] issues = {
            new BookIssue("Clean Code", "Aarav", 18),
            new BookIssue("Effective Java", "Isha", 5),
            new BookIssue("Refactoring", "Kabir", 0),
            new BookIssue("DSA Handbook", "Neha", 21),
            new BookIssue("Design Patterns", "Vikram", 9)
        };

        for (BookIssue issue : issues) {
            String status = issue.isSeverelyOverdue() ? "Severely overdue" : "OK";
            System.out.println(issue.getTitle() + " - " + issue.getDaysOverdue() + " days - " + status);
        }

        System.out.println("Total fine collected: Rs " + BookIssue.totalFineCollected(issues));
    }
}
