import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class getPlayerThread implements Runnable{
    final static int CLIENT_PORT = 5656;
    final static int SERVER_PORT = 5556;

    public void run(){
        Socket s;

        while (true) {
            try {
                s = new Socket("localhost", SERVER_PORT);
                OutputStream outstream = s.getOutputStream();
                PrintWriter out = new PrintWriter(outstream);

                String command = "GETPLAYER "+Properties.PLAYER+"\n";
                System.out.println("Sending: " + command);
                out.println(command);
                out.flush();

                s.close();
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {
                Thread.sleep(500);
            } catch(Exception e) {

            }

        }
    }
}
