import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class prepGameService implements Runnable {

    private Socket s;
    private Scanner in;

    private ThePlayer myPlayer;
    private Enemy myEnemy;

    private JButton StartButton, exitButton;

    private JLabel playerLabel, enemyLabel;
    public prepGameService (Socket aSocket, ThePlayer a, Enemy b, JButton c, JButton d,
                            JLabel e, JLabel f) {
        this.s = aSocket;
        this.myPlayer = a;
        this.myEnemy = b;
        this.StartButton = c;
        this.exitButton = d;
        this.playerLabel = e;
        this.enemyLabel = f;
    }

    public void run() {

        try {
            in = new Scanner(s.getInputStream());
            processRequest( );
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void processRequest () throws IOException {
        //if next request is empty then return
        while(true) {
            if(!in.hasNext( )){
                return;
            }
            String command = in.next();
            if (command.equals("Quit")) {
                return;
            } else {
                executeCommand(command);
            }
        }
    }

    public void executeCommand(String command) throws IOException {
        if ( command.equals("PLAYERDATA")) {
            System.out.println("PLAYERDATA received");


            int playerNo = in.nextInt();
            //String playerAction = in.next();
            int playerX = in.nextInt();
            int playerY = in.nextInt();

            myPlayer.setSpriteX(playerX);
            myPlayer.setSpriteY(playerY);

            playerLabel.setLocation(myPlayer.getSpriteX(), myPlayer.getSpriteY());

            //System.out.println("Player "+playerNo+" "+playerAction + " "+playerX+", "+playerY);
        }
    }

}
