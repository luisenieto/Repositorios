/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import autores.modelos.Autor;
import grupos.modelos.Grupo;
import publicaciones.modelos.Publicacion;

public interface IGestorPermisos {
    public static final String ERROR_BORRAR_GRUPOS = "No se puede borrar el grupo porque no se es super administrador";
    public static final String ERROR_MODIFICAR_GRUPOS = "No se puede modificar el grupo porque no se es administrador";
    public static final String ERROR_BORRAR_PUBLICACIONES = "No se puede borrar la publicación porque no se es super administrador o autor de la misma";
    public static final String ERROR_MODIFICAR_PUBLICACIONES = "No se puede modificar la publicación porque no se es super administrador o autor de la misma";

    
    /**
     * Si existe un autor con el documento y clave especificados lo devuelve
     * Si no existe un autor con el documento y claves especificados devuelve null
     * @param dni documento del autor
     * @param clave clave del autor 
     * @return Autor  - autor cuyo documento y clave coincidan con los especificados, o null en caso contrario
     */
    public Autor validarAutor(int dni, String clave);
    
    /**
     * Determina si el autor especificado puede o no crear un nuevo tipo de publicación
     * Sólo los miembros del grupo super administradores pueden crear tipos de publicación
     * @param autor autor a validar
     * @return boolean  - true si el autor especificado puede crear un nuevo tipo de publicación, false en caso contrario
     */
    public boolean puedeCrearTipos(Autor autor);
    
    /**
     * Determina si el autor especificado puede o no borrar un tipo de publicación
     * Sólo los miembros del grupo super administradores pueden borrar tipos de publicación
     * @param autor autor a validar
     * @return boolean  - true si el autor especificado puede borrar un tipo de publicación, false en caso contrario
     */
    public boolean puedeBorrarTipos(Autor autor);
    
    /**
     * Determina si el autor especificado puede o no buscar un tipo de publicación
     * Todos los autores pueden buscar tipos de publicación
     * @param autor autor a validar
     * @return boolean  - true si el autor especificado puede buscar un tipo de publicación, false en caso contrario
     */
    public boolean puedeBuscarTipos(Autor autor);
    
    /**
     * Determina si el autor especificado puede o no ver todos los tipos de publicación
     * Todos los autores pueden ver todos los tipos de publicación
     * @param autor autor a validar
     * @return boolean  - true si el autor especificado puede ver todos los tipos de publicación, false en caso contrario
     */
//    public boolean puedeVerTodosLosTipos(Autor autor);
    
    /**
     * Determina si el autor especificado puede o no crear un nuevo idioma
     * Sólo los miembros del grupo super administradores pueden crear idiomas
     * @param autor autor a validar
     * @return boolean  - true si el autor especificado puede crear un nuevo idioma, false en caso contrario
     */
    public boolean puedeCrearIdiomas(Autor autor);
    
    /**
     * Determina si el autor especificado puede o no borrar un idioma
     * Sólo los miembros del grupo super administradores pueden borrar idiomas
     * @param autor autor a validar
     * @return boolean  - true si el autor especificado puede borrar un idioma, false en caso contrario
     */
    public boolean puedeBorrarIdiomas(Autor autor);
    
    /**
     * Determina si el autor especificado puede o no buscar un idioma
     * Todos los autores pueden buscar idiomas
     * @param autor autor a validar
     * @return boolean  - true si el autor especificado puede buscar un idioma, false en caso contrario
     */
    public boolean puedeBuscarIdiomas(Autor autor);
    
    /**
     * Determina si el autor especificado puede o no ver todos los idiomas
     * Todos los autores pueden ver todos los idiomas
     * @param autor autor a validar
     * @return boolean  - true si el autor especificado puede ver todos los idiomas, false en caso contrario
     */
//    public boolean puedeVerTodosLosIdiomas(Autor autor);
    
    /**
     * Determina si el autor especificado puede o no crear un nuevo lugar
     * Sólo los miembros del grupo super administradores pueden crear lugars
     * @param autor autor a validar
     * @return boolean  - true si el autor especificado puede crear un nuevo lugar, false en caso contrario
     */
    public boolean puedeCrearLugares(Autor autor);
    
