//Kent Liu
//kjl2186
//Methods for Deck

import java.util.ArrayList;

public class Deck {

    private Card[] cards;
    private int top = 0; // the index of the top of the deck

    // add more instance variables if needed


    public Deck(){
        this.cards = new Card[52];
        int index = 0;
        for (int suit = 1; suit < 5; suit++){
            for(int rank = 1; rank < 14; rank ++){
                this.cards[index] = new Card(suit,rank);
                index ++;
            }
        }
    }

    public void shuffle(){
        for(int i = 0; i < this.cards.length; i++){
            int j = (int)(Math.random() * this.cards.length);
            Card temp = this.cards[i];
            this.cards[i] = this.cards[j];
            this.cards[j] = temp;
        }
        this.top = 0;
    }

    public Card deal(){
        top ++;
        return this.cards[top-1];

    }

    // add more methods here if needed
    public void replace(int index, Card card){
        this.cards[index] = card;
    }

    //test mode methods
    public int findCard(Card card){
        for(int i = 0; i < this.cards.length; i++){
            if(card.getRank() == this.cards[i].getRank()
                    && card.getSuit() == this.cards[i].getSuit()){
                return i;
            }
        }
        return -1;
    }
    //swap card
    public void swapCard(int firstIndex, int secondIndex){
        Card temp = this.cards[firstIndex];
        this.cards[firstIndex] = this.cards[secondIndex];
        this.cards[secondIndex] = temp;
    }

    public String toString(){
        String message = "Current deck: ";
        message += "\n";
        for(Card card: this.cards){
            message += card.toString();
        }

        return message;
    }



}
