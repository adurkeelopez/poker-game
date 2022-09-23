package org.example;

import java.util.ArrayList;


public class Hand {
    public ArrayList<Card> hand;
    public int handSize;
    private DeckOfCards deck;
    private String owner;


    public Hand(DeckOfCards deck) {
        this(deck, 1);
    }

    public Hand(DeckOfCards deck, String owner) {
        this(deck, 1, owner);
    }

    public Hand(DeckOfCards deck, int handSize) {
        this(deck, handSize, "Player");
    }

    public Hand(DeckOfCards deck, int handSize, String owner) {
        this.hand = new ArrayList<Card>();
        this.handSize = handSize;
        this.deck = deck;
        this.owner = owner;

        for (int i = 0; i < this.handSize; i++) {
            this.hand.add(this.deck.drawCard());
        }
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getHandSize() {
        return handSize;
    }

    public DeckOfCards getDeck() {
        return deck;
    }

    public String getOwner() {
        return owner;
    }

    public void printHand() {
        for (Card card : hand) {
            System.out.println(card.getCardName());
        }
    }

    public void printHandSentence() {
        StringBuilder sb = new StringBuilder("The ");
        sb.append(this.owner).append("'s hand is: ");
        for (int i = 0; i < hand.size(); i++) {
            sb.append(hand.get(i).getCardName());
            if (i != handSize - 1) {
                sb.append(", ");
            }
        }
        sb.insert(sb.lastIndexOf(", ") + 2, "and ");
        sb.append(".");

        System.out.println(sb);
    }
}

enum PokerHands {
    PIG,
    ONEPAIR,
    TWOPAIR,            //Rank by higher pair, then lower pair, then last card/kicker
    THREEOFAKIND,
    STRAIGHT,           //5 numbers in a row, any suits
    FLUSH,              //5 same suits
    FULLHOUSE,          //One Pair & Three of a Kind; rank by set, then pair
    FOUROFAKIND,
    STRAIGHTFLUSH,      //5 numbers in a row, all same suit
    ROYALSTRAIGHTFLUSH  //9 10 J Q K Straight Flush
}

class PokerHand extends Hand {
    PokerHand(DeckOfCards deck) {
        super(deck, 5);
    }

    PokerHand(DeckOfCards deck, String owner) {
        super(deck, 5, owner);
    }

    public void redrawCards(Character[] cardsToReplace)
    {
        int numToRedraw = 0;
        ArrayList<Card> removeCardList = new ArrayList<>();


        for (int i = 0; i < cardsToReplace.length; i++)
        {
            if (cardsToReplace[i] == 'X')
            {
                removeCardList.add(this.hand.get(i));
                numToRedraw++;
            }
        }

        this.hand.removeAll(removeCardList);

        for (int i = 0; i < numToRedraw; i++)
        {
            this.hand.add(this.getDeck().drawCard());
        }

        System.out.println(this.getOwner() + " redrew " + numToRedraw + " card(s).");
    }
}

