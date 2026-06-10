package domain.game;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import ui.GameView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameController {
    private static final String SKIP_PLAYED = "Skip played. Your turn ends without drawing a card.";
    private static final String UNPLAYABLE_CARD =
            "Card cannot be played during a normal turn.";
    private static final String INVALID_CARD_INDEX = "cardIndex is out of bounds";
    private static final String SUPER_SKIP_PLAYED = "Super Skip played! All forced turns cleared. Turn ended.";
    private static final String REVERSE_PLAYED = "Reverse played! Direction changed. Turn ended.";

    // Open to discussion here  
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Controller coordinates the injected game model as shared application state.")
    private Game model;

    private GameView view;

    public GameController(Game model, GameView view) {
        this.model = model;
        this.view = view;
    }

    public void startGame(List<String> playerNames) {
        try {
            model.setupGame(playerNames);
            view.displayGameReady();
        } catch (IllegalArgumentException e) {
            view.displayError(e.getMessage());
        }
    }

    public void runGame() {
        view.displayStartScreen();
        while (model.getPlayers().isEmpty()) {
            startGame(view.promptPlayerNames());
        }
        while (!model.isWon()) {
            playInteractiveTurn();
        }
    }

    public void startTurn() {
        view.displayPublicPlayerState(model.getPlayers(), model.getEliminatedPlayers());

        Player currentPlayer = model.getCurrentPlayer();
        view.displayHand(currentPlayer.getName(), currentPlayer.getHandSnapshot());
    }

    public void completeTurn(List<Integer> cardIndexes) {
        startTurn();
        for (int cardIndex : cardIndexes) {
            if (playSelectedCard(cardIndex)) {
                return;
            }
        }
        takeCard();
    }

    void playInteractiveTurn() {
        startTurn();
        while (true) {
            int cardChoice = view.promptCardChoice();
            if (cardChoice == 0) {
                takeCard();
                return;
            }
            if (playSelectedCard(cardChoice - 1)) {
                return;
            }
            Player currentPlayer = model.getCurrentPlayer();
            view.displayHand(currentPlayer.getName(), currentPlayer.getHandSnapshot());
        }
    }

    boolean playSelectedCard(int cardIndex) {
        Player currentPlayer = model.getCurrentPlayer();
        if (cardIndex < 0 || cardIndex >= currentPlayer.getHandSize()) {
            view.displayError(INVALID_CARD_INDEX);
            return false;
        }

        Card selectedCard = currentPlayer.getHandSnapshot().get(cardIndex);
        if (selectedCard.getType() == CardType.SKIP) {
            return playSkip(cardIndex);
        }
        if (selectedCard.getType() == CardType.SUPER_SKIP) {
            return playSuperSkip(cardIndex);
        }
        if (selectedCard.getType() == CardType.REVERSE) {
            return playReverse(cardIndex);
        }
        if (isCatCard(selectedCard.getType())) {
            playCatPair(currentPlayer, cardIndex);
            return false;
        }
        if (selectedCard.getType() == CardType.SEE_THE_FUTURE) {
            SeeFutureCardController seeFutureController =
                    new SeeFutureCardController(model.getDrawPile(), model.getDiscardPile());
            view.displaySeeTheFutureCards(seeFutureController.play(currentPlayer, cardIndex));
            return false;
        }
        if (selectedCard.getType() == CardType.SHUFFLE) {
            new ShuffleCardController().play(model, cardIndex);
            return false;
        }
        if (selectedCard.getType() == CardType.BURY) {
            new BuryCardController().play(model, cardIndex);
            return false;
        }
        if (selectedCard.getType() == CardType.ATTACK) {
            AttackCardController attackCardController =
                    new AttackCardController(model.getDrawPile(), model.getDiscardPile());
            attackCardController.play(currentPlayer, cardIndex);
            model.applyAttack();
            return true;
        }
        if (selectedCard.getType() == CardType.TARGETED_ATTACK) {
            playTargetedAttack(cardIndex);
            return true;
        }
        if (selectedCard.getType() == CardType.DRAW_FROM_BOTTOM) {
            playDrawFromBottom(currentPlayer, cardIndex);
            return true;
        }
        if (selectedCard.getType() == CardType.SWAP_TOP_AND_BOTTOM) {
            new SwapTopAndBottomController().play(model, cardIndex);
            return false;
        }

        view.displayError(UNPLAYABLE_CARD);
        return false;
    }

    private void playCatPair(Player currentPlayer, int firstCardIndex) {
        int secondCardIndex = view.promptSecondCardChoice() - 1;
        List<Player> eligibleTargets = new ArrayList<>(model.getPlayers());
        eligibleTargets.remove(currentPlayer);
        eligibleTargets.removeIf(player -> player.getHandSize() == 0);
        Player target = view.promptTargetPlayer(eligibleTargets);

        CatCardController catCardController =
                new CatCardController(model.getDiscardPile(), new Random());
        Card stolenCard = catCardController.play(
                currentPlayer, target, firstCardIndex, secondCardIndex);
        view.displayCardStolen(stolenCard);
    }

    private boolean isCatCard(CardType type) {
        return type == CardType.BEARD_CAT
                || type == CardType.HAIRY_POTATO_CAT
                || type == CardType.TACOCAT
                || type == CardType.RAINBOW_RALPHING_CAT;
    }

    private void playDrawFromBottom(Player currentPlayer, int cardIndex) {
        DrawFromBottomCardController drawFromBottomCardController =
                new DrawFromBottomCardController(model.getDrawPile(), model.getDiscardPile());
        Card drawnCard = drawFromBottomCardController.play(currentPlayer, cardIndex);
        if (drawnCard.getType() == CardType.EXPLODING_KITTEN) {
            handleExplodingKitten(currentPlayer, drawnCard);
            return;
        }
        currentPlayer.addCard(drawnCard);
        view.displayCardDrawn(drawnCard);
        model.advanceTurn();
    }

    public Card takeCard() {
        Player currentPlayer = model.getCurrentPlayer();
        Card drawnCard = model.getDrawPile().draw();
        if (drawnCard.getType() == CardType.EXPLODING_KITTEN) {
            handleExplodingKitten(currentPlayer, drawnCard);
            return drawnCard;
        }
        currentPlayer.addCard(drawnCard);
        view.displayCardDrawn(drawnCard);
        model.advanceTurn();
        return drawnCard;
    }

    private void handleExplodingKitten(Player currentPlayer, Card drawnCard) {
        view.displayCardDrawn(drawnCard);
        ExplodingKittenCardController explodingKittenController =
                new ExplodingKittenCardController(model.getDrawPile(), model.getDiscardPile());
        boolean defused = explodingKittenController.play(currentPlayer, drawnCard);
        if (defused) {
            model.advanceTurn();
            return;
        }
        model.eliminatePlayer(currentPlayer, drawnCard);
        if (model.isWon()) {
            view.displayGameOver(model.getPlayers().get(0).getName());
        }
    }

    public boolean playSkip(int cardIndex) {
        try {
            Player currentPlayer = model.getCurrentPlayer();

            SkipCardController skipCardController = new SkipCardController(model.getDiscardPile());

            skipCardController.play(currentPlayer, cardIndex);
            view.displayMessage(SKIP_PLAYED);
            model.advanceTurn();
            return true;
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            view.displayError(e.getMessage());
            return false;
        }
    }

    public boolean playSuperSkip(int cardIndex) {
        try {
            Player currentPlayer = model.getCurrentPlayer();
            SuperSkipCardController superSkipController = new SuperSkipCardController(model.getDiscardPile());

            boolean played = superSkipController.play(currentPlayer, cardIndex);
            if (played) {
                model.endTurnClearingForced();
                view.displayMessage(SUPER_SKIP_PLAYED);
                return true;
            }
            return false;
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            view.displayError(e.getMessage());
            return false;
        }
    }

    public boolean playReverse(int cardIndex) {
        try {
            Player currentPlayer = model.getCurrentPlayer();
            ReverseCardController reverseController = new ReverseCardController(model.getDiscardPile());

            boolean played = reverseController.play(currentPlayer, cardIndex);
            if (played) {
                model.reverseDirection();
                model.advanceTurnWithDirection();
                view.displayMessage(REVERSE_PLAYED);
                return true;
            }
            return false;
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            view.displayError(e.getMessage());
            return false;
        }
    }


    public void playTargetedAttack(int cardIndex) {
        try {
            Player currentPlayer = model.getCurrentPlayer();
            if (cardIndex < 0 || cardIndex >= currentPlayer.getHandSize()) {
                throw new IllegalArgumentException(INVALID_CARD_INDEX);
            }
            if (currentPlayer.getHandSnapshot().get(cardIndex).getType()
                    != CardType.TARGETED_ATTACK) {
                throw new IllegalArgumentException(UNPLAYABLE_CARD);
            }

            List<Player> eligibleTargets = new java.util.ArrayList<>(model.getPlayers());
            eligibleTargets.remove(currentPlayer);
            Player target = view.promptTargetPlayer(eligibleTargets);
            if (!eligibleTargets.contains(target)) {
                throw new IllegalArgumentException("target must be another active player");
            }

            TargetedAttackCardController targetedAttackController =
                    new TargetedAttackCardController(model.getDiscardPile());
            targetedAttackController.play(currentPlayer, cardIndex);
            model.applyTargetedAttack(target);
        } catch (IllegalArgumentException e) {
            view.displayError(e.getMessage());
        }
    }
}
