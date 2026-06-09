package domain.game;

public final class BuryCardController {
    public void play(Game game, int cardIndex) {
        Player currentPlayer = game.getCurrentPlayer();
        Card buryCard = currentPlayer.getHandSnapshot().get(cardIndex);

        currentPlayer.removeCard(cardIndex);
        game.getDiscardPile().add(buryCard);
        game.getDrawPile().moveTopToBottom();
    }
}
