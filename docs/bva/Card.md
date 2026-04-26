# BVA Analysis for `Card`

## Method under test: `Card(CardType type)`

| ID | State of the system | Expected output | Implemented? |
| --- | --- | --- | --- |
| `CARD-CTOR-1` | Construct a card with `type = EXPLODING_KITTEN`. | Card is created successfully and stores `EXPLODING_KITTEN`. | :white_check_mark: |
| `CARD-CTOR-2` | Construct a card with `type = DEFUSE`. | Card is created successfully and stores `DEFUSE`. | :white_check_mark: |
| `CARD-CTOR-3` | Construct a card with `type = OTHER`. | Card is created successfully and stores `OTHER`. | :white_check_mark: |
| `CARD-CTOR-4` | Construct a card with `type = null`. | Throw `NullPointerException` with message `type must not be null`. | :white_check_mark: |

## Method under test: `CardType getType()`

| ID | State of the system | Expected output | Implemented? |
| --- | --- | --- | --- |
| `CARD-TYPE-1` | Card was constructed with `EXPLODING_KITTEN`. | Return `EXPLODING_KITTEN`. | :white_check_mark: |
| `CARD-TYPE-2` | Card was constructed with `DEFUSE`. | Return `DEFUSE`. | :white_check_mark: |
| `CARD-TYPE-3` | Card was constructed with `OTHER`. | Return `OTHER`. | :white_check_mark: |