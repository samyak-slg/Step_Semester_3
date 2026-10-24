import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Week 5 - Problem 4 (Intermediate / Advanced):
 * LibraryMember JavaBean, Chained Constructors & Security Answer
 *
 *  - Three constructors chained with this(...) into ONE real initialization path.
 *  - JavaBean naming: getX()/setX(), and isX() for the boolean property.
 *  - membershipId is write-once: the first non-blank value sticks forever.
 *  - securityAnswer is write-only: only a one-way SHA-256 hash is stored, and there
 *    is no getter for it (or for the hash) anywhere. toString() never prints it.
 */
public class LibraryMemberBean {

    static class LibraryMember implements Serializable {
        private static final long serialVersionUID = 1L;

        private String membershipId;
        private String name;
        private boolean premiumMember;
        private String securityAnswerHash; // write-only: no getter exists

        public LibraryMember() {
            this((String) null);
        }

        public LibraryMember(String name) {
            this(null, name);
        }

        public LibraryMember(String membershipId, String name) {
            this.name = name;
            setMembershipId(membershipId);
        }

        public String getMembershipId() {
            return membershipId;
        }

        /** Takes effect only once, ever. Later calls are silently ignored. */
        public void setMembershipId(String id) {
            if (this.membershipId != null || id == null || id.trim().isEmpty()) {
                return;
            }
            this.membershipId = id.trim();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isPremiumMember() {
            return premiumMember;
        }

        public void setPremiumMember(boolean premium) {
            this.premiumMember = premium;
        }

        public void setSecurityAnswer(String answer) {
            this.securityAnswerHash = (answer == null) ? null : sha256(answer.trim().toLowerCase());
        }

        /** Lets the kiosk CHECK an answer without the original ever being readable again. */
        public boolean verifySecurityAnswer(String candidate) {
            return securityAnswerHash != null && candidate != null
                    && securityAnswerHash.equals(sha256(candidate.trim().toLowerCase()));
        }

        private static String sha256(String text) {
            try {
                byte[] digest = MessageDigest.getInstance("SHA-256").digest(text.getBytes(StandardCharsets.UTF_8));
                StringBuilder hex = new StringBuilder();
                for (byte b : digest) {
                    hex.append(String.format("%02x", b));
                }
                return hex.toString();
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException("SHA-256 is not available", e);
            }
        }

        @Override
        public String toString() {
            return "LibraryMember{membershipId=" + membershipId + ", name=" + name
                    + ", premiumMember=" + premiumMember + "}";
        }
    }

    private static String quote(String value) {
        return value == null ? "null" : "\"" + value + "\"";
    }

    public static void main(String[] args) {
        System.out.println(quote(new LibraryMember("Priya Nair").getMembershipId()));
        System.out.println(quote(new LibraryMember("LIB-8841", "Priya Nair").getMembershipId()));

        LibraryMember m = new LibraryMember();
        m.setMembershipId("LIB-8841");
        m.setMembershipId("FAKE-0000"); // ignored: write-once
        System.out.println(quote(m.getMembershipId()));

        m.setName("Priya Nair");
        m.setPremiumMember(true);
        m.setSecurityAnswer("Blue Elephant");
        System.out.println(m);
        System.out.println("Correct answer accepted: " + m.verifySecurityAnswer("blue elephant"));
        System.out.println("Wrong answer accepted  : " + m.verifySecurityAnswer("red tiger"));
    }
}
