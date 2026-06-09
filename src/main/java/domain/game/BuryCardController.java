package domain.game;

public final class BuryCardController {
    private static final String INVALID_INDEX_MESSAGE = "cardIndex is out of bounds";
    private static final String NOT_BURY_CARD_MESSAGE = "selected card is not a Bury card";

    public void play(Game game, int cardIndex) {
        Player currentPlayer = game.getCurrentPlayer();
        if (cardIndex < 0) {
            throw new IllegalArgumentException(INVALID_INDEX_MESSAGE);
        }
        Card buryCard = currentPlayer.getHandSnapshot().get(cardIndex);
        if (buryCard.getType() != CardType.BURY) {
            throw new IllegalArgumentException(NOT_BURY_CARD_MESSAGE);
        }

        currentPlayer.removeCard(cardIndex);
        game.getDiscardPile().add(buryCard);
        game.getDrawPile().moveTopToBottom();
    }
}
