package Server.Playables;

import java.util.ArrayList;
import java.util.List;

public class Card {

    private ClassLoader cl;

    private int id;
    private String name;
    private String description;
    private int cost;
    private boolean discard;
    private List<Effect> effects = new ArrayList<Effect>();

    public Card() {

    }

    public Card(int id, String name, String description, int cost, boolean discard, List<Effect> effects) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.discard = discard;
        this.effects = effects;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDiscard(boolean discard) {
        this.discard = discard;
    }

    public boolean isDiscard() {
        return discard;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEffects(String[] effects) {

        // Loops through all effects and associates them with corresponding Effect class
        for (String s: effects) {
            try{
                Class<?> c = Class.forName("Server.Playables.Effects."+s);
                Effect e = (Effect) c.getDeclaredConstructor().newInstance();
                this.effects.add(e);
            }
            // General Exception handling to avoid cases
            // ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
            catch (Exception e){
                System.out.println("Could not find class" + s);
                e.printStackTrace();
            }
        }
    }

    public List<Effect> getEffects() {
        return effects;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: "+getName()+" \t");
        sb.append("Cost: "+getCost()+" \t");
        sb.append(getDescription()+" \t ");
        sb.append(isDiscard()? "USED IMMEDIATELY":"");

        return sb.toString();
    }
}
