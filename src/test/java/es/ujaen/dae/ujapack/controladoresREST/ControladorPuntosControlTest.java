/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST;

import es.ujaen.dae.ujapack.servicios.ServicioLimpiadoBaseDeDatos;
import java.util.List;
import javax.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 *
 * @author Ana
 */
@SpringBootTest(classes = es.ujaen.dae.ujapack.app.UjaPackApp.class)
public class ControladorPuntosControlTest {
 
    @Autowired
    ServicioLimpiadoBaseDeDatos limpiadorBaseDatos;
    
    @LocalServerPort
    int localPort;
    
    @Autowired
    MappingJackson2HttpMessageConverter springBootJacksonConverter;
    
    RestTemplateBuilder restTemplateBuilder;
    //TestRestTemplate restTemplate;
    
//    @PostConstruct
//    void crearRestTemplateBuilder(){
//        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
//                .rootUri("http://localhost:" + localPort + "/ujapack")
//                .additionalMessageConverters(List.of(springBootJacksonConverter));
//        restTemplate = new TestRestTemplate(restTemplateBuilder);
//    }
    
//    @PostConstruct
//    void crearRestTemplateBuilder() {
//        restTemplateBuilder = new RestTemplateBuilder()
//                .rootUri("http://localhost:" + localPort + "/ujapack")
//                .additionalMessageConverters(List.of(springBootJacksonConverter));        
//    }
    
    
    
//    @BeforeEach
//    void limpiarBaseDatos() {
//        limpiadorBaseDatos.limpiar();
//    }
}