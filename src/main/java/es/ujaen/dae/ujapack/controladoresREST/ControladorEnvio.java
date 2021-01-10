/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST;

import com.fasterxml.jackson.databind.node.TextNode;
import es.ujaen.dae.ujapack.controladoresREST.DTO.DTOCliente;
import es.ujaen.dae.ujapack.controladoresREST.DTO.DTOEnvio;
import es.ujaen.dae.ujapack.controladoresREST.DTO.DTOEnvioContext;
import es.ujaen.dae.ujapack.controladoresREST.DTO.DTOPaquete;
import es.ujaen.dae.ujapack.controladoresREST.DTO.DTORuta;
import es.ujaen.dae.ujapack.entidades.Cliente;
import es.ujaen.dae.ujapack.entidades.Envio;
import es.ujaen.dae.ujapack.excepciones.ClienteYaRegistrado;
import es.ujaen.dae.ujapack.excepciones.EnvioNoEncontrado;
import es.ujaen.dae.ujapack.excepciones.PedidoEntregado;
import es.ujaen.dae.ujapack.excepciones.ProvinciaNoValida;
import es.ujaen.dae.ujapack.objetosvalor.Paquete;
import es.ujaen.dae.ujapack.servicios.ServicioUjaPack;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controlador REST de ujapack para los envios
 * @author Jose Carlos Mena
 */
@RestController
@RequestMapping("/ujapack/envios")
@CrossOrigin(origins = "*")
public class ControladorEnvio {
    @Autowired
    ServicioUjaPack servicios;
    
    /** Handler para excepciones de violación de restricciones */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handlerViolacionRestricciones(ConstraintViolationException e){
    }
    
    /** Handler para excepciones de accesos de Envios no encontrados */
    @ExceptionHandler(EnvioNoEncontrado.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void HandlerCentroLogisticoNoValido(EnvioNoEncontrado e){
    }
    
    /** Handler para excepciones de Provincias invalidas */
    @ExceptionHandler(ProvinciaNoValida.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void HandlerProvinciaNoValida(ProvinciaNoValida e){
    }
    
    /** Creacion de envios */
    @PostMapping("/")
    ResponseEntity<DTOEnvio> altaEnvio(@RequestBody DTOEnvioContext envioContext){
        try{
            List<Paquete> aPaquetes = new ArrayList<>();
            for(DTOPaquete paquete : envioContext.getPaquetes()){
                aPaquetes.add(paquete.aPaquete());
            }
            Envio envio = servicios.nuevoEnvio(aPaquetes, envioContext.getRemitente().aCliente(), envioContext.getDestinatario().aCliente());
            return ResponseEntity.status(HttpStatus.CREATED).body(new DTOEnvio(envio));
        } catch(ClienteYaRegistrado e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    
    /** Datos del envio
     * @param localizador
     * @return 
     */
    @GetMapping("/{localizador}")
    @ResponseStatus(HttpStatus.OK)
    public DTOEnvio verEnvio(@PathVariable int localizador){
        return new DTOEnvio(servicios.buscarEnvio(localizador));
    }
    
    /** Datos de la ruta del envio
     * @param localizador
     * @return 
     */
    @GetMapping("/{localizador}/ruta")
    @ResponseStatus(HttpStatus.OK)
    public DTORuta verRutaEnvio(@PathVariable int localizador){
        return new DTORuta(servicios.buscarEnvio(localizador));
    }
    
    /** Notificar paso por punto de control (Centro logistico)
     * @param localizador
     * @return 
     */
    @PostMapping("/{localizador}/notificarcentrologistico/{idCentro}")
    ResponseEntity<Void> notificarPasoCentroLogistico(@RequestBody TextNode tipoNotificacion, @PathVariable int localizador, @PathVariable int idCentro){
        try{
            if(ServicioUjaPack.TipoNotificacion.LLEGADA.name().equalsIgnoreCase(tipoNotificacion.asText())){
                servicios.notificarCentroLogistico(ServicioUjaPack.TipoNotificacion.LLEGADA, idCentro, localizador);
            } else {
                servicios.notificarCentroLogistico(ServicioUjaPack.TipoNotificacion.SALIDA, idCentro, localizador);
            }
            
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch(PedidoEntregado e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    
    /** Notificar paso por punto de control (Oficina)
     * @param localizador
     * @return 
     */
    @PostMapping("/{localizador}/notificaroficina/{oficina}")
    ResponseEntity<Void> notificarPasoOficina(@RequestBody TextNode tipoNotificacion, @PathVariable int localizador, @PathVariable String oficina){
        try{
            if(ServicioUjaPack.TipoNotificacion.LLEGADA.name().equalsIgnoreCase(tipoNotificacion.asText())){
                servicios.notificarOficina(ServicioUjaPack.TipoNotificacion.LLEGADA, oficina, localizador);
            } else {
                servicios.notificarOficina(ServicioUjaPack.TipoNotificacion.SALIDA, oficina, localizador);
            }
            
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch(PedidoEntregado e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    
    /** Creacion de clientes */
    @PostMapping("/clientes")
    ResponseEntity<DTOCliente> altaCliente(@RequestBody DTOCliente cliente){
        try{
            Cliente nCliente = cliente.aCliente();
            servicios.altaCliente(nCliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(new DTOCliente(nCliente));
        } catch(ClienteYaRegistrado e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    
}