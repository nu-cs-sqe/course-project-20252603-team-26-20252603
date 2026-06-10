package domain.game;

import org.junit.jupiter.api.Test;

import java.util.List;

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
}
