/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import es.ujaen.dae.ujapack.objetosvalor.Paquete;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Envio de UjaPack
 * @author Jose Carlos Mena
 */
@Entity
public class Envio implements Serializable {
    /** Enum que indicara el estado del envio */
    public enum Estado{
        PENDIENTE,  //aun no se ha enviado
        TRANSITO,   //el paquete está en una oficina provincial o en un centro de logística
        REPARTO,    //en el vehículo de reparto
        ENTREGADO;  //entregado por el vehículo de reparto
    }
    
    /** Localizador del envio */
    @Id
    private int localizador;
    
    /** Estado del pedido */
    private Estado estado;
    
    /** Fecha de llegada del envio */
    private LocalDate fechaLlegada;
    
    /** Hora de llegada del envio */
    private LocalDate horaLlegada;
    
    /** Coste de realizar el envio */
    private float importe;
    
    /** Paquetes asociadas al envio */
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name = "envioPaquetes")
    private List<Paquete> paquetes;
    
    /** Ruta asociada al pedido */
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name = "envioLocalizador")
    private List<PasoPuntoControl> ruta;
    
    /** Cliente asociado a remitente */
    @ManyToOne
    @JoinColumn(name = "remitenteDni")
    private Cliente remitente;
    
    /** Cliente asociado a destinatario */
    @ManyToOne
    @JoinColumn(name = "destinatarioDni")
    private Cliente destinatario;

    public Envio() {
    }
    
    /**
     * Constructor de envio
     * @param localizador numero entero de 10 digitos (unico) generado aleatoriamente
     * @param remitente cliente que realiza el envio
     * @param destinatario cliente que recibira el envio
     * @param paquetes lista de paquetes que componen el envio
     */
    public Envio(int localizador, Cliente remitente, Cliente destinatario, List<Paquete> paquetes){
        this.localizador = localizador;
        this.estado = Estado.PENDIENTE;
        this.fechaLlegada = null;
        this.horaLlegada = null;
        this.paquetes = paquetes;
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.ruta = new ArrayList<>();
        this.importe = 0;
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
     * @return el estado
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * @param estado el estado a insertar
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
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
     * @return the paquetes
     */
    public List<Paquete> getPaquetes() {
        return paquetes;
    }

    /**
     * @param paquetes the paquetes to set
     */
    public void setPaquetes(List<Paquete> paquetes) {
        this.paquetes = paquetes;
    }

    /**
     * @return the ruta
     */
    public List<PasoPuntoControl> getRuta() {
        return ruta;
    }

    /**
     * @param ruta the ruta to set
     */
    public void setRuta(List<PasoPuntoControl> ruta) {
        this.ruta = ruta;
    }

    /**
     * @return el remitente
     */
    public Cliente getRemitente() {
        return remitente;
    }

    /**
     * @param remitente el remitente a insertar
     */
    public void setRemitente(Cliente remitente) {
        this.remitente = remitente;
    }

    /**
     * @return el destinatario
     */
    public Cliente getDestinatario() {
        return destinatario;
    }

    /**
     * @param destinatario el destinatario a insertar
     */
    public void setDestinatario(Cliente destinatario) {
        this.destinatario = destinatario;
    }
    
    /**
     * Calcula importe del envio
     * Formula: importe = peso(kg) * dim(cm2) * (num_puntos_control + 1) / 1000
     */
    public void calculaImporte(){
        float dim = 0.0f;
        
        for(Paquete paquete : paquetes){
            dim = paquete.getAltura() * paquete.getAnchura() * paquete.getProfundidad();
            this.importe += ((paquete.getPeso() * dim * (ruta.size() + 1)) / 1000);
        }

    }
    
}
