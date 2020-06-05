/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.awt.event.ActionEvent;


public interface IControladorPrincipal {
    public static final String CONFIRMACION = "¿Desea terminar la sesión?";
    public static final String TITULO = "Repositorios";
    
    /**
     * Acción a ejecutar cuando se selecciona el botón Palabras claves
     * @param evt evento
     */                        
    public void btnPalabrasClavesClic(ActionEvent evt);

    /**
     * Acción a ejecutar cuando se selecciona el botón Lugares
     * @param evt evento
     */                        
    public void btnLugaresClic(ActionEvent evt);

    /**
     * Acción a ejecutar cuando se selecciona el botón Idiomas
     * @param evt evento
     */                        
    public void btnIdiomasClic(ActionEvent evt);
    
    /**
     * Acción a ejecutar cuando se selecciona el botón Tipos
     * @param evt evento
     */                        
    public void btnTiposClic(ActionEvent evt);
    
    /**
     * Acción a ejecutar cuando se selecciona el botón Grupos
     * @param evt evento
     */                        
    public void btnGruposClic(ActionEvent evt);
    
    /**
     * Acción a ejecutar cuando se selecciona el botón Autores
     * @param evt evento
     */                        
    public void btnAutoresClic(ActionEvent evt);
    
    /**
     * Acción a ejecutar cuando se selecciona el botón Publicaciones
     * @param evt evento
     */                        
    public void btnPublicacionesClic(ActionEvent evt);

    /**
     * Acción a ejecutar cuando se selecciona el botón Salir
     * @param evt evento
     */                        
    public void btnSalirClic(ActionEvent evt);
}
