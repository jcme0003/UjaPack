/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.servicios;

import es.ujaen.dae.ujapack.entidades.puntocontrol.CentroLogistico;
import es.ujaen.dae.ujapack.entidades.Cliente;
import es.ujaen.dae.ujapack.entidades.Envio;
import es.ujaen.dae.ujapack.entidades.puntocontrol.Oficina;
import es.ujaen.dae.ujapack.objetosvalor.Paquete;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
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
    
    /**
     * Constructor del servicio UjaPack
     */
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
     * Carga archivo JSon que contiene los centros logisticos, oficinas y conexiones
     * @param url url donde esta guardado el fichero JSon
     */
    public void cargaJSon(String url){
        try{
            // Carga y almacena en un string el archivo json
            File file = new File(url);
            String data;
            try (Scanner sc = new Scanner(new FileInputStream(file), "UTF-8")) {
                data = new String();
                while(sc.hasNextLine()){
                    data += sc.nextLine();
                }
            }
//            System.out.println(data);
            
            JSONObject obj = new JSONObject(data);
            JSONObject elem;
            JSONArray arr;
            
            String id;
            String nombre;
            String localizacion;
            String provincia;
            int conexion;
            List<String> provincias = new ArrayList<>();
            
            // Cargamos datos de cada identificador del Json "1", "2", ... "10"
            for(int i = 1; i <= 10; i++){
                id = Integer.toString(i);
                elem = obj.getJSONObject(id);
                
                nombre = elem.getString("nombre");
//                System.out.println(nombre);
                
                localizacion = elem.getString("localización");
//                System.out.println(localizacion);
                
                // Oficinas correspondientes a este centro logistico
                List<Oficina> oficinas = new ArrayList<>();
                // Cargamos las provincias correspondientes al identificador actual i
                arr = elem.getJSONArray("provincias");
                for(int j = 0; j < arr.length(); j++){
                    provincia = arr.getString(j);
//                    System.out.println(arr.getString(j));
                    // Añadir a array de provincias y crear Oficina (provincia) si no esta creada
                    if(!provincias.contains(arr.getString(j))){
                        provincias.add(provincia);
                        oficinas.add(new Oficina(provincia));
                    }
                }
                
                // Conexiones correspondientes a este centro logistico
                List<Integer> conexiones = new ArrayList<>();
                // Cargamos las conexiones correspondientes al identificador actual i
                arr = elem.getJSONArray("conexiones");
                for(int j = 0; j < arr.length(); j++){
                    conexion = arr.getInt(j);
                    if(!conexiones.contains(conexion)){
                        conexiones.add(arr.getInt(j));
                    }
//                    System.out.println(arr.getInt(j));
                }
                
                // Creamos nuestro centro logistico y lo añadir a la lista de centros logisticos del sistema
                CentroLogistico centroLogistico = new CentroLogistico(Integer.parseInt(id), nombre, localizacion, oficinas, conexiones);
                centrosLogisticos.put(id, centroLogistico);
            }
           
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        
    }
    
    /**
     * Lista los centros logisticos de nuestra aplicacion
     */
    public void listarCentrosLogisticos(){
        Iterator it = centrosLogisticos.entrySet().iterator();
        
        while(it.hasNext()){
            Map.Entry e = (Map.Entry<String, CentroLogistico>)it.next();
            CentroLogistico cl = centrosLogisticos.get(e.getKey());
            System.out.println(e.getKey() + " " + cl.getNombre() + " " + cl.getConexiones());
            System.out.println("Oficinas:");
            System.out.println("----------------------------:");
            List<Oficina> of = cl.getOficinas();
            for(int i = 0; i < of.size(); i++){
                System.out.println(of.get(i).getNombreProvincia());
            }
            System.out.println("----------------------------:");
        }
    }
    
    /**
     * Crear un nuevo envio en el sistema y calcular su ruta
     * @param paquetes paquetes que componen el envio
     * @param remitente cliente que realiza el envio
     * @param destinatario cliente que recibira el/los paquete/s
     */
    public void nuevoEnvio(List<Paquete> paquetes, Cliente remitente, Cliente destinatario){
        int localizador = generaLocalizador();
        
        envios.put(localizador, new Envio(localizador, remitente, destinatario, paquetes));
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
