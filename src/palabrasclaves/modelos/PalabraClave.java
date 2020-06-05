/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palabrasclaves.modelos;

import java.util.Objects;

public class PalabraClave implements Comparable<PalabraClave> {
    private String nombre;

    /**
     * Constructor
     * @param nombre nombre de la palabra clave
     */
    public PalabraClave(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Devuelve el nombre de la palabra clave
     * @return String  - cadena con el nombre de la palabra clave
     */
    public String verNombre() {
        return this.nombre;
    }

    /**
     * Devuelve el hashcode de una palabra clave
     * @return int  - hashcode de una palabra clave
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    /**
     * Compara si 2 palabras claves son iguales o no según el nombre
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
        final PalabraClave other = (PalabraClave) obj;
        return this.nombre.trim().equalsIgnoreCase(other.nombre.trim());
    }

    /**
     * Permite ordenar las palabras claves por nombre
     * @param o objeto contra el cual comparar
     * @return int  - resultado de la comparación
     */
    @Override
    public int compareTo(PalabraClave o) {
        return this.nombre.compareToIgnoreCase(o.nombre);
    }

    /**
     * Devuelve la representación de una palabra clave como cadena
     * @return String  - cadena con la representación de la palabra clave
     */
    @Override
    public String toString() {
        return this.nombre;
    }        
}
