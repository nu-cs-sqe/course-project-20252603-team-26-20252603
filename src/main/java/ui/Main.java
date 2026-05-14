package ui;

import domain.game.Card;
import domain.game.CardType;

import java.util.List;

public class Main {
    // testing the GameView UI via main
    public static void main(String[] args) {
        GameView view = new GameView();

        // TC 1 "displayStartScreen_DisplayOnce_ShowsTitle" from BVA GameView
        view.displayStartScreen();

        // TC 2-7 promptPlayerNames
        var names = view.promptPlayerNames();
        System.out.println("Names of Players: " + names);

        // TC 8 "displayGameReady_DisplayOnce_ShowsGameReadyMessage" from BVA GameView
        view.displayGameReady();
        //Testing displayCardDrawn
        System.out.println("\n testing displayCardDrawn");

        // TC4: displayCardDrawn_OtherCard_ShowsOther from TakeCard BVA
        Card placeholderCard = new Card(CardType.PLACEHOLDER_CARD);
        view.displayCardDrawn(placeholderCard);

        Card defuseCard = new Card(CardType.DEFUSE);
        view.displayCardDrawn(defuseCard);

        Card explodingKittenCard = new Card(CardType.EXPLODING_KITTEN);
        view.displayCardDrawn(explodingKittenCard);

        // TC5: displayCardDrawn_OtherCard_ShowsOther from TakeCard BVA
        System.out.println("\n testing null card should throw an exception");

        System.out.println("\n testing displaySeeTheFutureCards with empty list");

        view.displaySeeTheFutureCards(List.of());

        try {
            view.displayCardDrawn(null);
        } catch (RuntimeException err) {
            System.out.println("exception occurred " + err.getMessage());
        }

    }
}