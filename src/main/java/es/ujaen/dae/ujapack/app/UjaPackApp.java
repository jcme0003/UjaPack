/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.app;

import es.ujaen.dae.ujapack.entidades.Cliente;
import es.ujaen.dae.ujapack.entidades.Envio;
import es.ujaen.dae.ujapack.entidades.PasoPuntoControl;
import es.ujaen.dae.ujapack.objetosvalor.Paquete;
import es.ujaen.dae.ujapack.servicios.ServicioJSon;
import es.ujaen.dae.ujapack.servicios.ServicioUjaPack;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Root
 */
@SpringBootApplication(scanBasePackages="es.ujaen.dae.ujapack.servicios")
public class UjaPackApp {
    public static void main(String[] args) throws Exception {
        // Creación de servidor
        SpringApplication servidor = new SpringApplication(UjaPackApp.class);
        ApplicationContext context = servidor.run(args);
        
//        ServicioJSon servicioJSon = new ServicioJSon();
//        servicioJSon.cargaJSon("redujapack.json");
//        servicioJSon.cargaConexiones("redujapack.json");
        
//        Profundidad profundidad = new Profundidad();
//        profundidad.generaNodos(servicioJSon.getCentrosLogisticos());
        
        ServicioUjaPack servicio = context.getBean(ServicioUjaPack.class);
//        servicio.setCentrosLogisticos(servicioJSon.getCentrosLogisticos());
        servicio.cargaDatosJSon("redujapack.json");
        
        
//        servicio.cargaJSon("redujapack.json");
        servicio.listarCentrosLogisticos();
        
//        servicio.provinciasValidas("Jaén", "Madrid");
//        servicio.generaLocalizador();

        Cliente cl1 = new Cliente("12345678A", "Paco", "Perez", "Calle falsa", "Jaén", "999000111", "email@email.com");
        Cliente cl2 = new Cliente("87654321B", "Maria", "Muñoz", "Calle verdadera", "Sevilla", "555666777", "gmail@gmail.com");
        
        List<Paquete> paquetes = new ArrayList<>();
        paquetes.add(new Paquete(1.5f, 50.0f, 10.0f, 15.0f));
        
        servicio.nuevoEnvio(paquetes, cl1, cl2);
        
        
        
        
        Iterator it = servicio.getEnvios().entrySet().iterator();
        Map.Entry e;
        Envio envio;
        while(it.hasNext()){
            e = (Map.Entry<Integer, Envio>)it.next();
            envio = servicio.getEnvios().get(e.getKey());
            envio.actualizaEstadoEnvio();
            System.out.println(envio.getEstado());
            
            envio.getRuta().get(0).setFechaLlegada(LocalDate.now());
            envio.getRuta().get(0).setFechaSalida(LocalDate.now());
            
            for(PasoPuntoControl ppc : envio.getRuta()){
                System.out.println(ppc.getPuntoDeControl().getIdCentro());
            }
            
            envio.actualizaEstadoEnvio();
            System.out.println(envio.getEstado());
        }
        
        
    }
}
