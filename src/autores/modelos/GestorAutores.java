/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autores.modelos;

import grupos.modelos.GestorGrupos;
import grupos.modelos.Grupo;
import interfaces.IControladorPrincipal;
import interfaces.IGestorAutores;
import interfaces.IGestorGrupos;
import interfaces.IGestorPublicaciones;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import grupos.modelos.MiembroEnGrupo;
import publicaciones.modelos.GestorPublicaciones;

public class GestorAutores implements IGestorAutores {
    private final String NOMBRE_ARCHIVO_PROFESORES = "./Profesores.txt";
    //nombre del archivo con los profesores    
    private final String NOMBRE_ARCHIVO_ALUMNOS = "./Alumnos.txt";
    //nombre del archivo con los alumnos    
    private final char SEPARADOR = ','; 
    //caracter usado como separador      
    
    private List<Autor> autores = new ArrayList<>();    
    private static GestorAutores gestor;
    
    private boolean problemasConArchivoProfesores;
    private boolean problemasConArchivoAlumnos;
    //sirven para saber si hubo o no problemas con los archivos
        
    /**
     * Constructor
    */                                            
    private GestorAutores() {
        String resultado = this.leerArchivoAlumnos();
        if ((resultado.equals(LECTURA_ALUMNOS_ERROR)) || (resultado.equals(CREACION_ALUMNOS_ERROR))) {
            JOptionPane.showMessageDialog(null, resultado, IControladorPrincipal.TITULO, JOptionPane.ERROR_MESSAGE);          
            this.problemasConArchivoAlumnos = true;
        }
        else {
            this.problemasConArchivoAlumnos = false;
        }
        
        resultado = this.leerArchivoProfesores();
        if ((resultado.equals(LECTURA_PROFESORES_ERROR)) || (resultado.equals(CREACION_PROFESORES_ERROR))) {
            JOptionPane.showMessageDialog(null, resultado, IControladorPrincipal.TITULO, JOptionPane.ERROR_MESSAGE);          
            this.problemasConArchivoProfesores = true;
        }
        else {
            this.problemasConArchivoProfesores = false;
            Collections.sort(this.autores);
        }
    }
    
    /**
     * Método estático que permite crear una única instancia de GestorAutores
     * @return GestorAutores
    */                                                            
    public static GestorAutores instanciar() {
        if (gestor == null) 
            gestor = new GestorAutores();            
        return gestor;
    } 
    
    @Override
    public String nuevoAutor(int dni, String apellidos, String nombres, Cargo cargo, String clave, String claveRepetida) {
        if (!this.problemasConArchivoProfesores) {
            if (!this.validadDNI(dni))
                return ERROR_DNI;
            
            if (!this.validarApellidos(apellidos))
                return ERROR_APELLIDOS;
            
            if (!this.validarNombres(nombres))
                return ERROR_NOMBRES;
            
            if (!this.validarCargo(cargo))
                return ERROR_CARGO;
            
            if (!this.validarClaves(clave, claveRepetida))
                return ERROR_CLAVES;
            else {
                Autor profesor = new Profesor(dni, apellidos, nombres, clave, cargo);
                if (!this.autores.contains(profesor)) { //no existe este profesor
                    this.autores.add(profesor);
                    Collections.sort(this.autores);
                    String resultado = this.escribirArchivoProfesores();
                    return (resultado.equals(ESCRITURA_PROFESORES_OK) ? EXITO : resultado);
                }
                else //ya existe un profesor/alumno con este documento
                    return PROFESORES_DUPLICADOS;
            }            
        }
        else
            return PROBLEMAS_ES;
    }

    @Override
    public String nuevoAutor(int dni, String apellidos, String nombres, String cx, String clave, String claveRepetida) {
        if (!this.problemasConArchivoAlumnos) {
            if (!this.validadDNI(dni))
                return ERROR_DNI;
            
            if (!this.validarApellidos(apellidos))
                return ERROR_APELLIDOS;
            
            if (!this.validarNombres(nombres))
                return ERROR_NOMBRES;
            
            if (!this.validarCX(cx))
                return ERROR_CX;
            
            if (!this.validarClaves(clave, claveRepetida))
                return ERROR_CLAVES;   
            else {
                Autor alumno = new Alumno(dni, apellidos, nombres, clave, cx);
                if (!this.autores.contains(alumno)) { //no existe este alumno
                    this.autores.add(alumno);
                    Collections.sort(this.autores);
                    String resultado = this.escribirArchivoAlumnos();
                    return (resultado.equals(ESCRITURA_ALUMNOS_OK) ? EXITO : resultado);
                }
                else //ya existe un alumno/profesor con este documento/cx
                    return ALUMNOS_DUPLICADOS;
            }
        }
        else
            return PROBLEMAS_ES;
    }
    
