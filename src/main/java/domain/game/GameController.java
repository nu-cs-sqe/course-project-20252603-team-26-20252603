package domain.game;

import ui.GameView;

import java.util.List;

public class GameController {
    //model object
    private Game model;

    //view object
    private GameView view;

    //constructor
    public GameController(Game model, GameView view){
        this.model = model;
        this.view = view;
    }

    public void startGame(List<String> playerNames){
       try{
          model.setupGame(playerNames);
          view.displayGameReady();
       }catch (IllegalArgumentException e){
           view.displayError(e.getMessage());
       }
    }
}
