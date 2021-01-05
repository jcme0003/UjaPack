/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades.puntocontrol;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotBlank;

/**
 * Clase que representa un punto de control
 * @author Jose Carlos Mena
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class PuntoControl implements Serializable {
    /** Enum que indicara el tipo de punto de control */
    public enum Tipo{
        CENTRO_LOGISTICO,  //centro logistico
        OFICINA;   //oficina
    }
    
    /** Identificador tabla */
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int id;
    
    /** Centro logistico del punto de control */
    private int idCentro;
    
    private Tipo tipo;
    
    /** Provincia en la que se encuentra el punto de control */
    @NotBlank
    private String oficinaEntrega;

    public PuntoControl() {
        this.tipo = Tipo.OFICINA;
        this.idCentro = -1;
        this.oficinaEntrega = null;
    }
    
    /**
     * Constructor de punto de control
     * @param tipo
     * @param oficinaEntrega nombre de la provincia
     */
    public PuntoControl(Tipo tipo, String oficinaEntrega){
        this.tipo = tipo;
        this.idCentro = -1;
        this.oficinaEntrega = oficinaEntrega;
    }
    
    /**
     * Constructor de punto de control
     * @param idCentro identificador del centro logistico
     * @param oficinaEntrega nombre de la provincia
     */
    public PuntoControl(Tipo tipo, int idCentro, String oficinaEntrega){
        this.tipo = tipo;
        this.idCentro = idCentro;
        this.oficinaEntrega = oficinaEntrega;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
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

    /**
     * @return tipo punto de control
     */
    public Tipo getTipo() {
        return tipo;
    }
    
}
