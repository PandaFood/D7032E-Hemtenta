package Server.Monster.Monsters;

import Server.Monster.Monster;
import Server.Playables.Card;
import Server.Playables.Effect;
import Server.Playables.Effects.Store.MoreDamage1;
import Server.Playables.Effects.Store.Stars2;

import java.util.ArrayList;
import java.util.List;

public class Alienoid extends Monster {

    public Alienoid(){
        super();
        this.name = "Alienoid";

        // TODO: Fix evolution cards
//        List<Effect> effects = new ArrayList<Effect>();
//        effects.add(new Stars2());
//        evolutions.add(new Card(1001, "Alien Scourge", "Receive 2 stars", 0, true, effects));
    }



}
