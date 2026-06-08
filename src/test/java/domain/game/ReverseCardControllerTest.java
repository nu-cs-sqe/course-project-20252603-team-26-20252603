package domain.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ReverseCardControllerTest {
    @Test
    void playReverse_ValidCard_RemovesFromHandAndAddsToDiscard() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.REVERSE));

        DiscardPile discardPile = new DiscardPile();
        ReverseCardController controller = new ReverseCardController(discardPile);

        boolean result = controller.play(player, 0);

        assertTrue(result);
        assertEquals(0, player.getHandSize());
        assertEquals(1, discardPile.size());
        assertEquals(CardType.REVERSE, discardPile.snapshot().get(0).getType());
    }

    @Test
    void playReverse_NonReverseCard_ThrowsException() {
        Player player = new Player("Gojo");
        player.addCard(new Card(CardType.ATTACK));

        DiscardPile discardPile = new DiscardPile();
        ReverseCardController controller = new ReverseCardController(discardPile);

        assertThrows(IllegalArgumentException.class, () -> controller.play(player, 0));
    }

    @Test
    void playReverse_NegativeIndex_ThrowsException() {
        Player player = new Player("Gojo");
        player.addCard(new Card(CardType.REVERSE));

        DiscardPile discardPile = new DiscardPile();
        ReverseCardController controller = new ReverseCardController(discardPile);

        assertThrows(IllegalArgumentException.class, () -> controller.play(player, -1));
    }

    @Test
    void playReverse_IndexEqualsHandSize_ThrowsException() {
        Player player = new Player("Gojo");
        player.addCard(new Card(CardType.REVERSE));

        DiscardPile discardPile = new DiscardPile();
        ReverseCardController controller = new ReverseCardController(discardPile);

        assertThrows(IllegalArgumentException.class, () -> controller.play(player, 1));
    }
}
