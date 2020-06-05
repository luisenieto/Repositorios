/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupos.modelos;

import autores.modelos.Autor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import publicaciones.modelos.Publicacion;

public class MiembroEnGrupo {
    private Autor miembro;
    private Grupo grupo;
    private Rol rol;
    private List<Publicacion> publicaciones = new ArrayList<>();

    /**
     * Constructor
     * Sirve para mostrar todos los autores en la ventana AMGrupo
     * @param miembro miembro 
     */
    public MiembroEnGrupo(Autor miembro) {
        this(miembro, null, null);
    }
    
    /**
     * Constructor
     * Sirve para mostrar todos los grupos en la ventana AMAlumno/AMProfesor
     * @param grupo grupo 
     */
    public MiembroEnGrupo(Grupo grupo) {
        this(null, grupo, null);
    }
    
    /**
     * Constructor
     * @param miembro miembro que se quiere agregar al grupo especificado
     * @param grupo grupo al cual se agrega el miembro
     * @param rol rol que cumple el miembro en el grupo especificado
     */
    public MiembroEnGrupo(Autor miembro, Grupo grupo, Rol rol) {
        this.miembro = miembro;
        this.grupo = grupo;
        this.rol = rol;
    }
    
    /**
     * Devuelve el miembro del grupo
     * @return Autor  - miembro del grupo
     */
    public Autor verMiembro() {
        return this.miembro;
    }
    
    /**
     * Devuelve el grupo del miembro
     * @return Grupo  - grupo del miembro
     */
    public Grupo verGrupo() {
        return this.grupo;
    }
    
    /**
     * Devuelve el rol del miembro en el grupo
     * @return Rol  - rol del miembro en el grupo
     */
    public Rol verRol() {
        return this.rol;
    }
    
    /**
     * Asigna el rol al miembro
     * @param rol rol del miembro
     */
    public void asignarRol(Rol rol) {
        this.rol = rol;
    }

    /**
     * Devuelve el hashcode de un miembro en grupo
     * @return int  - hashcode de un miembro en grupo
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.miembro);
        hash = 13 * hash + Objects.hashCode(this.grupo);
        return hash;
    }

    /**
     * Compara si 2 miembros en el grupo son iguales: un miembro no puede pertenecer más de una vez al mismo grupo
     * @param obj objeto contra el cual se compara
     * @return boolean  - true si el miembro ya pertenece al grupo, false en caso contrario
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
        final MiembroEnGrupo other = (MiembroEnGrupo) obj;
        if (!Objects.equals(this.miembro, other.miembro)) {
            return false;
        }
        if (!Objects.equals(this.grupo, other.grupo)) {
            return false;
        }
        return true;
    }    
}
