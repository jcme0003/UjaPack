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
import es.ujaen.dae.ujapack.excepciones.PasoPuntoControlNoEncontrado;
import es.ujaen.dae.ujapack.excepciones.ProvinciaNoValida;
import es.ujaen.dae.ujapack.objetosvalor.Paquete;
import es.ujaen.dae.ujapack.repositorios.RepositorioPuntosControl;
import es.ujaen.dae.ujapack.repositorios.RepositorioEnvios;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * Clase con los servicios de la clase UjaPack
 * @author Jose Carlos Mena
 */
@Service
@Validated
public class ServicioUjaPack {
    public enum TipoNotificacion{
        SALIDA,
        LLEGADA;
    }
    
    @Autowired
    RepositorioEnvios repositorioEnvios;
    
    @Autowired
    RepositorioPuntosControl repositorioCentrosLogisticos;
    
    /**
     * Constructor del servicio UjaPack
     */
    public ServicioUjaPack(){
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
            repositorioCentrosLogisticos.guardarOf(of);
        }
    }
    
    /**
     * Añadimos centros logisticos a la base de datos (sin conexiones)
     * @param centrosLogisticos centros logisticos cargados del json
     */
    private void insertaCentrosBD(Map<Integer, CentroLogistico> centrosLogisticos){
        for(Map.Entry<Integer, CentroLogistico> cl : centrosLogisticos.entrySet()){
            repositorioCentrosLogisticos.guardarCL(cl.getValue());
        }
    }
    
    /**
     * Añadimos centros logisticos a la base de datos
     * @param centrosLogisticos centros logisticos cargados del json
     */
    private void actualizarCentrosBD(Map<Integer, CentroLogistico> centrosLogisticos){
        for(Map.Entry<Integer, CentroLogistico> cl : centrosLogisticos.entrySet()){
            repositorioCentrosLogisticos.actualizarCL(cl.getValue());
        }
    }
    
    /**
     * Crear un nuevo cliente en el sistema
     * @param cliente Cliente a añadir al sistema
     */
    public void altaCliente(Cliente cliente){
        if(repositorioEnvios.buscarCliente(cliente.getDni()).isPresent()){
            throw new ClienteYaRegistrado();
        }
        
        repositorioEnvios.guardarCliente(cliente);
    }
    
    /**
     * Insertar paquetes de un envio en la BD
     * @param paquetes paquetes a insertar
     */
    private void altaPaquetes(List<Paquete> paquetes){
        for(Paquete paquete : paquetes){
            repositorioEnvios.guardarPaquete(paquete);
        }
    }
    
    /**
     * Guardar ruta del envio en la BD
     * @param ruta ruta a insertar
     */
    private void altaRuta(List<PasoPuntoControl> ruta){
        for(PasoPuntoControl pasoPuntoControl : ruta){
            repositorioEnvios.guardarPuntoControl(pasoPuntoControl);
        }
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
        
        altaPaquetes(paquetes);
        altaCliente(remitente);
        altaCliente(destinatario);
        
        Envio envio = new Envio(localizador, remitente, destinatario, paquetes);
        List<PasoPuntoControl> ruta = calculaRuta(envio.getLocalizador(), remitente.getProvincia(), destinatario.getProvincia());
        
        altaRuta(ruta);
        
        envio.setRuta(ruta);
        envio.calculaImporte();
        repositorioEnvios.guardarEnvio(envio);
        
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
        } while(repositorioEnvios.buscarEnvio(localizador).isPresent());
        
        return localizador;
    }
    
    /**
     * Calcula la ruta que debera seguir nuestro paquete
     * @param pRemitente provincia del remitente
     * @param pDestinatario provincia del destinatario
     * @return ruta que debe seguir el paquete para llegar a su destino
     */
    private List<PasoPuntoControl> calculaRuta(int localizador, String pRemitente, String pDestinatario){
        // Comprueba si las provincias introducidas son validas
        if(!provinciasValidas(pRemitente, pDestinatario)){
            throw new ProvinciaNoValida();
        }
        /** Lista de pasos por punto de control que indicara la ruta de nuestro envio */
        List<PasoPuntoControl> ruta = new ArrayList<>();
        
        // Tipo de envio 1
        if(pRemitente.equals(pDestinatario)){
            PasoPuntoControl ppc = new PasoPuntoControl(localizador, buscaProvincia(pRemitente));
            ruta.add(ppc);
        }
        
        boolean b = mismoCentroLogistico(pRemitente, pDestinatario);
        
        // Tipo de envio 2
        if(!pRemitente.equals(pDestinatario) && mismoCentroLogistico(pRemitente, pDestinatario)){
            
            PasoPuntoControl paso = new PasoPuntoControl(localizador, buscaProvincia(pRemitente));
            ruta.add(paso);
            
            PasoPuntoControl pasoCl = new PasoPuntoControl(localizador, buscaCentroLogistico(pRemitente));
            ruta.add(pasoCl);
            
            PasoPuntoControl pasoDestino = new PasoPuntoControl(localizador, buscaProvincia(pDestinatario));
            ruta.add(pasoDestino);
            
        }
        
        // Tipo de envio 3
//        if(!pRemitente.equals(pDestinatario) && !mismoCentroLogistico(pRemitente, pDestinatario)){
//            calculaRutaTipo3(buscaCentroLogistico(pRemitente), buscaCentroLogistico(pDestinatario));
//        }
        
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
        return (buscaCentroLogistico(pRemitente).getIdCentro() == (buscaCentroLogistico(pDestinatario).getIdCentro()));
    }
    
    /**
     * Busca el identificador del centro logistico de una provincia.
     * Si no la encuentra devuelve -1
     * @param provincia Provincia de la que buscaremos su centro logistico
     * @return id del centro logistico al que pertenece la provincia
     */
    private CentroLogistico buscaCentroLogistico(String provincia){
        Oficina oficina = repositorioCentrosLogisticos.buscarOf(provincia).orElseThrow(ProvinciaNoValida::new);
        CentroLogistico centroLogistico = repositorioCentrosLogisticos.buscarCLIdCentro(oficina.getIdCentro()).orElseThrow(CentroLogisticoNoValido::new);
        
        return centroLogistico;
    }
    
    /**
     * Buscar Provincia
     * @param provincia provincia a buscar
     * @return el objeto provincia encontrado
     */
    private Oficina buscaProvincia(String provincia){
        Oficina oficina = repositorioCentrosLogisticos.buscarOf(provincia).orElseThrow(ProvinciaNoValida::new);
        
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
        Envio envio = repositorioEnvios.buscarEnvio(localizador).orElseThrow(EnvioNoEncontrado::new);
        return envio.getEstado();
    }
    
    /**
     * Obtener ruta de un envio
     * @param localizador localizador del envio
     * @return ruta del envio
     */
    public List<PasoPuntoControl> listarPuntosDeControlEnvio(int localizador){
        Envio envio = repositorioEnvios.buscarEnvio(localizador).orElseThrow(EnvioNoEncontrado::new);
        return envio.getRuta();
    }
    
    /**
     * Notificar llegada o salida de oficina
     * @param tipoNotificacion llegada o salida
     * @param oficina oficina a modificar
     * @param localizador localizador del envio
     */
    public void notificarOficina(TipoNotificacion tipoNotificacion, String oficina, int localizador){
        //ERROR - Carga datos antiguos de oficinas...
        Oficina of = repositorioCentrosLogisticos.buscarOf(oficina).orElseThrow(ProvinciaNoValida::new);
        List<PasoPuntoControl> ruta = repositorioEnvios.buscarRutaEnvio(localizador, of.getId()).orElseThrow(PasoPuntoControlNoEncontrado::new);
        System.out.println(of.getId());
        System.out.println(of.getIdCentro());
        
        if(tipoNotificacion == TipoNotificacion.SALIDA){
            for(PasoPuntoControl ppc : ruta){
                if(ppc.getFechaSalida().isEqual(null)){
                    ppc.setFechaSalida(LocalDateTime.now());
                    repositorioEnvios.actualizarPasoPuntoControl(ppc);
                }
            }
        }
        
        if(tipoNotificacion == TipoNotificacion.LLEGADA){
            for(PasoPuntoControl ppc : ruta){
                if(ppc.getFechaSalida().isEqual(null)){
                    ppc.setFechaLlegada(LocalDateTime.now());
                    repositorioEnvios.actualizarPasoPuntoControl(ppc);
                }
            }
        }
    }
    
    /**
     * Notificar llegada o salida de paquete de un centro logistico
     * @param tipoNotificacion entrada o salida
     * @param idCentro identificador del centro logistico
     * @param localizador localizador del envio
     * @return 
     */
    public List<PasoPuntoControl> notificarCentroLogistico(TipoNotificacion tipoNotificacion, int idCentro, int localizador){
        //ERROR - Carga datos antiguos de centroslogisticos...
        CentroLogistico cl = repositorioCentrosLogisticos.buscarCLIdCentro(idCentro).orElseThrow(CentroLogisticoNoValido::new);
        List<PasoPuntoControl> ruta = repositorioEnvios.buscarRutaEnvio(localizador, cl.getId()).orElseThrow(PasoPuntoControlNoEncontrado::new);
        System.out.println(cl.getId());
        
        if(tipoNotificacion == TipoNotificacion.SALIDA){
            for(PasoPuntoControl ppc : ruta){
                if(ppc.getFechaSalida().isEqual(null)){
                    ppc.setFechaSalida(LocalDateTime.now());
                    repositorioEnvios.actualizarPasoPuntoControl(ppc);
                }
            }
        }
        
        if(tipoNotificacion == TipoNotificacion.LLEGADA){
            for(PasoPuntoControl ppc : ruta){
                if(ppc.getFechaSalida().isEqual(null)){
                    ppc.setFechaLlegada(LocalDateTime.now());
                    repositorioEnvios.actualizarPasoPuntoControl(ppc);
                }
            }
        }
        
        return ruta;
    }
    
    /**
     * Marca envio como entregado
     * @param localizador localizador del envio
     */
    void notificarEntrega(int localizador){
        Envio envio = repositorioEnvios.buscarEnvio(localizador).orElseThrow(EnvioNoEncontrado::new);
        actualizaEstadoEnvio(envio, Estado.ENTREGADO);
        repositorioEnvios.actualizarEnvio(envio);
    }
    
    /**
     * Actualizar estado envio
     * @param envio envio a actualizar
     * @param estado estado nuevo del envio
     */
    public void actualizaEstadoEnvio(Envio envio, Estado estado){
        envio.setEstado(estado);
        
        // Si algun paso punto de control tiene fecha a null la cambiamos a actual
        for(PasoPuntoControl ppc : envio.getRuta()){
            if(ppc.getFechaSalida() == null){
                ppc.setFechaSalida(LocalDateTime.now());
            }
            
            if(ppc.getFechaLlegada() == null){
                ppc.setFechaLlegada(LocalDateTime.now());
            }
            
            repositorioEnvios.actualizarPuntoControl(ppc);
        }
        
        repositorioEnvios.actualizarEnvio(envio);
    }
}
