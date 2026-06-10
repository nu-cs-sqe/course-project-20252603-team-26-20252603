package domain.game;

import java.util.Collections;

public final class DeckFactory {
    private static final int STANDARD_DECK_SIZE = 38;

    private DeckFactory() {
    }

    public static Deck standardDeck() {
        Card placeholder = new Card(CardType.PLACEHOLDER_CARD);
        return new Deck(Collections.nCopies(STANDARD_DECK_SIZE, placeholder));
    }
}
