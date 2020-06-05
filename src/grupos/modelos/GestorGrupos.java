/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupos.modelos;

import autores.modelos.Autor;
import autores.modelos.Cargo;
import autores.modelos.GestorAutores;
import autores.modelos.Profesor;
import interfaces.IControladorPrincipal;
import interfaces.IGestorAutores;
import interfaces.IGestorGrupos;
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

public class GestorGrupos implements IGestorGrupos { 
    private final String NOMBRE_ARCHIVO = "./Grupos.txt";
    //nombre del archivo con los tipos de publicación
    private final char SEPARADOR = ','; 
    //caracter usado para separar el nombre y la descripción de los grupos, y también para separar los miembros 
    private final char FIN_GRUPO = ':'; 
    //caracter usado para indicar dónde termina la descripción de un grupo
    private final String VALORES_NULOS = "-";
    //cadena usada para los valores nulos (descripción)
    
    private List<Grupo> grupos = new ArrayList<>();
    private static GestorGrupos gestor;
    
    private boolean problemasConArchivo;
    //sirve para saber si hubo o no problemas con el archivo
    
    /**
     * Constructor
    */                                            
    private GestorGrupos() {    
        String resultado = this.leerArchivo();
        if ((resultado.equals(LECTURA_ERROR)) || (resultado.equals(CREACION_ERROR))) {
            JOptionPane.showMessageDialog(null, resultado, IControladorPrincipal.TITULO, JOptionPane.ERROR_MESSAGE);          
            this.problemasConArchivo = true;
        }
        else
            this.crearGrupoSuperAdministradores();
    }
    
    /**
     * Crea el grupo de super administradores (si no existe) y le agrega el usuario "Admin"
     */
    private void crearGrupoSuperAdministradores() {
        String resultado;
        Grupo grupo = this.verGrupo(NOMBRE_SUPER_ADMINISTRADORES);
        if (grupo == null) { //si no existe el grupo de super administradores se lo crea
            grupo = new Grupo(NOMBRE_SUPER_ADMINISTRADORES, DESCRIP_SUPER_ADMINISTRADORES);
            this.grupos.add(grupo);
            Collections.sort(this.grupos);
            resultado = this.escribirArchivo();
            if (resultado.equals(ESCRITURA_ERROR)) {
                JOptionPane.showMessageDialog(null, resultado, IControladorPrincipal.TITULO, JOptionPane.ERROR_MESSAGE);          
                this.problemasConArchivo = true;
            }
            else
                this.problemasConArchivo = false;
        }    
        

        if (!grupo.tieneMiembros()) { //se le agrega el usuario Admin si no tiene miembros
//            IGestorAutores ga = GestorAutores.instanciar();
//            ga.nuevoAutor(0, IGestorAutores.NOMBRE_SUPER_ADMINISTRADOR, IGestorAutores.NOMBRE_SUPER_ADMINISTRADOR, Cargo.TITULAR, IGestorAutores.NOMBRE_SUPER_ADMINISTRADOR, IGestorAutores.NOMBRE_SUPER_ADMINISTRADOR);
            Autor autor = new Profesor(0, IGestorAutores.NOMBRE_SUPER_ADMINISTRADOR, IGestorAutores.NOMBRE_SUPER_ADMINISTRADOR, IGestorAutores.NOMBRE_SUPER_ADMINISTRADOR, Cargo.TITULAR);
            //este autor (super administrador) no pasa por el gestor
            //por lo que el mismo no lo tiene registrado
            grupo.agregarMiembro(autor, Rol.ADMINISTRADOR);
        }        
    }
    
    /**
     * Método estático que permite crear una única instancia de GestorGrupos
     * @return GestorGrupos
    */                                                            
    public static GestorGrupos instanciar() {
        if (gestor == null) 
            gestor = new GestorGrupos();            
        return gestor;
    } 
    
