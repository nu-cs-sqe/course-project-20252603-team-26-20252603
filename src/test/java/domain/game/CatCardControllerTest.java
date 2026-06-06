package domain.game;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void playCatCards_DifferentCatCards_ThrowsException() {
        Player currentPlayer = new Player("Sophie");
        Card beardCat = new Card(CardType.BEARD_CAT);
        Card tacoCat = new Card(CardType.TACOCAT);
        currentPlayer.addCard(beardCat);
        currentPlayer.addCard(tacoCat);

        Player targetPlayer = new Player("Target");
        Card targetCard = new Card(CardType.DEFUSE);
        targetPlayer.addCard(targetCard);

        DiscardPile discardPile = new DiscardPile();
        CatCardController controller =
                new CatCardController(discardPile, new FixedRandom(0));

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> controller.play(currentPlayer, targetPlayer, 0, 1));

        assertEquals("selected cards must be matching cat cards", exception.getMessage());
        assertEquals(List.of(beardCat, tacoCat), currentPlayer.getHandSnapshot());
        assertEquals(List.of(targetCard), targetPlayer.getHandSnapshot());
        assertEquals(List.of(), discardPile.snapshot());
    }

    @Test
    void playCatCards_MatchingNonCatCards_ThrowsException() {
        Player currentPlayer = new Player("Sophie");
        Card firstDefuse = new Card(CardType.DEFUSE);
        Card secondDefuse = new Card(CardType.DEFUSE);
        currentPlayer.addCard(firstDefuse);
        currentPlayer.addCard(secondDefuse);

        Player targetPlayer = new Player("Target");
        Card targetCard = new Card(CardType.ATTACK);
        targetPlayer.addCard(targetCard);

        DiscardPile discardPile = new DiscardPile();
        CatCardController controller =
                new CatCardController(discardPile, new FixedRandom(0));

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> controller.play(currentPlayer, targetPlayer, 0, 1));

        assertEquals("selected cards must be matching cat cards", exception.getMessage());
        assertEquals(List.of(firstDefuse, secondDefuse), currentPlayer.getHandSnapshot());
        assertEquals(List.of(targetCard), targetPlayer.getHandSnapshot());
        assertEquals(List.of(), discardPile.snapshot());
    }

    @Test
    void playCatCards_SameIndexTwice_ThrowsException() {
        Player currentPlayer = new Player("Sophie");
        Card firstCat = new Card(CardType.BEARD_CAT);
        Card secondCat = new Card(CardType.BEARD_CAT);
        currentPlayer.addCard(firstCat);
        currentPlayer.addCard(secondCat);

        Player targetPlayer = new Player("Target");
        Card targetCard = new Card(CardType.DEFUSE);
        targetPlayer.addCard(targetCard);

        DiscardPile discardPile = new DiscardPile();
        CatCardController controller =
                new CatCardController(discardPile, new FixedRandom(0));

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> controller.play(currentPlayer, targetPlayer, 0, 0));

        assertEquals("selected cards must be matching cat cards", exception.getMessage());
        assertEquals(List.of(firstCat, secondCat), currentPlayer.getHandSnapshot());
        assertEquals(List.of(targetCard), targetPlayer.getHandSnapshot());
        assertEquals(List.of(), discardPile.snapshot());
    }

    @Test
    void playCatCards_NegativeFirstIndex_ThrowsException() {
        Player currentPlayer = new Player("Sophie");
        Card firstCat = new Card(CardType.BEARD_CAT);
        Card secondCat = new Card(CardType.BEARD_CAT);
        currentPlayer.addCard(firstCat);
        currentPlayer.addCard(secondCat);

        Player targetPlayer = new Player("Target");
        Card targetCard = new Card(CardType.DEFUSE);
        targetPlayer.addCard(targetCard);

        DiscardPile discardPile = new DiscardPile();
        CatCardController controller =
                new CatCardController(discardPile, new FixedRandom(0));

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> controller.play(currentPlayer, targetPlayer, -1, 1));

        assertEquals("cardIndex is out of bounds", exception.getMessage());
        assertEquals(List.of(firstCat, secondCat), currentPlayer.getHandSnapshot());
        assertEquals(List.of(targetCard), targetPlayer.getHandSnapshot());
        assertEquals(List.of(), discardPile.snapshot());
    }

    @Test
    void playCatCards_SecondIndexEqualsHandSize_ThrowsException() {
        Player currentPlayer = new Player("Sophie");
        Card firstCat = new Card(CardType.BEARD_CAT);
        Card secondCat = new Card(CardType.BEARD_CAT);
        currentPlayer.addCard(firstCat);
        currentPlayer.addCard(secondCat);

        Player targetPlayer = new Player("Target");
        Card targetCard = new Card(CardType.DEFUSE);
        targetPlayer.addCard(targetCard);

        DiscardPile discardPile = new DiscardPile();
        CatCardController controller =
                new CatCardController(discardPile, new FixedRandom(0));

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> controller.play(
                                currentPlayer,
                                targetPlayer,
                                0,
                                currentPlayer.getHandSize()));

        assertEquals("cardIndex is out of bounds", exception.getMessage());
        assertEquals(List.of(firstCat, secondCat), currentPlayer.getHandSnapshot());
        assertEquals(List.of(targetCard), targetPlayer.getHandSnapshot());
        assertEquals(List.of(), discardPile.snapshot());
    }

    @Test
    void playCatCards_TargetPlayerHasEmptyHand_ThrowsException() {
        Player currentPlayer = new Player("Sophie");
        Card firstCat = new Card(CardType.BEARD_CAT);
        Card secondCat = new Card(CardType.BEARD_CAT);
        currentPlayer.addCard(firstCat);
        currentPlayer.addCard(secondCat);

        Player targetPlayer = new Player("Target");

        DiscardPile discardPile = new DiscardPile();
        CatCardController controller =
                new CatCardController(discardPile, new FixedRandom(0));

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> controller.play(currentPlayer, targetPlayer, 0, 1));

        assertEquals("target player has no cards", exception.getMessage());
        assertEquals(List.of(firstCat, secondCat), currentPlayer.getHandSnapshot());
        assertEquals(0, targetPlayer.getHandSize());
        assertEquals(List.of(), discardPile.snapshot());
    }

}

