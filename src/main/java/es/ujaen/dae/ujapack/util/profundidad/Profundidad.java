/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.util.profundidad;

import es.ujaen.dae.ujapack.entidades.puntocontrol.CentroLogistico;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author Root
 */
public class Profundidad {
    /** Pila de nodos */
    private final Stack<Nodo> stack;
    /** Estructura interno de la conexion entre centros logisticos */
    private List<Nodo> nodos;

    /**
     * Constructor por defecto
     */
    public Profundidad() {
        this.stack = new Stack<>();
        this.nodos = new ArrayList<>();
    }

    /**
     * @return the nodos
     */
    public List<Nodo> getNodos() {
        return nodos;
    }
    
    public void setCentroLogistico(CentroLogistico cl){
        Nodo nodo = new Nodo(cl);
        getNodos().add(nodo);
    }
    
    /**
     * 
     * @param centrosLogisticos 
     */
    public void generaNodos(Map<Integer, CentroLogistico> centrosLogisticos){
        for(CentroLogistico cl : centrosLogisticos.values()){
            this.getNodos().add(new Nodo(cl));
        }
        generaAdyacentes(centrosLogisticos);
    }
    
    /**
     * 
     * @param centrosLogisticos
     */
    public void generaAdyacentes(Map<Integer, CentroLogistico> centrosLogisticos){
        List<Nodo> adyacentes = new ArrayList<>();
        for(CentroLogistico cl : centrosLogisticos.values()){
            for(CentroLogistico clAdy : cl.getConexiones()){
                adyacentes.add(buscaNodo(clAdy));
            }
            buscaNodo(cl).setAdyacentes(adyacentes);
        }
    }
    
    /**
     * 
     * @param cl
     * @return 
     */
    private Nodo buscaNodo(CentroLogistico cl){
        for(Nodo nodo : this.nodos){
            if(nodo.getCentroLogistico().getIdCentro() == cl.getIdCentro()){
                return nodo;
            }
        }
        
        return null;
    }

    /**
     * Algoritmo de busqueda en profundidad
     * @param pRemitente
     * @param pDestinatario
     * @return 
     */
    public List<Nodo> profundidad(CentroLogistico pRemitente, CentroLogistico pDestinatario) {
        Nodo nodoInicial = buscaNodo(pRemitente);
        Nodo nodoFinal = buscaNodo(pDestinatario);
        
        stack.add(nodoInicial);
        nodoInicial.setVisitado(true);

        while (!stack.isEmpty()) {
            Nodo nodoActual = stack.pop();
            System.out.println(nodoActual + " ");
            for (Nodo nodo : nodoActual.getAdyacentes()) {
                if (!nodo.isVisitado()) {
                    nodo.setVisitado(true);
                    //nodo.setPredecesor(nodoActual);
                    stack.add(nodo);
                }
                if(nodo.getCentroLogistico().getIdCentro() == nodoFinal.getCentroLogistico().getIdCentro()){
                    return new ArrayList<>(stack);
                }
            }
        }
        
        return null;
    }
    
}
