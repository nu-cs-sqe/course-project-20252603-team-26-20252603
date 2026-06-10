package ui;

import java.io.InputStream;


import java.text.MessageFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import domain.game.Card;
import domain.game.EliminatedPlayer;
import domain.game.Player;

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

    public int promptCardChoice() {
        return readInteger("turn.card.prompt");
    }

    public int promptSecondCardChoice() {
        return readInteger("turn.second.card.prompt");
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

    public void displayCardStolen(Card card) {
        String message = MessageFormat.format(
                messages.getString("card.stolen.message"), card.getType());
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

    public Player promptTargetPlayer(List<Player> players) {
        while (true) {
            System.out.println(messages.getString("target.player.prompt"));
            for (int i = 0; i < players.size(); i++) {
                System.out.println((i + 1) + ". " + players.get(i).getName());
            }
            int choice = readInteger("target.player.choice");
            if (choice >= 1 && choice <= players.size()) {
                return players.get(choice - 1);
            }
            String message = MessageFormat.format(
                    messages.getString("target.player.invalid"), players.size());
            System.out.println(message);
        }
    }

    public void displayPublicPlayerState(
            List<Player> activePlayers,
            List<EliminatedPlayer> eliminatedPlayers) {
        System.out.println("Public player state:");

        for (Player player : activePlayers) {
            System.out.println(player.getName() + ": "
                    + player.getHandSize() + " face-down card(s)");
        }

        for (EliminatedPlayer eliminatedPlayer : eliminatedPlayers) {
            System.out.println(eliminatedPlayer.getPlayerName()
                    + ": eliminated by face-up "
                    + eliminatedPlayer.getKillingKitten().getType());

            if (eliminatedPlayer.getVisibleCards().isEmpty()) {
                System.out.println("Remaining face-up cards: none");
            } else {
                System.out.println("Remaining face-up cards:");
                for (Card card : eliminatedPlayer.getVisibleCards()) {
                    System.out.println("- " + card.getType());
                }
            }
        }
    }

    private int readInteger(String promptKey) {
        while (true) {
            System.out.print(messages.getString(promptKey));
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                return choice;
            }
            scanner.nextLine();
            System.out.println(messages.getString("input.integer.error"));
        }
    }
}
