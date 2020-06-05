/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;
import lugares.modelos.Lugar;

public interface IGestorLugares {
    //Constantes para las operaciones de E/S
    public static final String LECTURA_ERROR = "Error al leer los lugares de publicación";
    public static final String LECTURA_OK = "Se pudieron leer los lugares de publicación";
    public static final String ESCRITURA_ERROR = "Error al guardar los lugares de publicación";
    public static final String ESCRITURA_OK = "Se pudieron guardar los lugares de publicación"; 
    public static final String CREACION_ERROR = "Error al crear el archivo de lugares de publicación";
    public static final String CREACION_OK = "Se pudo crear el archivo de lugares de publicación";
    public static final String PROBLEMAS_ES = "No se puede realizar la operación por problemas con el archivo";    

    //Constantes para el ABM de lugares de publicación
    public static final String EXITO = "Lugar de publicación creado/borrado con éxito";
    public static final String ERROR_NOMBRE = "El nombre del lugar de publicación no puede ser nulo";
    public static final String LUGARES_DUPLICADOS = "Ya existe un lugar de publicación con ese nombre";
    public static final String LUGAR_CON_PUBLICACION = "No se puede borrar el lugar de publicación porque hay publicaciones con el mismo";    
    public static final String LUGAR_INEXISTENTE = "No existe el lugar especificado";

    
    /**
     * Crea un nuevo lugar de publicación
     * @param nombre nombre del lugar de publicación
     * @return cadena con el resultado de la operación (EXITO | ESCRITURA_ERROR | PROBLEMAS_ES | LUGARES_DUPLICADOS | ERROR_NOMBRE)
    */                                                                    
    public String nuevoLugar(String nombre);
        
    /**
     * Borra un lugar de publicación siempre y cuando no haya publicaciones con el mismo
     * @param lugar lugar de publicación a borrar
     * @return String  - cadena con el resultado de la operación (EXITO | ESCRITURA_ERROR | PROBLEMAS_ES | LUGAR_INEXISTENTE | LUGAR_CON_PUBLICACION)
     */
    public String borrarLugar(Lugar lugar);
    
    /**
     * Busca si existen lugares de publicación con el nombre especificado (total o parcialmente)
     * Este método es necesario para las clases ModeloTablaLugares y ModeloComboPalabrasClaves
     * @param nombre nombre del lugar de publicación
     * @return List<Lugar>  - lista de lugares de publicación, ordenados por nombre, cuyos nombres coincidan con el especificado
    */                                                                           
    public List<Lugar> buscarLugares(String nombre);
    
    /**
     * Devuelve todos los lugares de publicación ordenados por nombre
     * Este método es necesario para las clases ModeloTablaPublicaciones y ModeloComboPalabrasClaves
     * @return List<Lugar>  - lista de lugares ordenados por nombre
    */                                                                           
    public List<Lugar> verLugares();   
    
    /**
     * Busca si existe un lugar cuyo nombre coincida con el especificado
     * Si existe un lugar con el nombre especificado, lo devuelve
     * Si no hay un lugar con el nombre especicado, devuelve null
     * @param nombre nombre del lugar a buscar
     * @return Lugar  - lugar cuyo nombre coincida con el especificado, o null
     */
    public Lugar verLugar(String nombre);
    
    /**
     * Devuelve true si existe el lugar especificado, false en caso contrario
     * @param lugar lugar a buscar
     * @return boolean  - true si existe el lugar especificado, false en caso contrario
     */
    public boolean existeEsteLugar(Lugar lugar);
}
