package domain.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class DeckFactory {
    private static final int STANDARD_DECK_SIZE = 38;
    private static final int EXPLODING_KITTEN_COUNT = 3;
    private static final int DEFUSE_COUNT = 5;

    private DeckFactory() {
    }

    public static Deck standardDeck() {
        List<Card> cards = new ArrayList<>(STANDARD_DECK_SIZE);
        Card explodingKitten = new Card(CardType.EXPLODING_KITTEN);
        Card defuse = new Card(CardType.DEFUSE);
        Card placeholder = new Card(CardType.PLACEHOLDER_CARD);
        cards.addAll(Collections.nCopies(EXPLODING_KITTEN_COUNT, explodingKitten));
        cards.addAll(Collections.nCopies(DEFUSE_COUNT, defuse));
        cards.addAll(Collections.nCopies(
                STANDARD_DECK_SIZE - EXPLODING_KITTEN_COUNT - DEFUSE_COUNT,
                placeholder));
        return new Deck(cards);
    }
}
