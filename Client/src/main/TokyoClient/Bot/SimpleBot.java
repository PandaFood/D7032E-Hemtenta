package TokyoClient.Bot;

import java.util.Random;

public class SimpleBot {

    Random random = new Random();

    public SimpleBot(){

    }

    public String nextAnswer() {
        String value = String.valueOf(random.nextInt(5));
        System.out.println("The bot rolled " + value);
        return value;
    }
}
