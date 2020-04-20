import javax.swing.*;
import java.awt.*;

public class item extends Sprite implements Runnable {

    private boolean exists;
    private Thread t;
    private JLabel itemLabel;
    private ThePlayer myPlayer;
    private JLabel playerLabel;
    private JButton StartButton;
    private bird myBird;
    private JLabel birdLabel;
    private int countdown;
    private score myScore;


    public item() {
        super(0,0, "item.png", 77, 106);
        exists = false;
    }


    public void stopExist() {
        this.exists = false;
    }

    public void itemExists() {
        this.exists = true;
        System.out.println("Working");
        t = new Thread(this, "item");
        t.start();
    }

    @Override
    public void run() {

        this.setSpriteName("item.png");
        this.setSpriteName("player.png");

        itemLabel.setIcon(new ImageIcon(
                getClass().getResource("item.png")
        ));

        playerLabel.setIcon(new ImageIcon(
                getClass().getResource("player.png")
        ));

        while (exists) {
            //detect collision
            Rectangle rPlayer = myPlayer.getRectangle();
            Rectangle rItem = this.r;

            if (rPlayer.intersects(rItem)) {

                //Bird countdown and bird is reset

                myBird.resetBird();
                myBird.setCountdown(1);

                myScore.setScore(myScore.getScore()+1);

                System.out.println("score is " + myScore.getScore());

                //Random coordinates are generated and assigned to the new item
                int max = 700;
                int min = 1;
                int range = max - min + 1;
                int randX = (int)(Math.random() * range) + min;
                int randY = (int)(Math.random() * range) + min;

                this.setSpriteX(randX);
                this.setSpriteY(randY);
                itemLabel.setLocation(this.getSpriteX(), this.getSpriteY());
            }

            //pause
            try {
                Thread.sleep(200);
            } catch (Exception e) {

            }
        }
    }

    //Getters
    public Boolean getExists() {
        return exists;
    }


    //Setters
    public void setItemLabel(JLabel temp) {
        this.itemLabel = temp;
    }

    public void setPlayerLabel(JLabel temp) {
        this.playerLabel = temp;
    }

    public void setBirdLabel(JLabel temp) {
        this.birdLabel = temp;
    }

    public void setPlayer(ThePlayer temp) {
        this.myPlayer = temp;
    }

    public void setBird(bird temp) {
        this.myBird = temp;
    }

    public void setStartButton (JButton temp) { this.StartButton = temp; }

    public void setScore (score temp) { this.myScore = temp; }

    public void setCountDown (int temp) {this.countdown = temp;}
}
