/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST.DTO;

/**
 * DTO auxiliar para indicar el tipo de nitificacion a realizar sobre los pasos por punto de control
 * @author Jose Carlos Mena
 */
public class DTOTipoNotificacion {
    /** Tipo de notificacion */
    private String tipo;
    
    public DTOTipoNotificacion(String tipoNotificacion){
        this.tipo = tipoNotificacion;
    }

    public String getTipoNotificacion() {
        return tipo;
    }
}
