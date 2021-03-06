/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.servicios;

import es.ujaen.dae.ujapack.entidades.Cliente;
import es.ujaen.dae.ujapack.entidades.Envio;
import es.ujaen.dae.ujapack.entidades.Envio.Estado;
import es.ujaen.dae.ujapack.entidades.PasoPuntoControl;
import es.ujaen.dae.ujapack.excepciones.ClienteYaRegistrado;
import es.ujaen.dae.ujapack.excepciones.ProvinciaNoValida;
import es.ujaen.dae.ujapack.objetosvalor.Paquete;
import es.ujaen.dae.ujapack.servicios.ServicioUjaPack.TipoNotificacion;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author Jose Carlos Mena
 */
@SpringBootTest(classes = es.ujaen.dae.ujapack.app.UjaPackApp.class)
public class ServicioUjaPackTest {
    
    @Autowired
    ServicioUjaPack servicioUjaPack;
    
    @Autowired
    ServicioCargaDatosRedLogistica servicioCargarDatos;
    
    @Autowired
    ServicioLimpiadoBaseDeDatos limpiadorBaseDatos;
    
    @Test
    public void testAccesoServicioUjaPack(){
        Assertions.assertThat(servicioUjaPack).isNotNull();
    }
    
    @Test
    public void testAltaClienteDuplicado() {
        Cliente cliente = new Cliente(
                            "87654321B",
                            "Maria",
                            "Muñoz",
                            "Calle verdadera",
                            "Sevilla",
                            "555666777",
                            "gmail@gmail.com");
        
        servicioUjaPack.altaCliente(cliente);
        Assertions.assertThatThrownBy(() -> {
            servicioUjaPack.altaCliente(cliente); })
                .isInstanceOf(ClienteYaRegistrado.class);
    }
    
    @Test
    public void testCargaDatosJSon(){
        Assertions.assertThat(servicioCargarDatos.cargaDatosJSon().size() == 10);
    }
    
    @Test
    public void testGeneraLocalizador(){
        Paquete paquete = new Paquete(
                1.5f,
                50.0f,
                10.0f,
                15.0f);
        List<Paquete> paquetes = new ArrayList<>();
        paquetes.add(paquete);
        
        Cliente clRemitente = new Cliente(
                "12345678A",
                "Paco",
                "Perez",
                "Calle falsa",
                "Jaén",
                "999000111",
                "email@email.com");
        
        Cliente clDestinatario = new Cliente(
                "87654321B",
                "Maria",
                "Muñoz",
                "Calle verdadera",
                "Sevilla",
                "555666777",
                "gmail@gmail.com");
        
        Envio envio = servicioUjaPack.nuevoEnvio(paquetes, clRemitente, clDestinatario);
        
        Assertions.assertThat(Integer.toString(envio.getLocalizador()).length() == 10);
    }
    
    @Test
    public void testNuevoEnvio(){
        Paquete paquete = new Paquete(
                1.5f,
                50.0f,
                10.0f,
                15.0f);
        List<Paquete> paquetes = new ArrayList<>();
        paquetes.add(paquete);
        
        Cliente clRemitente = new Cliente(
                "12345678A",
                "Paco",
                "Perez",
                "Calle falsa",
                "Jaén",
                "999000111",
                "email@email.com");
        
        Cliente clDestinatario = new Cliente(
                "87654321B",
                "Maria",
                "Muñoz",
                "Calle verdadera",
                "Sevilla",
                "555666777",
                "gmail@gmail.com");
        
        Envio envio = servicioUjaPack.nuevoEnvio(paquetes, clRemitente, clDestinatario);
        Assertions.assertThat(servicioUjaPack.consultarEstadoEnvio(envio.getLocalizador()) == Estado.PENDIENTE);
    }
    
    @Test
    public void testConsultarEstadoEnvio(){
        Paquete paquete = new Paquete(
                1.5f,
                50.0f,
                10.0f,
                15.0f);
        List<Paquete> paquetes = new ArrayList<>();
        paquetes.add(paquete);
        
        Cliente clRemitente = new Cliente(
                "12345678A",
                "Paco",
                "Perez",
                "Calle falsa",
                "Jaén",
                "999000111",
                "email@email.com");
        
        Cliente clDestinatario = new Cliente(
                "87654321B",
                "Maria",
                "Muñoz",
                "Calle verdadera",
                "Sevilla",
                "555666777",
                "gmail@gmail.com");
        
        Envio envio = servicioUjaPack.nuevoEnvio(paquetes, clRemitente, clDestinatario);
        
        Assertions.assertThat(servicioUjaPack.consultarEstadoEnvio(envio.getLocalizador()).equals(Estado.PENDIENTE));
    }
    
