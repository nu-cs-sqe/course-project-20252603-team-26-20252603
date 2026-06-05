package domain.game;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Random;

public final class CatCardController {
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Controller must add played Cat Cards to the injected discard pile.")
    private final DiscardPile discardPile;

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
        Card firstCard = currentPlayer.getHandSnapshot().get(firstCardIndex);
        Card secondCard = currentPlayer.getHandSnapshot().get(secondCardIndex);

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
}