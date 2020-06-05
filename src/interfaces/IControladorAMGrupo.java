/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

/**
 * Interfaz para el controlador de Altas y Modificaciones de grupos
 */
public interface IControladorAMGrupo {    
    public static final String TITULO_NUEVO = "Nuevo grupo"; 
    public static final String TITULO_MODIFICAR = "Modificar grupo"; 
    
    /**
     * Acción a ejecutar cuando se selecciona el botón Guardar
     * @param evt evento
     */                        
    public void btnGuardarClic(ActionEvent evt);

    /**
     * Acción a ejecutar cuando se selecciona el botón Cancelar
     * @param evt evento
     */                        
    public void btnCancelarClic(ActionEvent evt);
        
    /**
     * Acción a ejecutar cuando se selecciona el botón Modificar Miembros
     * @param evt evento
     */                        
    public void btnModificarMiembrosClic(ActionEvent evt);        
    
    /**
     * Acción a ejecutar cuando se presiona una tecla en el campo txtNombre
     * @param evt evento
     */
    public void txtNombrePresionarTecla(KeyEvent evt); 
    
    /**
     * Acción a ejecutar cuando se presiona una tecla en el campo txtDescripcion
     * @param evt evento
     */
    public void txtDescripcionPresionarTecla(KeyEvent evt);
    
    /**
     * Acción a ejecutar cuando la ventana obtiene el foco
     * @param evt evento
     */
    public void ventanaObtenerFoco(WindowEvent evt);
}
