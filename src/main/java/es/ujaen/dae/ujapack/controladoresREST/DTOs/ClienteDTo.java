/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST.DTOs;

import es.ujaen.dae.ujapack.entidades.Cliente;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author joseo
 */
public class ClienteDTo {
    /** DNI del cliente */
    private String dni;
    
    /** Nombre del cliente */
    private String nombre;
    
    /** Apellidos del cliente */
    private String apellidos;
    
    /** Direccion del cliente */
    private String direccion;
    
    /** Provincia del cliente */
    private String provincia;
    
    /** Telefono del cliente */
    private String telefono;
    
    /** Email del cliente */
    private String email;

    public ClienteDTo(String dni, String nombre, String apellidos, String direccion, String provincia, String telefono, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.provincia = provincia;
        this.telefono = telefono;
        this.email = email;
    }
    
    public Cliente nuevoClienteDTo(){
        return new Cliente(dni,nombre,apellidos,direccion,provincia,telefono,email);
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }
    
    
}

