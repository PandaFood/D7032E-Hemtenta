package Server;

import Server.Monster.Monster;
import Server.Util.EffectCalculator;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class DiceRollHandler {

    GameState gameState;
    EffectCalculator effectCalculator = new EffectCalculator();

    public DiceRollHandler(GameState gameState){
        this.gameState = gameState;
    }

    public void checkDiceResults(Monster currentMonster){

        Collections.sort(gameState.dice);
        HashMap<Dice, Integer> result = new HashMap<Dice, Integer>();
        for (Dice unique : new HashSet<Dice>(gameState.dice)) {
            result.put(unique, Collections.frequency(gameState.dice, unique));
        }

        checkStars(result, currentMonster);
        checkEnergy(result, currentMonster);
        checkClaw(result, currentMonster);
        checkHeart(result, currentMonster);
    }

    void checkStars(HashMap<Dice, Integer> result, Monster currentMonster){
        for (int num = 1; num < 4; num++) {
            if (result.containsKey(new Dice(num)))
                if (result.get(new Dice(num)).intValue() >= 3){
                    int points = num + (result.get(new Dice(num)).intValue() - 3);
                    currentMonster.addVictoryPoints(points);
                    currentMonster.print.addedVictoryPoints(points);
                }
        }
    }

    void checkEnergy(HashMap<Dice, Integer> result, Monster currentMonster){
        Dice anEnergy = new Dice(Dice.ENERGY);
        if (result.containsKey(anEnergy)){
            int energyGained = result.get(anEnergy).intValue();
            currentMonster.addEnergy(energyGained);
            currentMonster.print.addedEnergyPoints(energyGained);
        }

    }

    void checkClaw(HashMap<Dice, Integer> result, Monster currentMonster){
        Dice aClaw = new Dice(Dice.CLAWS);
        if (result.containsKey(aClaw)) {
            int damage = result.get(aClaw).intValue() + effectCalculator.getMoreDamage(currentMonster);
            currentMonster.addVictoryPoints(effectCalculator.starsWhenAttacking(currentMonster));

            if (currentMonster.isInTokyo()) {
                for(Monster monster: gameState.monsters){
                    if(monster != currentMonster){
                        int damageTaken = Math.max(0, damage - effectCalculator.getArmor(monster));
                        monster.reduceHealth(damageTaken);
                        monster.print.removedHealthPoints(damageTaken, currentMonster);
                    }
                }
            }
            else{
                if(gameState.monsterInTokyo == null){
                    gameState.monsterInTokyo = currentMonster;
                    currentMonster.setInTokyo(true);
                }
                else {
                    String response = "";
                    Monster monsterInTokyo = gameState.monsterInTokyo;
                    int damageTaken = Math.max(0, damage - effectCalculator.getArmor(monsterInTokyo));
                    monsterInTokyo.reduceHealth(damageTaken);
                    monsterInTokyo.print.removedHealthPoints(damageTaken, currentMonster);
                    monsterInTokyo.print.leaveTokyo();
                    response = monsterInTokyo.getPlayerStringInput();

                    if(response.toLowerCase().equals("no"))
                        return;
                    else
                        gameState.setMonsterInTokyo(currentMonster);


                }
            }

        }



    }

    void checkHeart(HashMap<Dice, Integer> result, Monster currentMonster) {
        Dice aHeart = new Dice(Dice.HEART);
        if (result.containsKey(aHeart)) {
            if(!currentMonster.isInTokyo()){
                int healthGained = result.get(aHeart).intValue();
                currentMonster.healHealth(healthGained);
                currentMonster.print.addedHealthPoints(healthGained);
            }
            if (result.get(aHeart).intValue() >= 3) {
                currentMonster.drawEvolutionCard();
            }
        }
    }




}
