package domain.game;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import ui.GameView;


import java.util.*;


import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameControllerTest {
    @Test
    void startGame_EmptyPlayerList_DisplaysError() {
        // Arrange
        Deck deck = EasyMock.createMock(Deck.class);
        //is it too late to rename Game to GameModel for consistency? There should be an intelij feature
        Game mockModel = EasyMock.createMock(Game.class); //Game(deck);
        GameView mockView = EasyMock.createMock(GameView.class);

        // expect displayError to be called once with any non-null string
        // i would think here is where we set the error we expect to see
        String expctedError =  "player count must be between 2 and 4";

        //we fake passing in the empty list to the fake model resulting in this error
        mockModel.setupGame(List.of());
        expectLastCall().andThrow(new IllegalArgumentException("player count must be between 2 and 4"));
        replay(mockModel);
        // tell the mock view to expect displayError called once with anyt string
        mockView.displayError(anyString());
        expectLastCall().once();
        replay(mockView);

        GameController controller = new GameController(mockModel, mockView);

        // Act
        controller.startGame(List.of());

        // Assert
        verify(mockView); // verifies displayError was called, and displayGameReady was NOT
        verify(mockModel);
    }

    @Test
    void startGame_WithOnePlayer_DisplaysError() {
        //Deck deck = EasyMock.createMock(Deck.class);
        Game mockModel = EasyMock.createMock(Game.class);
        GameView mockView = EasyMock.createMock(GameView.class);

        // expect displayError to be called once with any non-null string
        // i would think here is where we set the error we expect to see
        String expctedError =  "player count must be between 2 and 4";

        //we fake passing in the empty list to the fake model resulting in this error
        mockModel.setupGame(List.of("cat1"));
        expectLastCall().andThrow(new IllegalArgumentException("player count must be between 2 and 4"));
        replay(mockModel);
        // tell the mock view to expect displayError called once with anyt string
        mockView.displayError(anyString());
        expectLastCall().once();
        replay(mockView);

        GameController controller = new GameController(mockModel, mockView);

        // Act
        controller.startGame(List.of("cat1"));

        // Assert
        verify(mockView); // verifies displayError was called, and displayGameReady was NOT
        verify(mockModel);
    }

 /*@Test
    void startGame_WithTwoPLayers_DisplaysError() {
        // Arrange
        Deck deck = new Deck(List.of());
        Game model = new Game(deck);
        GameView view = EasyMock.createMock(GameView.class);

        // expect displayGameReady to be called once with any non-null string
        view.displayGameReady();
        expectLastCall().once();
        replay(view);

        GameController controller = new GameController(model, view);

        // Act
        controller.startGame(List.of("Cat1", "Cat2"));

        // Assert
        verify(view); // verifies displayError was called, and displayGameReady was NOT
    }*/


}
