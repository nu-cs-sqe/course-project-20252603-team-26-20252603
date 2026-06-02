# BVA Analysis for `AttackCardController`

## Method under test: `public int play(Player player, int cardIndex)`

### Step 1-3 Results

| Step | Injected dependency: draw pile | Injected dependency: discard pile | Input: player | Input: selected card index | Input: selected card type | Output |
| --- | --- | --- | --- | --- | --- | --- |
| Step 1 | The draw pile that the attacking player does not draw from. | The discard pile that receives the played card. | The player choosing an Attack card. | Index of the card the current player chooses to play. | The card selected from the player's hand. | Selected Attack card is discarded, removed from hand, and `2` forced turns are returned, or an exception is thrown. |
| Step 2 | Collection | Collection | Pointer / Object state | Array Index | Cases | Integer / Collection state change / Exception |
| Step 3 | empty; one card; more than one card | initially empty discard pile | `-1`; `0`; largest valid index `getHandSize() - 1`; one larger than largest valid index `getHandSize()` | `ATTACK`; non-`ATTACK` | return `2`; hand loses selected Attack card; discard pile gains selected Attack card; draw pile stays unchanged; invalid input throws exception and leaves state unchanged |

### Step 4: Each-Choice Test Cases

| ID                    | State of the system | Expected output | Implemented? |
|-----------------------| --- | --- | --- |
| `ATTACK-CONTROLLER-1` | Controller is constructed with the draw pile and discard pile, player's hand contains only `[ATTACK]`, and `cardIndex = 0`. | Return `2`, remove the Attack card from the hand, and add it to the discard pile. | :white_check_mark: |
| `ATTACK-CONTROLLER-2` | Controller is constructed with the draw pile and discard pile, player's hand contains `[BEARD_CAT, ATTACK]`, and `cardIndex = 1`. | Return `2`, remove only the Attack card from the hand, and add it to the discard pile. | :white_check_mark: |
| `ATTACK-CONTROLLER-3` | Controller is constructed with the draw pile and discard pile, player's hand contains only `[BEARD_CAT]`, and `cardIndex = 0`. | Throw `IllegalArgumentException` with message `selected card is not an attack card`; hand and discard pile are unchanged. | :white_check_mark: |
| `ATTACK-CONTROLLER-4` | Controller is constructed with the draw pile and discard pile, player's hand contains `[ATTACK]`, and `cardIndex = -1`. | Throw `IllegalArgumentException` with message `cardIndex is out of bounds`; hand and discard pile are unchanged. | :white_check_mark: |
| `ATTACK-CONTROLLER-5` | Controller is constructed with the draw pile and discard pile, player's hand contains `[ATTACK]`, and `cardIndex = getHandSize()`. | Throw `IllegalArgumentException` with message `cardIndex is out of bounds`; hand and discard pile are unchanged. | :white_check_mark: |
| `ATTACK-CONTROLLER-6` | Controller is constructed with a multi-card draw pile and discard pile, player's hand contains only `[ATTACK]`, and `cardIndex = 0`. | Return `2` and leave the draw pile unchanged. | :white_check_mark: |
