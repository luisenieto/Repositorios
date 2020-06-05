/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autores.modelos;

import java.util.HashMap;
import java.util.Map;

public enum Cargo {
    TITULAR("Titular"),
    ASOCIADO("Asociado"),
    ADJUNTO("Adjunto"),
    JTP("Jefe de Trabajos Prácticos"),
    ADG("Aux. Docente Graduado");
    
    private String valor;
    
    /**
     * Constructor
     * @param valor valor de la enumeración
     */                
    private Cargo(String valor) {
        this.valor = valor;
    }            
    
    /**
     * Devuelve la constante como cadena
     * @return String  - constante como cadena
     */                
    @Override
    public String toString() {
        return this.valor;
    }    
    
    /**
     * Transforma una cadena en el valor de su enumeración correspondiente
     * @param cargo cadena que representa un cargo
     * @return Cargo  - enumeración Cargo
     */
    public static Cargo verCargo(String cargo) {
        Map<String, Cargo> mapeo = new HashMap<>();
        for (Cargo c : Cargo.values()) {
            mapeo.put(c.toString(), c);
        }
        return mapeo.get(cargo);
    }
}
