# BVA Analysis for Reverse Card

## Method under test: `GameController.playReverse(int cardIndex)`

| ID      | State of system                      | Expected output                              | Implemented?  |
|---------|--------------------------------------|----------------------------------------------|---------------| 
| `REV-1` | Player has reverse card, valid index | Card removed, direction flips, turn advances | :x:           |
| `REV-2` | Player doesn't have reverse card     | Error message, card not removed              | :x:           |
| `REV-3` | Invalid card index (-1)              | Error message                                | :x:           |
| `REV-4` | Index equals handSize                | Error message                                | :x:           |

## Method under test: `Game.reverseDirection()`

| ID          | State of system  | Expected output  | Implemented? |
|-------------|------------------|------------------|--------------|
| `REV-DIR-1` | direction = 1    | direction = -1   | yes          |
| `REV-DIR-2` | direction = -1   | direction = 1    | yes          |