/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;

/**
 * Clase principal
 * @author Jose Carlos Mena
 */
@SpringBootApplication(scanBasePackages={"es.ujaen.dae.ujapack.servicios", "es.ujaen.dae.ujapack.repositorios","es.ujaen.dae.ujapack.controladoresREST", "es.ujaen.dae.ujapack.servicios.seguridad"})
@EntityScan(basePackages={"es.ujaen.dae.ujapack.entidades", "es.ujaen.dae.ujapack.objetosvalor"})
public class UjaPackApp {
    public static void main(String[] args) throws Exception {
        // Creación de servidor
        SpringApplication servidor = new SpringApplication(UjaPackApp.class);
        ApplicationContext context = servidor.run(args);
    }
}