    @Override
    public String nuevoGrupo(String nombre, String descripcion) {
        if (this.problemasConArchivo)
            return PROBLEMAS_ES;
        
        if ((nombre == null) || (nombre.trim().isEmpty()))  //nombre del grupo no nulo y no vacío
            return ERROR_NOMBRE; 

        Grupo grupo = new Grupo(nombre, descripcion);
        if (this.grupos.contains(grupo))  //no admite duplicados
            return GRUPOS_DUPLICADOS;  
        
        this.grupos.add(grupo); 
        Collections.sort(this.grupos);
        String resultado = this.escribirArchivo();
        return (resultado.equals(ESCRITURA_OK) ? EXITO : resultado);
    }
   
    @Override
    public List<Grupo> buscarGrupos(String nombre) {        
        List<Grupo> gruposBuscados = new ArrayList<>();
        if (nombre == null)
            return gruposBuscados;
        
        for(Grupo grupo : this.grupos) {
            if (grupo.verNombre().toLowerCase().contains(nombre.toLowerCase().trim()))
                gruposBuscados.add(grupo);
        }            
        return gruposBuscados;
    }
    
    @Override
    public List<Grupo> verGrupos() {
        return this.grupos;
    }
    
    @Override
    public Grupo verGrupo(String nombre) {
        if ((nombre == null) || (nombre.isEmpty()))
            return null;
        
        for(Grupo grupo : this.grupos)
            if (grupo.verNombre().equals(nombre))
                return grupo;
        return null;                    
    }
    
    @Override
    public boolean existeEsteGrupo(Grupo grupo) {
        if (grupo == null)
            return false;
                
        for(Grupo g : this.grupos) {
            if (g.equals(grupo))
                return true;
        }
        return false;
    }
       
    @Override
    public String borrarGrupo(Grupo grupo) {
        if (!this.existeEsteGrupo(grupo))
            return GRUPO_INEXISTENTE;
        
        if (this.problemasConArchivo) 
            return PROBLEMAS_ES;
        
        IGestorAutores ga = GestorAutores.instanciar();
        if (ga.hayAutoresConEsteGrupo(grupo)) //hay al menos un autor que pertenece a este grupo
            return GRUPO_CON_MIEMBROS;

        this.grupos.remove(grupo);
        String resultado = this.escribirArchivo();
        return (resultado.equals(ESCRITURA_OK) ? EXITO : resultado);           
    }     
    
    @Override
    public String modificarGrupo(Grupo grupo, String descripcion) {
        if (!this.existeEsteGrupo(grupo))
            return GRUPO_INEXISTENTE;
        
        if (this.problemasConArchivo) 
            return PROBLEMAS_ES;
        
        if ((descripcion != null) && (descripcion.isEmpty()))
            descripcion = null;
        grupo.asignarDescripcion(descripcion);
        String resultado = this.escribirArchivo();
        return (resultado.equals(ESCRITURA_OK) ? EXITO : resultado);        
    }
        
    @Override
    public String agregarMiembros(Grupo grupo, List<MiembroEnGrupo> miembros) {
        if (!this.existeEsteGrupo(grupo)) 
            return GRUPO_INEXISTENTE;
        
        if (this.problemasConArchivo)
            return PROBLEMAS_ES;
        
        if (miembros == null)
            return MIEMBROS_INEXISTENTES;
        
        grupo.agregarMiembros(miembros);                    
        String resultado = this.escribirArchivo();
        return (resultado.equals(ESCRITURA_OK) ? EXITO_MIEMBROS : resultado);            
    }
       
    @Override
    public String quitarMiembros(Grupo grupo, List<MiembroEnGrupo> miembros) {
        if (!this.existeEsteGrupo(grupo))
            return GRUPO_INEXISTENTE;
        
        if (this.problemasConArchivo)
            return PROBLEMAS_ES;
        
        if (miembros == null)
            return MIEMBROS_INEXISTENTES;
        
        grupo.quitarMiembros(miembros);
        String resultado = this.escribirArchivo();
        return (resultado.equals(ESCRITURA_OK) ? EXITO_MIEMBROS : resultado);            
    }
    
    @Override
    public String actualizarGrupos() {
        return this.escribirArchivo();
    }
                    
