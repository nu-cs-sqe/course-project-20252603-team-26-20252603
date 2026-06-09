# BVA Analysis for Swap Top and Bottom

## Feature under test: Swap Top and Bottom card

## Rule

When a player plays Swap Top and Bottom, the played card is discarded and the top and bottom cards of the draw pile are swapped. The player's turn continues, so the player still draws a card after the card effect.

The main card behavior is implemented in `SwapTopAndBottomController`.
---

## Method under test 1: `Deck.swapTopAndBottom()`

### Step 1-3 Results

| Step | Input / State | Category | Boundary values / cases |
|---|---|---|---|
| Step 1 | draw pile size | Collection size | empty deck; one card; exactly two cards; more than two cards |
| Step 2 | bottom card | Card position | index `0` |
| Step 3 | top card | Card position | index `size - 1` |
| Step 4 | output/state change | Collection state change | empty and one-card decks stay unchanged; two-or-more-card decks swap first and last cards |

### Test Cases

- **TC1: swapTopAndBottom_EmptyDeck_LeavesDeckEmpty**
    - **State of system**: Draw pile = `[]`
    - **Expected output**: Draw pile remains `[]`.
    - **Implemented?** :white_check_mark:

- **TC2: swapTopAndBottom_OneCardDeck_LeavesDeckUnchanged**
    - **State of system**: Draw pile = [`DEFUSE`]
    - **Expected output**: Draw pile remains [`DEFUSE`].
    - **Implemented?** :white_check_mark:

- **TC3: swapTopAndBottom_TwoCardDeck_SwapsCards**
    - **State of system**: Draw pile = [`BOTTOM_CARD`, `TOP_CARD`]
    - **Expected output**: Draw pile becomes [`TOP_CARD`, `BOTTOM_CARD`].
    - **Implemented?** :white_check_mark:

- **TC4: swapTopAndBottom_ThreeCardDeck_SwapsOnlyTopAndBottom**
    - **State of system**: Draw pile = [`BOTTOM_CARD`, `MIDDLE_CARD`, `TOP_CARD`]
    - **Expected output**: Draw pile becomes [`TOP_CARD`, `MIDDLE_CARD`, `BOTTOM_CARD`].
    - **Implemented?** :white_check_mark:

---

### Method under test 2: `SwapTopAndBottomController.play(Game game, int cardIndex)`

| Step | Input / State | Category | Boundary values / cases |
|---|---|---|---|
| Step 1 | selected `cardIndex` | Array index | `0`; invalid indexes already covered by controller pattern |
| Step 2 | selected card type | Card type case | selected card is `SWAP_TOP_AND_BOTTOM`; selected card is not `SWAP_TOP_AND_BOTTOM` |
| Step 3 | current player hand | Collection state | hand contains playable Swap Top and Bottom card |
| Step 4 | discard pile | Collection state change | played card is added to discard pile |
| Step 5 | draw pile | Collection state change | top and bottom cards are swapped |
| Step 6 | output/state change | State change / exception | valid card mutates hand, discard pile, and draw pile; wrong card type throws exception and leaves state unchanged |
### Test Cases

- **TC5: playSwapTopAndBottom_ValidCard_DiscardsCardAndSwapsDrawPile**
    - **State of system**: Current player hand = [`SWAP_TOP_AND_BOTTOM`], draw pile = [`BOTTOM_CARD`, `MIDDLE_CARD`, `TOP_CARD`], discard pile empty.
    - **Expected output**: Player hand becomes empty; discard pile contains `SWAP_TOP_AND_BOTTOM`; draw pile becomes [`TOP_CARD`, `MIDDLE_CARD`, `BOTTOM_CARD`].
    - **Implemented?** :white_check_mark:

- **TC6: playSwapTopAndBottom_SelectedCardIsDefuse_ThrowsException**
    - **State of system**: Current player hand = [`DEFUSE`], draw pile has cards, discard pile empty.
    - **Expected output**: `IllegalArgumentException` thrown; player hand, draw pile, and discard pile stay unchanged.
    - **Implemented?** :x:

---

## Method under test 3: `GameController.completeTurn(List<Integer> selectedCardIndexes)`

### Step 1-3 Results

| Step | Input / State | Category | Boundary values / cases |
|---|---|---|---|
| Step 1 | selected card list | Collection | one selected Swap Top and Bottom card |
| Step 2 | card effect result | Turn action | effect returns `CONTINUE`, so turn does not end immediately |
| Step 3 | draw pile after effect | Collection state | top and bottom are swapped before drawing |
| Step 4 | final turn behavior | Turn state | player still draws after playing Swap Top and Bottom and then turn advances |

### Test Cases

- **TC7: completeTurn_SwapTopAndBottomPlayed_SwapsThenDrawsAndAdvances**
    - **State of system**: Current player has [`SWAP_TOP_AND_BOTTOM`], draw pile = [`BOTTOM_CARD`, `MIDDLE_CARD`, `TOP_CARD`].
    - **Expected output**: Swap card is discarded; top and bottom are swapped; current player draws the new top card; turn advances to next player.
    - **Implemented?** :white_check_mark: