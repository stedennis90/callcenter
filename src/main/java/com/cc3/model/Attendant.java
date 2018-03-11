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
    
    
    
    private String name;
    private Rol rol;

    public Attendant(String nombre, Rol rol) {
        this.name = nombre;
        this.rol = rol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
}
