(A) Model Layer (Core Logic)
Game
Responsibility: Manage overall game state and setup

class Game {
    private List<Player> players;
    private Deck drawPile;
    private DiscardPile discardPile;
    private int currentPlayerIndex;

    public void setupGame(List<String> playerNames);
    public List<Player> getPlayers();
    public Player getCurrentPlayer();
}

Player

class Player {
    private String name;
    private List<Card> hand;

    public void addCard(Card card);
    public int getHandSize();
}

Deck

class Deck {
    private List<Card> cards;

    public void shuffle();
    public Card draw();
    public void addCard(Card card);
    public List<Card> removeCardsByType(CardType type);
}

Card

class Card {
    private CardType type;

    public CardType getType();
}

CardType

enum CardType {
    EXPLODING_KITTEN,
    DEFUSE,
    OTHER
}

DiscardPile

class DiscardPile {
    private List<Card> cards;

    public void add(Card card);
}

(B) Controller Layer
GameController
Responsibility (from lecture):
Interpret user action 
Call model methods 

class GameController {
    private Game game;

    public void startGame(List<String> playerNames);
}

(C) View Layer
GameView
Responsibility (from lecture):
Display UI 
Get user input 

class GameView {
    private GameController controller;

    public void displayStartScreen();
    public void promptPlayerNames();
    public void displayGameReady();
}
