package com.websocket;

import java.io.IOException;
import java.io.StringWriter;
import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocket")
public class WebSocketEndPoint {

    @OnOpen
    public void handleOpen(Session userSession) throws InterruptedException,
            IOException {

        //System.out.println("Client connected: " + userSession);
    }

    @OnClose
    public void handleClose(Session userSession) {
        
        //System.out.println("Client disconnected: " + userSession);
        
        ClientThread thread = _clients.remove(userSession);
        thread.stopSending();
        try {
            thread.join(5000);
        } catch (InterruptedException e) {
            System.err.println("Error waiting for client thread to stop: " + e.getMessage());
        }
        
        synchronized(_clients) {
            if(_clients.isEmpty()) {
                _stopMulticastListening();
            }
        }
    }
    
    @OnMessage
    public void handleMessage(String message, Session userSession)
            throws IOException, InterruptedException {

        if (message.equals("stop")) {
            handleClose(userSession);
        } else if(message.equals("start")) {

            //System.out.println("Client sent start: " + userSession);

            synchronized(_clients) {

                if(_clients.isEmpty()) {
                    _startMulticastListening();
                }

                BlockingQueue<byte[]> writeQueue = new LinkedBlockingQueue<byte[]>();
                _writeQueues.add(writeQueue);
    
                ClientThread thread = new ClientThread(userSession, writeQueue);
                thread.start();
                
                _clients.put(userSession, thread);
            }
        }

    }

    @OnError
    public void handleError(Throwable t) {
        System.err.println("Error: " + t.getMessage());
        t.printStackTrace();
    }

  private static void _loadSources() {
         
      _sources.put("BH", new Source("Boucher Hill",
          "172.16.42.86",
          "233.7.117.114",
          4042,
          "33.33", "-116.92"));

        _sources.put("BMR", new Source("Big Black Mountain",
            "172.16.42.86",
            "233.7.117.125",
            4045,
            "33.16", "-116.811"));
    
        _sources.put("CNMZ3", new Source("Cabrillo National Monument, Zone 3",
            "172.16.42.86",
            "233.7.117.82",
            4026,
            "32.67", "-117.24"));

        _sources.put("HWB", new Source("West Ramona",
            "172.16.42.86",
            "233.7.117.79",
            4021,
            "33.03", "-116.96"));

        _sources.put("LP", new Source("Lyons Peak",
            "172.16.42.86",
            "233.7.117.79",
            4020,
            "32.70", "-116.76"));

        _sources.put("ML", new Source("Mount Laguna",
            "172.16.42.86",
            "233.7.117.111",
            4039,
            "32.89", "-116.42"));

        _sources.put("MW", new Source("Mount Woodson",
            "172.16.42.86",
            "233.7.117.79",
            4024,
            "33.01", "-116.97"));

  
        /* TODO: is this NOAA-NWS?
        _sources.add(new Source("NN",
            "172.16.42.86",
            "233.7.117.82",
            4026));
         */
        
        _sources.put("PA", new Source("Pala",
            "172.16.42.86",
            "233.7.117.104",
            4031,
            "33.35", "-116.98"));

    
        _sources.put("PLC", new Source("Puerta La Cruz",
            "172.16.42.86",
            "233.7.117.123",
            4044,
            "33.32", "-116.68"));

        _sources.put("RM", new Source("Red Mountain",
            "172.16.42.86",
            "233.7.117.106",
            4033,
            "33.40", "-117.19"));

        _sources.put("SCI", new Source("San Clemente Island",
            "172.16.42.86",
            "233.7.117.107",
            4034,
            "32.91", "-118.48"));

        _sources.put("SY", new Source("San Ysabel",
            "172.16.42.86",
            "233.7.117.105",
            4032,
            "33.13", "-116.61"));

        _sources.put("SO", new Source("Sky Oaks",
            "172.16.42.86",
            "233.7.117.113",
            4041,
            "33.38", "-116.62"));

        _sources.put("SMERNS", new Source("SMER North Station",
            "172.16.42.86",
            "233.7.117.128",
            4046,
            "33.46", "-117.17"));

        _sources.put("SDSC", new Source("SDSC",
                "172.16.42.86",
                "233.7.117.110",
                4038,
                "32.88", "-117.24"));

        _sources.put("WS", new Source("Warner Springs",
            "172.16.42.86",
            "233.7.117.119",
            4043,
            "33.27", "-116.64"));

        _sources.put("TP", new Source("Toro Peak",
            "172.16.42.86",
            "233.7.117.102",
            4027,
            "33.52", "-116.43"));

    }

    private static void _startMulticastListening() {

        //System.out.println("start multicast listening");
        
        _sources.clear();
        _loadSources();
        
        _dispatcher = new DispatcherThread();
        _dispatcher.start();
        
        for(Source source : _sources.values()) {
            MulticastListenerThread thread;
            try {
                thread = new MulticastListenerThread(source.host,
                        source.group,
                        source.port,
                        _multicastQueue);
            } catch (InvalidParameterException | IOException e) {
                System.err.println("Error creating multicast thread: " + e.getMessage());
                continue;
            }
            thread.start();
            _listeners.put(source.name, thread);
        }
        
        //System.out.println("done start multicast listening");

    }
    
