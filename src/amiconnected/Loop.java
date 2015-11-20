package amiconnected;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leonhard Künzler
 */
public class Loop extends Thread {

    private final Gui frame;
    private final String adress;
    private Connecter c;
    private boolean running;

    public Loop(Gui frame, String adress) {
        this.frame = frame;
        this.adress = adress;
        running = true;
    }

    @Override
    public void run() {
        while (running) {
            try {
                c = new Connecter(frame, adress);
                c.start();
                c.join();
                sleep(300);
            } catch (InterruptedException ex) {
                //frame.message(ex.toString());
            }            
        }
    }

    public void kill() {
        running = false;
        c.interrupt();
        interrupt();
    }
}
