/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import es.ujaen.dae.ujapack.entidades.puntocontrol.Oficina;
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
public class OficinaTest {
    public OficinaTest(){
        
    }
    
    @Test
    void testValidacionOficina() {
 
        Oficina of = new Oficina(
                "Jaén");

        
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Oficina>> violations = validator.validate(of);
                
        Assertions.assertThat(violations).isEmpty();
    }
}
