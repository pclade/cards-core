package ch.sakru.cards;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CardSetTest {
    @Test
    public void standardDeckKeepsPistiCardFacesAndIds() {
        Deck deck = new Deck(CardSet.french52());
        deck.createCards();

        assertEquals(52, deck.getDecSize());
        assertFace(deck.getCardDeck().get(0), 0, "A", "♠");
        assertFace(deck.getCardDeck().get(35), 35, "T", "♦");
        assertFace(deck.getCardDeck().get(51), 51, "K", "♣");

        deck.createCards();
        assertEquals(52, deck.getCardDeck().size());
    }

    @Test
    public void twentyFourCardSelectionRetainsTheOriginalImageIds() {
        CardSet sixtySixCards = CardSet.french52().onlyRanks("9", "J", "Q", "K", "T", "A");
        Deck deck = new Deck(sixtySixCards);
        deck.createCards();

        assertEquals(24, deck.getDecSize());
        Set<Integer> ids = new HashSet<>();
        for (Card card : deck.getCardDeck()) {
            assertTrue(ids.add(card.getId()));
        }
        assertFace(deck.getCardDeck().get(0), 0, "A", "♠");
        assertFace(deck.getCardDeck().get(1), 8, "9", "♠");
        assertFace(deck.getCardDeck().get(6), 13, "A", "♥");
        assertFace(deck.getCardDeck().get(23), 51, "K", "♣");

        for (int i = 0; i < 24; i++) {
            deck.dealTopCardOnDec();
        }
        assertTrue(deck.isEmpty());
        deck.createCards();
        assertEquals(24, deck.getCardDeck().size());
    }

    @Test
    public void shuffleCanBeReproducedForSimulation() {
        Deck first = new Deck();
        Deck second = new Deck();
        first.createCards();
        second.createCards();
        first.shuffleDeck(new Random(1234));
        second.shuffleDeck(new Random(1234));

        for (int i = 0; i < first.getDecSize(); i++) {
            assertEquals(first.dealTopCardOnDec().getId(),
                    second.dealTopCardOnDec().getId());
        }
    }

    @Test
    public void customItalianSetIsIndependentOfFrenchRanksAndArtwork() {
        String[] suits = {"Bastoni", "Spade", "Denari", "Coppe"};
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "Fante", "Cavallo", "Re"};
        ArrayList<Card> faces = new ArrayList<>();
        for (String suit : suits) {
            for (String rank : ranks) {
                faces.add(new Card(100 + faces.size(), rank, suit));
            }
        }
        CardSet italian = CardSet.of(faces);
        Deck deck = new Deck(italian.onlyRanks("A", "3", "Fante"));
        deck.createCards();

        assertEquals(40, italian.size());
        assertEquals(12, deck.getDecSize());
        assertEquals(100, deck.getCardDeck().get(0).getId());
        assertEquals("Bastoni", deck.getCardDeck().get(0).getSuit());
        assertEquals("Fante", deck.getCardDeck().get(2).getRank());
        assertEquals(110, deck.getCardDeck().get(3).getId());
    }

    @Test
    public void rejectsDuplicateFacesAndDoesNotExposeDeckForMutation() {
        boolean rejected = false;
        try {
            CardSet.of(Arrays.asList(new Card(4, "A", "♠"), new Card(5, "A", "♠")));
        } catch (IllegalArgumentException expected) {
            rejected = true;
        }
        assertTrue(rejected);

        Deck deck = new Deck();
        deck.createCards();
        boolean immutableView = false;
        try {
            deck.getCardDeck().clear();
        } catch (UnsupportedOperationException expected) {
            immutableView = true;
        }
        assertTrue(immutableView);
        assertEquals(52, deck.getCardDeck().size());
    }

    private static void assertFace(Card card, int id, String rank, String suit) {
        assertEquals(id, card.getId());
        assertEquals(rank, card.getRank());
        assertEquals(suit, card.getSuit());
    }
}
