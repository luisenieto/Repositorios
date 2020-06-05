/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idiomas.controladores;

import autores.modelos.Autor;
import auxiliares.Apariencia;
import interfaces.IControladorIdiomas;
import interfaces.IGestorIdiomas;
import idiomas.vistas.VentanaIdiomas;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import idiomas.modelos.GestorIdiomas;
import idiomas.modelos.Idioma;
import idiomas.modelos.ModeloTablaIdiomas;
import interfaces.IGestorPermisos;
import permisos.modelos.GestorPermisos;
import interfaces.IControladorAIdioma;

public class ControladorIdiomas implements IControladorIdiomas {
    private VentanaIdiomas ventana;
    
    private int filaSeleccionada = -1;
    //sirve para manejar la tabla
    
    private Autor autor;
    //autor que se logueó
    
    /**
     * Constructor
     * Muestra la ventana de idiomas de forma modal
     * @param ventanaPadre ventana padre (VentanaPrincipal en este caso)
     * @param autor autor sobre el cual hay que ver qué habilitar según sus grupos
     */
    public ControladorIdiomas(Frame ventanaPadre, Autor autor) {
        this.autor = autor;
        this.ventana = new VentanaIdiomas(this, ventanaPadre);
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
        
        this.ventana.verBtnNuevo().setEnabled(gp.puedeCrearIdiomas(this.autor));
        this.ventana.verBtnBorrar().setEnabled(gp.puedeBorrarIdiomas(this.autor)); 
        this.ventana.verBtnBuscar().setEnabled(gp.puedeBuscarIdiomas(this.autor));
        this.ventana.verTxtNombre().setEnabled(gp.puedeBuscarIdiomas(this.autor));
    }
            
    /**
     * Asigna la fila seleccionada en la tabla tablaIdiomas
     */
    private void asignarFilaSeleccionada() {
        JTable tablaIdiomas = this.ventana.verTablaIdiomas();
        this.filaSeleccionada = tablaIdiomas.getSelectedRow();        
    }
                              
    @Override
    public void btnNuevoClic(ActionEvent evt) {
        this.asignarFilaSeleccionada();
        IControladorAIdioma controlador = new ControladorAIdioma(this.ventana, this.autor);
    }
                                
    @Override
    public void btnBorrarClic(ActionEvent evt) {
        this.asignarFilaSeleccionada();
        if (this.filaSeleccionada != -1) { //hay una fila seleccionada
            int opcion = JOptionPane.showOptionDialog(null, CONFIRMACION, TITULO, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Sí", "No"}, this);       
            if (opcion == JOptionPane.YES_OPTION) { //se quiere borrar el lugar
                IGestorIdiomas gi = GestorIdiomas.instanciar(); 
                ModeloTablaIdiomas modelo = (ModeloTablaIdiomas)this.ventana.verTablaIdiomas().getModel();
                Idioma idioma = modelo.verIdioma(this.filaSeleccionada);
                String resultado = gi.borrarIdioma(idioma);
                if (!resultado.equals(IGestorIdiomas.EXITO)) //no se pudo borrar el idioma
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
        ModeloTablaIdiomas mti;
        
        String nombre = this.ventana.verTxtNombre().getText().trim();
        if (nombre.isEmpty())
            mti = new ModeloTablaIdiomas();
        else
            mti = new ModeloTablaIdiomas(nombre);
        this.configurarTabla(mti);
    }

    @Override
    public void ventanaObtenerFoco(WindowEvent evt) {
        this.configurarTabla(new ModeloTablaIdiomas()); 
        IGestorPermisos gp = GestorPermisos.instanciar(); 
        this.ventana.verBtnBorrar().setEnabled(gp.puedeBorrarIdiomas(this.autor)); 
        this.ventana.verBtnBuscar().setEnabled(gp.puedeBuscarIdiomas(this.autor));
        this.ventana.verTxtNombre().setEnabled(gp.puedeBuscarIdiomas(this.autor));
    } 
            
    /**
     * Configura la tabla de idiomas asignándole un modelo y seleccionando la primera fila (si hay filas)
     * @param mti modelo para la tabla de idiomas
     */
    private void configurarTabla(ModeloTablaIdiomas mti) {
        JTable tablaIdiomas = this.ventana.verTablaIdiomas();
        tablaIdiomas.setModel(mti);

        if (mti.getRowCount() > 0) { //si hay filas, se selecciona la primera
            this.filaSeleccionada = 0;
            tablaIdiomas.setRowSelectionInterval(this.filaSeleccionada, this.filaSeleccionada);                           
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
