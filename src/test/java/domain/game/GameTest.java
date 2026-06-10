package domain.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class GameTest {
    @Test
    void constructorRejectsNullDrawPile() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Game(null));

        assertEquals("draw pile must not be null", exception.getMessage());
    }

    @Test
    void getPlayersStartsEmptyBeforeSetup() {
        Game game = new Game(createDeck(1, 2, 10));

        assertEquals(List.of(), game.getPlayers());
    }

    @Test
    void getCurrentPlayerRejectsBeforeSetup() {
        Game game = new Game(createDeck(1, 2, 10));

        IllegalStateException exception =
                assertThrows(IllegalStateException.class, game::getCurrentPlayer);

        assertEquals("game has not been set up", exception.getMessage());
    }

    @Test
    void setupGameRejectsNullPlayerNames() {
        Game game = new Game(createDeck(2, 2, 10));

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> game.setupGame(null));

        assertEquals("player names must not be null", exception.getMessage());
    }

    @Test
    void setupGameRejectsTooFewPlayers() {
        Game game = new Game(createDeck(2, 2, 10));

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> game.setupGame(List.of("Avery")));

        assertEquals("player count must be between 2 and 4", exception.getMessage());
    }

    @Test
    void setupGameRejectsTooManyPlayers() {
        Game game = new Game(createDeck(4, 5, 30));

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> game.setupGame(List.of("Avery", "Jordan", "Casey", "Riley", "Morgan")));

        assertEquals("player count must be between 2 and 4", exception.getMessage());
    }

    @Test
    void setupGameRejectsDeckWithoutEnoughDefuses() {
        Game game = new Game(createDeck(2, 1, 10));

        IllegalStateException exception =
                assertThrows(IllegalStateException.class,
                        () -> game.setupGame(List.of("Avery", "Jordan")));

        assertEquals("deck must contain at least one defuse per player", exception.getMessage());
    }

    @Test
    void setupGameRejectsDeckWithoutEnoughExplodingKittens() {
        Game game = new Game(createDeck(2, 4, 20));

        IllegalStateException exception =
                assertThrows(IllegalStateException.class,
                        () -> game.setupGame(List.of("Avery", "Jordan", "Casey", "Riley")));

        assertEquals("deck must contain enough exploding kittens for setup", exception.getMessage());
    }

    @Test
    void setupGameRejectsDeckWithoutEnoughOpeningCards() {
        Game game = new Game(createDeck(2, 2, 9));

        IllegalStateException exception =
                assertThrows(IllegalStateException.class,
                        () -> game.setupGame(List.of("Avery", "Jordan")));

        assertEquals(
                "deck must contain enough non-special cards to deal opening hands",
                exception.getMessage());
    }

    @Test
    void setupGameConfiguresMinimumPlayerGame() {
        Game game = new Game(createDeck(3, 3, 12));

        game.setupGame(List.of("Avery", "Jordan"));

        assertEquals(2, game.getPlayers().size());
        assertEquals("Avery", game.getPlayers().get(0).getName());
        assertEquals("Jordan", game.getPlayers().get(1).getName());
        assertEquals("Avery", game.getCurrentPlayer().getName());
        assertEquals(0, game.getDiscardPile().size());
        assertPlayersHaveOpeningHands(game.getPlayers());
        assertEquals(1, game.getDrawPile().countCardsOfType(CardType.EXPLODING_KITTEN));
        assertEquals(1, game.getDrawPile().countCardsOfType(CardType.DEFUSE));
        assertEquals(4, game.getDrawPile().size());
    }

    @Test
    void setupGameConfiguresMaximumPlayerGame() {
        Game game = new Game(createDeck(4, 5, 20));

        game.setupGame(List.of("Avery", "Jordan", "Casey", "Riley"));

        assertEquals(4, game.getPlayers().size());
        assertEquals("Avery", game.getCurrentPlayer().getName());
        assertPlayersHaveOpeningHands(game.getPlayers());
        assertEquals(3, game.getDrawPile().countCardsOfType(CardType.EXPLODING_KITTEN));
        assertEquals(1, game.getDrawPile().countCardsOfType(CardType.DEFUSE));
        assertEquals(4, game.getDrawPile().size());
    }

    @Test
    void isWon_WhenOnePlayerRemains_ReturnsTrue() {
        Game game = new Game(createDeck(2, 2, 10));
        game.setupGame(List.of("Avery", "Jordan"));
        Player eliminatedPlayer = game.getPlayers().get(0);

        game.eliminatePlayer(eliminatedPlayer);

        assertEquals(1, game.getPlayers().size());
        assertTrue(game.isWon());
    }

    @Test
    void eliminatePlayer_WithThreePlayers_RemovesPlayerAndGameContinues() {
        Game game = new Game(createDeck(3, 3, 15));
        game.setupGame(List.of("Avery", "Jordan", "Casey"));
        Player eliminatedPlayer = game.getPlayers().get(1);

        game.eliminatePlayer(eliminatedPlayer);

        assertFalse(game.getPlayers().contains(eliminatedPlayer));
        assertEquals(2, game.getPlayers().size());
        assertEquals("Avery", game.getPlayers().get(0).getName());
        assertEquals("Casey", game.getPlayers().get(1).getName());
        assertFalse(game.isWon());
    }
    @Test
    void eliminateCurrentLastPlayer_WrapsCurrentPlayerToFirstRemainingPlayer() {
        Game game = new Game(createDeck(3, 3, 15));
        game.setupGame(List.of("Avery", "Jordan", "Casey"));
        game.advanceTurn();
        game.advanceTurn();
        Player eliminatedPlayer = game.getCurrentPlayer();

        game.eliminatePlayer(eliminatedPlayer);

        assertFalse(game.getPlayers().contains(eliminatedPlayer));
        assertEquals("Avery", game.getCurrentPlayer().getName());
        assertEquals(0, game.getCurrentPlayerIndex());
    }

    @Test
    void setupGame_UsesInjectedRandomForBothSetupShuffles() {
        Random random = EasyMock.createMock(Random.class);
        expectShuffle(random, 10);
        expectShuffle(random, 2);
        EasyMock.replay(random);
        Game game = new Game(createDeck(3, 3, 10, random));

        game.setupGame(List.of("Avery", "Jordan"));

        EasyMock.verify(random);
    }

    @Test
    void setupGame_CalledTwice_DoesNotDuplicatePlayers() {
        //initialize deck big enough for back to back game setups
        Game game = new Game(createDeck(6, 6, 24));

        game.setupGame(List.of("Avery", "Jordan"));
        game.setupGame(List.of("Avery", "Jordan"));

        assertEquals(2, game.getPlayers().size());
    }

    @Test
    void setupGame_CalledTwice_ClearsPlayers() {
        //initialize deck big enough for back to back game setups
        Game game = new Game(createDeck(6, 6, 24));

        game.setupGame(List.of("Avery", "Jordan"));
        List<Player> playerOne = game.getPlayers();
        game.setupGame(List.of("Morgan", "Kate"));
        List<Player> playerTwo = game.getPlayers();

        assertEquals(2, game.getPlayers().size());
        assertNotEquals(playerOne, playerTwo);
    }

    @Test
    void getCurrentPlayerIndex_AfterSetup_ReturnsZero() {
        Game game = new Game(createDeck(3, 3, 12));
        game.setupGame(List.of("Avery", "Jordan"));

        assertEquals(0, game.getCurrentPlayerIndex());
    }
    @Test
    void getCurrentPlayerIndex_AfterSetupAndOneRound_ReturnsOne() {
        Game game = new Game(createDeck(3, 3, 12));
        game.setupGame(List.of("Avery", "Jordan"));
        game.advanceTurn();

        assertNotEquals(0, game.getCurrentPlayerIndex());
    }

    @Test
    void advanceTurn_WrapsAroundToFirstPlayer() {
        Game game = new Game(createDeck(3, 3, 12));
        game.setupGame(List.of("Avery", "Jordan"));

        game.advanceTurn(); // index = 1
        game.advanceTurn(); // index should wrap to 0

        assertEquals("Avery", game.getCurrentPlayer().getName());
    }

    @Test
    void advanceTurn_ThreePlayers_CurrentPlayerIsSecond() {
        Game game = new Game(createDeck(4, 4, 20));
        game.setupGame(List.of("Avery", "Jordan", "Casey"));

        game.advanceTurn();

        assertEquals("Jordan", game.getCurrentPlayer().getName());
    }

    @Test
    void applyAttack_WhenAttackerIsAlreadyUnderAttack_StacksRemainingPlusTwo() {
        Game game = new Game(createDeck(3, 3, 15));
        game.setupGame(List.of("Avery", "Jordan", "Casey"));

        game.applyAttack();
        assertEquals("Jordan", game.getCurrentPlayer().getName());

        game.applyAttack();
        assertEquals("Casey", game.getCurrentPlayer().getName());

        game.advanceTurn();
        assertEquals("Casey", game.getCurrentPlayer().getName());
        game.advanceTurn();
        assertEquals("Casey", game.getCurrentPlayer().getName());
        game.advanceTurn();
        assertEquals("Casey", game.getCurrentPlayer().getName());

        game.advanceTurn();
        assertEquals("Avery", game.getCurrentPlayer().getName());
    }

    @Test
    void applyAttack_AfterReverse_TargetsPreviousPlayer() {
        Game game = new Game(createDeck(3, 3, 15));
        game.setupGame(List.of("Alice", "Bob", "Charlie"));
        game.reverseDirection();

        game.applyAttack();

        assertEquals("Charlie", game.getCurrentPlayer().getName());
        assertEquals(2, game.getForcedTurns());
    }

    @Test
    void endTurnClearingForced_WithNoForcedTurns_MovesToNextPlayer() {
        Game game = new Game(createDeck(1, 2, 10));
        game.setupGame(List.of("Alice", "Bob"));

        game.endTurnClearingForced();

        assertEquals(0, game.getForcedTurns());
        assertEquals("Bob", game.getCurrentPlayer().getName());
    }

    @Test
    void endTurnClearingForced_WithTwoForcedTurns_SetsToZeroAndMovesToNextPlayer() {
        Game game = new Game(createDeck(1, 2, 10));
        game.setupGame(List.of("Alice", "Bob"));

        game.applyAttack();
        assertEquals("Bob", game.getCurrentPlayer().getName());

        game.endTurnClearingForced();

        assertEquals(0, game.getForcedTurns());
        assertEquals("Alice", game.getCurrentPlayer().getName());
    }

    @Test
    void endTurnClearingForced_WithOneForcedTurn_SetsToZeroAndMovesToNextPlayer() {
        Game game = new Game(createDeck(1, 2, 10));
        game.setupGame(List.of("Alice", "Bob"));

        game.applyAttack();
        assertEquals("Bob", game.getCurrentPlayer().getName());
        game.advanceTurn();

        game.endTurnClearingForced();

        assertEquals(0, game.getForcedTurns());
        assertEquals("Alice", game.getCurrentPlayer().getName());
    }

    @Test
    void endTurnClearingForced_AfterReverse_MovesToPreviousPlayer() {
        Game game = new Game(createDeck(3, 3, 15));
        game.setupGame(List.of("Alice", "Bob", "Charlie"));
        game.reverseDirection();

        game.endTurnClearingForced();

        assertEquals(0, game.getForcedTurns());
        assertEquals("Charlie", game.getCurrentPlayer().getName());
    }

    @Test
    void reverseDirection_FromForward_BecomesBackward() {
        Game game = new Game(createDeck(3, 3, 15));
        game.setupGame(List.of("Alice", "Bob", "Charlie"));

        assertEquals(1, game.getDirection());
        game.reverseDirection();
        assertEquals(-1, game.getDirection());
    }

    @Test
    void reverseDirection_FromBackward_BecomesForward() {
        Game game = new Game(createDeck(3, 3, 15));
        game.setupGame(List.of("Alice", "Bob", "Charlie"));

        game.reverseDirection();
        assertEquals(-1, game.getDirection());

        game.reverseDirection();
        assertEquals(1, game.getDirection());
    }

    @Test
    void advanceTurnWithDirection_Forward_MovesToNextPlayer() {
        Game game = new Game(createDeck(3, 3, 15));
        game.setupGame(List.of("Alice", "Bob", "Charlie"));

        assertEquals("Alice", game.getCurrentPlayer().getName());

        game.advanceTurnWithDirection();
        assertEquals("Bob", game.getCurrentPlayer().getName());

        game.advanceTurnWithDirection();
        assertEquals("Charlie", game.getCurrentPlayer().getName());

        game.advanceTurnWithDirection();
        assertEquals("Alice", game.getCurrentPlayer().getName());
    }

    @Test
    void advanceTurnWithDirection_Reverse_MovesToPreviousPlayer() {
        Game game = new Game(createDeck(3, 3, 15));
        game.setupGame(List.of("Alice", "Bob", "Charlie"));

        game.reverseDirection();
        assertEquals(-1, game.getDirection());

        game.advanceTurnWithDirection();
        assertEquals("Charlie", game.getCurrentPlayer().getName());

        game.advanceTurnWithDirection();
        assertEquals("Bob", game.getCurrentPlayer().getName());

        game.advanceTurnWithDirection();
        assertEquals("Alice", game.getCurrentPlayer().getName());
    }

    @Test
    void advanceTurn_AfterReverse_MovesToPreviousPlayer() {
        Game game = new Game(createDeck(3, 3, 15));
        game.setupGame(List.of("Alice", "Bob", "Charlie"));
        game.reverseDirection();

        game.advanceTurn();

        assertEquals("Charlie", game.getCurrentPlayer().getName());
    }

    @Test
    void eliminatePlayer_WithExplodingKitten_TracksFaceUpKittenAndRemainingCards() {
        Game game = new Game(createDeck(1, 2, 10));
        game.setupGame(List.of("Avery", "Jordan"));

        Player eliminatedPlayer = game.getCurrentPlayer();
        clearHand(eliminatedPlayer);

        Card remainingCard = new Card(CardType.BEARD_CAT);
        Card secondRemainingCard = new Card(CardType.SKIP);
        eliminatedPlayer.addCard(remainingCard);
        eliminatedPlayer.addCard(secondRemainingCard);

        Card killingKitten = new Card(CardType.EXPLODING_KITTEN);

        game.eliminatePlayer(eliminatedPlayer, killingKitten);

        assertFalse(game.getPlayers().contains(eliminatedPlayer));
        assertEquals(1, game.getEliminatedPlayers().size());

        EliminatedPlayer record = game.getEliminatedPlayers().get(0);
        assertEquals("Avery", record.getPlayerName());
        assertEquals(killingKitten, record.getKillingKitten());
        assertEquals(List.of(remainingCard, secondRemainingCard), record.getVisibleCards());
        assertEquals(2, record.getVisibleCardCount());
    }

    @Test
    void eliminatePlayer_CurrentPlayerAfterReverse_SelectsPreviousPlayer() {
        Game game = new Game(createDeck(2, 3, 15));
        game.setupGame(List.of("Alice", "Bob", "Charlie"));
        game.reverseDirection();
        Player alice = game.getCurrentPlayer();

        game.eliminatePlayer(alice, new Card(CardType.EXPLODING_KITTEN));

        assertEquals("Charlie", game.getCurrentPlayer().getName());
    }

    private void clearHand(Player player) {
        while (player.getHandSize() > 0) {
            player.removeCard(0);
        }
    }

    private void assertPlayersHaveOpeningHands(List<Player> players) {
        for (Player player : players) {
            assertEquals(6, player.getHandSize());
            assertEquals(1, player.countCardsOfType(CardType.DEFUSE));
        }
    }

    private Deck createDeck(int explodingKittens, int defuses, int others) {
        return new Deck(createCards(explodingKittens, defuses, others));
    }

    private Deck createDeck(int explodingKittens, int defuses, int others, Random random) {
        return new Deck(createCards(explodingKittens, defuses, others), random);
    }

    private List<Card> createCards(int explodingKittens, int defuses, int others) {
        List<Card> cards = new ArrayList<>();
        for (int count = 0; count < explodingKittens; count++) {
            cards.add(createCardWithType(CardType.EXPLODING_KITTEN));
        }
        for (int count = 0; count < defuses; count++) {
            cards.add(createCardWithType(CardType.DEFUSE));
        }
        for (int count = 0; count < others; count++) {
            cards.add(createCardWithType(CardType.PLACEHOLDER_CARD));
        }
        return cards;
    }

    private void expectShuffle(Random random, int deckSize) {
        for (int bound = deckSize; bound > 1; bound--) {
            EasyMock.expect(random.nextInt(bound)).andReturn(0);
        }
    }

    private Card createCardWithType(CardType type) {
        Card card = EasyMock.createMock(Card.class);
        EasyMock.expect(card.getType()).andStubReturn(type);
        EasyMock.replay(card);
        return card;
    }


    @Test
    void applyTargetedAttack_NullTarget_ThrowsIllegalArgumentException() {
        Game game = new Game(createDeck(1, 2, 10));
        game.setupGame(List.of("Sophie", "Jordan"));

        assertThrows(IllegalArgumentException.class, () -> game.applyTargetedAttack(null));
    }

    @Test
    void applyTargetedAttack_ValidTarget_SetsCurrentPlayerToTargetWithTwoForcedTurns() {
        Game game = new Game(createDeck(1, 2, 10));
        game.setupGame(List.of("Sophie", "Jordan"));
        Player jordan = game.getPlayers().get(1);

        game.applyTargetedAttack(jordan);

        assertEquals("Jordan", game.getCurrentPlayer().getName());
        assertEquals(2, game.getForcedTurns());
    }

    @Test
    void applyTargetedAttack_SelfTarget_ThrowsIllegalArgumentException() {
        Game game = new Game(createDeck(1, 2, 10));
        game.setupGame(List.of("Sophie", "Jordan"));
        Player sophie = game.getCurrentPlayer();

        assertThrows(IllegalArgumentException.class, () -> game.applyTargetedAttack(sophie));
    }

    @Test
    void applyTargetedAttack_PlayerOutsideGame_ThrowsIllegalArgumentException() {
        Game game = new Game(createDeck(1, 2, 10));
        game.setupGame(List.of("Sophie", "Jordan"));

        assertThrows(IllegalArgumentException.class,
                () -> game.applyTargetedAttack(new Player("Casey")));
    }

    @Test
    void applyTargetedAttack_WithExistingForcedTurns_StacksForcedTurns() {
        Game game = new Game(createDeck(1, 2, 10));
        game.setupGame(List.of("Sophie", "Jordan"));
        Player sophie = game.getPlayers().get(0);
        game.applyAttack(); // current player is now Jordan

        game.applyTargetedAttack(sophie); // Jordan targets Sophie

        assertEquals("Sophie", game.getCurrentPlayer().getName());
        assertEquals(4, game.getForcedTurns());
    }
}
