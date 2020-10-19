/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import java.util.ArrayList;
import java.util.List;

/**
 * Centro logistico gestionado por ujapack
 * @author Jose Carlos Mena
 */
public class CentroLogistico {
    /** Identificador del centro logistico */
    private String idCentro;
    
    /** Oficinas asociadas al centro logistico */
    List<Oficina> oficinas;
    
    public CentroLogistico(String idCentro){
        this.idCentro = idCentro;
        
        oficinas = new ArrayList<>();
    }

    /**
     * @return el identificado del centro logistico
     */
    public String getIdCentro() {
        return idCentro;
    }

    /**
     * @param idCentro el identificado del centro logistico a insertar
     */
    public void setIdCentro(String idCentro) {
        this.idCentro = idCentro;
    }
    
    
}
