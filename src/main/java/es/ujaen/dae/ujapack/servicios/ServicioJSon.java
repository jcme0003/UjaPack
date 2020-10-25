/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.servicios;

import es.ujaen.dae.ujapack.entidades.puntocontrol.CentroLogistico;
import es.ujaen.dae.ujapack.entidades.puntocontrol.Oficina;
import es.ujaen.dae.ujapack.util.profundidad.Nodo;
import es.ujaen.dae.ujapack.util.profundidad.Profundidad;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Root
 */
public class ServicioJSon {
    /** Centros logisticos */
    private final Map<Integer, CentroLogistico> centrosLogisticos = new TreeMap<>();
    
//    Profundidad profundidad = new Profundidad();
    
    /**
     * Constructor por defecto de ServicioJson
     */
    public ServicioJSon(){
        
    }

    /**
     * @return the centrosLogisticos
     */
    public Map<Integer, CentroLogistico> getCentrosLogisticos() {
        return centrosLogisticos;
    }
    
    /**
     * Carga archivo JSon que contiene los centros logisticos, oficinas y conexiones
     * @param url url donde esta guardado el fichero JSon
     */
    public void cargaJSon(String url){
        String data = JSonToString(url);
        
        JSONObject obj = new JSONObject(data);
        JSONObject elem;
        JSONArray arr;

        String id;
        String nombre;
        String localizacion;
        String provincia;
        List<String> provincias = new ArrayList<>();

        // Cargamos datos de cada identificador del Json "1", "2", ... "10"
        for(int i = 1; i <= 10; i++){
            id = Integer.toString(i);
            elem = obj.getJSONObject(id);

            nombre = elem.getString("nombre");
            localizacion = elem.getString("localización");
            // Oficinas correspondientes a este centro logistico
            List<Oficina> oficinas = new ArrayList<>();
            // Cargamos las provincias correspondientes al identificador actual i
            arr = elem.getJSONArray("provincias");
            for(int j = 0; j < arr.length(); j++){
                provincia = arr.getString(j);
                // Añadir a array de provincias y crear Oficina (provincia) si no esta creada
                if(!provincias.contains(arr.getString(j))){
                    provincias.add(provincia);
                    oficinas.add(new Oficina(provincia));
                }
            }

            // Creamos nuestro centro logistico y lo añadir a la lista de centros logisticos del sistema
            CentroLogistico centroLogistico = new CentroLogistico(Integer.parseInt(id), nombre, localizacion, oficinas, new ArrayList<>());
            this.getCentrosLogisticos().put(parseInt(id), centroLogistico);
            
//            this.profundidad.setCentroLogistico(centroLogistico);
        }
        
    }
    
    /**
     * 
     * @param url 
     */
    public void cargaConexiones(String url){
        String data = JSonToString(url);

        JSONObject obj = new JSONObject(data);
        JSONObject elem;
        JSONArray arr;

        String id;
        int conexion;

        // Cargamos datos de cada identificador del Json "1", "2", ... "10"
        for(int i = 1; i <= 10; i++){
            id = Integer.toString(i);
            elem = obj.getJSONObject(id);

            // Cargamos las conexiones correspondientes al identificador actual i
            arr = elem.getJSONArray("conexiones");
            for(int j = 0; j < arr.length(); j++){
                conexion = arr.getInt(j);
                this.centrosLogisticos.get(i).setConexion(this.centrosLogisticos.get(conexion));
            }
        }
    }
    
    /**
     * Convierte archivo JSon en String
     * @param url
     * @return 
     */
    public String JSonToString(String url){
        String data = null;
        
        try{
            // Carga y almacena en un string el archivo json
            File file = new File(url);
            
            try (Scanner sc = new Scanner(new FileInputStream(file), "UTF-8")) {
                data = new String();
                while(sc.hasNextLine()){
                    data += sc.nextLine();
                }
            }
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        
        return data;
    }
    
}
