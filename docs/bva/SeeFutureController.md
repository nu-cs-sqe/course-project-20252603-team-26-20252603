# BVA Analysis for See the Future Cards
### Method under test: `public List<Card> play(Player player, int cardIndex)`

| Step 1 | Step 2 | Step 3 |
|---|---|---|
| Input 1: `player` | Object Reference | Values: <ul><li>`player` is valid</li><li>`player` is `null`</li></ul> |
| Input 2: `cardIndex` | Array Index | Values: <ul><li>Card Index is -1</li><li>Card Index is 0</li><li>Card Index is largest valid value `getHandSize() - 1`</li><li>Card Index is one larger than largest valid value `getHandSize()`</li><li>Hand is empty</li></ul> |
| Input 3: selected card type | Card Type | Values: <ul><li>Selected card is `SEE_THE_FUTURE`</li><li>Selected card is `DEFUSE`</li><li>Selected card is a Cat Card</li></ul> |
| Input 4: draw pile size | Deck Size | Values: <ul><li>Draw pile has 0 cards</li><li>Draw pile has 1 card</li><li>Draw pile has exactly 2 cards</li><li>Draw pile has more than 2 cards</li></ul> |
| Output | List / Exception / State Change | Values: <ul><li>Returns empty list when draw pile is empty</li><li>Returns 1 card when draw pile has 1 card</li><li>Returns top 2 cards when draw pile has 2 or more cards</li><li>Draw pile order and size stay unchanged</li><li>`SEE_THE_FUTURE` card is removed from player hand</li><li>`SEE_THE_FUTURE` card is added to discard pile</li><li>Exception thrown for invalid player, invalid index, or wrong card type</li></ul> |

## Step 4: Test Cases

- **TC1: playSeeFuture_EmptyDrawPile_ReturnsEmptyList** (:x:)
    - **State of system**: Player hand = [`SEE_THE_FUTURE`], draw pile = []
    - **Expected output**: Returns empty list.

- **TC2: playSeeFuture_OneCardInDrawPile_ReturnsOneCard** (:x:)
    - **State of system**: Player hand = [`SEE_THE_FUTURE`], draw pile = [`DEFUSE`]
    - **Expected output**: Returns [`DEFUSE`].

- **TC3: playSeeFuture_TwoCardsInDrawPile_ReturnsTopTwoCards** (:x:)
    - **State of system**: Player hand = [`SEE_THE_FUTURE`], draw pile has 2 cards
    - **Expected output**: Returns the top 2 cards in draw order.

- **TC4: playSeeFuture_ThreeCardsInDrawPile_ReturnsOnlyTopTwoCards** (:x:)
    - **State of system**: Player hand = [`SEE_THE_FUTURE`], draw pile has 3 cards
    - **Expected output**: Returns only 2 cards.

- **TC5: playSeeFuture_DoesNotChangeDrawPile** (:x:)
    - **State of system**: Player hand = [`SEE_THE_FUTURE`], draw pile has multiple cards
    - **Expected output**: Draw pile size and order stay the same.

- **TC6: playSeeFuture_RemovesSeeFutureFromHand** (:x:)
    - **State of system**: Player hand = [`SEE_THE_FUTURE`]
    - **Expected output**: Player hand size decreases by 1.

- **TC7: playSeeFuture_AddsSeeFutureToDiscardPile** (:x:)
    - **State of system**: Discard pile is empty
    - **Expected output**: Discard pile size becomes 1.

- **TC8: playSeeFuture_SelectedCardIsDefuse_ThrowsException** (:x:)
    - **State of system**: Player hand = [`DEFUSE`], `cardIndex = 0`
    - **Expected output**: `IllegalArgumentException` thrown.