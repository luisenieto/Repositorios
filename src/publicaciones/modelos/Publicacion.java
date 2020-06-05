/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package publicaciones.modelos;

import autores.modelos.Autor;
import grupos.modelos.MiembroEnGrupo;
import idiomas.modelos.Idioma;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lugares.modelos.Lugar;
import palabrasclaves.modelos.PalabraClave;
import tipos.modelos.Tipo;

public class Publicacion implements Comparable<Publicacion> {                 
    private String titulo;
    private MiembroEnGrupo miembroEnGrupo;
    private LocalDate fechaPublicacion;
    private Tipo tipo;
    private Idioma idioma;    
    private Lugar lugar;
    private List<PalabraClave> palabrasClaves = new ArrayList<>();    
    private String enlace;
    private String resumen;

    /**
     * Constructor para crear una publicación nueva
     * @param titulo título del trabajo
     * @param miembroEnGrupo miembro de un determinado grupo
     * @param fechaPublicacion fecha de la publicación
     * @param tipo tipo de publicación
     * @param idioma idioma de la publicación
     * @param lugar lugar de la publicación
     * @param palabrasClaves palabras claves de la publicación
     * @param enlace enlace para descargar el archivo con la publicación
     * @param resumen resumen de la publicación
     */
    public Publicacion(String titulo, MiembroEnGrupo miembroEnGrupo, LocalDate fechaPublicacion, Tipo tipo, Idioma idioma, Lugar lugar, List<PalabraClave> palabrasClaves, String enlace, String resumen) {
        this.titulo = titulo;
        this.miembroEnGrupo = miembroEnGrupo;
        this.fechaPublicacion = fechaPublicacion;
        this.tipo = tipo;
        this.idioma = idioma;
        this.lugar = lugar;
        this.palabrasClaves = palabrasClaves;
        this.enlace = enlace;
        this.resumen = resumen;
    }
            
    /**
     * Devuelve el título de la publicación
     * @return String  - título de la publicación
     */
    public String verTitulo() {
        return this.titulo;
    }

    /**
     * Devuelve la fecha de publicación
     * @return LocalDate  - fecha de publicación
     */
    public LocalDate verFechaPublicacion() {
        return this.fechaPublicacion;
    }
    
    /**
     * Devuelve las palabras claves de la publicación
     * @return List<PalabraClave>  - palabras claves de la publicación
     */            
    public List<PalabraClave> verPalabrasClaves() {
        Collections.sort(this.palabrasClaves);
        return this.palabrasClaves;
    }    
    
    /**
     * Devuelve el lugar de publicación de la publicación
     * @return Lugar  - lugar de publicación de la publicación
     */            
    public Lugar verLugar() {
        return this.lugar;
    }        
    
    /**
     * Devuelve el tipo de publicación
     * @return Tipo  - tipo de publicación
     */            
    public Tipo verTipo() {
        return this.tipo;
    } 
    
    /**
     * Devuelve el idioma de la publicación
     * @return Idioma  - idioma de la publicación
     */            
    public Idioma verIdioma() {
        return this.idioma;
    } 
        
    /**
     * Devuelve el miembro del grupo de la publicación
     * @return MiembroEnGrupo  - miembro del grupo de la publicación
     */            
    public MiembroEnGrupo verMiembroEnGrupo() {
        return this.miembroEnGrupo;
    }    

    /**
     * Devuelve el enlace de la publicación
     * @return String  - enlace de la publicación
     */            
    public String verEnlace() {
        return this.enlace;
    } 
    
    /**
     * Devuelve el resumen de la publicación
     * @return String  - resumen de la publicación
     */            
    public String verResumen() {
        return this.resumen;
    } 

    /**
     * Asigna los nuevos autores para la publicación
     * @param meg autor y grupo de la publicación
     */            
    public void asignarAutor(MiembroEnGrupo meg) {
        if (meg != null)
            this.miembroEnGrupo = meg;
    }

    /**
     * Asigna la nueva fecha de publicación para la publicación
     * @param fechaPublicacion fecha de publicación de la publicación
     */                            
    public void asignarFechaPublicacion(LocalDate fechaPublicacion) {
        if (fechaPublicacion != null)
            this.fechaPublicacion = fechaPublicacion;
    }

    /**
     * Asigna el nuevo tipo para la publicación
     * @param tipo tipo de la publicación
     */                    
    public void asignarTipo(Tipo tipo) {
        if (tipo != null)
            this.tipo = tipo;
    }

    /**
     * Asigna el nuevo idioma para la publicación
     * @param idioma idioma de la publicación
     */                
    public void asignarIdioma(Idioma idioma) {
        if (idioma != null)
            this.idioma = idioma;
    }

    /**
     * Asigna el nuevo lugar para la publicación
     * @param lugar lugar de la publicación
     */            
    public void asignarLugar(Lugar lugar) {
        if (lugar != null)
            this.lugar = lugar;
    }

    /**
     * Asigna las nuevas palabras claves para la publicación
     * @param palabrasClaves palabras claves de la publicación
     */        
    public void asignarPalabrasClaves(List<PalabraClave> palabrasClaves) {
        if ((palabrasClaves != null) && (!palabrasClaves.isEmpty()))
            this.palabrasClaves = palabrasClaves;
    }

    /**
     * Asigna el nuevo enlace para la publicación
     * @param enlace enlace de la publicación
     */    
    public void asignarEnlace(String enlace) {
        if (enlace != null)
            this.enlace = enlace;
    }

    /**
     * Asigna el nuevo resumen para la publicación
     * @param resumen resumen de la publicación
     */
    public void asignarResumen(String resumen) {
        if (resumen != null)
            this.resumen = resumen;
    }
        
    /**
     * Obtiene el hashcode de una publicación
     * @return int  - hashcode de una publicación
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.titulo);
        return hash;
    }

    /**
     * Compara si 2 publicaciones son iguales según el ...
     * @param obj objeto contra el cual se compara
     * @return boolean  - true si 2 publicaciones tienen el mismo ..., false en caso contrario
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
        final Publicacion other = (Publicacion) obj;
        if (!Objects.equals(this.titulo, other.titulo)) {
            return false;
        }
        return true;
    }
    
    /**
     * Permite ordenar las publicaciones alfabéticamente según el título
     * @param o trabajo contra el cual comparar
     * @return int  - resultado de la comparación
     */
    @Override
    public int compareTo(Publicacion o) {
        return this.titulo.compareTo(o.titulo);
    }
}
