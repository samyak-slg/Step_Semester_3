# Week 5 — Access Modifiers & Encapsulation

A library system used to practise Java's visibility rules, guarded state,
JavaBeans, write-once/write-only properties and true immutability.

| # | Level | Problem | Solution | Key Concepts |
|---|-------|---------|----------|--------------|
| 1 | Basic / Intermediate | Membership Field Reach Checker | [MembershipFieldReachChecker.java](MembershipFieldReachChecker.java) | `private` / default / `protected` / `public` rules |
| 2 | Intermediate | Reference Desk Subclass Reach | [SubclassReachChecker.java](SubclassReachChecker.java) | `protected` across packages, compile-time type |
| 3 | Intermediate | Book Copy Circulation Guard | [BookCirculationGuard.java](BookCirculationGuard.java) | Private state, class invariants |
| 4 | Intermediate / Advanced | LibraryMember JavaBean & Security Answer | [LibraryMemberBean.java](LibraryMemberBean.java) | JavaBean naming, write-once & write-only properties |
| 5 | Advanced | Immutable Loan Receipt & Nightly Ledger | [ImmutableLoanReceipt.java](ImmutableLoanReceipt.java) | `final`, defensive copies, wither pattern, `sealed` |

## Access rules used

| Modifier | Same class | Same package | Subclass, other package (own type) | Subclass, other package (parent type) | Other package |
|----------|:---:|:---:|:---:|:---:|:---:|
| `private`   | ✅ | ❌ | ❌ | ❌ | ❌ |
| default     | ✅ | ✅ | ❌ | ❌ | ❌ |
| `protected` | ✅ | ✅ | ✅ | ❌ | ❌ |
| `public`    | ✅ | ✅ | ✅ | ✅ | ✅ |

> **Problem 5 note:** the problem asks for a `final` `LoanReceipt` *and* a `ReferenceOnlyLoanReceipt`
> variant that lives in the same `LoanReceipt[]`. A `final` class cannot have any subclass, so
> `LoanReceipt` is `sealed ... permits ReferenceOnlyLoanReceipt` (Java 17+) and the variant is `final`.
> No other class can ever extend it, which is the same lock as `final`.

## Sample Output

```text
private: 1 allowed / 1 denied | default: 1 allowed / 1 denied | protected: 2 allowed / 0 denied | public: 1 allowed / 0 denied
classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE") -> DENIED
After 4 check-outs: 0
"LIB-8841"
2 processed | 1 null skipped | 1 reference-only | 1 regular
```
