/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import grupos.modelos.Grupo;
import java.util.List;
import grupos.modelos.MiembroEnGrupo;

public interface IGestorGrupos {
    //Constantes para las operaciones de E/S
    public static final String LECTURA_ERROR = "Error al leer los grupos";
    public static final String LECTURA_OK = "Se pudieron leer los grupos";
    public static final String ESCRITURA_ERROR = "Error al guardar los grupos";
    public static final String ESCRITURA_OK = "Se pudieron guardar los grupos";   
    public static final String CREACION_ERROR = "Error al crear el archivo de grupos";
    public static final String CREACION_OK = "Se pudo crear el archivo de grupos";
    public static final String PROBLEMAS_ES = "No se puede realizar la operación por problemas con el archivo";

    //Constantes para el ABM de grupos
    public static final String EXITO = "Grupo creado/borrado/modificado con éxito";
    public static final String ERROR_NOMBRE = "El nombre del grupo no puede ser nulo";
    public static final String GRUPOS_DUPLICADOS = "Ya existe un grupo con ese nombre";
    public static final String GRUPO_INEXISTENTE = "No existe el grupo especificado";   
    public static final String GRUPO_CON_MIEMBROS = "No se puede borrar el grupo porque tiene miembros";    
    public static final String AUTOR_DUPLICADO = "El autor ya es miembro de este grupo";    
    public static final String AUTOR_INVALIDO = "Uno de los autores a agregar/quitar es incorrecto o no existe";    
    public static final String MIEMBROS_INEXISTENTES = "No se especificaron miembros a agregar/quitar";    
    public static final String EXITO_MIEMBROS = "Miembros agregados/quitados con éxito";
    
    public static final String NOMBRE_SUPER_ADMINISTRADORES = "Super Administradores"; 
    public static final String DESCRIP_SUPER_ADMINISTRADORES = "Grupo para los super administradores"; 
    //grupo de super administradores
            
    /**
     * Crea un nuevo grupo
     * @param nombre nombre del grupo
     * @param descripcion descripcion del grupo
     * @return cadena con el resultado de la operación (EXITO | ESCRITURA_ERROR | PROBLEMAS_ES | GRUPOS_DUPLICADOS | ERROR_NOMBRE)
    */                                                                    
    public String nuevoGrupo(String nombre, String descripcion);
        
    /**
     * Borra un grupo siempre y cuando no haya autores con el mismo
     * @param grupo grupo a borrar
     * @return String  - cadena con el resultado de la operación (EXITO | ESCRITURA_ERROR | PROBLEMAS_ES | GRUPO_INEXISTENTE | GRUPO_CON_MIEMBROS)
     */
    public String borrarGrupo(Grupo grupo);
    
    /**
     * Al grupo especificado le modifica su descripción
     * @param grupo grupo a modificar
     * @param descripcion nueva descripción del grupo
     * @return String  - cadena con el resultado de la operación (EXITO | ESCRITURA_ERROR | PROBLEMAS_ES | GRUPO_INEXISTENTE)
     */
    public String modificarGrupo(Grupo grupo, String descripcion);
    
    /**
     * Agrega los miembros especificados al grupo, siempre y cuando no estén 
     * @param grupo grupo al cual se le agregarán los miembros
     * @param miembros miembros a agregar
     * @param rol rol que cumplen los miembros en el grupo
     * @return String  - cadena con el resultado de la operación (EXITO_MIEMBROS | ESCRITURA_ERROR | PROBLEMAS_ES | GRUPO_INEXISTENTE | MIEMBROS_INEXISTENTES)
     */    
//    public String agregarMiembros(Grupo grupo, List<Autor> miembros, Rol rol);
    
    public String agregarMiembros(Grupo grupo, List<MiembroEnGrupo> miembros);
    
    /**
     * Quita los miembros especificados del grupo
     * @param grupo grupo al que se le quitarán los miembros
     * @param miembros miembros a quitar
     * @return String  - cadena con el resultado de la operación (EXITO_MIEMBROS | ESCRITURA_ERROR | PROBLEMAS_ES | GRUPO_INEXISTENTE | MIEMBROS_INEXISTENTES)
     */    
//    public String quitarMiembros(Grupo grupo, List<Autor> miembros, Rol rol);
    
    public String quitarMiembros(Grupo grupo, List<MiembroEnGrupo> miembros);
        
    /**
     * Busca si existen grupos con el nombre especificado (total o parcialmente)
     * Este método es necesario para las clases ModeloTablaGrupos y ModeloComboPalabrasClaves
     * @param nombre nombre del grupo
     * @return List<Grupo>  - lista de grupos, ordenados por nombre, cuyos nombres coincidan con el especificado
    */                                                                           
    public List<Grupo> buscarGrupos(String nombre);
    
    /**
     * Devuelve todos los grupos ordenados por nombre
     * Este método es necesario para las clases ModeloTablaGrupos y ModeloComboPalabrasClaves
     * @return List<Grupo>  - lista de grupos ordenados por nombre
    */                                                                           
    public List<Grupo> verGrupos(); 
    
    /**
     * Busca si existe un grupo cuyo nombre coincida con el especificado
     * Si existe un grupo con el nombre especificado, lo devuelve
     * Si no hay un grupo con el nombre especicado, devuelve null
     * A este método lo usa la clase ControladorGrupos
     * @param nombre nombre del grupo a buscar
     * @return Grupo  - grupo cuyo nombre coincida con el especificado, o null
     */
    public Grupo verGrupo(String nombre); 
    
    /**
     * Devuelve true si existe el grupo especificado, false en caso contrario
     * @param grupo grupo a buscar
     * @return boolean  - true si existe el grupo especificado, false en caso contrario
     */
    public boolean existeEsteGrupo(Grupo grupo);
    
    /**
     * Vuelve a escribir los grupos en el archivo
     * Sirve para cuando se está modificando un profesor/alumno y se le agregaron grupos
     * @return String  - cadena con el resultado de la operación (ESCRITURA_OK | ESCRITURA_ERROR)
     */
    public String actualizarGrupos();
}
