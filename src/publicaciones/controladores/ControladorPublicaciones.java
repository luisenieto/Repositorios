/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package publicaciones.controladores;

import autores.modelos.Autor;
import auxiliares.Apariencia;
import interfaces.IControladorAMPublicacion;
import interfaces.IControladorPublicaciones;
import interfaces.IGestorPermisos;
import interfaces.IGestorPublicaciones;
import publicaciones.vistas.VentanaPublicaciones;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import permisos.modelos.GestorPermisos;
import publicaciones.modelos.GestorPublicaciones;
import publicaciones.modelos.ModeloTablaPublicaciones;
import publicaciones.modelos.Publicacion;

public class ControladorPublicaciones implements IControladorPublicaciones {
    private VentanaPublicaciones ventana;
    
    private int filaSeleccionada = -1;
    //sirve para manejar la tabla
    
    private Autor autor;
    //autor que se logueó
    
    /**
     * Constructor
     * Muestra la ventana de tipos de forma modal
     * @param ventanaPadre ventana padre (VentanaPrincipal en este caso)
     * @param autor autor sobre el cual hay que ver qué habilitar según sus grupos
     */
    public ControladorPublicaciones(Frame ventanaPadre, Autor autor) {
        this.autor = autor;
        this.ventana = new VentanaPublicaciones(this, ventanaPadre);
        this.ventana.setTitle(TITULO + " - (" + this.autor.verApeYNom() + ")");
        this.ventana.setLocationRelativeTo(null);
        Apariencia.asignarNimbusLookAndFeel("Nimbus"); //asigna el look and feel "Nimbus" a la ventana
        this.ventana.setVisible(true);
    }
    
    /**
     * Configura la ventana según el autor
     */
    private void configurarVentana() {        
        IGestorPublicaciones gp = GestorPublicaciones.instanciar();
        this.ventana.verBtnBorrar().setEnabled(gp.verPublicaciones().size() > 0);
        this.ventana.verBtnModificar().setEnabled(gp.verPublicaciones().size() > 0);
        this.ventana.verBtnBuscar().setEnabled(gp.verPublicaciones().size() > 0);
        this.ventana.verTxtTitulo().setEnabled(gp.verPublicaciones().size() > 0);
    }
            
    /**
     * Asigna la fila seleccionada en la tabla tablaPublicaciones
     */
    private void asignarFilaSeleccionada() {
        JTable tablaPublicaciones = this.ventana.verTablaPublicaciones();
        this.filaSeleccionada = tablaPublicaciones.getSelectedRow();        
    }
                         
    @Override
    public void btnNuevaClic(ActionEvent evt) {
        IControladorAMPublicacion controlador = new ControladorAMPublicacion(this.ventana, this.autor);
    }
                          
    @Override
    public void btnBorrarClic(ActionEvent evt) {
        this.asignarFilaSeleccionada();
        if (this.filaSeleccionada != -1) { //hay una fila seleccionada
            ModeloTablaPublicaciones modelo = (ModeloTablaPublicaciones)this.ventana.verTablaPublicaciones().getModel();
            Publicacion publicacion = modelo.verPublicacion(this.filaSeleccionada);
            IGestorPermisos gp = GestorPermisos.instanciar();
            if (!gp.puedeBorrarPublicacion(this.autor, publicacion)) 
                JOptionPane.showOptionDialog(null, IGestorPermisos.ERROR_BORRAR_PUBLICACIONES, TITULO, JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null, new Object[] {"Aceptar"}, this);       
            else {
                int opcion = JOptionPane.showOptionDialog(null, CONFIRMACION, TITULO, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Sí", "No"}, this);       
                if (opcion == JOptionPane.YES_OPTION) { //se quiere borrar la publicación 
                    IGestorPublicaciones gpu = GestorPublicaciones.instanciar();
                    String resultado = gpu.borrarPublicacion(publicacion);
                    if (!resultado.equals(IGestorPublicaciones.EXITO)) //no se pudo borrar la publicación
                        JOptionPane.showOptionDialog(null, resultado, TITULO, JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null, new Object[] {"Aceptar"}, this);       
                }
            }
        }
    }
                      
    @Override
    public void btnModificarClic(ActionEvent evt) {
        this.asignarFilaSeleccionada();
        if (this.filaSeleccionada != -1) { //hay una fila seleccionada
            ModeloTablaPublicaciones modelo = (ModeloTablaPublicaciones)this.ventana.verTablaPublicaciones().getModel();
            Publicacion publicacion = modelo.verPublicacion(this.filaSeleccionada);
            IGestorPermisos gp = GestorPermisos.instanciar();
            if (!gp.puedeModificarPublicacion(this.autor, publicacion))
                JOptionPane.showOptionDialog(null, IGestorPermisos.ERROR_MODIFICAR_PUBLICACIONES, TITULO, JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null, new Object[] {"Aceptar"}, this);       
            else {
                IControladorAMPublicacion controlador = new ControladorAMPublicacion(this.ventana, publicacion, this.autor);
            }
        }
    }
                      
    @Override
    public void btnVolverClic(ActionEvent evt) {
        this.ventana.dispose();
    }
                           
    @Override
    public void btnBuscarClic(ActionEvent evt) {
        ModeloTablaPublicaciones mtp;
        
        String titulo = this.ventana.verTxtTitulo().getText().trim();
        if (titulo.isEmpty())
            mtp = new ModeloTablaPublicaciones();
        else
            mtp = new ModeloTablaPublicaciones(titulo);
        this.configurarTabla(mtp);
    }
        
    @Override
    public void ventanaObtenerFoco(WindowEvent evt) {
        this.configurarTabla(new ModeloTablaPublicaciones());
        IGestorPublicaciones gp = GestorPublicaciones.instanciar();
        this.ventana.verBtnBorrar().setEnabled(gp.verPublicaciones().size() > 0);
        this.ventana.verBtnModificar().setEnabled(gp.verPublicaciones().size() > 0);
        this.ventana.verBtnBuscar().setEnabled(gp.verPublicaciones().size() > 0);
        this.ventana.verTxtTitulo().setEnabled(gp.verPublicaciones().size() > 0);        
    } 
            
    /**
     * Configura la tabla de publicaciones asignándole un modelo y seleccionando la primera fila (si hay filas)
     * @param mtp modelo para la tabla de publicaciones
     */
    private void configurarTabla(ModeloTablaPublicaciones mtp) {
        JTable tablaPubliciones = this.ventana.verTablaPublicaciones();
        tablaPubliciones.setModel(mtp);
        
        if (mtp.getRowCount() > 0) { //si hay filas, se selecciona la primera
            this.filaSeleccionada = 0;
            tablaPubliciones.setRowSelectionInterval(this.filaSeleccionada, this.filaSeleccionada);                           
        }
        else
            this.filaSeleccionada = -1;
    }
       
    @Override
    public void txtTituloPresionarTecla(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER)
            this.btnBuscarClic(null); //no importa el evento
    }      
}
