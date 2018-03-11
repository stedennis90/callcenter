/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callcenter.threads;

import com.callcenter.model.CallCenter;
import com.callcenter.model.CallData;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Dmartinezb
 */
public class IncomingCall extends Thread {
    
    private CallData callData;
    private Line line;
    private boolean finished;

    /**
     *
     * @param callCenter
     * @param callData
     */
    public IncomingCall(CallData callData) {
        this.callData = callData;
        this.setName(callData.getUsername());
        this.start();
    }
    
    public void assignLine(Line line){
        this.line = line;
    }
    
    private void playWaitMusic(){
        System.out.println("Please wait online... \\u266D");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {}
    }

    @Override
    public void run() {
        
        while(line==null) playWaitMusic();
        
        int retry = 0;
        
        while (!finished && retry < 3){
            if (line.lock.tryLock()){
                try{
                    System.out.printf("\nU - %s: esperando un asesor!", callData.getUsername());
                    boolean await = line.freeLine.await(5, TimeUnit.SECONDS);
                    System.out.printf("\nU - %s: - await: %s!", callData.getUsername(), await);
                    sendClientMessage();
                    finished = true;
                    line.finishedCall.signalAll();
                    System.out.printf("\nU - %s: terminó llamada!", callData.getUsername());
                } catch (InterruptedException ex) {
                    System.out.println("Ocurrio un error en la llamada de: " + callData.getUsername());
                } finally {
                    line.lock.unlock();
                }
            } else {
                System.out.printf("\nU %s: No se obtuvo el lock (Intento %d)", callData.getUsername(), (++retry));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {}
            }
        }
        
    }

    private void sendClientMessage() throws InterruptedException {
        System.out.printf("\n Llamada entrante / %d: Hola soy el usuario %s", callData.getFrom(), callData.getUsername());
        Thread.sleep(callData.getDuration());
    }
    
}
