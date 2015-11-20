/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amiconnected;

import java.awt.Toolkit;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.SwingUtilities;

/**
 *
 * @author Leonhard
 */
public class Connecter extends Thread {

    private final Gui frame;
    private final String adress;

    public Connecter(Gui frame, String adress) {
        this.frame = frame;
        this.adress = adress;
    }

    private void setTook(final long time) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setTook(time);
            }
        });
    }

    private void setConnected(final boolean b) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setConnected(b);
            }
        });

    }

    @Override
    public void run() {
        try {
            //make a URL to a known source
            URL url;
            long ontime, fulltime;
            url = new URL(adress);

            //open a connection to that source
            long time = System.currentTimeMillis();
            HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();

            //trying to retrieve data from the source. If there
            //is no connection, this line will fail
            urlConnect.setConnectTimeout(500);
            //Object objData = urlConnect.getContent();
            urlConnect.connect();
            time = System.currentTimeMillis() - time;
            if (frame.getSound()) {
                new Thread() {
                    public void run() {
                        Toolkit.getDefaultToolkit().beep();
                    }
                }.start();
            }
            setConnected(true);
            setTook(time);
            urlConnect.disconnect();
        } catch (IOException e) {
            System.out.println(e);
            setConnected(false);
        }
    }

    public void kill() {
        interrupt();
    }

}
