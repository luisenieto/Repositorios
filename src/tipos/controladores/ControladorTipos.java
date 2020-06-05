/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tipos.controladores;

import autores.modelos.Autor;
import auxiliares.Apariencia;
import interfaces.IControladorTipos;
import interfaces.IGestorPermisos;
import interfaces.IGestorTipos;
import tipos.vistas.VentanaTipos;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import permisos.modelos.GestorPermisos;
import tipos.modelos.GestorTipos;
import tipos.modelos.Tipo;
import tipos.modelos.ModeloTablaTipos;
import interfaces.IControladorATipo;

public class ControladorTipos implements IControladorTipos {
    private VentanaTipos ventana;
    
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
    public ControladorTipos(Frame ventanaPadre, Autor autor) {  
        this.autor = autor;
        this.ventana = new VentanaTipos(this, ventanaPadre);
        this.ventana.setTitle(TITULO + " - (" + this.autor.verApeYNom() + ")");
        this.ventana.setLocationRelativeTo(null);
        this.configurarVentana();
        Apariencia.asignarNimbusLookAndFeel("Nimbus"); //asigna el look and feel "Nimbus" a la ventana
        this.ventana.setVisible(true);
    }
    
    /**
     * Configura la ventana según el autor
     */
    private void configurarVentana() {                
        IGestorPermisos gp = GestorPermisos.instanciar();
        this.ventana.verBtnNuevo().setEnabled(gp.puedeCrearTipos(this.autor));
        this.ventana.verBtnBorrar().setEnabled(gp.puedeBorrarTipos(this.autor));
        this.ventana.verBtnBuscar().setEnabled(gp.puedeBuscarTipos(this.autor));
        this.ventana.verTxtNombre().setEnabled(gp.puedeBuscarTipos(this.autor));
    }
            
    /**
     * Asigna la fila seleccionada en la tabla tablaTipos
     */
    private void asignarFilaSeleccionada() {
        JTable tablaTipos = this.ventana.verTablaTipos();
        this.filaSeleccionada = tablaTipos.getSelectedRow();        
    }
    
    @Override
    public void btnNuevoClic(ActionEvent evt) {
        this.asignarFilaSeleccionada();
        IControladorATipo controlador = new ControladorATipo(this.ventana, this.autor);
    }
    
    @Override
    public void btnBorrarClic(ActionEvent evt) {
        this.asignarFilaSeleccionada();
        if (this.filaSeleccionada != -1) { //hay una fila seleccionada
            int opcion = JOptionPane.showOptionDialog(null, CONFIRMACION, TITULO, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Sí", "No"}, this);       
            if (opcion == JOptionPane.YES_OPTION) { //se quiere borrar el lugar
                IGestorTipos gt = GestorTipos.instanciar();                
                ModeloTablaTipos modelo = (ModeloTablaTipos)this.ventana.verTablaTipos().getModel();
                Tipo tipo = modelo.verTipo(this.filaSeleccionada);
                String resultado = gt.borrarTipo(tipo);
                if (!resultado.equals(IGestorTipos.EXITO)) //no se pudo borrar el tipo
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
        ModeloTablaTipos mtt;
        
        String nombre = this.ventana.verTxtNombre().getText().trim();
        if (nombre.isEmpty())
            mtt = new ModeloTablaTipos();
        else
            mtt = new ModeloTablaTipos(nombre);
        this.configurarTabla(mtt);
    }
        
    @Override
    public void ventanaObtenerFoco(WindowEvent evt) {
        this.configurarTabla(new ModeloTablaTipos());
        IGestorPermisos gp = GestorPermisos.instanciar();
        this.ventana.verBtnBorrar().setEnabled(gp.puedeBorrarTipos(this.autor));
        this.ventana.verBtnBuscar().setEnabled(gp.puedeBuscarTipos(this.autor));
        this.ventana.verTxtNombre().setEnabled(gp.puedeBuscarTipos(this.autor));
    } 
            
    /**
     * Configura la tabla de tipos asignándole un modelo y seleccionando la primera fila (si hay filas)
     * @param mtt modelo para la tabla de tipos
     */
    private void configurarTabla(ModeloTablaTipos mtt) {
        JTable tablaTipos = this.ventana.verTablaTipos();
        tablaTipos.setModel(mtt);

        if (mtt.getRowCount() > 0) { //si hay filas, se selecciona la primera
            this.filaSeleccionada = 0;
            tablaTipos.setRowSelectionInterval(this.filaSeleccionada, this.filaSeleccionada);                           
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
