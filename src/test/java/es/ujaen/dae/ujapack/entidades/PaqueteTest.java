/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import es.ujaen.dae.ujapack.objetosvalor.Paquete;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Root
 */
public class PaqueteTest {
    public PaqueteTest(){
    }
    
    @Test
    void testValidacionPaquete() {
        
        Paquete paquete = new Paquete(
                1.5f,
                50.0f,
                10.0f,
                15.0f);
        
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Paquete>> violations = validator.validate(paquete);
        
        Assertions.assertThat(violations).isEmpty();
    }
}
