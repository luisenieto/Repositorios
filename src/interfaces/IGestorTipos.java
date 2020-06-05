/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import tipos.modelos.Tipo;
import java.util.List;

public interface IGestorTipos {
    //Constantes para las operaciones de E/S
    public static final String LECTURA_ERROR = "Error al leer los tipos de publicación";
    public static final String LECTURA_OK = "Se pudieron leer los tipos de publicación";
    public static final String ESCRITURA_ERROR = "Error al guardar los tipos de publicación";
    public static final String ESCRITURA_OK = "Se pudieron guardar los tipos de publicación"; 
    public static final String CREACION_ERROR = "Error al crear el archivo de tipos de publicación";
    public static final String CREACION_OK = "Se pudo crear el archivo de tipos de publicación";
    public static final String PROBLEMAS_ES = "No se puede realizar la operación por problemas con el archivo";

    //Constantes para el ABM de tipos de publicación
    public static final String EXITO = "Tipo de publicación creado/borrado con éxito";
    public static final String ERROR_NOMBRE = "El nombre del tipo de publicación no puede ser nulo";
    public static final String TIPOS_DUPLICADOS = "Ya existe un tipo de publicación con ese nombre";
    public static final String TIPO_CON_PUBLICACION = "No se puede borrar el tipo de publicación porque hay publicaciones con el mismo";    
    public static final String TIPO_INEXISTENTE = "No existe el tipo de publicación especificado";

    
    /**
     * Crea un nuevo tipo de publicación
     * @param nombre nombre del tipo de publicación
     * @return cadena con el resultado de la operación (EXITO | ESCRITURA_ERROR | PROBLEMAS_ES | TIPOS_DUPLICADOS | ERROR_NOMBRE)
    */                                                                    
    public String nuevoTipo(String nombre);
        
    /**
     * Borra un tipo de publicación siempre y cuando no haya publicaciones con el mismo
     * @param tipo tipo a borrar
     * @return String  - cadena con el resultado de la operación (EXITO | ESCRITURA_ERROR | PROBLEMAS_ES | TIPO_INEXISTENTE | TIPO_CON_PUBLICACION)
     */
    public String borrarTipo(Tipo tipo);
    
    /**
     * Busca si existen tipos de publicación con el nombre especificado (total o parcialmente)
     * Este método es necesario para las clases ModeloTablaTipos y ModeloComboPalabrasClaves
     * @param nombre nombre del tipo de publicación
     * @return List<Tipo>  - lista de tipos de publicación, ordenados por nombre, cuyos nombres coincidan con el especificado
    */                                                                           
    public List<Tipo> buscarTipos(String nombre);
    
    /**
     * Devuelve todos los tipos de publicación ordenados por nombre
     * Este método es necesario para las clases ModeloTablaTipos y ModeloComboPalabrasClaves
     * @return List<Tipo>  - lista de tipos de publicación ordenados por nombre
    */                                                                           
    public List<Tipo> verTipos();   
    
    /**
     * Busca si existe un tipo cuyo nombre coincida con el especificado
     * Si existe un tipo con el nombre especificado, lo devuelve
     * Si no hay un tipo con el nombre especicado, devuelve null
     * @param nombre nombre del tipo a buscar
     * @return Tipo  - tipo cuyo nombre coincida con el especificado, o null
     */
    public Tipo verTipo(String nombre);         
    
    /**
     * Devuelve true si existe el tipo especificado, false en caso contrario
     * @param tipo tipo a buscar
     * @return boolean  - true si existe el tipo especificado, false en caso contrario
     */
    public boolean existeEsteTipo(Tipo tipo);
}