    /**
     * Determina si el autor especificado puede o no borrar un lugar
     * Sólo los miembros del grupo super administradores pueden borrar lugars
     * @param autor autor a validar
     * @return boolean  - true si el autor especificado puede borrar un lugar, false en caso contrario
     */
    public boolean puedeBorrarLugares(Autor autor);
    
    /**
     * Determina si el autor especificado puede o no buscar un lugar
     * Todos los autores pueden buscar lugares
     * @param autor autor a validar
     * @return boolean  - true si el autor especificado puede buscar un lugar, false en caso contrario
     */
    public boolean puedeBuscarLugares(Autor autor);
    
    /**
     * Determina si el autor especificado puede o no ver todos los lugars
     * Todos los autores pueden ver todos los lugares
     * @param autor autor a validar
     * @return boolean  - true si el autor especificado puede ver todos los lugares, false en caso contrario
     */
//    public boolean puedeVerTodosLosLugares(Autor autor);
    
    /**
     * Determina si el autor especificado puede o no crear una nueva palabra clave
     * Sólo los miembros del grupo super administradores pueden crear palabras claves
     * @param autor autor a validar
     * @return boolean  - true si el autor especificado puede crear una nueva palabra clave, false en caso contrario
     */
    public boolean puedeCrearPalabrasClaves(Autor autor);
    
    /**
     * Determina si el autor especificado puede o no borrar una palabra clave
     * Sólo los miembros del grupo super administradores pueden borrar palabras claves
     * @param autor autor a validar
     * @return boolean  - true si el autor especificado puede borrar una palabra clave, false en caso contrario
     */
    public boolean puedeBorrarPalabrasClaves(Autor autor);
    
    /**
     * Determina si el autor especificado puede o no buscar una palabra clave
     * Todos los autores pueden buscar palabras claves
     * @param autor autor a validar
     * @return boolean  - true si el autor especificado puede buscar una palabra clave, false en caso contrario
     */
    public boolean puedeBuscarPalabrasClaves(Autor autor);
    
    /**
     * Determina si el autor especificado puede o no ver todas las palabras claves
     * Todos los autores pueden ver todas las palabras claves
     * @param autor autor a validar
     * @return boolean  - true si el autor especificado puede ver todas las palabras claves, false en caso contrario
     */
//    public boolean puedeVerTodasLasPalabrasClaves(Autor autor);
    
    
    /**
     * Determina si el autor especificado puede o no crear un nuevo grupo
     * Sólo los miembros del grupo super administradores pueden crear grupos
     * @param autor autor a validar
     * @return boolean  - true si el autor especificado puede crear un nuevo grupo, false en caso contrario
     */
    public boolean puedeCrearGrupos(Autor autor);
    
    /**
     * Determina si el autor especificado puede o no borrar el grupo especificado
     * Si el grupo es el de super administradores, no se puede borrar
     * Si es otro grupo, sólo los super administradores lo pueden borrar
     * @param autor autor a validar
     * @param grupo grupo a borrar
     * @return boolean  - true si el autor especificado puede borrar el grupo especificado, false en caso contrario
     */
    public boolean puedeBorrarGrupo(Autor autor, Grupo grupo);
    
    /**
     * Determina si el autor especificado puede o no modificar el grupo especificado
     * Sólo los super administradores, o miembros con el rol de administrador, lo pueden modificar
     * @param autor autor a validar
     * @param grupo grupo a borrar
     * @return boolean  - true si el autor especificado puede modificar el grupo especificado, false en caso contrario
     */
    public boolean puedeModificarGrupo(Autor autor, Grupo grupo);
    
    /**
     * Determina si hay o no grupos para el autor especificado que luego los pueda borrar/modificar
     * Si el autor es super administrador, son todos los grupos
     * Si el autor no es super administrador, son los grupos a los que pertenece
     * @param autor autor a validar
     * @return boolean  - true si hay grupos para el autor especificado, false en caso contrario
     */
    public boolean hayGruposParaBorrarModificar(Autor autor);
    
