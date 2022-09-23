package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class DeckOfCards {
    private static DeckOfCards deck;
    private ArrayList<Card> cards;
    private ArrayList<Card> pulledCards;
    private Random rand = new Random();

    private DeckOfCards() {
        cards = new ArrayList<Card>();
        pulledCards = new ArrayList<Card>();


        Suit[] suits = {Suit.SPADES, Suit.HEARTS, Suit.CLUBS, Suit.DIAMONDS};
        for (Suit suit : suits) {
            for (int i = 1; i <= 13; i++) {
                cards.add(new Card(suit, i));
            }
        }
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public ArrayList<Card> getPulledCards() {
        return pulledCards;
    }

    public static DeckOfCards getInstance() {
        if (deck == null) {
            deck = new DeckOfCards();
        }

        return deck;
    }

    public Card drawCard() {
        if (this.cards.isEmpty()) {
            return null;
        }
        Card card = cards.remove(rand.nextInt(cards.size()));
        if (card != null) {
            pulledCards.add(card);
        }
        return card;
    }

    public void shuffle() {
        Collections.shuffle(this.cards, new Random());
    }

    public void print() {
        for (Card card : cards) {
            System.out.println(card.getCardName());
        }
    }

    public void reset() {
        cards.clear();
        pulledCards.clear();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}

enum Suit {
    SPADES,
    CLUBS,
    HEARTS,
    DIAMONDS
}

class Card {
    private Suit suit;
    private int cardNum;
    private String cardName;
    private String faceName;
    private boolean isFaceCard;
    private int cardValue;

    public Card(Suit s, int num) {
        this.suit = s;
        this.cardNum = num;
        this.cardName = "";
        this.faceName = "";
        this.isFaceCard = false;
        setCardStats(this);
    }

    public Suit getSuit() {
        return suit;
    }

    public int getCardNum() {
        return cardNum;
    }

    public String getCardName() {
        return cardName;
    }

    public String getFaceName() {
        return faceName;
    }

    public boolean getIsFaceCard() {
        return isFaceCard;
    }

    public int getCardValue() {
        return cardValue;
    }

    private void setCardStats(Card card) {
        switch (card.getCardNum()) {
            case 1:
                this.cardName += "Ace";
                this.faceName = "Ace";
                this.isFaceCard = true;
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                this.cardName += card.cardNum;
                break;
            case 11:
                this.cardName += "Jack";
                this.faceName = "Jack";
                break;
            case 12:
                this.cardName += "Queen";
                this.faceName = "Queen";
                break;
            case 13:
                this.cardName += "King";
                this.faceName = "King";
                break;
        }

        if (this.cardNum > 10) {
            this.cardValue = 10;
            this.isFaceCard = true;
        } else {
            this.cardValue = this.cardNum;
        }

        this.cardName += " of ";

        switch (this.getSuit()) {
            case SPADES:
                this.cardName += "Spades";
                break;
            case CLUBS:
                this.cardName += "Clubs";
                break;
            case HEARTS:
                this.cardName += "Hearts";
                break;
            case DIAMONDS:
                this.cardName += "Diamonds";
                break;
        }
    }
}