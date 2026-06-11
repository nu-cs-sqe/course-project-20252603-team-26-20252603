package domain.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class DeckFactory {
    private static final int STANDARD_DECK_SIZE = 38;
    private static final int EXPLODING_KITTEN_COUNT = 3;
    private static final int DEFUSE_COUNT = 5;
    private static final int ATTACK_COUNT = 3;
    private static final int SKIP_COUNT = 3;
    private static final int SEE_THE_FUTURE_COUNT = 4;
    private static final int SHUFFLE_COUNT = 4;
    private static final int CAT_CARD_COUNT = 4;
    private static final int INVENTED_CARD_COUNT = 2;

    private DeckFactory() {
    }

    public static Deck standardDeck() {
        List<Card> cards = new ArrayList<>(STANDARD_DECK_SIZE);
        Card explodingKitten = new Card(CardType.EXPLODING_KITTEN);
        Card defuse = new Card(CardType.DEFUSE);
        Card attack = new Card(CardType.ATTACK);
        Card skip = new Card(CardType.SKIP);
        Card seeTheFuture = new Card(CardType.SEE_THE_FUTURE);
        Card shuffle = new Card(CardType.SHUFFLE);
        cards.addAll(Collections.nCopies(EXPLODING_KITTEN_COUNT, explodingKitten));
        cards.addAll(Collections.nCopies(DEFUSE_COUNT, defuse));
        cards.addAll(Collections.nCopies(ATTACK_COUNT, attack));
        cards.addAll(Collections.nCopies(SKIP_COUNT, skip));
        cards.addAll(Collections.nCopies(SEE_THE_FUTURE_COUNT, seeTheFuture));
        cards.addAll(Collections.nCopies(SHUFFLE_COUNT, shuffle));
        cards.addAll(Collections.nCopies(
                CAT_CARD_COUNT, new Card(CardType.BEARD_CAT)));
        cards.addAll(Collections.nCopies(
                CAT_CARD_COUNT, new Card(CardType.HAIRY_POTATO_CAT)));
        cards.addAll(Collections.nCopies(
                CAT_CARD_COUNT, new Card(CardType.TACOCAT)));
        cards.addAll(Collections.nCopies(
                CAT_CARD_COUNT, new Card(CardType.RAINBOW_RALPHING_CAT)));
        return new Deck(cards);
    }

    public static Deck completeDeck() {
        List<Card> cards = new ArrayList<>(standardDeck().snapshot());
        addCards(cards, CardType.SUPER_SKIP, INVENTED_CARD_COUNT);
        addCards(cards, CardType.REVERSE, INVENTED_CARD_COUNT);
        addCards(cards, CardType.BURY, INVENTED_CARD_COUNT);
        addCards(cards, CardType.SWAP_TOP_AND_BOTTOM, INVENTED_CARD_COUNT);
        addCards(cards, CardType.DRAW_FROM_BOTTOM, INVENTED_CARD_COUNT);
        addCards(cards, CardType.TARGETED_ATTACK, INVENTED_CARD_COUNT);
        return new Deck(cards);
    }

    private static void addCards(List<Card> cards, CardType type, int count) {
        cards.addAll(Collections.nCopies(count, new Card(type)));
    }
}
