/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import es.ujaen.dae.ujapack.excepciones.PedidoEntregado;
import es.ujaen.dae.ujapack.objetosvalor.Paquete;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Envio de UjaPack
 * @author Jose Carlos Mena
 */
public class Envio {
    /** Enum que indicara el estado del envio */
    public enum Estado{
        PENDIENTE,  //aun no se ha enviado
        TRANSITO,   //el paquete está en una oficina provincial o en un centro de logística
        REPARTO,    //en el vehículo de reparto
        ENTREGADO;  //entregado por el vehículo de reparto
    }
    
    /** Localizador del envio */
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
    private List<Paquete> paquetes;
    
    /** Cuentas asociadas al cliente */
    private List<PasoPuntoControl> ruta;
    
    /** Cliente asociado a remitente */
    private Cliente remitente;
    
    /** Cliente asociado a destinatario */
    private Cliente destinatario;
    
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
        this.fechaLlegada = LocalDate.MIN;
        this.horaLlegada = LocalDate.MIN;
        
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
        
//        System.out.println(this.importe + "€");
    }
    
    /**
     * Actualiza el estado del envio
     */
    public void actualizaEstadoEnvio(){
        // Si el pedido esta entregado no se puede actualizar
        if(this.estado == Estado.ENTREGADO){
            throw new PedidoEntregado();
        }
        
        int i = 0;
        for(PasoPuntoControl ppc : ruta){
            i++;
            // Si el estado es pendiente y sabemos que ya ha salido de la oficina origen cambiamos estado a transito
            if(ppc.getFechaSalida() != LocalDate.MIN && this.estado == Estado.PENDIENTE){
                this.estado = Estado.TRANSITO;
            }
            
            // Si sabemos que ya ha salido de la oficina de entrega lo actualizamos a reparto
            if(ppc.getFechaSalida() != LocalDate.MIN && i == ruta.size()){
                this.estado = Estado.REPARTO;
            }
        }
    }
    
}
