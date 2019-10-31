package Server;

import Server.Monster.Monsters.Alienoid;
import Server.Monster.Monsters.Gigazaur;
import Server.Monster.Monsters.Kong;
import Server.Monster.Monster;
import Server.Playables.Card;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class GameState {

    Deck cardDeck;
    Stack<Monster> availableMonsters = new Stack<Monster>();
    List<Monster> monsters = new ArrayList<Monster>();
    protected List<Dice> dice = new ArrayList<Dice>();
    Monster monsterInTokyo = null;

    public final int TOTALPLAYERSPOTS = 2;


    /**
     * @param deck
     */
    public GameState(Deck deck){
        availableMonsters.add(new Alienoid());
        availableMonsters.add(new Gigazaur());
        availableMonsters.add(new Kong());

        for (int i = 0; i <= 5; i++) {
            dice.add(new Dice(i));
        }

        this.cardDeck = deck;
    }

    public void assignMonster(Socket connection) throws IOException {
        Collections.shuffle(availableMonsters);
        Monster player = availableMonsters.pop();
        player.assignPlayer(connection);
        monsters.add(player);
    }

    public List<Monster> getMonsters() {
        return monsters;
    }


    public List<Dice> rollDice(int... diceIndex) {
        for (int i:diceIndex) {
            // If they entered 0, do nothing
            if(i == 0)
                continue;

            // Decrement i to get correct index
            i--;
            dice.get(i).rollDie();
        }
        return dice;
    }

    public int getAlive() {
        List<Monster> alive = new ArrayList<Monster>();
        for (Monster monster: monsters) {
            if(monster.isAlive())
                alive.add(monster);
        }
        return alive.size();
    }

    public void setMonsterInTokyo(Monster monster){
        if(monsterInTokyo != null){
            this.monsterInTokyo.setInTokyo(false);
        }
        this.monsterInTokyo = monster;
        monster.setInTokyo(true);
        monster.addVictoryPoints(1);
        for(Monster m: monsters){
            m.print.enteredTokyo(monster);
        }
    }
}
