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
    
//    public static final String DNI = "\\d{8}[A-HJ-NP-TV-Z]";
//    public static final String TLF = "^(\\+34|0034|34)?[6789]\\d{8}$";
    public static final String DNI = "\\((([X-Z])|([LM])){1}([-]?)((\\d){7})([-]?)([A-Z]{1}))|((\\d{8})([-]?)([A-Z]))";
    public static final String TLF = "(\\+34|0034|34)?[ -](6|7)[ -]([0-9][ -]*){8}";

}
