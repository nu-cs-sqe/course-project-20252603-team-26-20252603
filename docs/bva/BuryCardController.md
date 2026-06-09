# BVA Analysis for `BuryCardController`

## Method under test: `public void play(Game game, int cardIndex)`

### Step 1-3 Results

| Step | Input: game | Input: selected card index | Input: selected card type | Input: draw pile | Output |
| --- | --- | --- | --- | --- | --- |
| Step 1 | The active game containing the current player, draw pile, and discard pile. | Index of the card the current player chooses to play. | The card selected from the current player's hand. | The draw pile whose top card is moved. | Selected Bury card is discarded, removed from the hand, and the top draw-pile card is moved to the bottom without ending the turn, or an exception is thrown. |
| Step 2 | Pointer / Object state | Array Index | Cases | Collection | Collection state change / Exception |
| Step 3 | valid game that has been set up | `-1`; `0`; largest valid index `getHandSize() - 1`; one larger than largest valid index `getHandSize()` | `BURY`; non-`BURY` | empty; one card; more than one card | hand loses selected Bury card; discard pile gains selected Bury card; former top card becomes bottom; current player is unchanged; invalid input throws an exception and leaves state unchanged |

### Step 4: Each-Choice Test Cases

| ID | State of the system | Expected output | Implemented? |
| --- | --- | --- | --- |
| `BURY-CONTROLLER-1` | Game has been set up, current player's hand contains only `[BURY]`, `cardIndex = 0`, and the draw pile contains more than one card. | Remove and discard Bury, move the top draw-pile card to the bottom, and leave the current player unchanged. | :white_check_mark: |
| `BURY-CONTROLLER-2` | Game has been set up, current player's hand contains `[BEARD_CAT, BURY]`, `cardIndex = 1`, and the draw pile contains one card. | Remove only Bury, discard it, leave the one-card draw pile unchanged, and leave the current player unchanged. | :black_square_button: |
| `BURY-CONTROLLER-3` | Game has been set up, current player's hand contains only `[BURY]`, `cardIndex = 0`, and the draw pile is empty. | Remove and discard Bury, leave the draw pile empty, and leave the current player unchanged. | :black_square_button: |
| `BURY-CONTROLLER-4` | Game has been set up, current player's hand contains only `[BEARD_CAT]`, `cardIndex = 0`, and the draw pile contains more than one card. | Throw `IllegalArgumentException` with message `selected card is not a Bury card`; hand, discard pile, draw pile, and current player are unchanged. | :black_square_button: |
| `BURY-CONTROLLER-5` | Game has been set up, current player's hand contains `[BURY]`, and `cardIndex = -1`. | Throw `IllegalArgumentException` with message `cardIndex is out of bounds`; hand, discard pile, draw pile, and current player are unchanged. | :black_square_button: |
| `BURY-CONTROLLER-6` | Game has been set up, current player's hand contains `[BURY]`, and `cardIndex = getHandSize()`. | Throw `IllegalArgumentException` with message `cardIndex is out of bounds`; hand, discard pile, draw pile, and current player are unchanged. | :black_square_button: |
