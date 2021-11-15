/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autores.controladores;

import interfaces.IControladorAMProfesor;
import autores.modelos.Cargo;
import autores.modelos.GestorAutores;
import autores.modelos.ModeloComboCargos;
import autores.modelos.ModeloTablaGrupos;
import autores.modelos.Profesor;
import autores.vistas.VentanaAProfesor;
import auxiliares.Apariencia;
import grupos.modelos.GestorGrupos;
import interfaces.IGestorAutores;
import interfaces.IGestorGrupos;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class ControladorAProfesor implements IControladorAMProfesor {    
    private VentanaAProfesor ventana;
    private Profesor profesor;

    /**
     * Constructor
     * @param ventanaPadre (VentanaAutores en este caso)
     */
    public ControladorAProfesor(Dialog ventanaPadre) {
        this.ventana = new VentanaAProfesor(this, ventanaPadre);
        this.ventana.setTitle(TITULO_NUEVO);
        this.ventana.setLocationRelativeTo(null);
        this.ventana.verComboCargo().setModel(new ModeloComboCargos());
        this.ventana.setVisible(true);
    }
    
    /**
     * Constructor
     * @param ventanaPadre (VentanaAutores en este caso)
     * @param profesor profesor a modificar
     */    
//    public ControladorAProfesor(Dialog ventanaPadre, Profesor profesor) {
//        this.profesor = profesor;
//        this.ventana = new VentanaAProfesor(this, ventanaPadre);
//        this.ventana.setTitle(this.profesor == null ? TITULO_NUEVO : TITULO_MODIFICAR);
//        this.ventana.setLocationRelativeTo(null);
//        this.ventana.verComboCargo().setModel(new ModeloComboCargos());
//        Apariencia.asignarNimbusLookAndFeel("Nimbus");
//        if (this.profesor == null) { //nuevo profesor
//            this.configurarTabla(new ModeloTablaGrupos());
//            this.ventana.verTablaGrupos().setEnabled(false);
//        }
//        else { //modificación de profesor
//            this.ventana.verTxtDNI().setText(Integer.toString(this.profesor.verDNI()));
//            this.ventana.verTxtDNI().setEditable(false);
//            this.ventana.verTxtApellidos().setText(this.profesor.verApellidos());
//            this.ventana.verTxtApellidos().requestFocus();
//            this.ventana.verTxtApellidos().selectAll(); // no funciona
//            this.ventana.verTxtNombres().setText(this.profesor.verNombres());              
//            Cargo cargo = profesor.verCargo();
//            ((ModeloComboCargos)this.ventana.verComboCargo().getModel()).seleccionarCargo(cargo);
//            this.ventana.verPassClave().setText(this.profesor.verClave());
//            this.ventana.verPassRepetirClave().setText(this.profesor.verClave());            
//            this.configurarTabla(new ModeloTablaGrupos(this.profesor));
//        }
//        this.ventana.setVisible(true);
//    }    
    
    /**
     * Configura la tabla de grupos asignándole un modelo y seleccionando la primera fila (si hay filas)
     * @param mtg modelo para la tabla de grupos
     */
//    private void configurarTabla(ModeloTablaGrupos mtg) {
//        JTable tablaGrupos = this.ventana.verTablaGrupos();
//        tablaGrupos.setModel(mtg);
//    }    
                                      
    @Override
    public void btnGuardarClic(ActionEvent evt) {
        int dni = 0;
        if (!this.ventana.verTxtDNI().getText().trim().isEmpty())
            dni = Integer.parseInt(this.ventana.verTxtDNI().getText().trim());
        String apellidos = this.ventana.verTxtApellidos().getText().trim();
        String nombres = this.ventana.verTxtNombres().getText().trim();
        Cargo cargo = ((ModeloComboCargos)this.ventana.verComboCargo().getModel()).obtenerCargo();
        String clave = new String(this.ventana.verPassClave().getPassword());
        String claveRepetida = new String(this.ventana.verPassRepetirClave().getPassword());
//        if (this.profesor == null) //nuevo profesor
            this.nuevoProfesor(dni, apellidos, nombres, cargo, clave, claveRepetida);
//        else //modificar profesor
//            this.modificarProfesor(apellidos, nombres, cargo, clave, claveRepetida);
    }
    
    /**
     * Se encarga de la creación de un profesor
     * @param dni dni del profesor
     * @param apellidos apellidos del profesor
     * @param nombres nombres del profesor
     * @param cargo cargo del profesor
     * @param clave clave del profesor
     * @param claveRepetida clave (repetida) del profesor
     */
    private void nuevoProfesor(int dni, String apellidos, String nombres, Cargo cargo, String clave, String claveRepetida) {        
        IGestorAutores ga = GestorAutores.instanciar();
        String resultado = ga.nuevoAutor(dni, apellidos, nombres, cargo, clave, claveRepetida);
        if (!resultado.equals(IGestorAutores.EXITO))
            JOptionPane.showMessageDialog(null, resultado, TITULO_NUEVO, JOptionPane.ERROR_MESSAGE);
        else
            this.ventana.dispose();                                    
    }
    
    /**
     * Se encarga de la modificación del profesor
     * @param apellidos apellidos del profesor
     * @param nombres nombres del profesor
     * @param cargo cargo del profesor
     * @param clave clave del profesor
     * @param claveRepetida clave (repetida) del profesor
     */    
//    private void modificarProfesor(String apellidos, String nombres, Cargo cargo, String clave, String claveRepetida) {
//        IGestorAutores ga = GestorAutores.instanciar();
//        String resultado = ga.modificarAutor(this.profesor, apellidos, nombres, cargo, clave, claveRepetida);
//        if (!resultado.equals(IGestorAutores.EXITO)) 
//            JOptionPane.showMessageDialog(null, resultado, TITULO_MODIFICAR, JOptionPane.ERROR_MESSAGE);
//        else {
//            IGestorGrupos gg = GestorGrupos.instanciar();
//            resultado = gg.actualizarGrupos();
//            if (!resultado.equals(IGestorGrupos.ESCRITURA_OK))
//                JOptionPane.showMessageDialog(null, resultado, TITULO_MODIFICAR, JOptionPane.ERROR_MESSAGE);
//            else
//                this.ventana.dispose();                                    
//        }
//    }    
                            
    @Override
    public void btnCancelarClic(ActionEvent evt) {
        this.ventana.dispose();
    }
  
    @Override
    public void txtDocumentoPresionarTecla(KeyEvent evt) { 
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) { //sólo se aceptan los dígitos del 0-9, Enter, Del y Backspace
            switch(c) {
                case KeyEvent.VK_ENTER: 
                    this.btnGuardarClic(null); //no importa el evento en este caso
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
    public void txtNombresPresionarTecla(KeyEvent evt) {
        this.txtApellidosPresionarTecla(evt);
    }
   
    @Override
    public void txtApellidosPresionarTecla(KeyEvent evt) {
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
    public void passClavePresionarTecla(KeyEvent evt) {
        if (evt.getKeyChar() == KeyEvent.VK_ENTER)
            this.btnGuardarClic(null);
    }

    @Override
    public void passRepetirClavePresionarTecla(KeyEvent evt) {
        this.passClavePresionarTecla(evt);
    }
    
    @Override
    public void ventanaObtenerFoco(WindowEvent evt) {
//        if (this.profesor != null) {
//            this.configurarTabla(new ModeloTablaGrupos(this.profesor)); //se refrezca la tabla
//        }
        //se hace esta comprobación para actualizar la tabla sólo en el caso que se esté modificando un profesor
        //y se esté volviendo de la ventana para agregar grupos        
    }        
}
