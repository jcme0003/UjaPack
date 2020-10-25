/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.util.profundidad;

import es.ujaen.dae.ujapack.entidades.puntocontrol.CentroLogistico;
import java.util.List;

/**
 *
 * @author Root
 */
public class Nodo {
    /** Centro logistico al que hace referencia el nodo */
    private CentroLogistico centroLogistico;
    /** Indica si el nodo ha sido visitado o no */
    private boolean visitado;
    /** Lista de nodos adyacentes */
    private List<Nodo> adyacentes;
    /** Nodo predecesor */
    /** Creo que puede eliminarse */
    private Nodo predecesor;

    /**
     * Constructor por defecto vacio
     */
    public Nodo() {
        this.centroLogistico = null;
        this.visitado = false;
        this.adyacentes = null;
        this.predecesor = null;
    }

    /**
     * 
     * @param centroLogistico 
     */
    public Nodo(CentroLogistico centroLogistico) {
        this.centroLogistico = centroLogistico;
        this.visitado = false;
        this.adyacentes = null;
        this.predecesor = null;
    }

    /**
     * 
     * @param nodo 
     */
    public void addVecino(Nodo nodo) {
        this.getAdyacentes().add(nodo);
    }

    /**
     * @return the centroLogistico
     */
    public CentroLogistico getCentroLogistico() {
        return centroLogistico;
    }

    /**
     * @param centroLogistico the centroLogistico to set
     */
    public void setCentroLogistico(CentroLogistico centroLogistico) {
        this.centroLogistico = centroLogistico;
    }

    /**
     * @return the visitado
     */
    public boolean isVisitado() {
        return visitado;
    }

    /**
     * @param visitado the visitado to set
     */
    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    /**
     * @return the adyacentes
     */
    public List<Nodo> getAdyacentes() {
        return adyacentes;
    }

    /**
     * @param adyacentes the adyacentes to set
     */
    public void setAdyacentes(List<Nodo> adyacentes) {
        this.adyacentes = adyacentes;
    }

    /**
     * @return the predecesor
     */
    public Nodo getPredecesor() {
        return predecesor;
    }

    /**
     * @param predecesor the predecesor to set
     */
    public void setPredecesor(Nodo predecesor) {
        this.predecesor = predecesor;
    }

    @Override
    public String toString() {
        return "Nodo{" +
                "CentroLogistico='" + this.centroLogistico.getIdCentro() + '\'' +
                '}';
    }
}
