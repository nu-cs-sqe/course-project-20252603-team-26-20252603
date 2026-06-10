package domain.game;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DrawFromBottomControllerTest {

    @Test
    void play_NegativeIndex_ThrowsIllegalArgumentException() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.DRAW_FROM_BOTTOM));
        Deck deck = new Deck(List.of(new Card(CardType.SKIP)));
        DiscardPile discardPile = new DiscardPile();
        DrawFromBottomCardController controller = new DrawFromBottomCardController(deck, discardPile);

        assertThrows(IllegalArgumentException.class, () -> controller.play(player, -1));
    }

    @Test
    void play_IndexEqualsHandSize_ThrowsIllegalArgumentException() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.DRAW_FROM_BOTTOM));
        Deck deck = new Deck(List.of(new Card(CardType.SKIP)));
        DiscardPile discardPile = new DiscardPile();
        DrawFromBottomCardController controller = new DrawFromBottomCardController(deck, discardPile);

        assertThrows(IllegalArgumentException.class, () -> controller.play(player, player.getHandSize()));
    }

    @Test
    void play_NotDrawFromBottomCard_ThrowsIllegalArgumentException() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.SKIP));
        Deck deck = new Deck(List.of(new Card(CardType.SKIP)));
        DiscardPile discardPile = new DiscardPile();
        DrawFromBottomCardController controller = new DrawFromBottomCardController(deck, discardPile);

        assertThrows(IllegalArgumentException.class, () -> controller.play(player, 0));
    }

    @Test
    void play_ValidCard_DrawsBottomCardIntoPlayerHand() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.DRAW_FROM_BOTTOM));
        Deck deck = new Deck(List.of(new Card(CardType.SKIP), new Card(CardType.ATTACK)));
        DiscardPile discardPile = new DiscardPile();
        DrawFromBottomCardController controller = new DrawFromBottomCardController(deck, discardPile);

        Card drawn = controller.play(player, 0);

        assertEquals(CardType.SKIP, drawn.getType());
        assertEquals(1, player.getHandSize());
        assertEquals(CardType.SKIP, player.getHandSnapshot().get(0).getType());
        assertEquals(1, discardPile.size());
    }

    @Test
    void play_ValidCard_ExplodingKittenDrawn_ReturnsExplodingKitten() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.DRAW_FROM_BOTTOM));
        Deck deck = new Deck(List.of(new Card(CardType.EXPLODING_KITTEN), new Card(CardType.ATTACK)));
        DiscardPile discardPile = new DiscardPile();
        DrawFromBottomCardController controller = new DrawFromBottomCardController(deck, discardPile);

        Card drawn = controller.play(player, 0);

        assertEquals(CardType.EXPLODING_KITTEN, drawn.getType());
        assertEquals(1, player.getHandSize());
        assertEquals(CardType.EXPLODING_KITTEN, player.getHandSnapshot().get(0).getType());
    }
}
