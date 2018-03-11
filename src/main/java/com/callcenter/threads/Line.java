/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callcenter.threads;


import com.callcenter.model.CallCenter;
import com.callcenter.model.CallData;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Dmartinezb
 */
public class Line extends Thread{

    
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

    
    private String name;
    private String role;
    private boolean free;
    
    private CallData callData;
    
    public Line(String name, String role) {
        this.name = name;
        this.role = role;
        this.free = true;
        this.start();
    }


    @Override
    public void run() {
        while(true){
            if (lock.tryLock()){
                try{
                    System.out.printf("\nCC - %s: disponible!", name);
                    freeLine.signalAll();
                    free = false;
                    System.out.printf("\nCC - %s: atendiendo!", name);
                    finishedCall.await();                
                    System.out.printf("\nCC - %s: colgando!", name);
                } catch (InterruptedException ex) {
                    System.out.println("Ocurrio un error en la llamada del asesor: " + name);
                } finally {
                    free = true;
                    lock.unlock();
                }

            } else {
                System.out.printf("\nCC - %s: No se obtuvo el lock", name);
            }
        }
    }

    public void setCallData(CallData callData) {
        this.callData = callData;
    }
    
    
}
