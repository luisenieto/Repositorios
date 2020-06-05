/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idiomas.modelos;

import java.util.Objects;

public class Idioma implements Comparable<Idioma> {
    private String nombre;

    /**
     * Constructor
     * @param nombre nombre del idioma
     */
    public Idioma(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Devuelve el nombre del idioma
     * @return String  - cadena con el nombre del idioma
     */
    public String verNombre() {
        return this.nombre;
    }

    /**
     * Devuelve el hashcode de un idioma
     * @return int  - hashcode de un idioma
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    /**
     * Compara si 2 idiomas son iguales o no según el nombre
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
        final Idioma other = (Idioma) obj;
        return this.nombre.trim().equalsIgnoreCase(other.nombre.trim());
    }

    /**
     * Permite ordenar los idiomas por nombre
     * @param o objeto contra el cual comparar
     * @return int  - resultado de la comparación
     */
    @Override
    public int compareTo(Idioma o) {
        return this.nombre.compareToIgnoreCase(o.nombre);
    }

    /**
     * Devuelve la representación de un idioma como cadena
     * @return String  - cadena con la representación del idioma
     */
    @Override
    public String toString() {
        return this.nombre;
    }        
}
