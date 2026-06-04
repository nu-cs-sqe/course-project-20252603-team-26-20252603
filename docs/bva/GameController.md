

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

## Method under test: `startTurn()`
| Step 1                       | Step 2     | Step 3                                        |
|------------------------------|------------|-----------------------------------------------|
| Internal state: current player | Object     | Values: <ul><li>Player named `Sophie`</li></ul> |
| Internal state: current hand | Collection | Values: <ul><li>More than one card</li></ul> |
| Output                       | UI Message | Values: <ul><li>Displays the current player's hand</li></ul> |

- **TC1: startTurn_DisplaysCurrentPlayerHand** (:white_check_mark:)
  - **State of system**: Current player is `Sophie` with [`SKIP`, `BEARD_CAT`] in hand.
  - **Expected output**: Calls the view to display `Sophie`'s hand with those cards.

## Method under test: `completeTurn(List<Integer> cardIndexes)`
| Step 1                              | Step 2     | Step 3                                  |
|-------------------------------------|------------|-----------------------------------------|
| Input: selected card indexes to play | Collection | Values: <ul><li>Empty list</li><li>One selected Skip card index</li><li>One selected See the Future card index</li><li>More than one selected See the Future card index</li><li>One selected Shuffle card index</li><li>Selected See the Future followed by selected Skip</li><li>Selected Skip followed by another card</li></ul> |
| Internal state: draw pile           | Collection | Values: <ul><li>One drawable non-Exploding-Kitten card</li><li>Two cards available for See the Future</li><li>Two cards available to shuffle before drawing</li></ul> |
| Output                              | State / UI | Values: <ul><li>Displays hand</li><li>Draws one card</li><li>Advances to next player</li><li>Ends turn without drawing when Skip is played</li><li>Displays future cards, then draws when See the Future is played</li><li>Shuffles the draw pile, then draws when Shuffle is played</li><li>Ends turn without drawing when Skip is played after a non-ending card</li><li>Ignores later selected cards after Skip ends the turn</li></ul> |

- **TC2: completeTurn_NoCardsPlayed_DisplaysHandThenDrawsAndAdvances** (:white_check_mark:)
  - **State of system**: Current player is `Sophie`, next player is `Jordan`, selected card indexes are `[]`, and the draw pile top card is `PLACEHOLDER_CARD`.
  - **Expected output**: Displays `Sophie`'s hand, draws the `PLACEHOLDER_CARD`, adds it to `Sophie`'s hand, and advances current player to `Jordan`.

- **TC3: completeTurn_SkipPlayed_DisplaysHandThenEndsWithoutDrawing** (:white_check_mark:)
  - **State of system**: Current player is `Sophie`, next player is `Jordan`, selected card indexes are `[0]`, card index `0` is `SKIP`, and the draw pile has one card.
  - **Expected output**: Displays `Sophie`'s hand, plays and discards `SKIP`, leaves the draw pile unchanged, and advances current player to `Jordan`.

- **TC4: completeTurn_SeeFuturePlayed_DisplaysFutureThenDrawsAndAdvances** (:white_check_mark:)
  - **State of system**: Current player is `Sophie`, next player is `Jordan`, selected card indexes are `[0]`, card index `0` is `SEE_THE_FUTURE`, and the draw pile has two cards.
  - **Expected output**: Displays `Sophie`'s hand, plays and discards `SEE_THE_FUTURE`, displays the top two draw pile cards, draws the top card, and advances current player to `Jordan`.

- **TC5: completeTurn_TwoSeeFutureCardsPlayed_DisplaysBothThenDrawsAndAdvances** (:white_check_mark:)
  - **State of system**: Current player is `Sophie`, next player is `Jordan`, selected card indexes are `[0, 0]`, both selected cards are `SEE_THE_FUTURE`, and the draw pile has two cards.
  - **Expected output**: Displays `Sophie`'s hand, plays and discards both `SEE_THE_FUTURE` cards, displays the top two draw pile cards after each play, draws the top card, and advances current player to `Jordan`.

- **TC6: completeTurn_ShufflePlayed_ShufflesThenDrawsAndAdvances** (:white_check_mark:)
  - **State of system**: Current player is `Sophie`, next player is `Jordan`, selected card indexes are `[0]`, card index `0` is `SHUFFLE`, and the draw pile has two cards with injected deterministic randomness.
  - **Expected output**: Displays `Sophie`'s hand, plays and discards `SHUFFLE`, shuffles the draw pile using the injected random source, draws the new top card, and advances current player to `Jordan`.

- **TC7: completeTurn_SeeFutureThenSkipPlayed_EndsWithoutDrawing** (:white_check_mark:)
  - **State of system**: Current player is `Sophie`, next player is `Jordan`, selected card indexes are `[0, 0]`, the first selected card is `SEE_THE_FUTURE`, the second selected card is `SKIP`, and the draw pile has two cards.
  - **Expected output**: Displays `Sophie`'s hand, plays and discards `SEE_THE_FUTURE`, displays the top two draw pile cards, plays and discards `SKIP`, leaves the draw pile unchanged, and advances current player to `Jordan`.

- **TC8: completeTurn_SkipThenSeeFuturePlayed_IgnoresCardsAfterTurnEnds** (:white_check_mark:)
  - **State of system**: Current player is `Sophie`, next player is `Jordan`, selected card indexes are `[0, 0]`, the first selected card is `SKIP`, the next selected card would be `SEE_THE_FUTURE`, and the draw pile has two cards.
  - **Expected output**: Displays `Sophie`'s hand, plays and discards `SKIP`, leaves `SEE_THE_FUTURE` in `Sophie`'s hand, does not display future cards, leaves the draw pile unchanged, and advances current player to `Jordan`.
