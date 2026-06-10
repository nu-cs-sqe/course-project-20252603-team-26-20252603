package domain.game;

import java.util.List;

public final class EliminatedPlayer {
    private final String playerName;
    private final Card killingKitten;
    private final List<Card> visibleCards;

    EliminatedPlayer(String playerName, Card killingKitten, List<Card> visibleCards) {
        this.playerName = playerName;
        this.killingKitten = killingKitten;
        this.visibleCards = List.copyOf(visibleCards);
    }

    public String getPlayerName() {
        return playerName;
    }

    public Card getKillingKitten() {
        return killingKitten;
    }

    public List<Card> getVisibleCards() {
        return visibleCards;
    }

    public int getVisibleCardCount() {
        return visibleCards.size();
    }
}
