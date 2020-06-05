/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package publicaciones.modelos;

import autores.modelos.Autor;
import autores.modelos.GestorAutores;
import auxiliares.ManejoDeFechas;
import grupos.modelos.GestorGrupos;
import grupos.modelos.Grupo;
import grupos.modelos.MiembroEnGrupo;
import idiomas.modelos.GestorIdiomas;
import idiomas.modelos.Idioma;
import interfaces.IControladorPrincipal;
import interfaces.IGestorAutores;
import interfaces.IGestorGrupos;
import interfaces.IGestorIdiomas;
import interfaces.IGestorLugares;
import interfaces.IGestorPalabrasClaves;
import interfaces.IGestorPublicaciones;
import interfaces.IGestorTipos;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import javax.swing.JOptionPane;
import lugares.modelos.GestorLugares;
import lugares.modelos.Lugar;
import palabrasclaves.modelos.GestorPalabrasClaves;
import palabrasclaves.modelos.PalabraClave;
import tipos.modelos.GestorTipos;
import tipos.modelos.Tipo;

public class GestorPublicaciones implements IGestorPublicaciones {
    private final String NOMBRE_ARCHIVO = "./Publicaciones.txt";
    //nombre del archivo con los trabajos    
    private final String SEPARADOR = "---"; 
    //caracter usado como separador 
    
    private boolean problemasConArchivo;
    //sirve para saber si hubo o no problemas con el archivo
    
    private List<Publicacion> publicaciones = new ArrayList<>();    
    private static GestorPublicaciones gestor;
        
    /**
     * Constructor
    */                                            
    private GestorPublicaciones() {   
        String resultado = this.leerArchivo();
        if ((resultado.equals(LECTURA_ERROR)) || (resultado.equals(CREACION_ERROR))) {
            JOptionPane.showMessageDialog(null, resultado, IControladorPrincipal.TITULO, JOptionPane.ERROR_MESSAGE);          
            this.problemasConArchivo = true;
        }
        else
            this.problemasConArchivo = false;
    }
    
    /**
     * Método estático que permite crear una única instancia de GestorPublicaciones
     * @return GestorPublicaciones
    */                                                            
    public static GestorPublicaciones instanciar() {
        if (gestor == null) 
            gestor = new GestorPublicaciones();            
        return gestor;
    }     
    
    @Override
    public String nuevaPublicacion(String titulo, MiembroEnGrupo miembroEnGrupo, LocalDate fechaPublicacion, Tipo tipo, Idioma idioma, Lugar lugar, List<PalabraClave> palabrasClaves, String enlace, String resumen) {      
        if (this.problemasConArchivo) 
            return PROBLEMAS_ES;
        
        if(!this.validarTitulo(titulo))
            return ERROR_TITULO;

        if (!this.validarMiembroEnGrupo(miembroEnGrupo))
            return ERROR_MIEMBRO_EN_GRUPO;

        if(!this.validarFecha(fechaPublicacion))
            return ERROR_FECHA;

        if (!this.validarTipo(tipo))
            return ERROR_TIPO;

        if (!this.validarIdioma(idioma))
            return ERROR_IDIOMA;

        if (!this.validarLugar(lugar))
            return ERROR_LUGAR;

        if (!this.validarPalabrasClaves(palabrasClaves))
            return ERROR_PALABRAS_CLAVES;

        if (!this.validarEnlace(enlace))
            return ERROR_ENLACE;

        if (!this.validarResumen(resumen))
            return ERROR_RESUMEN;
        else {
            Publicacion publicacion = new Publicacion(titulo, miembroEnGrupo, fechaPublicacion, tipo, idioma, lugar, palabrasClaves, enlace, resumen);
            if (this.publicaciones.contains(publicacion))
                return PUBLICACIONES_DUPLICADAS;
            
            this.publicaciones.add(publicacion);
            Collections.sort(this.publicaciones);
            String resultado = this.escribirArchivo();
            return (resultado.equals(ESCRITURA_OK) ? EXITO : resultado);              
        }
        
    }   
    
