/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autores.controladores;

import autores.modelos.Alumno;
import autores.modelos.Autor;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import autores.vistas.VentanaAutores;
import auxiliares.Apariencia;
import interfaces.IControladorAutores;
import interfaces.IGestorAutores;
import autores.modelos.ModeloTablaAlumnos;
import autores.modelos.ModeloTablaProfesores;
import autores.modelos.GestorAutores;
import autores.modelos.Profesor;
import interfaces.IControladorAMAlumno;
import interfaces.IControladorAMProfesor;
import interfaces.IGestorPermisos;
import permisos.modelos.GestorPermisos;

public class ControladorAutores implements IControladorAutores {
    private VentanaAutores ventana;
    
    private int filaProfesoresSeleccionada = -1;
    private int filaAlumnosSeleccionada = -1;
    //sirven para manejar las tablas de alumnos y profesores
    
    private Autor autor;
    //autor que se logueó
    
    /**
     * Constructor
     * Muestra la ventana de personas de forma modal
     * @param ventanaPadre ventana padre (VentanaPrincipal en este caso)
     * @param autor autor sobre el cual hay que ver qué habilitar según sus grupos
     */
    public ControladorAutores(Frame ventanaPadre, Autor autor) {
        this.autor = autor;
        this.ventana = new VentanaAutores(this, ventanaPadre);
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
        
        this.ventana.verBtnNuevoProfesor().setEnabled(gp.puedeCrearAutores(this.autor));
        this.ventana.verBtnBorrarProfesor().setEnabled(gp.puedeBorrarProfesores(this.autor));
        this.ventana.verBtnModificarProfesor().setEnabled(gp.puedeModificarProfesores(this.autor));
        this.ventana.verBtnBuscarProfesor().setEnabled(gp.puedeBuscarProfesores(this.autor));
        this.ventana.verTxtApellidosProfesor().setEnabled(gp.puedeBuscarProfesores(this.autor));
        
        this.ventana.verBtnNuevoAlumno().setEnabled(gp.puedeCrearAutores(this.autor));
        this.ventana.verBtnBorrarAlumno().setEnabled(gp.puedeBorrarAlumnos(this.autor));
        this.ventana.verBtnModificarAlumno().setEnabled(gp.puedeModificarAlumnos(this.autor));
        this.ventana.verBtnBuscarAlumno().setEnabled(gp.puedeBuscarAlumnos(this.autor));
        this.ventana.verTxtApellidosAlumno().setEnabled(gp.puedeBuscarAlumnos(this.autor));        
    }
    
    /**
     * Asigna la fila seleccionada en la tabla tablaProfesores
     */
    private void asignarFilaProfesoresSeleccionada() {
        JTable tablaProfesores = this.ventana.verTablaProfesores();
        this.filaProfesoresSeleccionada = tablaProfesores.getSelectedRow();        
    }    
    
    /**
     * Asigna la fila seleccionada en la tabla tablaAlumnos
     */
    private void asignarFilaAlumnosSeleccionada() {
        JTable tablaAlumnos = this.ventana.verTablaAlumnos();
        this.filaAlumnosSeleccionada = tablaAlumnos.getSelectedRow();        
    }        
                         
    @Override
    public void btnNuevoProfesorClic(ActionEvent evt) {
        IControladorAMProfesor controlador = new ControladorAProfesor(this.ventana);
    }
                               
    @Override
    public void btnNuevoAlumnoClic(ActionEvent evt) {
        IControladorAMAlumno controlador = new ControladorAAlumno(this.ventana);
    }    
                          
    @Override
    public void btnModificarProfesorClic(ActionEvent evt) {
        this.asignarFilaProfesoresSeleccionada();
        if (this.filaProfesoresSeleccionada != -1) { //hay una fila seleccionada
            ModeloTablaProfesores modelo = (ModeloTablaProfesores)this.ventana.verTablaProfesores().getModel();
            Profesor profesor = modelo.verProfesor(this.filaProfesoresSeleccionada);            
            if (profesor != null) {
                IControladorAMProfesor controlador = new ControladorMProfesor(this.ventana, profesor);
            }
        }
    }
                               
    @Override
    public void btnModificarAlumnoClic(ActionEvent evt) {
        this.asignarFilaAlumnosSeleccionada();
        if (this.filaAlumnosSeleccionada != -1) { //hay una fila seleccionada
            ModeloTablaAlumnos modelo = (ModeloTablaAlumnos)this.ventana.verTablaAlumnos().getModel();
            Alumno alumno = modelo.verAlumno(this.filaAlumnosSeleccionada);            
            if (alumno != null) {
                IControladorAMAlumno controlador = new ControladorMAlumno(this.ventana, alumno);                    
            }
        }
    }    
                               
