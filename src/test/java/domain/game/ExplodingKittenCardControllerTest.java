package domain.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}
