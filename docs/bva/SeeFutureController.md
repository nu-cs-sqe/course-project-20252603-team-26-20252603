# BVA Analysis for See the Future Cards

## Method under test 1: `Deck.peekTopCards(int count)`

| Step 1 | Step 2 | Step 3 |
|---|---|---|
| Input 1: `count` | Integer / Number of cards to peek | Values: <ul><li>`count` is negative</li><li>`count` is 0</li><li>`count` is 1</li><li>`count` is 2</li><li>`count` is greater than deck size</li></ul> |
| Input 2: draw pile size | Deck Size | Values: <ul><li>Deck has 0 cards</li><li>Deck has 1 card</li><li>Deck has exactly 2 cards</li><li>Deck has more than 2 cards</li></ul> |
| Output | List / Exception / State Change | Values: <ul><li>Returns empty list when deck is empty</li><li>Returns empty list when `count` is 0</li><li>Returns up to `count` cards from the top of the deck</li><li>Returns only available cards when `count` is greater than deck size</li><li>Does not change deck size or order</li><li>Throws exception when `count` is negative</li></ul> |

### Test Cases

- **TC1: peekTopCards_EmptyDeck_ReturnsEmptyList** (:white_check_mark:)
    - **State of system**: Deck = [], `count = 2`
    - **Expected output**: Returns empty list.

- **TC2: peekTopCards_OneCardDeck_ReturnsOneCard** (:white_check_mark:)
    - **State of system**: Deck = [`DEFUSE`], `count = 2`
    - **Expected output**: Returns [`DEFUSE`].
    - **Note**: This test name should eventually be renamed to `peekTopCards_OneCardDeck_ReturnsOneCard` because it tests `Deck.peekTopCards`, not `SeeFutureController.play`.

- **TC3: peekTopCards_DoesNotRemoveCardsFromDeck** (:white_check_mark:)
    - **State of system**: Deck = [`EXPLODING_KITTEN`, `DEFUSE`], `count = 2`
    - **Expected output**: Deck size stays 2 and deck order stays [`EXPLODING_KITTEN`, `DEFUSE`].

- **TC4: peekTopCards_ThreeCardDeck_ReturnsOnlyTopTwoCards** (:white_check_mark:)
    - **State of system**: Deck = [`DEFUSE`, `BEARD_CAT`, `EXPLODING_KITTEN`], `count = 2`
    - **Expected output**: Returns [`EXPLODING_KITTEN`, `BEARD_CAT`].

- **TC5: peekTopCards_NegativeCount_ThrowsException** (:white_check_mark:)
    - **State of system**: Deck has cards, `count = -1`
    - **Expected output**: `IllegalArgumentException` thrown.

- **TC6: peekTopCards_CountZero_ReturnsEmptyList** (:white_check_mark:)
    - **State of system**: Deck has cards, `count = 0`
    - **Expected output**: Returns empty list.

## Supporting Test: `Card(CardType type)`

### Test Cases

- **TC7: constructorStoresSeeTheFutureCardType** (:white_check_mark:)
  - **State of system**: Create `new Card(CardType.SEE_THE_FUTURE)`
  - **Expected output**: `card.getType()` returns `SEE_THE_FUTURE`.
  - **Note**: This supports the See the Future feature, but it is not part of `Deck.peekTopCards`.

## Method under test 2: `SeeFutureController.play(Player player, int cardIndex)`

| Step 1 | Step 2 | Step 3 |
|---|---|---|
| Input 1: `player` | Object Reference | Values: <ul><li>`player` is valid</li><li>`player` is `null`</li></ul> |
| Input 2: `cardIndex` | Array Index | Values: <ul><li>Card Index is -1</li><li>Card Index is 0</li><li>Card Index is one larger than largest valid value `getHandSize()`</li></ul> |
| Input 3: selected card type | Card Type | Values: <ul><li>Selected card is `SEE_THE_FUTURE`</li><li>Selected card is not `SEE_THE_FUTURE`, such as `DEFUSE`</li></ul> |
| Output | List / Exception / State Change | Values: <ul><li>Returns the top two cards from the draw pile</li><li>Removes `SEE_THE_FUTURE` from player hand</li><li>Adds `SEE_THE_FUTURE` to discard pile</li><li>Throws exception for wrong card type</li><li>Throws exception for invalid index</li><li>Throws exception for null player</li></ul> |

### Test Cases

- **TC8: playSeeFuture_ReturnsTopTwoCards** (:white_check_mark:)
  - **State of system**: Player hand = [`SEE_THE_FUTURE`], draw pile = [`DEFUSE`, `BEARD_CAT`, `EXPLODING_KITTEN`], `cardIndex = 0`
  - **Expected output**: Returns [`EXPLODING_KITTEN`, `BEARD_CAT`].

- **TC9: playSeeFuture_RemovesCardFromHand** (:x:)
  - **State of system**: Player hand = [`SEE_THE_FUTURE`], `cardIndex = 0`
  - **Expected output**: Player hand size decreases to 0.

- **TC10: playSeeFuture_AddsCardToDiscardPile** (:x:)
  - **State of system**: Player hand = [`SEE_THE_FUTURE`], discard pile is empty
  - **Expected output**: Discard pile size becomes 1 and contains `SEE_THE_FUTURE`.

- **TC11: playSeeFuture_SelectedCardIsDefuse_ThrowsException** (:x:)
  - **State of system**: Player hand = [`DEFUSE`], `cardIndex = 0`
  - **Expected output**: `IllegalArgumentException` thrown.

- **TC12: playSeeFuture_NegativeIndex_ThrowsException** (:x:)
  - **State of system**: Player hand = [`SEE_THE_FUTURE`], `cardIndex = -1`
  - **Expected output**: Exception thrown.

- **TC13: playSeeFuture_IndexEqualsHandSize_ThrowsException** (:x:)
  - **State of system**: Player hand = [`SEE_THE_FUTURE`], `getHandSize() = 1`, `cardIndex = 1`
  - **Expected output**: Exception thrown.

- **TC14: playSeeFuture_NullPlayer_ThrowsException** (:x:)
  - **State of system**: `player = null`
  - **Expected output**: Exception thrown.