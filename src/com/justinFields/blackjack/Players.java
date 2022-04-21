package com.justinFields.blackjack;

public class Players {
    public Deck playerDeck = new Deck();
    public double playerBankroll = 100.00;

    public double playerBet = 0;

    public boolean endRound = false;


    public Players() {
        this.playerDeck = new Deck();
        this.playerBankroll = 100.00;
    }

    public Players(Deck playerDeck, double playerBankroll) {
        this.playerDeck = playerDeck;
        this.playerBankroll = playerBankroll;
    }

    public Deck getPlayerDeck() {
        return playerDeck;
    }

    public void setPlayerDeck(Deck playerDeck) {
        this.playerDeck = playerDeck;
    }

    public double getPlayerBankroll() {
        return playerBankroll;
    }

    public void setPlayerBankroll(double playerBankroll) {
        this.playerBankroll = playerBankroll;
    }

    public double getPlayerBet() {
        return playerBet;
    }

    public void setPlayerBet(double playerBet) {
        this.playerBet = playerBet;
    }

    public boolean isEndRound() {
        return endRound;
    }

    public void setEndRound(boolean endRound) {
        this.endRound = endRound;
    }
}
