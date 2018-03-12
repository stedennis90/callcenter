/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cc3.controller;

import com.cc3.factories.CallDataFactory;
import com.cc3.factories.RolFactory;
import com.cc3.model.Attendant;
import com.cc3.model.Rol;
import com.cc3.threads.Line;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Clase para gestionar el Call Center.
 * @author Dennis
 */
public class CallCenter {

    /**
     * Cola de prioridad para almacenar las lineas disponibles.
     */
    public static BlockingQueue<Line> availableLines = new PriorityBlockingQueue<>();
    
    /**
     * Cola de asignación de roles.
     */
    private BlockingQueue<Rol> assignedRoles;
    
    /**
     * Dispatcher asociado.
     */
    private Dispatcher dispatcher;
    
    /**
     * Fabrica de datos de llamada.
     */
    private CallDataFactory callDataFactory;
    
    /**
     * Fabrica de roles.
     */
    private RolFactory rolFactory;
    

    /**
     * Constructor
     */
    public CallCenter() {
        this.assignedRoles = new LinkedBlockingQueue<>();
        this.dispatcher = new Dispatcher(this);
    }
    
    /**
     * Inicializa objetos independientes.
     */
    public void init(){
        this.callDataFactory = new CallDataFactory();
        this.rolFactory = new RolFactory();
        
    }
    
    public void start(int numberLines){
        Rol[] availableRoles = rolFactory.generateAvailableRoles(numberLines);
        
        for(int i=0; i < numberLines; i++){
            Attendant attendant = new Attendant(availableRoles[i].name() + "_" + (i+1), availableRoles[i]);
            Line line = new Line("Linea " + (i+1), attendant);
            availableLines.add(line);            
        }
        
        waitAllAttendantReady();
        System.out.println("\n>>> INICIA OPERACIONES CALL CENTER <<<\n");
    }    
    
    /**
     * Intenta reservar una linea.
     * @return Linea reservada o null en caso de no haber.
     * @throws InterruptedException 
     */
    public Line tryReserveLine() throws InterruptedException {
        Line line = availableLines.poll();        
        if (line!=null){
            assignedRoles.add(line.getAttendant().getRol());
            System.out.printf("\n *** CallCenter ha reservado llamada para: %s / %s", line.getAttendant().getRol().name(), line.getAttendant().getName());
        }
        return line;
    }
    
    /**
     * Libera una linea para otra llamada.
     * @param freeLine Linea libre.
     * @throws InterruptedException 
     */
    public void releaseLine(Line freeLine) throws InterruptedException {
        availableLines.add(freeLine);
    }    
    

    /**
     * Espera que todos los asesores estén listos para atender.
     */
    private void waitAllAttendantReady() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
            System.out.println("InterruptedException CallCenter Constructor");
        }
    }
    
    
    public static void main(String[] args) {
        
        CallCenter callCenter = new CallCenter();
        callCenter.init();
        callCenter.start(5);
        
        Dispatcher dispatcher1 = callCenter.getDispatcher();
        dispatcher1.dispatchCall( CallDataFactory.build());
        dispatcher1.dispatchCall( CallDataFactory.build());        
        dispatcher1.dispatchCall( CallDataFactory.build());        
        dispatcher1.dispatchCall( CallDataFactory.build(), 500 );        
        dispatcher1.dispatchCall( CallDataFactory.build(), 500 );
        dispatcher1.dispatchCall( CallDataFactory.build(), 1000 );
        dispatcher1.dispatchCall( CallDataFactory.build(), 1000 );
        dispatcher1.dispatchCall( CallDataFactory.build(), 1000 );   
    }

    public BlockingQueue<Rol> getAssignedRoles() {
        return assignedRoles;
    }

    public void setAssignedRoles(BlockingQueue<Rol> assignedRoles) {
        this.assignedRoles = assignedRoles;
    }    

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public CallDataFactory getCallDataFactory() {
        return callDataFactory;
    }

    public void setCallDataFactory(CallDataFactory callDataFactory) {
        this.callDataFactory = callDataFactory;
    }

    public RolFactory getRolFactory() {
        return rolFactory;
    }

    public void setRolFactory(RolFactory rolFactory) {
        this.rolFactory = rolFactory;
    }
    
    
}
