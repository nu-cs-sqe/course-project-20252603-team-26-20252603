# BVA Analysis for Skip Cards

## Supporting Test: `Card(CardType type)`

### Test Cases

- **TC1: constructorStoresSkipCardType** (:white_check_mark:)
    - **State of system**: Create `new Card(CardType.SKIP)`
    - **Expected output**: `card.getType()` returns `SKIP`.

## Method under test 1: `SkipController.play(Player player, int cardIndex)`

| Step 1 | Step 2 | Step 3 |
|---|---|---|
| Input 1: `player` | Object Reference | Values: <ul><li>`player` is valid</li</ul> |
| Input 2: `cardIndex` | Array Index | Values: <ul><li>Card Index is -1</li><li>Card Index is 0</li><li>Card Index is one larger than largest valid value `getHandSize()`</li></ul> |
| Input 3: selected card type | Card Type | Values: <ul><li>Selected card is `SKIP`</li><li>Selected card is not `SKIP`, such as `DEFUSE`</li></ul> |
| Output | Boolean / Exception / State Change | Values: <ul><li>Returns `true` when Skip is successfully played</li><li>Removes `SKIP` from player hand</li><li>Adds `SKIP` to discard pile</li><li>Throws exception for wrong card type</li><li>Throws exception for invalid index</li></ul> |

### Test Cases

- **TC2: playSkip_ReturnsTrue** (:white_check_mark:)
    - **State of system**: Player hand = [`SKIP`], `cardIndex = 0`
    - **Expected output**: Returns `true`.

- **TC3: playSkip_RemovesCardFromHand** (:white_check_mark:)
    - **State of system**: Player hand = [`SKIP`]
    - **Expected output**: Player hand size decreases to 0.

- **TC4: playSkip_AddsCardToDiscardPile** (:white_check_mark:)
    - **State of system**: Player hand = [`SKIP`], discard pile is empty
    - **Expected output**: Discard pile size becomes 1 and contains `SKIP`.

- **TC5: playSkip_SelectedCardIsDefuse_ThrowsException** (:white_check_mark:)
    - **State of system**: Player hand = [`DEFUSE`], `cardIndex = 0`
    - **Expected output**: `IllegalArgumentException` thrown.

- **TC6: playSkip_NegativeIndex_ThrowsException** (:white_check_mark:)
    - **State of system**: Player hand = [`SKIP`], `cardIndex = -1`
    - **Expected output**: Exception thrown.

- **TC7: playSkip_IndexEqualsHandSize_ThrowsException** (:white_check_mark:)
    - **State of system**: Player hand = [`SKIP`], `getHandSize() = 1`, `cardIndex = 1`
    - **Expected output**: Exception thrown.



## Method under test 2: `GameController.playSkip(int cardIndex)`

| Step 1 | Step 2 | Step 3 |
|---|---|---|
| Input 1: `cardIndex` | Array Index | Values: <ul><li>`-1`</li><li>`0`</li><li>`getHandSize()`</li></ul> |
| Internal state 1: current player hand | Card Collection | Values: <ul><li>Selected card is `SKIP`</li><li>Selected card is not `SKIP`, such as `DEFUSE`</li></ul> |
| Internal state 2: model/view/controller dependencies | Object References | Values: <ul><li>Valid `Game` model</li><li>Valid `GameView` view</li></ul> |
| Output | Boolean / UI Message / State Change | Values: <ul><li>Returns `true` when Skip is successfully played</li><li>Displays success message</li><li>Ends the turn without drawing</li><li>Advances to the next player</li><li>Returns `false` when selected card cannot be played</li><li>Displays error message for invalid input</li></ul> |

### Test Cases 
- **TC8: controllerPlaySkip_ValidSkip_ReturnsTrueAndDisplaysMessage** (:white_check_mark:)
  - **State of system**: Current player has [`SKIP`], `cardIndex = 0`, discard pile is empty
  - **Expected output**: Returns `true`, displays `"Skip played. Your turn ends without drawing a card."`, removes `SKIP` from hand, and adds it to discard pile.

- **TC13: playSkip_ValidSkip_AdvancesToNextPlayerWithoutDrawing** (:white_check_mark:)
  - **State of system**: Current player is `Sophie`, next player is `Jordan`, current player has [`SKIP`], and the draw pile has at least one card
  - **Expected output**: Returns `true`, removes and discards `SKIP`, leaves the draw pile unchanged, and advances the current player to `Jordan`.

- **TC9: playSkip_SelectedCardIsDefuse_ReturnsFalseAndDisplaysError** (:white_check_mark:)
  - **State of system**: Current player has [`DEFUSE`], `cardIndex = 0`, discard pile is empty
  - **Expected output**: Returns `false`, displays an error message, keeps `DEFUSE` in hand, and discard pile stays empty.

- **TC14: playSkip_InvalidCard_DoesNotAdvanceTurn** (:white_check_mark:)
  - **State of system**: Current player is `Sophie`, next player is `Jordan`, current player has [`DEFUSE`], and `cardIndex = 0`
  - **Expected output**: Returns `false`, displays an error message, keeps `DEFUSE` in hand, and keeps the current player as `Sophie`.

- **TC10: controllerPlaySkip_NegativeIndex_ReturnsFalseAndDisplaysError** (:white_check_mark:)
  - **State of system**: Current player has [`SKIP`], `cardIndex = -1`, discard pile is empty
  - **Expected output**: Returns `false`, displays an error message, keeps `SKIP` in hand, and discard pile stays empty.

- **TC11: controllerPlaySkip_IndexEqualsHandSize_ReturnsFalseAndDisplaysError** (:white_check_mark:)
  - **State of system**: Current player has [`SKIP`], `getHandSize() = 1`, `cardIndex = 1`, discard pile is empty
  - **Expected output**: Returns `false`, displays an error message, keeps `SKIP` in hand, and discard pile stays empty.

- **TC12: controllerPlaySkip_InvalidCard_DoesNotDisplaySuccessMessage** (:white_check_mark:)
  - **State of system**: Current player has [`DEFUSE`], `cardIndex = 0`
  - **Expected output**: Returns `false`, displays an error message, and does **not** display the success message.
