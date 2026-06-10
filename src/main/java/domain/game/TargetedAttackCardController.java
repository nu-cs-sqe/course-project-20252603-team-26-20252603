package domain.game;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public final class TargetedAttackCardController {
    private final DiscardPile discardPile;
    private static final String NOT_TARGETED_ATTACK_CARD_MESSAGE = "selected card is not a targeted attack card";

    private static final String INVALID_INDEX_MESSAGE = "cardIndex is out of bounds";


    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Controller must add the played card to the injected discard pile.")

    public TargetedAttackCardController(DiscardPile discardPile) {
        this.discardPile = discardPile;
    }

    public void play(Player player, int cardIndex) {
        if (cardIndex < 0 || cardIndex >= player.getHandSize()) {
            throw new IllegalArgumentException(INVALID_INDEX_MESSAGE);
        }
        Card selectedCard = player.getHandSnapshot().get(cardIndex);

        if (selectedCard.getType() != CardType.TARGETED_ATTACK) {
            throw new IllegalArgumentException(NOT_TARGETED_ATTACK_CARD_MESSAGE);
        }

        player.removeCard(cardIndex);
        discardPile.add(selectedCard);
    }
    }
