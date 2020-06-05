/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package publicaciones.modelos;

import autores.modelos.Autor;
import grupos.modelos.Grupo;
import grupos.modelos.MiembroEnGrupo;
import javax.swing.DefaultComboBoxModel;

/**
 * Clase para mostrar los tipos de publicaciones en un combo/lista
 */
public class ModeloComboGrupos extends DefaultComboBoxModel {
    
    /**
     * Constructor
     * @param autor autor de quien se quieren ver sus grupos
     */
    public ModeloComboGrupos(Autor autor) {  
        for(MiembroEnGrupo meg : autor.verGrupos())
            this.addElement(meg.verGrupo()); 
    }

    /**
     * Devuelve el grupo seleccionado
     * @return Grupo  - grupo seleccionado
     */
    public Grupo obtenerGrupo() { 
        return (Grupo)this.getSelectedItem();
    }
        
    /**
     * Selecciona el grupo especificado
     * @param grupo grupo del autor de la publicación
     */
    public void seleccionarGrupo(Grupo grupo) {
        this.setSelectedItem(grupo);
    }        
}
