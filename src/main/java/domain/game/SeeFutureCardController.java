package domain.game;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;

public class SeeFutureCardController {
    // Open to discussion here
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Controller must read from the injected draw pile.")
    private Deck drawPile;

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Controller must add the played card to the injected discard pile.")
    private DiscardPile discardPile;
    private static final String SEE_FUTURE_REQUIRED_MESSAGE = "card must be See the Future";
    private static final String INVALID_INDEX_MESSAGE = "cardIndex is out of bounds";
    public SeeFutureCardController(Deck drawPile, DiscardPile discardPile) {
        this.drawPile = drawPile;
        this.discardPile = discardPile;
    }

    public List<Card> play(Player player, int cardIndex) {

        if (cardIndex < 0 || cardIndex >= player.getHandSnapshot().size()) {
            throw new IllegalArgumentException(INVALID_INDEX_MESSAGE);
        }
        Card selectedCard = player.getHandSnapshot().get(cardIndex);
        if (selectedCard.getType() != CardType.SEE_THE_FUTURE) {
            throw new IllegalArgumentException(SEE_FUTURE_REQUIRED_MESSAGE);
        }
        player.removeCard(cardIndex);
        discardPile.add(selectedCard);
        return drawPile.peekTopCards(2);
    }

}
