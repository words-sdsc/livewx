package com.websocket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.security.InvalidParameterException;
//import java.util.Random;

public class MulticastListenerThread extends Thread {
    //private static final Random r = new Random();
    private MulticastSocket s;
    //private InetAddress address;
    private static boolean sentinel = true;
    private OutputStream out;

    public MulticastListenerThread(String host, String group, int port,
            OutputStream out) throws InvalidParameterException, IOException,
            UnknownHostException {
        if (port <= 1000) {
            throw new InvalidParameterException("Illegal port value");
        }

        // address is likely to be same for all threads.
        // it would be nice if it could be static.
        // need to make sure its initialized once and only once
        // run() cannot catch checkedExceptions.
        //address = InetAddress.getByName(host);
        s = new MulticastSocket(port);
        s.joinGroup(InetAddress.getByName(group));
        this.out = out;
    }

    public static void stopListening() {
        sentinel = false;
    }

    @Override
    public void run() {
        try {
            // System.out.println("Starting\n");
            while (true == sentinel) {
                byte buf[] = new byte[1024];
                DatagramPacket pack = new DatagramPacket(buf, buf.length);
                s.receive(pack);
                // System.out.println(pack.getAddress().toString() + ":" +
                // pack.getPort());
                out.write(pack.getData(), 0, pack.getLength());
            }
            // System.out.println("Stopping\n");
        } catch (Exception e) {
            // System.out.println(e.getMessage());
        }
    }
}
