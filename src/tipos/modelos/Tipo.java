/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tipos.modelos;

import java.util.Objects;

public class Tipo implements Comparable<Tipo> {
    private String nombre;

    /**
     * Constructor
     * @param nombre nombre del tipo de publicación
     */
    public Tipo(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Devuelve el nombre del tipo de publicación
     * @return String  - cadena con el nombre del tipo de publicación
     */
    public String verNombre() {
        return this.nombre;
    }

    /**
     * Devuelve el hashcode de un tipo de publicación
     * @return int  - hashcode de un tipo de publicación
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    /**
     * Compara si 2 tipos de publicación son iguales o no según el nombre
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
        final Tipo other = (Tipo) obj;
        return this.nombre.trim().equalsIgnoreCase(other.nombre.trim());
    }

    /**
     * Permite ordenar los tipos de publicación por nombre
     * @param o objeto contra el cual comparar
     * @return int  - resultado de la comparación
     */
    @Override
    public int compareTo(Tipo o) {
        return this.nombre.compareToIgnoreCase(o.nombre);
    }

    /**
     * Devuelve la representación de un tipo de publicación como cadena
     * @return String  - cadena con la representación del tipo de publicación
     */
    @Override
    public String toString() {
        return this.nombre;
    }        
}
