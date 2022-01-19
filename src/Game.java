//Kent Liu
//kjl2186
//How the poker game itself runs

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Collections;
import java.util.HashMap;

public class Game {

    private Player player;
    private Deck cards;
    private boolean testMode = false;

    public Game(String[] testHand){
        // This constructor is to help test your code.
        // use the contents of testHand to
        // make a hand for the player
        // use the following encoding for cards
        // c = clubs
        // d = diamonds
        // h = hearts
        // s = spades
        // 1-13 correspond to ace-king
        // example: s1 = ace of spades
        // example: testhand = {s1, s13, s12, s11, s10} = royal flush
        System.out.println("Welcome to test mode");
        this.cards = new Deck();
        int suitIndex = 0;
        int i = 0;
        for(String cardAbbreviation: testHand){
            if(cardAbbreviation.charAt(0) == 'c'){
                suitIndex = 1;
            }
            if(cardAbbreviation.charAt(0) == 'd'){
                suitIndex = 2;
            }
            if(cardAbbreviation.charAt(0) == 'h'){
                suitIndex = 3;
            }
            if(cardAbbreviation.charAt(0) == 's'){
                suitIndex = 4;
            }
            int rankIndex = Integer.parseInt(cardAbbreviation.substring(1));

            Card card = new Card(suitIndex,rankIndex);

            int index = this.cards.findCard(card);

            this.cards.swapCard(index,i);

            i++;
        }
        //Lets us know that we are in Test mode
        this.testMode = true;

    }

    public Game(){
        this.cards = new Deck();
    }

