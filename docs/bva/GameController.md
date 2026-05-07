

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



## Method under test: `handleExplodingKittenDrawn(Player)`
**Step 1: identify the input/outup equivilence classes**
- input: player, specifically a snapshot of their hand 
- output: player and game state. 

**Step 2:Choose relevant data type from BVA Catolog**
- input:
    - contents of collections, using a subset of the collection 
- output:
    -  contents of collections, using a subset of the collection
    - boolean --> win = T or F

**Step 3: Use the BVA catalog to select concrete values**
-input:
    - the subset is empty. --> [] or [PLACEHOLDER, PLACEHOLDER]
    - the subset contains exactly one element [PLACEHOLDER, PLACEHOLDER, DEFUSE]
    - the subset contains all but one element from the original. [PLACEHOLDER, DEFUSE, DEFUSE]
    - the subset is the same as the origi
## Method under test: `handleExplodingKittenDrawn(Player)`
**Step 1: identify the input/outup equivilence classes**
- input: player, specifically a snapshot of their hand
- output: player and game state.

**Step 2:Choose relevant data type from BVA Catolog**
- input:
    - contents of collections, using a subset of the collection
- output:
    -  contents of collections, using a subset of the collection
    - boolean --> win = T or F

**Step 3: Use the BVA catalog to select concrete values**
-input:
- the subset is empty. --> [] or [PLACEHOLDER, PLACEHOLDER]
- the subset contains exactly one element [PLACEHOLDER, PLACEHOLDER, DEFUSE]
- the subset contains all but one element from the original. [PLACEHOLDER, DEFUSE, DEFUSE]
- the subset is the same as the original. all defuse cars[DEFUSE, DEFUSE]
-output, the state of the player's hand:
- the subset is empty. --> [] or [PLACEHOLDER, PLACEHOLDER]
- the subset contains exactly one element [PLACEHOLDER, PLACEHOLDER, DEFUSE]
- the subset contains all but one element from the original. [PLACEHOLDER, DEFUSE, DEFUSE]
- the subset is the same as the original. all defuse cars[DEFUSE, DEFUSE]
-output, the state of the game:
- 0 (the basic false value): multiple extra players remain. playerCount >= 3
- 0: only one extra player remains. playerCount == 2
- 1 (normal true value): one player remains. playerCount == 1
- some true value other than 1
- are there any other win conditions?
- If the code is calculating a boolean, consider inputs for which the result is neither
true nor false.nal. all defuse cars[DEFUSE, DEFUSE]
-output, the state of the player's hand:
    - the subset is empty. --> [] or [PLACEHOLDER, PLACEHOLDER]
    - the subset contains exactly one element [PLACEHOLDER, PLACEHOLDER, DEFUSE]
    - the subset contains all but one element from the original. [PLACEHOLDER, DEFUSE, DEFUSE]
    - the subset is the same as the original. all defuse cars[DEFUSE, DEFUSE]
-output, the state of the game:
    - 0 (the basic false value): multiple extra players remain. playerCount >= 3
    - 0: only one extra player remains. playerCount == 2
    - 1 (normal true value): one player remains. playerCount == 1
    - some true value other than 1 
        - are there any other win conditions?
    - If the code is calculating a boolean, consider inputs for which the result is neither
true nor false.

**Step 4**


| ID                      | State of the system                                            | Expected output                                                        | Implemented? |
|-------------------------|----------------------------------------------------------------|------------------------------------------------------------------------|--------------|
| `GCEmpty `              | Draw EK with an empty hand, multiple players left              | game does not end,  playerm count decreases, current player eliminated | :white_check_mark:   |
| `GC1Player `            | Draw EK with a non empty hand, but no defuse card              | not enough players exception , gameStarted = False                     | :white_check_mark:     |
| `GC2Player `            | Draw EK with non empty hand and no defuse card                 | current player eliminated, player count decreases, Game over  = T      | :white_check_mark:      |
| `GCMultiPlayer `        | Draw EK with one defuse card                                   | number of defuse cards in hand decreases, game over = F             | :white_check_mark:     |
| `GCInvalidMultiPlayer ` | Draw EK with all but cards in hand except 1 being defuse cards | number of defuse cards in hand decreases, game over = F                | :white_check_mark:      |
| `GCMaxPlayer `          | Draw EK with a hand of only defuse cards                       | number of defuse cards in hand decreases, game over = F                           | :x_mark:     |
| `GCMultiPlayer `        | duplicate player  names                                        | Confirm and rename players exception , gameStarted = False             | :x_mark:     |