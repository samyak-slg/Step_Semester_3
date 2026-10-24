# STEP — Semester 3 · Java & OOP Assignments

![Java](https://img.shields.io/badge/Java-17%2B-orange?logo=openjdk&logoColor=white)
[![Build & Run](https://github.com/samyak-slg/Step_Semester_3/actions/workflows/build.yml/badge.svg)](https://github.com/samyak-slg/Step_Semester_3/actions/workflows/build.yml)
![Problems](https://img.shields.io/badge/problems%20solved-35-brightgreen)

Solutions to the weekly **STEP Semester 3** (CodInClub · Powered by BridgeLabz) Category A
assignment problems: Java fundamentals through to object-oriented design.

Each program is a **single, self-contained `.java` file** with a `main` method that runs the
sample inputs from the problem statement (plus a few edge cases), so every file can be
compiled and run on its own.

---

## 📚 Contents

| Week | Topic | Problems | Folder |
|------|-------|:--------:|--------|
| 1 | Arrays & Strings Fundamentals | 5 | [Week-01_Arrays_and_Strings](Week-01_Arrays_and_Strings) |
| 2 | String Manipulation | 5 | [Week-02_String_Manipulation](Week-02_String_Manipulation) |
| 3 | OOP: Classes & Objects | 5 | [Week-03_Classes_and_Objects](Week-03_Classes_and_Objects) |
| 4 | Constructors, `this`, `final` & `static` | 5 | [Week-04_Constructors_and_Keywords](Week-04_Constructors_and_Keywords) |
| 5 | Access Modifiers & Encapsulation | 5 | [Week-05_Access_Modifiers_and_Encapsulation](Week-05_Access_Modifiers_and_Encapsulation) |
| 6 | Inheritance & Polymorphism (Assignment + Practice) | 10 | [Week-06_Inheritance_and_Polymorphism](Week-06_Inheritance_and_Polymorphism) |

Each week folder has its own `README.md` listing every problem, its solution file and the
concepts it covers.

## 🗂️ Repository Structure

```text
Step_Semester_3/
├── Week-01_Arrays_and_Strings/
│   ├── ExamSeatDuplicateChecker.java
│   ├── TypingAccuracyChecker.java
│   ├── TrafficSignalStreakAnalyzer.java
│   ├── WarehouseInventoryBalancer.java
│   └── MovieReviewWordProfiler.java
├── Week-02_String_Manipulation/
│   ├── AtmPinLengthValidator.java
│   ├── WordReversalEncoder.java
│   ├── InventoryCsvParser.java
│   ├── LibraryIsbnValidator.java
│   └── FeedbackWordFrequency.java
├── Week-03_Classes_and_Objects/
│   ├── LibraryFineSystem.java
│   ├── EmployeeExtension.java
│   ├── ParkingAllocation.java
│   ├── LibraryMembershipSystem.java
│   └── HrParkingMiniSystem.java
├── Week-04_Constructors_and_Keywords/
│   ├── GhostOrderValidator.java
│   ├── DeliverySlotBooking.java
│   ├── CanteenTrustRanking.java
│   ├── SurgeFeeCalculatorDemo.java
│   └── NightlyReconciliationEngine.java
├── Week-05_Access_Modifiers_and_Encapsulation/
│   ├── MembershipFieldReachChecker.java
│   ├── SubclassReachChecker.java
│   ├── BookCirculationGuard.java
│   ├── LibraryMemberBean.java
│   └── ImmutableLoanReceipt.java
└── Week-06_Inheritance_and_Polymorphism/
    ├── Assignment/          # Riverside City Marathon (RaceEntry family)
    │   ├── RaceEntryBatchValidator.java
    │   ├── RaceFamilyClassifier.java
    │   ├── LateWithdrawalPenalty.java
    │   ├── RaceDayAnnouncerBoard.java
    │   └── RaceSettlementEngine.java
    └── Practice/            # CineHub Tech Fest (EventTicket family)
        ├── TicketBatchRegistration.java
        ├── TicketFamilyClassifier.java
        ├── LateRegistrationPenalty.java
        ├── NightlyTicketAnnouncer.java
        └── TicketSettlementEngine.java
```

## ▶️ How to Run

**Requirements:** JDK 17 or newer (Week 5 Problem 5 uses a `sealed` class).

```bash
# 1. Clone the repository
git clone https://github.com/samyak-slg/Step_Semester_3.git
cd Step_Semester_3

# 2. Run any program directly (Java 11+ can run a single source file)
java Week-01_Arrays_and_Strings/ExamSeatDuplicateChecker.java

# ...or compile first, then run
cd Week-01_Arrays_and_Strings
javac ExamSeatDuplicateChecker.java
java ExamSeatDuplicateChecker
```

A GitHub Actions workflow ([`build.yml`](.github/workflows/build.yml)) compiles and runs
**every** program on each push, so the badge above shows whether all solutions still build.

## 🧠 Concepts Covered

- **Fundamentals:** arrays, nested loops, string traversal, `StringBuilder`, `split()`, `trim()`, `substring()`
- **Classes & objects:** constructors, instance vs `static` members, object references, composition
- **Constructors & keywords:** validation at construction time, `this(...)` chaining, `final`, static blocks
- **Encapsulation:** access modifiers, invariants, JavaBeans, write-once / write-only properties, immutability, defensive copies
- **Inheritance & polymorphism:** single / multilevel / hierarchical inheritance, `super`, `@Override`,
  dynamic dispatch, `instanceof`-guarded downcasting, method overloading
- **Robustness:** null-safe batch processing, fail-fast input checks, `try/catch` around construction

## ✍️ Code Conventions

- One problem per file; the public class name matches the file name.
- Supporting classes are `static` nested classes, so files in the same folder never clash.
- Every file starts with a header comment describing the problem, the approach and (where relevant) its complexity.
