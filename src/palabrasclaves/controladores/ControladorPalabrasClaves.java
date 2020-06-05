/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palabrasclaves.controladores;

import autores.modelos.Autor;
import auxiliares.Apariencia;
import interfaces.IControladorPalabrasClaves;
import interfaces.IGestorPalabrasClaves;
import interfaces.IGestorPermisos;
import palabrasclaves.vistas.VentanaPalabrasClaves;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import palabrasclaves.modelos.GestorPalabrasClaves;
import palabrasclaves.modelos.ModeloTablaPalabrasClaves;
import palabrasclaves.modelos.PalabraClave;
import permisos.modelos.GestorPermisos;
import interfaces.IControladorAPalabraClave;

public class ControladorPalabrasClaves implements IControladorPalabrasClaves {
    private VentanaPalabrasClaves ventana;
    
    private int filaSeleccionada = -1;
    //sirve para manejar la tabla
    
    private Autor autor;
    //autor que se logueó
    
    /**
     * Constructor
     * Muestra la ventana de palabras claves de forma modal
     * @param ventanaPadre ventana padre (VentanaPrincipal en este caso)
     * @param autor autor sobre el cual hay que ver qué habilitar según sus grupos
     */
    public ControladorPalabrasClaves(Frame ventanaPadre, Autor autor) {
        this.autor = autor;
        this.ventana = new VentanaPalabrasClaves(this, ventanaPadre);
        this.ventana.setTitle(TITULO + " - (" + autor.verApeYNom() + ")");
        this.ventana.setLocationRelativeTo(null);
        this.configurarVentana(autor);
        Apariencia.asignarNimbusLookAndFeel("Nimbus"); //asigna el look and feel "Nimbus" a la ventana
        this.ventana.setVisible(true);
    }
    
    /**
     * Configura la ventana según el autor
     * @param autor autor sobre el cual hay que ver qué habilitar según sus grupos
     */
    private void configurarVentana(Autor autor) {        
        IGestorPermisos gp = GestorPermisos.instanciar(); 
        
        this.ventana.verBtnNueva().setEnabled(gp.puedeCrearPalabrasClaves(this.autor));
        this.ventana.verBtnBorrar().setEnabled(gp.puedeBorrarPalabrasClaves(this.autor));  
        this.ventana.verBtnBuscar().setEnabled(gp.puedeBuscarPalabrasClaves(this.autor));
        this.ventana.verTxtNombre().setEnabled(gp.puedeBuscarPalabrasClaves(this.autor));
    }
            
    /**
     * Asigna la fila seleccionada en la tabla tablaPalabrasClaves
     */
    private void asignarFilaSeleccionada() {
        JTable tablaPalabrasClaves = this.ventana.verTablaPalabrasClaves();
        this.filaSeleccionada = tablaPalabrasClaves.getSelectedRow();        
    }
                           
    @Override
    public void btnNuevaClic(ActionEvent evt) {
        this.asignarFilaSeleccionada();
        IControladorAPalabraClave controlador = new ControladorAPalabraClave(this.ventana, this.autor);
    }
                               
    @Override
    public void btnBorrarClic(ActionEvent evt) {
        this.asignarFilaSeleccionada();
        if (this.filaSeleccionada != -1) { //hay una fila seleccionada
            int opcion = JOptionPane.showOptionDialog(null, CONFIRMACION, TITULO, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Sí", "No"}, this);       
            if (opcion == JOptionPane.YES_OPTION) { //se quiere borrar la palabra clave 
                IGestorPalabrasClaves gpc = GestorPalabrasClaves.instanciar();                
                ModeloTablaPalabrasClaves modelo = (ModeloTablaPalabrasClaves)this.ventana.verTablaPalabrasClaves().getModel();
                PalabraClave palabraClave = modelo.verPalabraClave(this.filaSeleccionada);
                String resultado = gpc.borrarPalabraClave(palabraClave);
                if (!resultado.equals(IGestorPalabrasClaves.EXITO)) //no se pudo borrar la palabra clave
                    JOptionPane.showOptionDialog(null, resultado, TITULO, JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null, new Object[] {"Aceptar"}, this);       
            }
        }
    }
                     
    @Override
    public void btnVolverClic(ActionEvent evt) {
        this.ventana.dispose();
    }
                          
    @Override
    public void btnBuscarClic(ActionEvent evt) {
        ModeloTablaPalabrasClaves mtpc;
        
        String nombre = this.ventana.verTxtNombre().getText().trim();
        if (nombre.isEmpty())
            mtpc = new ModeloTablaPalabrasClaves();
        else
            mtpc = new ModeloTablaPalabrasClaves(nombre);
        this.configurarTabla(mtpc);
    }
        
    @Override
    public void ventanaObtenerFoco(WindowEvent evt) {
        this.configurarTabla(new ModeloTablaPalabrasClaves());
        IGestorPermisos gp = GestorPermisos.instanciar(); 
        this.ventana.verBtnBorrar().setEnabled(gp.puedeBorrarPalabrasClaves(this.autor));  
        this.ventana.verBtnBuscar().setEnabled(gp.puedeBuscarPalabrasClaves(this.autor));
        this.ventana.verTxtNombre().setEnabled(gp.puedeBuscarPalabrasClaves(this.autor));
    } 
            
    /**
     * Configura la tabla de palabras claves asignándole un modelo y seleccionando la primera fila (si hay filas)
     * @param mtpc modelo para la tabla de palabras claves
     */
    private void configurarTabla(ModeloTablaPalabrasClaves mtpc) {
        JTable tablaPalabrasClaves = this.ventana.verTablaPalabrasClaves();
        tablaPalabrasClaves.setModel(mtpc);

        if (mtpc.getRowCount() > 0) { //si hay filas, se selecciona la primera
            this.filaSeleccionada = 0;
            tablaPalabrasClaves.setRowSelectionInterval(this.filaSeleccionada, this.filaSeleccionada);                           
        }
        else
            this.filaSeleccionada = -1;
    }
       
    @Override
    public void txtNombrePresionarTecla(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER)
            this.btnBuscarClic(null); //no importa el evento
    }            
}
