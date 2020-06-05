/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupos.controladores;

import autores.modelos.Autor;
import auxiliares.Apariencia;
import interfaces.IControladorAMGrupo;
import interfaces.IGestorGrupos;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import grupos.modelos.GestorGrupos;
import grupos.modelos.Grupo;
import grupos.modelos.ModeloTablaMiembros;
import grupos.vistas.VentanaAMGrupo;
import java.awt.event.WindowEvent;
import javax.swing.JTable;
import interfaces.IControladorModificarMiembros;

/**
 * Clase para el controlador de Altas y Modificaciones de grupos
 */
public class ControladorAMGrupo implements IControladorAMGrupo {       
    private VentanaAMGrupo ventana;  
    private Grupo grupo;
    
    private Autor autor;
    //autor que se logueó
       
    /**
     * Constructor
     * @param ventanaPadre (VentanaGrupos en este caso)
     * @param autor autor sobre el cual hay que ver qué habilitar según sus grupos
     */    
    public ControladorAMGrupo(Dialog ventanaPadre, Autor autor) {
        this(ventanaPadre, null, autor);
    }    
    
    /**
     * Constructor
     * @param ventanaPadre (VentanaGrupos en este caso)
     * @param grupo grupo a modificar
     * @param autor autor sobre el cual hay que ver qué habilitar según sus grupos
     */    
    public ControladorAMGrupo(Dialog ventanaPadre, Grupo grupo, Autor autor) {
        this.grupo = grupo;
        this.autor = autor;
        this.ventana = new VentanaAMGrupo(this, ventanaPadre);
        this.ventana.setTitle(this.grupo == null ? TITULO_NUEVO + " - (" + autor.verApeYNom() + ")" : TITULO_MODIFICAR + " - (" + autor.verApeYNom() + ")");          
        this.ventana.setLocationRelativeTo(null);
//        Apariencia.asignarNimbusLookAndFeel("Nimbus");
        this.ventana.setVisible(true);
    }
    
    /**
     * Configura la ventana según el autor y el grupo
     * @param autor autor sobre el cual hay que ver qué habilitar según sus grupos
     */
    private void configurarVentana(Grupo grupo, Autor autor) {                 
        if (this.grupo == null) { //nuevo grupo
            this.ventana.verBtnModificar().setEnabled(false);
        }
        else { //modificación de grupo
            this.ventana.verTxtNombre().setText(this.grupo.verNombre());
            this.ventana.verTxtNombre().setEnabled(false); 
            this.ventana.verTxtDescripcion().setText(this.grupo.verDescripcion());
//            this.ventana.verTxtDescripcion().selectAll(); // no funciona            
        }                
    }
    
    /**
     * Configura la tabla de miembros asignándole un modelo
     */
    private void configurarTabla() {
        ModeloTablaMiembros mtm = new ModeloTablaMiembros(this.grupo);
        
        JTable tablaMiembros = this.ventana.verTablaMiembros();
        tablaMiembros.setModel(mtm);
    }
                                   
    @Override
    public void btnGuardarClic(ActionEvent evt) {
        String nombre = this.ventana.verTxtNombre().getText().trim();
        String descripcion = this.ventana.verTxtDescripcion().getText().trim();
        if (descripcion.isEmpty())
            descripcion = null;
        
        if (this.grupo == null) //nuevo grupo
            this.nuevoGrupo(nombre, descripcion);
        else //modificar trabajo
            this.modificarGrupo(descripcion);
    }
                               
    @Override
    public void btnModificarMiembrosClic(ActionEvent evt) {
        IControladorModificarMiembros controlador = new ControladorModificarMiembros(this.ventana, this.grupo, this.autor);
    }        
    
    /**
     * Se encarga de la creación de un grupo
     */
    private void nuevoGrupo(String nombre, String descripcion) {
        IGestorGrupos gg = GestorGrupos.instanciar();
        String resultado = gg.nuevoGrupo(nombre, descripcion);
        if (!resultado.equals(IGestorGrupos.EXITO))
            JOptionPane.showOptionDialog(null, resultado, TITULO_NUEVO, JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null, new Object[] {"Aceptar"}, this);       
        else
            this.ventana.dispose();        
    }
    
    /**
     * Se encarga de la modificación de un grupo
     */
    private void modificarGrupo(String descripcion) {
        IGestorGrupos gg = GestorGrupos.instanciar();
        String resultado = gg.modificarGrupo(this.grupo, descripcion);
        if (!resultado.equals(IGestorGrupos.EXITO))
            JOptionPane.showOptionDialog(null, resultado, TITULO_MODIFICAR, JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null, new Object[] {"Aceptar"}, this);               
        else
            this.ventana.dispose();                
    }
                            
    @Override
    public void btnCancelarClic(ActionEvent evt) {
        this.ventana.dispose();
    }
   
    @Override
    public void txtNombrePresionarTecla(KeyEvent evt) {
        this.presionarTecla(evt);
    }
    
    @Override
    public void txtDescripcionPresionarTecla(KeyEvent evt) {
        this.presionarTecla(evt);
    }
    
    /**
     * Acción a ejecutar cuando se presiona una tecla en los campos txtNombre y txtDescripcion
     * @param evt evento
     */
    private void presionarTecla(KeyEvent evt) {
        char c = evt.getKeyChar();            
        if (!Character.isLetter(c)) { //sólo se aceptan letras, Enter, Del, Backspace y espacio
            switch(c) {
                case KeyEvent.VK_ENTER: 
                    this.btnGuardarClic(null); //no importa el evento en este caso
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
    
    @Override
    public void ventanaObtenerFoco(WindowEvent evt) {
        this.configurarVentana(this.grupo, this.autor);
        this.configurarTabla();
    }
}
