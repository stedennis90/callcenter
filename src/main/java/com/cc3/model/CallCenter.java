/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cc3.model;

import com.cc3.threads.IncomingCall;
import com.cc3.threads.Line;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 *
 * @author Dmartinezb
 */
public class CallCenter {

    //public static Semaphore supportedLines;
    
    public static BlockingQueue<Line> availableLines = new PriorityBlockingQueue<>();
    
    private Rol[] exampleRoles = {Rol.DIRECTOR,
                                    Rol.SUPERVISOR,
                                    Rol.OPERADOR,
                                    Rol.OPERADOR,
                                    Rol.OPERADOR,
                                    Rol.OPERADOR,
                                    Rol.OPERADOR,
                                    Rol.OPERADOR,
                                    Rol.OPERADOR,
                                    Rol.OPERADOR
                                };
    
    public CallCenter(int numberLines) {
        //supportedLines = new Semaphore(numberLines);
        
        for(int i=0; i < numberLines; i++){
            Attendant attendant = new Attendant(exampleRoles[i].name() + "_" + (i+1), exampleRoles[i]);
            Line line = new Line(this, "Linea " + (i+1), attendant);
            availableLines.add(line);
            
        }
        
        // Simulando que todos los asesores estén listos para atender.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
            System.out.println("InterruptedException CallCenter Constructor");
        }
        System.out.println("\n>>> INICIA OPERACIONES CALL CENTER <<<\n");

    }
    
    
    public Line tryReserveLine() throws InterruptedException {
        //return availableLines.poll(5000, TimeUnit.MILLISECONDS);        
        Line line = availableLines.poll();
        if (line!=null)
            System.out.printf("\n *** CallCenter ha reservado llamada para: %s / %s", line.getAttendant().getRol().name(), line.getAttendant().getName());
        return line;
    }
    
    public void releaseLine(Line freeLine) throws InterruptedException {
        availableLines.add(freeLine);
    }
    
    public void dispatchCall(CallData data){
        IncomingCall call = new IncomingCall(this, data);
    }
    
    
    public static void main(String[] args) {
        
        CallCenter callCenter = new CallCenter(5);        
        
        callCenter.dispatchCall( new CallData(1234, "Andres", 1000) );
        callCenter.dispatchCall( new CallData(2020, "Juliana", 1300) );        
        callCenter.dispatchCall( new CallData(9909, "Felipe", 1500) );        
        callCenter.dispatchCall( new CallData(7654, "Manuela", 6500) );        
        callCenter.dispatchCall( new CallData(3356, "Victoria", 3000) );
        callCenter.dispatchCall( new CallData(3090, "Fernando", 1500) );
        callCenter.dispatchCall( new CallData(5281, "Carlos", 1500) );
        callCenter.dispatchCall( new CallData(9475, "Tomas", 1500) );
        
        
    }
    
    
}
