import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.swing.*;

public class Prep1 extends JFrame implements ActionListener, KeyListener {

	final static int CLIENT_PORT = 5656;
	final static int SERVER_PORT = 5556;

	private static final long serialVersionUID = 1L;

	//declare sprite object
	private ThePlayer myPlayer;
	private Enemy myEnemy;
	private bird myBird;
	private background myBg;
	private item myItem;
	private GameTracking gameTracking;
	private score myScore;
	private score myName;
    private int countdown = 1;

    //image icons to hold the png graphics
	private ImageIcon playerImage;
	private ImageIcon enemyImage;
	private ImageIcon birdImage;
	private ImageIcon bgImage;
	private ImageIcon itemImage;

	//labels to display the image icons
	private JLabel playerLabel, enemyLabel, bgLabel, itemLabel, birdLabel;

	//button to control the enemy
	private JButton StartButton, exitButton;

	//container for the graphics
	private Container content;


	public Prep1() throws IOException {

		//initialize variables
		myPlayer = new ThePlayer();
		myEnemy = new Enemy();
		myBird = new bird();
		gameTracking = new GameTracking();
		myScore = new score();
		myItem = new item();

		//Labels
        playerLabel = new JLabel();
        enemyLabel = new JLabel();
		itemLabel = new JLabel();
        birdLabel = new JLabel();

        //Buttons
		StartButton = new JButton();
		exitButton = new JButton();

		//Item Setters
		myItem.setItemLabel(itemLabel);
		myItem.setPlayer(myPlayer);
		myItem.setPlayerLabel(playerLabel);
		myItem.setStartButton(StartButton);
		myItem.setCountDown(countdown);
		myItem.setBird(myBird);
		myItem.setScore(myScore);

		//Background
		myBg = new background();
		bgLabel = new JLabel();

		//Enemy Setters
		myEnemy.setEnemyLabel(enemyLabel);
		myEnemy.setPlayer(myPlayer);
		myEnemy.setPlayerLabel(playerLabel);
		myEnemy.setStartButton(StartButton);
		myEnemy.setBird(myBird);
		myEnemy.setScore(myScore);
        myEnemy.setName(myName);

        //Bird Setter
		myBird.setBirdLabel(birdLabel);
		myBird.setPlayer(myPlayer);
		myBird.setPlayerLabel(playerLabel);
		myBird.setStartButton(StartButton);
		myBird.setScore(myScore);
		myBird.setName(myName);

		//Sprite ImageIcons
		playerImage = new ImageIcon(getClass().getResource(myPlayer.getSpriteName()));
		enemyImage = new ImageIcon(getClass().getResource(myEnemy.getSpriteName()));
		birdImage = new ImageIcon(getClass().getResource(myBird.getSpriteName()));
		bgImage = new ImageIcon(getClass().getResource(myBg.getSpriteName()));
		itemImage = new ImageIcon(getClass().getResource(myItem.getSpriteName()));

        //ContentPanel
		content = getContentPane();

		//set up GUI
		setSize(Properties.SCREEN_WIDTH, Properties.SCREEN_HEIGHT);
		content.setBackground(Color.white);
		setResizable(false);
		setLayout(null);


		//set up bg image
		myBg.setBgLabel(bgLabel);
		myBg.setSpriteX(0);
		myBg.setSpriteY(0);
		bgLabel.setIcon(bgImage);
		bgLabel.setSize(myBg.getSpriteW(), myBg.getSpriteH());
		bgLabel.setLocation(myBg.getSpriteX(), myBg.getSpriteY());


		//setup item
		myItem.setItemLabel(itemLabel);
		myItem.setSpriteX(550);
		myItem.setSpriteY(90);
		itemLabel.setIcon(itemImage);
		itemLabel.setSize(myItem.getSpriteW(), myItem.getSpriteH());
		itemLabel.setLocation(myItem.getSpriteX(), myItem.getSpriteY());

		add(itemLabel);


		//setup the player
		myPlayer.setPlayerLabel(playerLabel);
		myPlayer.setSpriteX(400);
		myPlayer.setSpriteY(530);
		playerLabel.setIcon(playerImage);
		playerLabel.setSize(myPlayer.getSpriteW(), myPlayer.getSpriteH());
		playerLabel.setLocation(myPlayer.getSpriteX(), myPlayer.getSpriteY());

		add(playerLabel);


		//setup the enemy
		myEnemy.setSpriteX(100);
		myEnemy.setSpriteY(370);
		enemyLabel.setIcon(enemyImage);
		enemyLabel.setSize(myEnemy.getSpriteW(), myEnemy.getSpriteH());
		enemyLabel.setLocation(myEnemy.getSpriteX(), myEnemy.getSpriteY());

		add(enemyLabel);


		//Bird needs to be set off-screen
		myBird.setSpriteX(-150);
		myBird.setSpriteY(600);
		birdLabel.setIcon(birdImage);
		birdLabel.setSize(myBird.getSpriteW(), myBird.getSpriteH());
		birdLabel.setLocation(myBird.getSpriteX(), myBird.getSpriteY());

		add(birdLabel);


		//Setting up the start button
		StartButton.setLocation(Properties.SCREEN_WIDTH - 800, Properties.SCREEN_HEIGHT - 700);
		StartButton.setSize(100, 40);
		StartButton.setText("Start");
		StartButton.setForeground(Color.green);
		StartButton.addActionListener(this);
		StartButton.setFocusable(false);
        StartButton.addActionListener(this);
        StartButton.setFocusable(false);

		add(StartButton);


		//setting up the exit button
		exitButton.setLocation(Properties.SCREEN_WIDTH - 800, Properties.SCREEN_HEIGHT - 500);
		exitButton.setSize(100, 40);
		exitButton.setText("Quit Game");
		exitButton.setForeground(Color.orange);
		exitButton.addActionListener(this);
		exitButton.setFocusable(false);

		add(exitButton);


		//add the bg image
		add(bgLabel);

		content.addKeyListener(this);
		content.setFocusable(true);


		getPlayerThread getPlayerThread = new getPlayerThread();
		Thread t1 = new Thread(getPlayerThread);
		t1.start();

		getEnemyThread getEnemyThread = new getEnemyThread();
		Thread t2 = new Thread(getEnemyThread);
		t2.start();

		gameServerThread gameServerThread = new gameServerThread(myPlayer, myEnemy, StartButton,
				exitButton, playerLabel, enemyLabel);
		Thread t3 = new Thread(gameServerThread);
		t3.start();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) throws IOException {
		int reply = JOptionPane.showConfirmDialog(null,
				"Welcome to my game. You will control a brave platypus that is just trying to \n" +
						"get a cup of coffee. The objective of this game is to obtain each cup of coffee that\n" +
						" that appears on the screen. However, keep in mind that the platypus's natural predator, the\n" +
						"fox, will attempt to hinder your progress (by killing you). In addition, be on the lookout for\n" +
						"the nasty hawk that likes to pray on players that linger for too long (10 seconds) without\n" +
						"picking up a coffee. Do your best to avoid the fox and the hawk while picking up coffee.\n" +
						"Movement controls: Up, Down, Left, Right directional keys\n" +
                        "Press \"Yes\" to start.\n" +
                        "Have fun!",
				"Welcome", JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {
			Prep1 myGame = new Prep1();
			myGame.setVisible(true);		}
		else {
			System.exit(0);
			}
		}


	@Override
	public void actionPerformed(ActionEvent e) {

	    //Adding the start button's functions
		if (e.getSource() == StartButton && !gameTracking.getGameOn()) {
			gameTracking.setGameOn(true);

			if (myEnemy.getMoving()) {

				myEnemy.stopEnemy();
				myBird.stopBird();
				myItem.stopExist();
				StartButton.setText("Start");
				StartButton.setForeground(Color.green);
			} else {
				myEnemy.moveEnemy();
				myBird.moveBird();
				myItem.itemExists();
				StartButton.setText("Pause");
				StartButton.setForeground(Color.red);
			}
		} else if (e.getSource() == StartButton && gameTracking.getGameOn()){
			gameTracking.setGameOn(false);
		}
		 else if (e.getSource() == exitButton) {
			System.exit(0);
		}
	}


	@Override
	public void keyTyped(KeyEvent e) { }

	@Override
	public void keyPressed(KeyEvent e) {
		myPlayer.plyrMove(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}