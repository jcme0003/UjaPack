/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST.DTO;

import es.ujaen.dae.ujapack.objetosvalor.Paquete;

/**
 * DTO para información de paquetes
 * @author Jose Carlos Mena
 */
public class DTOPaquete {
    /** Peso del paquete */
    private float peso;
    
    /** Anchura del paquete */
    private float anchura;
    
    /** Altura del paquete */
    private float altura;
    
    /** Profundidad del paquete */
    private float profundidad;
    
    public DTOPaquete(float peso, float anchura, float altura, float profundidad){
        this.peso = peso;
        this.anchura = anchura;
        this.altura = altura;
        this.profundidad = profundidad;
    }
    
    public DTOPaquete(Paquete paquete){
        this.peso = paquete.getPeso();
        this.anchura = paquete.getAnchura();
        this.altura = paquete.getAltura();
        this.profundidad = paquete.getProfundidad();
    }

    public float getPeso() {
        return peso;
    }

    public float getAnchura() {
        return anchura;
    }

    public float getAltura() {
        return altura;
    }

    public float getProfundidad() {
        return profundidad;
    }
    
    public Paquete aPaquete(){
        return new Paquete(peso, anchura, altura, profundidad);
    }
}
