package domain.game;

import java.util.Objects;

public final class ExplodingKittenCardController {
    private static final String DRAW_PILE_REQUIRED_MESSAGE = "draw pile must not be null";
    private static final String DISCARD_PILE_REQUIRED_MESSAGE = "discard pile must not be null";

    private final Deck drawPile;

    private final DiscardPile discardPile;

    public ExplodingKittenCardController(Deck drawPile, DiscardPile discardPile) {
        this.drawPile = Objects.requireNonNull(drawPile, DRAW_PILE_REQUIRED_MESSAGE);
        this.discardPile = Objects.requireNonNull(discardPile, DISCARD_PILE_REQUIRED_MESSAGE);
    }

    public boolean play(Player player, Card explodingKitten) {
        return false;
    }
}
