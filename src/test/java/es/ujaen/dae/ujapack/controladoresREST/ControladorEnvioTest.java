/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST;

import es.ujaen.dae.ujapack.servicios.ServicioLimpiadoBaseDeDatos;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Test para controlador REST de envios
 * @author joseo, Ana, Jose Carlos Mena
 */
@SpringBootTest(classes = es.ujaen.dae.ujapack.app.UjaPackApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControladorEnvioTest {
    
    @Autowired
    ServicioLimpiadoBaseDeDatos limpiadorBaseDatos;
    
    @LocalServerPort
    int localPort;
    
    @Autowired
    MappingJackson2HttpMessageConverter springBootJacksonConverter;
    
    RestTemplateBuilder restTemplateBuilder;
    
    /**
     * Crear RestTemplate para las pruebas
     */
    @PostConstruct
    void crearRestTemplateBuilder(){
        restTemplateBuilder = new RestTemplateBuilder()
                .rootUri("http://localhost:" + localPort + "/ujapack")
                .additionalMessageConverters(Arrays.asList(springBootJacksonConverter));
    }
    
}
