/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package publicaciones.modelos;

import autores.modelos.Autor;
import interfaces.IGestorPalabrasClaves;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import palabrasclaves.modelos.GestorPalabrasClaves;
import palabrasclaves.modelos.PalabraClave;

/**
 * Clase para mostrar los autores en la ventana AMPublicacion
 */
public class ModeloTablaPalabrasClaves extends AbstractTableModel {
    public static final String COLUMNA_NOMBRE = "Nombre";
    //constantes para los nombres de las columnas 
    
    private List<PalabraClave> palabrasClaves;
    //los datos los saca de GestorPalabrasClaves o de la publicación especificada
    
    private List<String> nombresColumnas = Arrays.asList(new String[] {COLUMNA_NOMBRE});       
    //colección para guardar los nombres de las columnas
    
    private IGestorPalabrasClaves gpc = GestorPalabrasClaves.instanciar(); 
        
    /**
    * Constructor
    */                                                        
    public ModeloTablaPalabrasClaves() {
        this.palabrasClaves = this.gpc.verPalabrasClaves(); //todas las palabras claves
    }
    
    /**
    * Constructor
    * @param publicacion publicación de la cual se quieren ver sus autores
    */                                                        
//    public ModeloTablaPalabrasClaves(Publicacion publicacion) {
//        this.palabrasClaves = publicacion.verPalabrasClaves(); //sólo las palabras claves de la publicación
//    }
        
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
    * Devuelve la palabra clave correspondiente a la fila especificada dentro de la tabla
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
