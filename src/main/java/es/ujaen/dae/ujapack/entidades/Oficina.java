/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

/**
 * Oficina correspondiente a centro logístico
 * @author Jose Carlos Mena
 */
public class Oficina {
    /** Identificador de oficina */
    private String idOficina;
    /** Nombre de provincia a la que corresponde esta oficina */
    private String nombreProvincia;
    
    public Oficina(String idOficina, String nombreProvincia){
        this.idOficina = idOficina;
        this.nombreProvincia = nombreProvincia;
    }

    /**
     * @return el identificador de la oficina
     */
    public String getIdOficina() {
        return idOficina;
    }

    /**
     * @param idOficina el identificador de la oficina a insertar
     */
    public void setIdOficina(String idOficina) {
        this.idOficina = idOficina;
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
