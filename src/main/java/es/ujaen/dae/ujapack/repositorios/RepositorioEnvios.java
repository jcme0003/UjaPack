/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.repositorios;

import es.ujaen.dae.ujapack.entidades.Cliente;
import es.ujaen.dae.ujapack.entidades.Envio;
import es.ujaen.dae.ujapack.entidades.PasoPuntoControl;
import es.ujaen.dae.ujapack.excepciones.EnvioNoEncontrado;
import es.ujaen.dae.ujapack.excepciones.PasoPuntoControlNoEncontrado;
import es.ujaen.dae.ujapack.excepciones.RutaNoEncontrada;
import es.ujaen.dae.ujapack.objetosvalor.Paquete;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
    
    public void guardarPuntoControl(PasoPuntoControl pasoPuntoControl){
        em.persist(pasoPuntoControl);
    }
    
    public void actualizarPuntoControl(PasoPuntoControl pasoPuntoControl){ 
        em.merge(pasoPuntoControl);
    }
    
    @Transactional
    public Optional<List<PasoPuntoControl>> buscarRutaEnvio(int localizador, int idPuntoControl){
        List<PasoPuntoControl> ruta;
        ruta = em.createQuery(
                "SELECT ppc FROM PasoPuntoControl ppc WHERE ppc.localizador = '" + localizador + "' and ppc.puntoDeControl = '" + idPuntoControl + "'",
                PasoPuntoControl.class
        ).getResultList();

        return ruta.isEmpty() ? Optional.empty() : Optional.of(ruta);
    }
    
    public void actualizarPasoPuntoControl(PasoPuntoControl ppc){ 
        em.merge(ppc);
    }
    
//    public void guardarRuta(List<PasoPuntoControl> ruta){
//        for(PasoPuntoControl pasoPuntoControl : ruta){
//            em.persist(pasoPuntoControl);
//        }
//    }
    
//    @Transactional(propagation=Propagation.SUPPORTS, readOnly = true)
//    public Optional<List<PasoPuntoControl>> buscarRuta(int localizador){
//        try {
//            List<PasoPuntoControl> ruta;
//            ruta = em.createQuery(
//                    "SELECT ppc FROM PasoPuntoControl ppc WHERE ppc.envioLocalizador = '" + localizador + "'",
//                    PasoPuntoControl.class
//            ).getResultList();
//        
//            return Optional.ofNullable(ruta);
//        } catch(IndexOutOfBoundsException e){
//            throw new RutaNoEncontrada();
//        }
//    }
    
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
