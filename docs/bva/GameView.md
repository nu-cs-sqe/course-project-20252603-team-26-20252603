# BVA Analysis for GameView
### Method under test: `public void displayStartScreen()`
| Step 1                 | Step 2 | Step 3                                                       |
|------------------------|--------|--------------------------------------------------------------|
| Input 1: None          | N/A    | Values: N/A                                                  | 
| Output: console output | String | Values: <ul><li> Start Playing Exploding Kittens! </li></ul> |

- **Step 4:**
  - **TC1: displayStartScreen_DisplayOnce_ShowsTitle** ( x: or :white_check_mark: )
    - **State of the system**: call function `displayStartScreen()`
    - **Expected output**: Console shows "Start Playing Exploding Kittens"

### Method under test: `public void List<String> promptPlayerNames()`
| Step 1                                        | Step 2     | Step 3                                                                                                 |
|-----------------------------------------------|------------|--------------------------------------------------------------------------------------------------------|
| Input 1: player count (user input)            | Counts     | Values: <ul><li>2</li><li>3</li><li>4</li><li>5</li><li>1</li><li>6</li></ul>                          |
| Input 2: player name being added (user input) | Strings    | Values: <ul><li>empty string</li><li>1 character</li><li>20 characters</li><li>21 characters</li></ul> |
| Output: list of players                       | Collection | Values: <ul><li>2</li><li>3</li><li>4</li><li>5</li><li>1</li><li>6</li></ul>                          |

- **Step 4:**
    - **TC2: promptPlayerNames_2Players_ListSize2** ( :white_check_mark: )
        - **State of the system**: Fresh GameView
        - **User input**: 2 players
        - **Expected output**: List<String> with size = 2

    - **TC3: promptPlayerNames_3Players_ListSize3** ( :white_check_mark: )
        - **State of the system**: Fresh GameView
        - **User input**: 3 players
        - **Expected output**: List<String> with size = 3

    - **TC4: promptPlayerNames_4Players_ListSize4** ( :white_check_mark: )
        - **State of the system**: Fresh GameView
        - **User input**: 4 players
        - **Expected output**: List<String> with size = 4

    - **TC5: promptPlayerNames_5Players_ListSize5** ( :white_check_mark: )
        - **State of the system**: Fresh GameView
        - **User input**: 5 players
        - **Expected output**: List<String> with size = 5

    - **TC6: promptPlayerNames_1Player_ListSize1** (:white_check_mark: )
        - **State of the system**: Fresh GameView
        - **User input**: 1 player (controller will validate size 1 list not allowed)
        - **Expected output**: List<String> with size = 1
      
    - **TC7: promptPlayerNames_6Players_ListSize6** ( :white_check_mark: )
        - **State of the system**: Fresh GameView
        - **User input**: 6 players (controller will validate size 6 list not allowed)
        - **Expected output**: List<String> with size = 6

### Method under test: `public void displayGameReady()`
| Step 1                 | Step 2 | Step 3                                                  |
|------------------------|--------|---------------------------------------------------------|
| Input 1: None          | N/A    | Values: N/A                                             | 
| Output: console output | String | Values: <ul><li> Game is ready! Let's begin! </li></ul> |

- **Step 4:**
    - **TC8: displayGameReady_DisplayOnce_ShowsGameReadyMessage** ( x: or :white_check_mark: )
        - **State of the system**: call function `displayGameReady()`
        - **Expected output**: Console shows "Game is ready! Let's begin!"

### Method under test: `public void displayHand(String playerName, List<Card> cards)`
| Step 1                 | Step 2     | Step 3                                                    |
|------------------------|------------|-----------------------------------------------------------|
| Input 1: player name   | String     | Values: <ul><li>Valid player name</li></ul>              |
| Input 2: cards in hand | Collection | Values: <ul><li>More than one card</li></ul>             |
| Output: console output | String     | Values: <ul><li>Player name and indexed card list</li></ul> |

