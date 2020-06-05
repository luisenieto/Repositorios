/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autores.controladores;

import autores.modelos.Alumno;
import interfaces.IControladorAMAlumno;
import autores.modelos.GestorAutores;
import autores.modelos.ModeloTablaGrupos;
import autores.vistas.VentanaAMAlumno;
import auxiliares.Apariencia;
import interfaces.IGestorAutores;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class ControladorAMAlumno implements IControladorAMAlumno {    
    private VentanaAMAlumno ventana;
    private Alumno alumno;

    /**
     * Constructor
     * @param ventanaPadre (VentanaAutores en este caso)
     */
    public ControladorAMAlumno(Dialog ventanaPadre) {
        this(ventanaPadre, null);
    }
    
    /**
     * Constructor
     * @param ventanaPadre (VentanaAutores en este caso)
     * @param alumno alumno a modificar
     */    
    public ControladorAMAlumno(Dialog ventanaPadre, Alumno alumno) {
        this.alumno = alumno;
        this.ventana = new VentanaAMAlumno(this, ventanaPadre);
        this.ventana.setTitle(this.alumno == null ? TITULO_NUEVO : TITULO_MODIFICAR);
        this.ventana.setLocationRelativeTo(null);
        Apariencia.asignarNimbusLookAndFeel("Nimbus");
        if (this.alumno == null) { //nuevo alumno
            this.configurarTabla(new ModeloTablaGrupos());
            this.ventana.verTablaGrupos().setEnabled(false);
        }
        else { //modificación de alumno
            this.ventana.verTxtDNI().setText(Integer.toString(this.alumno.verDNI()));
            this.ventana.verTxtDNI().setEditable(false);
            this.ventana.verTxtApellidos().setText(this.alumno.verApellidos());
            this.ventana.verTxtApellidos().requestFocus();
            this.ventana.verTxtApellidos().selectAll(); // no funciona
            this.ventana.verTxtNombres().setText(this.alumno.verNombres());
            this.ventana.verTxtCX().setText(this.alumno.verCX());
            this.ventana.verPassClave().setText(this.alumno.verClave());
            this.ventana.verPassRepetirClave().setText(this.alumno.verClave());            
            this.configurarTabla(new ModeloTablaGrupos(this.alumno));
        }
        this.ventana.setVisible(true);
    }    
    
    /**
     * Configura la tabla de grupos asignándole un modelo y seleccionando la primera fila (si hay filas)
     * @param mtg modelo para la tabla de grupos
     */
    private void configurarTabla(ModeloTablaGrupos mtg) {
        JTable tablaGrupos = this.ventana.verTablaGrupos();
        tablaGrupos.setModel(mtg);
    }    
                                    
    @Override
    public void btnGuardarClic(ActionEvent evt) {
        int dni = 0;
        if (!this.ventana.verTxtDNI().getText().trim().isEmpty())
            dni = Integer.parseInt(this.ventana.verTxtDNI().getText().trim());
        String apellidos = this.ventana.verTxtApellidos().getText().trim();
        String nombres = this.ventana.verTxtNombres().getText().trim();        
        String cx = this.ventana.verTxtCX().getText().trim();
        String clave = new String(this.ventana.verPassClave().getPassword());
        String claveRepetida = new String(this.ventana.verPassRepetirClave().getPassword());
        if (this.alumno == null) //nuevo alumno
            this.nuevoAlumno(dni, apellidos, nombres, cx, clave, claveRepetida);
        else //modificar alumno
            this.modificarAlumno(apellidos, nombres, cx, clave, claveRepetida);
    }
    
    /**
     * Se encarga de la creación de un alumno
     * @param dni dni del alumno
     * @param apellidos apellidos del alumno
     * @param nombres nombres del alumno
     * @param cx cx del alumno
     * @param clave clave del alumno
     * @param claveRepetida clave (repetida) del alumno
     */
    private void nuevoAlumno(int dni, String apellidos, String nombres, String cx, String clave, String claveRepetida) {        
        IGestorAutores ga = GestorAutores.instanciar();
        String resultado = ga.nuevoAutor(dni, apellidos, nombres, cx, clave, claveRepetida);
        if (!resultado.equals(IGestorAutores.EXITO))
            JOptionPane.showMessageDialog(null, resultado, TITULO_NUEVO, JOptionPane.ERROR_MESSAGE);
        else
            this.ventana.dispose();                                    
    }
    
    /**
     * Se encarga de la modificación del alumno
     * @param apellidos apellidos del alumno
     * @param nombres nombres del alumno
     * @param cx cx del alumno
     * @param clave clave del alumno
     * @param claveRepetida clave (repetida) del alumno
     */    
    private void modificarAlumno(String apellidos, String nombres, String cx, String clave, String claveRepetida) {
        IGestorAutores ga = GestorAutores.instanciar();
        String resultado = ga.modificarAutor(this.alumno, apellidos, nombres, cx, clave, claveRepetida);
        if (!resultado.equals(IGestorAutores.EXITO)) 
            JOptionPane.showMessageDialog(null, resultado, TITULO_MODIFICAR, JOptionPane.ERROR_MESSAGE);
        else
            this.ventana.dispose();                                    
    }    
                           
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
    public void txtCXPresionarTecla(KeyEvent evt) {
        this.txtDocumentoPresionarTecla(evt);
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
        if (this.alumno != null) {
            this.configurarTabla(new ModeloTablaGrupos(this.alumno)); //se refrezca la tabla
        }
        //se hace esta comprobación para actualizar la tabla sólo en el caso que se esté modificando un alumno
        //y se esté volviendo de la ventana para agregar grupos
    }    
}
