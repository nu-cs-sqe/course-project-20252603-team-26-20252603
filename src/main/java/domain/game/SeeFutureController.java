package domain.game;

import java.util.List;

public class SeeFutureController {
    private Deck drawPile;
    private DiscardPile discardPile;
    private static final String SEE_FUTURE_REQUIRED_MESSAGE = "card must be See the Future";

    public SeeFutureController(Deck drawPile, DiscardPile discardPile) {
        this.drawPile = drawPile;
        this.discardPile = discardPile;
    }

    public List<Card> play(Player player, int cardIndex) {
        Card selectedCard = player.getHandSnapshot().get(cardIndex);
        if (selectedCard.getType() != CardType.SEE_THE_FUTURE) {
            throw new IllegalArgumentException(SEE_FUTURE_REQUIRED_MESSAGE);
        }
        player.removeCard(cardIndex);
        discardPile.add(selectedCard);
        return drawPile.peekTopCards(2);
    }

}
