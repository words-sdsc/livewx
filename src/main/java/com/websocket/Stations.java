package com.websocket;

public class Stations {
	private String name;
	private String sn;
	private String sm;
	private String sx;
	private String dn;
	private String dm;
	private String dx;
	private String pa;
	private String ta;
	private String tp;
	private String ua;
	private String rc;
	private String rd;
	private String ri;
	private String rp;
	private String hc;
	private String hd;
	private String hi;
	private String hp;
	private String th;
	private String vh;
	private String vs;
	private String vr;
	
	public Stations(String Name){
		name = Name;
		sn = "";;
		sm = "";
		sx = "";
		dn = "";
		dm = "";
		dx = "";
		 pa = "";
		 ta = "";
		 tp = "";
		 ua = "";
		 rc = "";
		 rd = "";
		 ri = "";
		 rp = "";
		 hc = "";
		 hd = "";
		 hi = "";
		 hp = "";
		 th = "";
		 vh = "";
		 vs = "";
		 vr = "";
	}

	public Stations(Stations s){
		name = s.getName();
		sn = s.getSn();
		sm = s.getSm();
		sx = s.getSx();
		dn = s.getDn();
		dm = s.getDm();
		dx = s.getDx();
		 pa = s.getPa();
		 ta = s.getTa();
		 tp = s.getTp();
		 ua = s.getUa();
		 rc = s.getRc();
		 rd = s.getRd();
		 ri = s.getRi();
		 rp = s.getRp();
		 hc = s.getHc();
		 hd = s.getHd();
		 hi = s.getHi();
		 hp = s.getHp();
		 th = s.getTh();
		 vh = s.getVh();
		 vs = s.getVs();
		 vr = s.getVr();
	}
	public String getName(){
		return name;
	}
	public void setSn(String SN){
		sn = SN;
	}
	public String getSn(){
		return sn;
	}

	public void setSm(String SM){
		sm = SM;
	}
	public String getSm(){
		return sm;
	}

	public void setSx(String SX){
		sx = SX;
	}
	public String getSx(){
		return sx;
	}

	public void setDn(String DN){
		dn = DN;
	}
	public String getDn(){
		return dn;
	}

	public void setDm(String DM){
		dm = DM;
	}
	public String getDm(){
		return dm;
	}

	public void setDx(String DX){
		dx = DX;
	}
	public String getDx(){
		return dx;
	}

	public void setPa(String PA){
		pa = PA;
	}
	public String getPa(){
		return pa;
	}

	public void setTa(String TA){
		ta = TA;
	}
	public String getTa(){
		return ta;
	}

	public void setTp(String TP){
		tp = TP;
	}
	public String getTp(){
		return tp;
	}

	public void setUa(String UA){
		ua = UA;
	}
	public String getUa(){
		return ua;
	}

	public void setRc(String RC){
		rc = RC;
	}
	public String getRc(){
		return rc;
	}

	public void setRd(String RD){
		rd = RD;
	}
	public String getRd(){
		return rd;
	}

	public void setRi(String RI){
		ri = RI;
	}
	public String getRi(){
		return ri;
	}

	public void setRp(String RP){
		rp = RP;
	}
	public String getRp(){
		return rp;
	}

	public void setHc(String HC){
		hc = HC;
	}
	public String getHc(){
		return hc;
	}

	public void setHd(String HD){
		hd = HD;
	}
	public String getHd(){
		return hd;
	}

	public void setHi(String HI){
		hi = HI;
	}
	public String getHi(){
		return hi;
	}

	public void setHp(String HP){
		hp = HP;
	}
	public String getHp(){
		return hp;
	}

	public void setTh(String TH){
		th = TH;
	}
	public String getTh(){
		return th;
	}

	public void setVh(String VH){
		vh = VH;
	}
	public String getVh(){
		return vh;
	}

	public void setVs(String VS){
		vs = VS;
	}
	public String getVs(){
		return vs;
	}

	public void setVr(String VR){
		vr = VR;
	}
	public String getVr(){
		return vr;
	}

}
