package domain.game;

public final class SwapTopAndBottomController {
    private static final String INVALID_INDEX_MESSAGE = "cardIndex is out of bounds";
    private static final String NOT_SWAP_CARD_MESSAGE =
            "selected card is not a swap top and bottom card";

    public void play(Game game, int cardIndex) {
        Player currentPlayer = game.getCurrentPlayer();

        if (cardIndex < 0 || cardIndex >= currentPlayer.getHandSize()) {
            throw new IllegalArgumentException(INVALID_INDEX_MESSAGE);
        }

        Card swapCard = currentPlayer.getHandSnapshot().get(cardIndex);
        if (swapCard.getType() != CardType.SWAP_TOP_AND_BOTTOM) {
            throw new IllegalArgumentException(NOT_SWAP_CARD_MESSAGE);
        }

        currentPlayer.removeCard(cardIndex);
        game.getDiscardPile().add(swapCard);
        game.getDrawPile().swapTopAndBottom();
    }
}