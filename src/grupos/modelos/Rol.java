/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupos.modelos;

import java.util.HashMap;
import java.util.Map;

public enum Rol {
    ADMINISTRADOR("Administrador"),
    COLABORADOR("Colaborador");
    
    private String valor;
    
    /**
     * Constructor
     * @param valor valor de la enumeración
     */                
    private Rol(String valor) {
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
     * @param rol cadena que representa un rol
     * @return Rol  - enumeración Rol
     */
    public static Rol verRol(String rol) {
        Map<String, Rol> mapeo = new HashMap<>();
        for (Rol p : Rol.values()) {
            mapeo.put(p.toString(), p);
        }
        return mapeo.get(rol);
    }
}
