/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import java.time.LocalDate;
import java.util.List;

/**
 * Envio de UjaPack
 * @author Jose Carlos Mena
 */
public class Envio {
    /** Localizador del envio */
    private int localizador;
    /** Estado del pedido */
    
    /** Fecha de llegada del envio */
    private LocalDate fechaLlegada;
    /** Hora de llegada del envio */
    private LocalDate horaLlegada;
    /** Coste de realizar el envio */
    private float importe;
    
    /** Paquetes asociadas al envio */
    //List<Paquete> paquetes;
    
    /** Cuentas asociadas al cliente */
    //List<PasoPuntoControl> ruta;
    
    public Envio(int localizador){
        this.localizador = localizador;
        
        //paquetes = new ArrayList<>();
        //ruta = new ArrayList<>();
    }

    /**
     * @return el localizador
     */
    public int getLocalizador() {
        return localizador;
    }

    /**
     * @param localizador el localizador a insertar
     */
    public void setLocalizador(int localizador) {
        this.localizador = localizador;
    }

    /**
     * @return la fechaLlegada
     */
    public LocalDate getFechaLlegada() {
        return fechaLlegada;
    }

    /**
     * @param fechaLlegada la fechaLlegada a insertar
     */
    public void setFechaLlegada(LocalDate fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    /**
     * @return la horaLlegada
     */
    public LocalDate getHoraLlegada() {
        return horaLlegada;
    }

    /**
     * @param horaLlegada la horaLlegada a insertar
     */
    public void setHoraLlegada(LocalDate horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    /**
     * @return el importe
     */
    public float getImporte() {
        return importe;
    }

    /**
     * @param importe el importe a insertar
     */
    public void setImporte(float importe) {
        this.importe = importe;
    }
    
    /**
     * Calcula importe del envio
     * @return importe que cuesta el envio
     */
    public float calcularImporte(){
        return 0;
    }
}
