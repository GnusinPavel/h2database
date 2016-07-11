package org.h2.detectionService;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by Pavel Kulkov  on 11.07.2016.
 */
public class Transmission extends Thread {
        private DatagramSocket socket = null;
        private DatagramPacket packet = null;
        private final int PORT = 4445;
        private String addressGroup = "10.6.116.255";

        public Transmission(){
            try {
                socket = new DatagramSocket();
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }

        public void run(){
            for(int i=0;i<10;i++){
                try {
                    byte[] buf = new byte[256];
                    String msg = "h2o"+InetAddress.getLocalHost().getHostAddress();
                    buf = msg.getBytes();
                    InetAddress group = InetAddress.getByName(addressGroup);
                    packet = new DatagramPacket(buf, buf.length, group, PORT);
                    socket.send(packet);
                    sleep(500);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            socket.close();
        }
    }

