/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autores.modelos;

import interfaces.IGestorAutores;

public class Profesor extends Autor {
    private Cargo cargo;

    /**
     * Constructor
     * @param dni dni de un profesor
     * @param apellidos apellidos de un profesor
     * @param nombres nombres de un profesor
     * @param clave clave del profesor
     * @param cargo cargo de un profesor
     */
    public Profesor(int dni, String apellidos, String nombres, String clave, Cargo cargo) {
        super(dni, apellidos, nombres, clave);
        this.cargo = cargo;
    }

    /**
     * Muestra el cargo de un profesor
     * @return Cargo  - cargo de un profesor
     */    
    public Cargo verCargo() {
        return this.cargo;
    }

    /**
     * Asigna el cargo a un profesor
     * @param cargo cargo de un profesor
     */    
    public void asignarCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    @Override
    public String soy() {
        return IGestorAutores.SOY_PROFESOR;
    }
    
    /**
     * Muestra un profesor
     */    
//    @Override
//    public void mostrar() {
//        super.mostrar();
//        System.out.println("Cargo: " + this.cargo);
//    }    
    
    
}
