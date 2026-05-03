# BVA Analysis for RemoveCard
### Method under test: `public void removeCard(int cardIndex)`
| Step 1                                                    | Step 2      | Step 3                                                                                                                                                                                                         |
|-----------------------------------------------------------|-------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Input 1: cardIndex                                        | Array Index | Values: <ul><li> Card Index is - 1</li><li>Card Index is 0</li><li>Card Index is largest valid value 1 `getHandSize - 1`</li><li>Card Index is one larger than the largest valid value `getHandSize`</li></ul> | 
| Output: card removed, player's hand size decreases by 1   | N/A         | Values: N/A                                                                                                                                                                                                    |

- **Step 4:**
    - **TC1: removeCard_FirstIndex_RemovesCard** (x: or :white_check_mark:)
        - **State of system**: Player hand = [Card_A, Card_B, Card_C] (getHandSize = 3), cardIndex = 0
        - **Expected output**: Hand becomes [Card_B, Card_C], getHandSize = 2, Card_A added to discard pile
  
    - **TC2: removeCard_LastIndex_RemovesCard** (x: or :white_check_mark:)
        - **State of system**: Player hand = [Card_A, Card_B, Card_C] (getHandSize = 3), cardIndex = 2
        - **Expected output**: Hand becomes [Card_A, Card_B], getHandSize = 2, Card_C added to discard pile
    
    - **TC3: removeCard_MiddleIndex_RemovesCard** (x: or :white_check_mark:)
        - **State of system**: Player hand = [Card_A, Card_B, Card_C] (getHandSize = 3), cardIndex = 1
        - **Expected output**: Hand becomes [Card_A, Card_C], getHandSize = 2, Card_B added to discard pile
      
    - **TC4: removeCard_NegativeIndex_ThrowsException** (x: or :white_check_mark:)
        - **State of system**: Player hand = [Card_A, Card_B, Card_C] (getHandSize = 3), cardIndex = -1
        - **Expected output**: IllegalArgumentException thrown, hand & discard pile unchanged
      
    - **TC5: removeCard_IndexEqualsHandSize_ThrowsException** (x: or :white_check_mark:)
        - **State of system**: Player hand = [Card_A, Card_B, Card_C] (getHandSize = 3), cardIndex = 3
        - **Expected output**: IllegalArgumentException thrown, hand & discard pile unchanged
      
    - **TC6: removeCard_HandHasOneCard_RemovesCard** (x: or :white_check_mark:)
        - **State of system**: Player hand = [Card_A] (getHandSize = 1), cardIndex = 0
        - **Expected output**: Hand becomes [], getHandSize = 0, Card_X added to discard pile
      
    - **TC7: removeCard_HandEmpty_ThrowsException** (x: or :white_check_mark:)
        - **State of system**: Player hand = [] (getHandSize = 0), cardIndex = 0
        - **Expected output**: IllegalArgumentException thrown, hand & discard pile unchanged