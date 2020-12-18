/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST;
import es.ujaen.dae.ujapack.controladoresREST.DTOs.EnvioDTo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 *
 * @author joseo
 */
@SpringBootTest(classes = es.ujaen.dae.ujapack.app.UjaPackApp.class)
public class TestControladorEnvio {
    @LocalServerPort
    int localPort;
    
    @Autowired
    MappingJackson2HttpMessageConverter springBoot;
    
    @Test
    public void testNuevoEnvio(){
        System.out.println("Creando nuevo envio");
        EnvioDTo envio = null;
    }
    
}
