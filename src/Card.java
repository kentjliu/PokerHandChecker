//Kent Liu
//kjl2186
//The Card object

import java.util.List;
import java.util.Arrays;

public class Card implements Comparable<Card>{

    private int suit; // use integers 1-4 to encode the suit
    private int rank; // use integers 1-13 to encode the rank

    public Card(int suit, int rank){
        //make a card with suit s and value v
        this.suit = suit;
        this.rank = rank;
    }

    public static final String[] SUITS = {null, "Clubs", "Diamonds", "Hearts", "Spades"};
    public static final String[] RANKS = {null, "Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"}; //null is at index 0 so the other cards will align with their respective indices

    public int compareTo(Card card){
        // use this method to compare cards so they
        // may be easily sorted
        if(this.rank > card.rank){
            return 1;
        }
        if(this.rank < card.rank){
            return -1;
        }
        return 0;

    }

    public String toString(){
        return RANKS[this.rank] + " of " + SUITS[this.suit];
    }

    // add some more methods here if needed
    public int getSuit(){
        return this.suit;
    }

    public int getRank(){
        return this.rank;
    }

}
