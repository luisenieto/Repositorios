/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autores.modelos;

import interfaces.IGestorAutores;
import java.util.Objects;

public class Alumno extends Autor {
    private String cx;

    /**
     * Constructor
     * @param dni dni de un alumno
     * @param apellidos apellidos de un alumno
     * @param nombres nombres de un alumno
     * @param clave clave del alumno
     * @param cx cx de un alumno
     */    
    public Alumno(int dni, String apellidos, String nombres, String clave, String cx) {
        super(dni, apellidos, nombres, clave);
        this.cx = cx;
    }

    /**
     * Muestra el CX de un alumno
     * @return String  - CX de un alumno
     */        
    public String verCX() {
        return this.cx;
    }

    /**
     * Asigna el CX a un alumno
     * @param cx CX de un alumno
     */        
    public void asignarCX(String cx) {
        this.cx = cx;
    }

    /**
     * Muestra un alumno
     */    
//    @Override
//    public void mostrar() {
//        super.mostrar();
//        System.out.println("CX: " + this.cx);
//    }  

    /**
     * Obtiene el hashcode de un alumno
     * @return int  - hashcode de un alumno
     */    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + super.hashCode() + Objects.hashCode(this.cx);
        return hash;
    }

    /**
     * Compara si 2 alumnos son iguales según su dni y cx
     * @param obj objeto contra el cual se compara
     * @return boolean  - true si 2 alumnos tienen el mismo dni y/o cx, false en caso contrario
     */    
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Alumno other = (Alumno) obj;
            if (!Objects.equals(this.cx, other.cx)) {
                return false;
            }
            return true;
            }
        else
            return true;
    }  

    @Override
    public String soy() {
        return IGestorAutores.SOY_ALUMNO;
    }
    
    
}
