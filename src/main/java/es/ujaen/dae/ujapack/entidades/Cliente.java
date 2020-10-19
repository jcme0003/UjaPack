/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

/**
 * Clientes de UjaPack
 * @author Jose Carlos Mena
 */
public class Cliente {
    /** DNI del cliente */
    private String dni;
    /** Nombre del cliente */
    private String nombre;
    /** Apellidos del cliente */
    private String apellidos;
    /** Direccion del cliente */
    private String direccion;
    /** Telefono del cliente */
    private String telefono;
    /** Email del cliente */
    private String email;
    
    public Cliente(String dni, String nombre, String apellidos, String direccion, String telefono, String email){
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    /**
     * @return el dni
     */
    public String getDni() {
        return dni;
    }

    /**
     * @param dni el dni a insertar
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * @return el nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre el nombre a insertar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return los apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @param apellidos los apellidos a insertar
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * @return la direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion la direccion a insertar
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return el telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono el telefono a insertar
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return el email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email el email a insertar
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    
}
