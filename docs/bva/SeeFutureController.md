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

- **TC5: peekTopCards_NegativeCount_ThrowsException** (:x:)
    - **State of system**: Deck has cards, `count = -1`
    - **Expected output**: `IllegalArgumentException` thrown.

- **TC6: peekTopCards_CountZero_ReturnsEmptyList** (:x:)
    - **State of system**: Deck has cards, `count = 0`
    - **Expected output**: Returns empty list.