    /**
     * Valida que el dni del autor sea un número positivo
     * @param dni dni del autor
     * @return boolean  - true si el dni del autor es positivo, false en caso contrario
     */
    private boolean validadDNI(int dni) {
        return dni > 0;
    }
    
    /**
     * Valida que los apellidos del autor sean correctos
     * @param apellidos apellidos del autor
     * @return boolean  - true si los apellidos del autor son correctos, false en caso contrario
     */
    private boolean validarApellidos(String apellidos) {
        if((apellidos != null) && (!apellidos.trim().isEmpty()))  //apellidos correctos
            return true;
        else //apellidos incorrectos
            return false;
    }
    
    /**
     * Valida que los nombres del autor sean correctos
     * @param nombres nombres del autor
     * @return boolean  - true si los nombres del autor son correctos, false en caso contrario
     */
    private boolean validarNombres(String nombres) {
        if((nombres != null) && (!nombres.trim().isEmpty()))  //nombres correctos
            return true;
        else //nombres incorrectos
            return false;
    }
    
    /**
     * Valida que el cargo del profesor sea correcto
     * @param cargo cargo del profesor
     * @return boolean  - true si el cargo del profesor es correcto, false en caso contrario
     */
    private boolean validarCargo(Cargo cargo) {
        return (cargo != null);
    }
    
    /**
     * Valida que CX del alumno sea correcto
     * @param cx cx del alumno
     * @return boolean  - true si cx del alumno es correcto, false en caso contrario
     */
    private boolean validarCX(String cx) {
        if((cx != null) && (!cx.trim().isEmpty()))  //cx correcto
            return true;
        else //cx incorrecto
            return false;
    }
    
    /**
     * Valida que las 2 claves para el autor sean correctas (no vacías e iguales)
     * @param clave clave del autor
     * @param claveRepetida clave (repetida) del autor
     * @return boolean  - true si las 2 claves para el autor son correctas, false en caso contrario
     */
    private boolean validarClaves(String clave, String claveRepetida) {
        if ((clave != null) && (!clave.trim().isEmpty()) && (claveRepetida != null) && (!claveRepetida.trim().isEmpty()) && (clave.equals(claveRepetida)))
            return true;
        else
            return false;
    }
    
    
    
    @Override
    public String modificarAutor(Autor autor, String apellidos, String nombres, Cargo cargo, String clave, String claveRepetida) {
        if (!this.problemasConArchivoProfesores) {
            if ((this.existeEsteAutor(autor)) && (autor instanceof Profesor)) {
                try {
                    if (!this.validarApellidos(apellidos))
                        return ERROR_APELLIDOS;
                    
                    if (!this.validarNombres(nombres))
                        return ERROR_NOMBRES;
                    
                    if (!this.validarCargo(cargo))
                        return ERROR_CARGO;
                    
                    if (!this.validarClaves(clave, claveRepetida))
                        return ERROR_CLAVES;
                    else {
                        Profesor profesor = (Profesor)autor;
                        profesor.asignarApellidos(apellidos);
                        profesor.asignarNombres(nombres);
                        profesor.asignarCargo(cargo);    
                        profesor.asignarClave(clave);
                        String resultado = this.escribirArchivoProfesores();                      
                        return (resultado.equals(ESCRITURA_PROFESORES_OK) ? EXITO : resultado);
                    }
                }
                catch(ClassCastException e) {
                    return PROFESOR_INEXISTENTE;
                }
            }
            else
                return PROFESOR_INEXISTENTE;
        }
        else
            return PROBLEMAS_ES;
    }    
    
    @Override
    public String modificarAutor(Autor autor, String apellidos, String nombres, String cx, String clave, String claveRepetida) { 
        if (!this.problemasConArchivoProfesores) {
            if ((this.existeEsteAutor(autor)) && (autor instanceof Alumno)) {
                try {
                    if (!this.validarApellidos(apellidos))
                        return ERROR_APELLIDOS;
                    
                    if (!this.validarNombres(nombres))
                        return ERROR_NOMBRES;
                    
                    if (!this.validarCX(cx))
                        return ERROR_CX;
                    
                    if (!this.validarClaves(clave, claveRepetida))
                        return ERROR_CLAVES;
                    else {
                        Alumno alumno = (Alumno)autor;
                        alumno.asignarApellidos(apellidos);
                        alumno.asignarNombres(nombres);
                        alumno.asignarCX(cx);
                        alumno.asignarClave(clave);
                        String resultado = this.escribirArchivoAlumnos();                      
                        return (resultado.equals(ESCRITURA_ALUMNOS_OK) ? EXITO : resultado);
                    }
                }
                catch(ClassCastException e) {
                    return ALUMNO_INEXISTENTE;
                }            
            }
            else
                return ALUMNO_INEXISTENTE;
        }
        else
            return PROBLEMAS_ES;
    }        
    
