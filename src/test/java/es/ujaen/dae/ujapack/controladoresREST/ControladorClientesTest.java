/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST;

import es.ujaen.dae.ujapack.controladoresREST.DTOs.ClienteDTo;
import org.junit.jupiter.api.Test;

/**
 *
 * @author joseo
 */
public class ControladorClientesTest {
    
    public ControladorClientesTest() {
    }

    @Test
    public void testControladorIniciado() {
        System.out.println("controladorIniciado");
        ControladorClientes instance = new ControladorClientes();
        instance.controladorIniciado();
        //fail("The test case is a prototype.");
    }

    @Test
    public void testAltaCliente() {
        System.out.println("AltaCliente");
        ClienteDTo cliente = null;
        //Object = null;
        ControladorClientes instance = new ControladorClientes();
        Object expResult = null;
        //Object result = instance.AltaCliente(cliente, <error>);
        //assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }
    
}
