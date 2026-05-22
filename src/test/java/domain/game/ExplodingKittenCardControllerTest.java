package domain.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class ExplodingKittenCardControllerTest {
    @Test
    void constructor_NullDrawPile_ThrowsException() {
        NullPointerException exception =
                assertThrows(
                        NullPointerException.class,
                        () -> new ExplodingKittenCardController(null, new DiscardPile()));

        assertEquals("draw pile must not be null", exception.getMessage());
    }

    @Test
    void constructor_NullDiscardPile_ThrowsException() {
        Deck drawPile = new Deck(List.of());

        NullPointerException exception =
                assertThrows(
                        NullPointerException.class,
                        () -> new ExplodingKittenCardController(drawPile, null));

        assertEquals("discard pile must not be null", exception.getMessage());
    }

    @Test
    void play_NoDefuseEmptyHand_ReturnsFalseWithoutChangingPiles() {
        Player player = new Player("Alice");
        Card drawPileCard = new Card(CardType.BEARD_CAT);
        Card discardedCard = new Card(CardType.SKIP);
        Deck drawPile = new Deck(List.of(drawPileCard));
        DiscardPile discardPile = new DiscardPile();
        discardPile.add(discardedCard);
        ExplodingKittenCardController controller =
                new ExplodingKittenCardController(drawPile, discardPile);

        boolean defused = controller.play(player, new Card(CardType.EXPLODING_KITTEN));

        assertFalse(defused);
        assertEquals(0, player.getHandSize());
        assertEquals(List.of(drawPileCard), drawPile.snapshot());
        assertEquals(List.of(discardedCard), discardPile.snapshot());
    }

    @Test
    void play_NoDefuseNonEmptyHand_ReturnsFalseWithoutChangingHandOrPiles() {
        Player player = new Player("Alice");
        Card handCard = new Card(CardType.BEARD_CAT);
        Card drawPileCard = new Card(CardType.TACOCAT);
        Card discardedCard = new Card(CardType.SKIP);
        player.addCard(handCard);
        Deck drawPile = new Deck(List.of(drawPileCard));
        DiscardPile discardPile = new DiscardPile();
        discardPile.add(discardedCard);
        ExplodingKittenCardController controller =
                new ExplodingKittenCardController(drawPile, discardPile);

        boolean defused = controller.play(player, new Card(CardType.EXPLODING_KITTEN));

        assertFalse(defused);
        assertEquals(List.of(handCard), player.getHandSnapshot());
        assertEquals(List.of(drawPileCard), drawPile.snapshot());
        assertEquals(List.of(discardedCard), discardPile.snapshot());
    }

    @Test
    void play_OneDefuseWithEmptyDrawPileAndEmptyDiscardPile_DefusesKitten() {
        Player player = new Player("Alice");
        Card defuse = new Card(CardType.DEFUSE);
        Card explodingKitten = new Card(CardType.EXPLODING_KITTEN);
        player.addCard(defuse);
        Deck drawPile = new Deck(List.of());
        DiscardPile discardPile = new DiscardPile();
        ExplodingKittenCardController controller =
                new ExplodingKittenCardController(drawPile, discardPile);

        boolean defused = controller.play(player, explodingKitten);

        assertTrue(defused);
        assertEquals(0, player.getHandSize());
        assertEquals(List.of(defuse), discardPile.snapshot());
        assertEquals(List.of(explodingKitten), drawPile.snapshot());
    }

    @Test
    void play_AllButOneCardIsDefuse_RemovesOneDefuseAndKeepsOtherCards() {
        Player player = new Player("Alice");
        Card firstDefuse = new Card(CardType.DEFUSE);
        Card secondDefuse = new Card(CardType.DEFUSE);
        Card catCard = new Card(CardType.BEARD_CAT);
        Card drawPileCard = new Card(CardType.TACOCAT);
        Card discardedCard = new Card(CardType.SKIP);
        Card explodingKitten = new Card(CardType.EXPLODING_KITTEN);
        player.addCard(firstDefuse);
        player.addCard(secondDefuse);
        player.addCard(catCard);
        Deck drawPile = new Deck(List.of(drawPileCard));
        DiscardPile discardPile = new DiscardPile();
        discardPile.add(discardedCard);
        ExplodingKittenCardController controller =
                new ExplodingKittenCardController(drawPile, discardPile);

        boolean defused = controller.play(player, explodingKitten);

        assertTrue(defused);
        assertEquals(List.of(secondDefuse, catCard), player.getHandSnapshot());
        assertEquals(List.of(discardedCard, firstDefuse), discardPile.snapshot());
        assertEquals(2, drawPile.snapshot().size());
        assertTrue(drawPile.snapshot().contains(drawPileCard));
        assertTrue(drawPile.snapshot().contains(explodingKitten));
    }

    @Test
    void play_AllCardsAreDefuse_RemovesOnlyOneDefuse() {
        Player player = new Player("Alice");
        Card firstDefuse = new Card(CardType.DEFUSE);
        Card secondDefuse = new Card(CardType.DEFUSE);
        Card firstDrawPileCard = new Card(CardType.TACOCAT);
        Card secondDrawPileCard = new Card(CardType.RAINBOW_RALPHING_CAT);
        Card explodingKitten = new Card(CardType.EXPLODING_KITTEN);
        player.addCard(firstDefuse);
        player.addCard(secondDefuse);
        Deck drawPile = new Deck(List.of(firstDrawPileCard, secondDrawPileCard));
        DiscardPile discardPile = new DiscardPile();
        ExplodingKittenCardController controller =
                new ExplodingKittenCardController(drawPile, discardPile);

        boolean defused = controller.play(player, explodingKitten);

        assertTrue(defused);
        assertEquals(List.of(secondDefuse), player.getHandSnapshot());
        assertEquals(List.of(firstDefuse), discardPile.snapshot());
        assertEquals(3, drawPile.snapshot().size());
        assertTrue(drawPile.snapshot().contains(firstDrawPileCard));
        assertTrue(drawPile.snapshot().contains(secondDrawPileCard));
        assertTrue(drawPile.snapshot().contains(explodingKitten));
    }

    @Test
    void play_NullPlayer_ThrowsException() {
        Card drawPileCard = new Card(CardType.TACOCAT);
        Card discardedCard = new Card(CardType.SKIP);
        Deck drawPile = new Deck(List.of(drawPileCard));
        DiscardPile discardPile = new DiscardPile();
        discardPile.add(discardedCard);
        ExplodingKittenCardController controller =
                new ExplodingKittenCardController(drawPile, discardPile);

        NullPointerException exception =
                assertThrows(
                        NullPointerException.class,
                        () -> controller.play(null, new Card(CardType.EXPLODING_KITTEN)));

        assertEquals("player must not be null", exception.getMessage());
        assertEquals(List.of(drawPileCard), drawPile.snapshot());
        assertEquals(List.of(discardedCard), discardPile.snapshot());
    }
}
