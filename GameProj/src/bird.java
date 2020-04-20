import javax.swing.*;
import java.awt.*;

public class bird extends Sprite implements Runnable{

    private Boolean moving;
    private Thread t;
    private JLabel birdLabel, playerLabel;
    private ThePlayer myPlayer;
    private JButton StartButton;
    private int countdown;
    private item myItem;
    private score myScore, myName;

    public bird() {
        super(0,0, "bird.gif", 120, 85);
        moving = false;
    }


    public void stopBird() {
        this.moving = false;
    }

    public void moveBird() {
        this.moving = true;
        t = new Thread(this, "bird");
        t.start();
    }


    @Override
    public void run() {

        this.setSpriteName("bird.gif");
        this.setSpriteName("player.png");

        playerLabel.setIcon(new ImageIcon(
                getClass().getResource("player.png")
        ));

        while (countdown < 9){

            System.out.println(countdown);
            ++countdown;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (countdown >= 9){
                this.moving = true;

            }

        }

        while (moving && countdown >= 9) {
            //moving code


            int tX = this.spriteX;
            int tY = this.spriteY;

            tX += Properties.CHARACTER_STEP * 2;
            if (tX > Properties.SCREEN_WIDTH) {
                tX = -1 * this.spriteW;
            }

            tY += Properties.CHARACTER_STEP * 2;
            if (tY > Properties.SCREEN_HEIGHT) {
                tY = -2 * this.spriteH;
            }

            tY += Properties.CHARACTER_STEP * 2;
            if (tY > Properties.SCREEN_HEIGHT) {
                tY = 1 * this.spriteH;
            }

            this.setSpriteX(tX);
            this.setSpriteY(tY);

            birdLabel.setLocation(this.spriteX, this.spriteY);

            //detect collision
            Rectangle rPlayer = myPlayer.getRectangle();
            Rectangle rBird = this.r;

            if (rPlayer.intersects(rBird)) {

                playerLabel.setIcon(new ImageIcon(
                        getClass().getResource("fail.png")
                ));

                resetBird();
                myScore.setName(JOptionPane.showInputDialog("Please input your name  "));
                myScore.recordScore();
                JOptionPane.showMessageDialog(null, "You died. Your score is: " + myScore.getScore());
            }

            //pause
            try {
                Thread.sleep(200);
            } catch (Exception e) {
            }
        }
    }

    //Called upon obtaining item
    public void resetBird(){
        System.out.println("resetbird " + countdown);
        this.moving = false;
        this.countdown = 0;
        this.setSpriteX(-150);
        this.setSpriteY(600);
        birdLabel.setLocation(this.getSpriteX(), this.getSpriteY());
        //this.run();
        //Properties.CHARACTER_STEP = Properties.CHARACTER_STEP /2;
    }



    //Getters
    public int getCountdown() { return this.countdown; }

    public Boolean getMoving() { return this.moving; }


    //Setters
    public void setMoving(Boolean moving) {
        this.moving = moving;
    }

    public void setBirdLabel(JLabel temp) {
        this.birdLabel = temp;
    }

    public void setPlayerLabel(JLabel temp) {
        this.playerLabel = temp;
    }

    public void setPlayer(ThePlayer temp) {
        this.myPlayer = temp;
    }

    public void setStartButton (JButton temp) {
        this.StartButton = temp;
    }

    public void setCountdown (int temp) {
        this.countdown= temp;
    }

    public void setScore (score temp) { this.myScore = temp; }

    public void setName (score temp) { this.myName = temp; }
}
