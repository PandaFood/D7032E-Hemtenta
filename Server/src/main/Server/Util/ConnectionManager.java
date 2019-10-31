package Server.Util;

import Server.GameState;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionManager {

    ServerSocket aSocket;

    public ConnectionManager(){
        try {
            aSocket = new ServerSocket(2048);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void startSocketListener(GameState gameState){
        try {
            //assume two online clients
            for (int onlineClient = 0; onlineClient < gameState.TOTALPLAYERSPOTS; onlineClient++) {
                Socket connectionSocket = aSocket.accept();
                gameState.assignMonster(connectionSocket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
