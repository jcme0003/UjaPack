/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.servicios.seguridad;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Proveedor de datos de seguridad de UjaPack
 * @author Jose Carlos Mena
 */
@Configuration
public class ServicioSeguridadUjaPack extends WebSecurityConfigurerAdapter {
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("usuario").roles("USUARIO").password("{noop}usuario")
            .and()
            .withUser("admin").roles("ADMIN", "USUARIO").password("{noop}admin");
    }
    
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.httpBasic();
        
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/ujapack/").permitAll();
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/ujapack/envios/").hasRole("ADMIN");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/ujapack/envios/**").hasRole("ADMIN");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET, "/ujapack/envios/**").hasRole("USUARIO");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET, "/ujapack/puntoscontrol/**").hasRole("USUARIO");
    }
}
