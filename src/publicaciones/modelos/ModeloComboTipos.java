/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package publicaciones.modelos;

import interfaces.IGestorTipos;
import javax.swing.DefaultComboBoxModel;
import tipos.modelos.GestorTipos;
import tipos.modelos.Tipo;

/**
 * Clase para mostrar los tipos de publicaciones en un combo/lista
 */
public class ModeloComboTipos extends DefaultComboBoxModel {
    
    /**
     * Constructor
     */
    public ModeloComboTipos() {  
        IGestorTipos gt = GestorTipos.instanciar();
        for (Tipo tipo : gt.verTipos()) { //todos los tipos
            this.addElement(tipo); 
        }
    }

    /**
     * Devuelve el tipo seleccionado
     * @return Tipo  - tipo seleccionado
     */
    public Tipo obtenerTipo() { 
        return (Tipo)this.getSelectedItem();
    }
        
    /**
     * Selecciona el tipo especificado
     * @param tipo tipo de publicación
     */
    public void seleccionarTipo(Tipo tipo) {
        this.setSelectedItem(tipo);
    }        
}
