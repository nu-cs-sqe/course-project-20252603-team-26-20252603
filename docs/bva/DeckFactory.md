# BVA Analysis for `DeckFactory`

## Method under test: `public static Deck standardDeck()`

## Purpose

`DeckFactory.standardDeck()` builds the standard Exploding Kittens (Grab & Game edition) draw pile so the real game can be initialized without hand-assembling cards. The Grab & Game deck has 42 cards. Our project cuts the Nope card (4 copies), so the produced deck has 38 cards. Placeholder cards are a test-only stand-in and are never included.

`DeckFactory.completeDeck()` adds two copies of each team-invented card to the standard deck for a total of 50 cards. Both factories take no parameters, so the analysis is output-focused.

Standard composition (Nope removed):

| Card type | Count |
|---|---|
| Exploding Kitten | 3 |
| Defuse | 5 |
| Attack | 3 |
| Skip | 3 |
| See the Future | 4 |
| Shuffle | 4 |
| Beard Cat | 4 |
| Hairy Potato Cat | 4 |
| Tacocat | 4 |
| Rainbow Ralphing Cat | 4 |
| **Total** | **38** |

Invented composition added by `completeDeck()`:

| Card type | Count |
|---|---|
| Super Skip | 2 |
| Reverse | 2 |
| Bury | 2 |
| Swap Top and Bottom | 2 |
| Draw from Bottom | 2 |
| Targeted Attack | 2 |
| **Complete deck total** | **50** |

---

## Step 1-3 Results

| Step | Input / State | Category | Boundary values / cases |
|---|---|---|---|
| Step 1 | parameters | none | factory takes no arguments |
| Step 2 | returned deck size | Collection size | total card count equals 38 |
| Step 3 | per-type counts | Collection contents | each card type appears the exact number of times above |
| Step 4 | excluded types | Collection contents | no Placeholder cards present |
| Step 5 | usability for setup | Shared object state | deck supports setup for the minimum (2) and maximum (4) player counts without throwing |

---

## Step 4: Each-Choice Test Cases

| ID | Test name | State of the system | Expected output | Implemented? |
|---|---|---|---|---|
| `DECK-FACTORY-1` | `standardDeck_TotalCount_Returns38` | Call `standardDeck()`. | Returned deck contains exactly 38 cards. | :white_check_mark: |
| `DECK-FACTORY-2` | `standardDeck_ContainsThreeExplodingKittens` | Call `standardDeck()`. | Deck contains exactly 3 `EXPLODING_KITTEN` cards. | :white_check_mark: |
| `DECK-FACTORY-3` | `standardDeck_ContainsFiveDefuses` | Call `standardDeck()`. | Deck contains exactly 5 `DEFUSE` cards. | :white_check_mark: |
| `DECK-FACTORY-4` | `standardDeck_ContainsThreeAttacks` | Call `standardDeck()`. | Deck contains exactly 3 `ATTACK` cards. | :white_check_mark: |
| `DECK-FACTORY-5` | `standardDeck_ContainsThreeSkips` | Call `standardDeck()`. | Deck contains exactly 3 `SKIP` cards. | :white_check_mark: |
| `DECK-FACTORY-6` | `standardDeck_ContainsFourSeeTheFutureCards` | Call `standardDeck()`. | Deck contains exactly 4 `SEE_THE_FUTURE` cards. | :white_check_mark: |
| `DECK-FACTORY-7` | `standardDeck_ContainsFourShuffles` | Call `standardDeck()`. | Deck contains exactly 4 `SHUFFLE` cards. | :white_check_mark: |
| `DECK-FACTORY-8` | `standardDeck_ContainsFourOfEachCatCard` | Call `standardDeck()`. | Deck contains exactly 4 of each cat type (`BEARD_CAT`, `HAIRY_POTATO_CAT`, `TACOCAT`, `RAINBOW_RALPHING_CAT`). | :white_check_mark: |
| `DECK-FACTORY-9` | Exact-count implication | All exact standard counts and the 38-card total are satisfied. | No room remains for `PLACEHOLDER_CARD`. | :white_check_mark: |
| `DECK-FACTORY-10` | `completeDeck_ContainsTwoOfEachInventedCard` | Call `completeDeck()`. | Deck contains 50 cards and two of each invented type. | :white_check_mark: |

---

## Notes

- The factory returns an unshuffled `Deck`; `Game.setupGame` performs the shuffles required by the rules.
- Card counts follow the Grab & Game rulebook field guide. The Nope card (4 copies) is intentionally excluded because it is out of scope for this project.
- Two copies of each invented card are a team-defined deck-composition choice.
