package domain.game;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public final class ReverseCardController {
    private static final String NOT_REVERSE_CARD_MESSAGE = "selected card is not a Reverse card";
    private static final String INVALID_INDEX_MESSAGE = "cardIndex is out of bounds";
    private final DiscardPile discardPile;

    @SuppressFBWarnings("EI")
    public ReverseCardController(DiscardPile discardPile) {
        this.discardPile = discardPile;
    }

    public boolean play(Player player, int cardIndex) {
        if (cardIndex < 0 || cardIndex >= player.getHandSize()) {
            throw new IllegalArgumentException(INVALID_INDEX_MESSAGE);
        }

        Card selectedCard = player.getHandSnapshot().get(cardIndex);
        if (selectedCard.getType() != CardType.REVERSE) {
            throw new IllegalArgumentException(NOT_REVERSE_CARD_MESSAGE);
        }

        player.removeCard(cardIndex);
        discardPile.add(selectedCard);
        return true;
    }
}
