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
        model.advanceTurn();
        return drawnCard;
    }

    public boolean playSkip(int cardIndex) {
        try {
            Player currentPlayer = model.getCurrentPlayer();

            SkipCardController skipCardController = new SkipCardController(model.getDiscardPile());

            skipCardController.play(currentPlayer, cardIndex);
            view.displayMessage(SKIP_PLAYED);
            return true;
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            view.displayError(e.getMessage());
            return false;
        }
    }
}
