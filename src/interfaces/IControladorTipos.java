/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;


public interface IControladorTipos {    
    public static final String TITULO = "Tipos de publicación";
    public static final String CONFIRMACION = "¿Desea borrar el tipo de publicación?";
    
    /**
     * Acción a ejecutar cuando se selecciona el botón Nuevo
     * @param evt evento
     */                        
    public void btnNuevoClic(ActionEvent evt);

    /**
     * Acción a ejecutar cuando se selecciona el botón Borrar
     * @param evt evento
     */                        
    public void btnBorrarClic(ActionEvent evt);
    
    /**
     * Acción a ejecutar cuando se selecciona el botón Volver
     * @param evt evento
     */                        
    public void btnVolverClic(ActionEvent evt);    
    
    /**
     * Acción a ejecutar cuando se selecciona el botón Buscar
     * @param evt evento
     */                        
    public void btnBuscarClic(ActionEvent evt);    
    
    /**
     * Acción a ejecutar cuando la ventana obtiene el foco
     * @param evt evento
     */
    public void ventanaObtenerFoco(WindowEvent evt);
    
    /**
     * Acción a ejecutar cuando se presiona una tecla en el campo txtNombre
     * @param evt evento
     */
    public void txtNombrePresionarTecla(KeyEvent evt);    
    
}
