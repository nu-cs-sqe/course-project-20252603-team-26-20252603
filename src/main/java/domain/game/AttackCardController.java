package domain.game;

public final class AttackCardController {
    private static final int ATTACK_TURN_COUNT = 2;
    private static final String NOT_ATTACK_CARD_MESSAGE = "selected card is not an attack card";

    private final Deck drawPile;
    private final DiscardPile discardPile;

    public AttackCardController(Deck drawPile, DiscardPile discardPile) {
        this.drawPile = drawPile;
        this.discardPile = discardPile;
    }

    public int play(Player player, int cardIndex) {
        Card attackCard = player.getHandSnapshot().get(cardIndex);
        if (attackCard.getType() != CardType.ATTACK) {
            throw new IllegalArgumentException(NOT_ATTACK_CARD_MESSAGE);
        }

        player.removeCard(cardIndex);
        discardPile.add(attackCard);
        return ATTACK_TURN_COUNT;
    }
}
