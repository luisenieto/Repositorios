/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupos.controladores;

import autores.modelos.Autor;
import auxiliares.Apariencia;
import grupos.modelos.GestorGrupos;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import grupos.modelos.Grupo;
import grupos.modelos.ModeloTablaMiembros;
import grupos.vistas.VentanaModificarMiembros;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import interfaces.IControladorModificarMiembros;
import interfaces.IGestorGrupos;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumn;
import grupos.modelos.MiembroEnGrupo;
import grupos.modelos.ModeloComboRoles;


public class ControladorModificarMiembros implements IControladorModificarMiembros {       
    private VentanaModificarMiembros ventana;  
    private Grupo grupo;
    
    /**
     * Constructor
     * @param ventanaPadre (VentanaAMGrupo en este caso)
     * @param grupo grupo a modificar
     * @param autor autor sobre el cual hay que ver qué habilitar según sus grupos
     */    
    public ControladorModificarMiembros(Dialog ventanaPadre, Grupo grupo, Autor autor) {
        this.grupo = grupo;
        this.ventana = new VentanaModificarMiembros(this, ventanaPadre);
        this.ventana.setTitle(grupo.verNombre() + " - (" + autor.verApeYNom() + ")");
        this.ventana.setLocationRelativeTo(null);        
        Apariencia.asignarNimbusLookAndFeel("Nimbus");
        this.configurarTabla(new ModeloTablaMiembros()); //todos los autores
        this.ventana.setVisible(true);
    }
    
    /**
     * Configura la tabla de miembros asignándole un modelo y seleccionando las filas que se correspondan con los miembros del grupo
     * @param mtm modelo para la tabla de miembros
     */
    private void configurarTabla(ModeloTablaMiembros mtm) {
        JTable tablaMiembros = this.ventana.verTablaMiembros();
        tablaMiembros.setModel(mtm);

        tablaMiembros.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        //permite seleccionar múltiples filas no contiguas
        
        //se seleccionan las filas correspondientes a los miembros que ya tiene el grupo
        ListSelectionModel modeloSeleccion = tablaMiembros.getSelectionModel();        
        for(MiembroEnGrupo miembroEnGrupo : this.grupo.verMiembros()) {
            for(int fila = 0; fila < mtm.getRowCount(); fila++) {
                MiembroEnGrupo meg = mtm.verMiembroEnGrupo(fila);
                if (miembroEnGrupo.verMiembro().equals(meg.verMiembro())) {
                    meg.asignarRol(miembroEnGrupo.verRol());
                    modeloSeleccion.addSelectionInterval(fila, fila);
                    break;
                }
            }
        }
        
        TableColumn columnaRol = tablaMiembros.getColumnModel().getColumn(1);
        JComboBox combo = new JComboBox();
        combo.setModel(new ModeloComboRoles());
        columnaRol.setCellEditor(new DefaultCellEditor(combo));
    }
                       
    @Override
    public void btnTodosClic(ActionEvent evt) {
        JTable tablaMiembrosColaboradores = this.ventana.verTablaMiembros();
        ModeloTablaMiembros mtm = (ModeloTablaMiembros)tablaMiembrosColaboradores.getModel();
        
        tablaMiembrosColaboradores.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        //permite seleccionar múltiples filas no contiguas
        
        //se seleccionan todas las filas
        ListSelectionModel modeloSeleccion = tablaMiembrosColaboradores.getSelectionModel(); 
        
        modeloSeleccion.addSelectionInterval(0, mtm.getRowCount() - 1);
    }            
                           
    @Override
    public void btnNingunoClic(ActionEvent evt) {
        JTable tablaMiembrosColaboradores = this.ventana.verTablaMiembros();
        ListSelectionModel modeloSeleccion = tablaMiembrosColaboradores.getSelectionModel(); 
        
        modeloSeleccion.clearSelection();
    }        
                                
    @Override
    public void btnAceptarClic(ActionEvent evt) {
        int opcion = JOptionPane.showOptionDialog(null, CONFIRMACION, TITULO, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Sí", "No"}, this);       
        if (opcion == JOptionPane.YES_OPTION) { //se quieren modificar los miembros del grupo                        
            List<MiembroEnGrupo> miembrosViejos = new ArrayList<>();
            for(MiembroEnGrupo meg : this.grupo.verMiembros())
                miembrosViejos.add(meg);
            
            JTable tablaMiembros = this.ventana.verTablaMiembros();
            ModeloTablaMiembros modelo = (ModeloTablaMiembros)tablaMiembros.getModel();
            List<MiembroEnGrupo> miembrosNuevos = new ArrayList<>();
            int[] filasSeleccionadas = tablaMiembros.getSelectedRows();
            for (int i = 0; i < filasSeleccionadas.length; i++) {
                MiembroEnGrupo meg = modelo.verMiembroEnGrupo(filasSeleccionadas[i]);                
                miembrosNuevos.add(meg);            
            }
                       
            IGestorGrupos gg = GestorGrupos.instanciar();
            String resultado = gg.quitarMiembros(this.grupo, miembrosViejos);
            //se le quitan todos los miembros al grupo
            
            if (resultado.equals(IGestorGrupos.EXITO_MIEMBROS)) { //se pudieron quitarle todos los miembros
                resultado = gg.agregarMiembros(this.grupo, miembrosNuevos);
                //se le agregan los miembros nuevos al grupo
                if (resultado.equals(IGestorGrupos.EXITO_MIEMBROS)) { //se pudieron agregar los miembros nuevos
                    this.ventana.dispose(); 
                    this.ventana.getParent().requestFocus(); 
//              al preguntar si se quieren modificar los miembros y luego cerrar la ventana
//              la ventana padre no recibe el foco, pero eso esta línea                    
                }
                else
                    JOptionPane.showMessageDialog(null, resultado, grupo.verNombre() + " - " + TITULO, JOptionPane.ERROR_MESSAGE);
            }
            else
                JOptionPane.showMessageDialog(null, resultado, grupo.verNombre() + " - " + TITULO, JOptionPane.ERROR_MESSAGE);           
        }
    }
                                  
    @Override
    public void btnCancelarClic(ActionEvent evt) {
        this.ventana.dispose();
    }
}
