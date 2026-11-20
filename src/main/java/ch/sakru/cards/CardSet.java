package ch.sakru.cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** A fixed list of card faces, independent of game rules and artwork. */
public final class CardSet {
    private static final List<String> FRENCH_SUITS = Arrays.asList("♠", "♥", "♦", "♣");
    private static final List<String> FRENCH_RANKS = Arrays.asList(
            "A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K");

    private final List<Card> cards;

    private CardSet(List<Card> cards) {
        this.cards = Collections.unmodifiableList(new ArrayList<>(cards));
    }

    /** Define another deck, e.g. Italian 40 or Swiss 36, without changing this library. */
    public static CardSet of(List<Card> cards) {
        if (cards == null || cards.isEmpty()) {
            throw new IllegalArgumentException("A card set must contain cards");
        }
        Set<Integer> ids = new HashSet<>();
        Set<String> faces = new HashSet<>();
        for (Card card : cards) {
            if (card == null || !ids.add(card.getId())
                    || !faces.add(card.getSuit() + "\u0000" + card.getRank())) {
                throw new IllegalArgumentException("Null or duplicate card in set");
            }
        }
        return new CardSet(cards);
    }

    /** IDs retain their old 0..51 values for existing Pisti data and tests. */
    public static CardSet french52() {
        List<Card> faces = new ArrayList<>();
        int id = 0;
        for (String suit : FRENCH_SUITS) {
            for (String rank : FRENCH_RANKS) {
                faces.add(new Card(id++, rank, suit));
            }
        }
        return of(faces);
    }

    /** Retains each face's ID when taking a subset. */
    public CardSet onlyRanks(String... ranks) {
        if (ranks == null || ranks.length == 0) {
            throw new IllegalArgumentException("Select at least one rank");
        }
        Set<String> selected = new HashSet<>(Arrays.asList(ranks));
        if (selected.contains(null) || selected.size() != ranks.length) {
            throw new IllegalArgumentException("Null or duplicate rank");
        }
        List<Card> chosen = new ArrayList<>();
        Set<String> found = new HashSet<>();
        for (Card card : cards) {
            if (selected.contains(card.getRank())) {
                chosen.add(card);
                found.add(card.getRank());
            }
        }
        if (!found.equals(selected)) {
            throw new IllegalArgumentException("Unknown rank in card set");
        }
        return of(chosen);
    }

    public int size() {
        return cards.size();
    }

    List<Card> newCards() {
        return new ArrayList<>(cards);
    }
}