    /**
     * Determina si el autor especificado puede o no buscar un grupo
     * Los miembros del grupo super administradores pueden buscar todos los grupos
     * El resto sólo puede buscar los grupos a los que pertenece
     * @param autor autor a validar
     * @return boolean  - true si el autor especificado puede buscar un grupo, false en caso contrario
     */
//    public boolean puedeBuscarTodosGrupos(Autor autor);
    
    /**
     * Determina si el autor especificado puede o no ver todos los grupos
     * Los miembros del grupo super administradores pueden ver todos los grupos
     * El resto sólo puede ver los grupos a los que pertenece
     * @param autor autor a validar
     * @return boolean  - true si el autor especificado puede ver todos los grupos, false en caso contrario
     */
//    public boolean puedeVerTodosLosGrupos(Autor autor);
    
    /**
     * Determina si el autor especificado puede o no agregar un miembro como administrador en el grupo especificado
     * Sólo los miembros del grupo super administradores, o miembros con el rol de administrador, pueden agregar un miembro como administrador en el grupo especificado
     * @param autor autor a validar
     * @param grupo grupo al cual se lo quiere agregar un miembro como administrador
     * @return boolean  - true si el autor especificado puede agregar un miembro como administrador, false en caso contrario
     */
//    public boolean puedeAgregarMiembroComoAdministrador(Autor autor, Grupo grupo);
    
    /**
     * Determina si el autor especificado puede o no quitar un miembro como administrador en el grupo especificado
     * Sólo los miembros del grupo super administradores, o miembros con el rol de administrador, pueden quitar un miembro como administrador en el grupo especificado
     * @param autor autor a validar
     * @param grupo grupo al cual se lo quiere agregar un miembro como administrador
     * @return boolean  - true si el autor especificado puede quitar un miembro como administrador, false en caso contrario
     */
//    public boolean puedeQuitarMiembroComoAdministrador(Autor autor, Grupo grupo);
    
    /**
     * Determina si el autor especificado puede o no agregar un miembro como colaborador en el grupo especificado
     * Sólo los miembros del grupo super administradores, o miembros con el rol de administrador, pueden agregar un miembro como colaborador en el grupo especificado
     * @param autor autor a validar
     * @param grupo grupo al cual se lo quiere agregar un miembro como colaborador
     * @return boolean  - true si el autor especificado puede agregar un miembro como colaborador, false en caso contrario
     */
//    public boolean puedeAgregarMiembroComoColaborador(Autor autor, Grupo grupo);
    
    /**
     * Determina si el autor especificado puede o no quitar un miembro como colaborador en el grupo especificado
     * Sólo los miembros del grupo super administradores, o miembros con el rol de administrador, pueden quitar un miembro como colaborador en el grupo especificado
     * @param autor autor a validar
     * @param grupo grupo al cual se lo quiere agregar un miembro como colaborador
     * @return boolean  - true si el autor especificado puede quitar un miembro como colaborador, false en caso contrario
     */
//    public boolean puedeQuitarMiembroComoColaborador(Autor autor, Grupo grupo);  
    
    /**
     * Determina si el autor especificado puede o no ver los miembros del grupo especificado
     * Los miembros del grupo super administradores pueden ver los miembros de todos los grupos
     * El resto sólo puede ver los miembros de los grupos a los que pertenece
     * @param autor autor a validar
     * @param grupo grupo al que se le quieren ver los miembros
     * @return boolean  - true si el autor especificado puede ver los miembros del grupo especificado, false en caso contrario
     */
//    public boolean puedeVerMiembros(Autor autor, Grupo grupo);      
    
    
    /**
     * Determina si el autor especificado puede o no crear un nuevo autor
     * Sólo los miembros del grupo super administradores pueden crear autores
     * @param autor autor a validar
     * @return boolean  - true si el autor especificado puede crear un nuevo autor, false en caso contrario
     */
    public boolean puedeCrearAutores(Autor autor);
    
