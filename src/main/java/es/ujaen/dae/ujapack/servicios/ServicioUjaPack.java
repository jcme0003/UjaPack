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
import es.ujaen.dae.ujapack.entidades.puntocontrol.PuntoControl;
import es.ujaen.dae.ujapack.entidades.puntocontrol.PuntoControl.Tipo;
import es.ujaen.dae.ujapack.excepciones.CentroLogisticoNoValido;
import es.ujaen.dae.ujapack.excepciones.ClienteYaRegistrado;
import es.ujaen.dae.ujapack.excepciones.EnvioNoEncontrado;
import es.ujaen.dae.ujapack.excepciones.ProvinciaNoValida;
import es.ujaen.dae.ujapack.objetosvalor.Paquete;
import es.ujaen.dae.ujapack.repositorios.RepositorioPuntosControl;
import es.ujaen.dae.ujapack.repositorios.RepositorioEnvios;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    RepositorioPuntosControl repositorioPuntoControl;
    
    /**
     * Constructor del servicio UjaPack
     */
    public ServicioUjaPack(){
    }
    
    
    /**
     * Crear un nuevo cliente en el sistema
     * @param cliente Cliente a añadir al sistema
     */
    public void altaCliente(Cliente cliente){
        if(buscarCliente(cliente)){
            throw new ClienteYaRegistrado();
        }
        
        repositorioEnvios.guardarCliente(cliente);
    }
    
    /**
     * Buscar cliente
     * @param cliente Cliente a buscar en el sistema
     */
    private boolean buscarCliente(Cliente cliente){
        return repositorioEnvios.buscarCliente(cliente.getDni()).isPresent();
    }
    
    /**
     * Insertar paquetes de un envio en la BD
     * @param paquetes paquetes a insertar
     */
    public void altaPaquetes(List<Paquete> paquetes){
        for(Paquete paquete : paquetes){
            repositorioEnvios.guardarPaquete(paquete);
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
        
        if(!buscarCliente(remitente)){
            altaCliente(remitente);
        }
        if(!buscarCliente(destinatario)){
            altaCliente(destinatario);
        }
        
        Envio envio = new Envio(localizador, remitente, destinatario, paquetes);
        List<PasoPuntoControl> ruta = calculaRuta(envio.getLocalizador(), remitente.getProvincia(), destinatario.getProvincia());
        
        envio.setRuta(ruta);
        envio.calculaImporte();
        repositorioEnvios.guardarEnvio(envio);
        
        return envio;
    }
    
    /**
     * 
     * @param localizador
     * @return 
     */
    public Envio buscarEnvio(int localizador){
        return repositorioEnvios.buscarEnvio(localizador).orElseThrow(EnvioNoEncontrado::new);
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
        
        return ruta;
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
     * Buscar centro logistico segun su id de centro
     * @param idCentro identificador a buscar
     * @return centro logistico encontrado
     */
    public CentroLogistico buscaCentroLogistico(int idCentro){
        CentroLogistico centro = repositorioPuntoControl.buscarCLIdCentro(idCentro).orElseThrow(CentroLogisticoNoValido::new);
        
        return centro;
    }
    
    /**
     * Busca el identificador del centro logistico de una provincia.
     * Si no la encuentra devuelve -1
     * @param provincia Provincia de la que buscaremos su centro logistico
     * @return id del centro logistico al que pertenece la provincia
     */
    private CentroLogistico buscaCentroLogistico(String provincia){
        Oficina oficina = repositorioPuntoControl.buscarOf(provincia).orElseThrow(ProvinciaNoValida::new);
        CentroLogistico centroLogistico = repositorioPuntoControl.buscarCLIdCentro(oficina.getIdCentro()).orElseThrow(CentroLogisticoNoValido::new);
        
        return centroLogistico;
    }
    
    /**
     * Buscar Provincia
     * @param provincia provincia a buscar
     * @return el objeto provincia encontrado
     */
    public Oficina buscaProvincia(String provincia){
        Oficina oficina = repositorioPuntoControl.buscarOf(provincia).orElseThrow(ProvinciaNoValida::new);
        
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
        Envio envio = repositorioEnvios.buscarEnvio(localizador).orElseThrow(EnvioNoEncontrado::new);
        List<PasoPuntoControl> ruta = envio.getRuta();
        
        if(tipoNotificacion == TipoNotificacion.SALIDA){
            for(PasoPuntoControl ppc : ruta){
//                if(ppc.getPuntoDeControl().getProvincia().equals(oficina) && ppc.getFechaSalida() == null){
//                    ppc.setFechaSalida(LocalDateTime.now());
//                }
                if((ppc.getPuntoDeControl().getTipo().equals(Tipo.OFICINA) && ppc.getPuntoDeControl().getProvincia().equals(oficina) && ppc.getFechaSalida() == null) || (ppc.getPuntoDeControl().getTipo().equals(Tipo.OFICINA) && ppc.getPuntoDeControl().getProvincia().equals(oficina) && ppc.getFechaSalida().isBefore(LocalDateTime.now()))){
                    ppc.setFechaSalida(LocalDateTime.now());
                }
            }
        }
        
        if(tipoNotificacion == TipoNotificacion.LLEGADA){
            for(PasoPuntoControl ppc : ruta){
//                if(ppc.getPuntoDeControl().getProvincia().equals(oficina) && ppc.getFechaSalida() == null){
//                    ppc.setFechaLlegada(LocalDateTime.now());
//                }
                if((ppc.getPuntoDeControl().getTipo().equals(Tipo.OFICINA) && ppc.getPuntoDeControl().getProvincia().equals(oficina) && ppc.getFechaLlegada() == null) || (ppc.getPuntoDeControl().getTipo().equals(Tipo.OFICINA) && ppc.getPuntoDeControl().getProvincia().equals(oficina) && ppc.getFechaLlegada().isBefore(LocalDateTime.now()))){
                    ppc.setFechaLlegada(LocalDateTime.now());
                }
            }
        }
        
        repositorioEnvios.actualizarEnvio(envio);
    }
    
    /**
     * Notificar llegada o salida de paquete de un centro logistico
     * @param tipoNotificacion entrada o salida
     * @param idCentro identificador del centro logistico
     * @param localizador localizador del envio
     */
    public void notificarCentroLogistico(TipoNotificacion tipoNotificacion, int idCentro, int localizador){
        Envio envio = repositorioEnvios.buscarEnvio(localizador).orElseThrow(EnvioNoEncontrado::new);
        List<PasoPuntoControl> ruta = envio.getRuta();
        
        if(tipoNotificacion == TipoNotificacion.SALIDA){
            for(PasoPuntoControl ppc : ruta){
//                if((ppc.getPuntoDeControl().getIdCentro() == idCentro && ppc.getFechaSalida() == null) || (ppc.getPuntoDeControl().getIdCentro() == idCentro && ppc.getFechaSalida().isBefore(LocalDateTime.now()))){
//                    ppc.setFechaSalida(LocalDateTime.now());
//                }
                if((ppc.getPuntoDeControl().getTipo().equals(Tipo.CENTRO_LOGISTICO) && ppc.getPuntoDeControl().getIdCentro() == idCentro && ppc.getFechaSalida() == null) || (ppc.getPuntoDeControl().getTipo().equals(Tipo.CENTRO_LOGISTICO) && ppc.getPuntoDeControl().getIdCentro() == idCentro && ppc.getFechaSalida().isBefore(LocalDateTime.now()))){
                    ppc.setFechaSalida(LocalDateTime.now());
                }
            }
        }
        
        if(tipoNotificacion == TipoNotificacion.LLEGADA){
            for(PasoPuntoControl ppc : ruta){
//                System.out.println(ppc.getPuntoDeControl().getId());
//                System.out.println(ppc.getPuntoDeControl().getIdCentro() + " == " + idCentro + " => " + (ppc.getPuntoDeControl().getIdCentro() == idCentro));
//                System.out.println(ppc.getFechaLlegada());
//                if((ppc.getPuntoDeControl().getId() == idCentro && ppc.getFechaLlegada() == null) || (ppc.getPuntoDeControl().getId() == idCentro && ppc.getFechaLlegada().isBefore(LocalDateTime.now()))){
//                    System.out.println("ENTRA");
//                    ppc.setFechaLlegada(LocalDateTime.now());
//                    System.out.println("HORA CAMBIADA");
//                }
                if((ppc.getPuntoDeControl().getTipo().equals(Tipo.CENTRO_LOGISTICO) && ppc.getPuntoDeControl().getIdCentro() == idCentro && ppc.getFechaLlegada() == null) || (ppc.getPuntoDeControl().getTipo().equals(Tipo.CENTRO_LOGISTICO) && ppc.getPuntoDeControl().getIdCentro() == idCentro && ppc.getFechaLlegada().isBefore(LocalDateTime.now()))){
                    ppc.setFechaLlegada(LocalDateTime.now());
                }
            }
        }
        
        repositorioEnvios.actualizarEnvio(envio);
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
        }
        
        repositorioEnvios.actualizarEnvio(envio);
    }
}
