/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.servicios;

import es.ujaen.dae.ujapack.entidades.puntocontrol.CentroLogistico;
import es.ujaen.dae.ujapack.entidades.Cliente;
import es.ujaen.dae.ujapack.entidades.Envio;
import es.ujaen.dae.ujapack.entidades.PasoPuntoControl;
import es.ujaen.dae.ujapack.entidades.puntocontrol.Oficina;
import es.ujaen.dae.ujapack.entidades.puntocontrol.PuntoControl;
import es.ujaen.dae.ujapack.excepciones.ProvinciaDestinatarioNoValida;
import es.ujaen.dae.ujapack.excepciones.ProvinciaRemitenteNoValida;
import es.ujaen.dae.ujapack.objetosvalor.Paquete;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
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
    
    public Map<Integer, Envio> getEnvios(){
        return this.envios;
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
                List<CentroLogistico> conexiones = new ArrayList<>();
                // Cargamos las conexiones correspondientes al identificador actual i
                arr = elem.getJSONArray("conexiones");
                for(int j = 0; j < arr.length(); j++){
                    conexion = arr.getInt(j);
                    if(!conexiones.contains(conexion)){
                        conexiones.add(centrosLogisticos.get(j));
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
        
        Map.Entry e;
        CentroLogistico cl;
        List<Oficina> of;
        
        while(it.hasNext()){
            e = (Map.Entry<String, CentroLogistico>)it.next();
            cl = centrosLogisticos.get(e.getKey());
            System.out.println(e.getKey() + " " + cl.getNombre() + " " + cl.getConexiones());
            System.out.println("Oficinas:");
            System.out.println("----------------------------:");
            of = cl.getOficinas();
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
        
        Envio envio = new Envio(localizador, remitente, destinatario, paquetes);
        envio.setRuta(calculaRuta(remitente.getProvincia(), destinatario.getProvincia()));
        envio.calculaImporte();
        
        envios.put(localizador, envio);
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
    
    /**
     * Calcula la ruta que debera seguir nuestro paquete
     * @param pRemitente provincia del remitente
     * @param pDestinatario provincia del destinatario
     * @return ruta que debe seguir el paquete para llegar a su destino
     */
    private List<PasoPuntoControl> calculaRuta(String pRemitente, String pDestinatario){
        // Comprueba si las provincias introducidas son validas
        provinciasValidas(pRemitente, pDestinatario);
        
        List<PasoPuntoControl> ruta = new ArrayList<>();
        
        // Tipo de envio 1
        if(pRemitente.equals(pDestinatario)){
            System.out.println("Envio tipo 1");
            
            PasoPuntoControl ppc = new PasoPuntoControl(buscaProvincia(pRemitente));
            ruta.add(ppc);
        }
        
        // Tipo de envio 2
        if(!pRemitente.equals(pDestinatario) && mismoCentroLogistico(pRemitente, pDestinatario)){
            System.out.println("Envio tipo 2");
            PasoPuntoControl paso = new PasoPuntoControl(buscaProvincia(pRemitente));
            ruta.add(paso);
            
            PasoPuntoControl pasoCl = new PasoPuntoControl(buscaCentroLogistico(pRemitente));
            ruta.add(pasoCl);
            
            PasoPuntoControl pasoDestino = new PasoPuntoControl(buscaProvincia(pDestinatario));
            ruta.add(pasoDestino);
            
        }
        
        // Tipo de envio 3
        if(!pRemitente.equals(pDestinatario) && !mismoCentroLogistico(pRemitente, pDestinatario)){
            System.out.println("Envio tipo 3");
            PasoPuntoControl paso = new PasoPuntoControl(buscaProvincia(pRemitente));
            ruta.add(paso);
            
            PasoPuntoControl pasoClR = new PasoPuntoControl(buscaCentroLogistico(pRemitente));
            ruta.add(pasoClR);
            
            PasoPuntoControl pasoClD = new PasoPuntoControl(buscaCentroLogistico(pRemitente));
            ruta.add(pasoClD);
            
            PasoPuntoControl pasoDestino = new PasoPuntoControl(buscaProvincia(pDestinatario));
            ruta.add(pasoDestino);
        }
        
        return ruta;
    }
    
    /**
     * Comprueba si dos provincias pertenecen al mismo centro logistico
     * @param pRemitente provincia del remitente
     * @param pDestinatario provincia del destinatario
     * @return true en caso de que pertenezcan al mismo centro logistico y false en caso contrario
     */
    private boolean mismoCentroLogistico(String pRemitente, String pDestinatario){
        return (buscaCentroLogistico(pRemitente).getIdCentro() == buscaCentroLogistico(pDestinatario).getIdCentro());
    }
    
    /**
     * Busca el identificador del centro logistico de una provincia.
     * Si no la encuentra devuelve -1
     * @param provincia Provincia de la que buscaremos su centro logistico
     * @return id del centro logistico al que pertenece la provincia
     */
    private CentroLogistico buscaCentroLogistico(String provincia){
        Iterator it = centrosLogisticos.entrySet().iterator();
        
        Map.Entry e;
        CentroLogistico cl;
        List<Oficina> of;
        
        while(it.hasNext()){
            e = (Map.Entry<String, CentroLogistico>)it.next();
            cl = centrosLogisticos.get(e.getKey());
            of = cl.getOficinas();
            for(int i = 0; i < of.size(); i++){
                if(provincia.equals(of.get(i).getNombreProvincia())){
                    System.out.println("Centro logistico encontrado");
                    System.out.println("Provincia: " + provincia);
                    System.out.println("ID Centro logistico: " + cl.getIdCentro());
                    return centrosLogisticos.get(e.getKey());
                }
            }
        }
        
        return null;
    }
    
    /**
     * Comprueba si las provincias del envio son validas y en caso contrario genera excepcion
     * @param pRemitente provincia del remitente
     * @param pDestinatario provincia del destinatario
     */
    private void provinciasValidas(String pRemitente, String pDestinatario){
        if(buscaCentroLogistico(pRemitente) == null){
            throw new ProvinciaRemitenteNoValida();
        }
        
        if(buscaCentroLogistico(pDestinatario) == null){
            throw new ProvinciaDestinatarioNoValida();
        }
    }
    
    /**
     * Buscar Provincia
     * @param provincia provincia a buscar
     * @return el objeto provincia encontrado
     */
    private Oficina buscaProvincia(String provincia){
        Iterator it = centrosLogisticos.entrySet().iterator();
        
        Map.Entry e;
        List<Oficina> of;
        
        while(it.hasNext()){
            e = (Map.Entry<String, CentroLogistico>)it.next();
            of = centrosLogisticos.get(e.getKey()).getOficinas();
            for(int i = 0; i < of.size(); i++){
                if(provincia.equals(of.get(i).getNombreProvincia())){
                    return of.get(i);
                }
            }
        }
        
        return null;
    }
    
    /***
     * Busqueda en profundidad para calcular ruta
     * 
     */
//     public List<Oficina> DPSRuta(Oficina pRemitente, Oficina pDestinatario){
//        ArrayList<Oficina> visitadas = new ArrayList<>();

//        Stack<Oficina> control = new Stack<>();
//
//        //Inicia en el origen
//        Oficina actual = pRemitente;
//
//        do {
//            visitadas.add(actual);                                      
//            control.add(actual);                                        
//
//            Optional<Oficina> siguiente = adyacentes(actual).stream()   
//                    .filter((Oficina of) -> !visitadas.contains(of))      
//                    .findFirst();                                       
//
//            if(siguiente.isPresent()){                                  
//                actual = siguiente.get();                               
//            }else {                                                     
//                control.pop();                                          
//                if(control.empty()) return null;                        
//                actual = control.pop();                                 
//            }
//
//            if(actual.equals(pDestinatario)){                           
//                control.add(actual);                                   
//                return new ArrayList<>(control);                       
//            }
//
//        }while (!actual.equals(pDestinatario));
//
//        return null;
//    }
    
    
    /**
     * Calcular ruta (CentrosLogisticos) de la regiones intermedias hasta destino
     * @param pRemitente provincia del remitente
     * @param pDestinatario provincia del destinatario
     * @return 
     */
    private void calculaRutaCL (String pRemitente, String pDestinatario){
        
    }
    
    
    /**
     * Listado de los Puntos de Control por los que pasa un envio
     * @param localizador número entero aleatorio de 10 cifras
     * @return listado
     */
    private PuntoControl listaPuntosControl(int localizador){
        return null;
    
    }
    
    
}
