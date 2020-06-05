/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autores.modelos;

import grupos.modelos.Grupo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import grupos.modelos.MiembroEnGrupo;
import grupos.modelos.Rol;

public abstract class Autor implements Comparable<Autor> {
    private int dni;
    private String apellidos;
    private String nombres;
    private String clave;
    private List<MiembroEnGrupo> grupos = new ArrayList<>();
    
    /**
     * Constructor
     * @param dni dni de un autor
     * @param apellidos apellidos de un autor
     * @param nombres nombres de un autor
     * @param clave clave del autor
     */
    public Autor(int dni, String apellidos, String nombres, String clave) {
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.dni = dni;
        this.clave = clave;
    }

    /**
     * Obtiene el hashcode de un autor
     * @return int  - hashcode de un autor
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + this.dni;
        return hash;
    }
    
    /**
     * Compara si 2 autores son iguales según su dni
     * @param obj objeto contra el cual se compara
     * @return boolean  - true si 2 autores tienen el mismo dni, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass().getSuperclass() != obj.getClass().getSuperclass()) {
            return false;
        }
        final Autor other = (Autor) obj;
        return this.dni == other.dni;
    }
  

    /**
     * Muestra los apellidos de un autor
     * @return String  - apellidos de un autor
     */
    public String verApellidos() {
        return this.apellidos;
    }

    /**
     * Asigna los apellidos a un autor
     * @param apellidos apellidos de un autor
     */
    public void asignarApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    /**
     * Busca si existen grupos con el nombre especificado (total o parcialmente)
     * @param nombre nombre del grupo
     * @return List<Grupo>  - lista de grupos, ordenados por nombre, cuyos nombres coincidan con el especificado
    */                                                                           
    public List<Grupo> buscarGrupos(String nombre) {
        List<Grupo> grupos = new ArrayList<>();
        for(MiembroEnGrupo meg : this.grupos) {
            if(meg.verGrupo().verNombre().toLowerCase().contains(nombre.trim()))
                grupos.add(meg.verGrupo());
        }
        return grupos;
    }
    
    /**
     * Determina si el autor es super administrador
     * Para eso recorre todos los grupos a los que pertenece buscando si uno es el de super administradores
     * @return boolean  - true si el autor es super administrador, false en caso contrario
     */
    public boolean esSuperAdministrador() {
        for(MiembroEnGrupo meg : this.grupos) {
            Grupo grupo = meg.verGrupo();
            if (grupo.esSuperAdministradores())
                return true;
        }
        return false;
    }
    
    /**
     * Determina si el autor tiene el rol especificado en el grupo especificado
     * @param grupo grupo en el cual se quiere comprobar el rol del autor
     * @param rol rol a comprobar
     * @return boolean  - true si el autor tiene el rol especificado en el grupo especificado, false en caso contrario
     */
    public boolean tieneEsteRolEnEsteGrupo(Grupo grupo, Rol rol) {
        for(MiembroEnGrupo meg : this.grupos) {
            Grupo g = meg.verGrupo();
            Rol r = meg.verRol();
            if ((grupo.equals(g)) && (rol == r))
                return true;
        }
        return false;
    }
    
    /**
     * Valida que el documento y clave especificados coincidan con las del autor
     * @param dni documento a validar
     * @param clave clave a validar
     * @return boolean  - true si el documento y clave especificados coinciden, false en caso contrario
     */
    public boolean validarDocumentoYClave(int dni, String clave) {
        return ((this.dni == dni) && (this.clave.equals(clave)));
    }

    /**
     * Muestra los nombres de un autor
     * @return String  - nombres de un autor
     */    
    public String verNombres() {
        return this.nombres;
    }
    
    /**
     * Muestra los apellidos y nombres de un autor
     * @return String  - apellidos y nombres de un autor
     */        
    public String verApeYNom() {
        if (this.dni == 0)
            return this.apellidos;
        else
            return this.apellidos + ", " + this.nombres;
    }

    /**
     * Asigna los nombres a un autor
     * @param nombres nombres de un autor
     */    
    public void asignarNombres(String nombres) {
        this.nombres = nombres;
    }
    
    /**
     * Asigna la clave a un autor
     * @param clave clave de un autor
     */    
    public void asignarClave(String clave) {
        this.clave = clave;
    }

    /**
     * Muestra el dni de un autor
     * @return int  - dni de un autor
     */    
    public int verDNI() {
        return this.dni;
    }
    
    /**
     * Muestra la clave de un autor
     * @return String  - clave de un autor
     */    
    public String verClave() {
        return this.clave;
    }
    
