/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package permisos.modelos;

import autores.modelos.Autor;
import autores.modelos.GestorAutores;
import grupos.modelos.GestorGrupos;
import grupos.modelos.MiembroEnGrupo;
import grupos.modelos.Grupo;
import grupos.modelos.Rol;
import idiomas.modelos.GestorIdiomas;
import interfaces.IGestorAutores;
import interfaces.IGestorPermisos;
import interfaces.IGestorGrupos;
import interfaces.IGestorIdiomas;
import interfaces.IGestorLugares;
import interfaces.IGestorPalabrasClaves;
import interfaces.IGestorTipos;
import lugares.modelos.GestorLugares;
import palabrasclaves.modelos.GestorPalabrasClaves;
import publicaciones.modelos.Publicacion;
import tipos.modelos.GestorTipos;

public class GestorPermisos implements IGestorPermisos {    
    private static GestorPermisos gestor;
    
    /**
     * Constructor
    */                                            
    private GestorPermisos() {    
    }
    
    /**
     * Método estático que permite crear una única instancia de GestorGrupos
     * @return GestorGrupos
    */                                                            
    public static GestorPermisos instanciar() {
        if (gestor == null) 
            gestor = new GestorPermisos();            
        return gestor;
    } 
               
    @Override
    public Autor validarAutor(int dni, String clave) {        
        if (dni == 0) { //Si el dni = 0, se trata de Admin => hay que ver si está en el grupo de super administradores
            IGestorGrupos gg = GestorGrupos.instanciar();
            Grupo grupo = gg.verGrupo(IGestorGrupos.NOMBRE_SUPER_ADMINISTRADORES);
            if (grupo != null) //no debería ser null nunca
                return grupo.validarMiembro(dni, clave);
        }
        else { //si el dni <> 0, no se trata de Admin => hay que ver si existe el autor
            IGestorAutores ga = GestorAutores.instanciar();
            for (Autor autor : ga.verAutores()) {
                if (autor.validarDocumentoYClave(dni, clave))
                    return autor;
            }
            return null;
        }
        return null;
    }

    @Override
    public boolean puedeCrearTipos(Autor autor) {
        if (autor == null)
            return false;
        
        return autor.esSuperAdministrador();
    }    

    @Override
    public boolean puedeBorrarTipos(Autor autor) {
        if (autor == null)
            return false;
        
        IGestorTipos gt = GestorTipos.instanciar();
        return ((autor.esSuperAdministrador()) && (gt.verTipos().size() > 0));
    }

    @Override
    public boolean puedeBuscarTipos(Autor autor) {
        IGestorTipos gt = GestorTipos.instanciar();
        return (gt.verTipos().size() > 0);
    }

//    @Override
//    public boolean puedeVerTodosLosTipos(Autor autor) {
//        return true;
//    }

    @Override
    public boolean puedeCrearIdiomas(Autor autor) {
        if (autor == null)
            return false;
                
        return autor.esSuperAdministrador();
    }

    @Override
    public boolean puedeBorrarIdiomas(Autor autor) {
        if (autor == null)
            return false;
        
        IGestorIdiomas gi = GestorIdiomas.instanciar();
        return ((autor.esSuperAdministrador()) && (gi.verIdiomas().size() > 0));        
    }

    @Override
    public boolean puedeBuscarIdiomas(Autor autor) {
        IGestorIdiomas gi = GestorIdiomas.instanciar();
        return (gi.verIdiomas().size() > 0);
    }

//    @Override
//    public boolean puedeVerTodosLosIdiomas(Autor autor) {
//        return true;
//    }

    @Override
    public boolean puedeCrearLugares(Autor autor) {
        if (autor == null)
            return false;
        
        return autor.esSuperAdministrador();
    }

    @Override
    public boolean puedeBorrarLugares(Autor autor) {
        if (autor == null)
            return false;
        
        IGestorLugares gl = GestorLugares.instanciar();
        return ((autor.esSuperAdministrador()) && (gl.verLugares().size() > 0));
    }

    @Override
    public boolean puedeBuscarLugares(Autor autor) {
        IGestorLugares gl = GestorLugares.instanciar();
        return (gl.verLugares().size() > 0);
    }

//    @Override
//    public boolean puedeVerTodosLosLugares(Autor autor) {
//        return true;
//    }

    @Override
    public boolean puedeCrearPalabrasClaves(Autor autor) {
        if (autor == null)
            return false;
        
        return autor.esSuperAdministrador();
    }

    @Override
    public boolean puedeBorrarPalabrasClaves(Autor autor) {
        if (autor == null)
            return false;
        
        IGestorPalabrasClaves gpc = GestorPalabrasClaves.instanciar();
        return ((autor.esSuperAdministrador()) && (gpc.verPalabrasClaves().size() > 0));
    }

    @Override
    public boolean puedeBuscarPalabrasClaves(Autor autor) {
        IGestorPalabrasClaves gpc = GestorPalabrasClaves.instanciar();
        return (gpc.verPalabrasClaves().size() > 0);
    }

//    @Override
//    public boolean puedeVerTodasLasPalabrasClaves(Autor autor) {
//        return true;
//    }

    @Override
    public boolean puedeCrearGrupos(Autor autor) {
        if (autor == null)
            return false;
        
        return autor.esSuperAdministrador();
    }

    @Override
    public boolean puedeBorrarGrupo(Autor autor, Grupo grupo) {
        if ((autor == null) || (grupo == null))
            return false;
        
        return (grupo.verNombre().equals(IGestorGrupos.NOMBRE_SUPER_ADMINISTRADORES) ? false : autor.esSuperAdministrador());
    }

