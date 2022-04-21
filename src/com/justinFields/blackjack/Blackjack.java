package com.justinFields.blackjack;

import java.util.ArrayList;
import java.util.Scanner;

public class Blackjack {
    public static void main(String args[]) {
        Scanner userInput = new Scanner(System.in);
        //Welcome Message
        System.out.println("Welcome to Blackjack!");
        System.out.print("How many Players? :\t");
        int numPlayers = userInput.nextInt();
        ArrayList<Players> players = new ArrayList<Players>();

        for (int i = 0; i < numPlayers; i++) {
            Players player = new Players();
            players.add(player);
        }

        //Create Playing Deck
        Deck playingDeck = new Deck();
        System.out.print("Number of Decks? :\t");
        int numDecks = userInput.nextInt();
        for(int i = 0 ; i < numDecks ; i++)
            playingDeck.createFullDeck();
        playingDeck.shuffle();
        Deck dealerDeck = new Deck();

        boolean keepPlaying = true;


        //Game Loop
        while (keepPlaying) {
            //Continue
            for (int i = 0; i < players.size(); i++) {
                if(players.get(i).playerBankroll > 0) {
                    System.out.print("Player " + i + " Bankroll:\t$" + players.get(i).playerBankroll + "\n" +
                            "Your Bet:\t$");
                    players.get(i).playerBet = userInput.nextDouble();
                    while (players.get(i).playerBet > players.get(i).playerBankroll) {
                        System.out.println("Bet cannot exceed your bankroll.");
                        System.out.print("Player " + i + " Bankroll:\t$" + players.get(i).playerBankroll + "\n" +
                                "Your Bet:\t$");
                        players.get(i).playerBet = userInput.nextDouble();
                    }
                    players.get(i).endRound = false;
                }
                else
                    players.get(i).endRound = true;
            }
            //Start game
            //back and forth for 2 cards
            for (int i = 0; i < 2; i++) {
                for (Players player : players) {
                    if (player.playerBankroll > 0 && player.playerBet > 0)
                        player.playerDeck.draw(playingDeck);
                }
                dealerDeck.draw(playingDeck);
            }
            for (int j = 0; j < players.size(); j++) {
                int response = 1;
                if(players.get(j).playerBankroll > 0 && players.get(j).playerBet > 0) {
                    while(response == 1 && !players.get(j).endRound) {
                        System.out.println("Player " + j + " hand:\t" + players.get(j).playerDeck.toString());
                        System.out.println("Hand is at:\t" + players.get(j).playerDeck.cardsValue());
                        //Display Dealer
                        System.out.println("Dealer Hand:\t" + dealerDeck.getCard(0).toString());
                        //Player Action
                        System.out.println("(1)Hit or (2)Stand?");
                        response = userInput.nextInt();
                        //Hit
                        if (response == 1) {
                            players.get(j).playerDeck.draw(playingDeck);
                            System.out.println("You drew:\t" + players.get(j).playerDeck.getCard(players.get(j).playerDeck.deckSize() - 1).toString());
                            //Bust if Over 21
                            if (players.get(j).playerDeck.cardsValue() > 21) {
                                System.out.println("Bust with Value:\t" + players.get(j).playerDeck.cardsValue());
                                players.get(j).playerBankroll -= players.get(j).playerBet;
                                players.get(j).endRound = true;
                            }
                        }
                        //Stand
                    }
                }
                else
                    players.get(j).endRound = true;
            }
            //Reveal Dealer Cards
            System.out.println("Dealer Cards:\t" + dealerDeck.toString());
            boolean allEndRound = true;
            for(Players player : players)
            {
                if(!player.endRound){
                 allEndRound = false;
                 break;
                }
            }
            //Dealer Draws
            while ((dealerDeck.cardsValue() < 17) && !allEndRound) {
                dealerDeck.draw(playingDeck);
                System.out.println("Dealer Draws:\t" + dealerDeck.getCard(dealerDeck.deckSize() - 1).toString());
            }
            //Display Total Dealer Value
            System.out.println("Dealer Total Value:\t" + dealerDeck.cardsValue());
            //outcome
            for (int i = 0; i < players.size(); i++) {
                if (dealerDeck.cardsValue() > 21 && !players.get(i).endRound) {
                    System.out.println("Player " + i + " wins!");
                    players.get(i).playerBankroll += players.get(i).playerBet;
                    players.get(i).endRound = true;
                } else if (dealerDeck.cardsValue() == players.get(i).playerDeck.cardsValue() && !players.get(i).endRound) {
                    System.out.println("Push");
                    players.get(i).endRound = true;
                } else if (dealerDeck.cardsValue() > players.get(i).playerDeck.cardsValue() && !players.get(i).endRound) {
                    System.out.println("Dealer Wins.");
                    players.get(i).playerBankroll -= players.get(i).playerBet;
                    players.get(i).endRound = true;
                } else if (dealerDeck.cardsValue() < players.get(i).playerDeck.cardsValue() && !players.get(i).endRound) {
                    System.out.println("Player " + i + " wins!");
                    players.get(i).playerBankroll += players.get(i).playerBet;
                    players.get(i).endRound = true;
                }
                players.get(i).playerDeck.moveAllToDeck(playingDeck);
            }
            dealerDeck.moveAllToDeck(playingDeck);
            playingDeck.shuffle();
            System.out.println("End of hand.");


            if(isEveryoneBroke(players)){
                System.out.println("Everyone is Broke! Thank you for playing!");
                break;
            }
            System.out.println("Keep playing?(y) for Yes or (n) for No\t");
            if(userInput.next().contains("y"))
                keepPlaying = true;
            else{
                System.out.println("Thank you for playing!");
                break;
            }

        }
    }

    public static boolean isEveryoneBroke(ArrayList<Players> players){
        for(Players player : players)
        {
            if(player.playerBankroll > 0)
                return false;
        }
        return true;
    }
}
