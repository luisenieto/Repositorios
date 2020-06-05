/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palabrasclaves.modelos;

import interfaces.IControladorPrincipal;
import interfaces.IGestorPalabrasClaves;
import interfaces.IGestorPublicaciones;
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

public class GestorPalabrasClaves implements IGestorPalabrasClaves { 
    private final String NOMBRE_ARCHIVO = "./PalabrasClaves.txt";
    //nombre del archivo con las palabras claves    
    
    private List<PalabraClave> palabrasClaves = new ArrayList<>();
    private static GestorPalabrasClaves gestor;
    
    private boolean problemasConArchivo;
    //sirve para saber si hubo o no problemas con el archivo
    
    /**
     * Constructor
    */                                            
    private GestorPalabrasClaves() {    
        String resultado = this.leerArchivo();
        if ((resultado.equals(LECTURA_ERROR)) || (resultado.equals(CREACION_ERROR))) {
            JOptionPane.showMessageDialog(null, resultado, IControladorPrincipal.TITULO, JOptionPane.ERROR_MESSAGE);          
            this.problemasConArchivo = true;
        }
        else
            this.problemasConArchivo = false;
    }
    
    /**
     * Método estático que permite crear una única instancia de GestorPalabrasClaves
     * @return GestorPalabrasClaves
    */                                                            
    public static GestorPalabrasClaves instanciar() {
        if (gestor == null) 
            gestor = new GestorPalabrasClaves();            
        return gestor;
    } 
    
    @Override
    public String nuevaPalabraClave(String nombre) {
        if (this.problemasConArchivo)
            return PROBLEMAS_ES;
            
        if ((nombre == null) || (nombre.trim().isEmpty()))  //nombre de la palabra clave no nulo y no vacío
            return ERROR_NOMBRE; 

        PalabraClave palabraClave = new PalabraClave(nombre);
        if (this.palabrasClaves.contains(palabraClave))
            return PALABRAS_CLAVES_DUPLICADAS;  
        
        this.palabrasClaves.add(palabraClave); //no admite duplicados
        Collections.sort(this.palabrasClaves);
        String resultado = this.escribirArchivo();
        return (resultado.equals(ESCRITURA_OK) ? EXITO : resultado);
    }
   
    @Override
    public List<PalabraClave> buscarPalabrasClaves(String nombre) {        
        List<PalabraClave> palabrasClavesBuscadas = new ArrayList<>();
        if (nombre == null)
            return palabrasClavesBuscadas;
        
        for(PalabraClave palabraClave : this.palabrasClaves) {
            if (palabraClave.verNombre().toLowerCase().contains(nombre.toLowerCase().trim()))
                palabrasClavesBuscadas.add(palabraClave);
        }   
        return palabrasClavesBuscadas;
    }
    
    @Override
    public List<PalabraClave> verPalabrasClaves() {
        return this.palabrasClaves;
    }
        
    @Override
    public String borrarPalabraClave(PalabraClave palabraClave) {
        if (!this.existeEstaPalabraClave(palabraClave)) 
            return PALABRA_CLAVE_INEXISTENTE;
        
        if (this.problemasConArchivo) 
            return PROBLEMAS_ES;
        
        IGestorPublicaciones gp = GestorPublicaciones.instanciar();
        if (gp.hayPublicacionesConEstaPalabraClave(palabraClave)) //hay al menos una publicación con esta palabra clave
            return PALABRA_CLAVE_CON_PUBLICACION;
        
        this.palabrasClaves.remove(palabraClave);
        String resultado = this.escribirArchivo();
        return (resultado.equals(ESCRITURA_OK) ? EXITO : resultado);  
    }         
    
    @Override
    public PalabraClave verPalabraClave(String nombre) {
        if ((nombre == null) || (nombre.isEmpty()))
            return null;
        
        for(PalabraClave palabraClave : this.palabrasClaves) {
            if (palabraClave.verNombre().equals(nombre))
                return palabraClave;
        }
        return null;        
    }

    @Override
    public boolean existeEstaPalabraClave(PalabraClave palabraClave) {
        if (palabraClave == null)
            return false;
        
        for(PalabraClave pc : this.palabrasClaves) {
            if (pc.equals(palabraClave))
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
                PalabraClave palabraClave = new PalabraClave(nombre);
                this.palabrasClaves.add(palabraClave);
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
            for(PalabraClave palabraClave : this.palabrasClaves) {
                bw.write(palabraClave.verNombre());
                bw.newLine();
            }            
            return ESCRITURA_OK;
        } 
        catch (IOException ioe) {
            return ESCRITURA_ERROR;            
        }
    }          
}
