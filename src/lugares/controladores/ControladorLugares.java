/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugares.controladores;

import autores.modelos.Autor;
import auxiliares.Apariencia;
import interfaces.IControladorLugares;
import interfaces.IGestorLugares;
import interfaces.IGestorPermisos;
import lugares.vistas.VentanaLugares;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import lugares.modelos.GestorLugares;
import lugares.modelos.Lugar;
import lugares.modelos.ModeloTablaLugares;
import permisos.modelos.GestorPermisos;
import interfaces.IControladorALugar;

public class ControladorLugares implements IControladorLugares {
    private VentanaLugares ventana;
    
    private int filaSeleccionada = -1;
    //sirve para manejar la tabla
    
    private Autor autor;
    //autor que se logueó
    
    /**
     * Constructor
     * Muestra la ventana de lugares de forma modal
     * @param ventanaPadre ventana padre (VentanaPrincipal en este caso)
     * @param autor autor sobre el cual hay que ver qué habilitar según sus grupos
     */
    public ControladorLugares(Frame ventanaPadre, Autor autor) {
        this.autor = autor;
        this.ventana = new VentanaLugares(this, ventanaPadre);
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
        this.ventana.verBtnNuevo().setEnabled(gp.puedeCrearLugares(this.autor));
        this.ventana.verBtnBorrar().setEnabled(gp.puedeBorrarLugares(this.autor));
        this.ventana.verBtnBuscar().setEnabled(gp.puedeBuscarLugares(this.autor));
        this.ventana.verTxtNombre().setEnabled(gp.puedeBuscarLugares(this.autor));
    }
            
    /**
     * Asigna la fila seleccionada en la tabla tablaLugares
     */
    private void asignarFilaSeleccionada() {
        JTable tablaLugares = this.ventana.verTablaLugares();
        this.filaSeleccionada = tablaLugares.getSelectedRow();        
    }
                          
    @Override
    public void btnNuevoClic(ActionEvent evt) {
        this.asignarFilaSeleccionada();
        IControladorALugar controlador = new ControladorALugar(this.ventana, this.autor);
    }
                             
    @Override
    public void btnBorrarClic(ActionEvent evt) {
        this.asignarFilaSeleccionada();
        if (this.filaSeleccionada != -1) { //hay una fila seleccionada
            int opcion = JOptionPane.showOptionDialog(null, CONFIRMACION, TITULO, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Sí", "No"}, this);       
            if (opcion == JOptionPane.YES_OPTION) { //se quiere borrar el lugar
                IGestorLugares gl = GestorLugares.instanciar();   
                ModeloTablaLugares modelo = (ModeloTablaLugares)this.ventana.verTablaLugares().getModel();
                Lugar lugar = modelo.verLugar(this.filaSeleccionada);
                String resultado = gl.borrarLugar(lugar);
                if (!resultado.equals(IGestorLugares.EXITO)) //no se pudo borrar el lugar
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
        ModeloTablaLugares mtl;
        
        String nombre = this.ventana.verTxtNombre().getText().trim();
        if (nombre.isEmpty())
            mtl = new ModeloTablaLugares();
        else
            mtl = new ModeloTablaLugares(nombre);
        this.configurarTabla(mtl);
    }
    
    @Override
    public void ventanaObtenerFoco(WindowEvent evt) {
        this.configurarTabla(new ModeloTablaLugares());   
        IGestorPermisos gp = GestorPermisos.instanciar(); 
        this.ventana.verBtnBorrar().setEnabled(gp.puedeBorrarLugares(this.autor));
        this.ventana.verBtnBuscar().setEnabled(gp.puedeBuscarLugares(this.autor));
        this.ventana.verTxtNombre().setEnabled(gp.puedeBuscarLugares(this.autor));
    } 
            
    /**
     * Configura la tabla de lugares asignándole un modelo y seleccionando la primera fila (si hay filas)
     * @param mtl modelo para la tabla de lugares
     */
    private void configurarTabla(ModeloTablaLugares mtl) {
        JTable tablaLugares = this.ventana.verTablaLugares();
        tablaLugares.setModel(mtl);

        if (mtl.getRowCount() > 0) { //si hay filas, se selecciona la primera
            this.filaSeleccionada = 0;
            tablaLugares.setRowSelectionInterval(this.filaSeleccionada, this.filaSeleccionada);                           
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
