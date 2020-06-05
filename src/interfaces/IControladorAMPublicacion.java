/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public interface IControladorAMPublicacion {    
    public static final String TITULO_NUEVA = "Nueva publicación"; 
    public static final String TITULO_MODIFICAR = "Modificar publicación"; 
    
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
     * Acción a ejecutar cuando se presiona una tecla en el campo txtTitulo
     * @param evt evento
     */
    public void txtTituloPresionarTecla(KeyEvent evt);         

    /**
     * Acción a ejecutar cuando se selecciona el botón Todas Las Palabras Claves
     * @param evt evento
     */                        
    public void btnTodasLasPalabrasClavesClic(ActionEvent evt);
    
    /**
     * Acción a ejecutar cuando se selecciona el botón Ninguna Palabra Clave
     * @param evt evento
     */                        
    public void btnNingunaPalabraClaveClic(ActionEvent evt);
    
    /**
     * Acción a ejecutar cuando se selecciona el botón Abrir
     * @param evt evento
     */                        
    public void btnAbrirClic(ActionEvent evt);
    
}