    @Override
    public boolean existeEsteAutor(Autor autor) {
        if (autor == null)
            return false;
        else {
            for(Autor a : this.autores) {
                if (a.equals(autor))
                    return true;
            }
            return false;
        }        
    }
    
    @Override
    public List<Profesor> buscarProfesores(String apellidos) {
        List<Profesor> profesoresBuscados = new ArrayList<>();
        if (apellidos != null) {            
            for(Autor autor : this.autores) {
                if (autor instanceof Profesor) {
                    if (autor.verApellidos().toLowerCase().contains(apellidos.toLowerCase()))
                        profesoresBuscados.add((Profesor)autor);
                }
            }            
        }
        return profesoresBuscados;
    }    
        
    @Override
    public List<Alumno> buscarAlumnos(String apellidos) {
        List<Alumno> alumnosBuscados = new ArrayList<>();
        if (apellidos != null) {            
            for(Autor autor : this.autores) {
                if (autor instanceof Alumno) {
                    if (autor.verApellidos().toLowerCase().contains(apellidos.toLowerCase()))
                        alumnosBuscados.add((Alumno)autor);
                }
            }            
        }
        return alumnosBuscados;  
    }
    
    @Override
    public String borrarAutor(Autor autor) {
        if (this.existeEsteAutor(autor)) {
            if ((!this.problemasConArchivoAlumnos) || (!this.problemasConArchivoProfesores)) {
                IGestorPublicaciones gp = GestorPublicaciones.instanciar();
                if (gp.hayPublicacionesConEsteAutor(autor))  //hay al menos una publicación con este autor
                    return PUBLICACION_CON_AUTOR;
                else { //no hay publicaciones con este autor
                    this.autores.remove(autor);
                    if (autor instanceof Profesor) {
                        String resultado = this.escribirArchivoProfesores();
                        return (resultado.equals(ESCRITURA_PROFESORES_OK) ? EXITO : ESCRITURA_PROFESORES_ERROR);
                    }
                    else {
                        String resultado = this.escribirArchivoAlumnos();
                        return (resultado.equals(ESCRITURA_ALUMNOS_OK) ? EXITO : ESCRITURA_ALUMNOS_ERROR);                
                    }
                }
            }
            else
                return PROBLEMAS_ES;
        }
        else
            return AUTOR_INEXISTENTE;
    }
    
    @Override
    public Autor verAutor(int dni) {
        for(Autor autor : this.autores) {
            if (autor.verDNI() == dni)
                return autor;
        }
        return null;        
    }
        
    @Override
    public List<Autor> verAutores() {
//        List<Autor> autoresSinAdmin = new ArrayList<>();
//        for(Autor autor : this.autores) {
//            if (autor.verDNI() > 0) //no se muestra a Admin
//               autoresSinAdmin.add(autor);
//        }
//        return autoresSinAdmin;
        
        return this.autores;
    }
    
    @Override
    public List<Profesor> verProfesores() {
        List<Profesor> profesores = new ArrayList<>();
        List<Autor> autoresSinAdmin = this.verAutores();
        for(Autor autor : this.autores) {
            if (autor instanceof Profesor)
                profesores.add((Profesor)autor);
        }
        return profesores;
    }
    
    @Override
    public List<Alumno> verAlumnos() {
        List<Alumno> alumnos = new ArrayList<>();
        List<Autor> autoresSinAdmin = this.verAutores();
        for(Autor autor : this.autores) {
            if (autor instanceof Alumno)
                alumnos.add((Alumno)autor);
        }
        return alumnos;
    }
    
//    @Override
//    public String agregarGrupos(Autor autor, List<Grupo> grupos) {
//        if (this.existeEsteAutor(autor)) {
//            if (grupos != null) {
//                autor.agregarGrupos(grupos);
//                IGestorGrupos gg = GestorGrupos.instanciar();
//                String resultado = gg.actualizarGrupos();
//                return (resultado.equals(IGestorGrupos.ESCRITURA_OK) ? EXITO_GRUPOS: resultado);
//            }
//            else
//                return GRUPOS_INEXISTENTES;
//        }
//        else
//            return AUTOR_INEXISTENTE;
//    }
    
