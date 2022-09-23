package org.example;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DeckOfCards deck = DeckOfCards.getInstance();
        deck.shuffle();
        Scanner scan = new Scanner(System.in);

        PokerHand hand1 = new PokerHand(deck, "Player");
        System.out.println();
        hand1.printHandSentence();

        PokerHand hand2 = new PokerHand(deck, "Dealer");
        hand2.redrawCards(chooseCardsToRedraw(hand2));

        hand1.redrawCards(chooseCardsToRedraw(hand1, scan));
        System.out.println();

        hand1.printHandSentence();
        hand2.printHandSentence();

        System.out.println(comparePokerHands(hand1, hand2));

        deck.reset();
    }

    public static String comparePokerHands(PokerHand hand1, PokerHand hand2) {
        Suit[] hand1Suit = new Suit[hand1.handSize];
        Integer[] hand1Values = new Integer[hand1.handSize];
        Suit[] hand2Suit = new Suit[hand2.handSize];
        Integer[] hand2Values = new Integer[hand2.handSize];

        Integer[] hand1SuitNum = new Integer[]{0, 0, 0, 0};
        Integer[] hand2SuitNum = new Integer[]{0, 0, 0, 0};
        Integer[] hand1ValueNum = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        Integer[] hand2ValueNum = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        PokerHands hand1Hand;
        PokerHands hand2Hand;

        checkPokerHandSuitsAndValues(hand1, hand1Suit, hand1Values, hand1SuitNum, hand1ValueNum);
        hand1Hand = determinePokerHandHand(hand1Values, hand1SuitNum, hand1ValueNum);

        checkPokerHandSuitsAndValues(hand2, hand2Suit, hand2Values, hand2SuitNum, hand2ValueNum);
        hand2Hand = determinePokerHandHand(hand2Values, hand2SuitNum, hand2ValueNum);

        int hand1HighVal = getHighestPokerHandHandValue(hand1Values, hand1ValueNum, hand1Hand);
        int hand1SecHighVal = getSecondHighestPokerHandHandValue(hand1ValueNum, hand1Hand);
        int hand2HighVal = getHighestPokerHandHandValue(hand2Values, hand2ValueNum, hand2Hand);
        int hand2SecHighVal = getSecondHighestPokerHandHandValue(hand2ValueNum, hand2Hand);

        if (hand1Hand.equals(PokerHands.ROYALSTRAIGHTFLUSH) || hand2Hand.equals(PokerHands.ROYALSTRAIGHTFLUSH)) {
            if (hand1Hand.equals(PokerHands.ROYALSTRAIGHTFLUSH) && !(hand2Hand.equals(PokerHands.ROYALSTRAIGHTFLUSH))) {
                return hand1.getOwner() + " wins with a Royal Straight Flush!";
            } else if (!(hand1Hand.equals(PokerHands.ROYALSTRAIGHTFLUSH))) {
                return hand2.getOwner() + " wins with a Royal Straight Flush!";
            } else {
                return "DRAW!" + hand1.getOwner() + " and " + hand2.getOwner() + "both have Royal Straight" +
                        "Flushes";
            }
        } else if (hand1Hand.equals(PokerHands.STRAIGHTFLUSH) || hand2Hand.equals(PokerHands.STRAIGHTFLUSH)) {
            if (hand1Hand.equals(PokerHands.STRAIGHTFLUSH) && !(hand2Hand.equals(PokerHands.STRAIGHTFLUSH))) {
                return hand1.getOwner() + " wins with a Straight Flush!";
            } else if (!(hand1Hand.equals(PokerHands.STRAIGHTFLUSH))) {
                return hand2.getOwner() + " wins with a Straight Flush!";
            } else {
                if (hand1HighVal > hand2HighVal) {
                    return hand1.getOwner() + " wins with a Straight Flush!";
                } else if (hand2HighVal > hand1HighVal) {
                    return hand2.getOwner() + " wins with a Straight Flush!";
                } else {
                    return "DRAW!" + hand1.getOwner() + " and " + hand2.getOwner() + "both have Straight" +
                            " Flushes with the same highest card.";
                }
            }
        } else if (hand1Hand.equals(PokerHands.FOUROFAKIND) || hand2Hand.equals(PokerHands.FOUROFAKIND)) {
            if (hand1Hand.equals(PokerHands.FOUROFAKIND) && !(hand2Hand.equals(PokerHands.FOUROFAKIND))) {
                return hand1.getOwner() + " wins with Four of a Kind!";
            } else if (!(hand1Hand.equals(PokerHands.FOUROFAKIND))) {
                return hand2.getOwner() + " wins with Four of a Kind!";
            } else {
                if (hand1HighVal > hand2HighVal) {
                    return hand1.getOwner() + " wins with Four of a Kind!";
                } else if (hand2HighVal > hand1HighVal) {
                    return hand2.getOwner() + " wins with Four of a Kind!";
                }
            }
        } else if (hand1Hand.equals(PokerHands.FULLHOUSE) || hand2Hand.equals(PokerHands.FULLHOUSE)) {
            if (hand1Hand.equals(PokerHands.FULLHOUSE) && !(hand2Hand.equals(PokerHands.FULLHOUSE))) {
                return hand1.getOwner() + " wins with a Full House!";
            } else if (!(hand1Hand.equals(PokerHands.FULLHOUSE))) {
                return hand2.getOwner() + " wins with a Full House!";
            } else {
                if (hand1HighVal > hand2HighVal) {
                    return hand1.getOwner() + " wins with a Full House!";
                } else if (hand2HighVal > hand1HighVal) {
                    return hand2.getOwner() + " wins with a Full House!";
                }
            }
        } else if (hand1Hand.equals(PokerHands.FLUSH) || hand2Hand.equals(PokerHands.FLUSH)) {
            if (hand1Hand.equals(PokerHands.FLUSH) && !(hand2Hand.equals(PokerHands.FLUSH))) {
                return hand1.getOwner() + " wins with a Flush!";
            } else if (!(hand1Hand.equals(PokerHands.FLUSH))) {
                return hand2.getOwner() + " wins with a Flush!";
            } else {
                if (hand1HighVal > hand2HighVal) {
                    return hand1.getOwner() + " wins with a Flush!";
                } else if (hand2HighVal > hand1HighVal) {
                    return hand2.getOwner() + " wins with a Flush!";
                } else if (hand1SecHighVal > hand2SecHighVal) {
                    return hand1.getOwner() + " wins with a Flush!";
                } else if (hand2SecHighVal > hand1SecHighVal) {
                    return hand2.getOwner() + " wins with a Flush!";
                } else {
                    return "DRAW!" + hand1.getOwner() + " and " + hand2.getOwner() + "both have" +
                            " Flushes with the same highest cards.";
                }
            }
        } else if (hand1Hand.equals(PokerHands.STRAIGHT) || hand2Hand.equals(PokerHands.STRAIGHT)) {
            if (hand1Hand.equals(PokerHands.STRAIGHT) && !(hand2Hand.equals(PokerHands.STRAIGHT))) {
                return hand1.getOwner() + " wins with a Straight!";
            } else if (!(hand1Hand.equals(PokerHands.STRAIGHT))) {
                return hand2.getOwner() + " wins with a Straight!";
            } else {
                if (hand1HighVal > hand2HighVal) {
                    return hand1.getOwner() + " wins with a Straight!";
                } else if (hand2HighVal > hand1HighVal) {
                    return hand2.getOwner() + " wins with a Straight!";
                } else {
                    return "DRAW!" + hand1.getOwner() + " and " + hand2.getOwner() + "both have Straights" +
                            " with the same highest card.";
                }
            }
        } else if (hand1Hand.equals(PokerHands.THREEOFAKIND) || hand2Hand.equals(PokerHands.THREEOFAKIND)) {
            if (hand1Hand.equals(PokerHands.THREEOFAKIND) && !(hand2Hand.equals(PokerHands.THREEOFAKIND))) {
                return hand1.getOwner() + " wins with Three of a Kind!";
            } else if (!(hand1Hand.equals(PokerHands.THREEOFAKIND))) {
                return hand2.getOwner() + " wins with Three of a Kind!";
            } else {
                if (hand1HighVal > hand2HighVal) {
                    return hand1.getOwner() + " wins with Three of a Kind!";
                } else if (hand2HighVal > hand1HighVal) {
                    return hand2.getOwner() + " wins with Three of a Kind!";
                }
            }
        } else if (hand1Hand.equals(PokerHands.TWOPAIR) || hand2Hand.equals(PokerHands.TWOPAIR)) {
            if (hand1Hand.equals(PokerHands.TWOPAIR) && !(hand2Hand.equals(PokerHands.TWOPAIR))) {
                return hand1.getOwner() + " wins with two pairs!";
            } else if (!(hand1Hand.equals(PokerHands.TWOPAIR))) {
                return hand2.getOwner() + " wins with two pairs!";
            } else {
                if (hand1HighVal > hand2HighVal) {
                    return hand1.getOwner() + " wins with two pairs!";
                } else if (hand2HighVal > hand1HighVal) {
                    return hand2.getOwner() + " wins with two pairs!";
                } else if (hand1SecHighVal > hand2SecHighVal) {
                    return hand1.getOwner() + " wins with two pairs!";
                } else if (hand2SecHighVal > hand1SecHighVal) {
                    return hand2.getOwner() + " wins with two pairs!";
                } else {
                    return "DRAW!" + hand1.getOwner() + " and " + hand2.getOwner() + "both have two" +
                            " pairs with the same highest values.";
                }
            }
        } else if (hand1Hand.equals(PokerHands.ONEPAIR) || hand2Hand.equals(PokerHands.ONEPAIR)) {
            if (hand1Hand.equals(PokerHands.ONEPAIR) && !(hand2Hand.equals(PokerHands.ONEPAIR))) {
                return hand1.getOwner() + " wins with one pair!";
            } else if (!(hand1Hand.equals(PokerHands.ONEPAIR))) {
                return hand2.getOwner() + " wins with one pair!";
            } else {
                if (hand1HighVal > hand2HighVal) {
                    return hand1.getOwner() + " wins with one pair!";
                } else if (hand2HighVal > hand1HighVal) {
                    return hand2.getOwner() + " wins with one pair!";
                } else if (hand1SecHighVal > hand2SecHighVal) {
                    return hand1.getOwner() + " wins with one pair!";
                } else if (hand2SecHighVal > hand1SecHighVal) {
                    return hand2.getOwner() + " wins with one pair!";
                } else {
                    return "DRAW!" + hand1.getOwner() + " and " + hand2.getOwner() + "both have one" +
                            " pair with the same highest values.";
                }
            }
        } else if (hand1Hand.equals(PokerHands.PIG) || hand2Hand.equals(PokerHands.PIG)) {
            if (hand1HighVal > hand2HighVal) {
                return hand1.getOwner() + " wins with the higher card.";
            } else if (hand2HighVal > hand1HighVal) {
                return hand2.getOwner() + " wins with the higher card.";
            } else if (hand1SecHighVal > hand2SecHighVal) {
                return hand1.getOwner() + " wins with the higher card.";
            } else if (hand2SecHighVal > hand1SecHighVal) {
                return hand2.getOwner() + " wins with the higher card.";
            } else {
                return "DRAW!" + hand1.getOwner() + " and " + hand2.getOwner() + "both have hands" +
                        " with the same highest card.";
            }
        }

        return "DRAW! " + hand1.getOwner() + " and " + hand2.getOwner() + " both have terrible hands." +
                " Neither wins...";
    }

    public static void checkPokerHandSuitsAndValues(PokerHand hand, Suit[] suitArray,
                                                    Integer[] valueArray, Integer[] suitNumArray,
                                                    Integer[] valueNumArray) {
        for (int i = 0; i < hand.handSize; i++) {
            suitArray[i] = hand.getHand().get(i).getSuit();
            valueArray[i] = hand.getHand().get(i).getCardNum();

            switch (suitArray[i]) {
                case SPADES:
                    suitNumArray[0]++;
                    break;
                case CLUBS:
                    suitNumArray[1]++;
                    break;
                case HEARTS:
                    suitNumArray[2]++;
                    break;
                case DIAMONDS:
                    suitNumArray[3]++;
                    break;
                default:
                    break;
            }

            valueNumArray[valueArray[i] - 1]++;
        }
    }

    public static PokerHands determinePokerHandHand(Integer[] valueArray, Integer[] suitNumArray,
                                                    Integer[] valueNumArray) {
        PokerHands handType = PokerHands.PIG;

        Integer[] sortedValueArray = Arrays.copyOf(valueArray, valueArray.length);
        Arrays.sort(sortedValueArray);

        Integer[] straightCheck = new Integer[sortedValueArray.length];
        straightCheck[0] = sortedValueArray[0];

        for (int i = 1; i < sortedValueArray.length; i++) {
            straightCheck[i] = sortedValueArray[i - 1] + 1;
        }

        for (int i = 0; i < suitNumArray.length; i++) {
            if (suitNumArray[i] == 5) {
                if (Arrays.equals(valueArray, straightCheck)) {
                    if (Arrays.equals(valueNumArray, new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1})) {
                        return PokerHands.ROYALSTRAIGHTFLUSH;
                    } else {
                        return PokerHands.STRAIGHTFLUSH;
                    }
                } else {
                    handType = PokerHands.FLUSH;
                }
            }
        }

        for (int i = 0; i < valueNumArray.length; i++) {
            if (valueNumArray[i] == 4) {
                return PokerHands.FOUROFAKIND;
            } else if (valueNumArray[i] == 3) {
                for (int j = 0; j < valueNumArray.length; j++) {
                    if (valueNumArray[j] == 2) {
                        return PokerHands.FULLHOUSE;
                    }
                }

                if (handType != PokerHands.FLUSH) {
                    handType = PokerHands.THREEOFAKIND;
                }
            } else if (valueNumArray[i] == 2) {
                for (int j = 0; j < valueNumArray.length; j++) {
                    if (valueNumArray[j] == 2 && j != i) {
                        if (handType != PokerHands.FLUSH) {
                            handType = PokerHands.TWOPAIR;
                        }
                    }
                }

                if (handType != PokerHands.FLUSH && handType != PokerHands.TWOPAIR) {
                    handType = PokerHands.ONEPAIR;
                }
            }
        }

        return handType;
    }

    public static int getHighestPokerHandHandValue(Integer[] valueArray, Integer[] valueNumArray,
                                                   PokerHands handType) {
        Arrays.sort(valueArray);

        switch (handType) {
            case ROYALSTRAIGHTFLUSH:
            case STRAIGHTFLUSH:
            case STRAIGHT:
            case FLUSH:
            case PIG:
                return valueArray[valueArray.length - 1];
            case FOUROFAKIND:
                if (valueArray[valueArray.length - 1] > valueArray[0]) {
                    return valueArray[valueArray.length - 1];
                } else {
                    return valueArray[0];
                }
            case FULLHOUSE:
                for (int i = valueNumArray.length; i > 0; i--) {
                    if (valueNumArray[i - 1] == 3) {
                        for (int j = valueNumArray.length; j > 0; j--) {
                            if (valueNumArray[j - 1] == 2) {
                                return i;
                            }
                        }
                    }
                }
            case THREEOFAKIND:
                for (int i = valueNumArray.length; i > 0; i--) {
                    if (valueNumArray[i - 1] == 3) {
                        return i;
                    }
                }
            case TWOPAIR: {
                for (int i = valueNumArray.length; i > 0; i--) {
                    if (valueNumArray[i - 1] == 2) {
                        for (int j = i - 1; j > 0; j--) {
                            if (valueNumArray[j - 1] == 2) {
                                return i;
                            }
                        }
                    }
                }
            }
            case ONEPAIR:
                for (int i = valueNumArray.length; i > 0; i--) {
                    if (valueNumArray[i - 1] == 2) {
                        return i;
                    }
                }
            default:
                return 0;
        }
    }

    public static int getSecondHighestPokerHandHandValue(Integer[] valueNumArray, PokerHands handType) {
        switch (handType) {
            case ROYALSTRAIGHTFLUSH:
                return 11;
            case STRAIGHTFLUSH:
            case FLUSH:
            case STRAIGHT:
            case PIG:
                for (int i = valueNumArray.length; i > 0; i--) {
                    if (valueNumArray[i - 1] == 1) {
                        for (int j = valueNumArray.length; j > 0; j--) {
                            if (j != i && valueNumArray[j - 1] == 1) {
                                return j;
                            }
                        }
                    }
                }
            case FOUROFAKIND:
                for (int i = valueNumArray.length; i > 0; i--) {
                    if (valueNumArray[i - 1] == 1) {
                        return i;
                    }
                }
            case FULLHOUSE:
                for (int i = valueNumArray.length; i > 0; i--) {
                    if (valueNumArray[i - 1] == 2) {
                        return i;
                    }
                }
            case THREEOFAKIND:
                for (int i = valueNumArray.length; i > 0; i--) {
                    if (valueNumArray[i - 1] == 3) {
                        for (int j = valueNumArray.length; j > 0; j--) {
                            if (valueNumArray[j - 1] == 1) {
                                return j;
                            }
                        }
                    }
                }
            case TWOPAIR: {
                for (int i = valueNumArray.length; i > 0; i--) {
                    if (valueNumArray[i - 1] == 2) {
                        for (int j = i - 1; j > 0; j--) {
                            if (valueNumArray[j - 1] == 2) {
                                return j;
                            }
                        }
                    }
                }
            }
            case ONEPAIR:
                for (int i = valueNumArray.length; i > 0; i--) {
                    if (valueNumArray[i - 1] == 2) {
                        for (int j = valueNumArray.length; j > 0; j--) {
                            if (valueNumArray[j - 1] == 1) {
                                return j;
                            }
                        }
                    }
                }
            default:
                return 0;
        }
    }

    public static Character[] chooseCardsToRedraw(PokerHand hand) {
        Random randRedraw = new Random();
        Character[] redrawArray = new Character[hand.getHandSize()];
        Arrays.fill(redrawArray, 'O');
        int redrawIndex;
        boolean stop = false;

        while (!stop) {
            redrawIndex = randRedraw.nextInt(6);
            if (redrawIndex != 5) {
                if (redrawArray[redrawIndex] == 'O') {
                    redrawArray[redrawIndex] = 'X';
                }
            } else {
                stop = true;
            }
        }

        return redrawArray;
    }

    public static Character[] chooseCardsToRedraw(PokerHand hand, Scanner scan) {
        Character[] redrawArray = new Character[hand.getHandSize()];
        Arrays.fill(redrawArray, 'O');
        int redrawIndex;
        boolean stop = false;

        System.out.println();
        System.out.println("Would you like to redraw cards? Y/N");

        if (scan.nextLine().equalsIgnoreCase("Y")) {
            System.out.println();
            System.out.println("How would you like to redraw cards? Manually or randomly?");
            String randOrMan = scan.nextLine();
            if (randOrMan.equalsIgnoreCase("Randomly")) {
                return chooseCardsToRedraw(hand);
            } else if (randOrMan.equalsIgnoreCase("Manually")) {
                System.out.println("Your hand currently is: ");
                hand.printHand();
                while (!stop) {
                    System.out.println();
                    System.out.println("Please choose the index of the card to replace (1-5).");
                    System.out.println("Press 0 to finish replacing your cards.");

                    redrawIndex = Integer.parseInt(scan.nextLine());
                    if (redrawIndex > 0 && redrawIndex <= 5) {
                        if (redrawArray[redrawIndex - 1] == 'O') {
                            redrawArray[redrawIndex - 1] = 'X';
                            System.out.println("You have chosen to replace " +
                                    hand.getHand().get(redrawIndex - 1).getCardName());
                        } else if (redrawArray[redrawIndex - 1] == 'X') {
                            System.out.println("You have already chosen to replace this card.");
                        }
                    } else {
                        System.out.println("You have decided to stop replacing cards.");
                        stop = true;
                    }

                }
            } else {
                System.out.println("You have decided to not redraw any cards.");
            }
        } else {
            System.out.println("You have decided to not redraw any cards.");
        }

        return redrawArray;
    }
}