    @Override
    public String modificarPublicacion(Publicacion publicacion, MiembroEnGrupo miembroEnGrupo, LocalDate fechaPublicacion, Tipo tipo, Idioma idioma, Lugar lugar, List<PalabraClave> palabrasClaves, String enlace, String resumen) {
        if (!this.existeEstaPublicacion(publicacion)) 
            return PUBLICACION_INEXISTENTE;
        
        if (!this.validarMiembroEnGrupo(miembroEnGrupo))
            return ERROR_MIEMBRO_EN_GRUPO;
        
//        if (!publicacion.puedeSerModificadaPor(miembroEnGrupo.verMiembro()))    
//            return AUTOR_NO_PUEDE_MODIFICAR_PUBLICACION;
        
        if (this.problemasConArchivo) 
            return PROBLEMAS_ES;
        
        if(!this.validarFecha(fechaPublicacion))
            return ERROR_FECHA;

        if (!this.validarTipo(tipo))
            return ERROR_TIPO;

        if (!this.validarIdioma(idioma))
            return ERROR_IDIOMA;

        if (!this.validarLugar(lugar))
            return ERROR_LUGAR;

        if (!this.validarPalabrasClaves(palabrasClaves))
            return ERROR_PALABRAS_CLAVES;

        if (!this.validarEnlace(enlace))
            return ERROR_ENLACE;

        if (!this.validarResumen(resumen))
            return ERROR_RESUMEN;

        publicacion.asignarAutor(miembroEnGrupo);
        publicacion.asignarFechaPublicacion(fechaPublicacion);
        publicacion.asignarTipo(tipo);
        publicacion.asignarIdioma(idioma);
        publicacion.asignarLugar(lugar);
        publicacion.asignarPalabrasClaves(palabrasClaves);
        publicacion.asignarEnlace(enlace);
        publicacion.asignarResumen(resumen);
        String resultado = this.escribirArchivo();
        return (resultado.equals(ESCRITURA_OK) ? EXITO : resultado);           
    }
    
    /**
     * Valida que el título de la publicación sea correcto
     * @param titulo título de la publicación
     * @return boolean  - true si el título de la publicación es correcto, false en caso contrario
     */
    private boolean validarTitulo(String titulo) {
        if((titulo != null) && (!titulo.trim().isEmpty()))  //título correcto
            return true;
        else //título incorrecto
            return false;
    }
    
    /**
     * Valida que el miembro del grupo sea correcto
     * @param meg miembro del grupo de la publicación
     * @return boolean  - true si el miembro del grupo es correcto, false en caso contrario
     */
    private boolean validarMiembroEnGrupo(MiembroEnGrupo meg) {
        return ((meg != null) && (meg.verMiembro() != null) && (meg.verGrupo() != null) && (meg.verRol() != null));
    }    
    
    /**
     * Valida que la publicación tenga una fecha
     * @param fecha fecha de la publicación
     * @return boolean  - true si la publicación tiene una fecha, false en caso contrario
     */
    private boolean validarFecha(LocalDate fecha) {
        return (fecha != null);
    }    
    
    /**
     * Valida que el tipo de la publicación sea correcto
     * @param tipo tipo de la publicación
     * @return boolean  - true si el tipo de la publicación es correcto, false en caso contrario
     */
    private boolean validarTipo(Tipo tipo) {
        return (tipo != null);
    }    
    
    /**
     * Valida que el idioma de la publicación sea correcto
     * @param idioma idioma de la publicación
     * @return boolean  - true si el idioma de la publicación es correcto, false en caso contrario
     */
    private boolean validarIdioma(Idioma idioma) {
        return (idioma != null);
    }        
    
    /**
     * Valida que el lugar de la publicación sea correcto
     * @param lugar lugar de la publicación
     * @return boolean  - true si el lugar de la publicación es correcto, false en caso contrario
     */
    private boolean validarLugar(Lugar lugar) {
        return (lugar != null);
    }    
    
    /**
     * Valida que las palabras claves de la publicación sean correctas
     * @param palabrasClaves palabras claves de la publicación
     * @return boolean  - true si las palabras claves de la publicación son correctas, false en caso contrario
     */
    private boolean validarPalabrasClaves(List<PalabraClave> palabrasClaves) {
        if((palabrasClaves != null) && (!palabrasClaves.isEmpty()))  //palabras correctas correctos
            return true;
        else //palabras claves incorrectas
            return false;
    }    
    
    /**
     * Valida que el enlace de la publicación sea correcto
     * @param enlace enlace de la publicación
     * @return boolean  - true si el enlace de la publicación es correcto, false en caso contrario
     */
    private boolean validarEnlace(String enlace) {
        if((enlace != null) && (!enlace.trim().isEmpty()))  //enlace correcto
            return true;
        else //enlace incorrecto
            return false;
    }        
    
