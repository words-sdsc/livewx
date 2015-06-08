package com.websocket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    static Set<Session> users = Collections
            .synchronizedSet(new HashSet<Session>());

    String host_sdsc = "172.16.42.86";
    String group_sdsc = "233.7.117.110";
    int port_sdsc = 4038;

    String host_ws = "172.16.42.86";
    String group_ws = "233.7.117.119";
    int port_ws = 4043;

    String host_tp = "172.16.42.86";
    String group_tp = "233.7.117.102";
    int port_tp = 4037;

    String host_bmr = "172.16.42.86";
    String group_bmr = "233.7.117.125";
    int port_bmr = 4045;

    String host_bh = "172.16.42.86";
    String group_bh = "233.7.117.114";
    int port_bh = 4042;

    String host_lp = "172.16.42.86";
    String group_lp = "233.7.117.79";
    int port_lp = 4020;

    String host_ml = "172.16.42.86";
    String group_ml = "233.7.117.111";
    int port_ml = 4039;

    String host_mw = "172.16.42.86";
    String group_mw = "233.7.117.79";
    int port_mw = 4024;

    String host_cnmz3 = "172.16.42.86";
    String group_cnmz3 = "233.7.117.111";
    int port_cnmz3 = 4039;

    String host_nn = "172.16.42.86";
    String group_nn = "233.7.117.82";
    int port_nn = 4026;

    String host_pa = "172.16.42.86";
    String group_pa = "233.7.117.104";
    int port_pa = 4031;

    String host_plc = "172.16.42.86";
    String group_plc = "233.7.117.123";
    int port_plc = 4044;

    String host_rm = "172.16.42.86";
    String group_rm = "233.7.117.106";
    int port_rm = 4033;

    String host_sci = "172.16.42.86";
    String group_sci = "233.7.117.107";
    int port_sci = 4034;

    String host_sy = "172.16.42.86";
    String group_sy = "233.7.117.111";
    int port_sy = 4039;

    String host_so = "172.16.42.86";
    String group_so = "233.7.117.113";
    int port_so = 4041;

    String host_smerns = "172.16.42.86";
    String group_smerns = "233.7.117.128";
    int port_smerns = 4046;

    String host_hwb = "172.16.42.86";
    String group_hwb = "233.7.117.79";
    int port_hwb = 4010;

    NewThread SDSC;
    NewThread WS;
    NewThread TP;
    NewThread BMR;
    NewThread BH;
    NewThread LP;
    NewThread MG;
    NewThread ML;
    NewThread MW;
    NewThread CNMZ3;
    NewThread NN;
    NewThread PA;
    NewThread PLC;
    NewThread RM;
    NewThread SCI;
    NewThread SO;
    NewThread SMERNS;
    NewThread HWB;

    boolean connect = true;

    @OnOpen
    public void handleOpen(Session userSession) throws InterruptedException,
            IOException {
        System.out.println("Server get connected");
        // ConnectToMulticastListener();
        users.add(userSession);
        // System.out.println(ConnectToMulticastListener());
    }

    @OnClose
    public void handleClose(Session userSession) {
        System.out.println("Client is now disconnected!");
        SDSC.stop();
        WS.stop();
        TP.stop();
        BMR.stop();
        BH.stop();
        LP.stop();
        ML.stop();
        MW.stop();
        CNMZ3.stop();
        NN.stop();
        PA.stop();
        PLC.stop();
        RM.stop();
        SCI.stop();
        SO.stop();
        SMERNS.stop();
        HWB.stop();
        users.remove(userSession);
    }

    public void SendResult(Session session) {

    }

    @OnMessage
    public void handleMessage(String message, Session userSession)
            throws IOException, InterruptedException {

        System.out.println("handleMessage: " + message + " userSession: "
                + userSession);

        if (message.equals("stop")) {
            handleClose(userSession);
            System.out.println("Client is now disconnected!");
            SDSC.stop();
            WS.stop();
            TP.stop();
            BMR.stop();
            BH.stop();
            LP.stop();
            ML.stop();
            MW.stop();
            CNMZ3.stop();
            NN.stop();
            PA.stop();
            PLC.stop();
            RM.stop();
            SCI.stop();
            SO.stop();
            SMERNS.stop();
            HWB.stop();
            System.out.println("connection closed");
            
        } else {
            
            SDSC = new NewThread("SDSC", host_sdsc, group_sdsc, port_sdsc,
                    userSession);
            SDSC.start();

            WS = new NewThread("WS", host_ws, group_ws, port_ws, userSession);
            WS.start();

            TP = new NewThread("TP", host_tp, group_tp, port_tp, userSession);
            TP.start();

            BMR = new NewThread("BMR", host_bmr, group_bmr, port_bmr,
                    userSession);
            BMR.start();

            BH = new NewThread("BH", host_bh, group_bh, port_bh, userSession);
            BH.start();

            LP = new NewThread("LP", host_lp, group_lp, port_lp, userSession);
            LP.start();

            ML = new NewThread("ML", host_ml, group_ml, port_ml, userSession);
            ML.start();

            MW = new NewThread("MW", host_mw, group_mw, port_mw, userSession);
            MW.start();

            CNMZ3 = new NewThread("CNMZ3", host_cnmz3, group_cnmz3, port_cnmz3,
                    userSession);
            CNMZ3.start();

            NN = new NewThread("NN", host_nn, group_nn, port_nn, userSession);
            NN.start();

            PA = new NewThread("PA", host_pa, group_pa, port_pa, userSession);
            PA.start();

            PLC = new NewThread("PLC", host_plc, group_plc, port_plc,
                    userSession);
            PLC.start();

            RM = new NewThread("RM", host_rm, group_rm, port_rm, userSession);
            RM.start();

            SCI = new NewThread("SCI", host_sci, group_sci, port_sci,
                    userSession);
            SCI.start();

            SMERNS = new NewThread("SMERNS", host_smerns, group_smerns,
                    port_smerns, userSession);
            SMERNS.start();

            SO = new NewThread("SO", host_so, group_so, port_so, userSession);
            SO.start();

            HWB = new NewThread("HWB", host_hwb, group_hwb, port_hwb,
                    userSession);
            HWB.start();
        }

    }

    @OnError
    public void handleError(Throwable t) {
        t.printStackTrace();
    }

    class NewThread extends Thread {

        private String name;
        private String host;
        private String group;
        private int port;
        private Session userSession;
        private Map<String,String> data = new HashMap<String,String>();

        NewThread(String Name, String Host, String Group, int Port,
                Session UserSession) {
            super(Name);
            name = Name;
            host = Host;
            group = Group;
            port = Port;
            userSession = UserSession;
            data.put("Name", name);
        }

        @Override
        public void run() {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            MulticastListenerThread mlt;
            try {
                mlt = new MulticastListenerThread(host, group, port, os);
                mlt.start();
                // avoid busy waiting
                while (true) {
                    Thread.sleep(3000);
                    String str = os.toString();
                    os.reset();

                    for (String piece : str.split("\n")) {
                        data.put("name", name);

                        for (String temp : piece.split(",")) {
                            System.out.println(temp);
                            String[] value = temp.split("=");
                            if (value.length > 1) {
                                data.put(value[0], value[1]);
                            }
                        }
                    }
                    synchronized (userSession) {
                        userSession.getBasicRemote().sendText(
                                dataToJson());
                    }
                }

            } catch (InvalidParameterException | IOException
                    | InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        private String dataToJson() {

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
            System.out.println(str);
            return str;
        }
    }
}
