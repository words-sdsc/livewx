package com.websocket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.security.InvalidParameterException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class MulticastListenerThread extends Thread {
    private MulticastSocket s;
    private AtomicBoolean sentinel = new AtomicBoolean(true);
    private BlockingQueue<byte[]> _queue;

    public MulticastListenerThread(String host, String group, int port,
            BlockingQueue<byte[]> queue) throws InvalidParameterException, IOException,
            UnknownHostException {
        if (port <= 1000) {
            throw new InvalidParameterException("Illegal port value");
        }

        s = new MulticastSocket(port);
        s.joinGroup(InetAddress.getByName(group));
        _queue = queue;
    }

    public void stopListening() {
        sentinel.set(false);
    }

    @Override
    public void run() {
        try {
            while (sentinel.get()) {
                byte buf[] = new byte[1024];
                DatagramPacket pack = new DatagramPacket(buf, buf.length);
                s.receive(pack);
                _queue.put(pack.getData());
            }
        } catch (Exception e) {
            // System.out.println(e.getMessage());
        }
    }
}
