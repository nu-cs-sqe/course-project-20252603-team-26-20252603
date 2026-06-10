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

    - **TC3: promptPlayerNames_3Players_ListSize3** ( x: or :white_check_mark: )
        - **State of the system**: Fresh GameView
        - **User input**: 3 players
        - **Expected output**: List<String> with size = 3

    - **TC4: promptPlayerNames_4Players_ListSize4** ( x: or :white_check_mark: )
        - **State of the system**: Fresh GameView
        - **User input**: 4 players
        - **Expected output**: List<String> with size = 4

    - **TC5: promptPlayerNames_5Players_ListSize5** ( x: or :white_check_mark: )
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

### Method under test: `public void displayGameOver(String winnerName)`
| Step 1                 | Step 2 | Step 3                                                     |
|------------------------|--------|------------------------------------------------------------|
| Input 1: winner name   | String | Values: <ul><li> Name of the remaining player </li></ul>   |
| Output: console output | String | Values: <ul><li> Game over message with winner name </li></ul> |

- **Step 4:**
    - **TC9: displayGameOver_WithWinnerName_ShowsGameOverAndWinner** (:white_check_mark:)
        - **State of the system**: call function `displayGameOver("Jordan")`
        - **Expected output**: Console shows "Game over! Jordan wins!"
