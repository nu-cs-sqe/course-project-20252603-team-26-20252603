# BVA Analysis for `Deck`

## Method under test: `Deck(List<Card> cards)`

| ID | State of the system | Expected output | Implemented? |
| --- | --- | --- | --- |
| `DECK-CTOR-1` | Construct a deck with `cards = null`. | Throw `IllegalArgumentException` with message `cards must not be null`. | :white_check_mark: |
| `DECK-CTOR-2` | Construct a deck with `cards = []`. | Deck is created successfully with size `0`. | :white_check_mark: |
| `DECK-CTOR-3` | Construct a deck with `cards = [card1, card2]`. | Deck is created successfully with size `2`. | :white_check_mark: |

## Method under test: `public void shuffle()`

| ID | State of the system | Expected output | Implemented? |
| --- | --- | --- | --- |
| `DECK-SHUFFLE-1` | Shuffle an empty deck. | Deck remains empty. | :white_check_mark: |
| `DECK-SHUFFLE-2` | Shuffle a one-card deck. | Deck still contains the same one card. | :white_check_mark: |
| `DECK-SHUFFLE-3` | Shuffle a multi-card deck with deterministic randomness. | Cards are reordered according to the injected random sequence. | :white_check_mark: |

## Method under test: `public Card draw()`

| ID | State of the system | Expected output | Implemented? |
| --- | --- | --- | --- |
| `DECK-DRAW-1` | Draw from an empty deck. | Throw `IllegalStateException` with message `cannot draw from an empty deck`. | :white_check_mark: |
| `DECK-DRAW-2` | Draw from a one-card deck. | Return that card and reduce deck size to `0`. | :white_check_mark: |
| `DECK-DRAW-3` | Draw from a multi-card deck. | Return the current top card and reduce deck size by `1`. | :white_check_mark: |

## Method under test: `public void addCard(Card card)`

| ID | State of the system | Expected output | Implemented? |
| --- | --- | --- | --- |
| `DECK-ADD-1` | Empty deck, add a non-null card. | Deck size increases from `0` to `1`. | :white_check_mark: |
| `DECK-ADD-2` | Any deck state, add `null`. | Throw `IllegalArgumentException` with message `card must not be null`. | :white_check_mark: |

## Method under test: `public List<Card> removeCardsByType(CardType type)`

| ID | State of the system | Expected output | Implemented? |
| --- | --- | --- | --- |
| `DECK-REMOVE-1` | Deck contains no cards of the requested type. | Return `[]`; deck contents are unchanged. | :white_check_mark: |
| `DECK-REMOVE-2` | Deck contains some cards of the requested type. | Return only matching cards; remove only those cards from the deck. | :white_check_mark: |
| `DECK-REMOVE-3` | Requested `type = null`. | Throw `IllegalArgumentException` with message `card type must not be null`. | :white_check_mark: |
