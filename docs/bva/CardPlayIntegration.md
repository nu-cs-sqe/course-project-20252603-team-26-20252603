# BVA Analysis for Card-Play Integration

## Feature under test: Card-play flow

## Purpose

These integration tests verify that card-play behavior works correctly using real domain objects. The tests check that `Player`, `Deck`, `DiscardPile`, `Card`, `CardType`, and card controllers coordinate correctly when a player plays a card.

This counts as the second main feature for the A-level integration testing requirement.

---

## Step 1-3 Results

| Step | Input / State | Category | Boundary values / cases |
|---|---|---|---|
| Step 1 | selected `cardIndex` | Array index | first valid index `0`; selected valid index; invalid indexes covered in controller BVA |
| Step 2 | selected card type | Card type case | expected card type for controller; wrong card type covered in controller BVA |
| Step 3 | player hand before play | Collection state | hand contains selected playable card |
| Step 4 | player hand after play | Collection state change | played card is removed from hand |
| Step 5 | discard pile before play | Collection state | initially empty discard pile |
| Step 6 | discard pile after play | Collection state change | played card is added to discard pile |
| Step 7 | draw pile for See the Future | Collection state | draw pile has at least two cards; size and order must stay unchanged |
| Step 8 | draw pile for Skip | Collection state | draw pile should not be drawn from |
| Step 9 | draw pile for Shuffle | Collection state | draw pile size and contents should stay the same |
| Step 10 | draw pile for Attack | Collection state | draw pile should stay unchanged |
| Step 11 | controller output | Return value / state change | See the Future returns top two cards; Skip returns `true`; Attack returns `2`; Shuffle preserves deck contents |

---

## Step 4: Each-Choice Test Cases

| ID | Test name | State of the system | Expected output | Implemented?       |
|---|---|---|---|--------------------|
| `CARD-PLAY-INTEGRATION-1` | `seeFuture_PlayValidCard_RemovesCardDiscardsItAndPeeksTopTwoWithoutChangingDeckSize` | Player hand contains `[SEE_THE_FUTURE]`; draw pile has at least two known cards; discard pile is empty. | Returns top two cards; removes See the Future from hand; adds it to discard pile; draw pile size and order stay unchanged. | :white_check_mark: |
| `CARD-PLAY-INTEGRATION-2` | `skip_PlayValidCard_RemovesCardDiscardsItAndReturnsTrue` | Player hand contains `[SKIP]`; draw pile has cards; discard pile is empty. | Returns `true`; removes Skip from hand; adds it to discard pile; draw pile size stays unchanged. | :white_check_mark: |
| `CARD-PLAY-INTEGRATION-3` | `shuffle_PlayValidCard_RemovesCardDiscardsItAndPreservesDeckSize` | Game is set up; current player has `[SHUFFLE]`; draw pile has known cards; discard pile is empty. | Removes Shuffle from hand; adds it to discard pile; draw pile size stays the same; draw pile contains the same cards as before. | :x:                |
| `CARD-PLAY-INTEGRATION-4` | `attack_PlayValidCard_RemovesCardDiscardsItAndReturnsTwoTurns` | Player hand contains `[ATTACK]`; draw pile has cards; discard pile is empty. | Returns `2`; removes Attack from hand; adds it to discard pile; draw pile size and order stay unchanged. | :x:                |

---

## Integration risks covered

- `cardIndex` may be interpreted inconsistently.
- Played cards may not move correctly from hand to discard pile.
- Card effects may incorrectly change the draw pile.
- Card controllers may not coordinate correctly with `Player`, `Deck`, and `DiscardPile`.
- Card behavior may not match the intended game rules.