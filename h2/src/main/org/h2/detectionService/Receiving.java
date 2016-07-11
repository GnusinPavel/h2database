package org.h2.detectionService;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Pavel Kulkov  on 11.07.2016.
 */
public class Receiving extends Thread{
        private final int PORT = 4445;
        //private String IP = new String("224.0.0.0");
        private DatagramSocket socket;
        private InetAddress group;


        public Receiving() throws IOException {
            socket = new DatagramSocket(PORT);
            //group = InetAddress.getByName(IP);
        }


        public void run() {
            try {
                //socket.joinGroup(group);
                DatagramPacket packet;
                while (true) {
                    byte[] buf = new byte[256];
                    packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);
                    String received = new String(packet.getData(),packet.getOffset(),packet.getLength());
                    if(received.length()!=0 && received.substring(0,3).equals("h2o")){
                    //    Main.nodes.add(received.substring(3));
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                socket.close();
            }
        }
    }