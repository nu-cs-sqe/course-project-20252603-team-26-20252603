package domain.game;

public final class ShuffleCardController {
    private static final String NOT_SHUFFLE_CARD_MESSAGE = "selected card is not a shuffle card";

    public void play(Game game, int cardIndex) {
        Player currentPlayer = game.getCurrentPlayer();
        Card shuffleCard = currentPlayer.getHandSnapshot().get(cardIndex);
        if (shuffleCard.getType() != CardType.SHUFFLE) {
            throw new IllegalArgumentException(NOT_SHUFFLE_CARD_MESSAGE);
        }

        currentPlayer.removeCard(cardIndex);
        game.getDiscardPile().add(shuffleCard);
        game.getDrawPile().shuffle();
    }
}
