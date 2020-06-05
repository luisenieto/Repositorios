/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugares.modelos;

import interfaces.IControladorPrincipal;
import interfaces.IGestorPublicaciones;
import interfaces.IGestorLugares;
import publicaciones.modelos.GestorPublicaciones;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;

public class GestorLugares implements IGestorLugares { 
    private final String NOMBRE_ARCHIVO = "./Lugares.txt";
    //nombre del archivo con los lugares de publicación
    
    private List<Lugar> lugares = new ArrayList<>();
    private static GestorLugares gestor;
    
    private boolean problemasConArchivo;
    //sirve para saber si hubo o no problemas con el archivo
    
    /**
     * Constructor
    */                                            
    private GestorLugares() {    
        String resultado = this.leerArchivo();
        if ((resultado.equals(LECTURA_ERROR)) || (resultado.equals(CREACION_ERROR))) {
            JOptionPane.showMessageDialog(null, resultado, IControladorPrincipal.TITULO, JOptionPane.ERROR_MESSAGE);          
            this.problemasConArchivo = true;
        }
        else
            this.problemasConArchivo = false;
    }
    
    /**
     * Método estático que permite crear una única instancia de GestorLugares
     * @return GestorLugares
    */                                                            
    public static GestorLugares instanciar() {
        if (gestor == null) 
            gestor = new GestorLugares();            
        return gestor;
    } 
    
    @Override
    public String nuevoLugar(String nombre) {
        if (this.problemasConArchivo)
            return PROBLEMAS_ES;
        
        if ((nombre == null) || (nombre.trim().isEmpty()))  //nombre del lugar no nulo y no vacío
            return ERROR_NOMBRE; 

        Lugar lugar = new Lugar(nombre);
        if (this.lugares.contains(lugar))
            return LUGARES_DUPLICADOS;
        
        this.lugares.add(lugar); //no admite duplicados
        Collections.sort(this.lugares);
        String resultado = this.escribirArchivo();
        return (resultado.equals(ESCRITURA_OK) ? EXITO : resultado);
    }
   
    @Override
    public List<Lugar> buscarLugares(String nombre) {        
        List<Lugar> lugaresBuscados = new ArrayList<>();
        if (nombre == null)
            return lugaresBuscados;
        
        for(Lugar lugar : this.lugares) {
            if (lugar.verNombre().toLowerCase().contains(nombre.toLowerCase().trim()))
                lugaresBuscados.add(lugar);
        } 
        return lugaresBuscados;
    }
    
    @Override
    public List<Lugar> verLugares() {
        return this.lugares;
    }
       
    @Override
    public String borrarLugar(Lugar lugar) {
        if (!this.existeEsteLugar(lugar)) 
            return LUGAR_INEXISTENTE;
        
        if (this.problemasConArchivo)
            return PROBLEMAS_ES;
        
        IGestorPublicaciones gp = GestorPublicaciones.instanciar();
        if (gp.hayPublicacionesConEsteLugar(lugar)) //hay al menos una publicación con este lugar
            return LUGAR_CON_PUBLICACION;
        
        this.lugares.remove(lugar);
        String resultado = this.escribirArchivo();
        return (resultado.equals(ESCRITURA_OK) ? EXITO : resultado); 
    }            
    
    @Override
    public Lugar verLugar(String nombre) {
        if ((nombre == null) || (nombre.isEmpty()))
            return null;
        
        for(Lugar lugar : this.lugares) {
            if (lugar.verNombre().equals(nombre))
                return lugar;
        }
        return null;        
    }

    @Override
    public boolean existeEsteLugar(Lugar lugar) {
        if (lugar == null)
            return false;
        
        for(Lugar l : this.lugares) {
            if (l.equals(lugar))
                return true;
        }
        return false;        
    }
                    
    /**
     * Lee del archivo de texto y carga el ArrayList empleando un try con recursos
     * https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
     * Formato del archivo:
     *  nombre1
     *  nombre2
     *  nombre3
     * @return String  - cadena con el resultado de la operacion (LECTURA_OK | LECTURA_ERROR | | CREACION_OK | CREACION_ERROR)
     */
    private String leerArchivo() {
        File file = new File(NOMBRE_ARCHIVO);
        if (!file.exists())
            return this.crearArchivo();
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String nombre;
            while((nombre = br.readLine()) != null) {
                Lugar lugar = new Lugar(nombre);
                this.lugares.add(lugar);
            }
            return LECTURA_OK;
        }
        catch (IOException ioe) {
            return LECTURA_ERROR;
        }
    }          
    
    /**
     * Crea el archivo
     * @return String  - cadena con el resultado de la operacion (CREACION_OK | CREACION_ERROR)
     */
    private String crearArchivo() {
        File file = new File(NOMBRE_ARCHIVO);
        try (FileWriter fw = new FileWriter(file)) {     
            return CREACION_OK;
        } 
        catch (IOException ioe) {
            return CREACION_ERROR;            
        }
    }
    
    /**
     * Escribe en el archivo de texto el ArrayList
     * Formato del archivo:
     *  nombre1
     *  nombre2
     *  nombre3
     * @return String  - cadena con el resultado de la operacion (ESCRITURA_OK | ESCRITURA_ERROR)
     */
    private String escribirArchivo() {
        File file = new File(NOMBRE_ARCHIVO);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {     
            for(Lugar lugar : this.lugares) {
                bw.write(lugar.verNombre());
                bw.newLine();
            }            
            return ESCRITURA_OK;
        } 
        catch (IOException ioe) {
            return ESCRITURA_ERROR;            
        }
    }          
}