    @Test
    public void testConsultarEstadoEnvioEntregado(){
        Paquete paquete = new Paquete(
                1.5f,
                50.0f,
                10.0f,
                15.0f);
        List<Paquete> paquetes = new ArrayList<>();
        paquetes.add(paquete);
        
        Cliente clRemitente = new Cliente(
                "12345678A",
                "Paco",
                "Perez",
                "Calle falsa",
                "Jaén",
                "999000111",
                "email@email.com");
        
        Cliente clDestinatario = new Cliente(
                "87654321B",
                "Maria",
                "Muñoz",
                "Calle verdadera",
                "Sevilla",
                "555666777",
                "gmail@gmail.com");
        
        Envio envio = servicioUjaPack.nuevoEnvio(paquetes, clRemitente, clDestinatario);
        servicioUjaPack.actualizaEstadoEnvio(envio, Estado.ENTREGADO);
        
        Assertions.assertThat(servicioUjaPack.consultarEstadoEnvio(envio.getLocalizador()) == Estado.ENTREGADO);
    }
    
    @Test
    public void testCalculaRutaTipo1(){
        Paquete paquete = new Paquete(
                1.5f,
                50.0f,
                10.0f,
                15.0f);
        List<Paquete> paquetes = new ArrayList<>();
        paquetes.add(paquete);
        
        Cliente clRemitente = new Cliente(
                "12345678A",
                "Paco",
                "Perez",
                "Calle falsa",
                "Jaén",
                "999000111",
                "email@email.com");
        
        Cliente clDestinatario = new Cliente(
                "87654321B",
                "Maria",
                "Muñoz",
                "Calle verdadera",
                "Jaén",
                "555666777",
                "gmail@gmail.com");
        
        Envio envio = servicioUjaPack.nuevoEnvio(paquetes, clRemitente, clDestinatario);
        
        Assertions.assertThat(envio.getRuta().get(0).getPuntoDeControl().getProvincia().equals("Jaén"));
    }
    
    @Test
    public void testCalculaRutaTipo2(){
        Paquete paquete = new Paquete(
                1.5f,
                50.0f,
                10.0f,
                15.0f);
        List<Paquete> paquetes = new ArrayList<>();
        paquetes.add(paquete);
        
        Cliente clRemitente = new Cliente(
                "12345678A",
                "Paco",
                "Perez",
                "Calle falsa",
                "Jaén",
                "999000111",
                "email@email.com");
        
        Cliente clDestinatario = new Cliente(
                "87654321B",
                "Maria",
                "Muñoz",
                "Calle verdadera",
                "Sevilla",
                "555666777",
                "gmail@gmail.com");
        
        Envio envio = servicioUjaPack.nuevoEnvio(paquetes, clRemitente, clDestinatario);
        
        Assertions.assertThat(envio.getRuta().get(0).getPuntoDeControl().getProvincia().equals("Jaén"));
        Assertions.assertThat(envio.getRuta().get(1).getPuntoDeControl().getProvincia().equals("Sevilla"));
        Assertions.assertThat(envio.getRuta().get(2).getPuntoDeControl().getProvincia().equals("Sevilla"));
    }
    
    @Test
    public void testListarPuntosDeControlEnvio(){
        Paquete paquete = new Paquete(
                1.5f,
                50.0f,
                10.0f,
                15.0f);
        List<Paquete> paquetes = new ArrayList<>();
        paquetes.add(paquete);
        
        Cliente clRemitente = new Cliente(
                "12345678A",
                "Paco",
                "Perez",
                "Calle falsa",
                "Jaén",
                "999000111",
                "email@email.com");
        
        Cliente clDestinatario = new Cliente(
                "87654321B",
                "Maria",
                "Muñoz",
                "Calle verdadera",
                "Sevilla",
                "555666777",
                "gmail@gmail.com");
        
        Envio envio = servicioUjaPack.nuevoEnvio(paquetes, clRemitente, clDestinatario);
        
        List<PasoPuntoControl> listado = servicioUjaPack.listarPuntosDeControlEnvio(envio.getLocalizador());
        
        Assertions.assertThat(!listado.isEmpty());
    }
    
