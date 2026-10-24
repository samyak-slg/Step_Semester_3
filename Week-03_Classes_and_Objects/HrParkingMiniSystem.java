/**
 * Week 3 - F5 (Capstone): A Small HR + Parking Allocation Mini-System
 *
 * Brings the week together:
 *  - Inheritance  : Employee -> ManagerEmployee / InternEmployee (from F2)
 *  - Null safety  : ParkingSlot with a null-safe allotment (from F3)
 *  - Composition  : CompanyEmployeeRecord holds an Employee object AND a ParkingSlot object
 *  - Static state : CompanyEmployeeRecord.totalRecords counts every record created
 */
public class HrParkingMiniSystem {

    static class Employee {
        private final String empId;
        private final String empName;
        private final double salary;

        Employee(String empId, String empName, double salary) {
            this.empId = empId;
            this.empName = empName;
            this.salary = salary;
        }

        double getSalary() {
            return salary;
        }
    }

    static class ManagerEmployee extends Employee {
        private final double teamBonus;

        ManagerEmployee(String empId, String empName, double salary, double teamBonus) {
            super(empId, empName, salary);
            this.teamBonus = teamBonus;
        }

        double effectiveSalary() {
            return getSalary() + teamBonus;
        }
    }

    static class InternEmployee extends Employee {
        private final double stipendCap;

        InternEmployee(String empId, String empName, double salary, double stipendCap) {
            super(empId, empName, salary);
            this.stipendCap = stipendCap;
        }

        double effectiveSalary() {
            return Math.min(getSalary(), stipendCap);
        }
    }

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

        /** Null-safe allotment: returns the slot used, or null if nothing was free. */
        static ParkingSlot safeAllot(ParkingSlot[] slots, String vehicleNo) {
            ParkingSlot slot = findAvailableSlot(slots);
            if (slot == null) {
                System.out.println("No slots available for " + vehicleNo);
                return null;
            }
            slot.allot(vehicleNo);
            return slot;
        }
    }

    static class CompanyEmployeeRecord {
        static int totalRecords = 0;

        private final String name;
        private final String empId;
        private final Employee employee;   // an object as a field (composition)
        private ParkingSlot slot;          // may stay null on purpose

        CompanyEmployeeRecord(String name, String empId, Employee employee) {
            this.name = name;
            this.empId = empId;
            this.employee = employee;
            totalRecords++;
        }

        void assignParking(ParkingSlot slot) {
            this.slot = slot;
        }

        double effectivePay() {
            if (employee instanceof ManagerEmployee) {
                return ((ManagerEmployee) employee).effectiveSalary();
            }
            if (employee instanceof InternEmployee) {
                return ((InternEmployee) employee).effectiveSalary();
            }
            return employee.getSalary();
        }

        String fullProfile() {
            String slotText = (slot == null) ? "no parking assigned" : slot.getSlotNo();
            return name + " | Pay: Rs " + effectivePay() + " | Slot: " + slotText;
        }
    }

    public static void main(String[] args) {
        ParkingSlot[] slots = {
            new ParkingSlot("A1", 1, 0),
            new ParkingSlot("A2", 1, 0)
        };

        CompanyEmployeeRecord divya = new CompanyEmployeeRecord("Divya", "E201",
                new ManagerEmployee("E201", "Divya", 70000, 8000));
        CompanyEmployeeRecord karan = new CompanyEmployeeRecord("Karan", "E202",
                new Employee("E202", "Karan", 40000));
        CompanyEmployeeRecord meera = new CompanyEmployeeRecord("Meera", "E203",
                new InternEmployee("E203", "Meera", 12000, 10000));

        // Only two employees get parking; Meera is left unallotted on purpose.
        divya.assignParking(ParkingSlot.safeAllot(slots, "KA05MN1111"));
        karan.assignParking(ParkingSlot.safeAllot(slots, "KA05MN2222"));

        CompanyEmployeeRecord[] records = {divya, karan, meera};
        for (CompanyEmployeeRecord record : records) {
            System.out.println(record.fullProfile());
        }
        System.out.println("Total records: " + CompanyEmployeeRecord.totalRecords);
    }
}
