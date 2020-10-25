/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import es.ujaen.dae.ujapack.util.ExprReg;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Clientes de UjaPack
 * @author Jose Carlos Mena
 */
public class Cliente {
    /** DNI del cliente */
    @Pattern(regexp=ExprReg.DNI)
    private String dni;
    
    /** Nombre del cliente */
    @NotBlank
    private String nombre;
    
    /** Apellidos del cliente */
    @NotBlank
    private String apellidos;
    
    /** Direccion del cliente */
    @NotBlank
    private String direccion;
    
    /** Provincia del cliente */
    @NotBlank
    private String provincia;
    
    /** Telefono del cliente */
    @Pattern(regexp=ExprReg.TLF)
    private String telefono;
    
    /** Email del cliente */
    @Email
    private String email;
    
    /**
     * Constructor de cliente
     * @param dni dni (identificador) del cliente
     * @param nombre nombre del cliente
     * @param apellidos apellidos del cliente
     * @param direccion direccion del cliente
     * @param provincia provincia del cliente
     * @param telefono telefono de contacto del cliente
     * @param email email de contacto del cliente
     */
    public Cliente(String dni, String nombre, String apellidos, String direccion, String provincia, String telefono, String email){
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.provincia = provincia;
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
     * @return la provincia
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * @param provincia la provincia a insertar
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
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
