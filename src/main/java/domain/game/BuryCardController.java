package domain.game;

public final class BuryCardController {
    private static final String NOT_BURY_CARD_MESSAGE = "selected card is not a Bury card";

    public void play(Game game, int cardIndex) {
        Player currentPlayer = game.getCurrentPlayer();
        Card buryCard = currentPlayer.getHandSnapshot().get(cardIndex);
        if (buryCard.getType() != CardType.BURY) {
            throw new IllegalArgumentException(NOT_BURY_CARD_MESSAGE);
        }

        currentPlayer.removeCard(cardIndex);
        game.getDiscardPile().add(buryCard);
        game.getDrawPile().moveTopToBottom();
    }
}
