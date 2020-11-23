/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades.puntocontrol;


import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

/**
 * Oficina correspondiente a centro logístico
 * @author Jose Carlos Mena
 */

@Entity
public class Oficina extends PuntoControl{
    /** Nombre de provincia a la que corresponde esta oficina */
    @NotBlank
    private String nombreProvincia;

    public Oficina() {
    }
    
    /**
     * Constructor de oficina
     * @param nombreProvincia nombre de provincia a la que pertenece la oficina
     */
    public Oficina(String nombreProvincia){
        super(nombreProvincia);
        this.nombreProvincia = nombreProvincia;
    }
 
    /**
     * @return el nombre de la provincia
     */
    public String getNombreProvincia() {
        return nombreProvincia;
    }

    /**
     * @param nombreProvincia el nombre de la provincia a insertar
     */
    public void setNombreProvincia(String nombreProvincia) {
        this.nombreProvincia = nombreProvincia;
    }
    
    
}
