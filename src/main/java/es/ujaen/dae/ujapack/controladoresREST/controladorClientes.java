/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST;
import es.ujaen.dae.ujapack.entidades.Cliente;
import es.ujaen.dae.ujapack.entidades.Envio;
import es.ujaen.dae.ujapack.excepciones.ClienteYaRegistrado;
import es.ujaen.dae.ujapack.servicios.ServicioUjaPack;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


/**
 * 
 * @author joseo
 */
@RestController
@RequestMapping("/ujapack")
public class controladorClientes {
    @Autowired
    ServicioUjaPack servicios; 
    
    @PostConstruct
    void controladorIniciado(){
        System.out.println("El controlador REST del cliente ha sido iniciado");
    }
    
    @PostMapping("/Clientes")
    ResposeEntity<Envio> AltaCliente(@RequestBody Cliente cliente, ){
        try{
        servicios.altaCliente(cliente);
        } catch (ClienteYaRegistrado e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build;
        }
    }
    
}
