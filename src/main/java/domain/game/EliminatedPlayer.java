package domain.game;

public final class EliminatedPlayer {
    private final String playerName;
    private final Card killingKitten;
    private final int faceDownCardCount;

    public EliminatedPlayer(String playerName, Card killingKitten, int faceDownCardCount) {
        this.playerName = playerName;
        this.killingKitten = killingKitten;
        this.faceDownCardCount = faceDownCardCount;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Card getKillingKitten() {
        return killingKitten;
    }

    public int getFaceDownCardCount() {
        return faceDownCardCount;
    }
}
