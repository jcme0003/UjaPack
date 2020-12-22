/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import es.ujaen.dae.ujapack.entidades.puntocontrol.CentroLogistico;
import es.ujaen.dae.ujapack.entidades.puntocontrol.Oficina;
import es.ujaen.dae.ujapack.objetosvalor.Paquete;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 *
 * @author Root
 */
public class EnvioTest {
    
    public EnvioTest(){
        
    }
    
    @Test
    public void testValidacionEnvio() {
 
        Cliente cl1 = new Cliente("12345678A", "Paco", "Perez", "Calle falsa", "Jaén", "999000111", "email@email.com");
        Cliente cl2 = new Cliente("87654321B", "Maria", "Muñoz", "Calle verdadera", "Jaén", "555666777", "gmail@gmail.com");
        
        List<Paquete> paquetes = new ArrayList<>();
        paquetes.add(new Paquete(1.5f, 50.0f, 10.0f, 15.0f));
        
        Envio envio = new Envio(1234567890, cl1, cl2, paquetes);
        
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Envio>> violations = validator.validate(envio);
                
        Assertions.assertThat(violations).isEmpty();
    }
    
    @Test
    public void testCalculaImporteTipo1(){
        
        Cliente cl1 = new Cliente("12345678A", "Paco", "Perez", "Calle falsa", "Jaén", "999000111", "email@email.com");
        Cliente cl2 = new Cliente("87654321B", "Maria", "Muñoz", "Calle verdadera", "Jaén", "555666777", "gmail@gmail.com");
        
        List<Paquete> paquetes = new ArrayList<>();
        paquetes.add(new Paquete(1.5f, 50.0f, 10.0f, 15.0f));
        
        Envio envio = new Envio(1234567890, cl1, cl2, paquetes);
        
        List<PasoPuntoControl> listPPC = new ArrayList<>();
        listPPC.add(new PasoPuntoControl(envio.getLocalizador(), new Oficina("Jaén")));
        envio.setRuta(listPPC);
        envio.calculaImporte();
        float result = 22.5f;
        
        Assertions.assertThat(envio.getImporte()).isEqualTo(result);
    }
    
    @Test
    public void testCalculaImporteTipo2(){
        
        Cliente cl1 = new Cliente("12345678A", "Paco", "Perez", "Calle falsa", "Jaén", "999000111", "email@email.com");
        Cliente cl2 = new Cliente("87654321B", "Maria", "Muñoz", "Calle verdadera", "Sevilla", "555666777", "gmail@gmail.com");
        
        List<Paquete> paquetes = new ArrayList<>();
        paquetes.add(new Paquete(1.5f, 50.0f, 10.0f, 15.0f));
        
        Envio envio = new Envio(1234567890, cl1, cl2, paquetes);
        
        List<CentroLogistico> centro1 = new ArrayList<>();
        List<Oficina> of = new ArrayList<>();
        
        Oficina ofJaen = new Oficina("Jaén");
        Oficina ofSevilla = new Oficina("Sevilla");
        of.add(ofJaen);
        of.add(ofSevilla);
        CentroLogistico clAndalucia = new CentroLogistico(1, "CL Andalucía-Extremadura", "Sevilla", of, centro1);
        
        List<PasoPuntoControl> listPPC = new ArrayList<>();
        listPPC.add(new PasoPuntoControl(envio.getLocalizador(), ofJaen));
        listPPC.add(new PasoPuntoControl(envio.getLocalizador(), clAndalucia));
        listPPC.add(new PasoPuntoControl(envio.getLocalizador(), ofSevilla));
        envio.setRuta(listPPC);
        
        envio.calculaImporte();
        float result = 45.0f;
        
        Assertions.assertThat(envio.getImporte()).isEqualTo(result);
    }
    
    @Test
    public void testCalculaImporteTipo3(){
        
        Cliente cl1 = new Cliente("12345678A", "Paco", "Perez", "Calle falsa", "Jaén", "999000111", "email@email.com");
        Cliente cl2 = new Cliente("87654321B", "Maria", "Muñoz", "Calle verdadera", "Madrid", "555666777", "gmail@gmail.com");
        
        List<Paquete> paquetes = new ArrayList<>();
        paquetes.add(new Paquete(1.5f, 50.0f, 10.0f, 15.0f));
        
        Envio envio = new Envio(1234567890, cl1, cl2, paquetes);
        
        List<CentroLogistico> centros = new ArrayList<>();
        List<Oficina> of = new ArrayList<>();
        Oficina ofJaen = new Oficina("Jaén");
        of.add(ofJaen);
        CentroLogistico clAndalucia = new CentroLogistico(1, "CL Andalucía-Extremadura", "Sevilla", of, centros);
        
        
        List<Oficina> of1 = new ArrayList<>();
        Oficina ofMadrid = new Oficina("Madrid");
        of1.add(ofMadrid);
        CentroLogistico clMadrid = new CentroLogistico(9, "CL Madrid", "Madrid", of1, centros);
        
        
        List<Oficina> of2 = new ArrayList<>();
        Oficina ofToledo = new Oficina("Toledo");
        of2.add(ofToledo);
        CentroLogistico clCastillaMancha = new CentroLogistico(2, "CL Castilla La Mancha", "Toledo", of2, centros);
        
        
        List<PasoPuntoControl> listPPC = new ArrayList<>();
        listPPC.add(new PasoPuntoControl(envio.getLocalizador(), ofJaen));
        listPPC.add(new PasoPuntoControl(envio.getLocalizador(), clAndalucia));
        listPPC.add(new PasoPuntoControl(envio.getLocalizador(), clCastillaMancha));
        listPPC.add(new PasoPuntoControl(envio.getLocalizador(), clMadrid));
        listPPC.add(new PasoPuntoControl(envio.getLocalizador(), ofMadrid));
        
        envio.setRuta(listPPC);
        envio.calculaImporte();
        
        float result = 67.5f;
        
        Assertions.assertThat(envio.getImporte()).isEqualTo(result);
    }
    
}
