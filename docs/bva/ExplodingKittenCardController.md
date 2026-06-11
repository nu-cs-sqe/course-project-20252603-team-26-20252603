# BVA Analysis for Exploding Kitten Cards

The rules say that a player who draws an `EXPLODING_KITTEN` explodes unless
they can play a `DEFUSE`. If they do, the `DEFUSE` goes to the discard pile and
the player secretly returns the kitten anywhere in the draw pile. Position `0`
is the top (the next draw), and position `drawPile.size()` is the bottom.
Player elimination and game-over checks remain game-flow responsibilities.

The terminal tells all other players to look away before asking for the
position. It validates the choice before passing it to the controller. A
successful Defuse still ends the current turn.

## Method under test: `ExplodingKittenCardController.play(Player, Card, int)`

| Step 1 | Step 2 | Step 3 |
|---|---|---|
| Input 1: `player` | Object Reference | Values: <ul><li>`player` is valid</li></ul> |
| Input 2: `explodingKitten` | Object Reference / Card Type Case | Values: <ul><li>Card type is `EXPLODING_KITTEN`</li><li>Card type is not `EXPLODING_KITTEN`, such as `DEFUSE`</li></ul> |
| Input 3: player's `DEFUSE` subset | Collection Subset | Values: <ul><li>Subset is empty because the hand is empty</li><li>Subset is empty while the hand is not empty</li><li>Subset contains exactly one `DEFUSE`</li><li>Subset contains all but one card in the hand</li><li>Subset is the same as the whole hand</li></ul> |
| Input 4: draw pile before reinserting the kitten | Collection Size | Values: <ul><li>Draw pile is empty</li><li>Draw pile has exactly 1 card</li><li>Draw pile has more than 1 card</li></ul> |
| Input 5: discard pile before discarding `DEFUSE` | Collection Size | Values: <ul><li>Discard pile is empty</li><li>Discard pile is not empty</li></ul> |
| Input 6: insertion position from top | Integer Boundary | Values: <ul><li>`0` (top)</li><li>`1..size - 1` (middle)</li><li>`size` (bottom)</li><li>less than `0`</li><li>greater than `size`</li></ul> |
| Output | Boolean / State Change / Exception | Values: <ul><li>Returns `false` when the player has no `DEFUSE`</li><li>Returns `true` when the player defuses</li><li>Removes exactly 1 `DEFUSE` from the player hand</li><li>Adds exactly 1 `DEFUSE` to the discard pile</li><li>Returns the drawn `EXPLODING_KITTEN` at the selected position</li><li>Leaves non-Defuse hand cards with the player</li><li>Throws exception for invalid inputs</li></ul> |

### Test Cases

- **TC3: play_NoDefuseEmptyHand_ReturnsFalseWithoutChangingPiles** (:white_check_mark:)
    - **State of system**: Player hand = []; draw pile and discard pile are not empty; drawn card = `EXPLODING_KITTEN`.
    - **Expected output**: Returns `false`, player hand stays empty, draw pile is unchanged, and discard pile is unchanged.

- **TC4: play_NoDefuseNonEmptyHand_ReturnsFalseWithoutChangingHandOrPiles** (:white_check_mark:)
    - **State of system**: Player hand = [`BEARD_CAT`]; drawn card = `EXPLODING_KITTEN`.
    - **Expected output**: Returns `false`, keeps [`BEARD_CAT`] in hand, draw pile is unchanged, and discard pile is unchanged.

- **TC5: play_OneDefuseWithEmptyDrawPileAndEmptyDiscardPile_DefusesKitten** (:white_check_mark:)
    - **State of system**: Player hand = [`DEFUSE`]; draw pile = []; discard pile = []; drawn card = `EXPLODING_KITTEN`.
    - **Expected output**: Returns `true`, player hand size becomes 0, discard pile becomes [`DEFUSE`], and draw pile becomes [`EXPLODING_KITTEN`].

- **TC6: play_AllButOneCardIsDefuse_RemovesOneDefuseAndKeepsOtherCards** (:white_check_mark:)
    - **State of system**: Player hand = [`DEFUSE`, `DEFUSE`, `BEARD_CAT`]; draw pile has exactly 1 card; discard pile is not empty; drawn card = `EXPLODING_KITTEN`.
    - **Expected output**: Returns `true`, removes exactly 1 `DEFUSE`, keeps [`DEFUSE`, `BEARD_CAT`] in the player's hand, discard pile size increases by 1, and draw pile size increases by 1.

- **TC7: play_AllCardsAreDefuse_RemovesOnlyOneDefuse** (:white_check_mark:)
    - **State of system**: Player hand = [`DEFUSE`, `DEFUSE`]; draw pile has more than 1 card; drawn card = `EXPLODING_KITTEN`.
    - **Expected output**: Returns `true`, exactly 1 `DEFUSE` remains in hand, discard pile size increases by 1, and draw pile size increases by 1.

- **TC10: play_DrawnCardIsNotExplodingKitten_ThrowsException** (:white_check_mark:)
    - **State of system**: Current player is valid; drawn card = `DEFUSE`.
    - **Expected output**: `IllegalArgumentException` thrown.

- **TC11: play_DefuseWithMiddlePosition_ReinsertsKittenAtSelectedPosition** (:white_check_mark:)
    - **State of system**: Draw pile has a top and bottom card; insertion position is `1`.
    - **Expected output**: The kitten is inserted between those cards.

- **TC12: promptDefuseInsertionPosition_BottomPosition_ReturnsChoiceAndWarnsOtherPlayers** (:white_check_mark:)
    - **State of system**: Draw pile size and entered position are both `3`.
    - **Expected output**: The terminal tells other players to look away and accepts the bottom position.

- **TC13: promptDefuseInsertionPosition_OutOfRangeThenValid_ReturnsValidChoice** (:white_check_mark:)
    - **State of system**: Player first enters `-1`, then a valid position.
    - **Expected output**: The terminal rejects the invalid value and returns the valid one.

- **TC14: takeCard_ExplodingKittenWithDefuse_ReinsertsKittenAtSelectedPosition** (:white_check_mark:)
    - **State of system**: The current player has a `DEFUSE` and selects a middle position.
    - **Expected output**: The kitten is placed at that position and play advances to the next player.
