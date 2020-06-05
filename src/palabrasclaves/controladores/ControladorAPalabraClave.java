/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palabrasclaves.controladores;

import autores.modelos.Autor;
import auxiliares.Apariencia;
import interfaces.IGestorPalabrasClaves;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import palabrasclaves.modelos.GestorPalabrasClaves;
import palabrasclaves.vistas.VentanaAMPalabraClave;
import interfaces.IControladorAPalabraClave;

/**
 * Clase para el controlador de Altas de palabras claves
 */
public class ControladorAPalabraClave implements IControladorAPalabraClave {       
    private VentanaAMPalabraClave ventana;    
    
    /**
     * Constructor
     * @param ventanaPadre (VentanaPalabrasClaves en este caso)
     * @param autor autor sobre el cual hay que ver qué habilitar según sus grupos
     */    
    public ControladorAPalabraClave(Dialog ventanaPadre, Autor autor) {
        this.ventana = new VentanaAMPalabraClave(this, ventanaPadre);
        this.ventana.setTitle(TITULO + " - (" + autor.verApeYNom() + ")");
        this.ventana.setLocationRelativeTo(null);
        Apariencia.asignarNimbusLookAndFeel("Nimbus");
        this.ventana.setVisible(true);
    }    
                                 
    @Override
    public void btnGuardarClic(ActionEvent evt) {
        String nombre = this.ventana.verTxtNombre().getText().trim();
        IGestorPalabrasClaves gpc = GestorPalabrasClaves.instanciar();
        String resultado = gpc.nuevaPalabraClave(nombre);
        if (!resultado.equals(IGestorPalabrasClaves.EXITO))
            JOptionPane.showMessageDialog(null, resultado, TITULO, JOptionPane.ERROR_MESSAGE);
        else
            this.ventana.dispose();
    }
                          
    @Override
    public void btnCancelarClic(ActionEvent evt) {
        this.ventana.dispose();
    }
  
    @Override
    public void txtNombrePresionarTecla(KeyEvent evt) {
        char c = evt.getKeyChar();            
        if (!Character.isLetter(c)) { //sólo se aceptan letras, Enter, Del, Backspace y espacio
            switch(c) {
                case KeyEvent.VK_ENTER: 
                    this.btnGuardarClic(null); // no importa el evento
                    break;
                case KeyEvent.VK_BACK_SPACE:    
                case KeyEvent.VK_DELETE:
                case KeyEvent.VK_SPACE:
                    break;
                default:
                    evt.consume(); //consume el evento para que no sea procesado por la fuente
            }
        }
    }
}
