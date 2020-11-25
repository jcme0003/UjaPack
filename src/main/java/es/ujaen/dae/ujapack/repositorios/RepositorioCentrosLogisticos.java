/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.repositorios;

import es.ujaen.dae.ujapack.entidades.puntocontrol.CentroLogistico;
import es.ujaen.dae.ujapack.entidades.puntocontrol.Oficina;
import es.ujaen.dae.ujapack.entidades.puntocontrol.PuntoControl;
import es.ujaen.dae.ujapack.excepciones.JSonNoEncontrado;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ana
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class RepositorioCentrosLogisticos {
    @PersistenceContext
    EntityManager em;
    
    @Transactional(propagation=Propagation.SUPPORTS, readOnly = true)
    public Optional<CentroLogistico> buscarCL(int idCentro){
        return Optional.ofNullable(em.find(CentroLogistico.class, idCentro));
    }
    
    public void guardarCL(CentroLogistico cl){
        em.persist(cl);
    }
    
    public void actualizarCL(CentroLogistico cl){
        em.merge(cl);
    }
    
    public Optional<Oficina> buscarOf(String nombreProvincia){
        return Optional.ofNullable(em.find(Oficina.class, nombreProvincia));
    }
    
    public void guardarOf(Oficina oficina){
        em.persist(oficina);
    }
    
    public Optional<PuntoControl> buscarPC(int idCentro){
        return Optional.ofNullable(em.find(PuntoControl.class, idCentro));
    }
    
    public void guardarPC(PuntoControl pc){
        em.persist(pc);
    }
    
}
