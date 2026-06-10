# BVA Analysis for Tracking Eliminated Players

## Feature under test: Eliminated player tracking

## Rule

When a player draws an Exploding Kitten and cannot Defuse it, that player is eliminated from the active player list. The killing Exploding Kitten should be shown face up in front of the eliminated player. The eliminated player's remaining hand cards should also be visible by card type.

The terminal should print public player state every turn:
- Active players are shown with face-down card counts.
- Eliminated players are shown with the face-up killing Exploding Kitten and their remaining face-up card types.

---

## Method under test 1: `Game.eliminatePlayer(Player player, Card killingKitten)`

| Step | Input / State | Category | Boundary values / cases |
|---|---|---|---|
| Step 1 | active player count | Collection size | 2 players; more than 2 players |
| Step 2 | eliminated player hand | Collection size | empty hand; one card; more than one card |
| Step 3 | killing card | Card type | `EXPLODING_KITTEN` |
| Step 4 | output/state change | Collection state | player removed from active players; eliminated player record added; killing kitten stored face up; remaining hand cards stored face up by type |

### Test Cases

- **TC1: eliminatePlayer_WithExplodingKitten_TracksFaceUpKittenAndRemainingCards**
    - **State of system**: Current player has remaining cards and is eliminated by an Exploding Kitten.
    - **Expected output**: Player is removed from active players. Eliminated player record stores the killing kitten and the remaining card types.
    - **Implemented?** :white_check_mark:

---

## Method under test 2: `GameController.takeCard()`

| Step | Input / State | Category | Boundary values / cases |
|---|---|---|---|
| Step 1 | drawn card | Card type | `EXPLODING_KITTEN` |
| Step 2 | current player hand | Collection state | no Defuse; has remaining non-Defuse cards |
| Step 3 | output/state change | Controller/model integration | current player is eliminated; killing kitten and remaining cards are recorded |

### Test Cases

- **TC2: takeCard_ExplodingKittenWithoutDefuse_TracksEliminatedPlayer**
    - **State of system**: Current player draws an Exploding Kitten without a Defuse and has remaining cards.
    - **Expected output**: GameController records the eliminated player with the drawn Exploding Kitten face up and the remaining hand cards face up by type.
    - **Implemented?** :x:

---

## Method under test 3: `GameView.displayPublicPlayerState(...)`

| Step | Input / State | Category | Boundary values / cases |
|---|---|---|---|
| Step 1 | active players | Collection size | one or more active players |
| Step 2 | eliminated players | Collection size | no eliminated players; one eliminated player; multiple eliminated players |
| Step 3 | eliminated player visible cards | Collection size | empty visible hand; one visible card; multiple visible cards |
| Step 4 | output | Terminal display | active players shown with face-down card counts; eliminated players shown with face-up killing kitten and face-up remaining card types |

### Test Cases

- **TC3: displayPublicPlayerState_WithActiveAndEliminatedPlayers_PrintsFaceDownAndFaceUpState**
    - **State of system**: One active player and one eliminated player with a killing kitten and remaining cards.
    - **Expected output**: Terminal prints active player's face-down card count and eliminated player's face-up killing kitten plus remaining face-up card types.
    - **Implemented?** :x:

---

## Method under test 4: `GameController.startTurn()`

| Step | Input / State | Category | Boundary values / cases |
|---|---|---|---|
| Step 1 | start of turn | Controller flow | public state should be displayed before current player's private hand |
| Step 2 | active and eliminated players | Model state | active players only; active plus eliminated players |
| Step 3 | output | View interaction | view displays public state every turn |

### Test Cases

- **TC4: startTurn_DisplaysPublicPlayerStateThenCurrentPlayerHand**
    - **State of system**: Game has active players and may have eliminated player records.
    - **Expected output**: View displays public player state, then displays the current player's hand.
    - **Implemented?** :x: