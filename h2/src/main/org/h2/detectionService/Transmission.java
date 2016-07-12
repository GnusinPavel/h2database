package org.h2.detectionService;

import java.io.IOException;
import java.net.*;

public class Transmission {
    private DatagramSocket socket = null;
    private DatagramPacket packet = null;
    private int PORT = 4445;
    private byte IP[] = new byte[3];
    private String addressGroup;

    public Transmission(){
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        try {
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
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

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
            byte[] buf;
            String msg = "node" + InetAddress.getLocalHost().getHostAddress();
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

