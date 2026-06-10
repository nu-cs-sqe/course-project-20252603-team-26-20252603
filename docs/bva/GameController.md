

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
| Input: selected card indexes to play | Collection | Values: <ul><li>Empty list</li><li>One selected Skip card index</li><li>One selected See the Future card index</li><li>More than one selected See the Future card index</li><li>One selected Shuffle card index</li><li>One selected Bury card index</li><li>Selected See the Future followed by selected Skip</li><li>Selected Skip followed by another card</li><li>One selected unplayable card index</li><li>One selected index equal to hand size</li><li>One negative selected index</li></ul> |
| Internal state: draw pile           | Collection | Values: <ul><li>One drawable non-Exploding-Kitten card</li><li>Two cards available for See the Future</li><li>Two cards available to shuffle before drawing</li><li>Three cards available to bury the top card and draw the newly exposed card</li></ul> |
| Output                              | State / UI | Values: <ul><li>Displays hand</li><li>Draws one card</li><li>Advances to next player</li><li>Ends turn without drawing when Skip is played</li><li>Displays future cards, then draws when See the Future is played</li><li>Shuffles the draw pile, then draws when Shuffle is played</li><li>Moves the top card to the bottom, then draws when Bury is played</li><li>Ends turn without drawing when Skip is played after a non-ending card</li><li>Ignores later selected cards after Skip ends the turn</li><li>Displays error for an unplayable selected card</li><li>Displays error for an out-of-bounds selected index</li></ul> |

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

- **TC9: completeTurn_UnplayableCard_DisplaysErrorThenDrawsAndAdvances** (:white_check_mark:)
  - **State of system**: Current player is `Sophie`, next player is `Jordan`, selected card indexes are `[0]`, card index `0` is `DEFUSE`, and the draw pile top card is `PLACEHOLDER_CARD`.
  - **Expected output**: Displays `Sophie`'s hand, displays an error because `DEFUSE` cannot be played during a normal turn, keeps `DEFUSE` in `Sophie`'s hand, draws the top card, and advances current player to `Jordan`.

- **TC10: completeTurn_IndexEqualsHandSize_DisplaysErrorThenDrawsAndAdvances** (:white_check_mark:)
  - **State of system**: Current player is `Sophie`, next player is `Jordan`, selected card index is equal to `Sophie`'s hand size, and the draw pile top card is `PLACEHOLDER_CARD`.
  - **Expected output**: Displays `Sophie`'s hand, displays an out-of-bounds error, keeps the hand's existing card, draws the top card, and advances current player to `Jordan`.

- **TC11: completeTurn_NegativeIndex_DisplaysErrorThenDrawsAndAdvances** (:white_check_mark:)
  - **State of system**: Current player is `Sophie`, next player is `Jordan`, selected card index is `-1`, and the draw pile top card is `PLACEHOLDER_CARD`.
  - **Expected output**: Displays `Sophie`'s hand, displays an out-of-bounds error, keeps the hand's existing card, draws the top card, and advances current player to `Jordan`.

- **TC12: completeTurn_AttackPlayed_DiscardsAttackThenEndsWithoutDrawing** (:white_check_mark:)
  - **State of system**: Current player is `Sophie`, next player is `Jordan`, selected card indexes are `[0]`, card index `0` is `ATTACK`, and the draw pile has one card.
  - **Expected output**: Displays `Sophie`'s hand, plays and discards `ATTACK`, leaves the draw pile unchanged (turn ends without drawing), and advances current player to `Jordan`.

- **TC13: completeTurn_AfterAttack_AttackedPlayerMustTakeSecondTurn** (:white_check_mark:)
  - **State of system**: Two players `Sophie` and `Jordan`; `Sophie` plays `ATTACK`, then `Jordan` completes one normal (drawing) turn.
  - **Expected output**: After the Attack, `Jordan` is current and owes 2 turns; after `Jordan`'s first turn the current player is still `Jordan` (the forced second turn).

- **TC14: completeTurn_SkipAsAttackDefense_EndsOnlyOneForcedTurnPerSkip** (:white_check_mark:)
  - **State of system**: `Sophie` attacks `Jordan` (forcing 2 turns); `Jordan` holds two `SKIP` cards and plays one Skip per turn.
  - **Expected output**: The first Skip ends only one forced turn so `Jordan` stays current with one Skip left; the second Skip ends the last forced turn and passes play to `Sophie`, and no card is drawn for either Skip.

- **TC15: completeTurn_BuryPlayed_MovesTopCardThenDrawsAndAdvances** (:white_check_mark:)
  - **State of system**: Current player is `Sophie`, next player is `Jordan`, selected card indexes are `[0]`, card index `0` is `BURY`, and the draw pile contains three known cards.
  - **Expected output**: Displays `Sophie`'s hand, plays and discards `BURY`, moves the former top card to the bottom, draws the newly exposed top card, adds it to `Sophie`'s hand, and advances current player to `Jordan`.

_Attack is now played through `completeTurn` (see TC12–TC14) and the forced-turn
mechanics live in the `Game` model (`applyAttack` / `advanceTurn`); see `Game.md`._

- **TC15: completeTurn_DrawFromBottomPlayed_DrawsBottomCardAndAdvances** (:white_check_mark:)
  - **State of the system**: current player is `Sophie`, next player is `Jordan`, selected card index is `[0]`, card at index `0` is `DRAW_FROM_BOTTOM`, deck has `[SKIP, ATTACK]` where `SKIP` is at the bottom
  - **Expected output**: `DRAW_FROM_BOTTOM` discarded, `SKIP` added to `Sophie`'s hand, turn advances to `Jordan`

