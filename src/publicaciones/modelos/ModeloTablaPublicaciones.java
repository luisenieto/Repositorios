/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package publicaciones.modelos;

import interfaces.IGestorPublicaciones;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Clase para mostrar los tipos de publicación en una tabla
 */
public class ModeloTablaPublicaciones extends AbstractTableModel {
    public static final String COLUMNA_TITULO = "Título";
    public static final String COLUMNA_AUTOR = "Autor";
    public static final String COLUMNA_ANIO = "Año";
    //constantes para los nombres de las columnas 
    
    private List<Publicacion> publicaciones;
    //los datos los saca de GestorPublicaciones
    
    private List<String> nombresColumnas = Arrays.asList(new String[] {COLUMNA_TITULO, COLUMNA_AUTOR, COLUMNA_ANIO});       
    //colección para guardar los nombres de las columnas
    
    private IGestorPublicaciones gp = GestorPublicaciones.instanciar(); 
    
    /**
    * Constructor
    * @param titulo título que se usa para filtrar la búsqueda de publicaciones
    */                                                        
    public ModeloTablaPublicaciones(String titulo) {
        this.publicaciones = this.gp.buscarPublicaciones(titulo);
    }    
    
    /**
    * Constructor
    */                                                        
    public ModeloTablaPublicaciones() {
        this.publicaciones = this.gp.verPublicaciones();
    }
        
    /**
    * Obtiene el valor de la celda especificada
    * @param fila fila de la celda
    * @param columna columna de la celda
    * @return Object  - valor de la celda
    */                        
    @Override
    public Object getValueAt(int fila, int columna) {
        Publicacion publicacion = this.publicaciones.get(fila);
        switch (columna) {
            case 0: return publicacion.verTitulo();
            case 1: return publicacion.verMiembroEnGrupo().verMiembro().verApeYNom();
            case 2: return publicacion.verFechaPublicacion().getYear();
            default: return publicacion.verTitulo();
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
        return this.publicaciones.size();
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
    * Devuelve la Publicacion correspondiente a la fila especificada dentro de la tabla
    * Si se especifica una fila inválida devuelve null
    * @param fila fila dentro de la tabla
    * @return Publicacion  - objeto Publicacion correspondiente a la fila que se especifica
    * @see Publicacion
    */        
    public Publicacion verPublicacion(int fila) {
        try {
            return this.publicaciones.get(fila);
        }
        catch(IndexOutOfBoundsException e) {
            return null;
        }
    }    
}
