# Week 6 — Inheritance & Polymorphism

Two parallel problem sets build the same ideas step by step: single, multilevel and
hierarchical inheritance, `super(...)`, method overriding, safe downcasting,
overloading and static counters.

## Assignment — Riverside City Marathon

```text
            RaceEntry
            /       \
     RunnerEntry   RelayTeamEntry
          |
   EliteRunnerEntry
```

| # | Level | Problem | Solution | Key Concepts |
|---|-------|---------|----------|--------------|
| 1 | Basic / Intermediate | Race Entry Foundation & Batch Bib Validator | [RaceEntryBatchValidator.java](Assignment/RaceEntryBatchValidator.java) | Single inheritance, `super(...)`, try/catch |
| 2 | Intermediate | Three Shapes of One Race Family | [RaceFamilyClassifier.java](Assignment/RaceFamilyClassifier.java) | Multilevel & hierarchical, `instanceof`, polymorphism |
| 3 | Intermediate | Late-Withdrawal Penalty Override & Audit Trail | [LateWithdrawalPenalty.java](Assignment/LateWithdrawalPenalty.java) | `@Override`, `super.method()`, defensive copy |
| 4 | Intermediate / Advanced | The Race-Day Announcer Board | [RaceDayAnnouncerBoard.java](Assignment/RaceDayAnnouncerBoard.java) | Dynamic dispatch, guarded downcast, `StringBuilder` |
| 5 | Advanced | Bib Issuance, Discount Codes & Nightly Settlement | [RaceSettlementEngine.java](Assignment/RaceSettlementEngine.java) | Static counter, `final` ID, overloading, null safety |

## Practice — CineHub Tech Fest

```text
              EventTicket
              /         \
     WorkshopTicket   HackathonTicket
            |
   PremiumWorkshopTicket
```

| # | Level | Problem | Solution | Key Concepts |
|---|-------|---------|----------|--------------|
| 1 | Basic / Intermediate | Ticket Hierarchy & Batch Registration Validator | [TicketBatchRegistration.java](Practice/TicketBatchRegistration.java) | Single inheritance, `super(...)`, try/catch |
| 2 | Intermediate | Three Shapes of One Family Tree | [TicketFamilyClassifier.java](Practice/TicketFamilyClassifier.java) | Multilevel & hierarchical, `instanceof`, polymorphism |
| 3 | Intermediate | Late-Registration Penalty Override & Audit Trail | [LateRegistrationPenalty.java](Practice/LateRegistrationPenalty.java) | `@Override`, `super.method()`, defensive copy |
| 4 | Intermediate / Advanced | The Nightly Ticket Announcer | [NightlyTicketAnnouncer.java](Practice/NightlyTicketAnnouncer.java) | Dynamic dispatch, guarded downcast, `StringBuilder` |
| 5 | Advanced | Ticket Issuance, Promo Codes & Nightly Settlement | [TicketSettlementEngine.java](Practice/TicketSettlementEngine.java) | Static counter, `final` ID, overloading, null safety |

## Sample Output

```text
Registered: 2 | Rejected: 1
getTotalBalanceDue -> 530.0
History     -> [40.0]
"Runner Entry | Bib: BIB2001 | Category: Open 10K | Balance: 90.0 | Relay Team | Bib: BIB4001 | Team Size: 4 | Balance: 300.0 [Team size via downcast: 4] | "
2 processed | 1 null skipped | 1 relay | 1 individual
```
