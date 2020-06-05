/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.controladores;

import autores.modelos.Autor;
import autores.modelos.GestorAutores;
import auxiliares.Apariencia;
import grupos.modelos.GestorGrupos;
import interfaces.IControladorPrincipal;
import interfaces.IControladorVentanaLogin;
import interfaces.IGestorPermisos;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import permisos.modelos.GestorPermisos;
import principal.vistas.VentanaLogin;
import principal.vistas.VentanaLogin1;

public class ControladorVentanaLogin implements IControladorVentanaLogin {
//    private VentanaLogin ventana;
    private VentanaLogin1 ventana;

    /**
     * Constructor
     * @param ventanaPadre (VentanaPrincipal en este caso)
     */
    public ControladorVentanaLogin(Frame ventanaPadre) {
//        this.ventana = new VentanaLogin(this, ventanaPadre);
        this.ventana = new VentanaLogin1(this);
        this.ventana.setTitle(TITULO);
        this.ventana.setLocationRelativeTo(null);  
        Apariencia.asignarNimbusLookAndFeel("Nimbus"); //asigna el look and feel "Nimbus" a la ventana
        this.ventana.setVisible(true);
    }
    
    

    @Override
    public void btnAceptarClic(ActionEvent evt) {
        GestorAutores.instanciar();
        GestorGrupos.instanciar();
        int dni = 0;
        if (!this.ventana.verTxtDNI().getText().trim().isEmpty())
            dni = Integer.parseInt(this.ventana.verTxtDNI().getText().trim());
        
        String clave = new String(this.ventana.verPassClave().getPassword());
        
        IGestorPermisos gp = GestorPermisos.instanciar();
        Autor autor = gp.validarAutor(dni, clave);
        if (autor != null) { //existe un autor con el dni y clave especificados
            this.ventana.dispose();
            IControladorPrincipal controladorPrincipal = new ControladorPrincipal(autor);
        }
        else //no existe un autor con el dni y clave especificados
            JOptionPane.showOptionDialog(null, ERROR_VALIDACION, TITULO, JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null, new Object[] {"Aceptar"}, this);       
    }

    @Override
    public void btnCancelarClic(ActionEvent evt) {
        this.ventana.dispose();
        System.exit(0);
    }

    @Override
    public void txtDocumentoPresionarTecla(KeyEvent evt) {
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) { //sólo se aceptan los dígitos del 0-9, Enter, Del y Backspace
            switch(c) {
                case KeyEvent.VK_ENTER: 
                    this.btnAceptarClic(null); //no importa el evento en este caso
                    break;
                case KeyEvent.VK_BACK_SPACE:    
                case KeyEvent.VK_DELETE:
                    break;
                default:
                    evt.consume(); //consume el evento para que no sea procesado por la fuente
            }
        }
    }

    @Override
    public void passClavePresionarTecla(KeyEvent evt) {
        if (evt.getKeyChar() == KeyEvent.VK_ENTER)
            this.btnAceptarClic(null);
    }
    
}
