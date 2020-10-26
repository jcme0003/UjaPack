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
import es.ujaen.dae.ujapack.objetosvalor.Paquete;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author Root
 */
@SpringBootTest(classes = es.ujaen.dae.ujapack.app.UjaPackApp.class)
public class ServicioUjaPackTest {
    
    @Autowired
    ServicioUjaPack servicioUjaPack;
    
    @Test
    public void testAccesoServicioUjaPack(){
        Assertions.assertThat(servicioUjaPack).isNotNull();
    }
    
    @Test
    public void testAltaClienteDuplicado() {
        Cliente cliente = new Cliente(
                            "1234S5678A",
                            "Paco",
                            "Perez",
                            "Calle falsa",
                            "Jaén",
                            "9990001ASD11",
                            "email@email.com");
        
        servicioUjaPack.altaCliente(cliente);
        Assertions.assertThatThrownBy(() -> {
            servicioUjaPack.altaCliente(cliente); })
                .isInstanceOf(ClienteYaRegistrado.class);
    }
    
    @Test
    public void testCargaDatosJSon(){
        servicioUjaPack.cargaDatosJSon("redujapack.json");
        Assertions.assertThat(servicioUjaPack.getCentrosLogisticos().size() == 10);
    }
    
    @Test
    public void testGeneraLocalizador(){
        int localizador = servicioUjaPack.generaLocalizador();
        Assertions.assertThat(Integer.toString(localizador).length() == 10);
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
        
        servicioUjaPack.cargaDatosJSon("redujapack.json");
        servicioUjaPack.nuevoEnvio(paquetes, clRemitente, clDestinatario);
        Assertions.assertThat(servicioUjaPack.getEnvios().size() == 1);
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
        
        servicioUjaPack.cargaDatosJSon("redujapack.json");
        servicioUjaPack.nuevoEnvio(paquetes, clRemitente, clDestinatario);
        
        Estado estado = Estado.ENTREGADO;
        
        for(Envio envio : servicioUjaPack.getEnvios().values()){
            estado = servicioUjaPack.consultarEstadoEnvio(envio.getLocalizador());
        }
        
        Assertions.assertThat(estado.equals(Estado.PENDIENTE));
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
        
        servicioUjaPack.cargaDatosJSon("redujapack.json");
        servicioUjaPack.nuevoEnvio(paquetes, clRemitente, clDestinatario);
        
        for(Envio envio : servicioUjaPack.getEnvios().values()){
            Assertions.assertThat(envio.getRuta().size() == 1);
        }
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
        
        servicioUjaPack.cargaDatosJSon("redujapack.json");
        servicioUjaPack.nuevoEnvio(paquetes, clRemitente, clDestinatario);
        
        for(Envio envio : servicioUjaPack.getEnvios().values()){
            Assertions.assertThat(envio.getRuta().size() == 3);
        }
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
        
        servicioUjaPack.cargaDatosJSon("redujapack.json");
        servicioUjaPack.nuevoEnvio(paquetes, clRemitente, clDestinatario);
        
        List<PasoPuntoControl> listado = new ArrayList<>();
        
        for(Envio envio : servicioUjaPack.getEnvios().values()){
            listado = servicioUjaPack.listarPuntosDeControlEnvio(envio.getLocalizador());
        }
        
        Assertions.assertThat(!listado.isEmpty());
    }
}
