/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.objetosvalor;

/**
 * Clase que contiene las caracteristicas necesarias de un paquete
 * @author joseo
 */
public class Paquete {
    /** Peso del paquete */
    private float peso;
    /** Anchura del paquete */
    private float anchura;
    /** Altura del paquete */
    private float altura;
    /** Profundidad del paquete */
    private float profundidad;
    
    public Paquete(float peso, float anchura, float altura, float profundidad){
        this.peso = peso;
        this.anchura = anchura;
        this.altura = altura;
        this.profundidad = profundidad;
    }

    /**
     * @return el peso
     */
    public float getPeso() {
        return peso;
    }

    /**
     * @param peso el peso a insertar
     */
    public void setPeso(float peso) {
        this.peso = peso;
    }

    /**
     * @return la anchura
     */
    public float getAnchura() {
        return anchura;
    }

    /**
     * @param anchura la anchura a insertar
     */
    public void setAnchura(float anchura) {
        this.anchura = anchura;
    }

    /**
     * @return la altura
     */
    public float getAltura() {
        return altura;
    }

    /**
     * @param altura la altura a insertar
     */
    public void setAltura(float altura) {
        this.altura = altura;
    }

    /**
     * @return la profundidad
     */
    public float getProfundidad() {
        return profundidad;
    }

    /**
     * @param profundidad la profundidad a insertar
     */
    public void setProfundidad(float profundidad) {
        this.profundidad = profundidad;
    }
    
}
