/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import idiomas.modelos.Idioma;
import java.util.List;

public interface IGestorIdiomas {
    //Constantes para las operaciones de E/S
    public static final String LECTURA_ERROR = "Error al leer los idiomas";
    public static final String LECTURA_OK = "Se pudieron leer los idiomas";
    public static final String ESCRITURA_ERROR = "Error al guardar los idiomas";
    public static final String ESCRITURA_OK = "Se pudieron guardar los idiomas";  
    public static final String CREACION_ERROR = "Error al crear el archivo de idiomas";
    public static final String CREACION_OK = "Se pudo crear el archivo de idiomas";
    public static final String PROBLEMAS_ES = "No se puede realizar la operación por problemas con el archivo";    

    //Constantes para el ABM de idiomas
    public static final String EXITO = "Idioma creado/borrado con éxito";
    public static final String ERROR_NOMBRE = "El nombre del idioma no puede ser nulo";
    public static final String IDIOMAS_DUPLICADOS = "Ya existe un idioma con ese nombre";
    public static final String IDIOMA_CON_PUBLICACION = "No se puede borrar el idioma porque hay publicaciones con el mismo";    
    public static final String IDIOMA_INEXISTENTE = "No existe el idioma especificado";

    
    /**
     * Crea un nuevo idioma
     * @param nombre nombre del idioma
     * @return cadena con el resultado de la operación (EXITO | ESCRITURA_ERROR | PROBLEMAS_ES | IDIOMAS_DUPLICADOS | ERROR_NOMBRE)
    */                                                                    
    public String nuevoIdioma(String nombre);
        
    /**
     * Borra un idioma siempre y cuando no haya publicaciones con el mismo
     * @param idioma idioma a borrar
     * @return String  - cadena con el resultado de la operación (EXITO | ESCRITURA_ERROR | PROBLEMAS_ES | IDIOMA_INEXISTENTE | IDIOMA_CON_PUBLICACION)
     */
    public String borrarIdioma(Idioma idioma);
    
    /**
     * Busca si existen idiomas con el nombre especificado (total o parcialmente)
     * Este método es necesario para las clases ModeloTablaIdiomas y ModeloComboPalabrasClaves
     * @param nombre nombre del idioma
     * @return List<Idioma>  - lista de idiomas, ordenados por nombre, cuyos nombres coincidan con el especificado
    */                                                                           
    public List<Idioma> buscarIdiomas(String nombre);
    
    /**
     * Devuelve todos los idiomas ordenados por nombre
     * Este método es necesario para las clases ModeloTablaIdiomas y ModeloComboPalabrasClaves
     * @return List<Idioma>  - lista de idiomas ordenados por nombre
    */                                                                           
    public List<Idioma> verIdiomas();    

    /**
     * Busca si existe un idioma cuyo nombre coincida con el especificado
     * Si existe un idioma con el nombre especificado, lo devuelve
     * Si no hay un idioma con el nombre especicado, devuelve null
     * @param nombre nombre del idioma a buscar
     * @return Idioma  - idioma cuyo nombre coincida con el especificado, o null
     */
    public Idioma verIdioma(String nombre); 
    
    /**
     * Devuelve true si existe el idioma especificado, false en caso contrario
     * @param idioma idioma a buscar
     * @return boolean  - true si existe el idioma especificado, false en caso contrario
     */
    public boolean existeEsteIdioma(Idioma idioma);
}
