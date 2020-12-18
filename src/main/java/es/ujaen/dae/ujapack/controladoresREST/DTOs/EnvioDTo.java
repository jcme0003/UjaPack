/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST.DTOs;

import es.ujaen.dae.ujapack.entidades.Cliente;
import es.ujaen.dae.ujapack.entidades.Envio;
import es.ujaen.dae.ujapack.entidades.PasoPuntoControl;
import es.ujaen.dae.ujapack.objetosvalor.Paquete;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author joseo
 */
public class EnvioDTo {
    /** Localizador del envio */
    private int localizador;
    
    /** Estado del pedido */
    private Envio.Estado estado;
    
    /** Fecha de llegada del envio */
    private LocalDate fechaLlegada;
    
    /** Hora de llegada del envio */
    private LocalDate horaLlegada;
    
    /** Coste de realizar el envio */
    private float importe;
    
        
    /** DNI del cliente asociado a remitente */
    String remitente;
    
    /** DNI del cliente asociado a destinatario */
    String destinatario;

    public EnvioDTo(int localizador, Envio.Estado estado, LocalDate fechaLlegada, LocalDate horaLlegada, float importe, String remitente, String destinatario) {
        this.localizador = localizador;
        this.estado = estado;
        this.fechaLlegada = fechaLlegada;
        this.horaLlegada = horaLlegada;
        this.importe = importe;
        this.remitente = remitente;
        this.destinatario = destinatario;
    }
    
    public EnvioDTo(Envio envio){
        this.destinatario = envio.getDestinatario().getDni();
        this.estado = envio.getEstado();
        this.fechaLlegada = envio.getHoraLlegada();
        this.importe = envio.getImporte();
        this.localizador = envio.getLocalizador();
        this.remitente = envio.getRemitente().getDni();
        
    }

    public int getLocalizador() {
        return localizador;
    }

    public Envio.Estado getEstado() {
        return estado;
    }

    public LocalDate getFechaLlegada() {
        return fechaLlegada;
    }

    public LocalDate getHoraLlegada() {
        return horaLlegada;
    }

    public float getImporte() {
        return importe;
    }

    public String getRemitente() {
        return remitente;
    }

    public String getDestinatario() {
        return destinatario;
    }
    
    
}
