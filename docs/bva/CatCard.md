# BVA Analysis for Cat Cards

### Method under test: `public boolean canSubmitCard(int cardIndex)`

| Step 1 | Step 2 | Step 3 |
|---|---|---|
| Input 1: `cardIndex` | Array Index | Values: <ul><li>Card Index is -1</li><li>Card Index is 0</li><li>Card Index is middle valid value</li><li>Card Index is largest valid value `getHandSize() - 1`</li><li>Card Index is one larger than largest valid value `getHandSize()`</li></ul> |
| Input 2: selected card type and matching count in player's hand | Card Type / Matching Pair | Values: <ul><li>Selected card is one cat card with no matching pair</li><li>Selected card has exactly 2 matching cat cards</li><li>Selected card has more than 2 matching cat cards</li><li>Selected card is a cat card, but the other cat card is a different type</li><li>Selected card is `PLACEHOLDER_CARD`</li><li>Selected card is `DEFUSE`</li><li>Hand is empty</li></ul> |
| Output | Boolean / Exception | Values: <ul><li>`true` when selected card is a cat card and player has at least 2 of that same cat card type</li><li>`false` when selected card is a cat card but player does not have a matching pair</li><li>`true` when selected card is `PLACEHOLDER_CARD`</li><li>`false` when selected card is `DEFUSE`</li><li>Exception thrown for invalid index</li></ul> |




