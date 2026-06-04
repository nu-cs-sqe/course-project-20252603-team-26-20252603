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

## Method under test: `public void nextTurn()`

| ID            | State of the system               | Expected output               | Implemented? |
|---------------|-----------------------------------|-------------------------------|--------------|
| `GAME-NEXT-1` | 2 players, currentPlayerIndex = 0 | Index becomes 1               | :x:          |
| `GAME-NEXT-2` | 2 players, currentPlayerIndex = 1 | Index becomes 0 (wraps)       | :x:          |
| `GAME-NEXT-3` | 3 players, currentPlayerIndex = 2 | Index becomes 0 (wraps)       | :x:          |
| `GAME-NEXT-4` | No players                        | No exception, index unchanged | :x:          |

