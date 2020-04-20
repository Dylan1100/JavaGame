import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.*;

public class ThePlayer extends Sprite{

	final static int CLIENT_PORT = 5656;
	final static int SERVER_PORT = 5556;

	private JLabel playerLabel;
	private item myItem;

	public ThePlayer() {
		super(0,0, "player.png", 84, 84);
	}
	
	public void setPlayerLabel (JLabel plyr) {
		playerLabel = plyr;
	}
	
	public void plyrMove (KeyEvent e) {


		int plyrX = this.spriteX;
		int plyrY = this.spriteY;
		
		if (e.getKeyCode()==KeyEvent.VK_DOWN) {
			plyrY += Properties.CHARACTER_STEP;
			playerLabel.setIcon(new ImageIcon(
					getClass().getResource("playerD.png")
			));
			if (plyrY > Properties.SCREEN_HEIGHT) {
				plyrY = -1 * this.spriteH;
			}
				
		} else if (e.getKeyCode()==KeyEvent.VK_UP) {
			
			plyrY -= Properties.CHARACTER_STEP;
			playerLabel.setIcon(new ImageIcon(
					getClass().getResource("playerU.png")
			));
			if (plyrY < -1 * this.spriteH) {
				plyrY = Properties.SCREEN_HEIGHT;
			}


		} else if (e.getKeyCode()==KeyEvent.VK_LEFT) {
			playerLabel.setIcon(new ImageIcon(
					getClass().getResource("playerL.png")
			));
			plyrX -= Properties.CHARACTER_STEP;
			if (plyrX < -1 * this.spriteW) {
				plyrX = Properties.SCREEN_WIDTH;
			}
			
		} else if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
			plyrX += Properties.CHARACTER_STEP;
			playerLabel.setIcon(new ImageIcon(
					getClass().getResource("playerR.png")
			));
			if (plyrX > Properties.SCREEN_WIDTH) {
				plyrX = -1 * this.spriteW;
			}
		}

		Socket s;

		//comm socket
		try {
			s = new Socket("localhost", SERVER_PORT);
			OutputStream outstream = s.getOutputStream();
			PrintWriter out = new PrintWriter(outstream);

			String command = "UPDATEPLAYER "+Properties.PLAYER+" "+plyrX+" "+plyrY+"\n";
			System.out.println("Sending: " + command);
			out.println(command);
			out.flush();

			s.close();
		} catch (UnknownHostException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (IOException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}

	}
}
