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
import es.ujaen.dae.ujapack.excepciones.CentroLogisticoNoValido;
import es.ujaen.dae.ujapack.excepciones.ClienteYaRegistrado;
import es.ujaen.dae.ujapack.excepciones.EnvioNoEncontrado;
import es.ujaen.dae.ujapack.excepciones.ProvinciaNoValida;
import es.ujaen.dae.ujapack.objetosvalor.Paquete;
import es.ujaen.dae.ujapack.repositorios.RepositorioCentrosLogisticos;
import es.ujaen.dae.ujapack.repositorios.RepositorioEnvios;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    
    @Autowired
    RepositorioEnvios repositorioEnvios;
    
    @Autowired
    RepositorioCentrosLogisticos repositorioCentrosLogisticos;
    
    /**
     * Constructor del servicio UjaPack
     */
    public ServicioUjaPack(){
        this.envios = new TreeMap<>();
        this.clientes = new TreeMap<>();
        this.centrosLogisticos = new TreeMap<>();
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
     * @return 
     */
    @PostConstruct
    public Map<Integer, CentroLogistico> cargaDatosJSon(){
        String url = "redujapack.json";
        ServicioJSon servicioJSon = new ServicioJSon();
        servicioJSon.cargaJSon(url);
        insertaOficinasBD(servicioJSon.getOficinas());
//        eliminar
        this.centrosLogisticos = servicioJSon.getCentrosLogisticos();
        insertaCentrosBD(servicioJSon.getCentrosLogisticos());
        servicioJSon.cargaConexiones(url);
//        eliminar
        this.centrosLogisticos = servicioJSon.getCentrosLogisticos();
        actualizarCentrosBD(servicioJSon.getCentrosLogisticos());
        return servicioJSon.getCentrosLogisticos();
    }
    
    /**
     * Añadimos oficinas a la BD
     * @param oficinas a insertar en la BD cargadas del json
     */
    @Transactional
    private void insertaOficinasBD(List<Oficina> oficinas){
        for(Oficina of : oficinas){
            repositorioCentrosLogisticos.guardarOf(of);
        }
    }
    
    /**
     * Añadimos centros logisticos a la base de datos (sin conexiones)
     * @param centrosLogisticos centros logisticos cargados del json
     */
    @Transactional
    public void insertaCentrosBD(Map<Integer, CentroLogistico> centrosLogisticos){
        for(Map.Entry<Integer, CentroLogistico> cl : centrosLogisticos.entrySet()){
            repositorioCentrosLogisticos.guardarCL(cl.getValue());
        }
    }
    
    /**
     * Añadimos centros logisticos a la base de datos
     * @param centrosLogisticos centros logisticos cargados del json
     */
    @Transactional
    public void actualizarCentrosBD(Map<Integer, CentroLogistico> centrosLogisticos){
        for(Map.Entry<Integer, CentroLogistico> cl : centrosLogisticos.entrySet()){
            repositorioCentrosLogisticos.actualizarCL(cl.getValue());
        }
    }
    
    /**
     * Crear un nuevo cliente en el sistema
     * @param cliente Cliente a añadir al sistema
     */
    public void altaCliente(Cliente cliente){
//        if(repositorioEnvios.buscarCliente(cliente.getDni()).isPresent()){
//            throw new ClienteYaRegistrado();
//        }
//        repositorioEnvios.guardarCliente(cliente);
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
     * @return envio creado
     */
    public Envio nuevoEnvio(List<Paquete> paquetes, Cliente remitente, Cliente destinatario){
        int localizador = generaLocalizador();
        
        Envio envio = new Envio(localizador, remitente, destinatario, paquetes);
        envio.setRuta(calculaRuta(remitente.getProvincia(), destinatario.getProvincia()));
        envio.calculaImporte();
        
        getEnvios().put(localizador, envio);
        
        return envio;
    }
    
    /**
     * Generar localizador de 10 dígitos aleatorio y no usado previamente
     * @return codigo localizador (unico) generado para el envio
     */
    private int generaLocalizador(){
        int localizador;
        Long min = 1000000000L;
        Long max = 9999999999L;
        do {
            localizador = (int)Math.floor(Math.random()*(max-min+1)+min);
        } while(getEnvios().containsKey(localizador));
        
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
        if(!provinciasValidas(pRemitente, pDestinatario)){
            throw new ProvinciaNoValida();
        }
        /** Lista de pasos por punto de control que indicara la ruta de nuestro envio */
        List<PasoPuntoControl> ruta = new ArrayList<>();
        
        // Tipo de envio 1
        if(pRemitente.equals(pDestinatario)){
            PasoPuntoControl ppc = new PasoPuntoControl(buscaProvincia(pRemitente));
            ruta.add(ppc);
        }
        
        // Tipo de envio 2
        if(!pRemitente.equals(pDestinatario) && mismoCentroLogistico(pRemitente, pDestinatario)){
            
            PasoPuntoControl paso = new PasoPuntoControl(buscaProvincia(pRemitente));
            ruta.add(paso);
            
            PasoPuntoControl pasoCl = new PasoPuntoControl(buscaCentroLogistico(pRemitente));
            ruta.add(pasoCl);
            
            PasoPuntoControl pasoDestino = new PasoPuntoControl(buscaProvincia(pDestinatario));
            ruta.add(pasoDestino);
            
        }
        
        // Tipo de envio 3
        if(!pRemitente.equals(pDestinatario) && !mismoCentroLogistico(pRemitente, pDestinatario)){
            calculaRutaTipo3(buscaCentroLogistico(pRemitente), buscaCentroLogistico(pDestinatario));
        }
        
        return ruta;
    }
    
    private void calculaRutaTipo3(CentroLogistico remitente, CentroLogistico destinatario){
        
        
        
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
//        for(CentroLogistico centro: getCentrosLogisticos().values()){
//            Oficina oficina = centro.buscarOficinaDependiente(provincia);
//            if(oficina != null){
//                return centro;
//            }
//        }
        
        CentroLogistico centroLogistico = repositorioCentrosLogisticos.buscarCL(buscaProvincia(provincia).getIdCentro())
                .orElseThrow(CentroLogisticoNoValido::new);
        
        
        return centroLogistico;
    }
    
    /**
     * Buscar Provincia
     * @param provincia provincia a buscar
     * @return el objeto provincia encontrado
     */
    private Oficina buscaProvincia(String provincia){
//        for (CentroLogistico centro: getCentrosLogisticos().values()) {
//            return centro.buscarOficinaDependiente(provincia);
//        }

        Oficina oficina = repositorioCentrosLogisticos.buscarOf(provincia)
                .orElseThrow(ProvinciaNoValida::new);
        
        return oficina;
    }
    
    /**
     * Comprueba si las provincias del envio son validas y en caso contrario genera excepcion
     * @param pRemitente provincia del remitente
     * @param pDestinatario provincia del destinatario
     */
    private boolean provinciasValidas(String pRemitente, String pDestinatario){
        return buscaCentroLogistico(pRemitente) != null && buscaCentroLogistico(pDestinatario) != null;
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
        if(!envios.containsKey(localizador)){
            throw new EnvioNoEncontrado();
        }
        
        return envios.get(localizador).getRuta();
    }
    
}
