package Server.Util;

import Server.Monster.Monster;
import Server.Playables.Effect;

public class EffectCalculator {

    public EffectCalculator(){

    }

    public int getMoreDamage(Monster monster){
        int damage = 0;
        for (Effect effect: monster.getEffects()) {
            damage += effect.getMoreDamage();
        }
        return damage;
    }

    public int getLessCost(Monster monster) {
        int cost = 0;
        for (Effect effect: monster.getEffects()) {
            cost += effect.getLessCost();
        }
        return cost;
    }

    public int getMoreStars(Monster monster) {
        int stars = 0;
        for (Effect effect: monster.getEffects()) {
            stars += effect.getMoreStars();
        }
        return stars;
    }

    public int starsWhenAttacking(Monster monster) {
        int stars = 0;
        for (Effect effect: monster.getEffects()) {
            stars += effect.starsWhenAttacking();
        }
        return stars;
    }

    public int getArmor(Monster monster){
        int armor = 0;
        for (Effect effect: monster.getEffects()) {
            armor += effect.getArmor();
        }
        return armor;
    }


}
