package domain.game;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import ui.GameView;

public class GameControllerTest {
    @Test
    void startGame_EmptyPlayerList_DisplaysError() {
        Game mockModel = EasyMock.createMock(Game.class); //Game(deck);
        GameView mockView = EasyMock.createMock(GameView.class);

        //we fake passing in the empty list to the fake model resulting in this error
        mockModel.setupGame(List.of());
        expectLastCall().andThrow(new IllegalArgumentException("player count must be between 2 and 4"));
        replay(mockModel);
        // tell the mock view to expect displayError called once with anyt string
        mockView.displayError(anyString());
        expectLastCall().once();
        replay(mockView);

        GameController controller = new GameController(mockModel, mockView);

        // Act
        controller.startGame(List.of());

        // Assert
        verify(mockView); // verifies displayError was called, and displayGameReady was NOT
        verify(mockModel);
    }

    @Test
    void startGame_WithOnePlayer_DisplaysError() {
        //Deck deck = EasyMock.createMock(Deck.class);
        Game mockModel = EasyMock.createMock(Game.class);
        GameView mockView = EasyMock.createMock(GameView.class);


        //we fake passing in the empty list to the fake model resulting in this error
        mockModel.setupGame(List.of("cat1"));
        expectLastCall().andThrow(new IllegalArgumentException("player count must be between 2 and 4"));
        replay(mockModel);
        // tell the mock view to expect displayError called once with anyt string
        mockView.displayError(anyString());
        expectLastCall().once();
        replay(mockView);

        GameController controller = new GameController(mockModel, mockView);

        // Act
        controller.startGame(List.of("cat1"));

        // Assert
        verify(mockView); // verifies displayError was called, and displayGameReady was NOT
        verify(mockModel);
    }

    @Test
    void startGame_WithTwoPLayers_DisplaysStartGame() {
        // Deck modelDeck = EasyMock.createMock(Deck.class);
         Game mockModel = EasyMock.createMock(Game.class);
         GameView mockView = EasyMock.createMock(GameView.class);
         List<String> twoPlayers = List.of("Cat1", "Cat2");


        mockModel.setupGame(twoPlayers);
        expectLastCall().once();
        replay(mockModel);

        mockView.displayGameReady();
        expectLastCall().once();
        replay(mockView);

        GameController controller = new GameController(mockModel, mockView);

        // Act
        controller.startGame(twoPlayers);

        // Assert
        verify(mockView); // verifies displayError was called, and displayGameReady was NOT
        verify(mockModel);
    }

    @Test
    void startGame_WithThreePLayers_DisplaysStartGame() {
        // Deck modelDeck = EasyMock.createMock(Deck.class);
        Game mockModel = EasyMock.createMock(Game.class);
        GameView mockView = EasyMock.createMock(GameView.class);
        List<String> threePlayers = List.of("Cat1", "Cat2", "Cat3");


        mockModel.setupGame(threePlayers);
        expectLastCall().once();
        replay(mockModel);

        mockView.displayGameReady();
        expectLastCall().once();
        replay(mockView);

        GameController controller = new GameController(mockModel, mockView);

        // Act
        controller.startGame(threePlayers);

        // Assert
        verify(mockView); // verifies displayError was called, and displayGameReady was NOT
        verify(mockModel);
    }


    @Test
    void startGame_WithThreePLayers_OneInvalidPLayer_DisplaysError() {
        List<Card> threePlayerDeck = new ArrayList<>();
        threePlayerDeck.add(new Card(CardType.EXPLODING_KITTEN));
        threePlayerDeck.add(new Card(CardType.EXPLODING_KITTEN));
        threePlayerDeck.add(new Card(CardType.DEFUSE));
        threePlayerDeck.add(new Card(CardType.DEFUSE));
        threePlayerDeck.add(new Card(CardType.DEFUSE));
        for (int i = 0; i < 15; i++) {
            threePlayerDeck.add(new Card(CardType.PLACEHOLDER_CARD));
            }
            // needs real Game and real Deck to actually validate the player name
            Deck deck = new Deck(threePlayerDeck);
            Game model = new Game(deck);
            GameView mockView = EasyMock.createMock(GameView.class);
            List<String> multiPlayer = List.of("cat1", "cat2", "");

            // tell the mock view to expect displayError called once with anyt string
            mockView.displayError(anyString());
            expectLastCall().once();
            replay(mockView);

            GameController controller = new GameController(model, mockView);

            // Act
            controller.startGame(multiPlayer);

            // Assert
            verify(mockView); // verifies displayError was called, and displayGameReady was NOT
        }

