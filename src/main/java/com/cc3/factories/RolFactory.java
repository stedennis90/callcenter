/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cc3.factories;

import com.cc3.model.Rol;
import java.util.Arrays;

/**
 * Fabrica de roles.
 * @author dennis
 */
public class RolFactory {
    
    
    /**
     * Crea una lista de roles
     * @param numberLines Número de roles a generar.
     * @return Array de roles.
     */
    public Rol[] generateAvailableRoles(int numberLines) {
        return Arrays.copyOf(exampleRoles, numberLines);
    }
    
    
    /**
     * Array Roles de ejemplo.
     */
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
    
    
}
