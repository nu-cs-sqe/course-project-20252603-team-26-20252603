# BVA Analysis for `DeckFactory`

## Method under test: `public static Deck standardDeck()`

## Purpose

`DeckFactory.standardDeck()` builds the standard Exploding Kittens (Grab & Game edition) draw pile so the real game can be initialized without hand-assembling cards. The Grab & Game deck has 42 cards. Our project cuts the Nope card (4 copies), so the produced deck has 38 cards. Placeholder cards are a test-only stand-in and are never included.

The factory takes no parameters, so the analysis is output-focused: the composition (size and per-type counts) of the returned `Deck`, and that the composition is sufficient for `Game.setupGame` across all legal player counts.

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
| `DECK-FACTORY-1` | `standardDeck_TotalCount_Returns38` | Call `standardDeck()`. | Returned deck contains exactly 38 cards. | :black_square_button: |
| `DECK-FACTORY-2` | `standardDeck_ContainsThreeExplodingKittens` | Call `standardDeck()`. | Deck contains exactly 3 `EXPLODING_KITTEN` cards. | :black_square_button: |
| `DECK-FACTORY-3` | `standardDeck_ContainsFiveDefuses` | Call `standardDeck()`. | Deck contains exactly 5 `DEFUSE` cards. | :black_square_button: |
| `DECK-FACTORY-4` | `standardDeck_ContainsThreeAttacks` | Call `standardDeck()`. | Deck contains exactly 3 `ATTACK` cards. | :black_square_button: |
| `DECK-FACTORY-5` | `standardDeck_ContainsThreeSkips` | Call `standardDeck()`. | Deck contains exactly 3 `SKIP` cards. | :black_square_button: |
| `DECK-FACTORY-6` | `standardDeck_ContainsFourSeeTheFuture` | Call `standardDeck()`. | Deck contains exactly 4 `SEE_THE_FUTURE` cards. | :black_square_button: |
| `DECK-FACTORY-7` | `standardDeck_ContainsFourShuffles` | Call `standardDeck()`. | Deck contains exactly 4 `SHUFFLE` cards. | :black_square_button: |
| `DECK-FACTORY-8` | `standardDeck_ContainsFourOfEachCatCard` | Call `standardDeck()`. | Deck contains exactly 4 of each cat type (`BEARD_CAT`, `HAIRY_POTATO_CAT`, `TACOCAT`, `RAINBOW_RALPHING_CAT`). | :black_square_button: |
| `DECK-FACTORY-9` | `standardDeck_ContainsNoPlaceholderCards` | Call `standardDeck()`. | Deck contains 0 `PLACEHOLDER_CARD` cards. | :black_square_button: |
| `DECK-FACTORY-10` | `standardDeck_SupportsMinAndMaxPlayerSetup` | Build a `Game` from a fresh `standardDeck()` and call `setupGame` for 2 players, and again for 4 players. | Neither call throws; setup completes. | :black_square_button: |

---

## Notes

- The factory returns an unshuffled `Deck`; `Game.setupGame` performs the shuffles required by the rules.
- Card counts follow the Grab & Game rulebook field guide. The Nope card (4 copies) is intentionally excluded because it is out of scope for this project.
- `:black_square_button:` rows are flipped to `:white_check_mark:` as each test case is implemented under TDD.
