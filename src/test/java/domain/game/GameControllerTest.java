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
    void playSkip_ValidSkip_ReturnsTrueAndDisplaysMessage() {
        Game mockModel = EasyMock.createMock(Game.class);
        GameView mockView = EasyMock.createMock(GameView.class);

        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.SKIP));
        DiscardPile discardPile = new DiscardPile();

        expect(mockModel.getCurrentPlayer()).andReturn(player).once();
        expect(mockModel.getDiscardPile()).andReturn(discardPile).once();
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

    @Test
    void endTurn_WithMultiplePendingAttacks_DecrementsAndStays() {
        Game mockModel = EasyMock.createMock(Game.class);
        GameView mockView = EasyMock.createMock(GameView.class);

        GameController controller = new GameController(mockModel, mockView);
        controller.setPendingAttackTurns(2);

        mockView.displayMessage("1 attack turn(s) remain.");
        EasyMock.expectLastCall().once();
        EasyMock.replay(mockModel, mockView);

        controller.endTurn();

        EasyMock.verify(mockModel, mockView);
    }

    @Test
    void endTurn_WithOnePendingAttack_DecrementsAndMovesToNextPlayer() {
        Game mockModel = EasyMock.createMock(Game.class);
        GameView mockView = EasyMock.createMock(GameView.class);

        mockModel.nextTurn();
        EasyMock.expectLastCall().once();
        EasyMock.replay(mockModel, mockView);

        GameController controller = new GameController(mockModel, mockView);
        controller.setPendingAttackTurns(1);

        controller.endTurn();

        EasyMock.verify(mockModel, mockView);
    }

    @Test
    void endTurn_WithNoPendingAttacks_MovesToNextPlayer() {
        Game mockModel = EasyMock.createMock(Game.class);
        GameView mockView = EasyMock.createMock(GameView.class);

        mockModel.nextTurn();
        EasyMock.expectLastCall().once();
        EasyMock.replay(mockModel, mockView);

        GameController controller = new GameController(mockModel, mockView);
        controller.setPendingAttackTurns(0);

        controller.endTurn();

        EasyMock.verify(mockModel, mockView);
    }

    @Test
    void playAttackCard_NoPendingTurns_AddsTwoTurns() {
        Game realGame = new Game(createDeckForPlayers(2));
        realGame.setupGame(List.of("Alice", "Bob"));
        GameView mockView = EasyMock.createNiceMock(GameView.class);
        EasyMock.replay(mockView);

        Player currentPlayer = realGame.getCurrentPlayer();
        clearHand(currentPlayer);
        currentPlayer.addCard(new Card(CardType.ATTACK));

        GameController controller = new GameController(realGame, mockView);

        controller.playAttackCard(0);

        assertEquals(0, currentPlayer.getHandSize());

        EasyMock.verify(mockView);
    }

    private Deck createDeckForPlayers(int playerCount) {
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
        return new Deck(cards);
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
