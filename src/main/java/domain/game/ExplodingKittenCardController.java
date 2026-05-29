package domain.game;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import java.util.Objects;

public final class ExplodingKittenCardController {
    private static final String EXPLODING_KITTEN_REQUIRED_MESSAGE =
            "exploding kitten must not be null";
    private static final String EXPLODING_KITTEN_TYPE_REQUIRED_MESSAGE =
            "drawn card must be an exploding kitten";

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Controller must return a defused Exploding Kitten to the injected draw pile.")
    private final Deck drawPile;

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Controller must discard a used Defuse into the injected discard pile.")
    private final DiscardPile discardPile;

    public ExplodingKittenCardController(Deck drawPile, DiscardPile discardPile) {
        this.drawPile = drawPile;
        this.discardPile = discardPile;
    }

    public boolean play(Player player, Card explodingKitten) {
        Objects.requireNonNull(explodingKitten, EXPLODING_KITTEN_REQUIRED_MESSAGE);
        if (explodingKitten.getType() != CardType.EXPLODING_KITTEN) {
            throw new IllegalArgumentException(EXPLODING_KITTEN_TYPE_REQUIRED_MESSAGE);
        }
        int defuseIndex = findDefuseIndex(player);
        if (defuseIndex < 0) {
            return false;
        }

        Card defuse = player.getHandSnapshot().get(defuseIndex);
        player.removeCard(defuseIndex);
        discardPile.add(defuse);
        drawPile.addCard(explodingKitten);
        return true;
    }

    private int findDefuseIndex(Player player) {
        List<Card> hand = player.getHandSnapshot();
        for (int index = 0; index < hand.size(); index++) {
            if (hand.get(index).getType() == CardType.DEFUSE) {
                return index;
            }
        }
        return -1;
    }
}