- **Step 4:**
    - **TC10: displayHand_WithCards_ShowsPlayerNameAndIndexedCards** (:white_check_mark:)
        - **State of the system**: call `displayHand("Sophie", [SKIP, BEARD_CAT])`
        - **Expected output**: Console shows "Sophie, your hand:", "1. SKIP", and "2. BEARD_CAT"

  - - **TC: displayHand_EmptyHand_ShowsHeaderOnly** (:white_check_mark:)
    - **State of the system**: call `displayHand("Sophie", [])`
    - **Expected output**: Console shows `"Sophie, your hand:"`, no card lines

  - **TC: displayHand_OneCard_ShowsHeaderAndSingleIndexedCard** (:white_check_mark:)
    - **State of the system**: call `displayHand("Sophie", [SKIP])`
    - **Expected output**: Console shows `"Sophie, your hand:"` and `"1. SKIP"`, no second card line


### Method under test: `public void displayGameOver(String winnerName)`
| Step 1                 | Step 2 | Step 3                                                     |
|------------------------|--------|------------------------------------------------------------|
| Input 1: winner name   | String | Values: <ul><li> Name of the remaining player </li></ul>   |
| Output: console output | String | Values: <ul><li> Game over message with winner name </li></ul> |

- **Step 4:**
    - **TC9: displayGameOver_WithWinnerName_ShowsGameOverAndWinner** (:white_check_mark:)
        - **State of the system**: call function `displayGameOver("Jordan")`
        - **Expected output**: Console shows "Game over! Jordan wins!"

### Method under test: `public void displayError(String message)`
| Step 1                 | Step 2 | Step 3                                                                                          |
|------------------------|--------|-------------------------------------------------------------------------------------------------|
| Input 1: message       | String | Values: <ul><li>null</li><li>empty string</li><li>normal string</li></ul>                       |
| Output: console output | String | Values: <ul><li>error prefix only</li><li>error prefix and message</li><li>exception</li></ul>  |

- **Step 4:**
    - **TC: displayError_NullMessage_ThrowsException** ( :white_check_mark: )
        - **State of the system**: call `displayError(null)`
        - **Expected output**: throws `IllegalArgumentException`

    - **TC: displayError_EmptyMessage_ShowsPrefixOnly** (  :white_check_mark: )
        - **State of the system**: call `displayError("")`
        - **Expected output**: Console shows `"Error: "`

    - **TC: displayError_NormalMessage_ShowsPrefixAndMessage** ( :white_check_mark: )
        - **State of the system**: call `displayError("something went wrong")`
        - **Expected output**: Console shows `"Error: something went wrong"`

### Method under test: `public Player promptTargetPlayer(List<Player> players)`
| Step 1                        | Step 2     | Step 3                                                                                                                                    |
|-------------------------------|------------|-------------------------------------------------------------------------------------------------------------------------------------------|
| Input 1: players list         | Collection | Values: <ul><li>one player</li><li>more than one player</li></ul>                                                                        |
| Input 2: user input           | Integer    | Values: <ul><li>1 (min valid index)</li><li>size of list (max valid index)</li></ul>                                                     |
| Output: selected player       | Object     | Values: <ul><li>returns first player</li><li>returns last player</li></ul>                                                               |

- **Step 4:**
    - **TC1: promptTargetPlayer_OnePlayer_ReturnsOnlyPlayer** (:white_check_mark: )
        - **State of the system**: players list has one player `Jordan`, user inputs `1`
        - **Expected output**: returns `Jordan`

    - **TC2: promptTargetPlayer_MultiplePlayersSelectFirst_ReturnsFirstPlayer** (:white_check_mark: )
        - **State of the system**: players list has `Jordan` and `Casey`, user inputs `1`
        - **Expected output**: returns `Jordan`

    - **TC3: promptTargetPlayer_MultiplePlayersSelectLast_ReturnsLastPlayer** ( :white_check_mark: )
        - **State of the system**: players list has `Jordan` and `Casey`, user inputs `2`
        - **Expected output**: returns `Casey`

