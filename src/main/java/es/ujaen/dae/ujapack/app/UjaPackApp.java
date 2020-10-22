/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.app;

import es.ujaen.dae.ujapack.entidades.Cliente;
import es.ujaen.dae.ujapack.servicios.ServicioUjaPack;
import java.util.ArrayList;
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
        
        ServicioUjaPack servicio = context.getBean(ServicioUjaPack.class);
        servicio.cargaJSon("redujapack.json");
        servicio.listarCentrosLogisticos();
        
//        servicio.provinciasValidas("Jaén", "Madrid");
//        servicio.generaLocalizador();

        Cliente cl1 = new Cliente("12345678A", "Paco", "Perez", "Calle falsa", "Jaén", "999000111", "email@email.com");
        Cliente cl2 = new Cliente("87654321B", "Maria", "Muñoz", "Calle verdadera", "Jaén", "555666777", "gmail@gmail.com");
        
        servicio.nuevoEnvio(new ArrayList<>(), cl1, cl2);

    }
}
