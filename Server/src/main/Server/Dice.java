package Server;

import java.util.Random;

public class Dice implements Comparable<Dice> {

    public static final int HEART = 0;
    public static final int POINT1 = 1;
    public static final int POINT2 = 2;
    public static final int POINT3 = 3;
    public static final int ENERGY = 4;
    public static final int CLAWS = 5;
    public int value = -1;

    private Random random = new Random();

    public Dice(int value) {
        this.value = value;
    }

    public String rollDie(){
        this.value = random.nextInt(6);
        return toString();
    }

    public String toString() {
        switch (value){
            case HEART:
                return "HEART";
            case ENERGY:
                return "ENERGY";
            case CLAWS:
                return "CLAWS";
            case POINT1:
                return "ONE";
            case POINT2:
                return "TWO";
            case POINT3:
                return "THREE";
            default:
                return "ERROR";
        }
    }

    @Override
    public int compareTo(Dice o) {
        return Integer.compare(value, o.value);
    }

    public boolean equals(Object o) {
        return value == ((Dice) o).value;
    }

    public int hashCode() {
        return toString().hashCode();
    }
}
