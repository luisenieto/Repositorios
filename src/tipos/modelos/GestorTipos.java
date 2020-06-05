/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tipos.modelos;

import interfaces.IControladorPrincipal;
import interfaces.IGestorPublicaciones;
import interfaces.IGestorTipos;
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

public class GestorTipos implements IGestorTipos { 
    private final String NOMBRE_ARCHIVO = "./Tipos.txt";
    //nombre del archivo con los tipos de publicación
    
    private List<Tipo> tipos = new ArrayList<>();
    private static GestorTipos gestor;
    
    private boolean problemasConArchivo;
    //sirve para saber si hubo o no problemas con el archivo
    
    /**
     * Constructor
    */                                            
    private GestorTipos() {    
        String resultado = this.leerArchivo();
        if ((resultado.equals(LECTURA_ERROR)) || (resultado.equals(CREACION_ERROR))) {
            JOptionPane.showOptionDialog(null, resultado, IControladorPrincipal.TITULO, JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null, new Object[] {"Aceptar"}, this);       
            this.problemasConArchivo = true;
        }
        else
            this.problemasConArchivo = false;
    }
    
    /**
     * Método estático que permite crear una única instancia de GestorTipos
     * @return GestorTipos
    */                                                            
    public static GestorTipos instanciar() {
        if (gestor == null) 
            gestor = new GestorTipos();            
        return gestor;
    } 
                                                                            
    @Override
    public String nuevoTipo(String nombre) {
        if (this.problemasConArchivo) 
            return PROBLEMAS_ES;
        
        if ((nombre == null) || (nombre.trim().isEmpty()))  //nombre del tipo no nulo y no vacío
            return ERROR_NOMBRE; 

        Tipo tipo = new Tipo(nombre);
        if (this.tipos.contains(tipo))
            return TIPOS_DUPLICADOS; 
        
        this.tipos.add(tipo); //no admite duplicados
        Collections.sort(this.tipos);
        String resultado = this.escribirArchivo();
        return (resultado.equals(ESCRITURA_OK) ? EXITO : resultado);
    }
   
    @Override
    public List<Tipo> buscarTipos(String nombre) {        
        List<Tipo> tiposBuscados = new ArrayList<>();
        if (nombre == null)
            return tiposBuscados;
        
        for(Tipo tipo : this.tipos) {
            if (tipo.verNombre().toLowerCase().contains(nombre.toLowerCase().trim()))
                tiposBuscados.add(tipo);
        }
        return tiposBuscados;
    }
    
    @Override
    public List<Tipo> verTipos() {
        return this.tipos;
    }
       
    @Override
    public String borrarTipo(Tipo tipo) {
        if (!this.existeEsteTipo(tipo))
            return TIPO_INEXISTENTE;
        
        if (this.problemasConArchivo)
            return PROBLEMAS_ES; 
        
        IGestorPublicaciones gp = GestorPublicaciones.instanciar();
        if (gp.hayPublicacionesConEsteTipo(tipo)) //hay al menos una publicación con este tipo de publicación
            return TIPO_CON_PUBLICACION;
        
        this.tipos.remove(tipo);
        String resultado = this.escribirArchivo();
        return (resultado.equals(ESCRITURA_OK) ? EXITO : resultado);      
    }     
    
    @Override
    public Tipo verTipo(String nombre) {
        if ((nombre == null) || (nombre.isEmpty()))
            return null;
        
        for(Tipo tipo : this.tipos) {
            if (tipo.verNombre().equalsIgnoreCase(nombre.trim()))
                return tipo;
        }
        return null;
    }    

    @Override
    public boolean existeEsteTipo(Tipo tipo) {
        if (tipo == null)
            return false;
       
        for(Tipo t : this.tipos) {
            if (t.equals(tipo))
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
                Tipo tipo = new Tipo(nombre);
                this.tipos.add(tipo);
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
            for(Tipo tipo : this.tipos) {
                bw.write(tipo.verNombre());
                bw.newLine();
            }            
            return ESCRITURA_OK;
        } 
        catch (IOException ioe) {
            return ESCRITURA_ERROR;            
        }
    }          
}
