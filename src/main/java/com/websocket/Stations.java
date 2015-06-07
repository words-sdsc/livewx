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

    public Stations(String Name) {
        name = Name;
        sn = "";
        ;
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

    public Stations(Stations s) {
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

    public String getName() {
        return name;
    }

    public void setSn(String SN) {
        if (!SN.contains("#")) {
            sn = SN;
        }
    }

    public String getSn() {
        return sn;
    }

    public void setSm(String SM) {
        if (!SM.contains("#")) {
            sm = SM;
        }
    }

    public String getSm() {
        return sm;
    }

    public void setSx(String SX) {
        if (!SX.contains("#"))
            sx = SX;
    }

    public String getSx() {
        return sx;
    }

    public void setDn(String DN) {
        if (!DN.contains("#"))
            dn = DN;
    }

    public String getDn() {
        return dn;
    }

    public void setDm(String DM) {
        if (!DM.contains("#"))
            dm = DM;
    }

    public String getDm() {
        return dm;
    }

    public void setDx(String DX) {
        if (!DX.contains("#"))
            dx = DX;
    }

    public String getDx() {
        return dx;
    }

    public void setPa(String PA) {
        if (!PA.contains("#"))
            pa = PA;
    }

    public String getPa() {
        return pa;
    }

    public void setTa(String TA) {
        if (!TA.contains("#"))
            ta = TA;
    }

    public String getTa() {
        return ta;
    }

    public void setTp(String TP) {
        if (!TP.contains("#"))
            tp = TP;
    }

    public String getTp() {
        return tp;
    }

    public void setUa(String UA) {
        if (!UA.contains("#"))
            ua = UA;
    }

    public String getUa() {
        return ua;
    }

    public void setRc(String RC) {
        if (!RC.contains("#"))
            rc = RC;
    }

    public String getRc() {
        return rc;
    }

    public void setRd(String RD) {
        if (!RD.contains("#"))
            rd = RD;
    }

    public String getRd() {
        return rd;
    }

    public void setRi(String RI) {
        if (!RI.contains("#"))
            ri = RI;
    }

    public String getRi() {
        return ri;
    }

    public void setRp(String RP) {
        if (!RP.contains("#"))
            rp = RP;
    }

    public String getRp() {
        return rp;
    }

    public void setHc(String HC) {
        if (!HC.contains("#"))
            hc = HC;
    }

    public String getHc() {
        return hc;
    }

    public void setHd(String HD) {
        if (!HD.contains("#"))
            hd = HD;
    }

    public String getHd() {
        return hd;
    }

    public void setHi(String HI) {
        if (!HI.contains("#"))
            hi = HI;
    }

    public String getHi() {
        return hi;
    }

    public void setHp(String HP) {
        if (!HP.contains("#"))
            hp = HP;
    }

    public String getHp() {
        return hp;
    }

    public void setTh(String TH) {
        if (!TH.contains("#"))
            th = TH;
    }

    public String getTh() {
        return th;
    }

    public void setVh(String VH) {
        if (!VH.contains("#"))
            vh = VH;
    }

    public String getVh() {
        return vh;
    }

    public void setVs(String VS) {
        if (!VS.contains("#"))
            vs = VS;
    }

    public String getVs() {
        return vs;
    }

    public void setVr(String VR) {
        if (!VR.contains("#"))
            vr = VR;
    }

    public String getVr() {
        return vr;
    }

}
