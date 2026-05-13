# BVA Analysis for Skip Cards

## Supporting Test: `Card(CardType type)`

### Test Cases

- **TC1: constructorStoresSkipCardType** (:x:)
    - **State of system**: Create `new Card(CardType.SKIP)`
    - **Expected output**: `card.getType()` returns `SKIP`.

## Method under test 1: `SkipController.play(Player player, int cardIndex)`

| Step 1 | Step 2 | Step 3 |
|---|---|---|
| Input 1: `player` | Object Reference | Values: <ul><li>`player` is valid</li><li>`player` is `null`</li></ul> |
| Input 2: `cardIndex` | Array Index | Values: <ul><li>Card Index is -1</li><li>Card Index is 0</li><li>Card Index is one larger than largest valid value `getHandSize()`</li></ul> |
| Input 3: selected card type | Card Type | Values: <ul><li>Selected card is `SKIP`</li><li>Selected card is not `SKIP`, such as `DEFUSE`</li></ul> |
| Output | Boolean / Exception / State Change | Values: <ul><li>Returns `true` when Skip is successfully played</li><li>Removes `SKIP` from player hand</li><li>Adds `SKIP` to discard pile</li><li>Throws exception for wrong card type</li><li>Throws exception for invalid index</li><li>Throws exception for null player</li></ul> |

### Test Cases

- **TC2: playSkip_ReturnsTrue** (:x:)
    - **State of system**: Player hand = [`SKIP`], `cardIndex = 0`
    - **Expected output**: Returns `true`.

- **TC3: playSkip_RemovesCardFromHand** (:x:)
    - **State of system**: Player hand = [`SKIP`]
    - **Expected output**: Player hand size decreases to 0.

- **TC4: playSkip_AddsCardToDiscardPile** (:x:)
    - **State of system**: Player hand = [`SKIP`], discard pile is empty
    - **Expected output**: Discard pile size becomes 1 and contains `SKIP`.

- **TC5: playSkip_SelectedCardIsDefuse_ThrowsException** (:x:)
    - **State of system**: Player hand = [`DEFUSE`], `cardIndex = 0`
    - **Expected output**: `IllegalArgumentException` thrown.

- **TC6: playSkip_NegativeIndex_ThrowsException** (:x:)
    - **State of system**: Player hand = [`SKIP`], `cardIndex = -1`
    - **Expected output**: Exception thrown.

- **TC7: playSkip_IndexEqualsHandSize_ThrowsException** (:x:)
    - **State of system**: Player hand = [`SKIP`], `getHandSize() = 1`, `cardIndex = 1`
    - **Expected output**: Exception thrown.

- **TC8: playSkip_NullPlayer_ThrowsException** (:x:)
    - **State of system**: `player = null`
    - **Expected output**: Exception thrown.