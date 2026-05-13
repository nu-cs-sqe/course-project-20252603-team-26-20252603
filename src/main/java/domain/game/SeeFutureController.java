package domain.game;

import java.util.List;

public class SeeFutureController {
    private Deck drawPile;
    private DiscardPile discardPile;

    public SeeFutureController(Deck drawPile, DiscardPile discardPile) {
        this.drawPile = drawPile;
        this.discardPile = discardPile;
    }

    public List<Card> play(Player player, int cardIndex) {
        return drawPile.peekTopCards(2);
    }

}