### Method under test: `public void displayCardDrawn(Card card)`
| Step 1          | Step 2 | Step 3                                                                                                                                          |
|-----------------|--------|-------------------------------------------------------------------------------------------------------------------------------------------------|
| Input 1: card   | Object | Values: <ul><li>null</li><li>`EXPLODING_KITTEN`</li><li>`DEFUSE`</li><li>non-special card (`PLACEHOLDER_CARD`)</li></ul>                        |
| Output: console output | String | Values: <ul><li>throws exception</li><li>shows card type in message</li></ul> |

- **Step 4:**
    - **TC: displayCardDrawn_NullCard_ThrowsException** ( :white_check_mark:)
        - **State of the system**: call `displayCardDrawn(null)`
        - **Expected output**: throws `IllegalArgumentException`

    - **TC: displayCardDrawn_ExplodingKitten_ShowsExplodingKitten** ( :white_check_mark:)
        - **State of the system**: call `displayCardDrawn(new Card(CardType.EXPLODING_KITTEN))`
        - **Expected output**: Console shows `"EXPLODING_KITTEN"`

    - **TC: displayCardDrawn_Defuse_ShowsDefuse** ( :white_check_mark:)
        - **State of the system**: call `displayCardDrawn(new Card(CardType.DEFUSE))`
        - **Expected output**: Console shows `"DEFUSE"`

    - **TC: displayCardDrawn_PlaceholderCard_ShowsPlaceholderCard** ( :white_check_mark:)
        - **State of the system**: call `displayCardDrawn(new Card(CardType.PLACEHOLDER_CARD))`
        - **Expected output**: Console shows `"PLACEHOLDER_CARD"`

### Method under test: `public void displayMessage(String message)`
| Step 1                 | Step 2 | Step 3                                                                                          |
|------------------------|--------|-------------------------------------------------------------------------------------------------|
| Input 1: message       | String | Values: <ul><li>empty string</li><li>normal string</li></ul>                                    |
| Output: console output | String | Values: <ul><li>blank line</li><li>message text</li></ul>                                       |

- **Step 4:**
    - **TC: displayMessage_EmptyString_PrintsBlankLine** (:white_check_mark:)
        - **State of the system**: call `displayMessage("")`
        - **Expected output**: console output is not null

    - **TC: displayMessage_NormalString_ShowsMessage** (:white_check_mark:)
        - **State of the system**: call `displayMessage("Skip played. Your turn ends without drawing a card.")`
        - **Expected output**: Console shows `"Skip played. Your turn ends without drawing a card."`

### Method under test: `public void displaySeeTheFutureCards(List<Card> cards)`
| Step 1                 | Step 2     | Step 3                                                                                                              |
|------------------------|------------|---------------------------------------------------------------------------------------------------------------------|
| Input 1: cards         | Collection | Values: <ul><li>empty list</li><li>one card</li><li>two cards (peek maximum)</li></ul>                              |
| Output: console output | String     | Values: <ul><li>title and no cards message</li><li>title and one indexed card</li><li>title and two indexed cards</li></ul> |

- **Step 4:**
    - **TC: displaySeeTheFutureCards_EmptyList_ShowsTitleAndNoCardsMessage** (:white_check_mark:)
        - **State of the system**: call `displaySeeTheFutureCards([])`
        - **Expected output**: Console shows `"See the Future: Top cards in the draw pile:"` and `"No cards to view."`

    - **TC: displaySeeTheFutureCards_OneCard_ShowsTitleAndSingleCard** (:white_check_mark:)
        - **State of the system**: call `displaySeeTheFutureCards([ATTACK])`
        - **Expected output**: Console shows title, `"1. ATTACK"`, no second card line

    - **TC: displaySeeTheFutureCards_TwoCards_ShowsTitleAndBothCardsInOrder** (:white_check_mark:)
        - **State of the system**: call `displaySeeTheFutureCards([ATTACK, SHUFFLE])`
        - **Expected output**: Console shows title, `"1. ATTACK"`, `"2. SHUFFLE"`