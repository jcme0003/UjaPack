/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST.DTO;

import es.ujaen.dae.ujapack.entidades.Envio;
import java.time.LocalDate;

/**
 * DTO para recopilación de datos de envio
 * @author joseo, Ana, Jose Carlos Mena
 */
public class DTOEnvio {
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
    
    DTORuta ruta;

    public DTOEnvio(int localizador, Envio.Estado estado, LocalDate fechaLlegada, LocalDate horaLlegada, float importe, String remitente, String destinatario) {
        this.localizador = localizador;
        this.estado = estado;
        this.fechaLlegada = fechaLlegada;
        this.horaLlegada = horaLlegada;
        this.importe = importe;
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.ruta = new DTORuta();
    }
    
    public DTOEnvio(Envio envio){
        this.localizador = envio.getLocalizador();
        this.estado = envio.getEstado();
        this.fechaLlegada = envio.getFechaLlegada();
        this.horaLlegada = envio.getHoraLlegada();
        this.importe = envio.getImporte();
        this.remitente = envio.getRemitente().getDni();
        this.destinatario = envio.getDestinatario().getDni();
        this.ruta = new DTORuta(envio.getEstado(), envio.getRuta());
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
    
    public DTORuta getRuta(){
        return ruta;
    }
    
}
