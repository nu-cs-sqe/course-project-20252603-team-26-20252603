package domain.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Deck {
    private static final String CARDS_REQUIRED_MESSAGE = "cards must not be null";
    private static final String RANDOM_REQUIRED_MESSAGE = "random must not be null";
    private static final String CARD_REQUIRED_MESSAGE = "card must not be null";
    private static final String CARD_TYPE_REQUIRED_MESSAGE = "card type must not be null";
    private static final String EMPTY_DECK_MESSAGE = "cannot draw from an empty deck";

    private final List<Card> cards;
    private final Random random;

    public Deck(List<Card> cards) {
        this(cards, new Random());
    }

    Deck(List<Card> cards, Random random) {
        if (cards == null) {
            throw new IllegalArgumentException(CARDS_REQUIRED_MESSAGE);
        }
        this.cards = new ArrayList<>(cards);
        this.random = Objects.requireNonNull(random, RANDOM_REQUIRED_MESSAGE);
    }

    public void shuffle() {
        for (int index = cards.size() - 1; index > 0; index--) {
            int indexToSwap = random.nextInt(index + 1);
            Card currentCard = cards.get(index);
            cards.set(index, cards.get(indexToSwap));
            cards.set(indexToSwap, currentCard);
        }
    }

    public Card draw() {
        if (cards.isEmpty()) {
            throw new IllegalStateException(EMPTY_DECK_MESSAGE);
        }
        return cards.remove(cards.size() - 1);
    }

    public void addCard(Card card) {
        if (card == null) {
            throw new IllegalArgumentException(CARD_REQUIRED_MESSAGE);
        }
        cards.add(card);
    }

    public List<Card> removeCardsByType(CardType type) {
        if (type == null) {
            throw new IllegalArgumentException(CARD_TYPE_REQUIRED_MESSAGE);
        }

        List<Card> removedCards = new ArrayList<>();
        Iterator<Card> iterator = cards.iterator();
        while (iterator.hasNext()) {
            Card card = iterator.next();
            if (card.getType() == type) {
                removedCards.add(card);
                iterator.remove();
            }
        }
        return removedCards;
    }

    int size() {
        return cards.size();
    }

    List<Card> snapshot() {
        return List.copyOf(cards);
    }

    long countCardsOfType(CardType type) {
        Objects.requireNonNull(type, CARD_TYPE_REQUIRED_MESSAGE);
        return cards.stream().filter(card -> card.getType() == type).count();
    }
}
