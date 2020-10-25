/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.servicios;

import es.ujaen.dae.ujapack.entidades.puntocontrol.CentroLogistico;
import es.ujaen.dae.ujapack.entidades.puntocontrol.Oficina;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
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
        //Assertions.assertThat(servicioUjaPack).isNotNull();
    }
    
    @Test
    public void testcalculaRuta(){
        List<Oficina> of = new ArrayList<Oficina>();
        List<CentroLogistico> centro = new ArrayList<CentroLogistico>();
    }
    
}
