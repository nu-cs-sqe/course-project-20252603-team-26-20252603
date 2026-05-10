# BVA Analysis for `ShuffleCardController`

## Method under test: `public void play(Game game, int cardIndex)`

### Step 1-3 Results

| Step | Input: game | Input: selected card index | Input: selected card type | Input: draw pile | Output |
| --- | --- | --- | --- | --- | --- |
| Step 1 | The active game containing the current player, draw pile, and discard pile. | Index of the card the current player chooses to play. | The card selected from the current player's hand. | The draw pile to shuffle. | Selected Shuffle card is discarded, removed from hand, and the draw pile is shuffled, or an exception is thrown. |
| Step 2 | Pointer / Object state | Array Index | Cases | Collection | Collection state change / Exception |
| Step 3 | valid game that has been set up | `-1`; `0`; largest valid index `getHandSize() - 1`; one larger than largest valid index `getHandSize()` | `SHUFFLE`; non-`SHUFFLE` | empty; one card; more than one card | hand loses selected Shuffle card; discard pile gains selected Shuffle card; draw pile order changes according to shuffle; invalid input throws exception and leaves state unchanged |

### Step 4: Each-Choice Test Cases

| ID | State of the system | Expected output | Implemented? |
| --- | --- | --- | --- |
| `SHUFFLE-CONTROLLER-1` | Game has been set up, current player's hand contains only `[SHUFFLE]`, `cardIndex = 0`, and draw pile contains more than one card. | Remove the Shuffle card from the hand, add it to the discard pile, and shuffle the draw pile. | :white_check_mark: |
| `SHUFFLE-CONTROLLER-2` | Game has been set up, current player's hand contains `[PLACEHOLDER_CARD, SHUFFLE]`, `cardIndex = 1`, and draw pile contains one card. | Remove only the Shuffle card, add it to the discard pile, and leave the one-card draw pile unchanged. | :white_check_mark: |
| `SHUFFLE-CONTROLLER-3` | Game has been set up, current player's hand contains only `[SHUFFLE]`, `cardIndex = 0`, and draw pile is empty. | Remove the Shuffle card, add it to the discard pile, and leave the empty draw pile unchanged. | :white_check_mark: |
| `SHUFFLE-CONTROLLER-4` | Game has been set up, current player's hand contains only `[PLACEHOLDER_CARD]`, `cardIndex = 0`, and draw pile contains more than one card. | Throw `IllegalArgumentException` with message `selected card is not a shuffle card`; hand, discard pile, and draw pile are unchanged. | :white_check_mark: |
| `SHUFFLE-CONTROLLER-5` | Game has been set up, current player's hand contains `[SHUFFLE]` and `cardIndex = -1`. | Throw `IllegalArgumentException` with message `cardIndex is out of bounds`; hand, discard pile, and draw pile are unchanged. | :white_check_mark: |
| `SHUFFLE-CONTROLLER-6` | Game has been set up, current player's hand contains `[SHUFFLE]` and `cardIndex = getHandSize()`. | Throw `IllegalArgumentException` with message `cardIndex is out of bounds`; hand, discard pile, and draw pile are unchanged. | :x: |
