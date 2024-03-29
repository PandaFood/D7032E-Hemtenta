package TokyoClient;

import TokyoClient.Bot.SimpleBot;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class TokyoClient {

    private Scanner sc = new Scanner(System.in);
    private boolean botEnabled;
    private SimpleBot simpleBot = new SimpleBot();

    public TokyoClient(boolean bot) {
        this.botEnabled = bot;
        connectToGame();
    }

    public void connectToGame() {


        String name = "";
        Random rnd = ThreadLocalRandom.current();
        final String INPUTTOKEN = "__INPUT__";
        //Server stuffs

        try {
            Socket aSocket = new Socket("localhost", 2048);
            DataOutputStream outToServer = new DataOutputStream(aSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));


            while (true) {

                String message = inFromServer.readLine();
                if(message.matches(INPUTTOKEN)){
                    String input;
                    if(botEnabled)
                        input = simpleBot.nextAnswer();
                    else
                        input = sc.nextLine();
                    outToServer.writeBytes( input + "\n");
                }
                else{
                    System.out.println(message);
                }
            }

        } catch (Exception e) {
        }
            /*

                String[] message = inFromServer.readLine().split(":");
                for (int i = 0; i < message.length; i++) {
                    System.out.println(message[i]);
                }
                if (message[0].equalsIgnoreCase("VICTORY")) {
                    outToServer.writeBytes("Bye!\n");
                } else if (message[0].equalsIgnoreCase("ATTACKED")) {
                    if (bot)
                        outToServer.writeBytes("YES\n");
                    else {
                        outToServer.writeBytes(sc.nextLine() + "\n");
                    }
                } else if (message[0].equalsIgnoreCase("ROLLED")) {
                    if (bot) {
                        rnd = ThreadLocalRandom.current();
                        int num1 = rnd.nextInt(2) + 4;
                        int num2 = rnd.nextInt(2) + 1;
                        String reroll = "" + num1 + "," + num2 + "\n";
                        outToServer.writeBytes(reroll);// Some randomness at least
                    } else {
                        outToServer.writeBytes(sc.nextLine() + "\n");
                    }
                } else if (message[0].equalsIgnoreCase("PURCHASE")) {
                    if (bot)
                        outToServer.writeBytes("-1\n");
                    else
                        outToServer.writeBytes(sc.nextLine() + "\n");
                } else {
                    if (bot)
                        outToServer.writeBytes("OK\n");
                    else {
                        System.out.println("Press [ENTER]");
                        sc.nextLine();
                        outToServer.writeBytes("OK\n");
                    }
                }
                System.out.println("\n");

                */





    }

    public static void main(String[] argv) {
        TokyoClient client;
        if(argv.length != 0) //Syntax: java KingTokyoPowerUpClient bot
            client = new TokyoClient(true);
        else
            client = new TokyoClient(false);
    }

}
