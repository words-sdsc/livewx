package com.websocket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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


@ServerEndpoint(value="/websocket")
public class WebSocketEndPoint {

	static Set<Session> users = Collections.synchronizedSet(new HashSet<Session>());
	

	String host_sdsc = "172.16.42.86";
	String group_sdsc = "233.7.117.107";
	int port_sdsc = 4034;
	
	
	String host_ws = "172.16.42.86";
	String group_ws = "233.7.117.125";
	int port_ws = 4045;
	
	//MulticastListenerThread test21 = new MulticastListenerThread("172.16.42.86", "233.7.117.123", 4044, fos21);
	//MulticastListenerThread test22 = new MulticastListenerThread("172.16.42.86", "233.7.117.125", 4045, fos22);
	
	
	String host_tp = "172.16.42.86";
	String group_tp = "233.7.117.111";
	int port_tp = 4039;
	
	NewThread SDSC;
	NewThread WS;
	NewThread TP;
	
	boolean connect = true;
	
	
	@OnOpen
	public void handleOpen(Session userSession) throws InterruptedException, IOException{
		System.out.println("Server get connected");
		//ConnectToMulticastListener();
		users.add(userSession);
		//System.out.println(ConnectToMulticastListener());
	/*	while(true){

			NewThread SDSC = new NewThread("SDSC", host_sdsc, group_sdsc, port_sdsc, userSession);
			SDSC.start();
			Thread.sleep(1000);
			SDSC.stop(); 
			
			String data = "";
			String data_ucsd="";
			String data_ws="";
			String data_tp="";
			
			
			while(data_ucsd.equals("")){
				OutputStream os_sdsc = new ByteArrayOutputStream();
				MulticastListenerThread mlt_ucsd= new MulticastListenerThread(host_ucsd, group_ucsd, port_ucsd, os_sdsc);
				mlt_ucsd.start();
				Thread.sleep(100);
				data_ucsd = os_sdsc.toString();
				if(!data_ucsd.equals(""))
					data += "SDSC("+data_ucsd+"*)";
				mlt_ucsd.stop();			
			}
			
			while(data_ws.equals("")){
				OutputStream os = new ByteArrayOutputStream();
				MulticastListenerThread mlt_ws= new MulticastListenerThread(host_ws, group_ws, port_ws, os);
				mlt_ws.start();
				Thread.sleep(100);
				data_ws = os.toString();
				if(!data_ws.equals(""))
					data += "WS("+data_ws+"*)";
				mlt_ws.stop();	
			}
			if(!data.equals("")){
				System.out.println(data);
				userSession.getBasicRemote().sendText(buildJsonData("DATA", data));
			}
			//Thread.sleep(1000);
			 
		}*/
		
	}

	
	@OnClose
	public void handleClose(Session userSession){
		System.out.println("Client is now disconnected!");
		SDSC.stop(); 
		WS.stop();
		TP.stop();
		users.remove(userSession);
	}

	public void SendResult(Session session){
		
	}
	@OnMessage
	public void handleMessage(String message, Session userSession) throws IOException, InterruptedException{
		if(message.equals("stop")){
			handleClose(userSession);
		}else{
			Stations SDSC_Station = new Stations("SDSC");
			SDSC = new NewThread("SDSC", host_sdsc, group_sdsc, port_sdsc, userSession, SDSC_Station);
			SDSC.start();
	
			Stations WS_Station = new Stations("WS");
			WS = new NewThread("WS", host_ws, group_ws, port_ws, userSession,WS_Station);
			WS.start();
			
			Stations TP_Station = new Stations("TP");
			TP = new NewThread("TP", host_tp, group_tp, port_tp, userSession, TP_Station);
			TP.start();
		}

	}

	@OnError
	public void handleError(Throwable t){
		t.printStackTrace();
	}
	
	class NewThread extends Thread{

		private String name;
		private String host;
		private String group;
		private int port;
		private Session userSession;
		private Stations station;
		
