/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.servicios;

import es.ujaen.dae.ujapack.entidades.puntocontrol.CentroLogistico;
import es.ujaen.dae.ujapack.entidades.puntocontrol.Oficina;
import es.ujaen.dae.ujapack.repositorios.RepositorioEnvios;
import es.ujaen.dae.ujapack.repositorios.RepositorioPuntosControl;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ana
 */
@Service
public class ServicioCargaDatosRedLogistica {
    
    @Autowired
    RepositorioEnvios repositorioEnvios;
    
    @Autowired
    RepositorioPuntosControl repositorioPuntoControl;
    
    /**
     * Constructor del servicio CargaDatosRedLogistica
     */
    public ServicioCargaDatosRedLogistica(){
    }
    
    
    /**
    * Carga datos del fichero json haciendo uso del servicio ServicioJSon
    * @return Mapa de centros logisticos cargados
    */
    @PostConstruct
    public Map<Integer, CentroLogistico> cargaDatosJSon(){
        String url = "redujapack.json";
        ServicioJSon servicioJSon = new ServicioJSon();
        servicioJSon.cargaJSon(url);
        insertaOficinasBD(servicioJSon.getOficinas());
        insertaCentrosBD(servicioJSon.getCentrosLogisticos());
        servicioJSon.cargaConexiones(url);
        actualizarCentrosBD(servicioJSon.getCentrosLogisticos());
        return servicioJSon.getCentrosLogisticos();
    }
    
    /**
     * Añadimos oficinas a la BD
     * @param oficinas a insertar en la BD cargadas del json
     */
    private void insertaOficinasBD(List<Oficina> oficinas){
        for(Oficina of : oficinas){
            repositorioPuntoControl.guardarOf(of);
        }
    }
    
    /**
     * Añadimos centros logisticos a la base de datos (sin conexiones)
     * @param centrosLogisticos centros logisticos cargados del json
     */
    private void insertaCentrosBD(Map<Integer, CentroLogistico> centrosLogisticos){
        for(Map.Entry<Integer, CentroLogistico> cl : centrosLogisticos.entrySet()){
            repositorioPuntoControl.guardarCL(cl.getValue());
        }
    }
    
    /**
     * Añadimos centros logisticos a la base de datos
     * @param centrosLogisticos centros logisticos cargados del json
     */
    private void actualizarCentrosBD(Map<Integer, CentroLogistico> centrosLogisticos){
        for(Map.Entry<Integer, CentroLogistico> cl : centrosLogisticos.entrySet()){
            repositorioPuntoControl.actualizarCL(cl.getValue());
        }
    }  
}
