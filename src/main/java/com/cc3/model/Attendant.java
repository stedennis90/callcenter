/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cc3.model;

/**
 *
 * @author Dmartinezb
 */
public class Attendant {
    
    enum ROL{OPERADOR, SUPERVISOR, DIRECTOR }
    
    private String name;
    private ROL rol;

    public Attendant(String nombre, ROL rol) {
        this.name = nombre;
        this.rol = rol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public ROL getRol() {
        return rol;
    }

    public void setRol(ROL rol) {
        this.rol = rol;
    }
    
}