    @Test
    void startGame_WithMaxPLayers_DisplaysStartGame() {
        // Deck modelDeck = EasyMock.createMock(Deck.class);
        Game mockModel = EasyMock.createMock(Game.class);
        GameView mockView = EasyMock.createMock(GameView.class);
        List<String> threePlayers = List.of("Cat1", "Cat2", "Cat3", "Cat4", "cat5");


        mockModel.setupGame(threePlayers);
        expectLastCall().once();
        replay(mockModel);

        mockView.displayGameReady();
        expectLastCall().once();
        replay(mockView);

        GameController controller = new GameController(mockModel, mockView);

        // Act
        controller.startGame(threePlayers);

        // Assert
        verify(mockView); // verifies displayError was called, and displayGameReady was NOT
        verify(mockModel);
    }

    @Test
    void startTurn_DisplaysCurrentPlayerHand() {
        Game mockModel = EasyMock.createMock(Game.class);
        GameView mockView = EasyMock.createMock(GameView.class);
        Player player = new Player("Sophie");
        Card skip = new Card(CardType.SKIP);
        Card beardCat = new Card(CardType.BEARD_CAT);
        player.addCard(skip);
        player.addCard(beardCat);

        expect(mockModel.getCurrentPlayer()).andReturn(player).once();
        replay(mockModel);
        mockView.displayHand("Sophie", List.of(skip, beardCat));
        expectLastCall().once();
        replay(mockView);
        GameController controller = new GameController(mockModel, mockView);

        controller.startTurn();

        verify(mockModel);
        verify(mockView);
    }

    @Test
    void completeTurn_NoCardsPlayed_DisplaysHandThenDrawsAndAdvances() {
        Game game = new Game(createDeckForPlayers(2));
        GameView mockView = EasyMock.createStrictMock(GameView.class);
        game.setupGame(List.of("Sophie", "Jordan"));
        Player currentPlayer = game.getCurrentPlayer();
        List<Card> startingHand = currentPlayer.getHandSnapshot();
        clearDrawPile(game.getDrawPile());
        Card drawnCard = new Card(CardType.PLACEHOLDER_CARD);
        game.getDrawPile().addCard(drawnCard);

        mockView.displayHand("Sophie", startingHand);
        expectLastCall().once();
        mockView.displayCardDrawn(drawnCard);
        expectLastCall().once();
        replay(mockView);
        GameController controller = new GameController(game, mockView);

        controller.completeTurn(List.of());

        assertEquals(7, currentPlayer.getHandSize());
        assertEquals("Jordan", game.getCurrentPlayer().getName());
        verify(mockView);
    }

    @Test
    void completeTurn_SkipPlayed_DisplaysHandThenEndsWithoutDrawing() {
        Game game = new Game(createDeckForPlayers(2));
        GameView mockView = EasyMock.createStrictMock(GameView.class);
        game.setupGame(List.of("Sophie", "Jordan"));
        Player currentPlayer = game.getCurrentPlayer();
        clearHand(currentPlayer);
        Card skip = new Card(CardType.SKIP);
        currentPlayer.addCard(skip);
        List<Card> startingHand = currentPlayer.getHandSnapshot();
        clearDrawPile(game.getDrawPile());
        game.getDrawPile().addCard(new Card(CardType.PLACEHOLDER_CARD));
        int drawPileSize = game.getDrawPile().size();

        mockView.displayHand("Sophie", startingHand);
        expectLastCall().once();
        mockView.displayMessage("Skip played. Your turn ends without drawing a card.");
        expectLastCall().once();
        replay(mockView);
        GameController controller = new GameController(game, mockView);

        controller.completeTurn(List.of(0));

        assertEquals(0, currentPlayer.getHandSize());
        assertEquals(drawPileSize, game.getDrawPile().size());
        assertEquals("Jordan", game.getCurrentPlayer().getName());
        verify(mockView);
    }

