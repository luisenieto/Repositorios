/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupos.controladores;

import autores.modelos.Autor;
import auxiliares.Apariencia;
import grupos.modelos.GestorGrupos;
import interfaces.IControladorAMGrupo;
import interfaces.IControladorGrupos;
import interfaces.IGestorGrupos;
import grupos.vistas.VentanaGrupos;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import grupos.modelos.Grupo;
import grupos.modelos.ModeloTablaGrupos;
import interfaces.IGestorPermisos;
import permisos.modelos.GestorPermisos;

public class ControladorGrupos implements IControladorGrupos {
    private VentanaGrupos ventana;
    
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
    public ControladorGrupos(Frame ventanaPadre, Autor autor) {
        this.autor = autor;
        this.ventana = new VentanaGrupos(this, ventanaPadre);
        this.ventana.setTitle(TITULO + " - (" + autor.verApeYNom() + ")");
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
        this.ventana.verBtnNuevo().setEnabled(gp.puedeCrearGrupos(this.autor));
        this.ventana.verBtnBorrar().setEnabled(gp.hayGruposParaBorrarModificar(this.autor));
        this.ventana.verBtnModificar().setEnabled(gp.hayGruposParaBorrarModificar(this.autor));     
        this.ventana.verBtnBuscar().setEnabled(gp.hayGruposParaBorrarModificar(this.autor));
        this.ventana.verTxtNombre().setEnabled(gp.hayGruposParaBorrarModificar(this.autor));
    }
            
    /**
     * Asigna la fila seleccionada en la tabla tablaGrupos
     */
    private void asignarFilaSeleccionada() {
        JTable tablaGrupos = this.ventana.verTablaGrupos();
        this.filaSeleccionada = tablaGrupos.getSelectedRow();        
    }
                             
    @Override
    public void btnNuevoClic(ActionEvent evt) {
        IControladorAMGrupo controlador = new ControladorAMGrupo(this.ventana, this.autor);
    }
                                
    @Override
    public void btnBorrarClic(ActionEvent evt) {
        this.asignarFilaSeleccionada();
        if (this.filaSeleccionada != -1) { //hay una fila seleccionada
            ModeloTablaGrupos modelo = (ModeloTablaGrupos)this.ventana.verTablaGrupos().getModel();
            Grupo grupo = modelo.verGrupo(this.filaSeleccionada);            
            IGestorPermisos gp = GestorPermisos.instanciar();
            if (!gp.puedeBorrarGrupo(this.autor, grupo))
                JOptionPane.showOptionDialog(null, IGestorPermisos.ERROR_BORRAR_GRUPOS, TITULO, JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null, new Object[] {"Aceptar"}, this);       
            else {
                int opcion = JOptionPane.showOptionDialog(null, CONFIRMACION, TITULO, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Sí", "No"}, this);       
                if (opcion == JOptionPane.YES_OPTION) { //se quiere borrar el grupo                
                    IGestorGrupos gg = GestorGrupos.instanciar();      
                    String resultado = gg.borrarGrupo(grupo);
                    if (!resultado.equals(IGestorGrupos.EXITO)) //no se pudo borrar el grupo
                        JOptionPane.showOptionDialog(null, resultado, TITULO, JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null, new Object[] {"Aceptar"}, this);       
                }
            }            
        }
    }
                            
    @Override
    public void btnModificarClic(ActionEvent evt) {
        this.asignarFilaSeleccionada();
        if (this.filaSeleccionada != -1) { //hay una fila seleccionada
            ModeloTablaGrupos modelo = (ModeloTablaGrupos)this.ventana.verTablaGrupos().getModel();
            Grupo grupo = modelo.verGrupo(this.filaSeleccionada);
            IGestorPermisos gp = GestorPermisos.instanciar();
            if (!gp.puedeModificarGrupo(this.autor, grupo))
                JOptionPane.showOptionDialog(null, IGestorPermisos.ERROR_MODIFICAR_GRUPOS, TITULO, JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null, new Object[] {"Aceptar"}, this);       
            else {
                IControladorAMGrupo controlador = new ControladorAMGrupo(this.ventana, grupo, this.autor);
            }
        }
    }
                        
    @Override
    public void btnVolverClic(ActionEvent evt) {
        this.ventana.dispose();
    }
                           
    @Override
    public void btnBuscarClic(ActionEvent evt) {
        ModeloTablaGrupos mtg;
        
        String nombre = this.ventana.verTxtNombre().getText().trim();
        if (nombre.isEmpty())
            mtg = new ModeloTablaGrupos(this.autor);
        else
            mtg = new ModeloTablaGrupos(this.autor, nombre);
        this.configurarTabla(mtg);
    }
        
    @Override
    public void ventanaObtenerFoco(WindowEvent evt) {
        this.configurarTabla(new ModeloTablaGrupos(this.autor));
        IGestorPermisos gp = GestorPermisos.instanciar();       
        this.ventana.verBtnNuevo().setEnabled(gp.puedeCrearGrupos(this.autor));
        this.ventana.verBtnBorrar().setEnabled(gp.hayGruposParaBorrarModificar(this.autor));
        this.ventana.verBtnModificar().setEnabled(gp.hayGruposParaBorrarModificar(this.autor));     
        this.ventana.verBtnBuscar().setEnabled(gp.hayGruposParaBorrarModificar(this.autor));
        this.ventana.verTxtNombre().setEnabled(gp.hayGruposParaBorrarModificar(this.autor));
    } 
            
    /**
     * Configura la tabla de grupos asignándole un modelo y seleccionando la primera fila (si hay filas)
     * @param mtg modelo para la tabla de grupos
     */
    private void configurarTabla(ModeloTablaGrupos mtg) {        
        JTable tablaGrupos = this.ventana.verTablaGrupos();
        tablaGrupos.setModel(mtg);
        if (this.autor.esSuperAdministrador()) //funciona sólo cuando hay filas
            tablaGrupos.setToolTipText(TOOL_TIP_TEXT_SUPER_ADMINISTRADOR);
        else
            tablaGrupos.setToolTipText(TOOL_TIP_TEXT_AUTOR);
                
        if (mtg.getRowCount() > 0) { //si hay filas, se selecciona la primera
            this.filaSeleccionada = 0;
            tablaGrupos.setRowSelectionInterval(this.filaSeleccionada, this.filaSeleccionada);  
            this.ventana.verBtnModificar().setEnabled(true);
        }
        else {
            this.filaSeleccionada = -1;
            this.ventana.verBtnModificar().setEnabled(false);
        }
    }
  
    @Override
    public void txtNombrePresionarTecla(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER)
            this.btnBuscarClic(null); //no importa el evento
    }      
}
