# BVA Analysis for `Game`

## Method under test: `Game(Deck drawPile)`

| ID | State of the system | Expected output | Implemented? |
| --- | --- | --- | --- |
| `GAME-CTOR-1` | Construct a game with `drawPile = null`. | Throw `IllegalArgumentException` with message `draw pile must not be null`. | :white_check_mark: |
| `GAME-CTOR-2` | Construct a game with a valid deck. | Game is created successfully with no players set up yet. | :white_check_mark: |

## Method under test: `public void setupGame(List<String> playerNames)`

| ID | State of the system | Expected output | Implemented? |
| --- | --- | --- | --- |
| `GAME-SETUP-1` | `playerNames = null`. | Throw `IllegalArgumentException` with message `player names must not be null`. | :white_check_mark: |
| `GAME-SETUP-2` | `playerNames.size() = 1`. | Throw `IllegalArgumentException` with message `player count must be between 2 and 4`. | :white_check_mark: |
| `GAME-SETUP-3` | `playerNames.size() = 2` and deck has enough non-special cards, defuses, and kittens. | Create `2` players, give each `6` cards, leave `1` kitten in draw pile, reset discard pile, and set current player to index `0`. | :white_check_mark: |
| `GAME-SETUP-4` | `playerNames.size() = 4` and deck has enough non-special cards, defuses, and kittens. | Create `4` players, give each `6` cards, leave `3` kittens in draw pile, reset discard pile, and set current player to index `0`. | :white_check_mark: |
| `GAME-SETUP-5` | `playerNames.size() = 5`. | Throw `IllegalArgumentException` with message `player count must be between 2 and 4`. | :white_check_mark: |
| `GAME-SETUP-6` | Valid player count but deck contains fewer defuses than players. | Throw `IllegalStateException` with message `deck must contain at least one defuse per player`. | :white_check_mark: |
| `GAME-SETUP-7` | Valid player count but deck contains fewer exploding kittens than `playerCount - 1`. | Throw `IllegalStateException` with message `deck must contain enough exploding kittens for setup`. | :white_check_mark: |
| `GAME-SETUP-8` | Valid player count but deck contains fewer than `5 * playerCount` non-special cards. | Throw `IllegalStateException` with message `deck must contain enough non-special cards to deal opening hands`. | :white_check_mark: |
| `GAME-SETUP-9` | Valid 2-player game uses a real `Deck` with injected `Random`. | `setupGame` invokes the setup shuffle before dealing and the final shuffle after reinserting Defuses and Exploding Kittens, without assuming either shuffle must change deck order. | :white_check_mark: |

## Method under test: `public List<Player> getPlayers()`

| ID | State of the system | Expected output | Implemented? |
| --- | --- | --- | --- |
| `GAME-PLAYERS-1` | Game has not been set up yet. | Return `[]`. | :white_check_mark: |
| `GAME-PLAYERS-2` | Game was set up for `2` players. | Return the created players in input order. | implemented in `GAME-SETUP-3` |
| `GAME-PLAYERS-3` | Game was set up for `4` players. | Return the created players in input order. | implemented in `GAME-SETUP-4` |

## Method under test: `public Player getCurrentPlayer()`

| ID | State of the system | Expected output | Implemented? |
| --- | --- | --- | --- |
| `GAME-CURRENT-1` | Game has not been set up yet. | Throw `IllegalStateException` with message `game has not been set up`. | :white_check_mark: |
| `GAME-CURRENT-2` | Game was set up successfully. | Return the first player in the established turn order. | implemented in `GAME-SETUP-3` |

## Method under test: `void eliminatePlayer(Player player)`

| ID | State of the system | Expected output | Implemented? |
| --- | --- | --- | --- |
| `GAME-ELIMINATE-1` | Game has 3 active players; eliminated player is active. | Remove that player, keep the other 2 players active, and `isWon()` returns `false`. | :white_check_mark: |
| `GAME-ELIMINATE-2` | Game has 3 active players, the current player is the last player in turn order, and that current player is eliminated. | Remove that player and wrap the current player pointer to the first remaining player. | :white_check_mark: |

## Method under test: `boolean isWon()`

