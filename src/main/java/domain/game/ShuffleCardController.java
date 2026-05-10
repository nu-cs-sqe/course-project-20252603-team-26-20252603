package domain.game;

public final class ShuffleCardController {
    public void play(Game game, int cardIndex) {
        Player currentPlayer = game.getCurrentPlayer();
        Card shuffleCard = currentPlayer.getHandSnapshot().get(cardIndex);

        currentPlayer.removeCard(cardIndex);
        game.getDiscardPile().add(shuffleCard);
        game.getDrawPile().shuffle();
    }
}