    private static void _stopMulticastListening() {
        
        //System.out.println("stop multicast listening");

        _dispatcher.stopDispatching();
        try {
            _dispatcher.join(5000);
        } catch(InterruptedException e) {
            System.err.println("Error joining dispatcher thread: " + e.getMessage());
            e.printStackTrace();
        }
        _dispatcher = null;

        for(MulticastListenerThread thread : _listeners.values()) {
            thread.stopListening();
            try {
                thread.join(1000);
            } catch (InterruptedException e) {
                System.err.println("Error joining multicast thread: " + e.getMessage());
                e.printStackTrace();
            }
        }
        _listeners.clear();
    
        _multicastQueue.clear();

        //System.out.println("done stopping multicast listening");
    }
    
    private static class ClientThread extends Thread {

        ClientThread(Session session, BlockingQueue<byte[]> queue) {
            _session = session;
            _queue = queue;
        }

        @Override
        public void run() {
            
            Map<String,String> data = new HashMap<String,String>();
            Set<String> stationsSent = new HashSet<String>();

            try {
                while (_keepSending.get()) {

                    final byte[] bytes = _queue.take();                    
                    final String str = new String(bytes);
                    
                    if(str.trim().isEmpty()) {
                        continue;
                    }
                                        
                    //System.out.println("read " + str);
                    
                    for (String line : str.split("\n")) {
                        
                        String parts[] = line.split("[:\\-]+");
                        if(parts.length < 2) {
                            //System.err.println("WARNING: could not find name in: " +
                                //line);
                            continue;
                        }
                        
                        String stationName = parts[1];
                        
                        String measurementType = parts[3];

                        Source station = _sources.get(stationName);

                        data.put("Name", station.name);
                        data.put("mtype", measurementType);
                        
                        if(!stationsSent.contains(stationName)) {
                            data.put("lat", station.lat);
                            data.put("lng", station.lng);
                            stationsSent.add(stationName);
                        }

                        for (String temp : line.split(",")) {
                            //System.out.println(temp);
                            String[] value = temp.split("=");
                            if (value.length > 1) {
                                data.put(value[0], value[1]);
                            }
                        }
                    }
                    
                    synchronized(_session) {
                        if(_keepSending.get() && _session.isOpen()) {
                            _session.getBasicRemote().sendText(
                                    _dataToJson(data));
                        } else {
                            break;
                        }
                    }
                    
                    data.clear();

                }
            } catch (InvalidParameterException | IOException
                    | InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        public void stopSending() {
            _keepSending.set(false);
        }

        private String _dataToJson(Map<String,String> data) {

            JsonObjectBuilder dataJson = Json.createObjectBuilder();
            for(Map.Entry<String, String> entry: data.entrySet()) {
                dataJson.add(entry.getKey(), entry.getValue());
            }
            
            JsonObject result = Json.createObjectBuilder().add(
                    "message", dataJson).build();
            StringWriter sw = new StringWriter();
            try (JsonWriter writer = Json.createWriter(sw)) {
                writer.write(result);
            }
            String str = sw.toString();
            //System.out.println(str);
            return str;
        }
       
        private BlockingQueue<byte[]> _queue;
        private Session _session;
        private AtomicBoolean _keepSending = new AtomicBoolean(true);
    }

    private static class DispatcherThread extends Thread {
        
        @Override
        public void run() {

            try {
                while(!_stop.get()) {
            
                    byte[] data = _multicastQueue.take();

                    //synchronized(_writeQueues) {
                        for(BlockingQueue<byte[]> writeQueue: _writeQueues) {
                            writeQueue.put(data);    
                        }
                    //}
                }
            } catch(InterruptedException e) {
                System.err.println("Interrupted exception in Dispatcher thread: " +  e.getMessage());
                e.printStackTrace();    
            }
        }

        public void stopDispatching() {
            _stop.set(true);
        }

        private AtomicBoolean _stop = new AtomicBoolean(false);
    }
    
    private static class Source {

        public Source(String name, String host, String group, int port, String lat, String lng) {
            this.name = name;
            this.host = host;
            this.group = group;
            this.port = port;
            this.lat = lat;
            this.lng = lng;
        }
        
        public String name;        
        public String host;
        public String group;
        public int port;
        public String lat;
        public String lng;
        
    }
    
    private static Map<Session,ClientThread> _clients = Collections
            .synchronizedMap(new HashMap<Session,ClientThread>());
    
    private static BlockingQueue<byte[]> _multicastQueue = new LinkedBlockingQueue<byte[]>();
    
    private static Map<String,MulticastListenerThread> _listeners =
            Collections.synchronizedMap(new HashMap<String,MulticastListenerThread>());

    private static Set<BlockingQueue<byte[]>> _writeQueues = Collections
            .synchronizedSet(new HashSet<BlockingQueue<byte[]>>());

    private static Map<String,Source> _sources = new HashMap<String,Source>();

    private static DispatcherThread _dispatcher;
}
