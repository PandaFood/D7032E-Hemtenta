package Server;

import Server.Monster.Monster;
import Server.Playables.Card;
import javafx.beans.binding.StringBinding;

import java.util.ArrayList;
import java.util.List;

public class CardShop {

    GameState gameState;
    StringBuilder sb = new StringBuilder();
    private final int MAXCARDSINSHOP = 3;


    List<Card> shop = new ArrayList<Card>();

    public CardShop(GameState gameState){
        this.gameState = gameState;
        topUpShop();
    }

    private void topUpShop(){
        while (shop.size() < MAXCARDSINSHOP){
            shop.add(gameState.cardDeck.getTopCard());
        }
    }


    public String checkCards(Monster player) {
        sb.setLength(0);
        sb.append("The shop has these cards: \n");
        for(int i = 0; i < shop.size(); i++){
            sb.append((i+1) + ": " + shop.get(i).toString() + "\n");
        }
        return sb.toString();
    }

    public void buyCard(int number, Monster player){
        // Decrement number to match indexes
        number--;
        // Check if number is out of bounds
        if (number < 0 || number >= 3)
            return;

        Card card = shop.get(number);
        if (canPlayerAffordCard(card, player)){
            player.reduceEnergy(card.getCost());
            player.addCard(card);
            player.print.boughtCard(card.toString());
            shop.remove(number);
            topUpShop();
        }
        else{
            player.print.cantAffordCard(card.toString());
        }

    }

    private boolean canPlayerAffordCard(Card card, Monster player){
        return player.getEnergy() >= card.getCost();
    }


}
