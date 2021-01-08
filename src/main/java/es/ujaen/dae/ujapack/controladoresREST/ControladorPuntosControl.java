/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST;

import es.ujaen.dae.ujapack.controladoresREST.DTO.DTOCentroLogistico;
import es.ujaen.dae.ujapack.controladoresREST.DTO.DTOOficina;
import es.ujaen.dae.ujapack.excepciones.CentroLogisticoNoValido;
import es.ujaen.dae.ujapack.excepciones.ProvinciaNoValida;
import es.ujaen.dae.ujapack.servicios.ServicioUjaPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST de ujapack para los puntos de control
 * @author Jose Carlos Mena
 */
@RestController
@RequestMapping("/ujapack/puntoscontrol")
@CrossOrigin(origins = "*")
//(origins = "*", methods = {RequestMethod.GET,RequestMethod.POST})
public class ControladorPuntosControl {
    
    @Autowired
    ServicioUjaPack servicios;
    
    /** Handler para excepciones de accesos de centros logisticos no registrados */
    @ExceptionHandler(CentroLogisticoNoValido.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void HandlerCentroLogisticoNoValido(CentroLogisticoNoValido e){
    }
    
    /** Handler para excepciones de accesos de provincias no registradas */
    @ExceptionHandler(ProvinciaNoValida.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void HandlerProvinciaNoValida(ProvinciaNoValida e){
    }
    
    /** Obtener un centro logistico por su id de centro logistico
     * @param idCentro identificador del centro logistico
     * @return DTOCentroLogistico con los datos del centro logistico
     */
    @GetMapping("/centroslogisticos/{idCentro}")
    @ResponseStatus(HttpStatus.OK)
    public DTOCentroLogistico verCentroLogistico(@PathVariable int idCentro) {
        return new DTOCentroLogistico(servicios.buscaCentroLogistico(idCentro));
    }
    
    /** Obtener una oficina a través de una provincia
     * @param provincia nombre de la provincia a buscar
     * @return DTOOficina con los datos de la oficina
     */
    @GetMapping("/oficinas/{provincia}")
    @ResponseStatus(HttpStatus.OK)
    public DTOOficina verOficina(@PathVariable String provincia) {
        return new DTOOficina(servicios.buscaProvincia(provincia));
    }
}
