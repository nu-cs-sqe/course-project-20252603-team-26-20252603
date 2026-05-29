package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Locale;
import java.util.ResourceBundle;
import java.text.MessageFormat;

import domain.game.Card;

public class GameView {
    private Scanner scanner;
    private ResourceBundle messages;
    private static final String PLAYER_NOT_NULL_MESSAGE = "player must not be null";


    public GameView() {
        this.scanner = new Scanner(System.in, java.nio.charset.StandardCharsets.UTF_8.name());
        this.messages = ResourceBundle.getBundle("message", Locale.getDefault());
    }

    public void displayStartScreen() {
        System.out.println(messages.getString("start.screen.title"));
    }

    public List<String> promptPlayerNames() {
        List<String> names = new ArrayList<>();

        // we don't validate size of players since that is handled by controller class
        System.out.print(messages.getString("player.prompt.count"));
        final int playerCount = scanner.nextInt();
        scanner.nextLine(); // clears extra \n from buffer

        for (int i = 0; i < playerCount; i++) {
            String prompt = MessageFormat.format(messages.getString("player.prompt.name"), i + 1);
            System.out.print(prompt);
            names.add(scanner.nextLine()); // add player's to name list
        }
        return names;
    }

    public void displayGameReady() {
        System.out.println(messages.getString("game.ready.message"));
    }

    public void displayError(String message) {
        System.out.println(messages.getString("error.prefix") + message );
    }

    public void displayCardDrawn (Card card) {

        String message = MessageFormat.format(messages.getString("card.drawn.message"), card.getType());
        System.out.println(message);
    }

    public void displayMessage(String message) {
        System.out.println(message);

    }
      
    public void displaySeeTheFutureCards(List<Card> cards) {
        if (cards == null) {
            throw new NullPointerException(messages.getString("error.cards.not.null"));
        }

        System.out.println(messages.getString("see.future.title"));

        if (cards.isEmpty()) {
            System.out.println(messages.getString("see.future.no.cards"));
            return;
        }

        for (int i = 0; i < cards.size(); i++) {
           String cardLine = MessageFormat.format(messages.getString("see.future.card.format"), i + 1, cards.get(i).getType());
            System.out.println(cardLine);
        }

    }

    public void displayGameOver() {
        System.out.println("Game over!");
    }
}