    @Test
    public void testProvinciasValida(){
        Paquete paquete = new Paquete(
                1.5f,
                50.0f,
                10.0f,
                15.0f);
        List<Paquete> paquetes = new ArrayList<>();
        paquetes.add(paquete);
        
        Cliente clRemitente = new Cliente(
                "12345678A",
                "Paco",
                "Perez",
                "Calle falsa",
                "Jaén",
                "999000111",
                "email@email.com");
        
        Cliente clDestinatario = new Cliente(
                "87654321B",
                "Maria",
                "Muñoz",
                "Calle verdadera",
                "Sevilla",
                "555666777",
                "gmail@gmail.com");
        
        Envio envio = servicioUjaPack.nuevoEnvio(paquetes, clRemitente, clDestinatario);
        
        Assertions.assertThat(!envio.getRuta().isEmpty());
    }
    
    @Test
    public void testProvinciasNoValidas(){
        Paquete paquete = new Paquete(
                1.5f,
                50.0f,
                10.0f,
                15.0f);
        List<Paquete> paquetes = new ArrayList<>();
        paquetes.add(paquete);
        
        Cliente clRemitente = new Cliente(
                "12345678A",
                "Paco",
                "Perez",
                "Calle falsa",
                "Jaén",
                "999000111",
                "email@email.com");
        
        Cliente clDestinatario = new Cliente(
                "87654321B",
                "Maria",
                "Muñoz",
                "Calle verdadera",
                "jlsd",
                "555666777",
                "gmail@gmail.com");
        
        Assertions.assertThatThrownBy(() -> {
            servicioUjaPack.nuevoEnvio(paquetes, clRemitente, clDestinatario); })
                .isInstanceOf(ProvinciaNoValida.class);
    }
    
    @Test
    public void testActualizarPasoPuntoControlOficina(){
        Paquete paquete = new Paquete(
                1.5f,
                50.0f,
                10.0f,
                15.0f);
        List<Paquete> paquetes = new ArrayList<>();
        paquetes.add(paquete);
        
        Cliente clRemitente = new Cliente(
                "12345678A",
                "Paco",
                "Perez",
                "Calle falsa",
                "Jaén",
                "999000111",
                "email@email.com");
        
        Cliente clDestinatario = new Cliente(
                "87654321B",
                "Maria",
                "Muñoz",
                "Calle verdadera",
                "Jaén",
                "555666777",
                "gmail@gmail.com");
        
        Envio envio = servicioUjaPack.nuevoEnvio(paquetes, clRemitente, clDestinatario);
        
        servicioUjaPack.notificarOficina(TipoNotificacion.SALIDA, "Jaén", envio.getLocalizador());
        List<PasoPuntoControl> ruta = servicioUjaPack.buscarEnvio(envio.getLocalizador()).getRuta();
        
        Assertions.assertThat(!ruta.isEmpty());
        Assertions.assertThat(ruta.get(0).getFechaSalida() != null);
        Assertions.assertThat(ruta.get(0).getFechaSalida().isAfter(LocalDateTime.MIN));
        Assertions.assertThat(ruta.get(0).getFechaSalida().isBefore(LocalDateTime.now()));
    }
    
    @Test
    public void testActualizarPasoPuntoControlCentroLogistico(){
        Paquete paquete = new Paquete(
                1.5f,
                50.0f,
                10.0f,
                15.0f);
        List<Paquete> paquetes = new ArrayList<>();
        paquetes.add(paquete);
        
        Cliente clRemitente = new Cliente(
                "12345678A",
                "Paco",
                "Perez",
                "Calle falsa",
                "Jaén",
                "999000111",
                "email@email.com");
        
        Cliente clDestinatario = new Cliente(
                "87654321B",
                "Maria",
                "Muñoz",
                "Calle verdadera",
                "Sevilla",
                "555666777",
                "gmail@gmail.com");
        
        Envio envio = servicioUjaPack.nuevoEnvio(paquetes, clRemitente, clDestinatario);
        
        servicioUjaPack.notificarCentroLogistico(TipoNotificacion.LLEGADA, 1, envio.getLocalizador());
        List<PasoPuntoControl> ruta = servicioUjaPack.buscarEnvio(envio.getLocalizador()).getRuta();
        
        Assertions.assertThat(!ruta.isEmpty());
        Assertions.assertThat(ruta.get(0).getFechaLlegada() != null);
        Assertions.assertThat(ruta.get(0).getFechaLlegada().isAfter(LocalDateTime.MIN));
        Assertions.assertThat(ruta.get(0).getFechaLlegada().isBefore(LocalDateTime.now()));
    }
    
    @BeforeEach
    void limpiarBaseDatos() {
        limpiadorBaseDatos.limpiar();
    }
    
}
