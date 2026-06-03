

# BVA Analysis for `GameController`
Class definition:

` 
class GameController {
private Game game;
    public void startGame(List<String> playerNames);
} 
`

## Method under test: `startGame(List<String> playerNames)`
**Step 1: identify the input/outup equivilence classes**
- input: group/collection/list of player names
- output: None --> start the game

**Step 2:Choose relevant data type from BVA Catolog**
  - input: 
    - colelctions
    - strings
  - output: 
    - none 
    - check the state of the game: has it started or not --> Boolean
**Step 3: Use the BVA catalog to select concrete values**
  - input
    - empty / no player: []
    - only one player: ['one player']
    - more than one element: ['player one','player 2', 'player 3']
    - maz possible size / max number of players (5): ['player one','player 2', 'player 3', 'player4', player5']
    - contains duplicate elements:
      - only applicable if we start by entering player names (we do)
      - ["alex", "alex"]
        - sounds like a valid case, I would suggest a flow where the second user is prompted to add a number / nickname to differentiate
    - **Sequenceable Collection**
      - not important unless this method has control over the order players take turns
    - **Strings:**
      - the empty string ['']
      - do we need to check if it's not a string
  - output
    - 0 / False / Game Not Started
    - 1 / True / Game Started
    - there are no circumastances where neither is true
**Step 4:**

| ID                      | State of the system                                | Expected output                                              | Implemented? |
|-------------------------|----------------------------------------------------|--------------------------------------------------------------|--------------|
| `GCEmpty `              | no players                                         | not enough players exception ,   gameStarted = False         | :white_check_mark:   |
| `GC1Player `            | one player                                         | not enough players exception , gameStarted = False           | :white_check_mark:     |
| `GC2Player `            | two players (min # valid players)                  | None: gameStarted = True                                     | :white_check_mark:      |
| `GCMultiPlayer `        | multiple players, all valid                        | None: Game starts    , gameStarted = True                    | :white_check_mark:     |
| `GCInvalidMultiPlayer ` | multiple player, at least one invald (empty sting) | invalid player name/type exception ,     gameStarted = False | :white_check_mark:      |
| `GCMaxPlayer `          | max number of players                              | None: game starts          , gameStarted = True              | :x_mark:     |
| `GCMultiPlayer `        | duplicate player  names                            | Confirm and rename players exception , gameStarted = False   | :x_mark:     |


## Method under test: `playAttackCard(int cardIndex)`

| Step 1                          | Step 2                    | Step 3                                                                                                                                 |
|---------------------------------|---------------------------|----------------------------------------------------------------------------------------------------------------------------------------|
| Input 1: `cardIndex`            | Array Index               | Values: <ul><li>0 (valid)</li><li>-1 (invalid)</li></ul>                                                                               |
| Input 2: current player's hand  | Collection                | Values: <ul><li>Hand contains ATTACK</li><li>Hand does not contain ATTACK</li></ul>                                                    |
| Input 3: pending attack turns   | Count                     | Values: <ul><li>0</li><li>1</li><li>2</li><li>3</li></ul>                                                                              |
| Input 4: current turn number    | Count                     | Values: <ul><li>Turn 1 of pending sequence</li><li>Turn 2 (or later) of pending sequence</li><li>Turn N (last turn)</li></ul>          |
| Output                          | State Change / Exception  | Values: <ul><li>pendingAttackTurns = remaining + 2</li><li>Attack card removed from hand</li><li>Exception for invalid input</li></ul> |

### Step 4: Test Cases

| ID            | State of the system                                                                  | Expected output                                                    | Implemented? |
|---------------|--------------------------------------------------------------------------------------|--------------------------------------------------------------------|--------------|
| `GC-ATTACK-1` | No pending turns, play Attack                                                        | pending = 2, card removed                                          | :x:          |
| `GC-ATTACK-2` | pending = 2 (turn 1 of 2), play Attack                                               | pending = 3 (1 remaining + 2), card removed                        | :x:          |
| `GC-ATTACK-3` | pending = 2 (turn 2 of 2), play Attack                                               | pending = 2 (0 remaining + 2), card removed                        | :x:          |
| `GC-ATTACK-4` | pending = 3 (turn 1 of 3), play Attack                                               | pending = 4 (2 remaining + 2), card removed                        | :x:          |
| `GC-ATTACK-5` | pending = 3 (turn 2 of 3), play Attack                                               | pending = 3 (1 remaining + 2), card removed                        | :x:          |
| `GC-ATTACK-6` | pending = 3 (turn 3 of 3), play Attack                                               | pending = 2 (0 remaining + 2), card removed                        | :x:          |
| `GC-ATTACK-7` | Player hand does not contain ATTACK                                                  | Exception thrown, pending unchanged                                | :x:          |
| `GC-ATTACK-8` | Invalid cardIndex (-1)                                                               | Exception thrown, pending unchanged                                | :x:          |

- **`GC-ATTACK-1`: playAttackCard_NoPendingTurns_AddsTwoTurns** ( :x: )
    - **State of system**: Current player hand = [`ATTACK`], pendingAttackTurns = 0, cardIndex = 0
    - **Expected output**: pendingAttackTurns becomes 2, card removed from hand

- **`GC-ATTACK-2`: playAttackCard_OnFirstTurnOfTwo_AddsOneRemainingPlusTwo** ( :x: )
    - **State of system**: Current player hand = [`ATTACK`], pendingAttackTurns = 2 (turn 1 of 2), cardIndex = 0
    - **Expected output**: pendingAttackTurns becomes 3 (1 remaining + 2), card removed

- **`GC-ATTACK-3`: playAttackCard_OnSecondTurnOfTwo_AddsZeroRemainingPlusTwo** ( :x: )
    - **State of system**: Current player hand = [`ATTACK`], pendingAttackTurns = 2 (turn 2 of 2), cardIndex = 0
    - **Expected output**: pendingAttackTurns becomes 2 (0 remaining + 2), card removed

- **`GC-ATTACK-4`: playAttackCard_OnFirstTurnOfThree_AddsTwoRemainingPlusTwo** ( :x: )
    - **State of system**: Current player hand = [`ATTACK`], pendingAttackTurns = 3 (turn 1 of 3), cardIndex = 0
    - **Expected output**: pendingAttackTurns becomes 4 (2 remaining + 2), card removed

- **`GC-ATTACK-5`: playAttackCard_OnSecondTurnOfThree_AddsOneRemainingPlusTwo** ( :x: )
    - **State of system**: Current player hand = [`ATTACK`], pendingAttackTurns = 3 (turn 2 of 3), cardIndex = 0
    - **Expected output**: pendingAttackTurns becomes 3 (1 remaining + 2), card removed

- **`GC-ATTACK-6`: playAttackCard_OnThirdTurnOfThree_AddsZeroRemainingPlusTwo** ( :x: )
    - **State of system**: Current player hand = [`ATTACK`], pendingAttackTurns = 3 (turn 3 of 3), cardIndex = 0
    - **Expected output**: pendingAttackTurns becomes 2 (0 remaining + 2), card removed

- **`GC-ATTACK-7`: playAttackCard_NonAttackCard_ThrowsException** ( :x: )
    - **State of system**: Current player hand = [`BEARD_CAT`], pendingAttackTurns = 0, cardIndex = 0
    - **Expected output**: IllegalArgumentException thrown, pending unchanged, hand unchanged

- **`GC-ATTACK-8`: playAttackCard_NegativeIndex_ThrowsException** ( :x: )
    - **State of system**: Current player hand = [`ATTACK`], cardIndex = -1
    - **Expected output**: IllegalArgumentException thrown, pending unchanged, hand unchanged