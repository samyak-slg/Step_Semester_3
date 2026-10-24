/**
 * Week 3 - F2: Extending Employee Without Touching It
 *
 * Employee is treated as already-tested code: it is never edited. New behaviour is
 * added only by subclasses (ManagerEmployee, InternEmployee), and main() uses
 * instanceof to decide which extra behaviour to call for each object.
 */
public class EmployeeExtension {

    // ---- Tested, "closed" class: do not modify ----
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

        String getEmpId() {
            return empId;
        }

        String getEmpName() {
            return empName;
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

    public static void main(String[] args) {
        Employee[] staff = {
            new Employee("E101", "Karan", 40000),
            new ManagerEmployee("E102", "Divya", 70000, 8000),
            new InternEmployee("E103", "Meera", 12000, 10000)
        };

        for (Employee e : staff) {
            // Check the subclasses explicitly: Employee itself has no effectiveSalary().
            if (e instanceof ManagerEmployee) {
                ManagerEmployee m = (ManagerEmployee) e;
                System.out.println("Manager effective pay: Rs " + m.effectiveSalary());
            } else if (e instanceof InternEmployee) {
                InternEmployee i = (InternEmployee) e;
                System.out.println("Intern effective pay: Rs " + i.effectiveSalary());
            } else {
                System.out.println("Plain employee pay: Rs " + e.getSalary());
            }
        }
    }
}
