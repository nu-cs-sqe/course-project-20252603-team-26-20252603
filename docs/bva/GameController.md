

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

| Step 1                         | Step 2                   | Step 3                                                                                                                                |
|--------------------------------|--------------------------|---------------------------------------------------------------------------------------------------------------------------------------|
| Input 1: `cardIndex`           | Array Index              | Values: <ul><li>0 (valid)</li><li>-1 (invalid)</li><li>getHandSize() (invalid)</li></ul>                                              |
| Input 2: current player's hand | Collection               | Values: <ul><li>Hand contains ATTACK</li><li>Hand does not contain ATTACK</li></ul>                                                   |
| Input 3: pending attack turns  | Count                    | Values: <ul><li>0</li><li>1</li><li>2 or more</li></ul>                                                                               |
| Output                         | State Change / Exception | Values: <ul><li>pendingAttackTurns increases by 2</li><li>Attack card removed from hand</li><li>Exception for invalid index</li></ul> |

### Step 4: Test Cases

| ID            | State of the system                                  | Expected output                                    | Implemented? |
|---------------|------------------------------------------------------|----------------------------------------------------|--------------|
| `GC-ATTACK-1` | Player hand = [`ATTACK`], pending = 0, cardIndex = 0 | pending becomes 2, card removed, message displayed | :x:          |
| `GC-ATTACK-2` | Player hand = [`ATTACK`], pending = 2, cardIndex = 0 | pending becomes 4 (stacking), card removed         | :x:          |
| `GC-ATTACK-3` | Player hand = [`BEARD_CAT`], cardIndex = 0           | Exception thrown, pending unchanged                | :x:          |
| `GC-ATTACK-4` | cardIndex = -1                                       | Exception thrown, pending unchanged                | :x:          |

