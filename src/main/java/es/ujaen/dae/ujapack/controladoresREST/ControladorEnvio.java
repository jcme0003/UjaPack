/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST;

import es.ujaen.dae.ujapack.controladoresREST.DTO.DTOCliente;
import es.ujaen.dae.ujapack.controladoresREST.DTO.DTOEnvio;
import es.ujaen.dae.ujapack.controladoresREST.DTO.DTOEnvioContext;
import es.ujaen.dae.ujapack.controladoresREST.DTO.DTOPaquete;
import es.ujaen.dae.ujapack.entidades.Cliente;
import es.ujaen.dae.ujapack.entidades.Envio;
import es.ujaen.dae.ujapack.excepciones.ClienteYaRegistrado;
import es.ujaen.dae.ujapack.objetosvalor.Paquete;
import es.ujaen.dae.ujapack.servicios.ServicioUjaPack;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controlador REST de ujapack para los envios
 * @author Jose Carlos Mena
 */
@RestController
@RequestMapping("/ujapack/envios")
public class ControladorEnvio {
    @Autowired
    ServicioUjaPack servicios; 
    
    /** Hola mundo */
    @GetMapping("/hola")
    String hola(){
        return "Hola Mundo";
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
    
    /** Creacion de paquetes */
    @PostMapping("/paquetes")
    ResponseEntity<List<DTOPaquete>> altaPaquete(@RequestBody List<DTOPaquete> paquetes){
        try{
            List<Paquete> aPaquetes = new ArrayList<>();
            for(DTOPaquete paquete : paquetes){
                aPaquetes.add(paquete.aPaquete());
            }
            servicios.altaPaquetes(aPaquetes);
            return ResponseEntity.status(HttpStatus.CREATED).body(paquetes);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    
    /** Creacion de clientes */
    @PostMapping("/clientes")
    ResponseEntity<DTOCliente> altaCliente(@RequestBody DTOCliente cliente){
        try{
            Cliente nCliente = cliente.aCliente();
            servicios.altaCliente(nCliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
        } catch(ClienteYaRegistrado e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    
}