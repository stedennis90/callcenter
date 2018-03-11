/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callcenter2.model;

/**
 *
 * @author Dmartinezb
 */
class Attendant {
    
    enum ROL{OPERADOR, SUPERVISOR, DIRECTOR }
    
    private String nombre;
    private ROL rol;

    public Attendant(String nombre, ROL rol) {
        this.nombre = nombre;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ROL getRol() {
        return rol;
    }

    public void setRol(ROL rol) {
        this.rol = rol;
    }
    
}
