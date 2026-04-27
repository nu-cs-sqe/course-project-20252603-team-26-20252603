# BVA Analysis for `Player`

## Method under test: `Player(String name)`

| ID | State of the system | Expected output | Implemented? |
| --- | --- | --- | --- |
| `PLAYER-CTOR-1` | Construct a player with `name = null`. | Throw `IllegalArgumentException` with message `name must not be blank`. | :white_check_mark: |
| `PLAYER-CTOR-2` | Construct a player with `name = ""`. | Throw `IllegalArgumentException` with message `name must not be blank`. | :white_check_mark: |
| `PLAYER-CTOR-3` | Construct a player with `name = "Avery"`. | Player is created successfully and starts with an empty hand. | :white_check_mark: |

## Method under test: `public String getName()`

| ID | State of the system | Expected output | Implemented? |
| --- | --- | --- | --- |
| `PLAYER-NAME-1` | Player was constructed with `name = "Avery"`. | Return `"Avery"`. | :white_check_mark: |

## Method under test: `public void addCard(Card card)`

| ID | State of the system | Expected output | Implemented? |
| --- | --- | --- | --- |
| `PLAYER-ADD-1` | Empty hand, add a non-null card. | Hand size increases from `0` to `1`; added card is stored. | :white_check_mark: |
| `PLAYER-ADD-2` | Hand already contains `1` card, add another non-null card. | Hand size increases from `1` to `2`; new card is appended. | :white_check_mark: |
| `PLAYER-ADD-3` | Any hand state, add `null`. | Throw `IllegalArgumentException` with message `card must not be null`. | :white_check_mark: |

## Method under test: `public int getHandSize()`

| ID | State of the system | Expected output | Implemented? |
| --- | --- | --- | --- |
| `PLAYER-SIZE-1` | New player with no cards. | Return `0`. | :white_check_mark: |
| `PLAYER-SIZE-2` | Player has `1` card after `PLAYER-ADD-1`. | Return `1`. | implemented in `PLAYER-ADD-1` |
| `PLAYER-SIZE-3` | Player has `2` cards after `PLAYER-ADD-2`. | Return `2`. | implemented in `PLAYER-ADD-2` |
