/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.repositorios;

import es.ujaen.dae.ujapack.entidades.Cliente;
import es.ujaen.dae.ujapack.entidades.Envio;
import es.ujaen.dae.ujapack.entidades.PasoPuntoControl;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ana
 */
@Repository
@Transactional
public class RepositorioEnvios {
    @PersistenceContext
    EntityManager em;
    
    public Optional<Envio> buscar(int localizador){
        return Optional.ofNullable(em.find(Envio.class, localizador));
    }
    
    public void guardar(Envio envio){
        em.persist(envio);
    }
    
    public Optional<Cliente> buscarCliente(String dni){
        return Optional.ofNullable(em.find(Cliente.class, dni));
    }
    
    public void guardarCliente(Cliente cliente){
        em.persist(cliente);
    }
    
    public void actualizar(Envio envio){ 
        em.merge(envio);
    }
    
//    public void actualizar (Envio envio){ 
//        em.persist(envio);
//        envio = em.merge(envio);
//        envio.actualizaEstadoEnvio();
//    }
    
    
//    public void nuevoPasoPuntoControl (Envio envio, PasoPuntoControl ppc){
//        em.persist(ppc);
//        
//        envio = em.merge(envio);
//        envio.nuevoPasoPuntoControl(ppc);
//    }
}
