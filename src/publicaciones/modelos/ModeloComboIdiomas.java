/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package publicaciones.modelos;

import idiomas.modelos.GestorIdiomas;
import idiomas.modelos.Idioma;
import interfaces.IGestorIdiomas;
import javax.swing.DefaultComboBoxModel;
import tipos.modelos.Tipo;

/**
 * Clase para mostrar los tipos de publicaciones en un combo/lista
 */
public class ModeloComboIdiomas extends DefaultComboBoxModel {
    
    /**
     * Constructor
     */
    public ModeloComboIdiomas() {  
        IGestorIdiomas gi = GestorIdiomas.instanciar();
        for (Idioma idioma : gi.verIdiomas()) { //todos los idiomas
            this.addElement(idioma); 
        }
    }

    /**
     * Devuelve el idioma seleccionado
     * @return Idioma  - idioma seleccionado
     */
    public Idioma obtenerIdioma() { 
        return (Idioma)this.getSelectedItem();
    }
        
    /**
     * Selecciona el idioma especificado
     * @param idioma idioma de la publicación
     */
    public void seleccionarIdioma(Idioma idioma) {
        this.setSelectedItem(idioma);
    }        
}
