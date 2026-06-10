### Method under test: `public void play(Player player, int cardIndex)`
| Step 1                        | Step 2     | Step 3                                                                                                                                    |
|-------------------------------|------------|-------------------------------------------------------------------------------------------------------------------------------------------|
| Input 1: card index           | Integer    | Values: <ul><li>negative index</li><li>index equal to hand size</li><li>valid index pointing to `TARGETED_ATTACK`</li><li>valid index pointing to a non-`TARGETED_ATTACK` card</li></ul> |
| Output: side effects          | State      | Values: <ul><li>throws `IllegalArgumentException`</li><li>card removed from hand and added to discard pile</li></ul>                     |

- **Step 4:**
    - **TC1: play_NegativeIndex_ThrowsIllegalArgumentException** (:x::white_check_mark:)
        - **State of the system**: player has one `TARGETED_ATTACK` card, card index is `-1`
        - **Expected output**: throws `IllegalArgumentException`

    - **TC2: play_IndexEqualsHandSize_ThrowsIllegalArgumentException** (:x::white_check_mark:)
        - **State of the system**: player has one `TARGETED_ATTACK` card, card index equals hand size
        - **Expected output**: throws `IllegalArgumentException`

    - **TC3: play_NotTargetedAttackCard_ThrowsIllegalArgumentException** (:x::white_check_mark:)
        - **State of the system**: player has one `SKIP` card, card index is `0`
        - **Expected output**: throws `IllegalArgumentException`

    - **TC4: play_ValidCard_RemovesFromHandAndAddsToDiscard** (:x::white_check_mark:)
        - **State of the system**: player `Sophie` has one `TARGETED_ATTACK` card, card index is `0`
        - **Expected output**: `TARGETED_ATTACK` removed from `Sophie`'s hand, added to discard pile, hand size is `0`, discard size is `1`