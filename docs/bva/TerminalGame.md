# BVA Analysis for the Terminal Game

## Feature under test

The terminal entry point constructs the complete deck, prompts for two to four
players, and repeats playable turns until one active player remains.

Per the rulebook, a normal turn permits zero or more card plays and ends with a
draw. Skip, Super Skip, Reverse, Attack, Targeted Attack, and Draw from Bottom
can end a turn without the normal top draw.

## Input and output spaces

| Input / state | Category | Cases |
|---|---|---|
| Player count | Integer interval | below 2; 2; 4; above 4; non-numeric |
| Turn choice | Integer / card index | 0 to draw; first card; last card; out of range; non-numeric |
| Continuing card | Card type | See the Future; Shuffle; Bury; Swap Top and Bottom; Cat pair |
| Ending card | Card type | Skip; Super Skip; Reverse; Attack; Targeted Attack; Draw from Bottom |
| Cat pair input | Two indices and target | matching pair; nonmatching pair; no eligible target |
| Game state | Active-player count | more than one; exactly one winner |

## Implemented test cases

| ID | Test | Expected behavior | Implemented? |
|---|---|---|---|
| `TERMINAL-1` | `promptCardChoice_DrawChoice_ReturnsZero` | `0` represents pass/draw. | :white_check_mark: |
| `TERMINAL-2` | `promptCardChoice_NonNumericThenDrawChoice_DisplaysErrorAndReturnsZero` | Non-numeric input is rejected and reprompted. | :white_check_mark: |
| `TERMINAL-3` | `playInteractiveTurn_DrawChoice_DrawsAndEndsTurn` | Drawing adds the card and advances play. | :white_check_mark: |
| `TERMINAL-4` | `playInteractiveTurn_ContinuingCard_PromptsAgainBeforeDrawing` | A continuing card permits another play before drawing. | :white_check_mark: |
| `TERMINAL-5` | `playSelectedCard_MatchingCatPair_PromptsSecondIndexAndTargetThenContinues` | A matching Cat pair steals from a selected player and continues the turn. | :white_check_mark: |
| `TERMINAL-6` | `playSelectedCard_NonmatchingCatPair_DisplaysErrorAndContinues` | Invalid Cat pairs leave game state unchanged. | :white_check_mark: |
| `TERMINAL-7` | `playSelectedCard_CatPairWithNoEligibleTarget_DisplaysErrorAndContinues` | Cat play is rejected when no opponent has cards. | :white_check_mark: |
| `TERMINAL-8` | `playSelectedCard_SuperSkip_ClearsForcedTurnsAndEndsTurn` | Super Skip clears pending turns and ends the turn. | :white_check_mark: |
| `TERMINAL-9` | `playSelectedCard_Reverse_ReversesDirectionAndEndsTurn` | Reverse changes direction and ends the turn. | :white_check_mark: |
| `TERMINAL-10` | `runGame_TwoPlayersDrawingUntilExplosion_StopsWithWinner` | The game loops until one winner remains. | :white_check_mark: |
| `TERMINAL-11` | `main_PassOnlyGame_DisplaysWinner` | The real entry point runs a complete scripted terminal game. | :white_check_mark: |

## Launch

Run the game with:

```text
./gradlew run
```
