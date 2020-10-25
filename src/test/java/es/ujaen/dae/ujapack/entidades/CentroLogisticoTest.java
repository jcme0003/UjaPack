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
 
        List<Oficina> oficinas = new ArrayList<>();
        oficinas.add(new Oficina("Jaén"));
        
        CentroLogistico clCastillaMancha = new CentroLogistico(2, "CL Castilla La Mancha", "Toledo", new ArrayList<>(), new ArrayList<>());
        List<CentroLogistico> conexiones = new ArrayList<>();
        conexiones.add(clCastillaMancha);
        
        CentroLogistico cl = new CentroLogistico(10, "CL Canarias", "Santa Cruz de Tenerife", oficinas, conexiones);
        
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<CentroLogistico>> violations = validator.validate(cl);
        
        Assertions.assertThat(violations).isEmpty();
    }
}
