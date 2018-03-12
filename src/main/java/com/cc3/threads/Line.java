/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cc3.threads;


import com.cc3.model.Attendant;
import com.cc3.controller.CallCenter;
import com.cc3.model.CallData;
import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Clase que representa un hilo de una linea asignada a un asesor.
 * Esta clase realiza el ciclo de vida de la llamada desde el lado del asesor,
 *      Espera una nueva llamada.
 *      Notifica al usuario su disponibilidad para atender.
 *      Espera que el usuario finalice la llamada.
 * El ciclo de vida de la linea es infinito, por lo tanto el asesor siempre 
 * quedará despierto esperando la siguiente llamada.
 * @author Dennis
 */
public class Line extends Thread implements Comparable<Line> {
    
    /**
     * Lock para gesionar la linea.
     */
    public final Lock lock = new ReentrantLock();
    
    /**
     * Identifica si hay una nueva llamada para atenderla.
     */
    public final Condition newCall = lock.newCondition();
    
    /**
     * Identifica si la llamada ya terminó.
     */
    public final Condition finishedCall = lock.newCondition();
    
    /**
     * Identifica si hay una llamada en curso.
     */
    public final Condition conversationStablished = lock.newCondition();
    
    /**
     * Nombre de la linea.
     */
    private final String lineName;
    
    /**
     * Datos del asesor encargado.
     */
    private final Attendant attendant;
    
    /**
     * Datos de la llamada
     */
    private CallData callData;
    
    
    /**
     * Constructor.
     * @param name Nombre de la linea.
     * @param attendant Datos del asesor.
     */
    public Line(String name, Attendant attendant) {
        this.lineName = name;
        this.attendant = attendant;
        this.start();
    }

    @Override
    public void run() {
        while(true){
            if (lock.tryLock()){
                try{

                    System.out.printf("\nCC - %s: disponible!", attendant.getName());
                    newCall.await();
                    
                    System.out.printf("\nCC - %s: atendiendo!", attendant.getName());
                    System.out.printf("\nCC - %s: Hola, como está Sr(a) %s!", attendant.getName(), callData.getUsername());
                    conversationStablished.signal();
                    
                    finishedCall.await();
                    callData = null;
                    System.out.printf("\nCC - %s: colgando!", attendant.getName());
                    
                } catch (InterruptedException ignored) {
                    System.out.println("InterruptedException Line run()");
                } finally {
                    lock.unlock();
                }

            } else {
                System.out.printf("\nCC - %s: No se obtuvo el lock", attendant.getName());
            }
        }
    }

    public void setCallData(CallData callData) {
        this.callData = callData;
    }

    public String getLineName() {
        return lineName;
    }

    public Attendant getAttendant() {
        return attendant;
    }

    @Override
    public int compareTo(Line other) {
        return this.hashCode()-other.hashCode();
    }

    @Override
    public int hashCode() {
        switch(this.attendant.getRol()){
            case DIRECTOR: return 3;
            case SUPERVISOR: return 2;
            case OPERADOR: return 1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Line other = (Line) obj;
        if (!Objects.equals(this.lineName, other.lineName)) {
            return false;
        }
        if (!Objects.equals(this.attendant, other.attendant)) {
            return false;
        }
        return true;
    }
    
}
