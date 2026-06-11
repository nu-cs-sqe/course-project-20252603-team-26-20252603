package domain.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SwapTopAndBottomControllerTest {
    @Test
    void playSwapTopAndBottom_ValidCard_DiscardsCardAndSwapsDrawPile() {
        Game game = new Game(createDeckForTwoPlayers());
        game.setupGame(List.of("Sophie", "Jordan"));

        Player currentPlayer = game.getCurrentPlayer();
        clearHand(currentPlayer);
        Card swapCard = new Card(CardType.SWAP_TOP_AND_BOTTOM);
        currentPlayer.addCard(swapCard);

        Card bottomCard = new Card(CardType.DEFUSE);
        Card middleCard = new Card(CardType.BEARD_CAT);
        Card topCard = new Card(CardType.EXPLODING_KITTEN);
        clearDrawPile(game.getDrawPile());
        game.getDrawPile().addCard(bottomCard);
        game.getDrawPile().addCard(middleCard);
        game.getDrawPile().addCard(topCard);

        SwapTopAndBottomController controller = new SwapTopAndBottomController();

        controller.play(game, 0);

        assertEquals(0, currentPlayer.getHandSize());
        assertEquals(List.of(swapCard), game.getDiscardPile().snapshot());
        assertEquals(List.of(topCard, middleCard, bottomCard),
                game.getDrawPile().snapshot());
    }

    private Deck createDeckForTwoPlayers() {
        return new Deck(List.of(
                new Card(CardType.EXPLODING_KITTEN),
                new Card(CardType.DEFUSE),
                new Card(CardType.DEFUSE),
                new Card(CardType.BEARD_CAT),
                new Card(CardType.TACOCAT),
                new Card(CardType.HAIRY_POTATO_CAT),
                new Card(CardType.RAINBOW_RALPHING_CAT),
                new Card(CardType.BEARD_CAT),
                new Card(CardType.TACOCAT),
                new Card(CardType.HAIRY_POTATO_CAT),
                new Card(CardType.RAINBOW_RALPHING_CAT),
                new Card(CardType.BEARD_CAT),
                new Card(CardType.TACOCAT)));
    }

    @Test
    void playSwapTopAndBottom_SelectedCardIsDefuse_ThrowsException() {
        Game game = new Game(createDeckForTwoPlayers());
        game.setupGame(List.of("Sophie", "Jordan"));

        Player currentPlayer = game.getCurrentPlayer();
        clearHand(currentPlayer);
        Card defuse = new Card(CardType.DEFUSE);
        currentPlayer.addCard(defuse);

        Card bottomCard = new Card(CardType.BEARD_CAT);
        Card topCard = new Card(CardType.SKIP);
        clearDrawPile(game.getDrawPile());
        game.getDrawPile().addCard(bottomCard);
        game.getDrawPile().addCard(topCard);
        List<Card> drawPileBeforePlay = game.getDrawPile().snapshot();

        SwapTopAndBottomController controller = new SwapTopAndBottomController();

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> controller.play(game, 0));

        assertEquals("selected card is not a swap top and bottom card",
                exception.getMessage());
        assertEquals(List.of(defuse), currentPlayer.getHandSnapshot());
        assertEquals(List.of(), game.getDiscardPile().snapshot());
        assertEquals(drawPileBeforePlay, game.getDrawPile().snapshot());
    }

    @Test
    void playSwapTopAndBottom_IndexEqualsHandSize_ThrowsException() {
        Game game = new Game(createDeckForTwoPlayers());
        game.setupGame(List.of("Sophie", "Jordan"));
        Player currentPlayer = game.getCurrentPlayer();
        clearHand(currentPlayer);
        currentPlayer.addCard(new Card(CardType.SWAP_TOP_AND_BOTTOM));

        SwapTopAndBottomController controller = new SwapTopAndBottomController();

        assertThrows(IllegalArgumentException.class,
                () -> controller.play(game, currentPlayer.getHandSize()));
    }

    private void clearHand(Player player) {
        while (player.getHandSize() > 0) {
            player.removeCard(0);
        }
    }

    private void clearDrawPile(Deck drawPile) {
        while (drawPile.size() > 0) {
            drawPile.draw();
        }
    }
}