- **TC16: completeTurn_DrawFromBottomDrawsExplodingKittenWithDefuse_DefusesAndAdvances** (:white_check_mark:)
    - **State of the system**: current player is `Sophie`, next player is `Jordan`, selected card index is `[0]`, card at index `0` is `DRAW_FROM_BOTTOM`, `Sophie` has a `DEFUSE`, deck has `[EXPLODING_KITTEN, ATTACK]` where `EXPLODING_KITTEN` is at the bottom
    - **Expected output**: `DRAW_FROM_BOTTOM` discarded, `DEFUSE` discarded, `EXPLODING_KITTEN` returned to deck, turn advances to `Jordan`

- **TC17: completeTurn_DrawFromBottomDrawsExplodingKittenWithoutDefuse_PlayerEliminated** (:white_check_mark:)
    - **State of the system**: current player is `Sophie`, next player is `Jordan`, selected card index is `[0]`, card at index `0` is `DRAW_FROM_BOTTOM`, `Sophie` has no `DEFUSE`, deck has `[EXPLODING_KITTEN, ATTACK]` where `EXPLODING_KITTEN` is at the bottom
    - **Expected output**: `DRAW_FROM_BOTTOM` discarded, `Sophie` is eliminated, `Jordan` is the only remaining player
- **TC18: completeTurn_TargetedAttackPlayed_PromptsTargetDiscardsCardAndEndsWithoutDrawing** (:x: or :white_check_mark:)
  - **State of the system**: current player is `Sophie`, next player is `Jordan`, `Sophie` has one `TARGETED_ATTACK` card, user selects `Jordan` as target, draw pile has one card
  - **Expected output**: displays `Sophie`'s hand, prompts for target, `TARGETED_ATTACK` discarded, current player is `Jordan`, forced turns is `2`, draw pile unchanged

### Method under test: `public void playTargetedAttack(int cardIndex)`
| Step 1                        | Step 2     | Step 3                                                                                                                                    |
|-------------------------------|------------|-------------------------------------------------------------------------------------------------------------------------------------------|
| Input 1: card index           | Integer    | Values: <ul><li>negative index</li><li>index equal to hand size</li><li>valid index pointing to `TARGETED_ATTACK`</li><li>valid index pointing to a non-`TARGETED_ATTACK` card</li></ul> |
| Internal state: players       | Collection | Values: <ul><li>two players</li></ul>                                                                                                    |
| Output: side effects          | State / UI | Values: <ul><li>throws or displays error</li><li>prompts for target, discards card, sets target as current player with two forced turns</li></ul> |

- **Step 4:**
    - **TC1: playTargetedAttack_NegativeIndex_DisplaysError** (:x: or :white_check_mark:)
        - **State of the system**: current player is `Sophie`, card index is `-1`
        - **Expected output**: displays error, turn does not advance

    - **TC2: playTargetedAttack_IndexEqualsHandSize_DisplaysError** (:x: or :white_check_mark:)
        - **State of the system**: current player is `Sophie` with one `TARGETED_ATTACK` card, card index equals hand size
        - **Expected output**: displays error, turn does not advance

    - **TC3: playTargetedAttack_NotTargetedAttackCard_DisplaysError** (:x: or :white_check_mark:)
        - **State of the system**: current player is `Sophie` with one `SKIP` card, card index is `0`
        - **Expected output**: displays error, turn does not advance

    - **TC4: playTargetedAttack_ValidCard_PromptsTargetDiscardsCardAndAppliesAttack** (:x: or :white_check_mark:)
        - **State of the system**: current player is `Sophie`, next player is `Jordan`, `Sophie` has one `TARGETED_ATTACK` card, user selects `Jordan` as target
        - **Expected output**: `TARGETED_ATTACK` discarded, current player is `Jordan`, forced turns is `2`

- **TC15: completeTurn_DrawFromBottomPlayed_DrawsBottomCardAndAdvances** (:x:white_check_mark:)
  - **State of the system**: current player is `Sophie`, next player is `Jordan`, selected card index is `[0]`, card at index `0` is `DRAW_FROM_BOTTOM`, deck has `[SKIP, ATTACK]` where `SKIP` is at the bottom
  - **Expected output**: `DRAW_FROM_BOTTOM` discarded, `SKIP` added to `Sophie`'s hand, turn advances to `Jordan`

- **TC16: completeTurn_DrawFromBottomDrawsExplodingKittenWithDefuse_DefusesAndAdvances** (:x:white_check_mark:)
    - **State of the system**: current player is `Sophie`, next player is `Jordan`, selected card index is `[0]`, card at index `0` is `DRAW_FROM_BOTTOM`, `Sophie` has a `DEFUSE`, deck has `[EXPLODING_KITTEN, ATTACK]` where `EXPLODING_KITTEN` is at the bottom
    - **Expected output**: `DRAW_FROM_BOTTOM` discarded, `DEFUSE` discarded, `EXPLODING_KITTEN` returned to deck, turn advances to `Jordan`

- **TC17: completeTurn_DrawFromBottomDrawsExplodingKittenWithoutDefuse_PlayerEliminated** (:x:white_check_mark:)
    - **State of the system**: current player is `Sophie`, next player is `Jordan`, selected card index is `[0]`, card at index `0` is `DRAW_FROM_BOTTOM`, `Sophie` has no `DEFUSE`, deck has `[EXPLODING_KITTEN, ATTACK]` where `EXPLODING_KITTEN` is at the bottom
    - **Expected output**: `DRAW_FROM_BOTTOM` discarded, `Sophie` is eliminated, `Jordan` is the only remaining player
