/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idiomas.modelos;

import interfaces.IGestorIdiomas;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Clase para mostrar los idiomas en una tabla
 */
public class ModeloTablaIdiomas extends AbstractTableModel {
    public static final String COLUMNA_NOMBRE = "Nombre";
    //constantes para los nombres de las columnas 
    
    private List<Idioma> idiomas = new ArrayList<>();
    //los datos los saca de GestorIdiomas
    
    private List<String> nombresColumnas = Arrays.asList(new String[] {COLUMNA_NOMBRE});       
    //colección para guardar los nombres de las columnas
    
    private IGestorIdiomas gi = GestorIdiomas.instanciar(); 
    
    /**
    * Constructor
    * @param nombre nombre que se usa para filtrar la búsqueda de idiomas
    */                                                        
    public ModeloTablaIdiomas(String nombre) {
        this.idiomas = this.gi.buscarIdiomas(nombre);
    }    
    
    /**
    * Constructor
    */                                                        
    public ModeloTablaIdiomas() {
        this.idiomas = this.gi.verIdiomas();
    }
        
    /**
    * Obtiene el valor de la celda especificada
    * @param fila fila de la celda
    * @param columna columna de la celda
    * @return Object  - valor de la celda
    */                        
    @Override
    public Object getValueAt(int fila, int columna) {
        return this.idiomas.get(fila);
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
        return this.idiomas.size();
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
    * Devuelve el Idioma correspondiente a la fila especificada dentro de la tabla
    * Si se especifica una fila inválida devuelve null
    * @param fila fila dentro de la tabla
    * @return Idioma  - objeto Idioma correspondiente a la fila que se especifica
    * @see Idioma
    */        
    public Idioma verIdioma(int fila) {
        try {
            return this.idiomas.get(fila);
        }
        catch(IndexOutOfBoundsException e) {
            return null;
        }
    }        
}
