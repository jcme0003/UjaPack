/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import es.ujaen.dae.ujapack.entidades.puntocontrol.Oficina;
import es.ujaen.dae.ujapack.objetosvalor.Paquete;
import java.util.ArrayList;
import java.util.List;
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
    public void testCalculaImporteTipo1(){
        
        Cliente cl1 = new Cliente("12345678A", "Paco", "Perez", "Calle falsa", "Jaén", "999000111", "email@email.com");
        Cliente cl2 = new Cliente("87654321B", "Maria", "Muñoz", "Calle verdadera", "Jaén", "555666777", "gmail@gmail.com");
        
        List<Paquete> paquetes = new ArrayList<>();
        paquetes.add(new Paquete(1.5f, 50.0f, 10.0f, 15.0f));
        
        Envio envio = new Envio(1234567890, cl1, cl2, paquetes);
        // Envio tipo 1
        List<PasoPuntoControl> listPPC = new ArrayList<>();
        listPPC.add(new PasoPuntoControl(new Oficina("Jaén")));
        envio.setRuta(listPPC);
        envio.calculaImporte();
        
        float importe = envio.getImporte();
        float result = 22.5f;
        // Formula: importe = peso(kg) * dim(cm2) * (num_puntos_control + 1) / 1000
        //assertEquals(importe, result);
        Assertions.assertThat(importe).isEqualTo(result);
        
    }
    
    @Test
    public void testCalculaImporteTipo2(){
        
        Cliente cl1 = new Cliente("12345678A", "Paco", "Perez", "Calle falsa", "Jaén", "999000111", "email@email.com");
        Cliente cl2 = new Cliente("87654321B", "Maria", "Muñoz", "Calle verdadera", "Sevilla", "555666777", "gmail@gmail.com");
        
        List<Paquete> paquetes = new ArrayList<>();
        paquetes.add(new Paquete(1.5f, 50.0f, 10.0f, 15.0f));
        
        Envio envio = new Envio(1234567890, cl1, cl2, paquetes);
        // Envio tipo 1
        List<PasoPuntoControl> listPPC = new ArrayList<>();
        listPPC.add(new PasoPuntoControl(new Oficina("Jaén")));
        listPPC.add(new PasoPuntoControl(new Oficina("Sevilla")));
        envio.setRuta(listPPC);
        envio.calculaImporte();
        
        float importe = envio.getImporte();
        float result = 45.0f;
        // Formula: importe = peso(kg) * dim(cm2) * (num_puntos_control + 1) / 1000
        //assertEquals(importe, result);
        Assertions.assertThat(importe).isEqualTo(result);
        
    }
    
    @Test
    public void testCalculaImporteTipo3(){
        
        Cliente cl1 = new Cliente("12345678A", "Paco", "Perez", "Calle falsa", "Jaén", "999000111", "email@email.com");
        Cliente cl2 = new Cliente("87654321B", "Maria", "Muñoz", "Calle verdadera", "Madrid", "555666777", "gmail@gmail.com");
        
        List<Paquete> paquetes = new ArrayList<>();
        paquetes.add(new Paquete(1.5f, 50.0f, 10.0f, 15.0f));
        
        Envio envio = new Envio(1234567890, cl1, cl2, paquetes);
        // Envio tipo 1
        List<PasoPuntoControl> listPPC = new ArrayList<>();
        listPPC.add(new PasoPuntoControl(new Oficina("Jaén")));
        listPPC.add(new PasoPuntoControl(new Oficina("Madrid")));
        envio.setRuta(listPPC);
        envio.calculaImporte();
        
        float importe = envio.getImporte();
        float result = 45.0f;
        // Formula: importe = peso(kg) * dim(cm2) * (num_puntos_control + 1) / 1000
        //assertEquals(importe, result);
        Assertions.assertThat(importe).isEqualTo(result);
        
    }
    
}
