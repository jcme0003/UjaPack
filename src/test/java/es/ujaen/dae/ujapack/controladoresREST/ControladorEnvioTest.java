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
import es.ujaen.dae.ujapack.controladoresREST.DTO.DTORuta;
import es.ujaen.dae.ujapack.entidades.Envio;
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
    public void testAltaCliente() {
        DTOCliente cliente = new DTOCliente(
                            "87654321B",
                            "Maria",
                            "Muñoz",
                            "Calle verdadera",
                            "Sevilla",
                            "555666777",
                            "gmail@gmail.com");
        TestRestTemplate restTemplate = new TestRestTemplate(restTemplateBuilder.basicAuthentication("admin", "admin"));
        ResponseEntity<DTOCliente> respuestaCliente = restTemplate.postForEntity("/clientes", cliente, DTOCliente.class);
        
        Assertions.assertThat(respuestaCliente.getStatusCode()).isEqualTo(HttpStatus.CREATED);
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
        TestRestTemplate restTemplate = new TestRestTemplate(restTemplateBuilder.basicAuthentication("admin", "admin"));
        ResponseEntity<DTOCliente> respuestaCliente = restTemplate.postForEntity("/clientes", cliente, DTOCliente.class);
        ResponseEntity<DTOCliente> respuestaClienteDuplicado = restTemplate.postForEntity("/clientes", cliente, DTOCliente.class);
        
        Assertions.assertThat(respuestaClienteDuplicado.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }
    
    @Test
    public void testNuevoEnvio(){
        DTOPaquete paquete = new DTOPaquete(
                1.5f,
                50.0f,
                10.0f,
                15.0f);
        List<DTOPaquete> paquetes = new ArrayList<>();
        paquetes.add(paquete);
        
        DTOCliente clRemitente = new DTOCliente(
                "12345678A",
                "Paco",
                "Perez",
                "Calle falsa",
                "Jaén",
                "999000111",
                "email@email.com");
        
        DTOCliente clDestinatario = new DTOCliente(
                "87654321B",
                "Maria",
                "Muñoz",
                "Calle verdadera",
                "Sevilla",
                "555666777",
                "gmail@gmail.com");
        
        DTOEnvioContext envio = new DTOEnvioContext(paquetes, clRemitente, clDestinatario);

        TestRestTemplate restTemplate = new TestRestTemplate(restTemplateBuilder.basicAuthentication("admin", "admin"));
        ResponseEntity<DTOEnvio> respuesta = restTemplate.postForEntity("/", envio, DTOEnvio.class);
        
        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
    
    @Test
    public void testBuscarEnvio(){
        DTOPaquete paquete = new DTOPaquete(
                1.5f,
                50.0f,
                10.0f,
                15.0f);
        List<DTOPaquete> paquetes = new ArrayList<>();
        paquetes.add(paquete);
        
        DTOCliente clRemitente = new DTOCliente(
                "12345678A",
                "Paco",
                "Perez",
                "Calle falsa",
                "Jaén",
                "999000111",
                "email@email.com");
        
        DTOCliente clDestinatario = new DTOCliente(
                "87654321B",
                "Maria",
                "Muñoz",
                "Calle verdadera",
                "Sevilla",
                "555666777",
                "gmail@gmail.com");
        
        DTOEnvioContext envio = new DTOEnvioContext(paquetes, clRemitente, clDestinatario);

        TestRestTemplate restTemplateAdmin = new TestRestTemplate(restTemplateBuilder.basicAuthentication("admin", "admin"));
        ResponseEntity<DTOEnvio> respuesta = restTemplateAdmin.postForEntity("/", envio, DTOEnvio.class);
        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        
        DTOEnvio envioCreado = respuesta.getBody();
        TestRestTemplate restTemplateUsuario = new TestRestTemplate(restTemplateBuilder.basicAuthentication("usuario", "usuario"));
        ResponseEntity<DTOEnvio> respuestaEnvio = restTemplateUsuario.getForEntity("/{localizador}", DTOEnvio.class, envioCreado.getLocalizador());
        Assertions.assertThat(respuestaEnvio.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    
    @Test
    public void testConsultarEstadoEnvio(){
        DTOPaquete paquete = new DTOPaquete(
                1.5f,
                50.0f,
                10.0f,
                15.0f);
        List<DTOPaquete> paquetes = new ArrayList<>();
        paquetes.add(paquete);
        
        DTOCliente clRemitente = new DTOCliente(
                "12345678A",
                "Paco",
                "Perez",
                "Calle falsa",
                "Jaén",
                "999000111",
                "email@email.com");
        
        DTOCliente clDestinatario = new DTOCliente(
                "87654321B",
                "Maria",
                "Muñoz",
                "Calle verdadera",
                "Sevilla",
                "555666777",
                "gmail@gmail.com");
        
        DTOEnvioContext envio = new DTOEnvioContext(paquetes, clRemitente, clDestinatario);

        TestRestTemplate restTemplate = new TestRestTemplate(restTemplateBuilder.basicAuthentication("admin", "admin"));
        ResponseEntity<DTOEnvio> respuesta = restTemplate.postForEntity("/", envio, DTOEnvio.class);
        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        
        DTOEnvio envioEstado = respuesta.getBody();
        Assertions.assertThat(envioEstado.getEstado()).isEqualTo(Envio.Estado.PENDIENTE);
    }
    
    @Test
    public void testCalculaRutaTipo1(){
        DTOPaquete paquete = new DTOPaquete(
                1.5f,
                50.0f,
                10.0f,
                15.0f);
        List<DTOPaquete> paquetes = new ArrayList<>();
        paquetes.add(paquete);
        
        DTOCliente clRemitente = new DTOCliente(
                "12345678A",
                "Paco",
                "Perez",
                "Calle falsa",
                "Jaén",
                "999000111",
                "email@email.com");
        
        DTOCliente clDestinatario = new DTOCliente(
                "87654321B",
                "Maria",
                "Muñoz",
                "Calle verdadera",
                "Jaén",
                "555666777",
                "gmail@gmail.com");
        
        DTOEnvioContext envio = new DTOEnvioContext(paquetes, clRemitente, clDestinatario);

        TestRestTemplate restTemplate = new TestRestTemplate(restTemplateBuilder.basicAuthentication("admin", "admin"));
        ResponseEntity<DTOEnvio> respuesta = restTemplate.postForEntity("/", envio, DTOEnvio.class);
        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        
        DTOEnvio envioCreado = respuesta.getBody();
        ResponseEntity<DTOEnvio> respuestaEnvio = restTemplate.getForEntity("/{localizador}", DTOEnvio.class, envioCreado.getLocalizador());
        Assertions.assertThat(respuestaEnvio.getStatusCode()).isEqualTo(HttpStatus.OK);
        
        ResponseEntity<DTORuta> respuestaEnvioRuta = restTemplate.getForEntity("/{localizador}/ruta", DTORuta.class, envioCreado.getLocalizador());
        Assertions.assertThat(respuestaEnvioRuta.getStatusCode()).isEqualTo(HttpStatus.OK);
        
        DTORuta ruta = respuestaEnvioRuta.getBody();
        Assertions.assertThat(ruta.getRuta().get(0).getTipo() == "OFICINA");
    }
    
    @Test
    public void testCalculaRutaTipo2(){
        DTOPaquete paquete = new DTOPaquete(
                1.5f,
                50.0f,
                10.0f,
                15.0f);
        List<DTOPaquete> paquetes = new ArrayList<>();
        paquetes.add(paquete);
        
        DTOCliente clRemitente = new DTOCliente(
                "12345678A",
                "Paco",
                "Perez",
                "Calle falsa",
                "Jaén",
                "999000111",
                "email@email.com");
        
        DTOCliente clDestinatario = new DTOCliente(
                "87654321B",
                "Maria",
                "Muñoz",
                "Calle verdadera",
                "Sevilla",
                "555666777",
                "gmail@gmail.com");
        
        DTOEnvioContext envio = new DTOEnvioContext(paquetes, clRemitente, clDestinatario);

        TestRestTemplate restTemplate = new TestRestTemplate(restTemplateBuilder.basicAuthentication("admin", "admin"));
        ResponseEntity<DTOEnvio> respuesta = restTemplate.postForEntity("/", envio, DTOEnvio.class);
        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        
        DTOEnvio envioCreado = respuesta.getBody();
        ResponseEntity<DTOEnvio> respuestaEnvio = restTemplate.getForEntity("/{localizador}", DTOEnvio.class, envioCreado.getLocalizador());
        Assertions.assertThat(respuestaEnvio.getStatusCode()).isEqualTo(HttpStatus.OK);
        
        ResponseEntity<DTORuta> respuestaEnvioRuta = restTemplate.getForEntity("/{localizador}/ruta", DTORuta.class, envioCreado.getLocalizador());
        Assertions.assertThat(respuestaEnvioRuta.getStatusCode()).isEqualTo(HttpStatus.OK);
        
        DTORuta ruta = respuestaEnvioRuta.getBody();
        Assertions.assertThat(ruta.getRuta().get(0).getTipo() == "OFICINA");
        Assertions.assertThat(ruta.getRuta().get(1).getTipo() == "CENTRO_LOGISTICO");
        Assertions.assertThat(ruta.getRuta().get(2).getTipo() == "OFICINA");
    }
    
    @Test
    public void testProvinciasEnvioValida(){
        DTOPaquete paquete = new DTOPaquete(
                1.5f,
                50.0f,
                10.0f,
                15.0f);
        List<DTOPaquete> paquetes = new ArrayList<>();
        paquetes.add(paquete);
        
        DTOCliente clRemitente = new DTOCliente(
                "12345678A",
                "Paco",
                "Perez",
                "Calle falsa",
                "Jaén",
                "999000111",
                "email@email.com");
        
        DTOCliente clDestinatario = new DTOCliente(
                "87654321B",
                "Maria",
                "Muñoz",
                "Calle verdadera",
                "Jaén",
                "555666777",
                "gmail@gmail.com");
        
        DTOEnvioContext envio = new DTOEnvioContext(paquetes, clRemitente, clDestinatario);

        TestRestTemplate restTemplate = new TestRestTemplate(restTemplateBuilder.basicAuthentication("admin", "admin"));
        ResponseEntity<DTOEnvio> respuesta = restTemplate.postForEntity("/", envio, DTOEnvio.class);
        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
    
    @Test
    public void testProvinciasEnvioNoValidas(){
        DTOPaquete paquete = new DTOPaquete(
                1.5f,
                50.0f,
                10.0f,
                15.0f);
        List<DTOPaquete> paquetes = new ArrayList<>();
        paquetes.add(paquete);
        
        DTOCliente clRemitente = new DTOCliente(
                "12345678A",
                "Paco",
                "Perez",
                "Calle falsa",
                "Jaén",
                "999000111",
                "email@email.com");
        
        DTOCliente clDestinatario = new DTOCliente(
                "87654321B",
                "Maria",
                "Muñoz",
                "Calle verdadera",
                "Jan",
                "555666777",
                "gmail@gmail.com");
        
        DTOEnvioContext envio = new DTOEnvioContext(paquetes, clRemitente, clDestinatario);

        TestRestTemplate restTemplate = new TestRestTemplate(restTemplateBuilder.basicAuthentication("admin", "admin"));
        ResponseEntity<DTOEnvio> respuesta = restTemplate.postForEntity("/", envio, DTOEnvio.class);
        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    
    @Test
    public void testActualizarPasoPuntoControlOficina(){
        DTOPaquete paquete = new DTOPaquete(
                1.5f,
                50.0f,
                10.0f,
                15.0f);
        List<DTOPaquete> paquetes = new ArrayList<>();
        paquetes.add(paquete);
        
        DTOCliente clRemitente = new DTOCliente(
                "12345678A",
                "Paco",
                "Perez",
                "Calle falsa",
                "Jaén",
                "999000111",
                "email@email.com");
        
        DTOCliente clDestinatario = new DTOCliente(
                "87654321B",
                "Maria",
                "Muñoz",
                "Calle verdadera",
                "Jaén",
                "555666777",
                "gmail@gmail.com");
        
        DTOEnvioContext envio = new DTOEnvioContext(paquetes, clRemitente, clDestinatario);

        TestRestTemplate restTemplate = new TestRestTemplate(restTemplateBuilder.basicAuthentication("admin", "admin"));
        ResponseEntity<DTOEnvio> respuesta = restTemplate.postForEntity("/", envio, DTOEnvio.class);
        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        
        DTOEnvio envioCreado = respuesta.getBody();
        DTORuta rutaEnvioCreado = envioCreado.getRuta();
        String fechaOriginal = rutaEnvioCreado.getRuta().get(0).getFechaLlegada();
        
        ResponseEntity<Void> respuestaNotificarOficina = restTemplate.postForEntity("/{localizador}/notificaroficina/{oficina}", "llegada", Void.class, envioCreado.getLocalizador(), clDestinatario.getProvincia());
        Assertions.assertThat(respuestaNotificarOficina.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        
        ResponseEntity<DTORuta> respuestaRutaEnvioModificado = restTemplate.getForEntity("/{localizador}/ruta", DTORuta.class, envioCreado.getLocalizador());
        Assertions.assertThat(respuestaRutaEnvioModificado.getStatusCode()).isEqualTo(HttpStatus.OK);
        DTORuta rutaEnvioModificado = respuestaRutaEnvioModificado.getBody();
        String fechaModificada = rutaEnvioModificado.getRuta().get(0).getFechaLlegada();
        
        Assertions.assertThat(!fechaOriginal.equals(fechaModificada));
    }
    
    @Test
    public void testActualizarPasoPuntoControlCentro(){
        DTOPaquete paquete = new DTOPaquete(
                1.5f,
                50.0f,
                10.0f,
                15.0f);
        List<DTOPaquete> paquetes = new ArrayList<>();
        paquetes.add(paquete);
        
        DTOCliente clRemitente = new DTOCliente(
                "12345678A",
                "Paco",
                "Perez",
                "Calle falsa",
                "Jaén",
                "999000111",
                "email@email.com");
        
        DTOCliente clDestinatario = new DTOCliente(
                "87654321B",
                "Maria",
                "Muñoz",
                "Calle verdadera",
                "Sevilla",
                "555666777",
                "gmail@gmail.com");
        
        DTOEnvioContext envio = new DTOEnvioContext(paquetes, clRemitente, clDestinatario);

        TestRestTemplate restTemplate = new TestRestTemplate(restTemplateBuilder.basicAuthentication("admin", "admin"));
        ResponseEntity<DTOEnvio> respuesta = restTemplate.postForEntity("/", envio, DTOEnvio.class);
        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        
        DTOEnvio envioCreado = respuesta.getBody();
        DTORuta rutaEnvioCreado = envioCreado.getRuta();
        String fechaOriginal = rutaEnvioCreado.getRuta().get(1).getFechaSalida();
        
        ResponseEntity<Void> respuestaNotificarPasoCentroLogistico = restTemplate.postForEntity("/{localizador}/notificarcentrologistico/{idCentro}", "salida", Void.class, envioCreado.getLocalizador(), 1);
        Assertions.assertThat(respuestaNotificarPasoCentroLogistico.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        
        ResponseEntity<DTORuta> respuestaRutaEnvioModificado = restTemplate.getForEntity("/{localizador}/ruta", DTORuta.class, envioCreado.getLocalizador());
        Assertions.assertThat(respuestaRutaEnvioModificado.getStatusCode()).isEqualTo(HttpStatus.OK);
        DTORuta rutaEnvioModificado = respuestaRutaEnvioModificado.getBody();
        String fechaModificada = rutaEnvioModificado.getRuta().get(1).getFechaSalida();
        
        Assertions.assertThat(!fechaOriginal.equals(fechaModificada));
    }
    
    @BeforeEach
    void limpiarBaseDatos() {
        limpiadorBaseDatos.limpiar();
    }
}
