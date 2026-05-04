# BVA Analysis for Cat Cards

### Method under test: `public boolean canSubmitCard(int cardIndex)`

| Step 1 | Step 2 | Step 3 |
|---|---|---|
| Input 1: `cardIndex` | Array Index | Values: <ul><li>Card Index is -1</li><li>Card Index is 0</li><li>Card Index is middle valid value</li><li>Card Index is largest valid value `getHandSize() - 1`</li><li>Card Index is one larger than largest valid value `getHandSize()`</li></ul> |
| Input 2: selected card type and matching count in player's hand | Card Type / Matching Pair | Values: <ul><li>Selected card is one cat card with no matching pair</li><li>Selected card has exactly 2 matching cat cards</li><li>Selected card has more than 2 matching cat cards</li><li>Selected card is a cat card, but the other cat card is a different type</li><li>Selected card is `PLACEHOLDER_CARD`</li><li>Selected card is `DEFUSE`</li><li>Hand is empty</li></ul> |
| Output | Boolean / Exception | Values: <ul><li>`true` when selected card is a cat card and player has at least 2 of that same cat card type</li><li>`false` when selected card is a cat card but player does not have a matching pair</li><li>`true` when selected card is `PLACEHOLDER_CARD`</li><li>`false` when selected card is `DEFUSE`</li><li>Exception thrown for invalid index</li></ul> |

## Step 4: Test Cases

- **TC1: canSubmitCard_OneBeardCat_ReturnsFalse** (:white_check_mark:)
    - **State of system**: Player hand = [`BEARD_CAT`] (`getHandSize = 1`), `cardIndex = 0`
    - **Expected output**: Returns `false` because one Cat Card cannot be submitted alone.

- **TC2: canSubmitCard_TwoMatchingBeardCats_ReturnsTrue** (:white_check_mark:)
    - **State of system**: Player hand = [`BEARD_CAT`, `BEARD_CAT`] (`getHandSize = 2`), `cardIndex = 0`
    - **Expected output**: Returns `true` because two matching Cat Cards are playable.

- **TC3: canSubmitCard_ThreeMatchingBeardCats_ReturnsTrue** (:white_check_mark:)
    - **State of system**: Player hand = [`BEARD_CAT`, `BEARD_CAT`, `BEARD_CAT`] (`getHandSize = 3`), `cardIndex = 1`
    - **Expected output**: Returns `true` because player has at least two matching Cat Cards.

- **TC4: canSubmitCard_TwoDifferentCatCards_ReturnsFalse** (:white_check_mark:)
    - **State of system**: Player hand = [`BEARD_CAT`, `TACOCAT`] (`getHandSize = 2`), `cardIndex = 0`
    - **Expected output**: Returns `false` because Cat Cards must match.

- **TC5: canSubmitCard_MatchingHairyPotatoCats_ReturnsTrue** (:white_check_mark:)
    - **State of system**: Player hand = [`HAIRY_POTATO_CAT`, `HAIRY_POTATO_CAT`] (`getHandSize = 2`), `cardIndex = 1`
    - **Expected output**: Returns `true`.

- **TC6: canSubmitCard_MatchingTacocats_ReturnsTrue** (:white_check_mark:)
    - **State of system**: Player hand = [`TACOCAT`, `TACOCAT`] (`getHandSize = 2`), `cardIndex = 0`
    - **Expected output**: Returns `true`.

- **TC7: canSubmitCard_MatchingRainbowRalphingCats_ReturnsTrue** (:white_check_mark:)
    - **State of system**: Player hand = [`RAINBOW_RALPHING_CAT`, `RAINBOW_RALPHING_CAT`] (`getHandSize = 2`), `cardIndex = 0`
    - **Expected output**: Returns `true`.

- **TC8: canSubmitCard_PlaceholderCard_ReturnsTrue** (:white_check_mark:)
    - **State of system**: Player hand = [`PLACEHOLDER_CARD`] (`getHandSize = 1`), `cardIndex = 0`
    - **Expected output**: Returns `true`.

- **TC9: canSubmitCard_DefuseCard_ReturnsFalse** (:white_check_mark:)
    - **State of system**: Player hand = [`DEFUSE`] (`getHandSize = 1`), `cardIndex = 0`
    - **Expected output**: Returns `false`.

- **TC10: canSubmitCard_NegativeIndex_ThrowsException** (:white_check_mark:)
    - **State of system**: Player hand = [`BEARD_CAT`, `BEARD_CAT`] (`getHandSize = 2`), `cardIndex = -1`
    - **Expected output**: `IllegalArgumentException` thrown.

- **TC11: canSubmitCard_IndexEqualsHandSize_ThrowsException** (:white_check_mark:)
    - **State of system**: Player hand = [`BEARD_CAT`, `BEARD_CAT`] (`getHandSize = 2`), `cardIndex = 2`
    - **Expected output**: `IllegalArgumentException` thrown.

- **TC12: canSubmitCard_HandEmpty_ThrowsException** (:white_check_mark:)
    - **State of system**: Player hand = [] (`getHandSize = 0`), `cardIndex = 0`
    - **Expected output**: `IllegalArgumentException` thrown.

