package Server.Monster.Util;

import Server.Dice;
import Server.Monster.Monster;
import Server.Playables.Card;

import java.util.List;

public class Printer {

    private Monster player;
    private StringBuilder sb = new StringBuilder();
    private final String INPUTTOKEN = "__INPUT__";


    public Printer(Monster player){
        this.player = player;
    }

    private void sendMessage(String message){
        String response = "";
        try {
            player.outToClient.writeBytes(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void resetSB(){
        sb.setLength(0);
    }

    public void welcomeMessage(){
        resetSB();
        sb.append("Welcome to King of Tokyo\n");
        sb.append("You are " + player.getName() + "\n");
        sendMessage(sb.toString());
    }

    public void pointForTokyo(){
        resetSB();
        sb.append("You've been awarded a point for being in Tokyo\n");
        sb.append("You now have " + player.getVictoryPoints() + " points\n");
        sendMessage(sb.toString());
    }

    public void rolledDice(List<Dice> dices){
        resetSB();
        sb.append("ROLLED:You rolled:\n\t[1]\t[2]\t[3]\t[4]\t[5]\t[6]:\n");
        for (Dice d: dices) {
            sb.append("\t" + d + "");
        }
        sb.append("\n");

        sendMessage(sb.toString());
    }

    public void rollDiceText(){
        resetSB();
        sb.append("Choose which dice to reroll, separate with comma(e.g. 5,4,1   0 to skip)\n");
        sb.append(INPUTTOKEN + "\n");

        sendMessage(sb.toString());
    }

    public void evolutionText(Card card){
        resetSB();
        sb.append("POWERUP: " + card.getDescription() + "\n");

        sendMessage(sb.toString());
    }

    public void noEvolutionText(){
        resetSB();
        sb.append("No evolution left.\n");

        sendMessage(sb.toString());
    }

    public void addedVictoryPoints(int points) {
        resetSB();
        sb.append("You got " + points + " victory points. \n");
        sb.append("You are now at " + player.getVictoryPoints() + " victory points. \n");

        sendMessage(sb.toString());
    }

    public void addedEnergyPoints(int points) {
        resetSB();
        sb.append("You got " + points + " energy points. \n");
        sb.append("You are now at " + player.getEnergy() + " energy. \n");

        sendMessage(sb.toString());
    }

    public void addedHealthPoints(int points) {
        resetSB();
        sb.append("You got " + points + " health points. \n");
        sb.append("You are now at " + player.getCurrentHealth() + " health. \n");

        sendMessage(sb.toString());
    }

    public void removedHealthPoints(int points, Monster monster) {
        resetSB();
        sb.append(monster.getName() + " hurt you for " + points + " damage\n");
        sb.append("You are now at " + player.getCurrentHealth() + " health. \n");

        sendMessage(sb.toString());
    }

    public void leaveTokyo() {
        resetSB();
        sb.append("Do you want to leave tokyo? \n");
        sb.append("Choose your answer with yes/no (Default: yes) \n");
        sb.append(INPUTTOKEN + "\n");

        sendMessage(sb.toString());
    }

    public void leftTokyo(Monster monster) {
        resetSB();
        sb.append(monster.getName() + " left Tokyo\n");

        sendMessage(sb.toString());
    }

    public void enteredTokyo(Monster monster) {
        resetSB();
        sb.append(monster.getName() + " entered Tokyo \n");

        sendMessage(sb.toString());
    }

    public void buyCard(String shop) {
        resetSB();
        sb.append("PURCHASE:Do you want to buy any of the cards from the store? (you have " + player.getEnergy() + " energy) [#/0]:" +  "\n");
        sb.append(shop + "\n");
        sb.append(INPUTTOKEN + "\n");

        sendMessage(sb.toString());
    }

    public void boughtCard(String card) {
        resetSB();
        sb.append("You bought " + card.toString() +  "\n");
        sb.append("You are now at " + player.getEnergy() + " energy. \n");

        sendMessage(sb.toString());
    }

    public void cantAffordCard(String card) {
        resetSB();
        sb.append("You can't afford that card.\n");

        sendMessage(sb.toString());
    }

    public void summary() {
        resetSB();

        sb.append("--------New round--------\n");
        sb.append("You are " + player.getName() + " \n");
        sb.append("Stats: \n");
        sb.append("\t Victory points: " + player.getVictoryPoints() + " \n");
        sb.append("\t Energy points: " + player.getEnergy() + " \n");
        sb.append("\t Health points: " + player.getCurrentHealth() + " \n");
        sb.append("\n\n");

        sendMessage(sb.toString());
    }

    public void genericMessage(String message){
        sendMessage(message);
    }


}
