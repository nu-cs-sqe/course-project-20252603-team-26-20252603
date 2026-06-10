package ui;

import domain.game.DeckFactory;
import domain.game.Game;
import domain.game.GameController;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        Game game = new Game(DeckFactory.completeDeck());
        GameView view = new GameView();
        GameController controller = new GameController(game, view);
        controller.runGame();
    }
}
