/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import es.ujaen.dae.ujapack.entidades.puntocontrol.CentroLogistico;
import es.ujaen.dae.ujapack.entidades.puntocontrol.Oficina;
import es.ujaen.dae.ujapack.entidades.puntocontrol.PuntoControl;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;

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
    @NotBlank
    private PuntoControl puntoDeControl;
    
    /**
     * Constructor de paso por punto de control
     * @param oficina Oficina de origen o destino
     */
    public PasoPuntoControl(Oficina oficina){
        this.fechaLlegada = LocalDate.MIN;
        this.fechaSalida = LocalDate.MIN;
        this.puntoDeControl = oficina;
    }
    
    /**
     * Constructor de paso por punto de control
     * @param centroLogistico Centro logistico de origen, destino o intermediario/s
     */
    public PasoPuntoControl(CentroLogistico centroLogistico){
        this.fechaLlegada = LocalDate.MIN;
        this.fechaSalida = LocalDate.MIN;
        this.puntoDeControl = centroLogistico;
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
     * @return el puntoDeControl
     */
    public PuntoControl getPuntoDeControl() {
        return puntoDeControl;
    }

    /**
     * @param puntoDeControl el puntoDeControl a insertar
     */
    public void setPuntoDeControl(PuntoControl puntoDeControl) {
        this.puntoDeControl = puntoDeControl;
    }
    
    /**
     * Notificacion al sistema del paso por punto de control del envio
     */
    public void notificacionLlegada(){
        this.fechaLlegada = LocalDate.now();
    }
    
    /**
     * Notificacion al sistema de la salida por punto de control del envio
     */
    public void notificacionSalida(){
        this.fechaSalida = LocalDate.now();
    }
}
