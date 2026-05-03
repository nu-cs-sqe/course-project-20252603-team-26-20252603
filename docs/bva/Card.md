# BVA Analysis for `Card`

## Method under test: `Card(CardType type)`

### Step 1: Identify the input and output equivalence classes
- Input: the card type used to construct the card
- Output: the created card stores the provided type, or construction fails with an exception

### Step 2: Choose the relevant data types from the BVA Catalog
- Input: Cases
- Output: Cases

### Step 3: Select concrete values along the edges
- Input cases: `EXPLODING_KITTEN`, `DEFUSE`, `PLACEHOLDER_CARD`, `null`
- Output cases: created card stores the same type, or `NullPointerException` with message `type must not be null`

### Step 4

| ID | State of the system | Expected output | Implemented? |
| --- | --- | --- | --- |
| `CARD-CTOR-1` | Construct a card with `type = EXPLODING_KITTEN`. | Card is created successfully and stores `EXPLODING_KITTEN`. | :white_check_mark: |
| `CARD-CTOR-2` | Construct a card with `type = DEFUSE`. | Card is created successfully and stores `DEFUSE`. | :white_check_mark: |
| `CARD-CTOR-3` | Construct a card with `type = PLACEHOLDER_CARD`. | Card is created successfully and stores `PLACEHOLDER_CARD`. | :x: |
| `CARD-CTOR-4` | Construct a card with `type = null`. | Throw `NullPointerException` with message `type must not be null`. | :white_check_mark: |

## Method under test: `CardType getType()`

### Step 1: Identify the input and output equivalence classes
- Input: the state of the card after construction
- Output: the stored card type returned by `getType()`

### Step 2: Choose the relevant data types from the BVA Catalog
- Input: Cases
- Output: Cases

### Step 3: Select concrete values along the edges
- Input cases: card constructed with `EXPLODING_KITTEN`, `DEFUSE`, `PLACEHOLDER_CARD`
- Output cases: returns the matching card type

### Step 4

| ID | State of the system | Expected output | Implemented? |
| --- | --- | --- | --- |
| `CARD-TYPE-1` | Card was constructed with `EXPLODING_KITTEN`. | Return `EXPLODING_KITTEN`. | :white_check_mark: |
| `CARD-TYPE-2` | Card was constructed with `DEFUSE`. | Return `DEFUSE`. | :white_check_mark: |
| `CARD-TYPE-3` | Card was constructed with `PLACEHOLDER_CARD`. | Return `PLACEHOLDER_CARD`. | :x: |
