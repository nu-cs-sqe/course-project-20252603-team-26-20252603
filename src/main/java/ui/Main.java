package ui;

import domain.game.Card;
import domain.game.CardType;

import java.util.List;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main {
    // testing the GameView UI via main
    public static void main(String[] args) {
        GameView view = new GameView();
        ResourceBundle messages = ResourceBundle.getBundle("message", Locale.getDefault());

        // TC 1 "displayStartScreen_DisplayOnce_ShowsTitle" from BVA GameView
        view.displayStartScreen();

        // TC 2-7 promptPlayerNames
        var names = view.promptPlayerNames();
        String namesMessage = MessageFormat.format(messages.getString("test.names.header"), names);
        System.out.println(namesMessage);

        // TC 8 "displayGameReady_DisplayOnce_ShowsGameReadyMessage" from BVA GameView
        view.displayGameReady();
        //Testing displayCardDrawn
        System.out.println(messages.getString("test.card.header"));

        // TC4: displayCardDrawn_OtherCard_ShowsOther from TakeCard BVA
        Card placeholderCard = new Card(CardType.PLACEHOLDER_CARD);
        view.displayCardDrawn(placeholderCard);

        Card defuseCard = new Card(CardType.DEFUSE);
        view.displayCardDrawn(defuseCard);

        Card explodingKittenCard = new Card(CardType.EXPLODING_KITTEN);
        view.displayCardDrawn(explodingKittenCard);

        // TC5: displayCardDrawn_OtherCard_ShowsOther from TakeCard BVA
        System.out.println(messages.getString("test.null.header"));
        System.out.println(messages.getString("test.see.future.header"));
        view.displaySeeTheFutureCards(List.of());

        try {
            view.displayCardDrawn(null);
        } catch (RuntimeException err) {
            String errorMessage = MessageFormat.format(messages.getString("test.exception.occurred"), err.getMessage());
            System.out.println(errorMessage);
        }

    }
}