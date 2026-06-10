package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Locale;
import java.util.ResourceBundle;
import java.text.MessageFormat;

import domain.game.Card;
import domain.game.EliminatedPlayer;
import domain.game.Player;

public class GameView {
    private Scanner scanner;
    private ResourceBundle messages;

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
}
