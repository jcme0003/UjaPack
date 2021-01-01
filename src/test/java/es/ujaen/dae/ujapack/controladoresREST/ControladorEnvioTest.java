/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST;

import es.ujaen.dae.ujapack.servicios.ServicioLimpiadoBaseDeDatos;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;

/**
 *
 * @author joseo, Ana
 */
@SpringBootTest(classes = es.ujaen.dae.ujapack.app.UjaPackApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControladorEnvioTest {
    
    @Autowired
    ServicioLimpiadoBaseDeDatos limpiadorBaseDatos;
    
    @LocalServerPort
    int localPort;
    
//    @Autowired
//    MappingJacksonHttpMessageConverter springBootJacksonConverter;
    
    RestTemplateBuilder restTemplateBuilder;
    
//    @PostConstruct
//    void crearRestTemplateBuilder(){
//        restTemplateBuilder = new RestTemplateBuilder()
//                .rootUri("http://localhost:" + localPort + "/ujapack")
//                .additionalMessageConverters(List.of(springBootJacksonConverter));
//    }
    
//    @BeforeEach
//    void limpiarBaseDatos() {
//        limpiadorBaseDatos.limpiar();
//    }
}
