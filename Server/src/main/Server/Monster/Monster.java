package Server.Monster;

import Server.Monster.Util.Printer;
import Server.Playables.Card;
import Server.Playables.Effect;
import Server.Util.EffectCalculator;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public abstract class Monster {

    protected String name;
    private int energy = 0;
    private int currentHealth = 10;
    private final int maxHealth = 10;
    private int victoryPoints = 0;
    private boolean inTokyo = false;
    private boolean isAlive = true;
    private List<Card> cards = new ArrayList<Card>();
    protected Stack<Card> evolutions = new Stack<Card>();

    private Socket connection = null;
    public BufferedReader inFromClient = null;
    public DataOutputStream outToClient = null;

    public Printer print = new Printer(this);

    public Monster(){

    }

    public void assignPlayer(Socket connection) throws IOException {
        if(this.connection != null)
            return;

        this.connection = connection;
        this.inFromClient = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        this.outToClient = new DataOutputStream(connection.getOutputStream());

    }

    public int[] getPlayerIntInput(){
        String response = getPlayerStringInput();
        int[] numbers = null;

        try{
            // Converts a string to an array which in turn is cast to int
            numbers = Arrays.stream(response.split(",")).mapToInt(Integer::parseInt).toArray();
        } catch (NumberFormatException e){
            print.genericMessage("Could not understand your input. Defaulting to 0 \n");
        }
        if(numbers == null)
            numbers = new int[]{0};


        return numbers;
    }

    public String getPlayerStringInput(){
        String response = "";
        try {
            response = inFromClient.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    public String getName() { return name; }

    public int getVictoryPoints() { return victoryPoints; }

    public void addVictoryPoints(int num){ victoryPoints += num; }

    public int getCurrentHealth() { return currentHealth; }

    public int reduceHealth(int damage){
        currentHealth -= damage;
        if(currentHealth <= 0)
            isAlive = false;


        return currentHealth;
    }

    public int healHealth(int healing){
        currentHealth += healing;
        if(currentHealth >= maxHealth)
            currentHealth = maxHealth;

        return currentHealth;
    }

    public int getMaxHealth() { return maxHealth; }

    public int getEnergy() { return energy; }

    public void addEnergy(int energy) { this.energy += energy; }

    public void reduceEnergy(int energy) { this.energy -= energy; }

    public boolean isAlive() { return isAlive; }

    public boolean isInTokyo() { return inTokyo; }

    public void setInTokyo(boolean inTokyo) { this.inTokyo = inTokyo; }

    public List<Card> getCards() { return cards; }

    public void addCard(Card card) {
        for (Effect effect: card.getEffects()) {
            addVictoryPoints(effect.getMoreStars());
        }

        if(!card.isDiscard())
            this.cards.add(card);

    }

    public List<Effect> getEffects(){
        List<Effect> effects = new ArrayList<Effect>();
        for (Card card: cards) {
            effects.addAll(card.getEffects());
        }
        return effects;
    }

    public void drawEvolutionCard(){
        if(evolutions.empty()){
            print.noEvolutionText();
            return;
        }
        Card evolution = evolutions.pop();
        addCard(evolution);
        print.evolutionText(evolution);

    }
}
