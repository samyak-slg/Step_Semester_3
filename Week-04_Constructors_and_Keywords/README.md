# Week 4 — Constructors, `this`, `final` & `static`

A campus food-delivery app used to practise validated constructors, constructor
chaining, `final` locking, static blocks and null-safe batch processing.

| # | Level | Problem | Solution | Key Concepts |
|---|-------|---------|----------|--------------|
| 1 | Basic | Ghost Order Validator | [GhostOrderValidator.java](GhostOrderValidator.java) | Validating constructor, no no-arg constructor |
| 2 | Basic | ASAP or Scheduled — Delivery Slot Booking | [DeliverySlotBooking.java](DeliverySlotBooking.java) | `this(...)` chaining, constants |
| 3 | Intermediate | Canteen Trust-Score Ranking Engine | [CanteenTrustRanking.java](CanteenTrustRanking.java) | `compareTo()`, deterministic tie-breaks, hand-written sort |
| 4 | Intermediate | Exam-Week Surge Fee Calculator | [SurgeFeeCalculatorDemo.java](SurgeFeeCalculatorDemo.java) | `final` class/field/method, tiered O(1) formula |
| 5 | Advanced | Nightly Multi-Kitchen Reconciliation Engine | [NightlyReconciliationEngine.java](NightlyReconciliationEngine.java) | Static block, `instanceof`, null safety, fail-fast validation |

## Design notes

- **Problem 3 tie-break order:** trust score (high → low) → canteen code (case-insensitive A → Z) → shorter name → name A → Z.
- **Problem 4:** surge is charged only on the *extra* minutes in each bracket (like tax slabs);
  the minimum floor applies only when `delayMinutes > 0`.
- **Problem 5:** if the three input arrays have different lengths the whole batch is rejected
  before anything runs, because mismatched arrays could charge the wrong amount to the wrong student.
  Premium accounts get 50% of the surge fee waived.

## Sample Output

```text
Valid: 2 | Rejected: 2
["hb1-c", "HB2-C", "HB3-C"]
orderValue = 500, delayMinutes = 16 -> Rs 72.5
2 processed | 1 null skipped | 1 premium | 1 regular | grand total surge fees = Rs 18.75
```
