/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import palabrasclaves.modelos.PalabraClave;
import java.util.List;

public interface IGestorPalabrasClaves {
    //Constantes para las operaciones de E/S
    public static final String LECTURA_ERROR = "Error al leer las palabras claves";
    public static final String LECTURA_OK = "Se pudieron leer las palabras claves";
    public static final String ESCRITURA_ERROR = "Error al guardar las palabras claves";
    public static final String ESCRITURA_OK = "Se pudieron guardar las palabras claves";  
    public static final String CREACION_ERROR = "Error al crear el archivo de palabras claves";
    public static final String CREACION_OK = "Se pudo crear el archivo de palabras claves";
    public static final String PROBLEMAS_ES = "No se puede realizar la operación por problemas con el archivo";    

    //Constantes para el ABM de palabras claves
    public static final String EXITO = "Palabra clave creada/borrada con éxito";
    public static final String ERROR_NOMBRE = "El nombre de la palabra clave no puede ser nulo";
    public static final String PALABRAS_CLAVES_DUPLICADAS = "Ya existe una palabra clave con ese nombre";
    public static final String PALABRA_CLAVE_CON_PUBLICACION = "No se puede borrar la palabra clave porque hay publicaciones con la misma";    
    public static final String PALABRA_CLAVE_INEXISTENTE = "No existe la palabra clave especificada";

    
    /**
     * Crea una nueva palabra clave
     * @param nombre nombre de la palabra clave
     * @return cadena con el resultado de la operación (EXITO | ESCRITURA_ERROR | PROBLEMAS_ES | PALABRAS_CLAVES_DUPLICADAS | ERROR_NOMBRE)
    */                                                                    
    public String nuevaPalabraClave(String nombre);
        
    /**
     * Borra una palabra clave siempre y cuando no haya publicaciones con la misma
     * @param palabraClave palabra clave a borrar
     * @return String  - cadena con el resultado de la operación (EXITO | ESCRITURA_ERROR | PROBLEMAS_ES | PALABRA_CLAVE_CON_PUBLICACION )
     */
    public String borrarPalabraClave(PalabraClave palabraClave);
    
    /**
     * Busca si existen palabras claves con el nombre especificado (total o parcialmente)
     * Este método es necesario para las clases ModeloTablaPalabrasClaves y ModeloComboPalabrasClaves
     * @param nombre nombre de la palabra clave 
     * @return List<PalabraClave>  - lista de palabras claves, ordenadas por nombre, cuyos nombres coincidan con el especificado
    */                                                                           
    public List<PalabraClave> buscarPalabrasClaves(String nombre);
    
    /**
     * Devuelve todas las palabras claves ordenadas por nombre
     * Este método es necesario para las clases ModeloTablaPalabrasClaves y ModeloComboPalabrasClaves
     * @return List<PalabraClave>  - lista de palabras claves ordenadas por nombre
    */                                                                           
    public List<PalabraClave> verPalabrasClaves(); 
    
    /**
     * Busca si existe una palabra clave cuyo nombre coincida con el especificado
     * Si existe una palabra clave con el nombre especificado, lo devuelve
     * Si no hay una palabra clave con el nombre especicado, devuelve null
     * @param nombre nombre de la palabra clave a buscar
     * @return PalabraClave  - palabra clave cuyo nombre coincida con el especificado, o null
     */
    public PalabraClave verPalabraClave(String nombre);
    
    /**
     * Devuelve true si existe la palabra clave especificada, false en caso contrario
     * @param palabraClave palabra clave a buscar
     * @return boolean  - true si existe la palabra clave especificada, false en caso contrario
     */
    public boolean existeEstaPalabraClave(PalabraClave palabraClave);
}
