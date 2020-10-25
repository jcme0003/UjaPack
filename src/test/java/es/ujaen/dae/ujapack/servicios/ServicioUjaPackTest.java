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
import java.util.Set;
import javax.validation.ConstraintViolation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Validator;
import javax.validation.Validation;
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
        List<Oficina> of1 = new ArrayList<Oficina>();
        of1.add(new Oficina("Santa Cruz de Tenerife"));
        
        List<Oficina> of2 = new ArrayList<Oficina>();
        of2.add(new Oficina("Alava"));
        
        List<CentroLogistico> centro = new ArrayList<CentroLogistico>();
        CentroLogistico pru1 = new CentroLogistico(10, "CL Noruega", "Santa Cruz de Brasil", of1, centro);
        centro.add(pru1);
        CentroLogistico pru2 = new CentroLogistico(7, "CL Pais Vasco-Cantabria", "Vitoria", of2, centro);
        centro.add(pru2);
        
        
       //Listar centros logisticos y saber si pru1 y pru2 existen dentro de esa lista        
//        for(CentroLogistico centro2: centrosLogisticos.values()){
//            if(servicioUjaPack.setCentrosLogisticos(centrosLogisticos))
//        }
        //servicioUjaPack.calculaRuta("Santa Cruz de Tenerife", "Alava");
        

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<CentroLogistico>> violations1 = validator.validate(pru1);
        Set<ConstraintViolation<CentroLogistico>> violations2 = validator.validate(pru2);        
        
        org.assertj.core.api.Assertions.assertThat(violations1).isEmpty();
        org.assertj.core.api.Assertions.assertThat(violations2).isEmpty();
            
            
        
        
        
        
    }
    
}