    @Test
    void completeTurn_SeeFuturePlayed_DisplaysFutureThenDrawsAndAdvances() {
        Game game = new Game(createDeckForPlayers(2));
        GameView mockView = EasyMock.createStrictMock(GameView.class);
        game.setupGame(List.of("Sophie", "Jordan"));
        Player currentPlayer = game.getCurrentPlayer();
        clearHand(currentPlayer);
        Card seeFuture = new Card(CardType.SEE_THE_FUTURE);
        currentPlayer.addCard(seeFuture);
        List<Card> startingHand = currentPlayer.getHandSnapshot();
        clearDrawPile(game.getDrawPile());
        Card secondFutureCard = new Card(CardType.BEARD_CAT);
        Card drawnCard = new Card(CardType.PLACEHOLDER_CARD);
        game.getDrawPile().addCard(secondFutureCard);
        game.getDrawPile().addCard(drawnCard);

        mockView.displayHand("Sophie", startingHand);
        expectLastCall().once();
        mockView.displaySeeTheFutureCards(List.of(drawnCard, secondFutureCard));
        expectLastCall().once();
        mockView.displayCardDrawn(drawnCard);
        expectLastCall().once();
        replay(mockView);
        GameController controller = new GameController(game, mockView);

        controller.completeTurn(List.of(0));

        assertEquals(1, currentPlayer.getHandSize());
        assertEquals(List.of(seeFuture), game.getDiscardPile().snapshot());
        assertEquals("Jordan", game.getCurrentPlayer().getName());
        verify(mockView);
    }

    @Test
    void completeTurn_TwoSeeFutureCardsPlayed_DisplaysBothThenDrawsAndAdvances() {
        Game game = new Game(createDeckForPlayers(2));
        GameView mockView = EasyMock.createStrictMock(GameView.class);
        game.setupGame(List.of("Sophie", "Jordan"));
        Player currentPlayer = game.getCurrentPlayer();
        clearHand(currentPlayer);
        Card firstSeeFuture = new Card(CardType.SEE_THE_FUTURE);
        Card secondSeeFuture = new Card(CardType.SEE_THE_FUTURE);
        currentPlayer.addCard(firstSeeFuture);
        currentPlayer.addCard(secondSeeFuture);
        List<Card> startingHand = currentPlayer.getHandSnapshot();
        clearDrawPile(game.getDrawPile());
        Card secondFutureCard = new Card(CardType.BEARD_CAT);
        Card drawnCard = new Card(CardType.PLACEHOLDER_CARD);
        game.getDrawPile().addCard(secondFutureCard);
        game.getDrawPile().addCard(drawnCard);

        mockView.displayHand("Sophie", startingHand);
        expectLastCall().once();
        mockView.displaySeeTheFutureCards(List.of(drawnCard, secondFutureCard));
        expectLastCall().once();
        mockView.displaySeeTheFutureCards(List.of(drawnCard, secondFutureCard));
        expectLastCall().once();
        mockView.displayCardDrawn(drawnCard);
        expectLastCall().once();
        replay(mockView);
        GameController controller = new GameController(game, mockView);

        controller.completeTurn(List.of(0, 0));

        assertEquals(1, currentPlayer.getHandSize());
        assertEquals(List.of(firstSeeFuture, secondSeeFuture), game.getDiscardPile().snapshot());
        assertEquals("Jordan", game.getCurrentPlayer().getName());
        verify(mockView);
    }

