# BVA Analysis for `DiscardPile`

## Method under test: `DiscardPile()`

| ID | State of the system | Expected output | Implemented? |
| --- | --- | --- | --- |
| `DISCARD-CTOR-1` | Construct a new discard pile. | Pile is created successfully and starts empty. | :white_check_mark: |

## Method under test: `public void add(Card card)`

| ID | State of the system | Expected output | Implemented? |
| --- | --- | --- | --- |
| `DISCARD-ADD-1` | Empty discard pile, add a non-null card. | Pile size increases from `0` to `1`; card is stored. | :white_check_mark: |
| `DISCARD-ADD-2` | Discard pile already contains `1` card, add another non-null card. | Pile size increases from `1` to `2`; new card is appended. | :white_check_mark: |
| `DISCARD-ADD-3` | Any discard pile state, add `null`. | Throw `IllegalArgumentException` with message `card must not be null`. | :white_check_mark: |
