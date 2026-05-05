package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameView {
    private Scanner scanner;
    public GameView() {
        this.scanner = new Scanner(System.in);
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
            System.out.print("Enter name of player " + (i+1) + ": ");
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
}