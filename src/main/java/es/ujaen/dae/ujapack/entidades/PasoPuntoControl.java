/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import es.ujaen.dae.ujapack.entidades.puntocontrol.CentroLogistico;
import es.ujaen.dae.ujapack.entidades.puntocontrol.Oficina;
import es.ujaen.dae.ujapack.entidades.puntocontrol.PuntoControl;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Puntos de control que componen la ruta del envio
 * @author Jose Carlos Mena
 */
@Entity
public class PasoPuntoControl implements Serializable {
    
    /** Identificador tabla */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    /** Localizador del envio asociado */
    private int localizador;
    
    /** Fecha de llegada al paso por punto de control */
    private LocalDateTime fechaLlegada;
    
    /** Fecha de salida del punto de control */
    private LocalDateTime fechaSalida;
    
    /** Punto de control */
    @ManyToOne
    @JoinColumn(name = "puntoDeControlId")
    private PuntoControl puntoDeControl;

    public PasoPuntoControl() {
    }
    
    /**
     * Constructor de paso por punto de control
     * @param localizador
     * @param oficina Oficina de origen o destino
     */
    public PasoPuntoControl(int localizador, Oficina oficina){
        this.localizador = localizador;
        this.fechaLlegada = null;
        this.fechaSalida = null;
        this.puntoDeControl = oficina;
    }
    
    /**
     * Constructor de paso por punto de control
     * @param localizador
     * @param centroLogistico Centro logistico de origen, destino o intermediario/s
     */
    public PasoPuntoControl(int localizador, CentroLogistico centroLogistico){
        this.localizador = localizador;
        this.fechaLlegada = null;
        this.fechaSalida = null;
        this.puntoDeControl = centroLogistico;
    }

    /**
     * @return the localizador
     */
    public int getLocalizador() {
        return localizador;
    }

    /**
     * @param localizador the localizador to set
     */
    public void setLocalizador(int localizador) {
        this.localizador = localizador;
    }

    /**
     * @return la fecha de llegada
     */
    public LocalDateTime getFechaLlegada() {
        return fechaLlegada;
    }

    /**
     * @param fechaLlegada la fecha de llegada a insertar
     */
    public void setFechaLlegada(LocalDateTime fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    /**
     * @return la fecha de salida
     */
    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    /**
     * @param fechaSalida la fecha de salida a insertar
     */
    public void setFechaSalida(LocalDateTime fechaSalida) {
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
        this.fechaLlegada = LocalDateTime.now();
    }
    
    /**
     * Notificacion al sistema de la salida por punto de control del envio
     */
    public void notificacionSalida(){
        this.fechaSalida = LocalDateTime.now();
    }
}
