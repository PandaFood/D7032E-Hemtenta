package Server;

import Server.Playables.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Deck {

    private Stack<Card> availableDeck = new Stack<Card>();
    private Stack<Card> discardPile = new Stack<Card>();
    List<Card> deck = new ArrayList<Card>();


    public Deck(){

    }

    public Stack<Card> getDeck() {
        return availableDeck;
    }

    public void setDeck(List<Card> deck){
        this.availableDeck.addAll(deck);
        Collections.shuffle(availableDeck);
    }

    public void discardCard(Card card){
        //TODO: implement discards
    }


    public Card getTopCard(){
        Card card = availableDeck.pop();
        return card;
    }

    public void shuffleInDiscardPile(){
        Collections.shuffle(discardPile);
        availableDeck.addAll(0, discardPile);
        discardPile.empty();
    }

}