    /**
     * Lee del archivo de texto y carga el ArrayList empleando un try con recursos
     * https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
     * Formato del archivo:
     *  nombre1,descripción1:dni1,rol,dni2,rol,...
     *  nombre1,descripción1:dni1,rol,dni2,rol,...
     *  nombre2,-:dni1,rol,dni3,rol,...
     *  nombre3,descripción3:
     *  nombre4,-:
     * @return String  - cadena con el resultado de la operacion (LECTURA_OK | LECTURA_ERROR | CREACION_OK | CREACION_ERROR)
     */
    private String leerArchivo() {
        File file = new File(NOMBRE_ARCHIVO);
        IGestorAutores ga = GestorAutores.instanciar();
        if (!file.exists())
            return this.crearArchivo();
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String cadena;
            while((cadena = br.readLine()) != null) {
                String[] infoGrupo = null;
                String[] miembros = null;
                if (cadena.endsWith(Character.toString(FIN_GRUPO))) { //grupo sin miembros
                    cadena = cadena.substring(0, cadena.length() - 1); //se saca el caracter usado para indicar dónde termina la descripción de un grupo
                    infoGrupo = cadena.split(Character.toString(SEPARADOR)); //infoGrupo[0] = nombre, infoGrupo[1] = descripción                      
                }
                else { //grupo con miembros
                    String[] vector = cadena.split(Character.toString(FIN_GRUPO)); //vector[0] = nombre,descripción, vector[1] = dni1,rol,dni2,rol,...
                    infoGrupo = vector[0].split(Character.toString(SEPARADOR)); //infoGrupo[0] = nombre, infoGrupo[1] = descripción                                              
                    miembros = vector[1].split(Character.toString(SEPARADOR)); //miembros[0] = dni1, miembros[1] = rol, miembros[2] = dni2, miembros[3] = rol
                }

                String nombre = infoGrupo[0];
                String descripcion = (infoGrupo[1].equals(VALORES_NULOS) ? null : infoGrupo[1]);
                Grupo grupo = new Grupo(nombre, descripcion);

                if (miembros != null) { //grupo con miembros
                    for(int i = 0; i < miembros.length / 2; i++) {
                        Autor miembro = ga.verAutor(Integer.parseInt(miembros[i * 2]));
                        Rol rol = Rol.verRol(miembros[i * 2 + 1]);
                        grupo.agregarMiembro(miembro, rol);
                    }
                }
                this.grupos.add(grupo);
            }
            return LECTURA_OK;
        }
        catch (IOException ioe) {
            return LECTURA_ERROR;
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
    
    /**
     * Escribe en el archivo de texto el ArrayList
     * Formato del archivo:
     *  nombre1,descripción1:dni1,rol,dni2,rol,...
     *  nombre1,descripción1:dni1,rol,dni2,rol,...
     *  nombre2,-:dni1,rol,dni3,rol,...
     *  nombre3,descripción3:
     *  nombre4,-:
     * @return String  - cadena con el resultado de la operacion (ESCRITURA_OK | ESCRITURA_ERROR)
     */
    private String escribirArchivo() {
        File file = new File(NOMBRE_ARCHIVO);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {     
            for(Grupo grupo : this.grupos) {
                String cadena = grupo.verNombre();
                cadena += SEPARADOR + (grupo.verDescripcion() == null ? VALORES_NULOS : grupo.verDescripcion()) + FIN_GRUPO;

                if (grupo.tieneMiembros()) {
                    //Todos los miembros
                    for(MiembroEnGrupo meg : grupo.verMiembros()) {
                        Autor miembro = meg.verMiembro();
                        if (miembro.verDNI() > 0) { //no se guarda el usuario Admin
                            Rol rol = meg.verRol();
                            cadena += Integer.toString(miembro.verDNI()) + SEPARADOR;
                            cadena += rol.toString() + SEPARADOR;
                        }
                    }
                    if (cadena.endsWith(Character.toString(SEPARADOR)))
                        cadena = cadena.substring(0, cadena.length() - 1); //se saca el último separador
                } 
                bw.write(cadena); 
                bw.newLine();
            }            
            return ESCRITURA_OK;
        } 
        catch (IOException ioe) {
            return ESCRITURA_ERROR;            
        }
    }          
}
