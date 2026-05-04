# BVA Analysis for ChooseCard

### Method under test: `public String chooseCard(int cardIndex)`

| Step 1 | Step 2 | Step 3 |
|---|---|---|
| Input 1: `cardIndex` | Array Index | Values: <ul><li>Card Index is -1</li><li>Card Index is 0</li><li>Card Index is middle valid value</li><li>Card Index is largest valid value `getHandSize() - 1`</li><li>Card Index is one larger than largest valid value `getHandSize()`</li></ul> |
| Output: explanation of selected card is returned | N/A | Values: <ul><li>Correct explanation is returned</li><li>Hand size stays the same</li><li>Exception thrown for invalid index</li></ul> |
