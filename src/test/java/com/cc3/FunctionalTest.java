/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cc3;

import com.cc3.controller.CallCenter;
import com.cc3.controller.Dispatcher;
import com.cc3.factories.CallDataFactory;
import com.cc3.factories.RolFactory;
import com.cc3.model.Rol;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Pruebas funcionales para el Call Center
 * @author dennis
 */
@RunWith(PowerMockRunner.class)
public class FunctionalTest {
    
    
    private final Rol[] UN_DIRECTOR = {Rol.DIRECTOR};
    
    private final Rol[] UN_SUPERVISOR = {Rol.SUPERVISOR};
    
    private final Rol[] UN_OPERADOR = {Rol.OPERADOR};
    
    private final Rol[] UN_OPERADOR_UN_SUPERVISOR_UN_DIRECTOR = {Rol.DIRECTOR, Rol.SUPERVISOR, Rol.OPERADOR};
    
    private final Rol[] UN_OPERADOR_UN_SUPERVISOR = {Rol.SUPERVISOR, Rol.OPERADOR};
    
    private final Rol[] UN_OPERADOR_UN_DIRECTOR = {Rol.DIRECTOR, Rol.OPERADOR};
    
    private final Rol[] TRES_OPERADORES_UN_SUPERVISOR_UN_DIRECTOR = {Rol.OPERADOR, Rol.OPERADOR, Rol.OPERADOR,
                                                                    Rol.SUPERVISOR, Rol.DIRECTOR};
    
    private final Rol[] TRES_OPERADORES_DOS_SUPERVISORES_UN_DIRECTOR = {Rol.OPERADOR, Rol.OPERADOR, Rol.OPERADOR,
                                                                    Rol.SUPERVISOR, Rol.SUPERVISOR, Rol.DIRECTOR};
    
    private final Rol[] CINCO_OPERADORES_TRES_SUPERVISORES_DOS_DIRECTORES = 
                                            {Rol.OPERADOR, Rol.OPERADOR, Rol.OPERADOR, Rol.OPERADOR, Rol.OPERADOR,
                                            Rol.SUPERVISOR, Rol.SUPERVISOR, Rol.SUPERVISOR, 
                                            Rol.DIRECTOR, Rol.DIRECTOR};
    
    @InjectMocks
    private CallCenter callCenter;
    
