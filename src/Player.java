//Kent Liu
//kjl2186
//Methods for the player

import java.util.ArrayList;
import java.util.Collections;

public class Player {


    private ArrayList<Card> hand;
    private double bankroll;
    private double bet;

    // you may choose to use more instance variables

    public Player(){
        this.hand = new ArrayList<Card>();

    }

    public void addCard(Card card){
        this.hand.add(card);
    }

    public void removeCard(Card card){
        this.hand.remove(card);
    }

    public void bets(double amt){
        this.bet = amt;
    }

    public void winnings(double odds){
        this.bankroll += (this.bet*odds);
    }

    public double getBankroll(){
        return this.bankroll;
    }

    // you may wish to use more methods here
    public void setBankroll(double newBankroll){
        this.bankroll = newBankroll;
    }

    public void clearHand(){
        this.hand.clear();
    }

    public ArrayList<Card> getHand(){
        return this.hand;
    }

    public Card getCard(int handIndex){
        return this.hand.get(handIndex);
    }

    public String getHandSummary(){
        Collections.sort(this.hand);
        int cardNumber = 1;
        String summary = "";

        for(Card card: this.hand){
            summary += "Card " + cardNumber + ": " + card.toString() + "\n";
            cardNumber++;
        }

        return summary;
    }

    //getHandSummary but doesn't sort so the card exchange system works
    public String getHandSummaryExchange(){
        int cardNumber = 1;
        String summary = "";

        for(Card card: this.hand){
            summary += "Card " + cardNumber + ": " + card.toString() + "\n";
            cardNumber++;
        }

        return summary;
    }
}


