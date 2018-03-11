/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callcenter2.threads;


import com.callcenter2.model.CallCenter;
import com.callcenter2.model.CallData;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Dmartinezb
 */
public class Line extends Thread implements Comparable<Line>{

    
    /**
     * Lock para gesionar el call center.
     */
    public final Lock lock = new ReentrantLock();
    
    /**
     * Identifica si hay una linea disponible para atender llamada.
     */
    public final Condition freeLine = lock.newCondition();
    
    /**
     * Identifica si la llamada ya terminó.
     */
    public final Condition finishedCall = lock.newCondition();

    
    private final String lineName;
    private final String attendantName;
    
    private CallData callData;
    private final CallCenter callCenter;
    
    public Line(CallCenter callCenter, String name, String role) {
        this.callCenter = callCenter;
        this.lineName = name;
        this.attendantName = role;
        this.start();
    }

    @Override
    public void run() {
        while(true){
            if (lock.tryLock()){
                try{
                    System.out.printf("\nCC - %s: disponible!", attendantName);
                    freeLine.signalAll();
                    
                    System.out.printf("\nCC - %s: atendiendo!", attendantName);
                    System.out.printf("\nCC - %s: Hola, como está Sr(a) %s!", attendantName, callData.getUsername());
                    
                    finishedCall.await();
                    callData = null;
                    System.out.printf("\nCC - %s: colgando!", attendantName);
                    
                } catch (InterruptedException ignored) {
                    System.out.println("InterruptedException Line run()");
                } finally {
                    lock.unlock();
                }

            } else {
                System.out.printf("\nCC - %s: No se obtuvo el lock", attendantName);
            }
        }
    }

    public void setCallData(CallData callData) {
        this.callData = callData;
    }

    public String getLineName() {
        return lineName;
    }

    public String getAttendantName() {
        return attendantName;
    }

    @Override
    public int compareTo(Line other) {
        return other.getLineName().compareTo(lineName);
    }

    
    
}
