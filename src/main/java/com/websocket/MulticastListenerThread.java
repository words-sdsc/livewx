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
    private MulticastSocket _socket;
    private InetAddress _groupInetAddress;
    private AtomicBoolean _sentinel = new AtomicBoolean(true);
    private BlockingQueue<byte[]> _queue;

    public MulticastListenerThread(String host, String group, int port,
            BlockingQueue<byte[]> queue) throws InvalidParameterException, IOException,
            UnknownHostException {
        if (port <= 1000) {
            throw new InvalidParameterException("Illegal port value");
        }

        _groupInetAddress = InetAddress.getByName(group);
        _socket = new MulticastSocket(port);
        _socket.joinGroup(_groupInetAddress);
        _queue = queue;
    }

    public void stopListening() {
        _sentinel.set(false);
    }

    @Override
    public void run() {
        try {
            while (_sentinel.get()) {
                byte buf[] = new byte[1024];
                DatagramPacket pack = new DatagramPacket(buf, buf.length);
                _socket.receive(pack);
                _queue.put(pack.getData());
            }
        } catch (Exception e) {
            // System.out.println(e.getMessage());
        } finally {
            try {
                _socket.leaveGroup(_groupInetAddress);
            } catch (IOException e) {
                System.err.println("Error leaving multicast group: " + e.getMessage());
                e.printStackTrace();
            }
            _socket.close();
            _socket = null;
        }
    }
}
