package domain.game;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public final class AttackCardController {
    private static final int ATTACK_TURN_COUNT = 2;
    private static final String INVALID_INDEX_MESSAGE = "cardIndex is out of bounds";
    private static final String NOT_ATTACK_CARD_MESSAGE = "selected card is not an attack card";
    private static final String PLAYER_NOT_NULL_MESSAGE = "player must not be null";

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Controller keeps the injected draw pile for turn-flow coordination.")
    private final Deck drawPile;

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Controller must add the played Attack card to the injected discard pile.")
    private final DiscardPile discardPile;

    public AttackCardController(Deck drawPile, DiscardPile discardPile) {
        this.drawPile = drawPile;
        this.discardPile = discardPile;
    }

    public int play(Player player, int cardIndex) {
        if (player == null) {
            throw new NullPointerException(PLAYER_NOT_NULL_MESSAGE);
        }
        if (cardIndex < 0 || cardIndex >= player.getHandSize()) {
            throw new IllegalArgumentException(INVALID_INDEX_MESSAGE);
        }
        Card attackCard = player.getHandSnapshot().get(cardIndex);
        if (attackCard.getType() != CardType.ATTACK) {
            throw new IllegalArgumentException(NOT_ATTACK_CARD_MESSAGE);
        }

        player.removeCard(cardIndex);
        discardPile.add(attackCard);
        return ATTACK_TURN_COUNT;
    }

    // turn advancement, pending forced turns, and 'rest of the' Attack stacking belong in a turn/game controller
}
