package domain.game;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private static final String DRAW_PILE_REQUIRED_MESSAGE = "draw pile must not be null";
    private static final String PLAYER_NAMES_REQUIRED_MESSAGE = "player names must not be null";
    private static final String INVALID_PLAYER_COUNT_MESSAGE =
            "player count must be between 2 and 4";
    private static final String GAME_NOT_READY_MESSAGE = "game has not been set up";
    private static final String NOT_ENOUGH_DEFUSES_MESSAGE =
            "deck must contain at least one defuse per player";
    private static final String NOT_ENOUGH_KITTENS_MESSAGE =
            "deck must contain enough exploding kittens for setup";
    private static final String NOT_ENOUGH_STARTING_CARDS_MESSAGE =
            "deck must contain enough non-special cards to deal opening hands";
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;
    private static final int OPENING_HAND_SIZE = 5;

    // Open to discussion here
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Game owns and mutates the injected draw pile as its shared deck state.")
    private final Deck drawPile;
    private final List<Player> players;
    private DiscardPile discardPile;
    private int currentPlayerIndex;
    private int forcedTurns;
    private int direction = 1;
    private final List<EliminatedPlayer> eliminatedPlayers;

    // Open to discussion: making Game final would avoid this warning, but controller tests currently mock it.
    @SuppressFBWarnings(
            value = "CT_CONSTRUCTOR_THROW",
            justification = "Validation rejects null draw piles before any subclass-visible state is used.")
    public Game(Deck drawPile) {
        if (drawPile == null) {
            throw new IllegalArgumentException(DRAW_PILE_REQUIRED_MESSAGE);
        }
        this.drawPile = drawPile;
        this.players = new ArrayList<>();
        this.discardPile = new DiscardPile();
        this.currentPlayerIndex = 0;
        this.forcedTurns = 0;
        this.direction = 1;
        this.eliminatedPlayers = new ArrayList<>();
    }
    public void setupGame(List<String> playerNames) {
        if (playerNames == null) {
            throw new IllegalArgumentException(PLAYER_NAMES_REQUIRED_MESSAGE);
        }

        int playerCount = playerNames.size();
        if (playerCount < MIN_PLAYERS || playerCount > MAX_PLAYERS) {
            throw new IllegalArgumentException(INVALID_PLAYER_COUNT_MESSAGE);
        }

        validateDeckForSetup(playerCount);

        //setup rules: remove special cards, deal defuses and opening hands,
        // then return only the required cards to the draw pile before the final shuffle
        List<Player> createdPlayers = createPlayers(playerNames);
        List<Card> explodingKittens = drawPile.removeCardsByType(CardType.EXPLODING_KITTEN);
        List<Card> defuses = drawPile.removeCardsByType(CardType.DEFUSE);

        dealMandatoryDefuses(createdPlayers, defuses, playerCount);
        drawPile.shuffle();
        dealOpeningHands(createdPlayers);
        returnCardsToDeck(defuses, playerCount, defuses.size());
        returnCardsToDeck(explodingKittens, 0, playerCount - 1);
        drawPile.shuffle();

        players.clear();
        players.addAll(createdPlayers);
        discardPile = new DiscardPile();
        currentPlayerIndex = 0;
        forcedTurns = 0;
        eliminatedPlayers.clear();
    }

    public List<Player> getPlayers() {
        return List.copyOf(players);
    }

    public Player getCurrentPlayer() {
        if (players.isEmpty()) {
            throw new IllegalStateException(GAME_NOT_READY_MESSAGE);
        }
        return players.get(currentPlayerIndex);
    }

    Deck getDrawPile() {
        return drawPile;
    }

    DiscardPile getDiscardPile() {
        return discardPile;
    }

    int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    private void validateDeckForSetup(int playerCount) {
        int explodingKittenCount = Math.toIntExact(
                drawPile.countCardsOfType(CardType.EXPLODING_KITTEN));
        if (explodingKittenCount < playerCount - 1L) {
            throw new IllegalStateException(NOT_ENOUGH_KITTENS_MESSAGE);
        }

        int defuseCount = Math.toIntExact(
                drawPile.countCardsOfType(CardType.DEFUSE));
        if (defuseCount < playerCount) {
            throw new IllegalStateException(NOT_ENOUGH_DEFUSES_MESSAGE);
        }


        int nonSpecialCardCount =
                drawPile.size() - explodingKittenCount - defuseCount;
        if (nonSpecialCardCount < playerCount * OPENING_HAND_SIZE) {
            throw new IllegalStateException(NOT_ENOUGH_STARTING_CARDS_MESSAGE);
        }
    }

    private List<Player> createPlayers(List<String> playerNames) {
        List<Player> createdPlayers = new ArrayList<>();
        for (String playerName : playerNames) {
            createdPlayers.add(new Player(playerName));
        }
        return createdPlayers;
    }

    private void dealMandatoryDefuses(List<Player> createdPlayers, List<Card> defuses, int playerCount) {
        for (int index = 0; index < playerCount; index++) {
            createdPlayers.get(index).addCard(defuses.get(index));
        }
    }

    private void dealOpeningHands(List<Player> createdPlayers) {
        for (Player player : createdPlayers) {
            for (int cardsDealt = 0; cardsDealt < OPENING_HAND_SIZE; cardsDealt++) {
                player.addCard(drawPile.draw());
            }
        }
    }

    // reinsert only the portion of removed cards required by setup
    private void returnCardsToDeck(List<Card> cards, int startInclusive, int endExclusive) {
        for (int index = startInclusive; index < endExclusive; index++) {
            drawPile.addCard(cards.get(index));
        }
    }
    void eliminatePlayer(Player player) {
        players.remove(player);
        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0;
        }
    }

    public void advanceTurn() {
        if (forcedTurns > 0) {
            forcedTurns--;
            if (forcedTurns > 0) {
                return;
            }
        }
        currentPlayerIndex = Math.floorMod(currentPlayerIndex + direction, players.size());
    }

    // Attack ends the attacker's turn and forces the next player to take the
    // attacker's untaken turns plus two more (so attacks stack).
    public void applyAttack() {
        int untakenTurns = forcedTurns;
        currentPlayerIndex = Math.floorMod(currentPlayerIndex + direction, players.size());
        forcedTurns = untakenTurns + 2;
    }

    public void applyTargetedAttack(Player target) {
        if (target == null) {
            throw new IllegalArgumentException("target must not be null");
        }
        if (target == getCurrentPlayer()) {
            throw new IllegalArgumentException("cannot target yourself");
        }
        int targetIndex = players.indexOf(target);
        if (targetIndex < 0) {
            throw new IllegalArgumentException("target must be an active player");
        }
        int untakenTurns = forcedTurns;
        currentPlayerIndex = targetIndex;
        forcedTurns = untakenTurns + 2;
    }

    boolean isWon() {
        return players.size() == 1;
    }

    int getForcedTurns() {
        return forcedTurns;
    }

    public void endTurnClearingForced() {
        forcedTurns = 0;
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public void reverseDirection() {
        direction = direction * -1;
    }

    public void advanceTurnWithDirection() {
        if (forcedTurns > 0) {
            forcedTurns--;
            if (forcedTurns > 0) {
                return;
            }
        }
        currentPlayerIndex = Math.floorMod(currentPlayerIndex + direction, players.size());
    }

    int getDirection() {
        return direction;
    }

    public void eliminatePlayer(Player player, Card killingKitten) {
        List<Card> visibleCards = player.getHandSnapshot();
        eliminatedPlayers.add(new EliminatedPlayer(
                player.getName(),
                killingKitten,
                visibleCards));

        int eliminatedIndex = players.indexOf(player);
        players.remove(player);

        while (player.getHandSize() > 0) {
            player.removeCard(0);
        }

        if (!players.isEmpty() && eliminatedIndex <= currentPlayerIndex) {
            currentPlayerIndex = currentPlayerIndex % players.size();
        }
    }
    public List<EliminatedPlayer> getEliminatedPlayers() {
        return List.copyOf(eliminatedPlayers);
    }
}
