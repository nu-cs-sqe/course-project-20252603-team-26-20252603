package domain.game;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Random;

public final class CatCardController {
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Controller must add played Cat Cards to the injected discard pile.")
    private final DiscardPile discardPile;

    private static final String CARD_MUST_BE_MATCHING_CAT_PAIR =
            "selected cards must be matching cat cards";

    private static final String INVALID_INDEX_MESSAGE = "cardIndex is out of bounds";

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Controller uses injected random for deterministic tests.")
    private final Random random;

    public CatCardController(DiscardPile discardPile, Random random) {
        this.discardPile = discardPile;
        this.random = random;
    }

    public Card play(
            Player currentPlayer,
            Player targetPlayer,
            int firstCardIndex,
            int secondCardIndex) {
        validateIndex(currentPlayer, firstCardIndex);
        if (firstCardIndex == secondCardIndex) {
            throw new IllegalArgumentException(CARD_MUST_BE_MATCHING_CAT_PAIR);
        }
        Card firstCard = currentPlayer.getHandSnapshot().get(firstCardIndex);
        Card secondCard = currentPlayer.getHandSnapshot().get(secondCardIndex);

        if (!isMatchingCatPair(firstCard, secondCard)) {
            throw new IllegalArgumentException(CARD_MUST_BE_MATCHING_CAT_PAIR);
        }

        removePlayedCards(currentPlayer, firstCardIndex, secondCardIndex);

        discardPile.add(firstCard);
        discardPile.add(secondCard);

        int stolenCardIndex = random.nextInt(targetPlayer.getHandSize());
        Card stolenCard = targetPlayer.removeCardAt(stolenCardIndex);
        currentPlayer.addCard(stolenCard);

        return stolenCard;
    }

    private void removePlayedCards(Player player, int firstCardIndex, int secondCardIndex) {
        int largerIndex = Math.max(firstCardIndex, secondCardIndex);
        int smallerIndex = Math.min(firstCardIndex, secondCardIndex);

        player.removeCard(largerIndex);
        player.removeCard(smallerIndex);
    }

    private boolean isMatchingCatPair(Card firstCard, Card secondCard) {
        return isCatCard(firstCard.getType())
                && firstCard.getType() == secondCard.getType();
    }

    private boolean isCatCard(CardType type) {
        return type == CardType.BEARD_CAT
                || type == CardType.HAIRY_POTATO_CAT
                || type == CardType.TACOCAT
                || type == CardType.RAINBOW_RALPHING_CAT;
    }

    private void validateIndex(Player player, int cardIndex) {
        if (cardIndex < 0 || cardIndex >= player.getHandSize()) {
            throw new IllegalArgumentException(INVALID_INDEX_MESSAGE);
        }
    }
}