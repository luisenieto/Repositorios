/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import autores.modelos.Autor;
import grupos.modelos.MiembroEnGrupo;
import idiomas.modelos.Idioma;
import java.time.LocalDate;
import java.util.List;
import palabrasclaves.modelos.PalabraClave;
import lugares.modelos.Lugar;
import publicaciones.modelos.Publicacion;
import tipos.modelos.Tipo;

public interface IGestorPublicaciones {
    //Constantes para las operaciones de E/S
    public static final String LECTURA_ERROR = "Error al leer las publicaciones";
    public static final String LECTURA_OK = "Se pudieron leer las publicaciones";
    public static final String ESCRITURA_ERROR = "Error al guardar las publicaciones";
    public static final String ESCRITURA_OK = "Se pudieron guardar las publicaciones";     
    public static final String CREACION_ERROR = "Error al crear el archivo de publicaciones";
    public static final String CREACION_OK = "Se pudo crear el archivo de publicaciones";
    public static final String PROBLEMAS_ES = "No se puede realizar la operación por problemas con el archivo";

    //Constantes para el ABM de publicaciones    
    public static final String EXITO = "Publicación creada/modificada/borrada con éxito";
    public static final String ERROR_TITULO = "El título de la publicación es incorrecto";
    public static final String ERROR_MIEMBRO_EN_GRUPO = "Error con el autor y/o grupo al que pertenece";    
    public static final String ERROR_FECHA = "La fecha de la publicación no puede ser nula";
    public static final String ERROR_TIPO = "El tipo de la publicación no puede ser nulo";    
    public static final String ERROR_IDIOMA = "El idioma de la publicación no puede ser nulo";    
    public static final String ERROR_LUGAR = "El lugar de la publicación no puede ser nulo";    
    public static final String ERROR_PALABRAS_CLAVES = "Las palabras claves de la publicación no pueden ser nulas";
    public static final String ERROR_ENLACE = "El enlace de la publicación no puede ser nulo";
    public static final String ERROR_RESUMEN = "El resumen de la publicación no puede ser nulo";  
    public static final String PUBLICACION_INEXISTENTE = "No existe la publicación especificada";
//    public static final String AUTOR_INEXISTENTE = "No existe el autor especificado";
//    public static final String AUTOR_NO_PUEDE_BORRAR_PUBLICACION = "Sólo el autor de la publicación, o un miembro del grupo de super administradores, la puede borrar";
//    public static final String AUTOR_NO_PUEDE_MODIFICAR_PUBLICACION = "Sólo el autor de la publicación, o un miembro del grupo de super administradores, la puede modificar";
    public static final String PUBLICACIONES_DUPLICADAS = "Ya existe una publicación con ese título";
        
    
    /**
     * Crea una nueva publicación
     * La fecha de aprobación debe ser igual o posterior a la de presentación
     * El tutor y el cotutor (en caso que hubiera) deben ser distintos
     * El jurado debe estar formado por 3 profesores distintos
     * El tutor no puede pertenecer al jurado
     * El cotutor (si hubiera) tampoco puede pertenecer al jurado
     * Por lo menos debe participar un alumno, y el mismo no debe estar actualmente en otro trabajo (con fecha de finalización no nula)
     * Si hay más de un alumno, deben ser distintos y ninguno debe estar en otro trabajo actualmente (con fecha de finalización no nula)
     * @param titulo título del trabajo
     * @param miembroEnGrupo miembro de un determinado grupo
     * @param fechaPublicacion fecha de la publicación
     * @param tipo tipo de publicación
     * @param idioma idioma de la publicación
     * @param lugar lugar de la publicación
     * @param palabrasClaves palabras claves de la publicación
     * @param enlace enlace para descargar el archivo con la publicación
     * @param resumen resumen de la publicación
     * @return String  - cadena con el resultado de la operación (EXITO | ESCRITURA_ERROR | PROBLEMAS_ES | PUBLICACIONES_DUPLICADAS | ERROR_TITULO | ERROR_AUTORES | ERROR_FECHA | ERROR_TIPO | ERROR_IDIOMA | ERROR_LUGAR | ERROR_PALABRAS_CLAVES | ERROR_ENLACE | ERROR_RESUMEN)
    */                                                                    
    public String nuevaPublicacion(String titulo, MiembroEnGrupo miembroEnGrupo, LocalDate fechaPublicacion, Tipo tipo, Idioma idioma, Lugar lugar, List<PalabraClave> palabrasClaves, String enlace, String resumen);
        
