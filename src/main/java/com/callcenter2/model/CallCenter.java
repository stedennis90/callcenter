/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callcenter2.model;

import com.callcenter2.threads.IncomingCall;
import com.callcenter2.threads.Line;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dmartinezb
 */
public class CallCenter {

    public static Semaphore supportedLines;
    
    public static BlockingQueue<Line> availableAttendant = new PriorityBlockingQueue<>();
    
    public CallCenter(int numberLines) {
        supportedLines = new Semaphore(numberLines);
        
        for(int i=0; i < numberLines; i++){
            availableAttendant.add( new Line(this, "Linea " + (i+1), "Attendant " + (i+1)) );
            //availableAttendant.add(new Attendant("Attendant " + (i+1), Attendant.ROL.OPERADOR));
        }
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(CallCenter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public Line reserveLine() throws InterruptedException {
        return availableAttendant.poll(5000, TimeUnit.MILLISECONDS);        
    }
    
    public void releaseLine(Line freeLine) throws InterruptedException {
        availableAttendant.add(freeLine);
    }
    
    public void dispatchCall(CallData data){
        IncomingCall call = new IncomingCall(this, data);
    }
    
    
    public static void main(String[] args) {
        
        CallCenter callCenter = new CallCenter(1);        
        
        callCenter.dispatchCall( new CallData(1234, "Andres", 3000) );
        callCenter.dispatchCall( new CallData(2020, "Juliana", 4000) );        
        //callCenter.dispatchCall( new CallData(9909, "Felipe", 4500) );        
    }
    
    
}
