package domain.game;

public final class ShuffleCardController {
    private static final String INVALID_INDEX_MESSAGE = "cardIndex is out of bounds";
    private static final String NOT_SHUFFLE_CARD_MESSAGE = "selected card is not a shuffle card";

    public void play(Game game, int cardIndex) {
        Player currentPlayer = game.getCurrentPlayer();
        if (cardIndex < 0 || cardIndex >= currentPlayer.getHandSize()) {
            throw new IllegalArgumentException(INVALID_INDEX_MESSAGE);
        }
        Card shuffleCard = currentPlayer.getHandSnapshot().get(cardIndex);
        if (shuffleCard.getType() != CardType.SHUFFLE) {
            throw new IllegalArgumentException(NOT_SHUFFLE_CARD_MESSAGE);
        }

        currentPlayer.removeCard(cardIndex);
        game.getDiscardPile().add(shuffleCard);

        game.getDrawPile().shuffle();
    }
}
