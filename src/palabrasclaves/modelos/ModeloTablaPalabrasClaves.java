/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palabrasclaves.modelos;

import interfaces.IGestorPalabrasClaves;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Clase para mostrar las palabras claves en una tabla
 */
public class ModeloTablaPalabrasClaves extends AbstractTableModel {
    public static final String COLUMNA_NOMBRE = "Nombre";
    //constantes para los nombres de las columnas 
    
    private List<PalabraClave> palabrasClaves = new ArrayList<>();
    //los datos los saca de GestorPalabrasClaves
    
    private List<String> nombresColumnas = Arrays.asList(new String[] {COLUMNA_NOMBRE});       
    //colección para guardar los nombres de las columnas
    
    private IGestorPalabrasClaves gpc = GestorPalabrasClaves.instanciar();
    
    /**
    * Constructor
    * @param nombre nombre que se usa para filtrar la búsqueda de palabras claves
    */                                                        
    public ModeloTablaPalabrasClaves(String nombre) {
        this.palabrasClaves = this.gpc.buscarPalabrasClaves(nombre);
    }    
    
    /**
    * Constructor
    */                                                        
    public ModeloTablaPalabrasClaves() {
        this.palabrasClaves = this.gpc.verPalabrasClaves();
    }
    
    
    /**
    * Obtiene el valor de la celda especificada
    * @param fila fila de la celda
    * @param columna columna de la celda
    * @return Object  - valor de la celda
    */                        
    @Override
    public Object getValueAt(int fila, int columna) {
        return this.palabrasClaves.get(fila);
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
        return this.palabrasClaves.size();
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
    * Devuelve la PalabraClave correspondiente a la fila especificada dentro de la tabla
    * Si se especifica una fila inválida devuelve null
    * @param fila fila dentro de la tabla
    * @return PalabraClave  - objeto PalabraClave correspondiente a la fila que se especifica
    * @see PalabraClave
    */        
    public PalabraClave verPalabraClave(int fila) {
        try {
            return this.palabrasClaves.get(fila);
        }
        catch(IndexOutOfBoundsException e) {
            return null;
        }
    }    
}
