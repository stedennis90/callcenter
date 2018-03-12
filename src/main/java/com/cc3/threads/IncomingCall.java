/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cc3.threads;


import com.cc3.controller.CallCenter;
import com.cc3.model.CallData;


/**
 * Clase que representa un hilo de una llamada entrante.
 * Esta clase realiza el ciclo de vida de la llamada desde el lado del cliente,
 *      Notifica la llamada entrante.
 *      Espera el asesor.
 *      Notifica el fin de la llamada.
 * 
 * @author Dennis
 */
public class IncomingCall extends Thread {
    
    /**
     * Cantidad de reintentos permitidos en caso que no haya asesor disponible
     * para atender la llamada.
     */    
    private final static int MAX_RETRY = 3;
    
    /**
     * Datos de la llamada
     */
    private final CallData callData;
    
    /**
     * Datos del call center.
     */
    private final CallCenter callCenter;
    
    /**
     * Datos de la linea asignada a la llamada.
     */
    private Line line;
    
    /**
     * Indica si la llamada fue finalizada.
     */
    private boolean finished;
    

    /**
     * Constructor
     * @param callCenter Datos del call center.
     * @param callData Datos de la llamada.
     */
    public IncomingCall(CallCenter callCenter, CallData callData) {
        this.callCenter = callCenter;
        this.callData = callData;
        this.setName(callData.getUsername());
        this.start();
    }
    
    /**
     * Asigna la linea que responderá la llamada.
     * @throws java.lang.InterruptedException
     */
    public void assignLine() throws InterruptedException{
        this.line = callCenter.tryReserveLine();
        if (this.line != null){
            this.line.setCallData(callData);
        }
    }
    
    /**
     * Indica si la linea está disponible.
     * @return true o false, dependiendo si la linea está disponible o no.
     */
    private boolean isAvailableLine(){
        return line!=null;
    }
    
    /**
     * Simulación de espera en linea
     */
    private void playWaitMusic(){
        System.out.printf("\nU - %s: Esperando asesor en linea ... \u266A\u266B\u266C \u266A\u266B\u266C!", callData.getUsername());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
            System.out.println("InterruptedException playWaitMusic");
        }
    }

    @Override
    public void run() {
        int retry = 0;
        System.out.printf("\nU - %s: Inicia llamada!", callData.getUsername());        
        while (!finished && retry < MAX_RETRY){
            finished = false;
            try {
                this.assignLine();                    
                if (isAvailableLine()){
                    try{
                        line.lock.lock();
                        line.newCall.signal();
                        System.out.printf("\nU - %s: esperando asesor!", callData.getUsername());

                        line.conversationStablished.await();
                        playConversation();

                        line.finishedCall.signal();
                        System.out.printf("\nU - %s: terminó llamada!", callData.getUsername());
                    } finally {
                        line.lock.unlock();
                    }
                    this.releaseLine();
                } else {
                    retry++;
                    System.out.printf("\nU %s: No se encontró asesor disponible (Intento %d)", callData.getUsername(), retry);
                    playWaitMusic();
                }
            } catch (InterruptedException ignored) {
                System.out.println("InterruptedException IncomingCall run() 2");
            }
        }
        
        
    }

    /**
     * Simulación de la conversación.
     * @throws InterruptedException 
     */
    private void playConversation() throws InterruptedException {
        System.out.printf("\n Llamada entrante / %d: Hola soy el usuario %s, hablo con el asesor '%s'", callData.getFrom(), callData.getUsername(), line.getAttendant().getName());
        Thread.sleep(callData.getDuration());
        System.out.printf("\n Llamada entrante / %d: Adios, muchas gracias por su atención [%d segundos]", callData.getFrom(), callData.getDuration()/1000);
        finished = true;
    }

    /**
     * Libera la linea actualmente asignada.
     * @throws InterruptedException 
     */
    private void releaseLine() throws InterruptedException {
        this.callCenter.releaseLine(this.line);
    }

    
    
    
}
