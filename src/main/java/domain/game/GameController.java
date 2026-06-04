package domain.game;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import ui.GameView;

import java.util.List;

public class GameController {
    private static final String SKIP_PLAYED = "Skip played. Your turn ends without drawing a card.";

    // Open to discussion here  
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Controller coordinates the injected game model as shared application state.")
    private Game model;

    private GameView view;

    private int pendingAttackTurns;

    private boolean attackStacking;

    private int currentTurnNumber;

    public GameController(Game model, GameView view) {
        this.model = model;
        this.view = view;
        this.pendingAttackTurns = 0;
        this.currentTurnNumber = 0;
    }

    public void startGame(List<String> playerNames) {
        try {
            model.setupGame(playerNames);
            view.displayGameReady();
        } catch (IllegalArgumentException e) {
            view.displayError(e.getMessage());
        }
    }

    public Card takeCard() {
        Player currentPlayer = model.getCurrentPlayer();
        Card drawnCard = model.getDrawPile().draw();
        if (drawnCard.getType() == CardType.EXPLODING_KITTEN) {
            view.displayCardDrawn(drawnCard);
            ExplodingKittenCardController explodingKittenController =
                    new ExplodingKittenCardController(model.getDrawPile(), model.getDiscardPile());
            boolean defused = explodingKittenController.play(currentPlayer, drawnCard);
            if (!defused) {
                model.eliminatePlayer(currentPlayer);
                if (model.isWon()) {
                    view.displayGameOver(model.getPlayers().get(0).getName());
                }
            }
            return drawnCard;
        }
        currentPlayer.addCard(drawnCard);
        view.displayCardDrawn(drawnCard);
        return drawnCard;
    }

    public boolean playSkip(int cardIndex) {
        try {
            Player currentPlayer = model.getCurrentPlayer();

            SkipCardController skipCardController = new SkipCardController(model.getDiscardPile());

            boolean skipped = skipCardController.play(currentPlayer, cardIndex);
            view.displayMessage(SKIP_PLAYED);
            return skipped;
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            view.displayError(e.getMessage());
            return false;
        }
    }

    public void playAttackCard(int cardIndex) {
        try {
            Player currentPlayer = model.getCurrentPlayer();
            AttackCardController attackController = new AttackCardController(
                    model.getDrawPile(), model.getDiscardPile());

            attackController.play(currentPlayer, cardIndex);

            int remaining = pendingAttackTurns - currentTurnNumber + 1;

            pendingAttackTurns = remaining + 2;
            currentTurnNumber = 1;

            view.displayMessage("Attack played! " + pendingAttackTurns + " turn(s) pending.");
            endTurn();
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            view.displayError(e.getMessage());
        }
    }

    public void endTurn() {
        if (pendingAttackTurns > 0) {
            pendingAttackTurns--;
            if (pendingAttackTurns > 0) {
                view.displayMessage(pendingAttackTurns + " attack turn(s) remain.");
                return;
            }
        }
        currentTurnNumber = 1;
        model.nextTurn();
    }

    // for testing attack card functionality
    void setPendingAttackTurns(int turns) {
        this.pendingAttackTurns = turns;
    }

    void setCurrentTurnNumber(int turnNumber) {
        this.currentTurnNumber = turnNumber;
    }

    int getPendingAttackTurns() {
        return pendingAttackTurns;
    }

    int getCurrentTurnNumber() {
        return currentTurnNumber;
    }


}
