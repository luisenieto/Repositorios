/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package publicaciones.modelos;

import autores.modelos.Autor;
import autores.modelos.GestorAutores;
import interfaces.IGestorAutores;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Clase para mostrar los autores en la ventana AMPublicacion
 */
public class ModeloTablaAutores extends AbstractTableModel {
    public static final String COLUMNA_NOMBRE = "Nombre";
    //constantes para los nombres de las columnas 
    
    private List<Autor> autores;
    //los datos los saca de GestorAutores o de la publicación especificada
    
    private List<String> nombresColumnas = Arrays.asList(new String[] {COLUMNA_NOMBRE});       
    //colección para guardar los nombres de las columnas
    
    private IGestorAutores ga = GestorAutores.instanciar(); 
        
    /**
    * Constructor
    */                                                        
    public ModeloTablaAutores() {
        this.autores = this.ga.verAutores(); //todos los autores
    }
    
    /**
    * Constructor
    * @param publicacion publicación de la cual se quieren ver sus autores
    */                                                        
//    public ModeloTablaAutores(Publicacion publicacion) {
//        this.autores = publicacion.verAutores(); //sólo los autores de la publicación
//    }
        
    /**
    * Obtiene el valor de la celda especificada
    * @param fila fila de la celda
    * @param columna columna de la celda
    * @return Object  - valor de la celda
    */                        
    @Override
    public Object getValueAt(int fila, int columna) {
        Autor autor = this.autores.get(fila);
        return autor.verApellidos() + ", " + autor.verNombres() + " (" + autor.verDNI() + ")";
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
        return this.autores.size();
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
    * Devuelve el autor correspondiente a la fila especificada dentro de la tabla
    * Si se especifica una fila inválida devuelve null
    * @param fila fila dentro de la tabla
    * @return Autor  - objeto Autor correspondiente a la fila que se especifica
    * @see Autor
    */        
    public Autor verAutor(int fila) {
        try {
            return this.autores.get(fila);
        }
        catch(IndexOutOfBoundsException e) {
            return null;
        }
    }    
}
