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
import es.ujaen.dae.ujapack.util.profundidad.Nodo;
import es.ujaen.dae.ujapack.util.profundidad.Profundidad;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import static java.lang.Integer.parseInt;
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
    private Map<Integer, CentroLogistico> centrosLogisticos;
    /**  */
    Profundidad conexionesNodos;
    
    /**
     * Constructor del servicio UjaPack
     */
    public ServicioUjaPack(){
        this.envios = new TreeMap<>();
        this.centrosLogisticos = new TreeMap<>();
        this.conexionesNodos = new Profundidad();
    }
    
    /**
     * Devuelve los envios del sistema
     * @return Envios realizados por el sistema
     */
    public Map<Integer, Envio> getEnvios(){
        return this.envios;
    }

    /**
     * @param centrosLogisticos the centrosLogisticos to set
     */
    public void setCentrosLogisticos(Map<Integer, CentroLogistico> centrosLogisticos) {
        this.centrosLogisticos = centrosLogisticos;
        this.conexionesNodos.generaNodos(centrosLogisticos);
    }
    
    /**
     * Comprobar que el proyecto ha sido configurado correctamente
     */
    public void helloUja(){
        System.out.println("Hello Uja");
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
            System.out.println(e.getKey() + " " + cl.getNombre() + " ");
            
            System.out.println("Oficinas:");
            for (CentroLogistico c : cl.getConexiones()) {
                System.out.print(c.getIdCentro() + " ");
            }
            System.out.println("\n----------------------------:");
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
        /** Lista de pasos por punto de control que indicara la ruta de nuestro envio */
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
            
//            PasoPuntoControl pasoClR = new PasoPuntoControl(buscaCentroLogistico(pRemitente));
//            ruta.add(pasoClR);
            
//            Nodo nodoInicial = new Nodo(buscaCentroLogistico(pRemitente));
//            Nodo nodoFinal = new Nodo(buscaCentroLogistico(pDestinatario));
//            Profundidad busquedaProfundidad = new Profundidad();
//            for(Nodo nodo : this.conexionesNodos.profundidad(buscaCentroLogistico(pRemitente), buscaCentroLogistico(pDestinatario))){
//                ruta.add(new PasoPuntoControl(nodo.getCentroLogistico()));
//            }

//            for(Nodo nodo : this.conexionesNodos.profundidad(buscaCentroLogistico(pRemitente), buscaCentroLogistico(pDestinatario))){
//                ruta.add(new PasoPuntoControl(nodo.getCentroLogistico()));
//            }
            
//            PasoPuntoControl pasoClD = new PasoPuntoControl(buscaCentroLogistico(pRemitente));
//            ruta.add(pasoClD);
            
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
        for(CentroLogistico centro: centrosLogisticos.values()){
            Oficina oficina = centro.buscarOficinaDependiente(provincia);
            if(oficina != null){
                return centro;
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
        for (CentroLogistico centro: centrosLogisticos.values()) {
            Oficina oficina = centro.buscarOficinaDependiente(provincia);
            if (oficina != null) {
                return oficina;
            }
        }
        
        return null;
    }
    
    /***
     * Busqueda en profundidad para calcular ruta
     * 
     */
//    public void DPSRuta (String pRemitente, String pDestino){
//        int contador = 0;
//        for(int i = 0; i < this.centrosLogisticos.size(); i++){
//            for(int j = 0; j < centrosLogisticos.get(visitadas.get(i)).getProvincia().size(); j++){
//                 if (pDestino == centrosLogisticos.get(visitadas.get(i)).getProvincias().get(j)) {
//                    ruta.add(centrosLogisticos.get(visitadas.get(i)).getLocalizacion());
//                    ruta.add(pDestino);
//                }
//            }
//            ruta.add(centrosLogisticos.get(visitadas.get(i)).getLocalizacion());
//            DPSRuta(centrosLogisticos.get(visitadas.get(i)).getConexiones());
//        }
//    }
    
    
    /**
     * Calcular ruta (CentrosLogisticos) de la regiones intermedias hasta destino
     * @param pRemitente provincia del remitente
     * @param pDestinatario provincia del destinatario
     * @return 
     */
    private void calculaRutaCL (String pRemitente, String pDestinatario){
        ArrayList<Oficina> conexiones = new ArrayList<>();
        
    }
    
    
    /**
     * Listado de los Puntos de Control por los que pasa un envio
     * @param localizador número entero aleatorio de 10 cifras
     * @return listado
     */
    private List<PuntoControl> listaPuntosControl(int localizador){
        
        return null;
    
    }
    
    
}
