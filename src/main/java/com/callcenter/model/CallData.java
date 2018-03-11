/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callcenter.model;

/**
 *
 * @author Dmartinezb
 */
public class CallData {
    
    private long from;
    private String username;
    private int duration;

    public CallData() {
    }

    public CallData(long from, String username, int duration) {
        this.from = from;
        this.username = username;
        this.duration = duration;
    }
    
    
    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }    
    
}
