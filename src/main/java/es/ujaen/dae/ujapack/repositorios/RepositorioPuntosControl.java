/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.repositorios;

import es.ujaen.dae.ujapack.entidades.puntocontrol.CentroLogistico;
import es.ujaen.dae.ujapack.entidades.puntocontrol.Oficina;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repositorio de entidades CentroLogistico, Oficina, PuntoControl, PasoPuntoControl
 * @author Ana, Jose Carlos Mena
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class RepositorioPuntosControl {
    @PersistenceContext
    EntityManager em;
    
    @Transactional(propagation=Propagation.SUPPORTS, readOnly = true)
    public Optional<CentroLogistico> buscarCLId(int id){
        return Optional.ofNullable(em.find(CentroLogistico.class, id));
    }
    
    @Transactional(propagation=Propagation.SUPPORTS, readOnly = true)
    public Optional<CentroLogistico> buscarCLIdCentro(int idCentro){
        List<CentroLogistico> centrosLogisticos;
        centrosLogisticos = em.createQuery("Select c from CentroLogistico c where c.idCentro = :idCentro", CentroLogistico.class)
                .setParameter("idCentro", idCentro)
                .getResultList();

        return centrosLogisticos.isEmpty() ? Optional.empty() : Optional.of(centrosLogisticos.get(0));
    }
    
    public void guardarCL(CentroLogistico cl){
        em.persist(cl);
    }
    
    public void actualizarCL(CentroLogistico cl){
        em.merge(cl);
    }
    
    @Transactional(propagation=Propagation.SUPPORTS, readOnly = true)
    public Optional<Oficina> buscarOf(String provincia){
        List<Oficina> oficinas;
        oficinas = em.createQuery(
                "SELECT o FROM Oficina o WHERE o.nombreProvincia = :nombreProvincia", Oficina.class)
                .setParameter("nombreProvincia", provincia)
                .getResultList();

        return oficinas.isEmpty() ? Optional.empty() : Optional.of(oficinas.get(0));
    }
    
    @Transactional(propagation=Propagation.SUPPORTS, readOnly = true)
    public Optional<Oficina> buscarOf(int idOficina){
        return Optional.ofNullable(em.find(Oficina.class, idOficina));
    }
    
    public void guardarOf(Oficina oficina){
        em.persist(oficina);
    }
    
}
