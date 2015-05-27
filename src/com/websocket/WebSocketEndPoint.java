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


@ServerEndpoint("/WebSocketEndPoint")
public class WebSocketEndPoint {

	static Set<Session> users = Collections.synchronizedSet(new HashSet<Session>());
	

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

	public void SendResult(Session session){
		
	}
	@OnMessage
	public void handleMessage(String message, Session userSession) throws IOException, InterruptedException{
		if(message.equals("stop")){
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
			
			Stations BMR_Station = new Stations("BMR");
			BMR = new NewThread("BMR", host_bmr, group_bmr, port_bmr, userSession, BMR_Station);
			BMR.start();
			
			Stations BH_Station = new Stations("BH");
			BH = new NewThread("BH", host_bh, group_bh, port_bh, userSession, BH_Station);
			BH.start();
			
			Stations LP_Station = new Stations("LP");
			LP = new NewThread("LP", host_lp, group_lp, port_lp, userSession, LP_Station);
			LP.start();
			
			
			Stations ML_Station = new Stations("ML");
			ML = new NewThread("ML", host_ml, group_ml, port_ml, userSession, ML_Station);
			ML.start();
			
			Stations MW_Station = new Stations("MW");
			MW = new NewThread("MW", host_mw, group_mw, port_mw, userSession, MW_Station);
			MW.start();
			
			Stations CNMZ3_Station = new Stations("CNMZ3");
			CNMZ3 = new NewThread("CNMZ3", host_cnmz3, group_cnmz3, port_cnmz3, userSession, CNMZ3_Station);
			CNMZ3.start();
			
			Stations NN_Station = new Stations("NN");
			NN = new NewThread("NN", host_nn, group_nn, port_nn, userSession, NN_Station);
			NN.start();
			
			Stations PA_Station = new Stations("PA");
			PA = new NewThread("PA", host_pa, group_pa, port_pa, userSession, PA_Station);
			PA.start();
			
			Stations PLC_Station = new Stations("PLC");
			PLC = new NewThread("PLC", host_plc, group_plc, port_plc, userSession, PLC_Station);
			PLC.start();
			
			Stations RM_Station = new Stations("RM");
			RM = new NewThread("RM", host_rm, group_rm, port_rm, userSession, RM_Station);
			RM.start();
			
			Stations SCI_Station = new Stations("SCI");
			SCI = new NewThread("SCI", host_sci, group_sci, port_sci, userSession, SCI_Station);
			SCI.start();
			
			Stations SMERNS_Station = new Stations("SMERNS");
			SMERNS = new NewThread("SMERNS", host_smerns, group_smerns, port_smerns, userSession, SMERNS_Station);
			SMERNS.start();
			
			Stations SO_Station = new Stations("SO");
			SO = new NewThread("SO", host_so, group_so, port_so, userSession, SO_Station);
			SO.start();
			
			Stations HWB_Station = new Stations("HWB");
			HWB = new NewThread("HWB", host_hwb, group_hwb, port_hwb, userSession, HWB_Station);
			HWB.start();
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
