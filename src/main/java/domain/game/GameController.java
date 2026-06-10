package domain.game;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import ui.GameView;

import java.util.List;

public class GameController {
    private static final String SKIP_PLAYED = "Skip played. Your turn ends without drawing a card.";
    private static final String UNPLAYABLE_CARD =
            "Card cannot be played during a normal turn.";
    private static final String INVALID_CARD_INDEX = "cardIndex is out of bounds";
    private static final String SUPER_SKIP_PLAYED = "Super Skip played! All forced turns cleared. Turn ended.";
    private static final String REVERSE_PLAYED = "Reverse played! Direction changed. Turn ended.";

    // Open to discussion here  
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Controller coordinates the injected game model as shared application state.")
    private Game model;

    private GameView view;

    public GameController(Game model, GameView view) {
        this.model = model;
        this.view = view;
    }

    public void startGame(List<String> playerNames) {
        try {
            model.setupGame(playerNames);
            view.displayGameReady();
        } catch (IllegalArgumentException e) {
            view.displayError(e.getMessage());
        }
    }

    public void startTurn() {
        view.displayPublicPlayerState(model.getPlayers(), model.getEliminatedPlayers());

        Player currentPlayer = model.getCurrentPlayer();
        view.displayHand(currentPlayer.getName(), currentPlayer.getHandSnapshot());
    }

    public void completeTurn(List<Integer> cardIndexes) {
        startTurn();
        for (int cardIndex : cardIndexes) {
            Player currentPlayer = model.getCurrentPlayer();
            if (cardIndex < 0 || cardIndex >= currentPlayer.getHandSize()) {
                view.displayError(INVALID_CARD_INDEX);
                continue;
            }
            Card selectedCard = currentPlayer.getHandSnapshot().get(cardIndex);
            if (selectedCard.getType() == CardType.SKIP && playSkip(cardIndex)) {
                return;
            }
            if (selectedCard.getType() == CardType.SEE_THE_FUTURE) {
                SeeFutureCardController seeFutureController =
                        new SeeFutureCardController(model.getDrawPile(), model.getDiscardPile());
                view.displaySeeTheFutureCards(seeFutureController.play(currentPlayer, cardIndex));
                continue;
            }
            if (selectedCard.getType() == CardType.SHUFFLE) {
                ShuffleCardController shuffleCardController = new ShuffleCardController();
                shuffleCardController.play(model, cardIndex);
                continue;
            }
            if (selectedCard.getType() == CardType.BURY) {
                BuryCardController buryCardController = new BuryCardController();
                buryCardController.play(model, cardIndex);
                continue;
            }
            if (selectedCard.getType() == CardType.ATTACK) {
                AttackCardController attackCardController =
                        new AttackCardController(model.getDrawPile(), model.getDiscardPile());
                attackCardController.play(currentPlayer, cardIndex);
                model.applyAttack();
                return;
            }
             if (selectedCard.getType() == CardType.DRAW_FROM_BOTTOM) {
                DrawFromBottomCardController drawFromBottomCardController =
                        new DrawFromBottomCardController(model.getDrawPile(), model.getDiscardPile());
                Card drawnCard = drawFromBottomCardController.play(currentPlayer, cardIndex);
                if (drawnCard.getType() == CardType.EXPLODING_KITTEN) {
                    view.displayCardDrawn(drawnCard);
                    ExplodingKittenCardController explodingKittenController =
                            new ExplodingKittenCardController(model.getDrawPile(), model.getDiscardPile());
                    boolean defused = explodingKittenController.play(currentPlayer, drawnCard);
                    if (defused) {
                        model.advanceTurn();
                    } else {
                        model.eliminatePlayer(currentPlayer);
                        if (model.isWon()) {
                            view.displayGameOver(model.getPlayers().get(0).getName());
                        }
                    }
                    return;
                }
                view.displayCardDrawn(drawnCard);
                model.advanceTurn();
                return;
            }

            if (selectedCard.getType() == CardType.SWAP_TOP_AND_BOTTOM) {
                new SwapTopAndBottomController().play(model, cardIndex);
                continue;
            }
            view.displayError(UNPLAYABLE_CARD);
        }
        takeCard();
    }

    public Card takeCard() {
        Player currentPlayer = model.getCurrentPlayer();
        Card drawnCard = model.getDrawPile().draw();
        if (drawnCard.getType() == CardType.EXPLODING_KITTEN) {
            view.displayCardDrawn(drawnCard);
            ExplodingKittenCardController explodingKittenController =
                    new ExplodingKittenCardController(model.getDrawPile(), model.getDiscardPile());
            boolean defused = explodingKittenController.play(currentPlayer, drawnCard);
            if (defused) {
                model.advanceTurn();
            } else {
                model.eliminatePlayer(currentPlayer, drawnCard);
                if (model.isWon()) {
                    view.displayGameOver(model.getPlayers().get(0).getName());
                }
            }
            return drawnCard;
        }
        currentPlayer.addCard(drawnCard);
        view.displayCardDrawn(drawnCard);
        model.advanceTurn();
        return drawnCard;
    }

    public boolean playSkip(int cardIndex) {
        try {
            Player currentPlayer = model.getCurrentPlayer();

            SkipCardController skipCardController = new SkipCardController(model.getDiscardPile());

            skipCardController.play(currentPlayer, cardIndex);
            view.displayMessage(SKIP_PLAYED);
            model.advanceTurn();
            return true;
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            view.displayError(e.getMessage());
            return false;
        }
    }

    public boolean playSuperSkip(int cardIndex) {
        try {
            Player currentPlayer = model.getCurrentPlayer();
            SuperSkipCardController superSkipController = new SuperSkipCardController(model.getDiscardPile());

            boolean played = superSkipController.play(currentPlayer, cardIndex);
            if (played) {
                model.endTurnClearingForced();
                view.displayMessage(SUPER_SKIP_PLAYED);
                return true;
            }
            return false;
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            view.displayError(e.getMessage());
            return false;
        }
    }

    public boolean playReverse(int cardIndex) {
        try {
            Player currentPlayer = model.getCurrentPlayer();
            ReverseCardController reverseController = new ReverseCardController(model.getDiscardPile());

            boolean played = reverseController.play(currentPlayer, cardIndex);
            if (played) {
                model.reverseDirection();
                model.advanceTurnWithDirection();
                view.displayMessage(REVERSE_PLAYED);
                return true;
            }
            return false;
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            view.displayError(e.getMessage());
            return false;
        }
    }



}
