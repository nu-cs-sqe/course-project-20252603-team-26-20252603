package domain.game;

public class SkipController {
    private final DiscardPile discardPile;
    private final String CARD_MUST_BE_SKIP = "card must be Skip";
    private final String CARD_NOT_NULL = "player must not be null";

    public SkipController(DiscardPile discardPile) {
        this.discardPile = discardPile;
    }

    public boolean play(Player player, int cardIndex) {
        if (player == null) {
            throw new NullPointerException(CARD_NOT_NULL);
        }
        Card selectedCard = player.getHandSnapshot().get(cardIndex);

        if (selectedCard.getType() != CardType.SKIP) {
            throw new IllegalArgumentException(CARD_MUST_BE_SKIP);
        }

        player.removeCard(cardIndex);
        discardPile.add(selectedCard);
        return true;
    }
}
