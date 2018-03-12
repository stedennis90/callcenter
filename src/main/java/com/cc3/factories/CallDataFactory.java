/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cc3.factories;

import com.cc3.model.CallData;
import java.util.Random;

/**
 * Fabrica de datos de llamada.
 * @author dennis
 */
public class CallDataFactory {
    
    private static final String[] NAMES = {"Andres", "Bella", "Carlos", "Daniela", "Esteban", "Felipe",
                                "Gabriela", "Hernan", "Ignacio", "Juliana", "Katy", "Lucia",
                                "Marcela", "Nestor", "Oscar", "Pedro", "Queen", "Raul",
                                "Sebastian", "Tatiana", "Uriel", "Victor", "Walter", "Xiomara",
                                "Yenny", "Zulay"                                
                            };
    
    private static int index = 1;
    private static final Random random = new Random();
    
    /**
     * Crea los datos de una llamada de manera aleatoria.
     * @return Datos de la llamada creados.
     */
    public static CallData build(){
        int duration = (int) ((random.nextFloat()*(10000-5000)) + 5000);
        int from = random.nextInt(9999);
        int idx = random.nextInt(NAMES.length);
        return build(from, idx, duration);
    }
    
    /**
     * Crea los datos de una llamada
     * @param from Numero origen.
     * @param idxName Secuencia de la llamada.
     * @param duration Duración de la llamada.
     * @return Datos de la llamada creados.
     */
    public static CallData build(int from, int idxName, int duration){
        System.out.println("Duration .." + duration);
        return new CallData(from, NAMES[idxName] + " " + index, duration);
    }
    
}
