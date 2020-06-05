/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.awt.event.ActionEvent;

public interface IControladorModificarMiembros {    
    public static final String TITULO = "Modificar miembros"; 
    public static final String CONFIRMACION = "¿Confirma que desea modificar los miembros de este grupo?"; 
    
    /**
     * Acción a ejecutar cuando se selecciona el botón Todos 
     * @param evt evento
     */                        
    public void btnTodosClic(ActionEvent evt);
    
    /**
     * Acción a ejecutar cuando se selecciona el botón Ninguno 
     * @param evt evento
     */                        
    public void btnNingunoClic(ActionEvent evt);
        
    /**
     * Acción a ejecutar cuando se selecciona el botón Aceptar
     * @param evt evento
     */                        
    public void btnAceptarClic(ActionEvent evt);

    /**
     * Acción a ejecutar cuando se selecciona el botón Cancelar
     * @param evt evento
     */                        
    public void btnCancelarClic(ActionEvent evt);
}