    @Test
    void completeTurn_ShufflePlayed_ShufflesThenDrawsAndAdvances() {
        CountingZeroRandom random = new CountingZeroRandom();
        Game game = new Game(createDeckForPlayers(2, random));
        GameView mockView = EasyMock.createStrictMock(GameView.class);
        game.setupGame(List.of("Sophie", "Jordan"));
        random.resetCalls();
        Player currentPlayer = game.getCurrentPlayer();
        clearHand(currentPlayer);
        Card shuffle = new Card(CardType.SHUFFLE);
        currentPlayer.addCard(shuffle);
        List<Card> startingHand = currentPlayer.getHandSnapshot();
        clearDrawPile(game.getDrawPile());
        Card drawnCardAfterShuffle = new Card(CardType.PLACEHOLDER_CARD);
        Card remainingCardAfterDraw = new Card(CardType.BEARD_CAT);
        game.getDrawPile().addCard(drawnCardAfterShuffle);
        game.getDrawPile().addCard(remainingCardAfterDraw);

        mockView.displayHand("Sophie", startingHand);
        expectLastCall().once();
        mockView.displayCardDrawn(drawnCardAfterShuffle);
        expectLastCall().once();
        replay(mockView);
        GameController controller = new GameController(game, mockView);

        controller.completeTurn(List.of(0));

        assertEquals(1, currentPlayer.getHandSize());
        assertEquals(List.of(shuffle), game.getDiscardPile().snapshot());
        assertEquals(List.of(remainingCardAfterDraw), game.getDrawPile().snapshot());
        assertEquals(List.of(2), random.getBoundsSinceReset());
        assertEquals("Jordan", game.getCurrentPlayer().getName());
        verify(mockView);
    }

    @Test
    void completeTurn_SeeFutureThenSkipPlayed_EndsWithoutDrawing() {
        Game game = new Game(createDeckForPlayers(2));
        GameView mockView = EasyMock.createStrictMock(GameView.class);
        game.setupGame(List.of("Sophie", "Jordan"));
        Player currentPlayer = game.getCurrentPlayer();
        clearHand(currentPlayer);
        Card seeFuture = new Card(CardType.SEE_THE_FUTURE);
        Card skip = new Card(CardType.SKIP);
        currentPlayer.addCard(seeFuture);
        currentPlayer.addCard(skip);
        List<Card> startingHand = currentPlayer.getHandSnapshot();
        clearDrawPile(game.getDrawPile());
        Card secondFutureCard = new Card(CardType.BEARD_CAT);
        Card topCard = new Card(CardType.PLACEHOLDER_CARD);
        game.getDrawPile().addCard(secondFutureCard);
        game.getDrawPile().addCard(topCard);
        int drawPileSize = game.getDrawPile().size();

        mockView.displayHand("Sophie", startingHand);
        expectLastCall().once();
        mockView.displaySeeTheFutureCards(List.of(topCard, secondFutureCard));
        expectLastCall().once();
        mockView.displayMessage("Skip played. Your turn ends without drawing a card.");
        expectLastCall().once();
        replay(mockView);
        GameController controller = new GameController(game, mockView);

        controller.completeTurn(List.of(0, 0));

        assertEquals(0, currentPlayer.getHandSize());
        assertEquals(List.of(seeFuture, skip), game.getDiscardPile().snapshot());
        assertEquals(drawPileSize, game.getDrawPile().size());
        assertEquals("Jordan", game.getCurrentPlayer().getName());
        verify(mockView);
    }

