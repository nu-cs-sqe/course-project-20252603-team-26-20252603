# BVA Analysis for ChooseCard

### Method under test: `public String chooseCard(int cardIndex)`

| Step 1 | Step 2 | Step 3 |
|---|---|---|
| Input 1: `cardIndex` | Array Index | Values: <ul><li>Card Index is -1</li><li>Card Index is 0</li><li>Card Index is middle valid value</li><li>Card Index is largest valid value `getHandSize() - 1`</li><li>Card Index is one larger than largest valid value `getHandSize()`</li></ul> |
| Output: explanation of selected card is returned | N/A | Values: <ul><li>Correct explanation is returned</li><li>Hand size stays the same</li><li>Exception thrown for invalid index</li></ul> |

# BVA Analysis for ChooseCard

### Method under test: `public String chooseCard(int cardIndex)`

| Step 1 | Step 2 | Step 3 |
|---|---|---|
| Input 1: `cardIndex` | Array Index | Values: <ul><li>Card Index is -1</li><li>Card Index is 0</li><li>Card Index is middle valid value</li><li>Card Index is largest valid value `getHandSize() - 1`</li><li>Card Index is one larger than largest valid value `getHandSize()`</li></ul> |
| Output: explanation of selected card is returned | N/A | Values: <ul><li>Correct explanation is returned</li><li>Hand size stays the same</li><li>Exception thrown for invalid index</li></ul> |

## Step 4: Test Cases

- **TC1: chooseCard_FirstIndex_ReturnsExplanation** (:x::)
    - **State of system**: Player hand = [`EXPLODING_KITTEN`, `DEFUSE`, `PLACEHOLDER_CARD`] (`getHandSize = 3`), `cardIndex = 0`
    - **Expected output**: Returns explanation for `EXPLODING_KITTEN`; hand remains unchanged; `getHandSize = 3`

- **TC2: chooseCard_LastIndex_ReturnsExplanation** (:x:)
    - **State of system**: Player hand = [`EXPLODING_KITTEN`, `DEFUSE`, `PLACEHOLDER_CARD`] (`getHandSize = 3`), `cardIndex = 2`
    - **Expected output**: Returns explanation for `PLACEHOLDER_CARD`; hand remains unchanged; `getHandSize = 3`

- **TC3: chooseCard_MiddleIndex_ReturnsExplanation** (:x:)
    - **State of system**: Player hand = [`EXPLODING_KITTEN`, `DEFUSE`, `PLACEHOLDER_CARD`] (`getHandSize = 3`), `cardIndex = 1`
    - **Expected output**: Returns explanation for `DEFUSE`; hand remains unchanged; `getHandSize = 3`

- **TC4: chooseCard_NegativeIndex_ThrowsException** (:x:)
    - **State of system**: Player hand = [`EXPLODING_KITTEN`, `DEFUSE`, `PLACEHOLDER_CARD`] (`getHandSize = 3`), `cardIndex = -1`
    - **Expected output**: `IllegalArgumentException` thrown; hand remains unchanged

- **TC5: chooseCard_IndexEqualsHandSize_ThrowsException** (:x:)
    - **State of system**: Player hand = [`EXPLODING_KITTEN`, `DEFUSE`, `PLACEHOLDER_CARD`] (`getHandSize = 3`), `cardIndex = 3`
    - **Expected output**: `IllegalArgumentException` thrown; hand remains unchanged

- **TC6: chooseCard_HandHasOneCard_ReturnsExplanation** (:x:)
    - **State of system**: Player hand = [`DEFUSE`] (`getHandSize = 1`), `cardIndex = 0`
    - **Expected output**: Returns explanation for `DEFUSE`; hand remains unchanged; `getHandSize = 1`

- **TC7: chooseCard_HandEmpty_ThrowsException** (:x:∂)
    - **State of system**: Player hand = [] (`getHandSize = 0`), `cardIndex = 0`
    - **Expected output**: `IllegalArgumentException` thrown; hand remains unchanged