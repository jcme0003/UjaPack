/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.repositorios;

import es.ujaen.dae.ujapack.entidades.Cliente;
import es.ujaen.dae.ujapack.entidades.Envio;
import es.ujaen.dae.ujapack.objetosvalor.Paquete;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repositorio de entidades Envio, Cliente, Paquete
 * @author Ana
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class RepositorioEnvios {
    @PersistenceContext
    EntityManager em;
    
    @Transactional(propagation=Propagation.SUPPORTS, readOnly = true)
    public Optional<Envio> buscarEnvio(int localizador){
        return Optional.ofNullable(em.find(Envio.class, localizador));
    }
    
    public void guardarEnvio(Envio envio){
        em.persist(envio);
    }
    
    @Transactional(propagation=Propagation.SUPPORTS, readOnly = true)
    public Optional<Cliente> buscarCliente(String dni){
        return Optional.ofNullable(em.find(Cliente.class, dni));
    }
    
    public void guardarCliente(Cliente cliente){
        em.persist(cliente);
    }
    
    public void actualizarEnvio(Envio envio){ 
        em.merge(envio);
    }
    
    public void guardarPaquete(Paquete paquete){
        em.persist(paquete);
    }
}