		NewThread(String Name, String Host, String Group, int Port, Session UserSession, Stations s){
			super(Name);
			name = Name;
			host = Host;
			group = Group;
			port = Port;
			userSession = UserSession;
			station = new Stations(s);
		}
		public void run(){
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			MulticastListenerThread mlt;
			try {
				mlt = new MulticastListenerThread(host, group, port, os);
				mlt.start();
				int i=10;
				// avoid busy waiting
				while(true){
					Thread.sleep(3000);
					String str = os.toString();
					os.reset();
					
					for(String piece: str.split("\n")){
						Map<String,String>data = new HashMap<>();
						data.put("name", name);
						
						for(String temp: piece.split(",")){
							System.out.println(temp);
							if(temp.contains("Sn")){
								String[] value = temp.split("=");
								if(value.length>1){
									data.put("Sn", value[1]);
									station.setSn(value[1]);
								}
							}else if(temp.contains("Sm")){
								String[] value = temp.split("=");
								if(value.length>1){
									data.put("Sm", value[1]);
									station.setSm(value[1]);
								}
							}else if(temp.contains("Sx")){
								String[] value = temp.split("=");
								if(value.length>1){
									data.put("Sx", value[1]);
									station.setSx(value[1]);
								}
							}else if(temp.contains("Dn")){
								String[] value = temp.split("=");
								if(value.length>1){
									data.put("Dn", value[1]);
									station.setDn(value[1]);
								}
							}else if(temp.contains("Dm")){
								String[] value = temp.split("=");
								if(value.length>1){
									data.put("Dm", value[1]);
									station.setDm(value[1]);
								}
							}else if(temp.contains("Dx")){
								String[] value = temp.split("=");
								if(value.length>1){
									data.put("Dx", value[1]);
									station.setDx(value[1]);
								}
							}else if(temp.contains("Pa")){
								String[] value = temp.split("=");
								if(value.length>1){
									data.put("Pa", value[1]);
									station.setPa(value[1]);
								}
							}else if(temp.contains("Ta")){
								String[] value = temp.split("=");
								if(value.length>1){
									data.put("Ta", value[1]);
									station.setTa(value[1]);
								}
							}else if(temp.contains("Tp")){
								String[] value = temp.split("=");
								if(value.length>1){
									data.put("Tp", value[1]);
									station.setTp(value[1]);
								}
							}else if(temp.contains("Ua")){
								String[] value = temp.split("=");
								if(value.length>1){
									data.put("Ua", value[1]);
									station.setUa(value[1]);
								}
							}else if(temp.contains("Rc")){
								String[] value = temp.split("=");
								if(value.length>1){
									data.put("Rc", value[1]);
									station.setRc(value[1]);
								}
							}else if(temp.contains("Rd")){
								String[] value = temp.split("=");
								if(value.length>1){
									data.put("Rd", value[1]);
									station.setRd(value[1]);
								}
							}else if(temp.contains("Ri")){
								String[] value = temp.split("=");
								if(value.length>1){
									data.put("Ri", value[1]);
									station.setRi(value[1]);
								}
							}else if(temp.contains("Rp")){
								String[] value = temp.split("=");
								if(value.length>1){
									data.put("Rp", value[1]);
									station.setRp(value[1]);
								}
							}else if(temp.contains("Hc")){
								String[] value = temp.split("=");
								if(value.length>1){
									data.put("Hc", value[1]);
									station.setHc(value[1]);
								}
							}else if(temp.contains("Hd")){
								String[] value = temp.split("=");
								if(value.length>1){
									data.put("Hd", value[1]);
									station.setHd(value[1]);
								}
							}else if(temp.contains("Hi")){
								String[] value = temp.split("=");
								if(value.length>1){
									data.put("Hi", value[1]);
									station.setHi(value[1]);
								}
							}else if(temp.contains("Hp")){
								String[] value = temp.split("=");
								if(value.length>1){
									data.put("Hp", value[1]);
									station.setHp(value[1]);
								}
							}else if(temp.contains("Th")){
								String[] value = temp.split("=");
								if(value.length>1){
									data.put("Th", value[1]);
									station.setTh(value[1]);
								}
							}else if(temp.contains("Vh")){
								String[] value = temp.split("=");
								if(value.length>1){
									data.put("Vh", value[1]);
									station.setVh(value[1]);
								}	
							}else if(temp.contains("Vs")){
								String[] value = temp.split("=");
								if(value.length>1){
									data.put("Vs", value[1]);
									station.setVs(value[1]);
								}
							}else if(temp.contains("Vr")){
								String[] value = temp.split("=");
								if(value.length>1){
									data.put("Vr", value[1]);
									station.setVr(value[1]);
								}
							}
							
						}
					}
					userSession.getBasicRemote().sendText(stationToJson(station));
				}
				
				/*
				while(i>=0){
					if(!os.toString().equals("")){
						
						String str = os.toString();
						Map<String,String>data = new HashMap<>();
						data.put("name", name);

						//System.out.println(buildJsonData("",stationToJson(station)));
						//System.out.println(stationToJson(station));
						System.out.println(str);
						//JSONObject json = new JSONObject(data);
						
						//userSession.getBasicRemote().sendText(stationToJson(station));
						userSession.getBasicRemote().sendText(str);
						os.reset();
						i--;
					}
				}*/
			} catch (InvalidParameterException | IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}
		
		private String stationToJson(Stations s) {
			
			JsonObjectBuilder builder = Json.createObjectBuilder();
			builder.add("message", Json.createObjectBuilder().add("Sn", s.getSn())
					.add("Sn", s.getSn())
					.add("Sm", s.getSm())
					.add("Sx", s.getSx())
					.add("Dn", s.getDn())
					.add("Dm", s.getDm())
					.add("Dx", s.getDx())
					.add("Pa", s.getPa())
					.add("Ta", s.getTa())
					.add("Tp", s.getTp())
					.add("Ua", s.getUa())
					.add("Rc", s.getRc())
					.add("Rd", s.getRd())
					.add("Ri", s.getRi())
					.add("Rp", s.getRp())
					.add("Hc", s.getHc())
					.add("Hd", s.getHd())
					.add("Hi", s.getHi())
					.add("Hp", s.getHp())
					.add("Th", s.getTh())
					.add("Vh", s.getVh())
					.add("Vs", s.getVs())
					.add("Vr", s.getVr())
					.add("Name", s.getName()));

			JsonObject result = builder.build();
			StringWriter sw = new StringWriter();
			try(JsonWriter writer = Json.createWriter(sw)){
				writer.write(result);
			} 
			
			
			return sw.toString();
		}

	}
}
/*
  						
 * */