    public void play(){
        // this method should play the game
        System.out.println("Let's play Poker!\n");

        //Creates a new player
        this.player = new Player();

        Random rand = new Random();
        Scanner scan = new Scanner(System.in);
        //set player bankroll
        this.player.setBankroll(rand.nextInt(10) + 1);

        int round = 1;

        //runs as long as player has tokens
        while(this.player.getBankroll() > 0){
            boolean keepPlaying = true;

            System.out.println("------------------");
            System.out.println("Round " + round + "\n");

            System.out.println("Current bankroll: " + this.player.getBankroll() + "\n");

            System.out.println("How many tokens would you like to bet?(Please choose a number between 1 and 5)\n");

            //player bets
            int playerInput = scan.nextInt();
            this.player.bets(playerInput);
            this.player.setBankroll(this.player.getBankroll() - playerInput);

            double bankrollBeforeBet = this.player.getBankroll();

            if(this.testMode == false || round != 1){
                this.cards.shuffle();
            }

            //clears hand and deals new hand
            this.player.clearHand();
            for(int i = 0; i <5; i++){
                this.player.addCard(this.cards.deal());
            }

            //ArrayList keeps track of already exchanged cards
            ArrayList<Integer> previouslyExchangedCards = new ArrayList<>();


            //card exchange system
            System.out.println("\nCurrent hand:");
            System.out.println(this.player.getHandSummaryExchange());

            while(true && previouslyExchangedCards.size() < 5){

                System.out.println("Which card(s) would you like to exchange? (enter 0 if no change is wanted)");

                playerInput = scan.nextInt();

                if(playerInput == 0){
                    break;
                }

                if (previouslyExchangedCards.contains(playerInput)) {
                    System.out.println("******************\nYou can only exchange a particular card once\n******************");
                }
                else {
                    this.player.getHand().set(playerInput - 1, this.cards.deal());
                    previouslyExchangedCards.add(playerInput);
                }

            }


            System.out.println("\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            System.out.println("\nYour hand after exchanges:");
            System.out.println(this.player.getHandSummary());
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n");
            System.out.println(this.checkHand(this.player.getHand()));
            double bankrollAfterBet = this.player.getBankroll();
            double winnings = bankrollAfterBet - bankrollBeforeBet;
            System.out.println("You won " + winnings + " tokens!");
            System.out.println("------------------");

            System.out.println("\nPress 1 to continue playing or press 0 to quit");
            int playerDesire = scan.nextInt();

            if(playerDesire == 0){
                break;
            }
            else if(playerDesire == 1){
                System.out.print("\033[H\033[2J");
                round++;
                continue;
            }

        }
        System.out.print("\033[H\033[2J");
        System.out.println("Thanks for playing!");
        System.out.println("You ended up with " + this.player.getBankroll() + " tokens!\n");

    }




    public String checkHand(ArrayList<Card> hand){
        // this method should take an ArrayList of cards
        // as input and then determine what evaluates to and
        // return that as a String

        //royal flush
        if(this.isRoyalFlush(hand)){
            this.player.winnings(250);
            return "Royal flush";
        }

        //four of a kind
        if(this.isFourOfAKind(hand)){
            this.player.winnings(25);
            return "Four of a kind";
        }

        //straight flush
        if(this.isStraightFlush(hand)){
            this.player.winnings(50);
            return "Straight flush";
        }

        //full house
        if(this.isFullHouse(hand)){
            this.player.winnings(6);
            return "Full house";
        }

        //flush
        if(this.isFlush(hand)){
            this.player.winnings(5);
            return "Flush";
        }

        //straight
        if(this.isStraight(hand)){
            this.player.winnings(4);
            return "Straight";
        }

        //three of a kind
        if(this.isThreeOfAKind(hand)){
            this.player.winnings(3);
            return "Three of a kind";
        }

        //two pair
        if(this.isTwoPair(hand)){
            this.player.winnings(2);
            return "Two Pair";
        }

        //one pair
        if(this.isOnePair(hand)){
            this.player.winnings(1);
            return "One pair";
        }

        //nothing
        return "No pair";

    }


    // you will likely want many more methods here
    // per discussion in class

    public HashMap<String,Integer> getSuitSummary(ArrayList<Card> hand){
        int numOfSuits,numOfClubs,numOfDiamonds,numOfHearts,numOfSpades;
        numOfSuits = numOfClubs = numOfDiamonds = numOfHearts = numOfSpades = 0;

        //checks hand for instances of new suits and increase the number of instances of that suit in order to return a thorough summary of the suits of the player hand
        for(Card card: hand){
            if(card.getSuit() == 1){
                if(numOfClubs == 0){
                    numOfSuits++;
                }
                numOfClubs++;
            }

            if(card.getSuit() == 2){
                if(numOfDiamonds == 0){
                    numOfSuits++;
                }
                numOfDiamonds++;
            }

            if(card.getSuit() == 3){
                if(numOfHearts == 0){
                    numOfSuits++;
                }
                numOfHearts++;
            }

            if(card.getSuit() == 4){
                if(numOfSpades == 0){
                    numOfSuits++;
                }
                numOfSpades++;
            }
        }

        HashMap<String,Integer> suitSummary = new HashMap<>();
        suitSummary.put("numOfSuits",numOfSuits);
        return suitSummary;

    }

    public HashMap<String,Integer> getRankSummary(ArrayList<Card> hand){
        int numOfRanks,numOfAces,numOfTwos,numOfThrees,numOfFours,numOfFives,numOfSixes,numOfSevens,numOfEights,numOfNines,numOfTens,numOfJacks,numOfQueens,numOfKings;
        numOfRanks = numOfAces = numOfTwos = numOfThrees = numOfFours = numOfFives = numOfSixes = numOfSevens = numOfEights = numOfNines = numOfTens = numOfJacks = numOfQueens = numOfKings = 0;

        //checks hand for instances of new ranks and increase the number of instances of that rank in order to return a thorough summary of the ranks of the player hand
        for(Card card: hand){
            if(card.getRank() == 1){
                if(numOfAces == 0){
                    numOfRanks++;
                }
                numOfAces++;
            }

            if(card.getRank() == 2){
                if(numOfTwos == 0){
                    numOfRanks++;
                }
                numOfTwos++;
            }

            if(card.getRank() == 3){
                if(numOfThrees == 0){
                    numOfRanks++;
                }
                numOfThrees++;
            }

            if(card.getRank() == 4){
                if(numOfFours == 0){
                    numOfRanks++;
                }
                numOfFours++;
            }

            if(card.getRank() == 5){
                if(numOfFives == 0){
                    numOfRanks++;
                }
                numOfFives++;
            }

            if(card.getRank() == 6){
                if(numOfSixes == 0){
                    numOfRanks++;
                }
                numOfSixes++;
            }

            if(card.getRank() == 7){
                if(numOfSevens == 0){
                    numOfRanks++;
                }
                numOfSevens++;
            }

            if(card.getRank() == 8){
                if(numOfEights == 0){
                    numOfRanks++;
                }
                numOfEights++;
            }

            if(card.getRank() == 9){
                if(numOfNines == 0){
                    numOfRanks++;
                }
                numOfNines++;
            }

            if(card.getRank() == 10){
                if(numOfTens == 0){
                    numOfRanks++;
                }
                numOfTens++;
            }

            if(card.getRank() == 11){
                if(numOfJacks == 0){
                    numOfRanks++;
                }
                numOfJacks++;
            }

            if(card.getRank() == 12){
                if(numOfQueens == 0){
                    numOfRanks++;
                }
                numOfQueens++;
            }

            if(card.getRank() == 13){
                if(numOfKings == 0){
                    numOfRanks++;
                }
                numOfKings++;
            }
        }

        HashMap<String,Integer> rankSummary = new HashMap<>();
        rankSummary.put("numOfRanks",numOfRanks);
        return rankSummary;

    }

    //methods for different hand situations


    public int checkRank(ArrayList<Card> hand, int rank){
        int counter = 0;
        for(int i = 0; i < hand.size(); i++){
            if(hand.get(i).getRank() == rank){
                counter++;
            }
        }

        return counter;
    }

    //for pair, two pair, three of a kind, four of a kind
    public boolean checkRankMatch(ArrayList<Card> hand, int numOfRankMatches){
        for(int i = 0; i < Card.RANKS.length; i++){
            int instances = this.checkRank(hand, i);

            if(instances == numOfRankMatches){
                return true;
            }
        }
        return false;
    }



    //checks one pair
    public boolean isOnePair(ArrayList<Card> hand){
        return this.checkRankMatch(hand, 2);
    }

    //two pair
    public boolean isTwoPair(ArrayList<Card> hand){
        int counter = 0;
        boolean isTwoPair = false;

        for(int i = 0; i < Card.RANKS.length; i++){
            int instances = this.checkRank(hand,i);

            if(instances == 2){
                counter++;
            }
        }

        if(counter == 2){
            isTwoPair = true;
        }

        return isTwoPair;
    }

    //three of a kind
    public boolean isThreeOfAKind(ArrayList<Card> hand){
        return this.checkRankMatch(hand, 3);
    }

    //four of a kind
    public boolean isFourOfAKind(ArrayList<Card> hand){
        return this.checkRankMatch(hand, 4);
    }

    //full house
    public boolean isFullHouse(ArrayList<Card> hand){
        boolean isFullHouse = false;

        if(this.isThreeOfAKind(hand) && this.isOnePair(hand)){
            isFullHouse = true;
        }

        return isFullHouse;
    }

    //flush
    public boolean isFlush(ArrayList<Card> hand){
        boolean isFlush = false;

        if(getSuitSummary(hand).get("numOfSuits") == 1){
            isFlush = true;
        }

        return isFlush;
    }

    //straight
    public boolean isStraight(ArrayList<Card> hand){
        boolean isStraight = false;
        Collections.sort(hand);

        for(int i = 0; i < hand.size() - 1; i++){
            Card current = hand.get(i);
            Card next = hand.get(i + 1);

            //check to see if the current card is king and if first is ace
            if((i == 0 && current.getRank() == 1 && next.getRank() == 10) || next.getRank() == current.getRank() + 1){
                isStraight = true;
            } else{
                isStraight = false;
                break;
            }
        }
        return isStraight;
    }

    //straight flush
    public boolean isStraightFlush(ArrayList<Card> hand){
        boolean isStraightFlush = false;

        if(this.isStraight(hand) && this.isFlush(hand)){
            isStraightFlush = true;
        }

        return isStraightFlush;
    }

    //royal flush
    public boolean isRoyalFlush(ArrayList<Card> hand){
        boolean isRoyalFlush = false;

        if(this.isStraight(hand) && this.isFlush(hand) && hand.get(0).getRank() == 1){
            isRoyalFlush = true;
        }

        return isRoyalFlush;
    }
}
