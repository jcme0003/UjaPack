/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST.DTO;

import es.ujaen.dae.ujapack.entidades.puntocontrol.Oficina;

/**
 * DTO para información de oficinas
 * @author Jose Carlos Mena
 */
public class DTOOficina {
    /** Nombre de provincia a la que corresponde esta oficina */
    private String nombreProvincia;
    
    public DTOOficina(String nombreProvincia){
        this.nombreProvincia = nombreProvincia;
    }
    
    public DTOOficina(Oficina oficina){
        this.nombreProvincia = oficina.getNombreProvincia();
    }

    public String getNombreProvincia() {
        return nombreProvincia;
    }
}
