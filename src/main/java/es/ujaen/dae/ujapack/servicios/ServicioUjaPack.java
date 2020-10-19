/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.servicios;

import es.ujaen.dae.ujapack.entidades.CentroLogistico;
import es.ujaen.dae.ujapack.entidades.Cliente;
import es.ujaen.dae.ujapack.entidades.Envio;
import es.ujaen.dae.ujapack.excepciones.LocalizadorYaRegistrado;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.stereotype.Service;

/**
 * Clase con los servicios de la clase UjaPack
 * @author Jose Carlos Mena
 */
@Service
public class ServicioUjaPack {
    /** Mapa con la lista de envios ordenada por codigo localizador */
    Map<Integer, Envio> envios;
    /** Mapa con la lista de centros logisticos ordenada por codigo id del centro */
    Map<String, CentroLogistico> centrosLogisticos;
    
    public ServicioUjaPack(){
        envios = new TreeMap<>();
        centrosLogisticos = new TreeMap<>();
    }
    
    /**
     * Comprobar que el proyecto ha sido configurado correctamente
     */
    public void helloUja(){
        System.out.println("Hello Uja");
    }
    
    /**
     * Crear un nuevo envio en el sistema y calcular su ruta
     * @param remitente cliente que realiza el envio
     * @param destinatario cliente que recibira el/los paquete/s
     */
    public void nuevoEnvio(Cliente remitente, Cliente destinatario){
        int localizador = generaLocalizador();
        
        envios.put(localizador, new Envio(localizador, remitente, destinatario));
    }
    
    /**
     * Generar localizador de 10 dígitos aleatorio y no usado previamente
     * @return codigo localizador (unico) generado para el envio
     */
    public int generaLocalizador(){
        int localizador;
        Long min = 1000000000L;
        Long max = 9999999999L;
        do {
            localizador = (int)Math.floor(Math.random()*(max-min+1)+min);
        } while(envios.containsKey(localizador));
        
        /*if(envios.containsKey(localizador)){
            throw new LocalizadorYaRegistrado();
        }*/
        
        System.out.println(localizador);
        return localizador;
    }
}
