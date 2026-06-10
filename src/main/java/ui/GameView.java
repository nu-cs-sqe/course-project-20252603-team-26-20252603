package ui;

import java.io.InputStream;
import java.util.*;
import java.text.MessageFormat;

import domain.game.Card;

public class GameView {
    private Scanner scanner;
    private ResourceBundle messages;
    private static final String MESSAGE_REQUIRED_MESSAGE = "message must not be null";

    /** Production constructor – reads from System.in. */
    public GameView() {
        this(System.in);
    }

    /**
     * Testable constructor – reads from the supplied stream.
     * Allows tests to inject simulated keyboard input without touching System.in globally.
     */
    public GameView(InputStream inputStream) {
        this.scanner  = new Scanner(
                Objects.requireNonNull(inputStream, "inputStream must not be null"),
                java.nio.charset.StandardCharsets.UTF_8);
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
        if (message == null) {
            throw new IllegalArgumentException(MESSAGE_REQUIRED_MESSAGE);
        }

        System.out.println(messages.getString("error.prefix") + message );
    }

    public void displayCardDrawn (Card card) {
        if (card == null) {
            throw new IllegalArgumentException("card must not be null");
        }
        String message = MessageFormat.format(messages.getString("card.drawn.message"), card.getType());
        System.out.println(message);
    }

    public void displayMessage(String message) {
        System.out.println(message);

    }

    public void displayHand(String playerName, List<Card> cards) {
        String title = MessageFormat.format(messages.getString("hand.title"), playerName);
        System.out.println(title);

        for (int i = 0; i < cards.size(); i++) {
            String cardLine = MessageFormat.format(messages.getString("hand.card.format"),
                    i + 1, cards.get(i).getType());
            System.out.println(cardLine);
        }
    }
      
    public void displaySeeTheFutureCards(List<Card> cards) {


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

    public void displayGameOver(String winnerName) {
        String message = MessageFormat.format(messages.getString("game.over.winner"), winnerName);
        System.out.println(message);
    }
}
