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
        Deck deck = new Deck(List.of());
        Game model = new Game(deck);
        GameView view = EasyMock.createMock(GameView.class);

        // expect displayError to be called once with any non-null string
        view.displayError(anyString());
        expectLastCall().once();
        replay(view);

        GameController controller = new GameController(model, view);

        // Act
        controller.startGame(List.of());

        // Assert
        verify(view); // verifies displayError was called, and displayGameReady was NOT
    }


}
