/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST.DTO;

import java.util.List;

/**
 * DTO auxiliar para DTOEnvio
 * @author Jose Carlos Mena
 */
public class DTOEnvioContext {
    private List<DTOPaquete> paquetes;
    
    private DTOCliente remitente;
    
    private DTOCliente destinatario;
    
    public DTOEnvioContext(List<DTOPaquete> paquetes, DTOCliente remitente, DTOCliente destinatario){
        this.paquetes = paquetes;
        this.remitente = remitente;
        this.destinatario = destinatario;
    }

    public List<DTOPaquete> getPaquetes() {
        return paquetes;
    }

    public DTOCliente getRemitente() {
        return remitente;
    }

    public DTOCliente getDestinatario() {
        return destinatario;
    }
}
