/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupos.modelos;

import autores.modelos.Autor;
import interfaces.IGestorGrupos;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Grupo implements Comparable<Grupo> {
    private String nombre;
    private String descripcion;
    private List<MiembroEnGrupo> miembros = new ArrayList<>();

    /**
     * Constructor
     * @param nombre nombre del grupo
     * @param descripcion descripción del grupo
     */
    public Grupo(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    /**
     * Constructor
     * @param nombre nombre del grupo
     */
    public Grupo(String nombre) {
        this(nombre, null);
    }    
    
    /**
     * Devuelve el nombre del grupo
     * @return String  - cadena con el nombre del grupo
     */
    public String verNombre() {
        return this.nombre;
    }
    
    /**
     * Devuelve la descripción del grupo
     * @return String  - cadena con la descripción del grupo
     */
    public String verDescripcion() {
        return this.descripcion;
    }  
    
    /**
     * Determina si se trata del grupo de super administradores
     * @return boolean  - true si se trata del grupo de super administradores, false en caso contrario
     */
    public boolean esSuperAdministradores() {
        return this.nombre.equals(IGestorGrupos.NOMBRE_SUPER_ADMINISTRADORES);
    }
    
    /**
     * Asigna la descripción especificada
     * @param descripcion descripción del grupo
     */
    public void asignarDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
        
    /**
     * Agrega el autor como miembro del grupo, junto con el rol en el mismo, siempre y cuando no esté
     * Si el grupo al cual se quiere agregar el miembro es el de super administradores, el rol es únicamente el de administrador
     * @param miembro miembro a agregar
     * @param rol rol que cumple el miembro en el grupo
     */
    public void agregarMiembro(Autor miembro, Rol rol) {
        if ((miembro != null) && (rol != null)) {   
            MiembroEnGrupo meg;
            if (this.nombre.equals(IGestorGrupos.NOMBRE_SUPER_ADMINISTRADORES))
                meg = new MiembroEnGrupo(miembro, this, Rol.ADMINISTRADOR);
                //al grupo de super administradores sólo se puede agregar un miembro con el rol de administrador
            else 
                meg = new MiembroEnGrupo(miembro, this, rol);
            if (!this.miembros.contains(meg)) {
                this.miembros.add(meg);
                Comparator<MiembroEnGrupo> cmp = (meg1, meg2) -> meg1.verMiembro().compareTo(meg2.verMiembro());
                Collections.sort(this.miembros, cmp);
                miembro.agregarGrupo(this, rol);                
            }
        }
    }
    
    /**
     * Agrega los miembros del grupo siempre y cuando no estén
     * @param miembros miembros a agregar
     */
    public void agregarMiembros(List<MiembroEnGrupo> miembros) {
        if (miembros != null) {
            for(MiembroEnGrupo meg : miembros) {
                Autor miembro = meg.verMiembro();
                Rol rol = meg.verRol();
                this.agregarMiembro(miembro, rol);
            }
        }
    }  
    
    /**
     * Quita el autor como miembro del grupo, a la vez que al autor también le quita la pertenencia al grupo
     * @param miembro miembro a quitar
     */
    public void quitarMiembro(Autor miembro) {
        if (miembro != null) {           
            for (MiembroEnGrupo meg : this.miembros) {
                Autor m = meg.verMiembro();
                if (miembro.equals(m)) {
                    this.miembros.remove(meg);
                    miembro.quitarGrupo(this);
                    break;
                }                
            }
        }
    }    
    
    /**
     * Quita los autores como miembros del grupo
     * @param miembros miembros a quitar
     */
    public void quitarMiembros(List<MiembroEnGrupo> miembros) {
        if (miembros != null) {            
            for(MiembroEnGrupo meg : miembros) {
                Autor miembro = meg.verMiembro();
                this.quitarMiembro(miembro);
            }
        }
    }
        
    /**
     * Si existe un autor con el documento y clave especificados lo devuelve
     * Si no existe un autor con el documento y claves especificados devuelve null
     * Sirve para saber si está o no el usuario Admin
     * @param dni documento del autor
     * @param clave clave del autor 
     * @return Autor  - autor cuyo documento y clave coincidan con los especificados, o null en caso contrario
     */
    public Autor validarMiembro(int dni, String clave) {
        for(MiembroEnGrupo meg : this.miembros) {
            Autor autor = meg.verMiembro();
            if (autor.validarDocumentoYClave(dni, clave))
                return autor;
        }
        return null;
    }
            
    /**
     * Devuelve la lista de miembros del grupo
     * Si en los miembros estuviera el usuario Admin no lo muestra
     * @return List<MiembroEnGrupo>  - lista de miembros del grupo
     */
    public List<MiembroEnGrupo> verMiembros() {
        List<MiembroEnGrupo> miembrosSinAdmin = new ArrayList<>();
        for(MiembroEnGrupo meg : this.miembros) {
            Autor autor = meg.verMiembro();
            if (autor.verDNI() > 0) //no es Admin
                miembrosSinAdmin.add(meg);
        }
        return miembrosSinAdmin;
    }        
    
    /**
     * Devuelve true si el grupo tiene al menos un miembro, false en caso contrario
     * @return boolean  - true si el grupo tiene al menos un miembro, false en caso contrario
     */
    public boolean tieneMiembros() {
        return !this.miembros.isEmpty();
    }
    
    /**
     * Devuelve el hashcode de un grupo
     * @return int  - hashcode de un grupo
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    /**
     * Compara si 2 grupos son iguales o no según el nombre
     * @param obj objeto contra el cual comparar
     * @return boolean  - resultado de la comparación
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Grupo other = (Grupo) obj;
        return this.nombre.trim().equalsIgnoreCase(other.nombre.trim());
    }

    /**
     * Permite ordenar los grupos por nombre
     * @param o objeto contra el cual comparar
     * @return int  - resultado de la comparación
     */
    @Override
    public int compareTo(Grupo o) {
        return this.nombre.compareToIgnoreCase(o.nombre);
    }

    /**
     * Devuelve la representación de un grupo como cadena
     * @return String  - cadena con la representación de un grupo como cadena
     */
    @Override
    public String toString() {
        return this.nombre;
    }        
}
