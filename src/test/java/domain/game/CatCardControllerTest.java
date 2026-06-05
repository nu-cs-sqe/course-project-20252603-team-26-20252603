package domain.game;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CatCardControllerTest {
    @Test
    void playCatCards_MatchingBeardCats_StealsRandomCard() {
        Player currentPlayer = new Player("Sophie");
        Card firstCat = new Card(CardType.BEARD_CAT);
        Card secondCat = new Card(CardType.BEARD_CAT);
        currentPlayer.addCard(firstCat);
        currentPlayer.addCard(secondCat);

        Player targetPlayer = new Player("Target");
        Card firstTargetCard = new Card(CardType.DEFUSE);
        Card secondTargetCard = new Card(CardType.SKIP);
        targetPlayer.addCard(firstTargetCard);
        targetPlayer.addCard(secondTargetCard);

        DiscardPile discardPile = new DiscardPile();
        CatCardController controller =
                new CatCardController(discardPile, new FixedRandom(1));

        Card stolenCard = controller.play(currentPlayer, targetPlayer, 0, 1);

        assertEquals(secondTargetCard, stolenCard);
        assertEquals(List.of(secondTargetCard), currentPlayer.getHandSnapshot());
        assertEquals(List.of(firstTargetCard), targetPlayer.getHandSnapshot());
        assertEquals(List.of(firstCat, secondCat), discardPile.snapshot());
    }

    private static final class FixedRandom extends Random {
        private final int value;

        FixedRandom(int value) {
            this.value = value;
        }

        @Override
        public int nextInt(int bound) {
            return value;
        }
    }

    @Test
    void playCatCards_ReversedIndexes_RemovesBothCatCards() {
        Player currentPlayer = new Player("Sophie");
        Card firstCat = new Card(CardType.TACOCAT);
        Card middleCard = new Card(CardType.DEFUSE);
        Card secondCat = new Card(CardType.TACOCAT);
        currentPlayer.addCard(firstCat);
        currentPlayer.addCard(middleCard);
        currentPlayer.addCard(secondCat);

        Player targetPlayer = new Player("Target");
        Card stolenCard = new Card(CardType.SKIP);
        targetPlayer.addCard(stolenCard);

        DiscardPile discardPile = new DiscardPile();
        CatCardController controller =
                new CatCardController(discardPile, new FixedRandom(0));

        controller.play(currentPlayer, targetPlayer, 2, 0);

        assertEquals(List.of(middleCard, stolenCard), currentPlayer.getHandSnapshot());
        assertEquals(0, targetPlayer.getHandSize());
        assertEquals(List.of(secondCat, firstCat), discardPile.snapshot());
    }

}

