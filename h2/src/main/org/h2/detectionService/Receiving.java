package org.h2.detectionservice;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

/**
 * Created by Dmitry Konyakhin  on 11.07.2016.
 */
public class Receiving {
    private int PORT;
    private String myIP;
    private DatagramSocket socket;
    private int timeout;
    private boolean cluster;


    public Receiving() throws IOException {
        this.PORT = 4445;
        myIP = InetAddress.getLocalHost().getHostAddress();
        cluster = false;
        timeout = 200;
    }

    public Receiving(int PORT) throws IOException {
        this.PORT = PORT;
        myIP = InetAddress.getLocalHost().getHostAddress();
        cluster = false;
        timeout = 200;
    }

    public Receiving(int PORT, int timeout) throws IOException {
        this.PORT = PORT;
        this.timeout = timeout;
        myIP = InetAddress.getLocalHost().getHostAddress();
        cluster = false;
    }


    public void run() {
        try {
            socket = new DatagramSocket(PORT);
            DatagramPacket packet;
            byte[] buf = new byte[256];
            packet = new DatagramPacket(buf, buf.length);
            socket.setSoTimeout(timeout);
            socket.receive(packet);
            String received = new String(packet.getData(), packet.getOffset(), packet.getLength());
            System.out.println(received);
            if (received.length() != 0) {
                received = received.substring(4, received.length() - 1);
                if (!Main.nodes.contains(received) && received != myIP) {
                    cluster = true;
                    Main.nodes.add(received);
                }
            }

        } catch (SocketTimeoutException ste) {
            System.out.println(ste.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }

    public boolean isCluster() {
        return cluster;
    }
}