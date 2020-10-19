/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import java.time.LocalDate;

/**
 * Puntos de control que componen la ruta del envio
 * @author Jose Carlos Mena
 */
public class PasoPuntoControl {
    /** Fecha de llegada al paso por punto de control */
    private LocalDate fechaLlegada;
    /** Fecha de salida del punto de control */
    private LocalDate fechaSalida;
    /** Punto de control */
    //private PuntoControl puntoDeControl;
    
    public PasoPuntoControl(){
        this.fechaLlegada = LocalDate.now();
        this.fechaSalida = LocalDate.now();
        //this.puntoDeControl = null;
    }

    /**
     * @return la fecha de llegada
     */
    public LocalDate getFechaLlegada() {
        return fechaLlegada;
    }

    /**
     * @param fechaLlegada la fecha de llegada a insertar
     */
    public void setFechaLlegada(LocalDate fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    /**
     * @return la fecha de salida
     */
    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    /**
     * @param fechaSalida la fecha de salida a insertar
     */
    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }
    
    /**
     * Notificacion al sistema del paso por punto de control del envio
     */
    public void notificacionLlegada(){
        
    }
    
    /**
     * Notificacion al sistema de la salida por punto de control del envio
     */
    public void notificacionSalida(){
        
    }
}
