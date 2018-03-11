/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callcenter2.threads;




import com.callcenter2.model.CallCenter;
import com.callcenter2.model.CallData;


/**
 *
 * @author Dmartinezb
 */
public class IncomingCall extends Thread {
    
    private CallData callData;
    private CallCenter callCenter;
    private Line line;
    private boolean finished;

    /**
     *
     * @param callCenter
     * @param callData
     */
    public IncomingCall(CallCenter callCenter, CallData callData) {
        this.callCenter = callCenter;
        this.callData = callData;
        this.setName(callData.getUsername());
        this.start();
    }
    
    public void assignLine(Line line){
        this.line = line;
        if (this.line != null){
            this.line.setCallData(callData);
        }
    }
    
    public void removeLine(){
        this.line.setCallData(null);
        this.line = null;
    }
    
    private boolean isAvailableLine(){
        return line!=null;
    }
    
    private void playWaitMusic(){
        System.out.println("Please wait online... \\u266D");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
            System.out.println("InterruptedException playWaitMusic");
        }
    }

    @Override
    public void run() {
        int retry = 0;
        
        while (!finished && retry < 3){
            finished = false;
            try {
                
                //CallCenter.supportedLines.acquire();
                assignLine(callCenter.reserveLine());
                    
                if (isAvailableLine()){
                    //if (line.lock.tryLock()){
                        try{
                            line.lock.lock();

                            System.out.printf("\nU - %s: conectando con asesor!", callData.getUsername());
                            //line.freeLine.await(1, TimeUnit.SECONDS);
                            line.freeLine.await();
                            
                            playConversation();

                            line.finishedCall.signalAll();
                            System.out.printf("\nU - %s: terminó llamada!", callData.getUsername());

                        } finally {
                            System.out.printf("\nU...Finally incoming call");
                            line.lock.unlock();
                        }
                    /*} else {
                        System.out.printf("\nU - %s: No se obtuvo el lock", callData.getUsername());
                    }*/
                    
                } else { // Retry                    
                    try {
                        retry++;
                        System.out.printf("\nU %s: No se encontró asesor disponible (Intento %d)", callData.getUsername(), retry);
                        Thread.sleep(500);
                    } catch (InterruptedException ignored) {
                        System.out.println("InterruptedException IncomingCall run()");
                    }
                }                
                
                callCenter.releaseLine(line);
                //CallCenter.supportedLines.release();
                
            } catch (InterruptedException ignored) {
                System.out.println("InterruptedException IncomingCall run() 2");
            }
        }
        System.out.printf("\nFinalizó incoming call # %d", callData.getFrom());
        
    }

    private void playConversation() throws InterruptedException {
        System.out.printf("\n Llamada entrante / %d: Hola soy el usuario %s, hablo con el asesor '%s'", callData.getFrom(), callData.getUsername(), line.getAttendantName());
        Thread.sleep(callData.getDuration());
        System.out.printf("\n Llamada entrante / %d: Adios, muchas gracias por su atención", callData.getFrom());
        finished = true;
    }

    
    
    
}
