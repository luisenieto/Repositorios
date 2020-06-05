/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupos.modelos;

import autores.modelos.Autor;
import autores.modelos.GestorAutores;
import interfaces.IGestorAutores;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Clase para mostrar los miembros del grupo en la ventana AMGrupo
 */
public class ModeloTablaMiembros extends AbstractTableModel {
    public static final String COLUMNA_NOMBRE = "Nombre";
    public static final String COLUMNA_ROL = "Rol";
    //constantes para los nombres de las columnas 
    private final String VALORES_NULOS = "-";
    //cadena usada para los valores nulos (rol)
    
    private List<MiembroEnGrupo> miembros = new ArrayList<>();
    //los datos los saca de GestorAutores o del grupo especificado
    
    private List<String> nombresColumnas = Arrays.asList(new String[] {COLUMNA_NOMBRE, COLUMNA_ROL});       
    //colección para guardar los nombres de las columnas
    
    private IGestorAutores ga = GestorAutores.instanciar(); 
        
    /**
    * Constructor
    */                                                        
    public ModeloTablaMiembros() {
        List<Autor> autores = this.ga.verAutores(); //todos los autores
        for(Autor autor : autores) {
            MiembroEnGrupo meg = new MiembroEnGrupo(autor);
            //No importan ni el grupo ni el rol, es sólo para mostrar todos los autores
            this.miembros.add(meg);
        }
    }
    
    /**
    * Constructor
    * @param grupo grupo del cual se quieren ver sus miembros
    */                                                        
    public ModeloTablaMiembros(Grupo grupo) {
        if (grupo != null)
            this.miembros = grupo.verMiembros(); //sólo los miembros del grupo
    }
        
    /**
    * Obtiene el valor de la celda especificada
    * @param fila fila de la celda
    * @param columna columna de la celda
    * @return Object  - valor de la celda
    */                        
    @Override
    public Object getValueAt(int fila, int columna) {
        MiembroEnGrupo meg = this.miembros.get(fila);
        switch(columna) {
            case 0: return meg.verMiembro().verApellidos()  + ", " + meg.verMiembro().verNombres() + " (" + meg.verMiembro().verDNI() + ")";
            case 1: return (meg.verRol() == null ? this.VALORES_NULOS : meg.verRol());
            default: return meg.verMiembro().verApellidos()  + ", " + meg.verMiembro().verNombres() + " (" + meg.verMiembro().verDNI() + ")";
        }

    }
    
    /**
    * Obtiene la cantidad de columnas de la tabla
    * @return int  - cantidad de columnas de la tabla
    */                            
    @Override
    public int getColumnCount() { 
        return this.nombresColumnas.size();
    }

    /**
    * Obtiene la cantidad de filas de la tabla
    * @return int  - cantidad de filas de la tabla
    */                        
    @Override
    public int getRowCount() { 
        return this.miembros.size();
    }

    /**
    * Obtiene el nombre de una columna
    * @param columna columna sobre la que se quiere obtener el nombre
    * @return String  - nombre de la columna especificada
    */                        
    @Override
    public String getColumnName(int columna) {
        return this.nombresColumnas.get(columna);
    }
    
    /**
    * Devuelve el miembro correspondiente a la fila especificada dentro de la tabla
    * Si se especifica una fila inválida devuelve null
    * @param fila fila dentro de la tabla
    * @return MiembroEnGrupo  - objeto MiembroEnGrupo correspondiente a la fila que se especifica
    * @see MiembroEnGrupo
    */        
    public MiembroEnGrupo verMiembroEnGrupo(int fila) {
        try {
            return this.miembros.get(fila);
        }
        catch(IndexOutOfBoundsException e) {
            return null;
        }
    }        

// los siguientes 3 métodos se deben redefinir si se quiere editar en la tabla
    
    /**
     * Obtiene el tipo de datos de una columna
     * @param columna columna sobre la que se quiere obtener el tipo de datos
     * @return Class  - clase de la columna especificada
    */                        
    @Override
    public Class getColumnClass(int columna) {      
        return (columna == 0 ? String.class : Rol.class);        
    }
    
    /**
     * Determina si una celda es o no editable. 
     * Este método se tiene que redefinir en caso que la tabla sea editable
     * @param fila fila a la cual pertenece la celda
     * @param columna columna a la cual pertenece la celda
     * @return boolean  - true si la celda es editable, false en caso contrario
    */                        
    @Override
    public boolean isCellEditable(int fila, int columna) {
        return (columna != 0);
    }
    
    /**
     * Asigna el valor de una celda. 
     * Este método se tiene que redefinir en caso que la tabla sea editable
     * @param unValor valor que se quiere asignar a la celda
     * @param fila fila a la cual pertenece la celda
     * @param columna columna a la cual pertenece la celda
    */                            
    @Override
    public void setValueAt(Object unValor, int fila, int columna) {
        if (columna == 1) {
            MiembroEnGrupo meg = this.miembros.get(fila);
            meg.asignarRol((Rol)unValor);
            fireTableCellUpdated(fila, columna);
        }
    }    
}