    @Override
    public String agregarGrupos(Autor autor, List<MiembroEnGrupo> grupos) {
        if (this.existeEsteAutor(autor)) {
            if (grupos != null) {
                autor.agregarGrupos(grupos);
                IGestorGrupos gg = GestorGrupos.instanciar();
                String resultado = gg.actualizarGrupos();
                return (resultado.equals(IGestorGrupos.ESCRITURA_OK) ? EXITO_GRUPOS: resultado);
            }
            else
                return GRUPOS_INEXISTENTES;
        }
        else
            return AUTOR_INEXISTENTE;
    }    
    
//    @Override
//    public String quitarGrupos(Autor autor, List<Grupo> grupos) {
//        if (this.existeEsteAutor(autor)) {
//            if (grupos != null) {
//                autor.quitarGrupos(grupos);
//                IGestorGrupos gg = GestorGrupos.instanciar();
//                String resultado = gg.actualizarGrupos();
//                return (resultado.equals(IGestorGrupos.ESCRITURA_OK) ? EXITO_GRUPOS: resultado);                
//            }
//            else
//                return GRUPOS_INEXISTENTES;
//        }
//        else
//            return AUTOR_INEXISTENTE;
//    }
    
    @Override
    public String quitarGrupos(Autor autor, List<MiembroEnGrupo> grupos) {
        if (this.existeEsteAutor(autor)) {
            if (grupos != null) {
                autor.quitarGrupos(grupos);
                IGestorGrupos gg = GestorGrupos.instanciar();
                String resultado = gg.actualizarGrupos();
                return (resultado.equals(IGestorGrupos.ESCRITURA_OK) ? EXITO_GRUPOS: resultado);                
            }
            else
                return GRUPOS_INEXISTENTES;
        }
        else
            return AUTOR_INEXISTENTE;
    }
    
    @Override
    public boolean hayAutoresConEsteGrupo(Grupo grupo) {
        for(Autor autor : this.autores) {
            for(MiembroEnGrupo meg : autor.verGrupos()) {
                if (meg.verGrupo().equals(grupo))
                    return true;
            }
        }
        return false;
    }
            
