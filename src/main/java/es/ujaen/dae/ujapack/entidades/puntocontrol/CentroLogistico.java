/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades.puntocontrol;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 * Centro logistico gestionado por ujapack
 * @author Jose Carlos Mena
 */
@Entity
public class CentroLogistico extends PuntoControl {
    
    /** Nombre del centro logistico */
    private String nombre;
    
    /** Ciudad donde se encuentra localizado el centro logistico */
    private String localizacion;
    
    /** Oficinas asociadas al centro logistico */
    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name = "oficinaNombreProvincia")
    private List<Oficina> oficinas;
    
    /** Centros logisticos con los que esta conectado este centro logistico */
    @ManyToMany
    private List<CentroLogistico> conexiones;

    public CentroLogistico() {
    }
   
    /**
     * Constructor CentroLogistico
     * @param idCentro identificador del centro logistico
     * @param nombre
     * @param localizacion
     * @param oficinas
     * @param conexiones
     */
    public CentroLogistico(int idCentro, String nombre, String localizacion, List<Oficina> oficinas, List<CentroLogistico> conexiones){
        super(idCentro, localizacion);
        this.nombre = nombre;
        this.localizacion = localizacion;
        this.oficinas = oficinas;
        this.conexiones = conexiones;
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
    public List<CentroLogistico> getConexiones() {
        return conexiones;
    }

    /**
     * Agrega lista de conexiones
     * @param conexiones las conexiones a insertar
     */
    public void setConexiones(List<CentroLogistico> conexiones) {
        this.conexiones = conexiones;
    }
    
    /**
     * Agrega una nueva conexion
     * @param conexion conexion a añadir
     */
    public void setConexion(CentroLogistico conexion){
        this.conexiones.add(conexion);
    }
   
}
