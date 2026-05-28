package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import domain.game.Card;

public class GameView {
    private Scanner scanner;
    public GameView() {
        this.scanner = new Scanner(System.in, java.nio.charset.StandardCharsets.UTF_8.name());
    }

    public void displayStartScreen() {
        System.out.println("Start Playing Exploding Kittens!");
    }

    public List<String> promptPlayerNames() {
        List<String> names = new ArrayList<>();

        // we don't validate size of players since that is handled by controller class
        System.out.print("Enter number of players: ");
        final int playerCount = scanner.nextInt();
        scanner.nextLine(); // clears extra \n from buffer

        for (int i = 0; i < playerCount; i++) {
            System.out.print("Enter name of player " + (i + 1) + ": ");
            names.add(scanner.nextLine()); // add player's to name list
        }
        return names;
    }

    public void displayGameReady() {
        System.out.println("Game is ready! Let's begin!");
    }

    public void displayError(String message) {
        System.out.println("Error: " + message);
    }

    public void displayCardDrawn (Card card) {
        System.out.println("You drew: " + card.getType());
    }

    public void displayMessage(String message) {
        System.out.println(message);

    }
      
    public void displaySeeTheFutureCards(List<Card> cards) {


        System.out.println("See the Future: Top cards in the draw pile:");

        if (cards.isEmpty()) {
            System.out.println("No cards to view.");
            return;
        }

        for (int i = 0; i < cards.size(); i++) {
            System.out.println((i + 1) + ". " + cards.get(i).getType());
        }
    }
}