| ID | State of the system | Expected output | Implemented? |
| --- | --- | --- | --- |
| `GAME-WON-1` | Game has exactly 1 active player after elimination. | Return `true`. | :white_check_mark: |
| `GAME-WON-2` | More than 1 player remains, and one player has successfully Defused exactly 3 Exploding Kittens. | Return `true`. | :white_check_mark: |
| `GAME-WON-3` | A player has successfully Defused more than 3 Exploding Kittens. | Continue returning `true`; the earned win does not disappear. | :white_check_mark: |

## Method under test: `Player getWinner()`

| ID | State of the system | Expected output | Implemented? |
| --- | --- | --- | --- |
| `GAME-WINNER-1` | One active player remains after elimination. | Return the remaining player. | :white_check_mark: |
| `GAME-WINNER-2` | Multiple players remain, and one player has successfully Defused at least 3 Exploding Kittens. | Return the player who earned the alternate win. | :white_check_mark: |
| `GAME-WINNER-3` | Neither win condition has been met. | Throw `IllegalStateException` with message `game does not have a winner`. | :white_check_mark: |

## Method under test: `public void advanceTurn()`

| ID                | State of the system                                  | Expected output                                                       | Implemented? |
|-------------------|------------------------------------------------------|----------------------------------------------------------------------|--------------|
| `GAME-ADVANCE-1`  | Current player has no forced turns (normal play)     | Move to the next player                                               | :white_check_mark: |
| `GAME-ADVANCE-2`  | Current player still owes forced turns (under Attack) | Decrement forced turns and keep the same current player              | :white_check_mark: |

## Method under test: `public void applyAttack()`

| ID               | State of the system                                       | Expected output                                                              | Implemented? |
|------------------|----------------------------------------------------------|-----------------------------------------------------------------------------|--------------|
| `GAME-ATTACK-1`  | Attacker has no forced turns (fresh Attack)              | Move to the next player and force them to take 2 turns                       | :white_check_mark: |
| `GAME-ATTACK-2`  | Attacker is already under Attack (stacks the Attack)     | Move to the next player and force them to take the untaken turns plus 2      | :white_check_mark: |

### Method under test: `public void applyTargetedAttack(Player target)`
| Step 1                        | Step 2     | Step 3                                                                                                                                    |
|-------------------------------|------------|-------------------------------------------------------------------------------------------------------------------------------------------|
| Input 1: target player        | Object     | Values: <ul><li>null</li><li>valid player in the game</li><li>current player (self-target)</li></ul> |
| Internal state: forced turns  | Integer    | Values: <ul><li>0 forced turns</li><li>existing forced turns</li></ul>                                                                   |
| Output: side effects          | State      | Values: <ul><li>throws `IllegalArgumentException`</li><li>current player set to target, forced turns += 2</li><li>forced turns stack with existing</li></ul> |

- **Step 4:**
    - **TC1: applyTargetedAttack_NullTarget_ThrowsIllegalArgumentException** (x:white_check_mark:)
        - **State of the system**: game has two players `Sophie` and `Jordan`, target is `null`
        - **Expected output**: throws `IllegalArgumentException`

    - **TC2: applyTargetedAttack_ValidTarget_SetsCurrentPlayerToTargetWithTwoForcedTurns** (x:white_check_mark:)
        - **State of the system**: game has two players `Sophie` and `Jordan`, current player is `Sophie`, target is `Jordan`, forced turns is `0`
        - **Expected output**: current player is `Jordan`, forced turns is `2`

    - **TC3: applyTargetedAttack_SelfTarget_ThrowsIllegalArgumentException** (x:white_check_mark:)
        - **State of the system**: game has two players `Sophie` and `Jordan`, current player is `Sophie`, target is `Sophie`
        - **Expected output**: throws `IllegalArgumentException`

    - **TC4: applyTargetedAttack_WithExistingForcedTurns_StacksForcedTurns** (x:white_check_mark:)
        - **State of the system**: game has two players `Sophie` and `Jordan`, current player is `Sophie`, forced turns is `1`, target is `Jordan`
        - **Expected output**: current player is `Jordan`, forced turns is `3`