    /**
     * Lee del archivo de texto y carga el ArrayList empleando un try con recursos
     * https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
     * Formato del archivo (suponiendo que la coma sea el separador):
     *  dni 1,apellidos 1,nombres 1,clave 1,CX 1 
     *  dni 2,apellidos 2,nombres 2,clave 2,CX 2 
     *  dni 3,apellidos 3,nombres 3,clave 3,CX 3 
     *  dni 4,apellidos 4,nombres 4,clave 4,CX 4 
     * @return String  - cadena con el resultado de la operacion (LECTURA_ALUMNOS_ERROR | LECTURA_ALUMNOS_OK | CREACION_ALUMNOS_OK | CREACION_ALUMNOS_ERROR)
     */
    private String leerArchivoAlumnos() {
        File file = new File(NOMBRE_ARCHIVO_ALUMNOS);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String cadena;
                while((cadena = br.readLine()) != null) {
                    String[] vector = cadena.split(Character.toString(SEPARADOR));
                    int dni = Integer.parseInt(vector[0]); 
                    String apellidos = vector[1];
                    String nombres = vector[2];  
                    String clave = vector[3];
                    String cx = vector[4];
                    Autor alumno = new Alumno(dni, apellidos, nombres, clave, cx);
                    this.autores.add(alumno);
                }
                return LECTURA_ALUMNOS_OK;
            }
            catch (IOException ioe) {
                return LECTURA_ALUMNOS_ERROR;
            }
        }
        else {
            return this.crearArchivoAlumnos();
        }
    }          
    
    /**
     * Crea el archivo de alumnos
     * @return String  - cadena con el resultado de la operacion (CREACION_ALUMNOS_OK | CREACION_ALUMNOS_ERROR)
     */
    private String crearArchivoAlumnos() {
        File file = new File(NOMBRE_ARCHIVO_ALUMNOS);
        try (FileWriter fw = new FileWriter(file)) {     
            return CREACION_ALUMNOS_OK;
        } 
        catch (IOException ioe) {
            return CREACION_ALUMNOS_ERROR;            
        }
    } 
    
    /**
     * Lee del archivo de texto y carga el ArrayList empleando un try con recursos
     * https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
     * Formato del archivo (suponiendo que la coma sea el separador):
     *  dni 1,apellidos 1,nombres 1,clave 1,cargo 1 
     *  dni 2,apellidos 2,nombres 2,clave 2,cargo 1 
     *  dni 3,apellidos 3,nombres 3,clave 3,cargo 2 
     *  dni 4,apellidos 4,nombres 4,clave 4,cargo 3
     * @return String  - cadena con el resultado de la operacion (LECTURA_PROFESORES_ERROR | LECTURA_PROFESORES_OK | CREACION_PROFESORES_OK | CREACION_PROFESORES_ERROR)
     */
    private String leerArchivoProfesores() {
        File file = new File(NOMBRE_ARCHIVO_PROFESORES);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String cadena;
                while((cadena = br.readLine()) != null) {
                    String[] vector = cadena.split(Character.toString(SEPARADOR));
                    int dni = Integer.parseInt(vector[0]);                        
                    String apellidos = vector[1];
                    String nombres = vector[2];
                    String clave = vector[3];
                    String cadenaCargo = vector[4];
                    Cargo cargo = Cargo.verCargo(cadenaCargo);
                    Autor profesor = new Profesor(dni, apellidos, nombres, clave, cargo);
                    this.autores.add(profesor);
                }
                return LECTURA_PROFESORES_OK;
            }
            catch (IOException ioe) {
                return LECTURA_PROFESORES_ERROR;
            }
        }
        else {
            return this.crearArchivoProfesores();
        }
    }    
    
    /**
     * Crea el archivo de profesores
     * @return String  - cadena con el resultado de la operacion (CREACION_PROFESORES_OK | CREACION_PROFESORES_ERROR)
     */
    private String crearArchivoProfesores() {
        File file = new File(NOMBRE_ARCHIVO_PROFESORES);
        try (FileWriter fw = new FileWriter(file)) {     
            return CREACION_PROFESORES_OK;
        } 
        catch (IOException ioe) {
            return CREACION_PROFESORES_ERROR;            
        }
    }
    
    /**
     * Escribe en el archivo de texto el ArrayList
     * Formato del archivo (suponiendo que la coma sea el separador):
     *  dni 1,apellidos 1,nombres 1,clave 1,CX 1 
     *  dni 2,apellidos 2,nombres 2,clave 2,CX 2 
     *  dni 3,apellidos 3,nombres 3,clave 3,CX 3 
     *  dni 4,apellidos 4,nombres 4,clave 4,CX 4 
     * @return String  - cadena con el resultado de la operacion (ESCRITURA_ALUMNOS_ERROR | ESCRITURA_ALUMNOS_OK)
     */
    private String escribirArchivoAlumnos() {
        File file = new File(NOMBRE_ARCHIVO_ALUMNOS);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {     
            for(Autor autor : this.autores) {
                if (autor instanceof Alumno) {
                    String cadena = Integer.toString(autor.verDNI()) + SEPARADOR;
                    cadena += autor.verApellidos() + SEPARADOR;
                    cadena += autor.verNombres() + SEPARADOR;
                    cadena += autor.verClave() + SEPARADOR;
                    cadena += ((Alumno)autor).verCX();
                    bw.write(cadena);
                    bw.newLine();
                }
            }
            return ESCRITURA_ALUMNOS_OK;
        } 
        catch (IOException ioe) {
            return ESCRITURA_ALUMNOS_ERROR;            
        }
    } 
        
    /**
     * Escribe en el archivo de texto el ArrayList
     * Formato del archivo (suponiendo que la coma sea el separador):
     *  dni 1,apellidos 1,nombres 1,clave 1,cargo 1 
     *  dni 2,apellidos 2,nombres 2,clave 2,cargo 1 
     *  dni 3,apellidos 3,nombres 3,clave 3,cargo 2 
     *  dni 4,apellidos 4,nombres 4,clave 4,cargo 3
     * @return String  - cadena con el resultado de la operacion (ESCRITURA_PROFESORES_ERROR | ESCRITURA_PROFESORES_OK)
     */
    private String escribirArchivoProfesores() {
        File file = new File(NOMBRE_ARCHIVO_PROFESORES);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {     
            for(Autor autor : this.autores) {
                if (autor instanceof Profesor) {                    
                    String cadena = Integer.toString(autor.verDNI()) + SEPARADOR;
                    cadena += autor.verApellidos() + SEPARADOR;
                    cadena += autor.verNombres() + SEPARADOR;
                    cadena += autor.verClave() + SEPARADOR;
                    cadena += ((Profesor)autor).verCargo();
                    bw.write(cadena);
                    bw.newLine();
                }
            }
            return ESCRITURA_PROFESORES_OK;
        } 
        catch (IOException ioe) {
            return ESCRITURA_PROFESORES_ERROR;            
        }
    }         
}
