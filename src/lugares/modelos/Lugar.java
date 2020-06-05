/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugares.modelos;

import java.util.Objects;

public class Lugar implements Comparable<Lugar> {
    private String nombre;

    /**
     * Constructor
     * @param nombre nombre del lugar de publicación
     */
    public Lugar(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Devuelve el nombre del lugar de publicación
     * @return String  - cadena con el nombre del lugar de publicación
     */
    public String verNombre() {
        return this.nombre;
    }

    /**
     * Devuelve el hashcode de un lugar de publicación
     * @return int  - hashcode de un lugar de publicación
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    /**
     * Compara si 2 lugares de publicación son iguales o no según el nombre
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
        final Lugar other = (Lugar) obj;
        return this.nombre.trim().equalsIgnoreCase(other.nombre.trim());
    }

    /**
     * Permite ordenar los lugares de publicación por nombre
     * @param o objeto contra el cual comparar
     * @return int  - resultado de la comparación
     */
    @Override
    public int compareTo(Lugar o) {
        return this.nombre.compareToIgnoreCase(o.nombre);
    }

    /**
     * Devuelve la representación de un lugar de publicación como cadena
     * @return String  - cadena con la representación del lugar de publicación
     */
    @Override
    public String toString() {
        return this.nombre;
    }        
}
