/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.util;

/**
 *
 * @author Root
 */
public class ExprReg {
    private ExprReg(){
        
    }
    
    public static final String DNI = "\\d{8}[A-HJ-NP-TV-Z]";
    public static final String TLF = "^(\\+34|0034|34)?[6789]\\d{8}$";
}
