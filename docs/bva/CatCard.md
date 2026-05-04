# BVA Analysis for Cat Cards

### Method under test: `public boolean canSubmitCard(int cardIndex)`

| Step 1 | Step 2 | Step 3 |
|---|---|---|
| Input 1: `cardIndex` | Array Index | Values: <ul><li>Card Index is -1</li><li>Card Index is 0</li><li>Card Index is middle valid value</li><li>Card Index is largest valid value `getHandSize() - 1`</li><li>Card Index is one larger than largest valid value `getHandSize()`</li></ul> |
| Input 2: cards in player's hand | Card Count / Card Type | Values: <ul><li>Hand has 0 cards</li><li>Hand has 1 `CAT_CARD`</li><li>Hand has exactly 2 `CAT_CARD`s</li><li>Hand has more than 2 `CAT_CARD`s</li><li>Hand has non-cat cards</li></ul> |
| Output | Boolean / Exception | Values: <ul><li>`true` when selected card is `CAT_CARD` and player has at least 2 cat cards</li><li>`false` when selected card is `CAT_CARD` but player has fewer than 2 cat cards</li><li>`true` when selected card is a normal playable placeholder card</li><li>`false` when selected card is `DEFUSE`, because Defuse is not normally submitted during a regular turn</li><li>Exception thrown for invalid index</li></ul> |


