/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.servicios;

import es.ujaen.dae.ujapack.entidades.CentroLogistico;
import es.ujaen.dae.ujapack.entidades.Envio;
import es.ujaen.dae.ujapack.excepciones.LocalizadorYaRegistrado;
import java.util.Map;
import java.util.TreeMap;
import java.util.Random;
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
     */
    public void nuevoEnvio(){
        // Generar localizador de 10 dígitos aleatorio y no usado previamente
        int localizador;
        do {
            localizador = (int)((new Random().nextLong() % 9000000000L) + 1000000000L);
        } while(envios.containsKey(localizador));
        
        System.out.println(localizador);
        
        /*if(envios.containsKey(localizador)){
            throw new LocalizadorYaRegistrado();
        }*/
    }
}
