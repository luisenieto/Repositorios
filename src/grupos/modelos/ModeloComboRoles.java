/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupos.modelos;

import javax.swing.DefaultComboBoxModel;

/**
 * Clase para mostrar los roles de los miembros en un grupo
 */
public class ModeloComboRoles extends DefaultComboBoxModel {
    
    /**
     * Constructor
     */
    public ModeloComboRoles() {  
        for (Rol rol : Rol.values()) {
            this.addElement(rol); 
        }
    }

    /**
     * Devuelve el rol seleccionado
     * @return Rol  - rol seleccionado
     */
    public Rol obtenerRol() { 
        return (Rol)this.getSelectedItem();
    }
        
    /**
     * Selecciona el rol especificado
     * @param rol rol
     */
    public void seleccionarRol(Rol rol) {
        this.setSelectedItem(rol);
    }        
}