    /**
     * Agrega el grupo al cual pertenece el autor, junto con el rol en el mismo, siempre y cuando no esté ya agregado
     * @param grupo grupo a agregar
     * @param rol rol que cumple el autor en el grupo
     */
    public void agregarGrupo(Grupo grupo, Rol rol) {
        if ((grupo != null) && (rol != null)) {
            MiembroEnGrupo meg = new MiembroEnGrupo(this, grupo, rol);
            if (!this.grupos.contains(meg)) {
                this.grupos.add(meg);
                Comparator<MiembroEnGrupo> cmp = (meg1, meg2) -> meg1.verGrupo().compareTo(meg2.verGrupo());
                Collections.sort(this.grupos, cmp); //ordena los grupos por nombre
                grupo.agregarMiembro(this, rol);                
            }
        }
    }
    
//    /**
//     * Agrega los grupos a los que pertenece el autor, junto con el rol en los mismos, siempre y cuando no perteneezca a los mismos
//     * @param grupos grupos a agregar
//     * @param rol rol que cumple el autor en los grupos
//     */
//    public void agregarGrupos(List<Grupo> grupos, Rol rol) {
//        if ((grupos != null) && (rol != null)) {
//            for(Grupo grupo : grupos)
//                this.agregarGrupo(grupo, rol);
//        }
//    }
    
    /**
     * Agrega los grupos a los que pertenece el autor, junto con el rol en los mismos, siempre y cuando no perteneezca a los mismos
     * @param grupos grupos a agregar
     */
    public void agregarGrupos(List<MiembroEnGrupo> grupos) {
        if (grupos != null) {
            for(MiembroEnGrupo meg : grupos) {
                Grupo grupo = meg.verGrupo();
                Rol rol = meg.verRol();
                this.agregarGrupo(grupo, rol);
            }
        }
    }
    
//    /**
//     * Quita el grupo al cual pertenece el autor con el rol especificado
//     * @param grupo grupo a quitar
//     * @param rol rol que cumple el autor en el grupo
//     */
//    public void quitarGrupo(Grupo grupo, Rol rol) {
//        if ((grupo != null) && (rol != null)) { 
//            MiembroEnGrupo meg = new MiembroEnGrupo(this, grupo, rol);
//            if (!this.grupos.contains(meg)) {
//                this.grupos.remove(meg);
//                grupo.quitarMiembro(this);
//            }
//        }
//    }
    
    /**
     * Quita el grupo al cual pertenece el autor con el rol especificado
     * @param grupo grupo a quitar
     */
    public void quitarGrupo(Grupo grupo) {
        if (grupo != null) {
            for(MiembroEnGrupo meg : this.grupos) {
                Grupo g = meg.verGrupo();
                if(grupo.equals(g)) {
                    this.grupos.remove(meg);
                    grupo.quitarMiembro(this);
                    break;
                }
            }
        }
    }
    
//    /**
//     * Quita los grupos a los cuales pertenece el autor
//     * @param grupos grupos a quitar
//     * @param rol rol que cumple el autor en los grupos
//     */
//    public void quitarGrupos(List<Grupo> grupos, Rol rol) {
//        if ((grupos != null) && (rol != null)) {            
//            for(Grupo grupo : grupos)
//                this.quitarGrupo(grupo, rol);
//        }
//    }
    
    /**
     * Quita los grupos a los cuales pertenece el autor
     * @param grupos grupos a quitar
     */
    public void quitarGrupos(List<MiembroEnGrupo> grupos) {
        if (grupos != null) {    
            for(MiembroEnGrupo meg : grupos) {
                Grupo grupo = meg.verGrupo();
                this.quitarGrupo(grupo);
            }
        }
    }
        
    /**
     * Devuelve la lista de grupos, junto con sus roles, a los que pertenece el autor
     * @return List<AutorEnGrupo>  - lista de grupos junto con sus roles a los que pertenece el autor
     */
    public List<MiembroEnGrupo> verGrupos() {
        return this.grupos;
    }
    
    /**
     * Modifica los grupos a los que pertenece el autor reemplazándolos por los especificados
     * A su vez a cada grupo le agrega este autor
     * @param gruposNuevos lista con los nuevos grupos del autor
     */    
//    public void modificarGrupos(List<Grupo> gruposNuevos) {
//        this.grupos = gruposNuevos;
//        for (Grupo grupo : gruposNuevos)
//            grupo.agregarMiembro(this);
//    }    

    /**
     * Ordena 2 autores primero por los apellidos y luego por los nombres
     * @param o autor contra la cual se compara
     * @return int  - número que indica qué autor se ordena primero
     */
    @Override
    public int compareTo(Autor o) {
        if (this.apellidos.compareToIgnoreCase(o.apellidos) == 0)
            return this.nombres.compareToIgnoreCase(o.nombres);
        else
            return this.apellidos.compareToIgnoreCase(o.apellidos);
    }
    
    public abstract String soy();
}