    /**
     * Valida que el resumen de la publicación sea correcto
     * @param resumen resumen de la publicación
     * @return boolean  - true si el resumen de la publicación es correcto, false en caso contrario
     */
    private boolean validarResumen(String resumen) {
        if((resumen != null) && (!resumen.trim().isEmpty()))  //resumen correcto
            return true;
        else //resumen incorrecto
            return false;
    }            
    
    @Override
    public List<Publicacion> buscarPublicaciones(String titulo) {
        List<Publicacion> publicacionesBuscadas = new ArrayList<>();
        if (titulo == null)
            return publicacionesBuscadas;
        
        for(Publicacion publicacion : this.publicaciones) {
            if (publicacion.verTitulo().toLowerCase().contains(titulo.toLowerCase()))
                publicacionesBuscadas.add(publicacion);
        }
        return publicacionesBuscadas;
    }   
        
    @Override
    public String borrarPublicacion(Publicacion publicacion) {
        if (!this.existeEstaPublicacion(publicacion))
            return PUBLICACION_INEXISTENTE;
        
//        IGestorAutores ga = GestorAutores.instanciar();
//        if (!ga.existeEsteAutor(autor)) 
//            return AUTOR_INEXISTENTE;
        
//        if (!publicacion.puedeSerBorradaPor(autor)) 
//            return AUTOR_NO_PUEDE_BORRAR_PUBLICACION;
        
        if (this.problemasConArchivo) 
            return PROBLEMAS_ES; 
        
        this.publicaciones.remove(publicacion);
        String resultado = this.escribirArchivo();
        return (resultado.equals(ESCRITURA_OK) ? EXITO : resultado);
    }
            
