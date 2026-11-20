package ch.sakru.cards;

/** One immutable card face. IDs are stable within a card set, but are not image names. */
public final class Card {
    private final int id;
    private final String rank;
    private final String suit;

    public Card(int id, String rank, String suit) {
        if (id < 0 || rank == null || rank.isEmpty() || suit == null || suit.isEmpty()) {
            throw new IllegalArgumentException("A card needs a nonnegative ID, rank and suit");
        }
        this.id = id;
        this.rank = rank;
        this.suit = suit;
    }

    public int getId() {
        return id;
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }
}