    /**
     * Determina si el autor especificado puede o no borrar un profesor
     * Sólo los miembros del grupo super administradores pueden borrar profesores
     * @param autor autor a validar
     * @return boolean  - true si el autor especificado puede borrar un profesor, false en caso contrario
     */
    public boolean puedeBorrarProfesores(Autor autor);
    
    /**
     * Determina si el autor especificado puede o no borrar un alumno
     * Sólo los miembros del grupo super administradores pueden borrar alumnos
     * @param autor autor a validar
     * @return boolean  - true si el autor especificado puede borrar un alumno, false en caso contrario
     */
    public boolean puedeBorrarAlumnos(Autor autor);
    
    /**
     * Determina si el autor especificado puede o no modificar profesores
     * Los miembros del grupo super administradores pueden modificar cualquier profesor
     * Un autor que no sea super administrador, y que sea profesor, se puede modificar a sí mismo
     * @param autor autor a validar
     * @return boolean  - true si el autor especificado puede modificar un profesor, false en caso contrario
     */
    public boolean puedeModificarProfesores(Autor autor);
    
    /**
     * Determina si el autor especificado puede o no modificar alumnos
     * Los miembros del grupo super administradores pueden modificar cualquier alumno
     * Un autor que no sea super administrador, y que sea alumno, se puede modificar a sí mismo
     * @param autor autor a validar
     * @return boolean  - true si el autor especificado puede modificar un alumno, false en caso contrario
     */
    public boolean puedeModificarAlumnos(Autor autor);
    
    /**
     * Determina si el autor especificado puede o no buscar un profesor
     * Sólo los miembros del grupo super administradores pueden buscar profesores
     * @param autor autor a validar
     * @return boolean  - true si el autor especificado puede buscar un profesor, false en caso contrario
     */
    public boolean puedeBuscarProfesores(Autor autor);
    
    /**
     * Determina si el autor especificado puede o no buscar un alumno
     * Sólo los miembros del grupo super administradores pueden buscar alumnos
     * @param autor autor a validar
     * @return boolean  - true si el autor especificado puede buscar un alumno, false en caso contrario
     */
    public boolean puedeBuscarAlumnos(Autor autor);
    
    /**
     * Determina si el autor especificado puede o no crear una nueva publicación
     * Todos pueden crear publicaciones
     * @param autor autor a validar
     * @return boolean  - true si el autor especificado puede crear una nueva publicación, false en caso contrario
     */
//    public boolean puedeCrearPublicaciones(Autor autor);
    
    /**
     * Determina si hay o no publicaciones para borrar/modificar (depende del GestorPublicaciones)
     * @param autor autor a validar
     * @return boolean  - true si hay grupos para el autor especificado, false en caso contrario
     */
//    public boolean hayPublicacionesParaBorrarModificar();
    
    /**
     * Determina si el autor especificado puede o no borrar la publicación especificada
     * Si el autor es super administrador puede borrar cualquier publicación
     * Si el autor no es super administrador, sólo si es autor de la publicación la puede borrar
     * @param autor autor a validar
     * @param publicacion publicación a borrar
     * @return boolean  - true si el autor especificado puede borrar la publicación especificada, false en caso contrario
     */
    public boolean puedeBorrarPublicacion(Autor autor, Publicacion publicacion);
    
    /**
     * Determina si el autor especificado puede o no modificar la publicación especificada
     * Si el autor es super administrador puede modificar cualquier publicación
     * Si el autor no es super administrador, sólo si es autor de la publicación la puede modificar
     * @param autor autor a validar
     * @param publicacion publicación a modificar
     * @return boolean  - true si el autor especificado puede modificar la publicación especificada, false en caso contrario
     */
    public boolean puedeModificarPublicacion(Autor autor, Publicacion publicacion);    
    
    /**
     * Determina si el autor especificado puede o no modificar el grupo especificado
     * Sólo los super administradores, o miembros con el rol de administrador, lo pueden modificar
     * @param autor autor a validar
     * @param grupo grupo a borrar
     * @return boolean  - true si el autor especificado puede modificar el grupo especificado, false en caso contrario
     */
//    public boolean puedeModificarGrupo(Autor autor, Grupo grupo);
}
