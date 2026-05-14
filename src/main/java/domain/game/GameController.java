package domain.game;

import ui.GameView;

import java.util.List;

public class GameController {
    private Game model;

    private GameView view;
    private final String SKIP_PLAYED = "Skip played. Your turn ends without drawing a card.";
    public GameController(Game model, GameView view){
        this.model = model;
        this.view = view;
    }

    public void startGame(List<String> playerNames){
       try{
          model.setupGame(playerNames);
          view.displayGameReady();
       }catch (IllegalArgumentException e){
           view.displayError(e.getMessage());
       }
    }

    public Card takeCard() {
        Player currentPlayer = model.getCurrentPlayer();
        Card drawnCard = model.getDrawPile().draw();
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
}
