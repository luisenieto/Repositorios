/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idiomas.modelos;

import interfaces.IControladorPrincipal;
import interfaces.IGestorPublicaciones;
import interfaces.IGestorIdiomas;
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

public class GestorIdiomas implements IGestorIdiomas { 
    private final String NOMBRE_ARCHIVO = "./Idiomas.txt";
    //nombre del archivo con los idiomas
    
    private List<Idioma> idiomas = new ArrayList<>();
    private static GestorIdiomas gestor;
    
    private boolean problemasConArchivo;
    //sirve para saber si hubo o no problemas con el archivo
    
    /**
     * Constructor
    */                                            
    private GestorIdiomas() {    
        String resultado = this.leerArchivo();
        if ((resultado.equals(LECTURA_ERROR)) || (resultado.equals(CREACION_ERROR))) {
            JOptionPane.showMessageDialog(null, resultado, IControladorPrincipal.TITULO, JOptionPane.ERROR_MESSAGE);          
            this.problemasConArchivo = true;
        }
        else {
            this.problemasConArchivo = false;
        }
    }
    
    /**
     * Método estático que permite crear una única instancia de GestorIdiomas
     * @return GestorIdiomas
    */                                                            
    public static GestorIdiomas instanciar() {
        if (gestor == null) 
            gestor = new GestorIdiomas();            
        return gestor;
    } 
    
    @Override
    public String nuevoIdioma(String nombre) {
        if (this.problemasConArchivo)
            return PROBLEMAS_ES;
        
        if ((nombre == null) || (nombre.trim().isEmpty()))  //nombre del idioma no nulo y no vacío
            return ERROR_NOMBRE; 

        Idioma idioma = new Idioma(nombre);
        if (this.idiomas.contains(idioma))
            return IDIOMAS_DUPLICADOS; 
        
        this.idiomas.add(idioma); //no admite duplicados
        Collections.sort(this.idiomas);
        String resultado = this.escribirArchivo();
        return (resultado.equals(ESCRITURA_OK) ? EXITO : resultado);                
    }
   
    @Override
    public List<Idioma> buscarIdiomas(String nombre) {        
        List<Idioma> idiomasBuscados = new ArrayList<>();
        if (nombre == null)
            return idiomasBuscados;
        
        for(Idioma idioma : this.idiomas) {
            if (idioma.verNombre().toLowerCase().contains(nombre.toLowerCase().trim()))
                idiomasBuscados.add(idioma);
        }            
        return idiomasBuscados;        
    }
    
    @Override
    public List<Idioma> verIdiomas() {
        return this.idiomas;
    }
       
    @Override
    public String borrarIdioma(Idioma idioma) {
        if (!this.existeEsteIdioma(idioma))
            return IDIOMA_INEXISTENTE;
        
        if (this.problemasConArchivo)
            return PROBLEMAS_ES;
        
        IGestorPublicaciones gp = GestorPublicaciones.instanciar();
        if (gp.hayPublicacionesConEsteIdioma(idioma)) //hay al menos una publicación con este idioma
            return IDIOMA_CON_PUBLICACION;
        
        this.idiomas.remove(idioma);
        String resultado = this.escribirArchivo();
        return (resultado.equals(ESCRITURA_OK) ? EXITO : resultado);   
    }      
    
    @Override
    public Idioma verIdioma(String nombre) {
        if ((nombre == null) || (nombre.isEmpty()))
            return null;
        
        for(Idioma idioma : this.idiomas) {
            if (idioma.verNombre().equals(nombre))
                return idioma;
        }
        return null;
    }

    @Override
    public boolean existeEsteIdioma(Idioma idioma) {
        if (idioma == null)
            return false;
        
        for(Idioma i : this.idiomas) {
            if (i.equals(idioma))
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
     * @return String  - cadena con el resultado de la operacion (LECTURA_OK | LECTURA_ERROR | CREACION_OK | CREACION_ERROR)
     */
    private String leerArchivo() {
        File file = new File(NOMBRE_ARCHIVO);
        if (!file.exists()) 
            return this.crearArchivo();
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String nombre;
            while((nombre = br.readLine()) != null) {
                Idioma idioma = new Idioma(nombre);
                this.idiomas.add(idioma);
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
            for(Idioma idioma : this.idiomas) {
                bw.write(idioma.verNombre());
                bw.newLine();
            }            
            return ESCRITURA_OK;
        } 
        catch (IOException ioe) {
            return ESCRITURA_ERROR;            
        }
    }          
}