    @Override
    public boolean hayPublicacionesConEstaPalabraClave(PalabraClave palabraClave) {
        for(Publicacion publicacion : this.publicaciones) {
            for(PalabraClave pc : publicacion.verPalabrasClaves()) {
                if (palabraClave.equals(pc))
                    return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean hayPublicacionesConEsteLugar(Lugar lugar) {
        for(Publicacion publicacion : this.publicaciones) {
            if (publicacion.verLugar().equals(lugar))
                return true;
        }
        return false;
    }   
    
    @Override
    public boolean hayPublicacionesConEsteTipo(Tipo tipo) {
        for(Publicacion publicacion : this.publicaciones) {
            if (publicacion.verTipo().equals(tipo))
                return true;
        }
        return false;
    }       
    
    @Override
    public boolean hayPublicacionesConEsteIdioma(Idioma idioma) {
        for(Publicacion publicacion : this.publicaciones) {
            if (publicacion.verIdioma().equals(idioma))
                return true;
        }
        return false;
    }   
    
    @Override
    public boolean hayPublicacionesConEsteAutor(Autor autor) {
        for(Publicacion publicacion : this.publicaciones) {
            MiembroEnGrupo meg = publicacion.verMiembroEnGrupo();
            if (autor.equals(meg.verMiembro()))
                return true;
        }
        return false;
    }

    @Override
    public boolean existeEstaPublicacion(Publicacion publicacion) {
        if (publicacion == null)
            return false;
        else {
            for(Publicacion p : this.publicaciones) {
                if (p.equals(publicacion))
                    return true;
            }
            return false;
        }
    }
    
    @Override
    public List<Publicacion> verPublicaciones() {
        return this.publicaciones;
    }

    /**
     * Lee del archivo de texto y carga el ArrayList empleando un try con recursos
     * https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
     * Formato del archivo:
     *  título 1,dni autor,nombre grupo,fechaPublicacion,tipo,idioma,lugar,enlace,resumen,cantPalabrasClaves,palabra 1,palabra 2,...
     *  título 2,dni autor,nombre grupo,fechaPublicacion,tipo,idioma,lugar,enlace,resumen,cantPalabrasClaves,palabra 1,palabra 3,...
     *  ...
     * @return String  - cadena con el resultado de la operacion (LECTURA_ERROR | LECTURA_OK | CREACION_OK | CREACION_ERROR | ERROR_FECHA | ERROR_TIPO | ERROR_IDIOMA | ERROR_LUGAR | ERROR_ENLACE | ERROR_RESUMEN | ERROR_AUTORES | ERROR_PALABRAS_CLAVES)
     */
    private String leerArchivo() {
        File file = new File(NOMBRE_ARCHIVO);
        if (!file.exists())
            return this.crearArchivo();
        
        IGestorAutores ga = GestorAutores.instanciar();
        IGestorGrupos gg = GestorGrupos.instanciar();
        IGestorTipos gt = GestorTipos.instanciar();
        IGestorIdiomas gi = GestorIdiomas.instanciar();
        IGestorLugares gl = GestorLugares.instanciar();
        IGestorPalabrasClaves gp = GestorPalabrasClaves.instanciar();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String cadena;
            while((cadena = br.readLine()) != null) {
                try {
                    String[] vector = cadena.split(SEPARADOR);
                    String titulo = vector[0];

                    int dni = Integer.parseInt(vector[1]);
                    Autor autor = ga.verAutor(dni);
                    String nombreGrupo = vector[2];
                    Grupo grupo = gg.verGrupo(nombreGrupo);
                    MiembroEnGrupo meg = this.armarMiembroEnGrupo(autor, grupo);
                    if (meg == null) {
                        this.publicaciones = new ArrayList<>();
                        JOptionPane.showOptionDialog(null, LECTURA_ERROR + ". " + ERROR_MIEMBRO_EN_GRUPO, IControladorPrincipal.TITULO, JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null, new Object[] {"Aceptar"}, this);       
                        return ERROR_MIEMBRO_EN_GRUPO;
                    }

                    String fPublicacion = vector[3];
                    LocalDate fechaPublicacion = ManejoDeFechas.transformarCadenaALocalDate(fPublicacion);
                    if (fechaPublicacion == null) {
                        this.publicaciones = new ArrayList<>();
                        JOptionPane.showOptionDialog(null, LECTURA_ERROR + ". " + ERROR_FECHA, IControladorPrincipal.TITULO, JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null, new Object[] {"Aceptar"}, this);       
                        return ERROR_FECHA;
                    }

                    String nombreTipo = vector[4];
                    Tipo tipo = gt.verTipo(nombreTipo);
                    if (tipo == null) {
                        this.publicaciones = new ArrayList<>();
                        JOptionPane.showOptionDialog(null, LECTURA_ERROR + ". " + ERROR_TIPO, IControladorPrincipal.TITULO, JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null, new Object[] {"Aceptar"}, this);                               
                        return ERROR_TIPO;
                    }                    

                    String nombreIdioma = vector[5];
                    Idioma idioma = gi.verIdioma(nombreIdioma);
                    if (idioma == null) {
                        this.publicaciones = new ArrayList<>();
                        JOptionPane.showOptionDialog(null, LECTURA_ERROR + ". " + ERROR_IDIOMA, IControladorPrincipal.TITULO, JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null, new Object[] {"Aceptar"}, this);                                                       
                        return ERROR_IDIOMA;
                    }                                        

                    String nombreLugar = vector[6];
                    Lugar lugar = gl.verLugar(nombreLugar);
                    if (lugar == null) {
                        this.publicaciones = new ArrayList<>();
                        JOptionPane.showOptionDialog(null, LECTURA_ERROR + ". " + ERROR_LUGAR, IControladorPrincipal.TITULO, JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null, new Object[] {"Aceptar"}, this);                                                                               
                        return ERROR_LUGAR;
                    }                                                            

                    String enlace = vector[7];
                    if (enlace == null) {
                        this.publicaciones = new ArrayList<>();
                        JOptionPane.showOptionDialog(null, LECTURA_ERROR + ". " + ERROR_ENLACE, IControladorPrincipal.TITULO, JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null, new Object[] {"Aceptar"}, this);                                                                                                       
                        return ERROR_ENLACE;
                    }                                                                                

                    String resumen = vector[8];
                    if (resumen == null) {
                        this.publicaciones = new ArrayList<>();
                        JOptionPane.showOptionDialog(null, LECTURA_ERROR + ". " + ERROR_RESUMEN, IControladorPrincipal.TITULO, JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null, new Object[] {"Aceptar"}, this);                                                                                                                               
                        return ERROR_RESUMEN;
                    }                                                                                                    

                    int cantPalabrasClaves = Integer.parseInt(vector[9]);                    

                    int primerPalabraClave = 10; //posición donde está la primer palabra clave
                    int ultimaPalabraClave = primerPalabraClave + cantPalabrasClaves - 1; //posición donde está la última palabra clave

                    List<PalabraClave> palabrasClaves = new ArrayList<>();
                    for(int i = primerPalabraClave; i <= ultimaPalabraClave; i++) {
                        String nombrePalabraClave = vector[i];
                        PalabraClave palabraClave = gp.verPalabraClave(nombrePalabraClave);
                        if (palabraClave == null) {
                            this.publicaciones = new ArrayList<>();
                            JOptionPane.showOptionDialog(null, LECTURA_ERROR + ". " + ERROR_PALABRAS_CLAVES, IControladorPrincipal.TITULO, JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null, new Object[] {"Aceptar"}, this);                                                                                                                                                           
                            return ERROR_PALABRAS_CLAVES;
                        }  
                        else
                            palabrasClaves.add(palabraClave);                        
                    }

                    Publicacion publicacion = new Publicacion(titulo, meg, fechaPublicacion, tipo, idioma, lugar, palabrasClaves, enlace, resumen);
                    this.publicaciones.add(publicacion);
                }
                catch(PatternSyntaxException e) { //error al leer del archivo
                    this.publicaciones = new ArrayList<>();
                    return LECTURA_ERROR;
                }
            }
            return LECTURA_OK;
        }
        catch (IOException ioe) {
            return LECTURA_ERROR;
        }

    }  
    
    /**
     * Arma el objeto MiembroEnGrupo según el autor y grupo especificados
     * @param autor autor con el cual se arma el objeto MiembroEnGrupo
     * @param grupo grupo con el cual se arma el objeto MiembroEnGrupo
     * @return MiembroEnGrupo  - objeto MiembroEnGrupo correspondiente al autor y grupo especificados, null en caso que el autor/grupo sean nulos, o bien que el autor no sea miembro del grupo
     */
    private MiembroEnGrupo armarMiembroEnGrupo(Autor autor, Grupo grupo) {
        if ((autor != null) && (grupo != null)) {
            for(MiembroEnGrupo meg : autor.verGrupos()) {
                if (meg.verGrupo().equals(grupo))
                    return meg;
            }
            return null;
        }
        else
            return null;
    }
    
    /**
     * Escribe en el archivo de texto el ArrayList
     * Formato del archivo:
     *  título 1,dni autor,nombre grupo,fechaPublicacion,tipo,idioma,lugar,enlace,resumen,cantPalabrasClaves,palabra 1,palabra 2,...
     *  título 2,dni autor,nombre grupo,fechaPublicacion,tipo,idioma,lugar,enlace,resumen,cantPalabrasClaves,palabra 1,palabra 3,...
     *  ...
     * @return String  - cadena con el resultado de la operacion (ESCRITURA_ERROR | ESCRITURA_OK)
     */
    private String escribirArchivo() {
        File file = new File(NOMBRE_ARCHIVO);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {     
            for(Publicacion publicacion : this.publicaciones) {
                String cadena = publicacion.verTitulo();
                
                cadena += SEPARADOR + Integer.toString(publicacion.verMiembroEnGrupo().verMiembro().verDNI());
                cadena += SEPARADOR + publicacion.verMiembroEnGrupo().verGrupo().verNombre();

                String fechaPublicacion = ManejoDeFechas.transformarLocalDateEnCadena(publicacion.verFechaPublicacion());
                cadena += SEPARADOR + fechaPublicacion;
                
                cadena += SEPARADOR + publicacion.verTipo().toString();
                
                cadena += SEPARADOR + publicacion.verIdioma().toString();
                
                cadena += SEPARADOR + publicacion.verLugar().toString();
                
                cadena += SEPARADOR + publicacion.verEnlace();
                
                cadena += SEPARADOR + publicacion.verResumen();
                                
                int cantPalabrasClaves = publicacion.verPalabrasClaves().size();
                cadena += SEPARADOR + Integer.toString(cantPalabrasClaves);
                
                for(PalabraClave palabraClave : publicacion.verPalabrasClaves())
                    cadena += SEPARADOR + palabraClave.verNombre();
                                
                bw.write(cadena);
                bw.newLine();
            }
            return ESCRITURA_OK;
        } 
        catch (IOException ioe) {
            return ESCRITURA_ERROR;            
        }
    }     
    
    /**
     * Crea el archivo
     * @return String  - cadena con el resultado de la operacion (CREACION_OK | CREACION_ERROR)
     */
    private String crearArchivo() {
        File file = new File(NOMBRE_ARCHIVO);
        try (FileWriter fw = new FileWriter(file)) {     
            return CREACION_OK;
        } 
        catch (IOException ioe) {
            return CREACION_ERROR;            
        }
    }         
}
