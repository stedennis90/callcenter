/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cc3.controller;

import com.cc3.model.CallData;
import com.cc3.threads.IncomingCall;

/**
 * Despachador de llamadas.
 * @author dennis
 */
public class Dispatcher {
    
    /**
     * Datos del call center.
     */
    private CallCenter callCenter;

    /**
     * Constructor.
     */
    public Dispatcher() {}

    /**
     * Constructor.
     * @param callCenter 
     */
    public Dispatcher(CallCenter callCenter) {
        this();
        this.callCenter = callCenter;
    }
    
    
    /**
     * Lanza una llamada entrante.
     * @param data Datos de la llamada
     */
    public void dispatchCall(CallData data){
        IncomingCall call = new IncomingCall(callCenter, data);
    }
    
    /**
     * Lanza una llamada entrante con un retraso definido.
     * @param data Datos de la llamada
     * @param delay Tiempo de retraso en milisegundos para lanzar la llamada.
     */
    public void dispatchCall(CallData data, int delay){
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ignored) {}
        
        this.dispatchCall(data);
    }

    public CallCenter getCallCenter() {
        return callCenter;
    }

    public void setCallCenter(CallCenter callCenter) {
        this.callCenter = callCenter;
    }
    
    
    
}
