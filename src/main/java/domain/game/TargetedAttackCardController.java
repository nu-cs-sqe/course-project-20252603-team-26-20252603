package domain.game;

public final class TargetedAttackCardController {
    private final DiscardPile discardPile;
    private static final String INVALID_INDEX_MESSAGE = "cardIndex is out of bounds";


    public TargetedAttackCardController(DiscardPile discardPile) {
        this.discardPile = discardPile;
    }

    public void play(Player player, int cardIndex) {
        if (cardIndex < 0 || cardIndex >= player.getHandSize()) {
            throw new IllegalArgumentException(INVALID_INDEX_MESSAGE);
        }
    }
    }
