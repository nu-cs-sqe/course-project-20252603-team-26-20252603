# BVA Analysis for Exploding Kitten Cards

The rules say that a player who draws an `EXPLODING_KITTEN` explodes unless they can play a `DEFUSE`. If they do play a `DEFUSE`, the `DEFUSE` goes to the discard pile and the drawn `EXPLODING_KITTEN` is returned to the draw pile.

This BVA uses each-choice coverage over the catalog values below. The current domain model can test player elimination, Defuse removal, discard pile changes, draw pile reinsertion, and win state; it does not yet model the secret placement UI or face-up/dead-player pile.

## Method under test 1: `ExplodingKittenCardController(Game game)`

| Step 1 | Step 2 | Step 3 |
|---|---|---|
| Input 1: `game` | Object Reference | Values: <ul><li>`game` is valid</li><li>`game` is `null`</li></ul> |
| Output | Constructed Object / Exception | Values: <ul><li>Controller is constructed with a valid game</li><li>Exception thrown for `null` game</li></ul> |

### Test Cases

- **TC1: constructor_NullGame_ThrowsException** (:white_check_mark:)
    - **State of system**: `game = null`
    - **Expected output**: Exception thrown.

## Method under test 2: `ExplodingKittenCardController.play(Player player, Card explodingKitten)`

| Step 1 | Step 2 | Step 3 |
|---|---|---|
| Input 1: `player` | Object Reference | Values: <ul><li>`player` is valid</li><li>`player` is `null`</li></ul> |
| Input 2: `explodingKitten` | Object Reference / Card Type Case | Values: <ul><li>`explodingKitten` is `null`</li><li>Card type is `EXPLODING_KITTEN`</li><li>Card type is not `EXPLODING_KITTEN`, such as `DEFUSE`</li></ul> |
| Input 3: player's `DEFUSE` subset | Collection Subset | Values: <ul><li>Subset is empty because the hand is empty</li><li>Subset is empty while the hand is not empty</li><li>Subset contains exactly one `DEFUSE`</li><li>Subset contains all but one card in the hand</li><li>Subset is the same as the whole hand</li></ul> |
| Input 4: active player count | Collection Size / Win Boundary | Values: <ul><li>Exactly 2 active players before elimination</li><li>More than 2 active players before elimination</li></ul> |
| Input 5: draw pile before reinserting the kitten | Collection Size | Values: <ul><li>Draw pile is empty</li><li>Draw pile has exactly 1 card</li><li>Draw pile has more than 1 card</li></ul> |
| Input 6: discard pile before discarding `DEFUSE` | Collection Size | Values: <ul><li>Discard pile is empty</li><li>Discard pile is not empty</li></ul> |
| Output | Boolean / State Change / Exception | Values: <ul><li>Returns `false` when the player explodes</li><li>Eliminates the player when no `DEFUSE` is available</li><li>Game is won when elimination leaves 1 player</li><li>Game is not won when elimination leaves more than 1 player</li><li>Returns `true` when the player defuses</li><li>Removes exactly 1 `DEFUSE` from the player hand</li><li>Adds exactly 1 `DEFUSE` to the discard pile</li><li>Returns the drawn `EXPLODING_KITTEN` to the draw pile</li><li>Leaves non-Defuse hand cards with the player</li><li>Throws exception for invalid inputs</li></ul> |

### Test Cases

- **TC2: play_NoDefuseEmptyHandWithThreePlayers_EliminatesPlayerAndGameContinues** (:x:)
    - **State of system**: Three active players; current player hand = []; drawn card = `EXPLODING_KITTEN`.
    - **Expected output**: Returns `false`, removes current player from the game, leaves 2 players active, and `game.isWon()` is `false`.

- **TC3: play_NoDefuseNonEmptyHandWithTwoPlayers_EliminatesPlayerAndGameIsWon** (:x:)
    - **State of system**: Two active players; current player hand = [`BEARD_CAT`]; drawn card = `EXPLODING_KITTEN`.
    - **Expected output**: Returns `false`, removes current player from the game, leaves 1 player active, and `game.isWon()` is `true`.

- **TC4: play_OneDefuseWithEmptyDrawPileAndEmptyDiscardPile_DefusesKitten** (:x:)
    - **State of system**: Current player hand = [`DEFUSE`]; draw pile = []; discard pile = []; drawn card = `EXPLODING_KITTEN`.
    - **Expected output**: Returns `true`, player remains active, player hand size becomes 0, discard pile becomes [`DEFUSE`], and draw pile becomes [`EXPLODING_KITTEN`].

- **TC5: play_AllButOneCardIsDefuse_RemovesOneDefuseAndKeepsOtherCards** (:x:)
    - **State of system**: Current player hand = [`DEFUSE`, `DEFUSE`, `BEARD_CAT`]; draw pile has exactly 1 card; discard pile is not empty; drawn card = `EXPLODING_KITTEN`.
    - **Expected output**: Returns `true`, removes exactly 1 `DEFUSE`, keeps [`DEFUSE`, `BEARD_CAT`] in the player's hand, discard pile size increases by 1, and draw pile size increases by 1.

- **TC6: play_AllCardsAreDefuse_RemovesOnlyOneDefuse** (:x:)
    - **State of system**: Current player hand = [`DEFUSE`, `DEFUSE`]; draw pile has more than 1 card; drawn card = `EXPLODING_KITTEN`.
    - **Expected output**: Returns `true`, player remains active, exactly 1 `DEFUSE` remains in hand, discard pile size increases by 1, and draw pile size increases by 1.

- **TC7: play_NullPlayer_ThrowsException** (:x:)
    - **State of system**: `player = null`; drawn card = `EXPLODING_KITTEN`.
    - **Expected output**: Exception thrown.

- **TC8: play_NullDrawnCard_ThrowsException** (:x:)
    - **State of system**: Current player is valid; `explodingKitten = null`.
    - **Expected output**: Exception thrown.

- **TC9: play_DrawnCardIsNotExplodingKitten_ThrowsException** (:x:)
    - **State of system**: Current player is valid; drawn card = `DEFUSE`.
    - **Expected output**: `IllegalArgumentException` thrown.
