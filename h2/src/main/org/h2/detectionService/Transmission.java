package org.h2.detectionservice;

import java.io.IOException;
import java.net.*;

/**
 * Created by Pavel Kulkov  on 11.07.2016.
 */

public class Transmission {
    private DatagramSocket socket = null;
    private DatagramPacket packet = null;
    private int PORT;
    private byte IP[] = new byte[3];
    private String addressGroup;

    public Transmission() {
        try {
            PORT = 4445;
            IP = InetAddress.getLocalHost().getAddress();
            IP[3] = (byte) 255;
            addressGroup = InetAddress.getByAddress(IP).getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public Transmission(int PORT) {
        this.PORT = PORT;
        try {
            IP = InetAddress.getLocalHost().getAddress();
            IP[3] = (byte) 255;
            addressGroup = InetAddress.getByAddress(IP).getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            socket = new DatagramSocket();
            byte[] buf;
            String msg = "node" + InetAddress.getLocalHost().getHostAddress() + "&";
            buf = msg.getBytes();
            InetAddress group = InetAddress.getByName(addressGroup);
            packet = new DatagramPacket(buf, buf.length, group, PORT);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }
}