    @Test
    void takeCard_DeckSizeZero_ThrowsException() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(CardType.DEFUSE));
        cards.add(new Card(CardType.DEFUSE));

        for (int i = 0; i < 10; i++) {
            cards.add(new Card(CardType.PLACEHOLDER_CARD));
        }
        // card that will be drawn
        cards.add(new Card(CardType.EXPLODING_KITTEN));

        Deck deck = new Deck(cards);
        Game game = new Game(deck);
        GameView mockView = EasyMock.createMock(GameView.class);
        List<String> players = List.of("player1", "player2");
        game.setupGame(players);

        // empty the deck
        while (game.getDrawPile().size() > 0) {
            game.getDrawPile().draw();
        }

        EasyMock.replay(mockView);

        GameController controller = new GameController(game, mockView);

        assertThrows(IllegalStateException.class, () -> controller.takeCard());
    }

    @Test
    void takeCard_DeckSizeOne_ReturnsCard() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(CardType.DEFUSE));
        cards.add(new Card(CardType.DEFUSE));

        for (int i = 0; i < 10; i++) {
            cards.add(new Card(CardType.PLACEHOLDER_CARD));
        }
        // card that will be drawn
        cards.add(new Card(CardType.EXPLODING_KITTEN));

        Deck deck = new Deck(cards);
        Game game = new Game(deck);
        GameView mockView = EasyMock.createMock(GameView.class);
        List<String> players = List.of("player1", "player2");
        game.setupGame(players);
        Player drawingPlayer = game.getCurrentPlayer();

        clearDrawPile(game.getDrawPile());
        Card expectedCard = new Card(CardType.PLACEHOLDER_CARD);
        game.getDrawPile().addCard(expectedCard);

        assertEquals(1, game.getDrawPile().size());


        // record expected view call
        mockView.displayCardDrawn(EasyMock.anyObject(Card.class));
        EasyMock.expectLastCall().once();
        EasyMock.replay(mockView);

        GameController controller = new GameController(game, mockView);
        Card result = controller.takeCard();

        assertEquals(expectedCard, result);
        assertEquals(0, game.getDrawPile().size());
        assertEquals(7, drawingPlayer.getHandSize());

        verify(mockView);
    }

    @Test
    void takeCard_DeckSizeGreaterThanOne_ReturnsCard() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(CardType.DEFUSE));
        cards.add(new Card(CardType.DEFUSE));

        for (int i = 0; i < 10; i++) {
            cards.add(new Card(CardType.PLACEHOLDER_CARD));
        }
        // card that will be drawn
        cards.add(new Card(CardType.EXPLODING_KITTEN));

        Deck deck = new Deck(cards);
        Game game = new Game(deck);
        GameView mockView = EasyMock.createMock(GameView.class);
        List<String> players = List.of("player1", "player2");
        game.setupGame(players);
        clearDrawPile(game.getDrawPile());
        Card firstDrawPileCard = new Card(CardType.PLACEHOLDER_CARD);
        Card expectedCard = new Card(CardType.BEARD_CAT);
        game.getDrawPile().addCard(firstDrawPileCard);
        game.getDrawPile().addCard(expectedCard);

        int beforeSize = game.getDrawPile().size();

        // record expected view call
        mockView.displayCardDrawn(EasyMock.anyObject(Card.class));
        EasyMock.expectLastCall().once();
        EasyMock.replay(mockView);

        GameController controller = new GameController(game, mockView);
        Card result = controller.takeCard();


        assertEquals(expectedCard, result);
        assertEquals(beforeSize - 1, game.getDrawPile().size());

        verify(mockView);
    }

    @Test
    void takeCard_NonExplodingCard_AdvancesToNextPlayer() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(CardType.DEFUSE));
        cards.add(new Card(CardType.DEFUSE));

        for (int i = 0; i < 10; i++) {
            cards.add(new Card(CardType.PLACEHOLDER_CARD));
        }
        cards.add(new Card(CardType.EXPLODING_KITTEN));

        Deck deck = new Deck(cards);
        Game game = new Game(deck);
        GameView mockView = EasyMock.createMock(GameView.class);
        game.setupGame(List.of("player1", "player2"));
        Player drawingPlayer = game.getCurrentPlayer();
        clearDrawPile(game.getDrawPile());
        Card drawnCard = new Card(CardType.PLACEHOLDER_CARD);
        game.getDrawPile().addCard(drawnCard);

        mockView.displayCardDrawn(drawnCard);
        expectLastCall().once();
        EasyMock.replay(mockView);
        GameController controller = new GameController(game, mockView);

        Card result = controller.takeCard();

        assertEquals(drawnCard, result);
        assertEquals(7, drawingPlayer.getHandSize());
        assertEquals("player2", game.getCurrentPlayer().getName());
        verify(mockView);
    }

    @Test
    void takeCard_ExplodingKittenWithoutDefuse_EliminatesPlayerAndGameContinues() {
        Game game = new Game(createDeckForPlayers(3));
        game.setupGame(List.of("Avery", "Jordan", "Casey"));
        Player currentPlayer = game.getCurrentPlayer();
        clearHand(currentPlayer);
        clearDrawPile(game.getDrawPile());
        Card explodingKitten = new Card(CardType.EXPLODING_KITTEN);
        game.getDrawPile().addCard(explodingKitten);
        GameView mockView = EasyMock.createMock(GameView.class);
        mockView.displayCardDrawn(explodingKitten);
        expectLastCall().once();
        EasyMock.replay(mockView);
        GameController controller = new GameController(game, mockView);

        Card drawnCard = controller.takeCard();

        assertEquals(explodingKitten, drawnCard);
        assertEquals(0, currentPlayer.getHandSize());
        assertFalse(game.getPlayers().contains(currentPlayer));
        assertEquals(2, game.getPlayers().size());
        assertFalse(game.isWon());
        verify(mockView);
    }

    @Test
    void takeCard_ExplodingKittenWithoutDefuse_CurrentPlayerIsNextRemainingPlayer() {
        Game game = new Game(createDeckForPlayers(3));
        game.setupGame(List.of("Avery", "Jordan", "Casey"));
        Player currentPlayer = game.getCurrentPlayer();
        clearHand(currentPlayer);
        clearDrawPile(game.getDrawPile());
        Card explodingKitten = new Card(CardType.EXPLODING_KITTEN);
        game.getDrawPile().addCard(explodingKitten);
        GameView mockView = EasyMock.createMock(GameView.class);
        mockView.displayCardDrawn(explodingKitten);
        expectLastCall().once();
        EasyMock.replay(mockView);
        GameController controller = new GameController(game, mockView);

        Card drawnCard = controller.takeCard();

        assertEquals(explodingKitten, drawnCard);
        assertFalse(game.getPlayers().contains(currentPlayer));
        assertEquals("Jordan", game.getCurrentPlayer().getName());
        verify(mockView);
    }

    @Test
    void takeCard_ExplodingKittenWithoutDefuse_EliminatesPlayerAndDisplaysWinner() {
        Game game = new Game(createDeckForPlayers(2));
        game.setupGame(List.of("Avery", "Jordan"));
        Player currentPlayer = game.getCurrentPlayer();
        clearHand(currentPlayer);
        clearDrawPile(game.getDrawPile());
        Card explodingKitten = new Card(CardType.EXPLODING_KITTEN);
        game.getDrawPile().addCard(explodingKitten);
        GameView mockView = EasyMock.createMock(GameView.class);
        mockView.displayCardDrawn(explodingKitten);
        expectLastCall().once();
        mockView.displayGameOver("Jordan");
        expectLastCall().once();
        EasyMock.replay(mockView);
        GameController controller = new GameController(game, mockView);

        Card drawnCard = controller.takeCard();

        assertEquals(explodingKitten, drawnCard);
        assertEquals(0, currentPlayer.getHandSize());
        assertFalse(game.getPlayers().contains(currentPlayer));
        assertEquals(1, game.getPlayers().size());
        assertTrue(game.isWon());
        verify(mockView);
    }

    @Test
    void takeCard_ExplodingKittenWithDefuse_DefusesAndReinsertsKitten() {
        Game game = new Game(createDeckForPlayers(2));
        game.setupGame(List.of("Avery", "Jordan"));
        Player currentPlayer = game.getCurrentPlayer();
        clearHand(currentPlayer);
        Card defuse = new Card(CardType.DEFUSE);
        currentPlayer.addCard(defuse);
        clearDrawPile(game.getDrawPile());
        Card explodingKitten = new Card(CardType.EXPLODING_KITTEN);
        game.getDrawPile().addCard(explodingKitten);
        GameView mockView = EasyMock.createMock(GameView.class);
        mockView.displayCardDrawn(explodingKitten);
        expectLastCall().once();
        EasyMock.replay(mockView);
        GameController controller = new GameController(game, mockView);

        Card drawnCard = controller.takeCard();

        assertEquals(explodingKitten, drawnCard);
        assertEquals(0, currentPlayer.getHandSize());
        assertEquals(List.of(defuse), game.getDiscardPile().snapshot());
        assertEquals(List.of(explodingKitten), game.getDrawPile().snapshot());
        assertTrue(game.getPlayers().contains(currentPlayer));
        assertEquals(2, game.getPlayers().size());
        verify(mockView);
    }

    @Test
    void takeCard_ExplodingKittenWithDefuse_AdvancesToNextPlayer() {
        Game game = new Game(createDeckForPlayers(2));
        game.setupGame(List.of("Avery", "Jordan"));
        Player currentPlayer = game.getCurrentPlayer();
        clearHand(currentPlayer);
        currentPlayer.addCard(new Card(CardType.DEFUSE));
        clearDrawPile(game.getDrawPile());
        Card explodingKitten = new Card(CardType.EXPLODING_KITTEN);
        game.getDrawPile().addCard(explodingKitten);
        GameView mockView = EasyMock.createMock(GameView.class);
        mockView.displayCardDrawn(explodingKitten);
        expectLastCall().once();
        EasyMock.replay(mockView);
        GameController controller = new GameController(game, mockView);

        Card drawnCard = controller.takeCard();

        assertEquals(explodingKitten, drawnCard);
        assertEquals("Jordan", game.getCurrentPlayer().getName());
        verify(mockView);
    }

    @Test
    void playSkip_ValidSkip_ReturnsTrueAndDisplaysMessage() {
        Game mockModel = EasyMock.createMock(Game.class);
        GameView mockView = EasyMock.createMock(GameView.class);

        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.SKIP));
        DiscardPile discardPile = new DiscardPile();

        expect(mockModel.getCurrentPlayer()).andReturn(player).once();
        expect(mockModel.getDiscardPile()).andReturn(discardPile).once();
        mockModel.advanceTurn();
        expectLastCall().once();
        replay(mockModel);

        mockView.displayMessage("Skip played. Your turn ends without drawing a card.");
        expectLastCall().once();
        replay(mockView);

        GameController controller = new GameController(mockModel, mockView);

        boolean result = controller.playSkip(0);

        assertTrue(result);
        assertEquals(0, player.getHandSize());
        assertEquals(1, discardPile.size());

        verify(mockModel);
        verify(mockView);
    }

    @Test
    void playSkip_ValidSkip_AdvancesToNextPlayerWithoutDrawing() {
        Game game = new Game(createDeckForPlayers(2));
        GameView mockView = EasyMock.createMock(GameView.class);
        game.setupGame(List.of("Sophie", "Jordan"));
        Player currentPlayer = game.getCurrentPlayer();
        clearHand(currentPlayer);
        currentPlayer.addCard(new Card(CardType.SKIP));
        int drawPileSize = game.getDrawPile().size();

        mockView.displayMessage("Skip played. Your turn ends without drawing a card.");
        expectLastCall().once();
        replay(mockView);
        GameController controller = new GameController(game, mockView);

        boolean result = controller.playSkip(0);

        assertTrue(result);
        assertEquals(0, currentPlayer.getHandSize());
        assertEquals(drawPileSize, game.getDrawPile().size());
        assertEquals("Jordan", game.getCurrentPlayer().getName());
        verify(mockView);
    }

    @Test
    void playSkip_SelectedCardIsDefuse_ReturnsFalseAndDisplaysError() {
        Game mockModel = EasyMock.createMock(Game.class);
        GameView mockView = EasyMock.createMock(GameView.class);

        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.DEFUSE));
        DiscardPile discardPile = new DiscardPile();

        expect(mockModel.getCurrentPlayer()).andReturn(player).once();
        expect(mockModel.getDiscardPile()).andReturn(discardPile).once();
        replay(mockModel);

        mockView.displayError(anyString());
        expectLastCall().once();
        replay(mockView);

        GameController controller = new GameController(mockModel, mockView);

        boolean result = controller.playSkip(0);

        assertFalse(result);
        assertEquals(1, player.getHandSize());
        assertEquals(0, discardPile.size());

        verify(mockModel);
        verify(mockView);
    }

    @Test
    void playSkip_InvalidCard_DoesNotAdvanceTurn() {
        Game game = new Game(createDeckForPlayers(2));
        GameView mockView = EasyMock.createMock(GameView.class);
        game.setupGame(List.of("Sophie", "Jordan"));
        Player currentPlayer = game.getCurrentPlayer();
        clearHand(currentPlayer);
        currentPlayer.addCard(new Card(CardType.DEFUSE));

        mockView.displayError(anyString());
        expectLastCall().once();
        replay(mockView);
        GameController controller = new GameController(game, mockView);

        boolean result = controller.playSkip(0);

        assertFalse(result);
        assertEquals("Sophie", game.getCurrentPlayer().getName());
        assertEquals(1, currentPlayer.getHandSize());
        verify(mockView);
    }

    @Test
    void playSkip_NegativeIndex_ReturnsFalseAndDisplaysError() {
        Game mockModel = EasyMock.createMock(Game.class);
        GameView mockView = EasyMock.createMock(GameView.class);

        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.SKIP));
        DiscardPile discardPile = new DiscardPile();

        expect(mockModel.getCurrentPlayer()).andReturn(player).once();
        expect(mockModel.getDiscardPile()).andReturn(discardPile).once();
        replay(mockModel);

        mockView.displayError(anyString());
        expectLastCall().once();
        replay(mockView);

        GameController controller = new GameController(mockModel, mockView);

        boolean result = controller.playSkip(-1);

        assertFalse(result);
        assertEquals(1, player.getHandSize());
        assertEquals(0, discardPile.size());

        verify(mockModel);
        verify(mockView);
    }

    @Test
    void playSkip_IndexEqualsHandSize_ReturnsFalseAndDisplaysError() {
        Game mockModel = EasyMock.createMock(Game.class);
        GameView mockView = EasyMock.createMock(GameView.class);

        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.SKIP));
        DiscardPile discardPile = new DiscardPile();

        expect(mockModel.getCurrentPlayer()).andReturn(player).once();
        expect(mockModel.getDiscardPile()).andReturn(discardPile).once();
        replay(mockModel);

        mockView.displayError(anyString());
        expectLastCall().once();
        replay(mockView);

        GameController controller = new GameController(mockModel, mockView);

        boolean result = controller.playSkip(1);

        assertFalse(result);
        assertEquals(1, player.getHandSize());
        assertEquals(0, discardPile.size());

        verify(mockModel);
        verify(mockView);
    }

    @Test
    void playSkip_InvalidCard_DoesNotDisplaySuccessMessage() {
        Game mockModel = EasyMock.createMock(Game.class);
        GameView mockView = EasyMock.createMock(GameView.class);

        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.DEFUSE));
        DiscardPile discardPile = new DiscardPile();

        expect(mockModel.getCurrentPlayer()).andReturn(player).once();
        expect(mockModel.getDiscardPile()).andReturn(discardPile).once();
        replay(mockModel);

        mockView.displayError(anyString());
        expectLastCall().once();

        replay(mockView);

        GameController controller = new GameController(mockModel, mockView);

        boolean result = controller.playSkip(0);

        assertFalse(result);

        verify(mockModel);
        verify(mockView);
    }

    private Deck createDeckForPlayers(int playerCount) {
        return createDeckForPlayers(playerCount, new Random());
    }

    private Deck createDeckForPlayers(int playerCount, Random random) {
        List<Card> cards = new ArrayList<>();
        for (int count = 0; count < playerCount - 1; count++) {
            cards.add(new Card(CardType.EXPLODING_KITTEN));
        }
        for (int count = 0; count < playerCount; count++) {
            cards.add(new Card(CardType.DEFUSE));
        }
        for (int count = 0; count < playerCount * 5; count++) {
            cards.add(new Card(CardType.PLACEHOLDER_CARD));
        }
        return new Deck(cards, random);
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

    private static final class CountingZeroRandom extends Random {
        private final List<Integer> boundsSinceReset = new ArrayList<>();

        @Override
        public int nextInt(int bound) {
            boundsSinceReset.add(bound);
            return 0;
        }

        void resetCalls() {
            boundsSinceReset.clear();
        }

        List<Integer> getBoundsSinceReset() {
            return List.copyOf(boundsSinceReset);
        }
    }

}
