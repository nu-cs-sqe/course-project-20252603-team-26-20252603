package ui;

public class Main {
    // testing the GameView UI via main
    public static void main(String[] args){
        GameView view = new GameView();

        // TC 1 "displayStartScreen_DisplayOnce_ShowsTitle" from BVA GameView
        view.displayStartScreen();

        // TC 2-7 promptPlayerNames
        var names = view.promptPlayerNames();
        System.out.println("Names of Players: " + names);

        // TC 8 "displayGameReady_DisplayOnce_ShowsGameReadyMessage" from BVA GameView
        view.displayGameReady();
    }
}