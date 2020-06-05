/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autores.modelos;

import interfaces.IGestorAutores;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Clase para mostrar los profesores en una tabla
 */
public class ModeloTablaAlumnos extends AbstractTableModel {
    public static final String COLUMNA_DNI = "DNI";    
    public static final String COLUMNA_APELLIDOS = "Apellidos";    
    public static final String COLUMNA_NOMBRES = "Nombres";
    public static final String COLUMNA_CX = "CX";
    //constantes para los nombres de las columnas 
    
    private List<Alumno> alumnos = new ArrayList<>();
    //los datos los saca de GestorAutores
    
    private List<String> nombresColumnas = Arrays.asList(new String[] {COLUMNA_DNI, COLUMNA_APELLIDOS, COLUMNA_NOMBRES, COLUMNA_CX});       
    //colección para guardar los nombres de las columnas
    
    private IGestorAutores ga = GestorAutores.instanciar(); 
    
    /**
    * Constructor
    * @param apellidos apellidos para filtrar la búsqueda de grupos
    */                                                        
    public ModeloTablaAlumnos(String apellidos) {
        this.alumnos = this.ga.buscarAlumnos(apellidos);
    }    
    
    /**
    * Constructor
    * @param autor autor que quiere ver los otros autores
    */                                                        
    public ModeloTablaAlumnos(Autor autor) {
        if (autor.esSuperAdministrador()) //si es super administrador puede ver todos los autores
            this.alumnos = this.ga.verAlumnos();
        else { //si no es super administrador sólo se ve él mismo
            if (autor instanceof Alumno)
                this.alumnos.add((Alumno)autor);  
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
        Alumno alumno = this.alumnos.get(fila);
        switch (columna) {
            case 0: return Integer.toString(alumno.verDNI());
            case 1: return alumno.verApellidos();
            case 2: return alumno.verNombres();
            case 3: return alumno.verCX();
            default: return Integer.toString(alumno.verDNI());
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
        return this.alumnos.size();
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
    * Devuelve el Alumno correspondiente a la fila especificada dentro de la tabla
    * Si se especifica una fila inválida devuelve null
    * @param fila fila dentro de la tabla
    * @return Alumno  - objeto Alumno correspondiente a la fila que se especifica
    * @see Alumno
    */        
    public Alumno verAlumno(int fila) {
        try {
            return this.alumnos.get(fila);
        }
        catch(IndexOutOfBoundsException e) {
            return null;
        }
    }    
}
