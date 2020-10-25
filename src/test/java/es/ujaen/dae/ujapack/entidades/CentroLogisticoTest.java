/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import es.ujaen.dae.ujapack.entidades.puntocontrol.CentroLogistico;
import es.ujaen.dae.ujapack.entidades.puntocontrol.Oficina;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Ana
 */
public class CentroLogisticoTest {
    public CentroLogisticoTest() {
    }

    @Test
    void testValidacionCentroLogistico() {
 
        List<Oficina> of = new ArrayList<Oficina>();
        of.add(new Oficina("Jaén"));
        
        List<Oficina> of1 = new ArrayList<Oficina>();
        of.add(new Oficina("Madrid"));
        
        List<Oficina> of2 = new ArrayList<Oficina>();
        of.add(new Oficina("Santa Cruz de Tenerife"));
        
       
        List<CentroLogistico> centro = new ArrayList<CentroLogistico>();
        List<CentroLogistico> centro1 = new ArrayList<CentroLogistico>();
        List<CentroLogistico> centro2 = new ArrayList<CentroLogistico>();
        CentroLogistico pru2 = new CentroLogistico(10, "CL Canarias", "Santa Cruz de Tenerife", of2, centro);
        centro2.add(pru2);
        CentroLogistico pru1 = new CentroLogistico(9, "CL Madrid", "Madrid", of1, centro2);
        centro1.add(pru1);
        CentroLogistico pru = new CentroLogistico(1, "CL Andalucía-Extremadura", "Sevilla", of, centro1);
        centro.add(pru);
 
        pru2.setConexion(pru);
       
        
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<CentroLogistico>> violations = validator.validate(pru);
        Set<ConstraintViolation<CentroLogistico>> violations1 = validator.validate(pru1);
        Set<ConstraintViolation<CentroLogistico>> violations2 = validator.validate(pru2);        
        
        Assertions.assertThat(violations).isEmpty();
        Assertions.assertThat(violations1).isEmpty();
        Assertions.assertThat(violations2).isEmpty();
    }
}
