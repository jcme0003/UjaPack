/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades.puntocontrol;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotBlank;

/**
 * Clase que representa un punto de control
 * @author Jose Carlos Mena
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class PuntoControl {
    /** Centro logistico del punto de control */
    @Id
    private int idCentro = 0;
    
    /** Provincia en la que se encuentra el punto de control */
    @NotBlank
    private String oficinaEntrega = "";
    
    public PuntoControl(){
        
    }
    /**
     * Constructor de punto de control
     * @param oficinaEntrega nombre de la provincia
     */
    public PuntoControl(String oficinaEntrega){
        this.idCentro = -1;
        this.oficinaEntrega = oficinaEntrega;
    }
    
    /**
     * Constructor de punto de control
     * @param idCentro identificador del centro logistico
     * @param oficinaEntrega nombre de la provincia
     */
    public PuntoControl(int idCentro, String oficinaEntrega){
        this.idCentro = idCentro;
        this.oficinaEntrega = oficinaEntrega;
    }

    /**
     * @return el identificador del centro logistico
     */
    public int getIdCentro() {
        return idCentro;
    }

    /**
     * @return la provincia
     */
    public String getProvincia() {
        return oficinaEntrega;
    }
    
}