    @Mock
    private RolFactory rolFactoryMock;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
    }
    
    @After
    public void tearDown() {
    }

    
    @Test
    public void pruebaUnaLlamadaUnDirector() throws InterruptedException {
        int numberIncomingCalls = 1;
        int numberLines = 1;
        
        Mockito.when( this.rolFactoryMock.generateAvailableRoles(Mockito.anyInt()) ).thenReturn(UN_DIRECTOR);
        
        this.callCenter.start(numberLines);        
        launchIncomingCalls(numberIncomingCalls);
        
        Assert.assertEquals("Esperando rol DIRECTOR", Rol.DIRECTOR, this.callCenter.getAssignedRoles().take());        
        
    }
    
    @Test
    public void pruebaUnaLlamadaUnSupervisor() throws InterruptedException {
        int numberIncomingCalls = 1;
        int numberLines = 1;
        
        Mockito.when( this.rolFactoryMock.generateAvailableRoles(Mockito.anyInt()) ).thenReturn(UN_SUPERVISOR);
        
        this.callCenter.start(numberLines);
        launchIncomingCalls(numberIncomingCalls);
        
        Assert.assertEquals("Esperando rol SUPERVISOR", Rol.SUPERVISOR, this.callCenter.getAssignedRoles().take());        
        
    }
    
    @Test
    public void pruebaUnaLlamadaUnOperador() throws InterruptedException {
        int numberIncomingCalls = 1;
        int numberLines = 1;
        
        Mockito.when( this.rolFactoryMock.generateAvailableRoles(Mockito.anyInt()) ).thenReturn(UN_OPERADOR);
        
        this.callCenter.start(numberLines);
        launchIncomingCalls(numberIncomingCalls);
        
        Assert.assertEquals("Esperando rol OPERADOR", Rol.OPERADOR, this.callCenter.getAssignedRoles().take());        
        
    }
    
    @Test
    public void pruebaDosLlamadasUnOperadorUnDirector() throws InterruptedException {
        int numberIncomingCalls = 2;
        int numberLines = 2;
        
        Mockito.when( this.rolFactoryMock.generateAvailableRoles(Mockito.anyInt()) ).thenReturn(UN_OPERADOR_UN_DIRECTOR);
        
        this.callCenter.start(numberLines);
        launchIncomingCalls(numberIncomingCalls);
        
        Assert.assertEquals("Esperando rol OPERADOR", Rol.OPERADOR, this.callCenter.getAssignedRoles().take());
        Assert.assertEquals("Esperando rol DIRECTOR", Rol.DIRECTOR, this.callCenter.getAssignedRoles().take());
        
    }
    
    @Test
    public void pruebaDosLlamadasUnOperadorUnSupervisor() throws InterruptedException {
        int numberIncomingCalls = 2;
        int numberLines = 2;
        
        Mockito.when( this.rolFactoryMock.generateAvailableRoles(Mockito.anyInt()) ).thenReturn(UN_OPERADOR_UN_SUPERVISOR);
        
        this.callCenter.start(numberLines);
        launchIncomingCalls(numberIncomingCalls);
        
        Assert.assertEquals("Esperando rol OPERADOR", Rol.OPERADOR, this.callCenter.getAssignedRoles().take());
        Assert.assertEquals("Esperando rol SUPERVISOR", Rol.SUPERVISOR, this.callCenter.getAssignedRoles().take());
        
    }
    
    @Test
    public void pruebaTresLlamadasUnOperadorUnSupervisorUnDirector() throws InterruptedException {
        int numberIncomingCalls = 3;
        int numberLines = 3;
        
        Mockito.when( this.rolFactoryMock.generateAvailableRoles(Mockito.anyInt()) ).thenReturn(UN_OPERADOR_UN_SUPERVISOR_UN_DIRECTOR);
        
        this.callCenter.start(numberLines);
        launchIncomingCalls(numberIncomingCalls);
        
        Assert.assertEquals("Esperando rol OPERADOR", Rol.OPERADOR, this.callCenter.getAssignedRoles().take());
        Assert.assertEquals("Esperando rol SUPERVISOR", Rol.SUPERVISOR, this.callCenter.getAssignedRoles().take());
        Assert.assertEquals("Esperando rol DIRECTOR", Rol.DIRECTOR, this.callCenter.getAssignedRoles().take());
        
    }
    
    @Test
    public void pruebaCincoLlamadasTresOperadoresUnSupervisorUnDirector() throws InterruptedException {
        int numberIncomingCalls = 5;
        int numberLines = 5;
        
        Mockito.when( this.rolFactoryMock.generateAvailableRoles(Mockito.anyInt()) ).thenReturn(TRES_OPERADORES_UN_SUPERVISOR_UN_DIRECTOR);
        
        this.callCenter.start(numberLines);
        launchIncomingCalls(numberIncomingCalls);
        
        Assert.assertEquals("Esperando rol OPERADOR", Rol.OPERADOR, this.callCenter.getAssignedRoles().take());
        Assert.assertEquals("Esperando rol OPERADOR", Rol.OPERADOR, this.callCenter.getAssignedRoles().take());
        Assert.assertEquals("Esperando rol OPERADOR", Rol.OPERADOR, this.callCenter.getAssignedRoles().take());
        Assert.assertEquals("Esperando rol SUPERVISOR", Rol.SUPERVISOR, this.callCenter.getAssignedRoles().take());
        Assert.assertEquals("Esperando rol DIRECTOR", Rol.DIRECTOR, this.callCenter.getAssignedRoles().take());
        
    }
    
    @Test
    public void pruebaSeisLlamadasTresOperadoresDosSupervisoresUnDirector() throws InterruptedException {
        int numberIncomingCalls = 6;
        int numberLines = 6;
        
        Mockito.when( this.rolFactoryMock.generateAvailableRoles(Mockito.anyInt()) ).thenReturn(TRES_OPERADORES_DOS_SUPERVISORES_UN_DIRECTOR);
        
        this.callCenter.start(numberLines);
        launchIncomingCalls(numberIncomingCalls);
        
        Assert.assertEquals("Esperando rol OPERADOR", Rol.OPERADOR, this.callCenter.getAssignedRoles().take());
        Assert.assertEquals("Esperando rol OPERADOR", Rol.OPERADOR, this.callCenter.getAssignedRoles().take());
        Assert.assertEquals("Esperando rol OPERADOR", Rol.OPERADOR, this.callCenter.getAssignedRoles().take());
        Assert.assertEquals("Esperando rol SUPERVISOR", Rol.SUPERVISOR, this.callCenter.getAssignedRoles().take());
        Assert.assertEquals("Esperando rol SUPERVISOR", Rol.SUPERVISOR, this.callCenter.getAssignedRoles().take());
        Assert.assertEquals("Esperando rol DIRECTOR", Rol.DIRECTOR, this.callCenter.getAssignedRoles().take());
        
    }
    
    
    @Test
    public void pruebaDiezLlamadasCincoOperadoresTresSupervisoresDosDirectores() throws InterruptedException {
        int numberIncomingCalls = 10;
        int numberLines = 10;
        
        Mockito.when( this.rolFactoryMock.generateAvailableRoles(Mockito.anyInt()) ).thenReturn(CINCO_OPERADORES_TRES_SUPERVISORES_DOS_DIRECTORES);
        
        this.callCenter.start(numberLines);
        launchIncomingCalls(numberIncomingCalls);
        
        Assert.assertEquals("Esperando rol OPERADOR", Rol.OPERADOR, this.callCenter.getAssignedRoles().take());
        Assert.assertEquals("Esperando rol OPERADOR", Rol.OPERADOR, this.callCenter.getAssignedRoles().take());
        Assert.assertEquals("Esperando rol OPERADOR", Rol.OPERADOR, this.callCenter.getAssignedRoles().take());
        Assert.assertEquals("Esperando rol OPERADOR", Rol.OPERADOR, this.callCenter.getAssignedRoles().take());
        Assert.assertEquals("Esperando rol OPERADOR", Rol.OPERADOR, this.callCenter.getAssignedRoles().take());
        Assert.assertEquals("Esperando rol SUPERVISOR", Rol.SUPERVISOR, this.callCenter.getAssignedRoles().take());
        Assert.assertEquals("Esperando rol SUPERVISOR", Rol.SUPERVISOR, this.callCenter.getAssignedRoles().take());
        Assert.assertEquals("Esperando rol SUPERVISOR", Rol.SUPERVISOR, this.callCenter.getAssignedRoles().take());
        Assert.assertEquals("Esperando rol DIRECTOR", Rol.DIRECTOR, this.callCenter.getAssignedRoles().take());
        Assert.assertEquals("Esperando rol DIRECTOR", Rol.DIRECTOR, this.callCenter.getAssignedRoles().take());
        
    }
    

    private void launchIncomingCalls(int number) {
        Dispatcher dispatcher1 = this.callCenter.getDispatcher();
        System.out.println("dispatcher: " + dispatcher1);
        
        for(int i=0; i<number; i++){
            System.out.println("Launch call");
            dispatcher1.dispatchCall(CallDataFactory.build());
        }
        
    }
    
}
