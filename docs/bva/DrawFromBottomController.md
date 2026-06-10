### Method under test: `public Card drawFromBottom()`
| Step 1                 | Step 2     | Step 3                                                                                                          |
|------------------------|------------|-----------------------------------------------------------------------------------------------------------------|
| Input 1: deck state    | Collection | Values: <ul><li>empty deck</li><li>one card</li><li>more than one card</li></ul>                                |
| Output: drawn card     | Object     | Values: <ul><li>throws `IllegalStateException`</li><li>returns the only card</li><li>returns bottom card not top card</li></ul> |

- **Step 4:**
    - **TC1: drawFromBottom_EmptyDeck_ThrowsIllegalStateException** (x: or :white_check_mark:)
        - **State of the system**: deck has no cards
        - **Expected output**: throws `IllegalStateException`

    - **TC2: drawFromBottom_OneCard_ReturnsOnlyCard** (x: or :white_check_mark:)
        - **State of the system**: deck has one card (`SKIP`)
        - **Expected output**: returns `SKIP`, deck is now empty

    - **TC3: drawFromBottom_MultipleCards_ReturnsBottomCard** (x: or :white_check_mark:)
        - **State of the system**: deck has `[SKIP, ATTACK]` where `SKIP` is at index 0 (bottom) and `ATTACK` is on top
        - **Expected output**: returns `SKIP`, `ATTACK` remains in deck