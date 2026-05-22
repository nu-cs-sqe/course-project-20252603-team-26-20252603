# BVA Analysis for TakeCard
### Method under test: `public Card takeCard()` controller method
| Step 1                                                    | Step 2      | Step 3                                                                              |
|-----------------------------------------------------------|-------------|-------------------------------------------------------------------------------------|
| Input 1: deck state (implicit)                            | Count       | Values: <ul><li>Deck size = 0</li><li>Deck size = 1</li><li>Deck size > 1</li></ul> | 
| Output: drawn card or exception                           | N/A         | Values: N/A                                                                         |

- **Step 4:**
    - **TC1: takeCard_DeckSizeZero_ThrowsException** (:white_check_mark:)
        - **State of system**: Deck size = 0 (empty), Player hand = []
        - **Expected output**: IllegalStateException thrown, Deck unchanged (size 0), Player hand unchanged (size 0)

    - **TC2: takeCard_DeckSizeOne_ReturnsCard** (:white_check_mark:)
        - **State of system**: Deck size = 1, Deck = [Card_X], Player hand = []
        - **Expected output**: Returns Card_X, Deck becomes [] (size = 0), Player hand = [Card_X] (size = 1)

    - **TC3: takeCard_DeckSizeGreaterThanOne_ReturnsCard** (:white_check_mark:)
        - **State of system**: Deck size = 3, Deck = [Card_A, Card_B, Card_C], Player hand = []
        - **Expected output**: Returns Card_C, Deck becomes [Card_A, Card_B] (size = 2), Player hand = [Card_C] (size = 1)

    - **TC6: takeCard_ExplodingKittenWithoutDefuse_EliminatesPlayerAndGameContinues** (:white_check_mark:)
        - **State of system**: Current player hand = [], draw pile top card = `EXPLODING_KITTEN`, 3 active players
        - **Expected output**: Returns `EXPLODING_KITTEN`, does not add it to the player's hand, removes the player from the game, leaves 2 active players, and does not display game over

    - **TC7: takeCard_ExplodingKittenWithoutDefuse_EliminatesPlayerAndDisplaysGameOver** (:white_check_mark:)
        - **State of system**: Current player hand = [], draw pile top card = `EXPLODING_KITTEN`, 2 active players
        - **Expected output**: Returns `EXPLODING_KITTEN`, does not add it to the player's hand, removes the player from the game, leaves 1 active player, and displays game over


### Method under test: `public void displayCardDrawn(Card card)` view method
| Step 1                 | Step 2 | Step 3                                                                                                                                      |
|------------------------|--------|---------------------------------------------------------------------------------------------------------------------------------------------|
| Input 1: Card object   | Card   | Values: <ul><li>Card with type OTHER</li><li>Card with type DEFUSE</li><li>Card with type EXPLODING_KITTEN</li><li>null (invalid)</li></ul> | 
| Output: console output | String | Values: <ul><li> "You drew: [Card_Type]"</li><li>NullPointerException for null</li></ul>                                                    |

- **Step 4:**
    - **TC4: displayCardDrawn_OtherCard_ShowsOther** (:white_check_mark: )
        - **State of system**: Call `displayCardDrawn(new Card(CardType.OTHER))`
        - **Expected output**: Console shows "You drew: " followed by the card's type

    - **TC5: displayCardDrawn_NullCard_ThrowsException** (:white_check_mark: )
        - **State of system**: Call `displayCardDrawn(null)`
        - **Expected output**: NullPointerException thrown
