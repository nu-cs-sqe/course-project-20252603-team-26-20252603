# BVA Analysis for Super Skip Card

## Method under test 1: `Game.endTurnClearingForced()`

| Step   | Input              | Type        | Values                              |
|--------|--------------------|-------------|-------------------------------------|
| Step 1 | forcedTurns        | Count       | Number of pending forced turns      |
| Step 2 | Same               | Count       | 0, 1, 2, 3                          |
| Step 3 | currentPlayerIndex | Array Index | Position in players list            |
| Output | State change       | Values      | forcedTurns = 0, next player's turn |

### Step 4: Test Cases

| ID            | State of system  | Expected output                    | Implemented? |
|---------------|------------------|------------------------------------|--------------|
| `SUPER-END-1` | forcedTurns = 0  | forcedTurns stays 0, next player   | yes          |
| `SUPER-END-2` | forcedTurns = 2  | forcedTurns becomes 0, next player | yes          |
| `SUPER-END-3` | forcedTurns = 1  | forcedTurns becomes 0, next player | yes          |


## Method under test 2: `SuperSkipCardController.play(Player player, int cardIndex)`

| Step    | Input                  | Type        | Values                              |
|---------|------------------------|-------------|-------------------------------------|
| Input 1 | `player`               | Object      | Valid player, null                  |
| Input 2 | `cardIndex`            | Array Index | -1, 0, handSize-1, handSize         |
| Input 3 | Selected card type     | Cases       | `SUPER_SKIP`, not `SUPER_SKIP`      |
| Output  | Boolean / State Change | Values      | `true` if played, `false` otherwise |

### Step 4: Test Cases

| ID        | State of system                        | Expected output                                    | Implemented? |
|-----------|----------------------------------------|----------------------------------------------------|--------------|
| `SUPER-1` | Player has SUPER_SKIP, forcedTurns = 2 | forcedTurns becomes 0, next player, card discarded | yes          |
| `SUPER-2` | Player has SUPER_SKIP, forcedTurns = 0 | forcedTurns stays 0, next player, card discarded   | yes          |
| `SUPER-3` | Player has non-SUPER_SKIP card         | Exception thrown                                   | yes          |
| `SUPER-4` | Invalid card index (-1)                | Exception thrown                                   | yes          |
| `SUPER-5` | Index equals handSize                  | Exception thrown                                   | :x:          |