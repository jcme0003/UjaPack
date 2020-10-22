/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades.puntocontrol;

import java.util.List;

/**
 * Centro logistico gestionado por ujapack
 * @author Jose Carlos Mena
 */
public class CentroLogistico extends PuntoControl {
    /** Identificador del centro logistico */
    private int idCentro;
    /** Nombre del centro logistico */
    private String nombre;
    /** Ciudad donde se encuentra localizado el centro logistico */
    private String localizacion;
    
    /** Oficinas asociadas al centro logistico */
    private List<Oficina> oficinas;
    /** Centros logisticos con los que esta conectado este centro logistico */
    private List<Integer> conexiones;
    
    /**
     * Constructor CentroLogistico
     * @param idCentro identificador del centro logistico
     * @param nombre
     * @param localizacion
     * @param oficinas
     * @param conexiones
     */
    public CentroLogistico(int idCentro, String nombre, String localizacion, List<Oficina> oficinas, List<Integer> conexiones){
//        super(idCentro, localizacion);
        super();
        this.idCentro = idCentro;
        this.nombre = nombre;
        this.localizacion = localizacion;
        this.oficinas = oficinas;
        this.conexiones = conexiones;
    }

    /**
     * @return el identificado del centro logistico
     */
    public int getIdCentro() {
        return idCentro;
    }

    /**
     * @param idCentro el identificado del centro logistico a insertar
     */
    public void setIdCentro(int idCentro) {
        this.idCentro = idCentro;
    }

    /**
     * @return el nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre el nombre a insertar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return la localizacion
     */
    public String getLocalizacion() {
        return localizacion;
    }

    /**
     * @param localizacion la localizacion a insertar
     */
    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    /**
     * @return las oficinas
     */
    public List<Oficina> getOficinas() {
        return oficinas;
    }

    /**
     * @param oficinas las oficinas a insertar
     */
    public void setOficinas(List<Oficina> oficinas) {
        this.oficinas = oficinas;
    }

    /**
     * @return las conexiones
     */
    public List<Integer> getConexiones() {
        return conexiones;
    }

    /**
     * @param conexiones las conexiones a insertar
     */
    public void setConexiones(List<Integer> conexiones) {
        this.conexiones = conexiones;
    }
    
    
}
