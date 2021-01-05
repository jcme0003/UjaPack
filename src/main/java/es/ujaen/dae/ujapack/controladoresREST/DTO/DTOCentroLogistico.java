/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST.DTO;

import es.ujaen.dae.ujapack.entidades.puntocontrol.CentroLogistico;

/**
 * DTO para información de centros logisticos
 * @author Jose Carlos Mena
 */
public class DTOCentroLogistico {
    /** Nombre del centro logistico */
    private String nombre;
    
    /** Ciudad donde se encuentra localizado el centro logistico */
    private String localizacion;
    
    public DTOCentroLogistico(int idCentro, String nombre, String localizacion){
        this.nombre = nombre;
        this.localizacion = localizacion;
    }
    
    public DTOCentroLogistico(CentroLogistico centroLogistico){
        this.nombre = centroLogistico.getNombre();
        this.localizacion = centroLogistico.getLocalizacion();
    }
    
    public String getNombre() {
        return nombre;
    }

    public String getLocalizacion() {
        return localizacion;
    }
    
}