    @Override
    public boolean puedeModificarGrupo(Autor autor, Grupo grupo) {
        if ((autor == null) || (grupo == null))
            return false;
        
        if (autor.esSuperAdministrador()) 
            return true;
        else {
            for(MiembroEnGrupo meg : grupo.verMiembros()) {
                if ((meg.verMiembro().equals(autor)) && (meg.verRol() == Rol.ADMINISTRADOR))
                    return true;
            }
            return false;            
        }
    }

    @Override
    public boolean hayGruposParaBorrarModificar(Autor autor) {
        if (autor == null)
            return false;
        
        if (autor.esSuperAdministrador()) {
            IGestorGrupos gg = GestorGrupos.instanciar();
            return gg.verGrupos().size() > 0;
        }
        else
            return autor.verGrupos().size() > 0;
    }

    
//    @Override
//    public boolean puedeAgregarMiembroComoAdministrador(Autor autor, Grupo grupo) {
//        if ((autor == null) || (grupo == null))
//            return false;
//        
//        return ((autor.esSuperAdministrador()) || (autor.tieneEsteRolEnEsteGrupo(grupo, Rol.ADMINISTRADOR)));
//    }

//    @Override
//    public boolean puedeQuitarMiembroComoAdministrador(Autor autor, Grupo grupo) {
//        if ((autor == null) || (grupo == null))
//            return false;
//        
//        return ((autor.esSuperAdministrador()) || (autor.tieneEsteRolEnEsteGrupo(grupo, Rol.ADMINISTRADOR)));
//    }

//    @Override
//    public boolean puedeAgregarMiembroComoColaborador(Autor autor, Grupo grupo) {
//        if ((autor == null) || (grupo == null))
//            return false;
//        
//        return ((autor.esSuperAdministrador()) || (autor.tieneEsteRolEnEsteGrupo(grupo, Rol.ADMINISTRADOR)));
//    }

//    @Override
//    public boolean puedeQuitarMiembroComoColaborador(Autor autor, Grupo grupo) {
//        if ((autor == null) || (grupo == null))
//            return false;
//        
//        return ((autor.esSuperAdministrador()) || (autor.tieneEsteRolEnEsteGrupo(grupo, Rol.ADMINISTRADOR)));
//    }

//    @Override
//    public boolean puedeBuscarTodosGrupos(Autor autor) {
//        if (autor == null)
//            return false;
//        
//        return autor.esSuperAdministrador();
//    }

//    @Override
//    public boolean puedeVerTodosLosGrupos(Autor autor) {
//        if (autor == null)
//            return false;
//        
//        return autor.esSuperAdministrador();
//    }

//    @Override
//    public boolean puedeVerMiembros(Autor autor, Grupo grupo) {
//        if ((autor == null) || (grupo == null))
//            return false;
//        
//        return ((autor.esSuperAdministrador()) || (autor.tieneEsteRolEnEsteGrupo(grupo, Rol.ADMINISTRADOR)) || (autor.tieneEsteRolEnEsteGrupo(grupo, Rol.COLABORADOR)));
//    }

    @Override
    public boolean puedeCrearAutores(Autor autor) {
        if (autor == null)
            return false;
        
        return autor.esSuperAdministrador();
    }

    @Override
    public boolean puedeBorrarProfesores(Autor autor) {
        if (autor == null)
            return false;
        
        IGestorAutores ga = GestorAutores.instanciar();
        return ((autor.esSuperAdministrador()) && (ga.verProfesores().size() > 0));
    }

    @Override
    public boolean puedeBorrarAlumnos(Autor autor) {
        if (autor == null)
            return false;
        
        IGestorAutores ga = GestorAutores.instanciar();
        return ((autor.esSuperAdministrador()) && (ga.verAlumnos().size() > 0));
    }
    
    

    @Override
    public boolean puedeModificarProfesores(Autor autor) {
        IGestorAutores ga = GestorAutores.instanciar();
        if (ga.verProfesores().isEmpty())
            return false;
        
        if (autor.esSuperAdministrador())
            return true;
        else
            return autor.soy().equals(IGestorAutores.SOY_PROFESOR);                    
    }

    @Override
    public boolean puedeModificarAlumnos(Autor autor) {
        IGestorAutores ga = GestorAutores.instanciar();
        if (ga.verAlumnos().isEmpty())
            return false;
        
        if (autor.esSuperAdministrador())
            return true;
        else
            return autor.soy().equals(IGestorAutores.SOY_ALUMNO);                    
    }
    
    

    @Override
    public boolean puedeBuscarProfesores(Autor autor) {
        if (autor == null)
            return false;
        
        IGestorAutores ga = GestorAutores.instanciar();
        return ((autor.esSuperAdministrador()) && (ga.verProfesores().size() > 0));
    }

    @Override
    public boolean puedeBuscarAlumnos(Autor autor) {
        if (autor == null)
            return false;
        
        IGestorAutores ga = GestorAutores.instanciar();
        return ((autor.esSuperAdministrador()) && (ga.verAlumnos().size() > 0));
    }

//    @Override
//    public boolean puedeCrearPublicaciones(Autor autor) {
//        return true;
//    }

    @Override
    public boolean puedeBorrarPublicacion(Autor autor, Publicacion publicacion) {
        if ((autor == null) || (publicacion == null))
            return false;
        
        if (autor.esSuperAdministrador())
            return true;
        else
            return publicacion.verMiembroEnGrupo().verMiembro().equals(autor);
    }
    
    @Override
    public boolean puedeModificarPublicacion(Autor autor, Publicacion publicacion) {
        if ((autor == null) || (publicacion == null))
            return false;
        
        if (autor.esSuperAdministrador())
            return true;
        else
            return publicacion.verMiembroEnGrupo().verMiembro().equals(autor);
    }
    
    
}
