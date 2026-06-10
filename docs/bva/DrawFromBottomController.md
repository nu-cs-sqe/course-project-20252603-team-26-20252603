### Method under test: `public Card drawFromBottom()`
| Step 1                 | Step 2     | Step 3                                                                                                          |
|------------------------|------------|-----------------------------------------------------------------------------------------------------------------|
| Input 1: deck state    | Collection | Values: <ul><li>empty deck</li><li>one card</li><li>more than one card</li></ul>                                |
| Output: drawn card     | Object     | Values: <ul><li>throws `IllegalStateException`</li><li>returns the only card</li><li>returns bottom card not top card</li></ul> |

- **Step 4:**
    - **TC1: drawFromBottom_EmptyDeck_ThrowsIllegalStateException** (:white_check_mark:)
        - **State of the system**: deck has no cards
        - **Expected output**: throws `IllegalStateException`

    - **TC2: drawFromBottom_OneCard_ReturnsOnlyCard** (:white_check_mark:)
        - **State of the system**: deck has one card (`SKIP`)
        - **Expected output**: returns `SKIP`, deck is now empty

    - **TC3: drawFromBottom_MultipleCards_ReturnsBottomCard** ( :white_check_mark:)
        - **State of the system**: deck has `[SKIP, ATTACK]` where `SKIP` is at index 0 (bottom) and `ATTACK` is on top
        - **Expected output**: returns `SKIP`, `ATTACK` remains in deck

### Method under test: `public Card play(Player player, int cardIndex)`
| Step 1                        | Step 2     | Step 3                                                                                                                                    |
|-------------------------------|------------|-------------------------------------------------------------------------------------------------------------------------------------------|
| Input 1: card index           | Integer    | Values: <ul><li>negative index</li><li>index equal to hand size</li><li>valid index pointing to `DRAW_FROM_BOTTOM`</li><li>valid index pointing to a non-`DRAW_FROM_BOTTOM` card</li></ul> |
| Input 2: deck state           | Collection | Values: <ul><li>one card (non-exploding kitten)</li><li>one card (exploding kitten)</li></ul>                                            |
| Output: drawn card / side effects | Object | Values: <ul><li>throws `IllegalArgumentException`</li><li>drawn card added to player hand and returned</li></ul>                         |

- **Step 4:**
    - **TC1: play_NegativeIndex_ThrowsIllegalArgumentException** (x: or :white_check_mark:)
        - **State of the system**: player has one `DRAW_FROM_BOTTOM` card, card index is `-1`
        - **Expected output**: throws `IllegalArgumentException`

    - **TC2: play_IndexEqualsHandSize_ThrowsIllegalArgumentException** (x: or :white_check_mark:)
        - **State of the system**: player has one `DRAW_FROM_BOTTOM` card, card index equals hand size
        - **Expected output**: throws `IllegalArgumentException`

    - **TC3: play_NotDrawFromBottomCard_ThrowsIllegalArgumentException** (x: or :white_check_mark:)
        - **State of the system**: player has one `SKIP` card, card index is `0`
        - **Expected output**: throws `IllegalArgumentException`

    - **TC4: play_ValidCard_DrawsBottomCardIntoPlayerHand** (x: or :white_check_mark:)
        - **State of the system**: player has one `DRAW_FROM_BOTTOM` card, deck has `[SKIP, ATTACK]` where `SKIP` is at the bottom
        - **Expected output**: `DRAW_FROM_BOTTOM` removed from hand and added to discard, `SKIP` added to player hand, returns `SKIP`

    - **TC5: play_ValidCard_ExplodingKittenDrawn_ReturnsExplodingKitten** (x: or :white_check_mark:)
        - **State of the system**: player has one `DRAW_FROM_BOTTOM` card, deck has `[EXPLODING_KITTEN, ATTACK]` where `EXPLODING_KITTEN` is at the bottom
        - **Expected output**: `DRAW_FROM_BOTTOM` discarded, `EXPLODING_KITTEN` added to player hand, returns `EXPLODING_KITTEN`