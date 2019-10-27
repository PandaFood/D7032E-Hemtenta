package main.Server.Monsters;

public abstract class Monster {

    String name;
    int energy;
    int currentHealth;
    int maxHealth;
    int victoryPoints;

    public Monster(){
        energy = 0;
        currentHealth = 10;
        maxHealth = 12;
        victoryPoints = 0;


    }



}
