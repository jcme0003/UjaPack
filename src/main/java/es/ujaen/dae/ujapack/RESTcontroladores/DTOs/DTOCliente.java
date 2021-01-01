/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.RESTcontroladores.DTOs;

import es.ujaen.dae.ujapack.entidades.Cliente;

/**
 * DTO para recopilación de datos de cliente
 * @author joseo, Ana
 */
public class DTOCliente {
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
    
    public DTOCliente(String dni, String nombre, String apellidos, String direccion, String provincia, String telefono, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.provincia = provincia;
        this.telefono = telefono;
        this.email = email;
    }
    
    public DTOCliente(Cliente cliente) {
        this.dni = cliente.getDni();
        this.nombre = cliente.getNombre();
        this.apellidos = cliente.getApellidos();
        this.direccion = cliente.getDireccion();
        this.provincia = cliente.getProvincia();
        this.telefono = cliente.getTelefono();
        this.email = cliente.getEmail();
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
    
    public Cliente nuevoClienteDTO(){
        return new Cliente(dni,nombre,apellidos,direccion,provincia,telefono,email);
    }
    
}
