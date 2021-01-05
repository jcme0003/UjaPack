/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST.DTO;

import es.ujaen.dae.ujapack.entidades.Envio;
import es.ujaen.dae.ujapack.entidades.PasoPuntoControl;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO que mostrará estado del pedido y ruta
 * @author Jose Carlos Mena
 */
public class DTORuta {
    /** Estado del pedido */
    private String estado;
    
    /** Ruta asociada al pedido */
    private List<DTOPasoPuntoControl> ruta;
    
    public DTORuta(Envio.Estado estado, List<PasoPuntoControl> ruta){
        this.estado = estado.toString();
        this.ruta = new ArrayList<>();
        for(PasoPuntoControl puntoControl : ruta){
            this.ruta.add(new DTOPasoPuntoControl(puntoControl));
        }
    }
    
    public DTORuta(Envio envio){
        this.estado = envio.getEstado().toString();
        this.ruta = new ArrayList<>();
        for(PasoPuntoControl puntoControl : envio.getRuta()){
            this.ruta.add(new DTOPasoPuntoControl(puntoControl));
        }
    }

    public String getEstado() {
        return estado;
    }

    public List<DTOPasoPuntoControl> getRuta() {
        return ruta;
    }
    
}
