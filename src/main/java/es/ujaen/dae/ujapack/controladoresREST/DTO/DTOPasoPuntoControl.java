/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST.DTO;

import es.ujaen.dae.ujapack.entidades.PasoPuntoControl;
import es.ujaen.dae.ujapack.entidades.puntocontrol.PuntoControl;
import es.ujaen.dae.ujapack.entidades.puntocontrol.PuntoControl.Tipo;

/**
 * DTO que mostrará los datos correspondientes a la ruta
 * @author Jose Carlos Mena
 */
public class DTOPasoPuntoControl {
    /** Identificador */
    private String id;
    
    /** Tipo del punto de control */
    private String tipo;
    
    /** Fecha de llegada al paso por punto de control */
    private String fechaLlegada;
    
    /** Fecha de salida del punto de control */
    private String fechaSalida;
    
    public DTOPasoPuntoControl(String tipo, String fechaLlegada, String fechaSalida){
        this.id = "UNDEFINED";
        this.tipo = "UNDEFINED";
        this.fechaLlegada = fechaLlegada;
        this.fechaSalida = fechaSalida;
    }
    
    public DTOPasoPuntoControl(PasoPuntoControl ppc){
        this.fechaLlegada = ppc.getFechaLlegada().toString();
        this.fechaSalida = ppc.getFechaSalida().toString();
        this.tipo = ppc.getPuntoDeControl().getTipo().toString();
        if(this.tipo.equalsIgnoreCase("OFICINA")){
            this.id = ppc.getPuntoDeControl().getProvincia();
        } else {
            this.id = Integer.toString(ppc.getPuntoDeControl().getIdCentro());
        }
    }

    public String getFechaLlegada() {
        return fechaLlegada;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }
    
    public String getTipo(){
        return tipo;
    }
    
    public String getId(){
        return id;
    }
    
}
