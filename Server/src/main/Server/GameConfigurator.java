package Server;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;



public class GameConfigurator {

    public  GameConfigurator(){
    }

    public Deck loadDeckConfiguration(String... path) throws IOException {

        //Read JSON file data to String
        byte[] jsonData = Files.readAllBytes(Paths.get(path.length != 0 ? path[0] : "Server/Cards.json"));
        //Create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        //Convert json string to object
        Deck deck = objectMapper.readValue(jsonData, Deck.class);

        return deck;
    }

}