    @Override
    public void btnBorrarProfesorClic(ActionEvent evt) {
        this.asignarFilaProfesoresSeleccionada();
        if (this.filaProfesoresSeleccionada != -1) { //hay una fila seleccionada
            int opcion = JOptionPane.showOptionDialog(null, CONFIRMACION_PROFESOR, TITULO, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Sí", "No"}, this);       
            if (opcion == JOptionPane.YES_OPTION) { //se quiere borrar el profesor                
                ModeloTablaProfesores modelo = (ModeloTablaProfesores)this.ventana.verTablaProfesores().getModel();
                Profesor profesor = modelo.verProfesor(this.filaProfesoresSeleccionada);
                if (profesor != null) {
                    IGestorAutores ga = GestorAutores.instanciar();
                    String resultado = ga.borrarAutor(profesor);
                    if (!resultado.equals(IGestorAutores.EXITO)) //no se pudo borrar el profesor
                        JOptionPane.showMessageDialog(null, resultado, TITULO, JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
                               
    @Override
    public void btnBorrarAlumnoClic(ActionEvent evt) {
        this.asignarFilaAlumnosSeleccionada();
        if (this.filaAlumnosSeleccionada != -1) { //hay una fila seleccionada
            int opcion = JOptionPane.showOptionDialog(null, CONFIRMACION_ALUMNO, TITULO, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Sí", "No"}, this);       
            if (opcion == JOptionPane.YES_OPTION) { //se quiere borrar el alumno                
                ModeloTablaAlumnos modelo = (ModeloTablaAlumnos)this.ventana.verTablaAlumnos().getModel();
                Alumno alumno = modelo.verAlumno(this.filaAlumnosSeleccionada);
                if (alumno != null) {
                    IGestorAutores ga = GestorAutores.instanciar();
                    String resultado = ga.borrarAutor(alumno);
                    if (!resultado.equals(IGestorAutores.EXITO)) //no se pudo borrar el alumno
                        JOptionPane.showMessageDialog(null, resultado, TITULO, JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }    
                        
    @Override
    public void btnVolverClic(ActionEvent evt) {
        this.ventana.dispose();
    }
                          
    @Override
    public void btnBuscarProfesorClic(ActionEvent evt) {
        ModeloTablaProfesores mtp;
        
        String apellidos = this.ventana.verTxtApellidosProfesor().getText().trim();
        if (apellidos.isEmpty())
            mtp = new ModeloTablaProfesores(this.autor);
        else
            mtp = new ModeloTablaProfesores(apellidos);
        this.configurarTabla(mtp);        
    }
                                
    @Override
    public void btnBuscarAlumnoClic(ActionEvent evt) {
        ModeloTablaAlumnos mta;
        
        String apellidos = this.ventana.verTxtApellidosAlumno().getText().trim();
        if (apellidos.isEmpty())
            mta = new ModeloTablaAlumnos(this.autor);
        else
            mta = new ModeloTablaAlumnos(apellidos);
        this.configurarTabla(mta);        
    }    
    
    @Override
    public void ventanaObtenerFoco(WindowEvent evt) {
        this.configurarTabla(new ModeloTablaProfesores(this.autor)); 
        this.configurarTabla(new ModeloTablaAlumnos(this.autor));
        IGestorPermisos gp = GestorPermisos.instanciar();
        this.ventana.verBtnBorrarProfesor().setEnabled(gp.puedeBorrarProfesores(this.autor));
        this.ventana.verBtnModificarProfesor().setEnabled(gp.puedeModificarProfesores(this.autor));
        this.ventana.verBtnBuscarProfesor().setEnabled(gp.puedeBuscarProfesores(this.autor));
        this.ventana.verTxtApellidosProfesor().setEnabled(gp.puedeBuscarProfesores(this.autor));
        
        this.ventana.verBtnBorrarAlumno().setEnabled(gp.puedeBorrarAlumnos(this.autor));
        this.ventana.verBtnModificarAlumno().setEnabled(gp.puedeModificarAlumnos(this.autor));
        this.ventana.verBtnBuscarAlumno().setEnabled(gp.puedeBuscarAlumnos(this.autor));
        this.ventana.verTxtApellidosAlumno().setEnabled(gp.puedeBuscarAlumnos(this.autor));
    }    
    
    /**
     * Configura la tabla de profesores asignándole un modelo y seleccionando la primera fila (si hay filas)
     * @param mtp modelo para la tabla de profesores
     */
    private void configurarTabla(ModeloTablaProfesores mtp) {
        JTable tablaProfesores = this.ventana.verTablaProfesores();
        tablaProfesores.setModel(mtp);
                
        if (mtp.getRowCount() > 0) { //si hay filas, se selecciona la primera
            this.filaProfesoresSeleccionada = 0;
            tablaProfesores.setRowSelectionInterval(this.filaProfesoresSeleccionada, this.filaProfesoresSeleccionada);                           
            this.ventana.verBtnBorrarProfesor().setEnabled(true);
            this.ventana.verBtnModificarProfesor().setEnabled(true);            
        }
        else {
            this.filaProfesoresSeleccionada = -1;   
//            this.ventana.verTxtApellidosProfesor().setEditable(false);
            this.ventana.verBtnBorrarProfesor().setEnabled(false);
            this.ventana.verBtnModificarProfesor().setEnabled(false);
        }
    }    
    
    /**
     * Configura la tabla de alumnos asignándole un modelo y seleccionando la primera fila (si hay filas)
     * @param mta modelo para la tabla de alumnos
     */
    private void configurarTabla(ModeloTablaAlumnos mta) {
        JTable tablaAlumnos = this.ventana.verTablaAlumnos();
        tablaAlumnos.setModel(mta);
        
        if (mta.getRowCount() > 0) { //si hay filas, se selecciona la primera
            this.filaAlumnosSeleccionada = 0;
            tablaAlumnos.setRowSelectionInterval(this.filaAlumnosSeleccionada, this.filaAlumnosSeleccionada);                           
            this.ventana.verBtnBorrarAlumno().setEnabled(true);
            this.ventana.verBtnModificarAlumno().setEnabled(true);
        }
        else {
            this.filaAlumnosSeleccionada = -1;  
//            this.ventana.verTxtApellidosAlumno().setEditable(false);
            this.ventana.verBtnBorrarAlumno().setEnabled(false);
            this.ventana.verBtnModificarAlumno().setEnabled(false);
        }
    }    
              
    @Override
    public void txtApellidosProfesorPresionarTecla(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER)
            this.btnBuscarProfesorClic(null); // no importa el evento
    }
       
    @Override
    public void txtApellidosAlumnoPresionarTecla(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER)
            this.btnBuscarAlumnoClic(null); // no importa el evento
    }    
}
