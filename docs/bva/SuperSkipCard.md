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
| `SUPER-END-3` | forcedTurns = 1  | forcedTurns becomes 0, next player | :x:          |


## Method under test 2: `SuperSkipCardController.play(Player player, int cardIndex)`

| Step    | Input                  | Type        | Values                              |
|---------|------------------------|-------------|-------------------------------------|
| Input 1 | `player`               | Object      | Valid player, null                  |
| Input 2 | `cardIndex`            | Array Index | -1, 0, handSize-1, handSize         |
| Input 3 | Selected card type     | Cases       | `SUPER_SKIP`, not `SUPER_SKIP`      |
| Output  | Boolean / State Change | Values      | `true` if played, `false` otherwise |

### Step 4: Test Cases

| ID        | State of system                        | Expected output                                    | Implemented?  |
|-----------|----------------------------------------|----------------------------------------------------|---------------|
| `SUPER-1` | Player has SUPER_SKIP, forcedTurns = 2 | forcedTurns becomes 0, next player, card discarded | :x:           |
| `SUPER-2` | Player has SUPER_SKIP, forcedTurns = 0 | forcedTurns stays 0, next player, card discarded   | :x:           |
| `SUPER-3` | Player has non-SUPER_SKIP card         | Exception thrown                                   | :x:           |
| `SUPER-4` | Invalid card index (-1)                | Exception thrown                                   | :x:           |
| `SUPER-5` | Index equals handSize                  | Exception thrown                                   | :x:           |

- **`SUPER-1`: superSkip_WithForcedTurns_EndsAllForcedTurns** ( :x: )
    - **State of system**: Player hand = [`SUPER_SKIP`], forcedTurns = 2, cardIndex = 0
    - **Expected output**: forcedTurns becomes 0, card removed from hand, next player's turn

- **`SUPER-2`: superSkip_WithNoForcedTurns_EndsTurnNormally** ( :x: )
    - **State of system**: Player hand = [`SUPER_SKIP`], forcedTurns = 0, cardIndex = 0
    - **Expected output**: forcedTurns stays 0, card removed, next player's turn

- **`SUPER-3`: superSkip_NonSuperSkipCard_ThrowsException** ( :x: )
    - **State of system**: Player hand = [`ATTACK`], cardIndex = 0
    - **Expected output**: IllegalArgumentException thrown

- **`SUPER-4`: superSkip_NegativeIndex_ThrowsException** ( :x: )
    - **State of system**: Player hand = [`SUPER_SKIP`], cardIndex = -1
    - **Expected output**: IllegalArgumentException thrown

- **`SUPER-5`: superSkip_IndexEqualsHandSize_ThrowsException** ( :x: )
    - **State of system**: Player hand = [`SUPER_SKIP`], handSize = 1, cardIndex = 1
    - **Expected output**: IllegalArgumentException thrown