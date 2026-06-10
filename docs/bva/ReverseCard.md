# BVA Analysis for Reverse Card

## Method under test: `GameController.playReverse(int cardIndex)`

| ID      | State of system                      | Expected output                              | Implemented? |
|---------|--------------------------------------|----------------------------------------------|--------------| 
| `REV-1` | Player has reverse card, valid index | Card removed, direction flips, turn advances | yes          |
| `REV-2` | Player doesn't have reverse card     | Error message, card not removed              | yes          |
| `REV-3` | Invalid card index (-1)              | Error message                                | yes          |
| `REV-4` | Index equals handSize                | Error message                                | yes          |

## Method under test: `Game.reverseDirection()`

| ID          | State of system  | Expected output  | Implemented? |
|-------------|------------------|------------------|--------------|
| `REV-DIR-1` | direction = 1    | direction = -1   | yes          |
| `REV-DIR-2` | direction = -1   | direction = 1    | yes          |


## Method under test: `Game.advanceTurnWithDirection()`

| ID          | State of system                      | Expected output  | Implemented? |
|-------------|--------------------------------------|------------------|--------------|
| `REV-ADV-1` | 3 players, direction = 1, index = 2  | index becomes 0  | yes          |
| `REV-ADV-2` | 3 players, direction = -1, index = 0 | index becomes 2  | yes          |