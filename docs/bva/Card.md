# BVA Analysis for `Card`

## Method under test: `Card(CardType type)`

| ID            | State of the system | Expected output | Implemented? |
|---------------| --- | --- | --- |
| `CARD-CTOR-1` | Construct a card with `type = EXPLODING_KITTEN`. | Card is created successfully and stores `EXPLODING_KITTEN`. | :white_check_mark: |
| `CARD-CTOR-2` | Construct a card with `type = DEFUSE`. | Card is created successfully and stores `DEFUSE`. | :white_check_mark: |
| `CARD-CTOR-3` | Construct a card with `type = SHUFFLE`. | Card is created successfully and stores `SHUFFLE`. | :white_check_mark: |
| `CARD-CTOR-4` | Construct a card with `type = PLACEHOLDER_CARD`. | Card is created successfully and stores `PLACEHOLDER_CARD`. | :white_check_mark: |
| `CARD-CTOR-5` | Construct a card with `type = BEARD_CAT`. | Card is created successfully and stores `BEARD_CAT`. | :white_check_mark: |
| `CARD-CTOR-6` | Construct a card with `type = HAIRY_POTATO_CAT`. | Card is created successfully and stores `HAIRY_POTATO_CAT`. | :white_check_mark: |
| `CARD-CTOR-7` | Construct a card with `type = TACOCAT`. | Card is created successfully and stores `TACOCAT`. | :white_check_mark: |
| `CARD-CTOR-8` | Construct a card with `type = RAINBOW_RALPHING_CAT`. | Card is created successfully and stores `RAINBOW_RALPHING_CAT`. | :white_check_mark: |
| `CARD-CTOR-9` | Construct a card with `type = ATTACK`. | Card is created successfully and stores `ATTACK`. | :white_check_mark: |
| `CARD-CTOR-10` | Construct a card with `type = null`. | Throw `NullPointerException` with message `type must not be null`. | :white_check_mark: |

## Method under test: `CardType getType()`

| ID | State of the system | Expected output | Implemented? |
| --- | --- | --- | --- |
| `CARD-TYPE-1` | Card was constructed with `EXPLODING_KITTEN`. | Return `EXPLODING_KITTEN`. | :white_check_mark: |
| `CARD-TYPE-2` | Card was constructed with `DEFUSE`. | Return `DEFUSE`. | :white_check_mark: |
| `CARD-TYPE-3` | Card was constructed with `SHUFFLE`. | Return `SHUFFLE`. | implemented in `CARD-CTOR-3` |
| `CARD-TYPE-4` | Card was constructed with `PLACEHOLDER_CARD`. | Return `PLACEHOLDER_CARD`. | implemented in `CARD-CTOR-4` |
| `CARD-TYPE-5` | Card was constructed with `ATTACK`. | Return `ATTACK`. | implemented in `CARD-CTOR-9` |
