/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.controladores;

import autores.controladores.ControladorAutores;
import autores.modelos.Autor;
import auxiliares.Apariencia;
import grupos.controladores.ControladorGrupos;
import idiomas.controladores.ControladorIdiomas;
import interfaces.IControladorAutores;
import interfaces.IControladorGrupos;
import interfaces.IControladorIdiomas;
import interfaces.IControladorLugares;
import interfaces.IControladorPalabrasClaves;
import interfaces.IControladorPrincipal;
import interfaces.IControladorPublicaciones;
import interfaces.IControladorTipos;
import interfaces.IControladorVentanaLogin;
import principal.vistas.VentanaPrincipal;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import lugares.controladores.ControladorLugares;
import palabrasclaves.controladores.ControladorPalabrasClaves;
import publicaciones.controladores.ControladorPublicaciones;
import tipos.controladores.ControladorTipos;

public class ControladorPrincipal implements IControladorPrincipal {
    private VentanaPrincipal ventana;
    private Autor autor;

    /**
     * Constructor
     * Muestra la ventana principal
     * @param autor autor logueado
     */
    public ControladorPrincipal(Autor autor) {
        if (autor != null) {
            this.ventana = new VentanaPrincipal(this);
            this.autor = autor;
            this.ventana.setLocationRelativeTo(null);
            this.ventana.setTitle(TITULO + " - " + autor.verApeYNom());
            Apariencia.asignarNimbusLookAndFeel("Nimbus"); //asigna el look and feel "Nimbus" a la ventana
            this.ventana.setVisible(true);   
        }
    }

    /**
     * Acción a ejecutar cuando se selecciona el botón Palabras claves
     * @param evt evento
     */                        
    @Override
    public void btnPalabrasClavesClic(ActionEvent evt) {
        IControladorPalabrasClaves controlador = new ControladorPalabrasClaves(this.ventana, this.autor);
    }

    /**
     * Acción a ejecutar cuando se selecciona el botón Lugares
     * @param evt evento
     */                            
    @Override
    public void btnLugaresClic(ActionEvent evt) {
        IControladorLugares controlador = new ControladorLugares(this.ventana, this.autor);
    }

    /**
     * Acción a ejecutar cuando se selecciona el botón Idiomas
     * @param evt evento
     */                        
    @Override
    public void btnIdiomasClic(ActionEvent evt) {
        IControladorIdiomas controlador = new ControladorIdiomas(this.ventana, this.autor);
    }
    
    /**
     * Acción a ejecutar cuando se selecciona el botón Tipos
     * @param evt evento
     */                        
    @Override
    public void btnTiposClic(ActionEvent evt) {
        IControladorTipos controlador = new ControladorTipos(this.ventana, this.autor);
    }
    
    /**
     * Acción a ejecutar cuando se selecciona el botón Grupos
     * @param evt evento
     */                        
    @Override
    public void btnGruposClic(ActionEvent evt) {
        IControladorGrupos controlador = new ControladorGrupos(this.ventana, this.autor);
    }
    
    /**
     * Acción a ejecutar cuando se selecciona el botón Autores
     * @param evt evento
     */                        
    @Override
    public void btnAutoresClic(ActionEvent evt) {
        IControladorAutores controlador = new ControladorAutores(this.ventana, this.autor);
    }
    
    /**
     * Acción a ejecutar cuando se selecciona el botón Publicaciones
     * @param evt evento
     */                        
    @Override
    public void btnPublicacionesClic(ActionEvent evt) {
        IControladorPublicaciones controlador = new ControladorPublicaciones(this.ventana, this.autor);
    }    
    
    /**
     * Acción a ejecutar cuando se selecciona el botón Salir
     * @param evt evento
     */                            
    @Override
    public void btnSalirClic(ActionEvent evt) {
        int opcion = JOptionPane.showOptionDialog(null, CONFIRMACION, TITULO, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Sí", "No"}, this);       
        if (opcion == JOptionPane.YES_OPTION) {
            this.ventana.dispose();
//            System.exit(0);
            IControladorVentanaLogin controlador = new ControladorVentanaLogin(null);
        }       
    }
        
    public static void main(String[] args) {        
//        GestorIdiomas.instanciar();
//        GestorLugares.instanciar();
//        GestorPalabrasClaves.instanciar();
//        GestorPermisos.instanciar();
//        GestorPublicaciones.instanciar();
//        GestorTipos.instanciar();
        
        IControladorVentanaLogin controlador = new ControladorVentanaLogin(null);
        
/*        
        String titulo = "Título";
        
        IGestorAutores ga = GestorAutores.instanciar();
        Autor a1 = ga.verAutor(1);
        Autor a2 = ga.verAutor(2);
        List<Autor> autores = new ArrayList<>();
        autores.add(a1);
        autores.add(a2);
        
        LocalDate fecha = LocalDate.now();
        
        IGestorTipos gt = GestorTipos.instanciar();
        Tipo tipo = gt.verTipo("Artículo");
        
        IGestorIdiomas gi = GestorIdiomas.instanciar();
        Idioma idioma = gi.verIdioma("Inglés");
        
        IGestorLugares gl = GestorLugares.instanciar();
        Lugar lugar = gl.verLugar("Facultad");
        
        IGestorPalabrasClaves gpc = GestorPalabrasClaves.instanciar();
        PalabraClave pc1 = gpc.verPalabraClave("HARDWARE");
        PalabraClave pc2 = gpc.verPalabraClave("SOFTWARE");
        List<PalabraClave> palabrasClaves = new ArrayList<>();
        palabrasClaves.add(pc1);
        palabrasClaves.add(pc2);
        
        String enlance = "/root/Escritorio/Título.pdf";
        
        String resumen = "Resumen";
        
        IGestorPublicaciones gp = GestorPublicaciones.instanciar();
        gp.nuevaPublicacion(titulo, autores, fecha, tipo, idioma, lugar, palabrasClaves, enlance, resumen);

        List<Publicacion> publicaciones = gp.verPublicaciones();
        for(Publicacion p : publicaciones) {
            System.out.println(p.verTitulo());
            for (Autor a : p.verAutores())
                System.out.println(a.verDNI());
            System.out.println(ManejoDeFechas.transformarLocalDateEnCadena(p.verFechaPublicacion()));
            System.out.println(p.verTipo());
            System.out.println(p.verIdioma());
            System.out.println(p.verLugar());
            for (PalabraClave pc : p.verPalabrasClaves())
                System.out.println(pc.verNombre());
            System.out.println(p.verEnlace());
            System.out.println(p.verResumen());
        }
*/
    }    
}
