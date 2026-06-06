# BVA Analysis for Game Setup Integration

## Method under test: `Game.setupGame(List<String> playerNames)`

## Purpose

These integration tests verify that the game setup flow works correctly using real domain objects. The tests check that `Game`, `Deck`, `Player`, `Card`, `CardType`, and `DiscardPile` coordinate correctly during setup.

This counts as one main feature for the A-level integration testing requirement.

---

## Step 1-3 Results

| Step | Input / State | Category | Boundary values / cases |
|---|---|---|---|
| Step 1 | `playerNames` | Collection size | minimum valid player count `2`; maximum valid player count `4` |
| Step 2 | Defuse card count in deck | Collection count | fewer than player count; exactly one per player; more than player count |
| Step 3 | Exploding Kitten count in deck | Collection count | fewer than `playerCount - 1`; exactly `playerCount - 1`; more than `playerCount - 1` |
| Step 4 | non-special card count in deck | Collection count | fewer than `playerCount * 5`; exactly `playerCount * 5`; more than `playerCount * 5` |
| Step 5 | player hands after setup | Collection state | each player has 6 total cards; each player has exactly 1 Defuse |
| Step 6 | draw pile after setup | Shared object state | contains `playerCount - 1` Exploding Kittens; contains extra Defuse cards; contains remaining cards |
| Step 7 | discard pile after setup | Shared object state | empty after setup |
| Step 8 | current player after setup | Object state | first player is current player |
| Step 9 | invalid setup state | Exception / state protection | setup throws exception before completing invalid setup |

---

## Step 4: Each-Choice Test Cases

| ID | Test name | State of the system | Expected output | Implemented? |
|---|---|---|---|---|
| `SETUP-INTEGRATION-1` | `setupGame_ValidTwoPlayers_DealsSixCardsEachAndResetsGameState` | Deck has enough Defuses, Exploding Kittens, and normal cards for 2 players. Player names are `[Alice, Bob]`. | Game has 2 players; each player has 6 cards; each player has exactly 1 Defuse; draw pile has exactly 1 Exploding Kitten; discard pile is empty; current player index is 0. | :white_check_mark: |
| `SETUP-INTEGRATION-2` | `setupGame_ValidFourPlayers_AddsThreeExplodingKittensBackToDrawPile` | Deck has enough Defuses, Exploding Kittens, and normal cards for 4 players. Player names are `[Alice, Bob, Cathy, David]`. | Game has 4 players; each player has 6 cards; each player has exactly 1 Defuse; draw pile has exactly 3 Exploding Kittens; discard pile is empty. | :x: |
| `SETUP-INTEGRATION-3` | `setupGame_TooFewDefuses_ThrowsException` | Deck has fewer Defuse cards than the number of players. | `IllegalStateException` is thrown with message `deck must contain at least one defuse per player`. | :x: |
| `SETUP-INTEGRATION-4` | `setupGame_TooFewNormalCards_ThrowsException` | Deck has enough Defuses and Exploding Kittens, but fewer than `playerCount * 5` normal cards. | `IllegalStateException` is thrown with message `deck must contain enough non-special cards to deal opening hands`. | :x: |

---

## Integration risks covered

- Setup rules may be interpreted inconsistently.
- Invalid deck sizes may not be rejected.
- Player hands and draw pile may not update correctly together.
- Setup may miss required game rules.
- `Game`, `Deck`, and `Player` may not coordinate correctly.