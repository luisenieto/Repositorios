/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugares.modelos;

import interfaces.IGestorLugares;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Clase para mostrar los lugares de publicación en una tabla
 */
public class ModeloTablaLugares extends AbstractTableModel {
    public static final String COLUMNA_NOMBRE = "Nombre";
    //constantes para los nombres de las columnas 
    
    private List<Lugar> lugares = new ArrayList<>();
    //los datos los saca de GestorLugares
    
    private List<String> nombresColumnas = Arrays.asList(new String[] {COLUMNA_NOMBRE});       
    //colección para guardar los nombres de las columnas
    
    private IGestorLugares gl = GestorLugares.instanciar(); 
    
    /**
    * Constructor
    * @param nombre nombre que se usa para filtrar la búsqueda de lugares de publicación
    */                                                        
    public ModeloTablaLugares(String nombre) {
        this.lugares = this.gl.buscarLugares(nombre);
    }    
    
    /**
    * Constructor
    */                                                        
    public ModeloTablaLugares() {
        this.lugares = this.gl.verLugares();
    }
        
    /**
    * Obtiene el valor de la celda especificada
    * @param fila fila de la celda
    * @param columna columna de la celda
    * @return Object  - valor de la celda
    */                        
    @Override
    public Object getValueAt(int fila, int columna) {
        return this.lugares.get(fila);
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
        return this.lugares.size();
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
    * Devuelve el Lugar correspondiente a la fila especificada dentro de la tabla
    * Si se especifica una fila inválida devuelve null
    * @param fila fila dentro de la tabla
    * @return Lugar  - objeto Lugar correspondiente a la fila que se especifica
    * @see Lugar
    */        
    public Lugar verLugar(int fila) {
        try {
            return this.lugares.get(fila);
        }
        catch(IndexOutOfBoundsException e) {
            return null;
        }
    }        
}
