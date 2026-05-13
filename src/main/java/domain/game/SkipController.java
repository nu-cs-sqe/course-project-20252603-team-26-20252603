package domain.game;

public class SkipController {
    private final DiscardPile discardPile;

    public SkipController(DiscardPile discardPile) {
        this.discardPile = discardPile;
    }

    public boolean play(Player player, int cardIndex) {
        Card selectedCard = player.getHandSnapshot().get(cardIndex);

        player.removeCard(cardIndex);
        return true;
    }
}
