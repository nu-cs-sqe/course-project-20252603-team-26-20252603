package domain.game;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;

public final class SuperSkipCardController {
    private static final String NOT_SUPER_SKIP_MESSAGE = "selected card is not a Super Skip card";
    private static final String INVALID_INDEX_MESSAGE = "cardIndex is out of bounds";
    private final DiscardPile discardPile;

    @SuppressFBWarnings("EI")
    public SuperSkipCardController(DiscardPile discardPile) {
        this.discardPile = discardPile;
    }

    public boolean play(Player player, int cardIndex) {
        if (cardIndex < 0 || cardIndex >= player.getHandSize()) {
            throw new IllegalArgumentException(INVALID_INDEX_MESSAGE);
        }

        Card selectedCard = player.getHandSnapshot().get(cardIndex);

        if (selectedCard.getType() != CardType.SUPER_SKIP) {
            throw new IllegalArgumentException(NOT_SUPER_SKIP_MESSAGE);
        }

        player.removeCard(cardIndex);
        discardPile.add(selectedCard);
        return true;
        }
}
