import java.awt.Rectangle;
import javax.swing.*;

public class Enemy extends Sprite implements Runnable {

    private Boolean moving;
    private Thread t;
    private JLabel enemyLabel;
    private ThePlayer myPlayer;
    private JLabel playerLabel;
    private JButton StartButton;
    private score myScore, myName;
    private bird myBird;
    private int countdown;


    public Enemy() {
        super(0,0, "enemy.gif", 120, 85);
        moving = false;
    }


    public void stopEnemy() {
        this.moving = false;
    }

    public void moveEnemy() {
        this.moving = true;
        t = new Thread(this, "Enemy");
        t.start();
    }

    @Override
    public void run() {

        this.setSpriteName("enemy.gif");
        this.setSpriteName("player.png");

        playerLabel.setIcon(new ImageIcon(
                getClass().getResource("player.png")
        ));

        while (moving) {
            //moving code

            int tX = this.spriteX;
            int tY = this.spriteY;

            tX += Properties.CHARACTER_STEP * 3;
            if (tX > Properties.SCREEN_WIDTH) {
                tX = -1 * this.spriteW;
            }

            this.setSpriteX(tX);
            this.setSpriteY(tY);

            enemyLabel.setLocation(this.spriteX, this.spriteY);

            //detect collision
            Rectangle rPlayer = myPlayer.getRectangle();
            Rectangle rEnemy = this.r;

            if (rPlayer.intersects(rEnemy)) {

                this.moving = false;

                playerLabel.setIcon(new ImageIcon(
                        getClass().getResource("fail.png")
                ));

                System.out.println("score is " + myScore.getScore());

                myBird.resetBird();
                myBird.setMoving(false);
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


    //Getters
    public Boolean getMoving() {
        return this.moving;
    }

    public void setMoving(Boolean moving) {
        this.moving = moving;
    }


    //Setters
    public void setEnemyLabel (JLabel temp) { this.enemyLabel = temp; }

    public void setPlayerLabel (JLabel temp) { this.playerLabel = temp; }

    public void setPlayer (ThePlayer temp) { this.myPlayer = temp; }

    public void setStartButton (JButton temp) { this.StartButton = temp; }

    public void setScore (score temp) { this.myScore = temp; }

    public void setName (score temp) { this.myName = temp; }

    public void setBird (bird temp) { this.myBird = temp; }

    public void setCountDown (int temp) {this.countdown = temp;}
}
