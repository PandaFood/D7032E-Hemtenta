package Server;

import Server.Util.ConnectionManager;
import Server.Monster.Monster;

import java.io.IOException;
import java.util.List;

public class Game {

    Deck deck;
    GameConfigurator gameConfigurator = new GameConfigurator();
    ConnectionManager connectionManager = new ConnectionManager();
    GameState gameState;
    DiceRollHandler rollHandler;
    CardShop cardShop;

    private boolean started = false;
    private final int[] ALLDICE = {1,2,3,4,5,6};
    int[] response;



    public Game(String[] argv){

        try {
            if(argv.length != 0)
                deck = gameConfigurator.loadDeckConfiguration(argv[0]);
            else
                deck = gameConfigurator.loadDeckConfiguration();
        } catch (IOException e) {
            System.out.println("Could not load the card configuration file ");
            e.printStackTrace();
        }


        gameState = new GameState(deck);
        cardShop = new CardShop(gameState);
        rollHandler = new DiceRollHandler(gameState);
        connectionManager.startSocketListener(gameState);

        startGame();
    }

    private void startGame(){
        if(started) return;
        else started = true;

        // WELCOME MESSAGE
        for (Monster player: gameState.monsters) {
            player.print.welcomeMessage();
        }

        while (true){
            for (Monster player: gameState.monsters) {
                monsterTurn(player);
            }
        }
    }
    /*
        Game loop:
        pre: Award a monster in Tokyo 1 star
        1. Roll 6 dice
        2. Decide which dice to keep
        3. Reroll remaining dice
        4. Decide which dice to keep
        5. Reroll remaining dice
        6. Sum up totals
          6a. Hearts = health (max 10 unless a cord increases it)
          6b. 3 hearts = power-up
          6c. 3 of a number = victory points
          6d. claws = attack (if in Tokyo attack everyone, else attack monster in Tokyo)
          6e. If you were outside, then the monster inside tokyo may decide to leave Tokyo
          6f. energy = energy tokens
        7. Decide to buy things for energy
          7a. Play "DISCARD" cards immediately
        8. Check victory conditions
    */
    private void monsterTurn(Monster player){
        response = null;

        /* Check if dead */
        if(!player.isAlive())
            return;

        /* Send a summary */
        player.print.summary();

        /* IS IN TOKYO */
        if(player.isInTokyo()){
            player.addVictoryPoints(1);
            player.print.pointForTokyo();
        }

        /* ROLL 6 DICE AND DECIDE WHICH TO KEEP*/
        rollDice(player, ALLDICE);
        player.print.rollDiceText();
        response = player.getPlayerIntInput();

        rollDice(player, response);
        player.print.rollDiceText();
        response = player.getPlayerIntInput();

        rollDice(player, response);

        /* CHECK ROLLS FOR EFFECTS */
        rollHandler.checkDiceResults(player);

        /* Buy cards */
        player.print.buyCard(cardShop.checkCards(player));
        response = player.getPlayerIntInput();
        cardShop.buyCard(response[0], player);

        /* Check Victory */
        checkVictory();
    }



    private void rollDice(Monster player, int... diceIndex){
        if(diceIndex == null)
            return;
        List<Dice> rolls = gameState.rollDice(diceIndex);
        player.print.rolledDice(rolls);
    }


    private void checkVictory(){
        for (Monster player: gameState.monsters) {
            if(player.getVictoryPoints() >= 20)
                hasWon("Victory: " + player.getName() + " has won by stars\n");
            if(gameState.getAlive() <= 1 && player.isAlive())
                hasWon("Victory: " + player.getName() + " has won by being the only one alive\n");
        }
    }

    private void hasWon(String message){
        for (Monster player: gameState.monsters) {
            player.print.genericMessage(message);
        }
        System.exit(0);
    }
}
