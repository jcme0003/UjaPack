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
import es.ujaen.dae.ujapack.servicios.ServicioLimpiadoBaseDeDatos;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
                .rootUri("http://localhost:" + localPort + "/ujapack/envios")
                .additionalMessageConverters(Arrays.asList(springBootJacksonConverter));
    }
    
    @Test
    public void testAltaClienteDuplicado() {
        DTOCliente cliente = new DTOCliente(
                            "87654321B",
                            "Maria",
                            "Muñoz",
                            "Calle verdadera",
                            "Sevilla",
                            "555666777",
                            "gmail@gmail.com");
        
        TestRestTemplate restTemplate = new TestRestTemplate(restTemplateBuilder);
        ResponseEntity<DTOCliente> respuestaCDuplicado = restTemplate.postForEntity("/clientes", cliente, DTOCliente.class);
        ResponseEntity<DTOCliente> respuestaC = restTemplate.postForEntity("/clientes", cliente, DTOCliente.class);
        Assertions.assertThat(respuestaC.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }
//    
//    @Test
//    public void testNuevoEnvio(){
//        DTOPaquete paquete = new DTOPaquete(
//                1.5f,
//                50.0f,
//                10.0f,
//                15.0f);
//        TestRestTemplate restTemplatePaq = new TestRestTemplate(restTemplateBuilder);
//        restTemplatePaq.postForEntity("/paquetes", paquete, DTOPaquete.class);
//        //ResponseEntity<DTOEnvio> respuestaPaq = restTemplate.postForEntity("/paquetes", paquete, DTOEnvio.class);
//        
////        List<DTOPaquete> paquetes = new ArrayList<>();
////        paquetes.add(paquete);
////        List<DTOPaquete> aPaquetes = new ArrayList<>();
////                aPaquetes.add(DTOPaquete.aPaquete());
//        
//        DTOCliente clRemitente = new DTOCliente(
//                "12345678A",
//                "Paco",
//                "Perez",
//                "Calle falsa",
//                "Jaén",
//                "999000111",
//                "email@email.com");
//        
//        TestRestTemplate restTemplateC1 = new TestRestTemplate(restTemplateBuilder);
//        restTemplateC1.postForEntity("/clientes", clRemitente, DTOCliente.class);
//        
//        DTOCliente clDestinatario = new DTOCliente(
//                "87654321B",
//                "Maria",
//                "Muñoz",
//                "Calle verdadera",
//                "Sevilla",
//                "555666777",
//                "gmail@gmail.com");
//        
//        TestRestTemplate restTemplateC2 = new TestRestTemplate(restTemplateBuilder);
//        restTemplateC2.postForEntity("/clientes", clDestinatario, DTOCliente.class);
//        
//       //ResponseEntity<DTOEnvio> respuestaPaq = restTemplate.postForEntity("/paquetes", paquete, DTOEnvio.class);
//        DTOEnvioContext envio = DTOEnvioContext.nuevoEnvio(paquete, clRemitente, clDestinatario);
//        Assertions.assertThat(servicioUjaPack.consultarEstadoEnvio(envio.getLocalizador()) == Estado.PENDIENTE);
//    }
    
    
//    @Test
//    public void testConsultarEstadoEnvioEntregado(){
//        DTOPaquete paquete = new DTOPaquete(
//                1.5f,
//                50.0f,
//                10.0f,
//                15.0f);
//        List<Paquete> paquetes = new ArrayList<>();
//        paquetes.add(paquete);
//        
//        DTOCliente clRemitente = new DTOCliente(
//                "12345678A",
//                "Paco",
//                "Perez",
//                "Calle falsa",
//                "Jaén",
//                "999000111",
//                "email@email.com");
//        
//        DTOCliente clDestinatario = new DTOCliente(
//                "87654321B",
//                "Maria",
//                "Muñoz",
//                "Calle verdadera",
//                "Sevilla",
//                "555666777",
//                "gmail@gmail.com");
//        
//        Envio envio = servicioUjaPack.nuevoEnvio(paquetes, clRemitente, clDestinatario);
//        servicioUjaPack.actualizaEstadoEnvio(envio, Estado.ENTREGADO);
//        
//        Assertions.assertThat(servicioUjaPack.consultarEstadoEnvio(envio.getLocalizador()) == Estado.ENTREGADO);
//    }
    
//    @Test
//    public void testCalculaRutaTipo1(){
//        DTOPaquete paquete = new DTOPaquete(
//                1.5f,
//                50.0f,
//                10.0f,
//                15.0f);
//        List<Paquete> paquetes = new ArrayList<>();
//        paquetes.add(paquete);
//        
//        DTOCliente clRemitente = new DTOCliente(
//                "12345678A",
//                "Paco",
//                "Perez",
//                "Calle falsa",
//                "Jaén",
//                "999000111",
//                "email@email.com");
//        
//        DTOCliente clDestinatario = new DTOCliente(
//                "87654321B",
//                "Maria",
//                "Muñoz",
//                "Calle verdadera",
//                "Jaén",
//                "555666777",
//                "gmail@gmail.com");
//        
//        Envio envio = servicioUjaPack.nuevoEnvio(paquetes, clRemitente, clDestinatario);
//        
//        Assertions.assertThat(envio.getRuta().get(0).getPuntoDeControl().getProvincia().equals("Jaén"));
//    }
    
//    @Test
//    public void testCalculaRutaTipo2(){
//        DTOPaquete paquete = new DTOPaquete(
//                1.5f,
//                50.0f,
//                10.0f,
//                15.0f);
//        List<Paquete> paquetes = new ArrayList<>();
//        paquetes.add(paquete);
//        
//        DTOCliente clRemitente = new DTOCliente(
//                "12345678A",
//                "Paco",
//                "Perez",
//                "Calle falsa",
//                "Jaén",
//                "999000111",
//                "email@email.com");
//        
//        DTOCliente clDestinatario = new DTOCliente(
//                "87654321B",
//                "Maria",
//                "Muñoz",
//                "Calle verdadera",
//                "Sevilla",
//                "555666777",
//                "gmail@gmail.com");
//        
//        Envio envio = servicioUjaPack.nuevoEnvio(paquetes, clRemitente, clDestinatario);
//        
//        Assertions.assertThat(envio.getRuta().get(0).getPuntoDeControl().getProvincia().equals("Jaén"));
//        Assertions.assertThat(envio.getRuta().get(1).getPuntoDeControl().getProvincia().equals("Sevilla"));
//        Assertions.assertThat(envio.getRuta().get(2).getPuntoDeControl().getProvincia().equals("Sevilla"));
//    }
    
        
//    @Test
//    public void testProvinciasValida(){
//        DTOPaquete paquete = new DTOPaquete(
//                1.5f,
//                50.0f,
//                10.0f,
//                15.0f);
//        List<Paquete> paquetes = new ArrayList<>();
//        paquetes.add(paquete);
//        
//        DTOCliente clRemitente = new DTOCliente(
//                "12345678A",
//                "Paco",
//                "Perez",
//                "Calle falsa",
//                "Jaén",
//                "999000111",
//                "email@email.com");
//        
//        TestRestTemplate restTemplateC1 = new TestRestTemplate(restTemplateBuilder);
//        restTemplateC1.postForEntity("/clientes", clRemitente, DTOCliente.class);
//        
//        DTOCliente clDestinatario = new DTOCliente(
//                "87654321B",
//                "Maria",
//                "Muñoz",
//                "Calle verdadera",
//                "Sevilla",
//                "555666777",
//                "gmail@gmail.com");
//        
//        TestRestTemplate restTemplateC2 = new TestRestTemplate(restTemplateBuilder);
//        restTemplateC2.postForEntity("/clientes", clDestinatario, DTOCliente.class);
    
//        Envio envio = servicioUjaPack.nuevoEnvio(paquetes, clRemitente, clDestinatario);
//        
//        Assertions.assertThat(!envio.getRuta().isEmpty());
//    }
    
//    @Test
//    public void testProvinciasNoValidas(){
//        DTOPaquete paquete = new DTOPaquete(
//                1.5f,
//                50.0f,
//                10.0f,
//                15.0f);
//        
//        TestRestTemplate restTemplatePaq = new TestRestTemplate(restTemplateBuilder);
//        restTemplatePaq.postForEntity("/paquetes", paquete, DTOPaquete.class);
////        List<Paquete> paquetes = new ArrayList<>();
////        paquetes.add(paquete);
//        
//        DTOCliente clRemitente = new DTOCliente(
//                "12345678A",
//                "Paco",
//                "Perez",
//                "Calle falsa",
//                "Jaén",
//                "999000111",
//                "email@email.com");
//        
//        TestRestTemplate restTemplateC1 = new TestRestTemplate(restTemplateBuilder);
//        restTemplateC1.postForEntity("/clientes", clRemitente, DTOCliente.class);
//        
//        DTOCliente clDestinatario = new DTOCliente(
//                "87654321B",
//                "Maria",
//                "Muñoz",
//                "Calle verdadera",
//                "jlsd",
//                "555666777",
//                "gmail@gmail.com");
//        TestRestTemplate restTemplateC2 = new TestRestTemplate(restTemplateBuilder);
//        restTemplateC2.postForEntity("/clientes", clDestinatario, DTOCliente.class);
//        
//        TestRestTemplate restTemplate = new TestRestTemplate(restTemplateBuilder);
//        DTOEnvioContext envio = DTOEnvioContext.nuevoEnvio(paquete, clRemitente, clDestinatario);
//        ResponseEntity<DTOEnvioContext> respuestaProvNV = restTemplate.postForEntity(
//                "/",
//                envio,
//                DTOEnvioContext.class
//        );
//
//        Assertions.assertThat(respuestaProvNV.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

//        Assertions.assertThatThrownBy(() -> {
//            servicioUjaPack.nuevoEnvio(paquetes, clRemitente, clDestinatario); })
//                .isInstanceOf(ProvinciaNoValida.class);
//    }
    
//    @Test
//    public void testActualizarPasoPuntoControlOficina(){
//        DTOPaquete paquete = new DTOPaquete(
//                1.5f,
//                50.0f,
//                10.0f,
//                15.0f);
//        List<Paquete> paquetes = new ArrayList<>();
//        paquetes.add(paquete);
//        
//        DTOCliente clRemitente = new DTOCliente(
//                "12345678A",
//                "Paco",
//                "Perez",
//                "Calle falsa",
//                "Jaén",
//                "999000111",
//                "email@email.com");
//        
//        DTOCliente clDestinatario = new DTOCliente(
//                "87654321B",
//                "Maria",
//                "Muñoz",
//                "Calle verdadera",
//                "Jaén",
//                "555666777",
//                "gmail@gmail.com");
//        
//        Envio envio = servicioUjaPack.nuevoEnvio(paquetes, clRemitente, clDestinatario);
//        
//        servicioUjaPack.notificarOficina(TipoNotificacion.SALIDA, "Jaén", envio.getLocalizador());
//        List<PasoPuntoControl> ruta = servicioUjaPack.buscarEnvio(envio.getLocalizador()).getRuta();
//        
//        Assertions.assertThat(!ruta.isEmpty());
//        Assertions.assertThat(ruta.get(0).getFechaSalida() != null);
//        Assertions.assertThat(ruta.get(0).getFechaSalida().isAfter(LocalDateTime.MIN));
//        Assertions.assertThat(ruta.get(0).getFechaSalida().isBefore(LocalDateTime.now()));
//    }
//    
//    @Test
//    public void testActualizarPasoPuntoControlCentroLogistico(){
//        DTOPaquete paquete = new DTOPaquete(
//                1.5f,
//                50.0f,
//                10.0f,
//                15.0f);
//        List<Paquete> paquetes = new ArrayList<>();
//        paquetes.add(paquete);
//        
//        DTOCliente clRemitente = new DTOCliente(
//                "12345678A",
//                "Paco",
//                "Perez",
//                "Calle falsa",
//                "Jaén",
//                "999000111",
//                "email@email.com");
//        
//        DTOCliente clDestinatario = new DTOCliente(
//                "87654321B",
//                "Maria",
//                "Muñoz",
//                "Calle verdadera",
//                "Sevilla",
//                "555666777",
//                "gmail@gmail.com");
//        
//        Envio envio = servicioUjaPack.nuevoEnvio(paquetes, clRemitente, clDestinatario);
//        
//        servicioUjaPack.notificarCentroLogistico(TipoNotificacion.LLEGADA, 1, envio.getLocalizador());
//        List<PasoPuntoControl> ruta = servicioUjaPack.buscarEnvio(envio.getLocalizador()).getRuta();
//        
//        Assertions.assertThat(!ruta.isEmpty());
//        Assertions.assertThat(ruta.get(0).getFechaLlegada() != null);
//        Assertions.assertThat(ruta.get(0).getFechaLlegada().isAfter(LocalDateTime.MIN));
//        Assertions.assertThat(ruta.get(0).getFechaLlegada().isBefore(LocalDateTime.now()));
//    }
    
    
    @BeforeEach
    void limpiarBaseDatos() {
        limpiadorBaseDatos.limpiar();
    }
}
