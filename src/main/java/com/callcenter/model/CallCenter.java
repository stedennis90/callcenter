/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callcenter.model;

import com.callcenter.threads.Line;
import com.callcenter.threads.IncomingCall;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Dmartinezb
 */
public class CallCenter {
    
    
    
    public static void main(String[] args) {
        
        
        CallCenter callCenter = new CallCenter();
        
        new Line("Asesor 1", "DIRECTOR");
        //new Line("Asesor 2", "ASESOR");
        //new Line("Asesor 3", "ASESOR");
        
        
        new IncomingCall(new CallData(1234, "Andres", 15000));
        new IncomingCall(new CallData(7788, "Carlos", 8000));
        //new IncomingCall(new CallData(4444, "Carolina", 4000));
        
        
    }
    
    public void registerIncomingCall(IncomingCall call){
        
    }
    
}
