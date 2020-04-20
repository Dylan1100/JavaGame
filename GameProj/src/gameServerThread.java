import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class gameServerThread implements Runnable {

    private ThePlayer myPlayer;
    private Enemy myEnemy;

    private JButton StartButton, exitButton;

    private JLabel playerLabel, enemyLabel;

    final static int CLIENT_PORT = 5656;
    final static int SERVER_PORT = 5556;

    final ServerSocket client = new ServerSocket(CLIENT_PORT);

    public gameServerThread(ThePlayer a, Enemy b, JButton c, JButton d,
                            JLabel e, JLabel f) throws IOException {
        this.myPlayer = a;
        this.myEnemy = b;
        this.StartButton = c;
        this.exitButton = d;
        this.playerLabel = e;
        this.enemyLabel = f;
    }

    //set up listening server
    public void run ( ) {
        synchronized(this) {

            System.out.println("Waiting for server responses...");
            while(true) {
                Socket s2;
                try {
                    s2 = client.accept();
                    prepGameService myService = new prepGameService (s2, myPlayer, myEnemy, exitButton, StartButton, playerLabel, enemyLabel);
                    Thread t = new Thread(myService);
                    t.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("client connected");

            }
        }
    }
}