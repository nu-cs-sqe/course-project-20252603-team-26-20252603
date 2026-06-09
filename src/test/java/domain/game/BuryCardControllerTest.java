package domain.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class BuryCardControllerTest {
    @Test
    void play_BuryOnlyCardWithMultiCardDrawPile_MovesTopCardToBottomAndDiscardsCard() {
        Game game = createStartedGame();
        Player currentPlayer = game.getCurrentPlayer();
        clearHand(currentPlayer);
        Card buryCard = new Card(CardType.BURY);
        currentPlayer.addCard(buryCard);
        Card bottomCard = new Card(CardType.BEARD_CAT);
        Card middleCard = new Card(CardType.DEFUSE);
        Card topCard = new Card(CardType.EXPLODING_KITTEN);
        clearDrawPile(game.getDrawPile());
        game.getDrawPile().addCard(bottomCard);
        game.getDrawPile().addCard(middleCard);
        game.getDrawPile().addCard(topCard);
        BuryCardController controller = new BuryCardController();

        controller.play(game, 0);

        assertEquals(0, currentPlayer.getHandSize());
        assertEquals(List.of(buryCard), game.getDiscardPile().snapshot());
        assertEquals(
                List.of(topCard, bottomCard, middleCard),
                game.getDrawPile().snapshot());
        assertEquals(currentPlayer, game.getCurrentPlayer());
    }

    @Test
    void play_BuryAtLastIndexWithOneCardDrawPile_DiscardsBuryAndLeavesDrawPile() {
        Game game = createStartedGame();
        Player currentPlayer = game.getCurrentPlayer();
        clearHand(currentPlayer);
        Card catCard = new Card(CardType.BEARD_CAT);
        Card buryCard = new Card(CardType.BURY);
        currentPlayer.addCard(catCard);
        currentPlayer.addCard(buryCard);
        Card onlyDrawPileCard = new Card(CardType.DEFUSE);
        clearDrawPile(game.getDrawPile());
        game.getDrawPile().addCard(onlyDrawPileCard);
        BuryCardController controller = new BuryCardController();

        controller.play(game, 1);

        assertEquals(List.of(catCard), currentPlayer.getHandSnapshot());
        assertEquals(List.of(buryCard), game.getDiscardPile().snapshot());
        assertEquals(List.of(onlyDrawPileCard), game.getDrawPile().snapshot());
        assertEquals(currentPlayer, game.getCurrentPlayer());
    }

    @Test
    void play_BuryOnlyCardWithEmptyDrawPile_DiscardsBuryAndLeavesDrawPileEmpty() {
        Game game = createStartedGame();
        Player currentPlayer = game.getCurrentPlayer();
        clearHand(currentPlayer);
        Card buryCard = new Card(CardType.BURY);
        currentPlayer.addCard(buryCard);
        clearDrawPile(game.getDrawPile());
        BuryCardController controller = new BuryCardController();

        controller.play(game, 0);

        assertEquals(0, currentPlayer.getHandSize());
        assertEquals(List.of(buryCard), game.getDiscardPile().snapshot());
        assertEquals(List.of(), game.getDrawPile().snapshot());
        assertEquals(currentPlayer, game.getCurrentPlayer());
    }

    @Test
    void play_NonBuryCard_ThrowsExceptionAndLeavesStateUnchanged() {
        Game game = createStartedGame();
        Player currentPlayer = game.getCurrentPlayer();
        clearHand(currentPlayer);
        Card catCard = new Card(CardType.BEARD_CAT);
        currentPlayer.addCard(catCard);
        List<Card> drawPileBeforePlay = game.getDrawPile().snapshot();
        BuryCardController controller = new BuryCardController();

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> controller.play(game, 0));

        assertEquals("selected card is not a Bury card", exception.getMessage());
        assertEquals(List.of(catCard), currentPlayer.getHandSnapshot());
        assertEquals(List.of(), game.getDiscardPile().snapshot());
        assertEquals(drawPileBeforePlay, game.getDrawPile().snapshot());
        assertEquals(currentPlayer, game.getCurrentPlayer());
    }

    @Test
    void play_NegativeCardIndex_ThrowsExceptionAndLeavesStateUnchanged() {
        Game game = createStartedGame();
        Player currentPlayer = game.getCurrentPlayer();
        clearHand(currentPlayer);
        Card buryCard = new Card(CardType.BURY);
        currentPlayer.addCard(buryCard);
        List<Card> drawPileBeforePlay = game.getDrawPile().snapshot();
        BuryCardController controller = new BuryCardController();

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> controller.play(game, -1));

        assertEquals("cardIndex is out of bounds", exception.getMessage());
        assertEquals(List.of(buryCard), currentPlayer.getHandSnapshot());
        assertEquals(List.of(), game.getDiscardPile().snapshot());
        assertEquals(drawPileBeforePlay, game.getDrawPile().snapshot());
        assertEquals(currentPlayer, game.getCurrentPlayer());
    }

    @Test
    void play_IndexEqualsHandSize_ThrowsExceptionAndLeavesStateUnchanged() {
        Game game = createStartedGame();
        Player currentPlayer = game.getCurrentPlayer();
        clearHand(currentPlayer);
        Card buryCard = new Card(CardType.BURY);
        currentPlayer.addCard(buryCard);
        List<Card> drawPileBeforePlay = game.getDrawPile().snapshot();
        BuryCardController controller = new BuryCardController();

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> controller.play(game, currentPlayer.getHandSize()));

        assertEquals("cardIndex is out of bounds", exception.getMessage());
        assertEquals(List.of(buryCard), currentPlayer.getHandSnapshot());
        assertEquals(List.of(), game.getDiscardPile().snapshot());
        assertEquals(drawPileBeforePlay, game.getDrawPile().snapshot());
        assertEquals(currentPlayer, game.getCurrentPlayer());
    }

    private Game createStartedGame() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(CardType.EXPLODING_KITTEN));
        cards.add(new Card(CardType.DEFUSE));
        cards.add(new Card(CardType.DEFUSE));
        for (int count = 0; count < 10; count++) {
            cards.add(new Card(CardType.BEARD_CAT));
        }
        Game game = new Game(new Deck(cards));
        game.setupGame(List.of("Avery", "Jordan"));
        return game;
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
