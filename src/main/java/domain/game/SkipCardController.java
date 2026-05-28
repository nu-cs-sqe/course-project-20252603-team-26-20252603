package domain.game;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public class SkipCardController {
    private static final String CARD_MUST_BE_SKIP = "card must be Skip";

    // Open to discussion here
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Controller must add the played card to the injected discard pile.")
    private final DiscardPile discardPile;

    public SkipCardController(DiscardPile discardPile) {
        this.discardPile = discardPile;
    }

    public boolean play(Player player, int cardIndex) {

        Card selectedCard = player.getHandSnapshot().get(cardIndex);

        if (selectedCard.getType() != CardType.SKIP) {
            throw new IllegalArgumentException(CARD_MUST_BE_SKIP);
        }

        player.removeCard(cardIndex);
        discardPile.add(selectedCard);
        return true;
    }
}
