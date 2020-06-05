/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupos.modelos;

import autores.modelos.Autor;
import interfaces.IGestorGrupos;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Clase para mostrar los grupos en una tabla
 */
public class ModeloTablaGrupos extends AbstractTableModel {
    public static final String COLUMNA_NOMBRE = "Nombre";
    public static final String COLUMNA_DESCRIPCION = "Descripción";
    //constantes para los nombres de las columnas 
    private final String VALORES_NULOS = "-";
    //cadena usada para los valores nulos (descripción)
    
    private List<Grupo> grupos = new ArrayList<>();
    //los datos los saca de GestorGrupos o del autor
    //Si el autor es Admin, los saca de GestorGrupos
    //Si el autor no es Admin, de sus grupos
    
    private List<String> nombresColumnas = Arrays.asList(new String[] {COLUMNA_NOMBRE, COLUMNA_DESCRIPCION});       
    //colección para guardar los nombres de las columnas
    
    private IGestorGrupos gg = GestorGrupos.instanciar(); 
    
    /**
    * Constructor
    * @param nombre nombre que se usa para filtrar la búsqueda de grupos
    * @param autor autor del cual se quieren ver sus grupos
    */                                                        
    public ModeloTablaGrupos(Autor autor, String nombre) {
        if (autor.esSuperAdministrador())
            this.grupos = this.gg.buscarGrupos(nombre);
        else
            this.grupos = autor.buscarGrupos(nombre);
    }    
    
    /**
    * Constructor
    * @param autor autor del cual se quieren ver sus grupos
    */                                                        
    public ModeloTablaGrupos(Autor autor) {
        if (autor.esSuperAdministrador())
            this.grupos = this.gg.verGrupos();
        else {
            for(MiembroEnGrupo meg : autor.verGrupos())
                this.grupos.add(meg.verGrupo());
        }
    }
        
    /**
    * Obtiene el valor de la celda especificada
    * @param fila fila de la celda
    * @param columna columna de la celda
    * @return Object  - valor de la celda
    */                        
    @Override
    public Object getValueAt(int fila, int columna) {
        Grupo grupo = this.grupos.get(fila);
        switch (columna) {
            case 0: return grupo.verNombre();
            case 1: return (grupo.verDescripcion() == null ? this.VALORES_NULOS : grupo.verDescripcion());
            default: return grupo.verNombre();
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
        return this.grupos.size();
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
    * Devuelve el Grupo correspondiente a la fila especificada dentro de la tabla
    * Si se especifica una fila inválida devuelve null
    * @param fila fila dentro de la tabla
    * @return Grupo  - objeto Grupo correspondiente a la fila que se especifica
    * @see Grupo
    */        
    public Grupo verGrupo(int fila) {
        try {
            return this.grupos.get(fila);
        }
        catch(IndexOutOfBoundsException e) {
            return null;
        }
    }    
}
