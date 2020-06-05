/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package publicaciones.modelos;

import interfaces.IGestorLugares;
import javax.swing.DefaultComboBoxModel;
import lugares.modelos.GestorLugares;
import lugares.modelos.Lugar;
import tipos.modelos.Tipo;

/**
 * Clase para mostrar los tipos de publicaciones en un combo/lista
 */
public class ModeloComboLugares extends DefaultComboBoxModel {
    
    /**
     * Constructor
     */
    public ModeloComboLugares() {  
        IGestorLugares gl = GestorLugares.instanciar();
        for (Lugar lugar : gl.verLugares()) { //todos los lugares
            this.addElement(lugar); 
        }
    }

    /**
     * Devuelve el lugar seleccionado
     * @return Lugar  - lugar seleccionado
     */
    public Lugar obtenerLugar() { 
        return (Lugar)this.getSelectedItem();
    }
        
    /**
     * Selecciona el lugar especificado
     * @param lugar lugar de la publicación
     */
    public void seleccionarLugar(Lugar lugar) {
        this.setSelectedItem(lugar);
    }        
}
