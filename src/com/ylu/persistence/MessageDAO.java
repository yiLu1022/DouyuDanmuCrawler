package com.ylu.persistence;

import java.sql.Time;
import java.util.Date;

import com.ylu.beans.Message;



public class MessageDAO {
    private String cid;

    private String type;

    private String gid;

    private String rid;

    private String uid;

    private String nn;

    private String bnn;

    private String txt;

    private String level;

    private Time mtime;
    
    public MessageDAO(Message message){
    	this.cid = message.getCid();
    	this.type = message.getType().name();
    	this.gid = message.getGid();
    	this.rid = message.getRid();
    	this.uid = message.getUid();
    	this.nn = message.getNn();
    	this.bnn = message.getBnn();
    	this.txt = message.getTxt();
    	this.level = message.getLevel();
    	this.mtime = new Time(message.getDate().getTime());
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid == null ? null : cid.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid == null ? null : gid.trim();
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid == null ? null : rid.trim();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getNn() {
        return nn;
    }

    public void setNn(String nn) {
        this.nn = nn == null ? null : nn.trim();
    }

    public String getBnn() {
        return bnn;
    }

    public void setBnn(String bnn) {
        this.bnn = bnn == null ? null : bnn.trim();
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt == null ? null : txt.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Time mtime) {
        this.mtime = mtime;
    }
}