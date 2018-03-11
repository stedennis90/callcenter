/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cc3.model;

import com.cc3.model.Attendant.ROL;
import com.cc3.threads.IncomingCall;
import com.cc3.threads.Line;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Dmartinezb
 */
public class CallCenter {

    //public static Semaphore supportedLines;
    
    public static BlockingQueue<Line> availableLines = new PriorityBlockingQueue<>();
    
    private ROL[] exampleRoles = {ROL.DIRECTOR,
                                    ROL.SUPERVISOR,
                                    ROL.OPERADOR,
                                    ROL.OPERADOR,
                                    ROL.OPERADOR,
                                    ROL.OPERADOR,
                                    ROL.OPERADOR,
                                    ROL.OPERADOR,
                                    ROL.OPERADOR,
                                    ROL.OPERADOR
                                };
    
    public CallCenter(int numberLines) {
        //supportedLines = new Semaphore(numberLines);
        
        for(int i=0; i < numberLines; i++){
            Attendant attendant = new Attendant(exampleRoles[i].name() + (i+1), exampleRoles[i]);
            Line line = new Line(this, "Linea " + (i+1), attendant);
            availableLines.add(line);
            
        }
        
        /*
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(CallCenter.class.getName()).log(Level.SEVERE, null, ex);
        }
*/
    }
    
    
    public Line tryReserveLine() throws InterruptedException {
        return availableLines.poll(5000, TimeUnit.MILLISECONDS);        
    }
    
    public void releaseLine(Line freeLine) throws InterruptedException {
        availableLines.add(freeLine);
    }
    
    public void dispatchCall(CallData data){
        IncomingCall call = new IncomingCall(this, data);
    }
    
    
    public static void main(String[] args) {
        
        CallCenter callCenter = new CallCenter(3);        
        
        callCenter.dispatchCall( new CallData(1234, "Andres", 3000) );
        callCenter.dispatchCall( new CallData(2020, "Juliana", 4000) );        
        callCenter.dispatchCall( new CallData(9909, "Felipe", 4500) );        
        callCenter.dispatchCall( new CallData(7654, "Manuela", 6500) );        
        callCenter.dispatchCall( new CallData(3356, "Victoria", 2500) );
        callCenter.dispatchCall( new CallData(2806, "Fernando", 1500) );
        
    }
    
    
}
