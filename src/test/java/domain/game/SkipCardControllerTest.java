package domain.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SkipCardControllerTest {
    @Test
    void playSkip_ReturnsTrue() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.SKIP));

        DiscardPile discardPile = new DiscardPile();
        SkipCardController controller = new SkipCardController(discardPile);

        boolean skipped = controller.play(player, 0);

        assertTrue(skipped);
    }

    @Test
    void playSkip_RemovesCardFromHand() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.SKIP));

        DiscardPile discardPile = new DiscardPile();
        SkipCardController controller = new SkipCardController(discardPile);

        controller.play(player, 0);

        assertEquals(0, player.getHandSize());
    }

    @Test
    void playSkip_AddsCardToDiscardPile() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.SKIP));

        DiscardPile discardPile = new DiscardPile();
        SkipCardController controller = new SkipCardController(discardPile);

        controller.play(player, 0);

        assertEquals(1, discardPile.size());
        assertEquals(CardType.SKIP, discardPile.snapshot().get(0).getType());
    }

    @Test
    void playSkip_SelectedCardIsDefuse_ThrowsException() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.DEFUSE));

        DiscardPile discardPile = new DiscardPile();
        SkipCardController controller = new SkipCardController(discardPile);

        assertThrows(IllegalArgumentException.class,
                () -> controller.play(player, 0));
    }

    @Test
    void playSkip_NegativeIndex_ThrowsException() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.SKIP));

        DiscardPile discardPile = new DiscardPile();
        SkipCardController controller = new SkipCardController(discardPile);

        assertThrows(IndexOutOfBoundsException.class,
                () -> controller.play(player, -1));
    }

    @Test
    void playSkip_IndexEqualsHandSize_ThrowsException() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.SKIP));

        DiscardPile discardPile = new DiscardPile();
        SkipCardController controller = new SkipCardController(discardPile);

        assertThrows(IndexOutOfBoundsException.class,
                () -> controller.play(player, 1));
    }

    @Test
    void playSkip_NullPlayer_ThrowsException() {
        DiscardPile discardPile = new DiscardPile();
        SkipCardController controller = new SkipCardController(discardPile);

        assertThrows(NullPointerException.class,
                () -> controller.play(null, 0));
    }

}
