# Week 2 — String Manipulation

Validating, parsing, normalising and analysing text with the core `String` API.

| # | Problem | Solution | Key Concepts |
|---|---------|----------|--------------|
| 1 | ATM PIN Length Validator | [AtmPinLengthValidator.java](AtmPinLengthValidator.java) | `length()`, `if / else` |
| 2 | Word Reversal Encoder | [WordReversalEncoder.java](WordReversalEncoder.java) | `split()`, `StringBuilder` |
| 3 | Product Inventory CSV Parser | [InventoryCsvParser.java](InventoryCsvParser.java) | `split(",")`, field validation |
| 4 | Library ISBN Normalizer & Validator | [LibraryIsbnValidator.java](LibraryIsbnValidator.java) | `trim()`, `substring()`, `isLetter()/isDigit()` |
| 5 | Stop-Word-Filtered Word Frequency Report | [FeedbackWordFrequency.java](FeedbackWordFrequency.java) | `replace()`, `split("\\s+")`, `HashMap`, sorting |

## Sample Output

```text
Invalid PIN -- must be exactly 4 digits.
olleh bulc
Product: Wireless Mouse | SKU: WM-2201 | Qty: 150
[PEN] YEAR: 2026 | CATALOG: 004251
great: 2
mentor: 1
```
