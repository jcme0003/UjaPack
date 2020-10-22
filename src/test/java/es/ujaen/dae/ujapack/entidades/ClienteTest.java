/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

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
public class ClienteTest {
     public ClienteTest() {
    }

    @Test
    void testValidacionCliente() {
 
        Cliente cliente = new Cliente(
                "45789632M", 
                "Jose",
                "Bermudez Carvajal", 
                "Calle La Luna",
                "Jaén",
                "953010203",
                "jbc@gmail.com");
               
        
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
                
        Assertions.assertThat(violations).isEmpty();
    }

}
