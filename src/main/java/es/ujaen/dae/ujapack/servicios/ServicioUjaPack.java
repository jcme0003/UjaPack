/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.servicios;

import es.ujaen.dae.ujapack.entidades.puntocontrol.CentroLogistico;
import es.ujaen.dae.ujapack.entidades.Cliente;
import es.ujaen.dae.ujapack.entidades.Envio;
import es.ujaen.dae.ujapack.entidades.Envio.Estado;
import es.ujaen.dae.ujapack.entidades.PasoPuntoControl;
import es.ujaen.dae.ujapack.entidades.puntocontrol.Oficina;
import es.ujaen.dae.ujapack.excepciones.ClienteYaRegistrado;
import es.ujaen.dae.ujapack.excepciones.EnvioNoEncontrado;
import es.ujaen.dae.ujapack.excepciones.ProvinciaDestinatarioNoValida;
import es.ujaen.dae.ujapack.excepciones.ProvinciaRemitenteNoValida;
import es.ujaen.dae.ujapack.objetosvalor.Paquete;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * Clase con los servicios de la clase UjaPack
 * @author Jose Carlos Mena
 */
@Service
@Validated
public class ServicioUjaPack {
    /** Mapa con la lista de envios ordenada por codigo localizador */
    private Map<Integer, Envio> envios;
    /** Mapa con la lista de clientes ordenada por DNI */
    private Map<String, Cliente> clientes;
    /** Mapa con la lista de centros logisticos ordenada por codigo id del centro */
    private Map<Integer, CentroLogistico> centrosLogisticos;
    /** Servicio JSon para cargar los centros logisticos y sus conexiones */
    private ServicioJSon servicioJSon;
    
    /**
     * Constructor del servicio UjaPack
     */
    public ServicioUjaPack(){
        this.envios = new TreeMap<>();
        this.clientes = new TreeMap<>();
        this.centrosLogisticos = new TreeMap<>();
        this.servicioJSon = new ServicioJSon();
    }

    /**
     * @return the envios
     */
    public Map<Integer, Envio> getEnvios() {
        return envios;
    }

    /**
     * @return the centrosLogisticos
     */
    public Map<Integer, CentroLogistico> getCentrosLogisticos() {
        return centrosLogisticos;
    }

    /**
     * @param centrosLogisticos the centrosLogisticos to set
     */
    public void setCentrosLogisticos(Map<Integer, CentroLogistico> centrosLogisticos) {
        this.centrosLogisticos = centrosLogisticos;
    }
    
    /**
     * Carga datos del fichero json haciendo uso del servicio ServicioJSon
     * @param url Ruta al fichero json a cargar
     */
    public void cargaDatosJSon(String url){
        servicioJSon.cargaJSon(url);
        servicioJSon.cargaConexiones(url);
        this.centrosLogisticos = servicioJSon.getCentrosLogisticos();
    }
    
    /**
     * Crear un nuevo cliente en el sistema
     * @param cliente Cliente a añadir al sistema
     */
    public void altaCliente(Cliente cliente){
        if (clientes.containsKey(cliente.getDni())) {
            throw new ClienteYaRegistrado();
        }
        
        clientes.put(cliente.getDni(), cliente);
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
        
        getEnvios().put(localizador, envio);
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
        } while(getEnvios().containsKey(localizador));
        
//        System.out.println(localizador);
        return localizador;
    }
    
    /**
     * Calcula la ruta que debera seguir nuestro paquete
     * @param pRemitente provincia del remitente
     * @param pDestinatario provincia del destinatario
     * @return ruta que debe seguir el paquete para llegar a su destino
     */
    public List<PasoPuntoControl> calculaRuta(String pRemitente, String pDestinatario){
        // Comprueba si las provincias introducidas son validas
        provinciasValidas(pRemitente, pDestinatario);
        /** Lista de pasos por punto de control que indicara la ruta de nuestro envio */
        List<PasoPuntoControl> ruta = new ArrayList<>();
        
        // Tipo de envio 1
        if(pRemitente.equals(pDestinatario)){
//            System.out.println("Envio tipo 1");
            PasoPuntoControl ppc = new PasoPuntoControl(buscaProvincia(pRemitente));
            ruta.add(ppc);
        }
        
        // Tipo de envio 2
        if(!pRemitente.equals(pDestinatario) && mismoCentroLogistico(pRemitente, pDestinatario)){
//            System.out.println("Envio tipo 2");
            PasoPuntoControl paso = new PasoPuntoControl(buscaProvincia(pRemitente));
            ruta.add(paso);
            
            PasoPuntoControl pasoCl = new PasoPuntoControl(buscaCentroLogistico(pRemitente));
            ruta.add(pasoCl);
            
            PasoPuntoControl pasoDestino = new PasoPuntoControl(buscaProvincia(pDestinatario));
            ruta.add(pasoDestino);
            
        }
        
        // Tipo de envio 3
//        if(!pRemitente.equals(pDestinatario) && !mismoCentroLogistico(pRemitente, pDestinatario)){
            
//        }
        
        return ruta;
    }
    
    /**
     * Comprueba si dos provincias pertenecen al mismo centro logistico
     * @param pRemitente provincia del remitente
     * @param pDestinatario provincia del destinatario
     * @return true en caso de que pertenezcan al mismo centro logistico y false en caso contrario
     */
    private boolean mismoCentroLogistico(String pRemitente, String pDestinatario){
        return (buscaCentroLogistico(pRemitente).equals(buscaCentroLogistico(pDestinatario)));
    }
    
    /**
     * Busca el identificador del centro logistico de una provincia.
     * Si no la encuentra devuelve -1
     * @param provincia Provincia de la que buscaremos su centro logistico
     * @return id del centro logistico al que pertenece la provincia
     */
    private CentroLogistico buscaCentroLogistico(String provincia){
        for(CentroLogistico centro: getCentrosLogisticos().values()){
            Oficina oficina = centro.buscarOficinaDependiente(provincia);
            if(oficina != null){
                return centro;
            }
        }
        return null;
    }
    
    /**
     * Buscar Provincia
     * @param provincia provincia a buscar
     * @return el objeto provincia encontrado
     */
    private Oficina buscaProvincia(String provincia){
        for (CentroLogistico centro: getCentrosLogisticos().values()) {
            return centro.buscarOficinaDependiente(provincia);
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
     * Obtener la situacion y estado actual del envio
     * @param localizador numero identificador del paquete
     * @return estado actual del envio
     */
    public Estado consultarEstadoEnvio(int localizador){
        if(!envios.containsKey(localizador)){
            throw new EnvioNoEncontrado();
        }
        
        return this.getEnvios().get(localizador).getEstado();
    }
    
    public List<PasoPuntoControl> listarPuntosDeControlEnvio(int localizador){
        return envios.get(localizador).getRuta();
    }
    
    /**
     * Lista los centros logisticos de nuestra aplicacion para comprobar que el json ha sido cargado correctamente
     */
    public void listarCentrosLogisticos(){
        for(CentroLogistico cl : this.getCentrosLogisticos().values()){
            System.out.print(cl.getIdCentro() + " " + cl.getNombre() + " ");
            for(CentroLogistico conexion : cl.getConexiones()){
                System.out.print(conexion.getIdCentro() + ", ");
            }
            System.out.println("\n----------------------------");
            for(Oficina oficina : cl.getOficinas()){
                System.out.println(oficina.getNombreProvincia());
            }
            System.out.println("----------------------------");
        }
    }
    
}