    /**
     * Modifica una nueva publicación
     * La fecha de aprobación debe ser igual o posterior a la de presentación
     * El tutor y el cotutor (en caso que hubiera) deben ser distintos
     * El jurado debe estar formado por 3 profesores distintos
     * El tutor no puede pertenecer al jurado
     * El cotutor (si hubiera) tampoco puede pertenecer al jurado
     * Por lo menos debe participar un alumno, y el mismo no debe estar actualmente en otro trabajo (con fecha de finalización no nula)
     * Si hay más de un alumno, deben ser distintos y ninguno debe estar en otro trabajo actualmente (con fecha de finalización no nula)
     * @param publicacion publicacion a modificar
     * @param miembroEnGrupo miembro de un determinado grupo
     * @param fechaPublicacion fecha de la publicación
     * @param tipo tipo de publicación
     * @param idioma idioma de la publicación
     * @param lugar lugar de la publicación
     * @param palabrasClaves palabras claves de la publicación
     * @param enlace enlace para descargar el archivo con la publicación
     * @param resumen resumen de la publicación
     * @return String  - cadena con el resultado de la operación (EXITO | ESCRITURA_ERROR | PROBLEMAS_ES | PUBLICACION_INEXISTENTE | ERROR_MIEMBRO_EN_GRUPO | ERROR_FECHA | ERROR_TIPO | ERROR_IDIOMA | ERROR_LUGAR | ERROR_PALABRAS_CLAVES | ERROR_ENLACE | ERROR_RESUMEN )
    */                                                                    
    public String modificarPublicacion(Publicacion publicacion, MiembroEnGrupo miembroEnGrupo, LocalDate fechaPublicacion, Tipo tipo, Idioma idioma, Lugar lugar, List<PalabraClave> palabrasClaves, String enlace, String resumen);
    
    /**
     * Determina si el autor especificado puede modificar o no la publicación
     * Sólo puede modificar una publicación su autor
     * @param publicacion publicación que se quiere ver si se puede modificar
     * @param autor autor a determinar si puede o no modificar la publicación
     * @return boolean  - true si el autor puede modificar la publicación, false en caso contrario
     */
//    public boolean puedeModificarEstaPublicacion(Publicacion publicacion, Autor autor);
    
    /**
     * Borra una publicación
     * @param publicacion publicación a borrar
     * @return String  - cadena con el resultado de la operación (EXITO | ESCRITURA_ERROR | PROBLEMAS_ES | PUBLICACION_INEXISTENTE)
     */
    public String borrarPublicacion(Publicacion publicacion);
                         
    /**
     * Busca si existen publicaciones con el título especificado (total o parcialmente)
     * Este método es usado por la clase ModeloTablaPublicaciones
     * @param titulo título del trabajo
     * @return List<Publicacion>  - lista con las publicaciones ordenadas por título
     */
    public List<Publicacion> buscarPublicaciones(String titulo);
    
    /**
     * Busca si hay al menos una publicación con la palabra clave especificada
     * A este método lo usa la clase GestorPalabrasClaves
     * @param palabraClave palabra clave a buscar
     * @return boolean  - true si hay al menos una publicación con la palabra clave especificada
     */
    public boolean hayPublicacionesConEstaPalabraClave(PalabraClave palabraClave);
    
    /**
     * Busca si hay al menos una publicación con el lugar especificado
     * A este método lo usa la clase GestorLugares
     * @param lugar lugar a buscar
     * @return boolean  - true si hay al menos una publicación con el lugar especificado
     */
    public boolean hayPublicacionesConEsteLugar(Lugar lugar);   
    
    /**
     * Busca si hay al menos una publicación con el idioma especificado
     * A este método lo usa la clase GestorIdiomas
     * @param idioma idioma a buscar
     * @return boolean  - true si hay al menos una publicación con el idioma especificado
     */
    public boolean hayPublicacionesConEsteIdioma(Idioma idioma);
    
    /**
     * Busca si hay al menos una publicación con el tipo especificado
     * A este método lo usa la clase GestorTipos
     * @param tipo tipo a buscar
     * @return boolean  - true si hay al menos una publicación con el tipo especificado
     */
    public boolean hayPublicacionesConEsteTipo(Tipo tipo);
        
    /**
     * Busca si hay al menos una publicación con el autor especificado
     * A este método lo usa la clase GestorAutores
     * @param autor autor a buscar
     * @return boolean  - true si hay al menos una publicación con el autor especificado
     */
    public boolean hayPublicacionesConEsteAutor(Autor autor);  
    
    /**
     * Devuelve true si existe la publicación especificada, false en caso contrario
     * @param publicacion publicación a buscar
     * @return boolean  - true si existe la publicación especificada, false en caso contrario
     */
    public boolean existeEstaPublicacion(Publicacion publicacion);
    
    /**
     * Devuelve todas las publicaciones ordenadas por título
     * Este método es necesario para las clases ModeloTablaPublicaciones y ModeloComboPalabrasClaves
     * @return List<Publicacion>  - lista de publicaciones ordenadas por título
    */                                                                           
    public List<Publicacion> verPublicaciones(); 
}
