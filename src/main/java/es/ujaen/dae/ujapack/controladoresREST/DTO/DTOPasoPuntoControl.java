/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST.DTO;

import es.ujaen.dae.ujapack.entidades.PasoPuntoControl;

/**
 * DTO que mostrará los datos correspondientes a la ruta
 * @author Jose Carlos Mena
 */
public class DTOPasoPuntoControl {
    /** Fecha de llegada al paso por punto de control */
    private String fechaLlegada;
    
    /** Fecha de salida del punto de control */
    private String fechaSalida;
    
    public DTOPasoPuntoControl(String fechaLlegada, String fechaSalida){
        this.fechaLlegada = fechaLlegada;
        this.fechaSalida = fechaSalida;
    }
    
    public DTOPasoPuntoControl(PasoPuntoControl ppc){
        this.fechaLlegada = ppc.getFechaLlegada().toString();
        this.fechaSalida = ppc.getFechaSalida().toString();
    }

    public String getFechaLlegada() {
        return fechaLlegada;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }
    
    
    
}
