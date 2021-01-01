/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST;

import es.ujaen.dae.ujapack.servicios.ServicioUjaPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controlador REST de ujapack para los envios
 * @author Jose Carlos Mena
 */
@RestController
@RequestMapping("/ujapack")
public class ControladorEnvio {
    @Autowired
    ServicioUjaPack servicios; 
    
    @GetMapping("/hola")
    String hola(){
        return "Hola Mundo";
    }
}