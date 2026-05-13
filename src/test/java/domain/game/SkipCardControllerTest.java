package domain.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SkipCardControllerTest {
    @Test
    void playSkip_ReturnsTrue() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.SKIP));

        DiscardPile discardPile = new DiscardPile();
        SkipController controller = new SkipController(discardPile);

        boolean skipped = controller.play(player, 0);

        assertTrue(skipped);
    }

    @Test
    void playSkip_RemovesCardFromHand() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.SKIP));

        DiscardPile discardPile = new DiscardPile();
        SkipController controller = new SkipController(discardPile);

        controller.play(player, 0);

        assertEquals(0, player.getHandSize());
    }

    @Test
    void playSkip_AddsCardToDiscardPile() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.SKIP));

        DiscardPile discardPile = new DiscardPile();
        SkipController controller = new SkipController(discardPile);

        controller.play(player, 0);

        assertEquals(1, discardPile.size());
        assertEquals(CardType.SKIP, discardPile.snapshot().get(0).getType());
    }
}
