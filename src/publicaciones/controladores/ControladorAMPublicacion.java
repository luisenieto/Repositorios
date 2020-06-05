/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package publicaciones.controladores;

import autores.modelos.Autor;
import publicaciones.modelos.ModeloTablaPalabrasClaves;
import auxiliares.Apariencia;
import auxiliares.ManejoDeFechas;
import com.toedter.calendar.JDateChooser;
import grupos.modelos.Grupo;
import grupos.modelos.MiembroEnGrupo;
import idiomas.modelos.Idioma;
import interfaces.IControladorAMPublicacion;
import interfaces.IControladorPrincipal;
import interfaces.IGestorPublicaciones;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import publicaciones.vistas.VentanaAMPublicacion;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import lugares.modelos.Lugar;
import palabrasclaves.modelos.PalabraClave;
import publicaciones.modelos.GestorPublicaciones;
import publicaciones.modelos.ModeloComboGrupos;
import publicaciones.modelos.ModeloComboIdiomas;
import publicaciones.modelos.ModeloComboLugares;
import publicaciones.modelos.ModeloComboTipos;
import publicaciones.modelos.Publicacion;
import tipos.modelos.Tipo;


public class ControladorAMPublicacion implements IControladorAMPublicacion {       
    private VentanaAMPublicacion ventana;  
    private Publicacion publicacion;
    
    private Autor autor;
    //autor que se logueó
    
    /**
     * Constructor
     * @param ventanaPadre (VentanaPublicaciones en este caso)
     * @param autor autor sobre el cual hay que ver qué habilitar según sus grupos
     */    
    public ControladorAMPublicacion(Dialog ventanaPadre, Autor autor) {
        this(ventanaPadre, null, autor);
    }    
    
    /**
     * Constructor
     * @param ventanaPadre (VentanaPublicaciones en este caso)
     * @param publicacion publicacion a modificar
     * @param autor autor sobre el cual hay que ver qué habilitar según sus grupos
     */    
    public ControladorAMPublicacion(Dialog ventanaPadre, Publicacion publicacion, Autor autor) {
        this.publicacion = publicacion;
        this.autor = autor;
        this.ventana = new VentanaAMPublicacion(this, ventanaPadre);
        this.ventana.setTitle(this.publicacion == null ? TITULO_NUEVA + " - (" + this.autor.verApeYNom() + ")" : TITULO_MODIFICAR  + " - (" + this.autor.verApeYNom() + ")");
        this.ventana.setLocationRelativeTo(null); 
        
        if (this.publicacion == null)  //nueva publicación                                    
            this.ventana.verTxtTitulo().requestFocus();
        else { //modificación de publicación
            this.ventana.verTxtTitulo().setText(this.publicacion.verTitulo());
            this.ventana.verTxtTitulo().setEnabled(false);
            this.ventana.verTxtEnlace().setText(this.publicacion.verEnlace());
            this.ventana.verTxtResumen().setText(this.publicacion.verResumen());
        }
                
        this.configurarComboGrupos(new ModeloComboGrupos(this.autor));
        this.configurarComboTipos(new ModeloComboTipos());
        this.configurarComboIdiomas(new ModeloComboIdiomas());
        this.configurarComboLugares(new ModeloComboLugares());
        
        this.configurarDateChooserFecha();
        
        this.configurarTablaPalabrasClaves(new ModeloTablaPalabrasClaves());
//        this.configurarVentana();
        Apariencia.asignarNimbusLookAndFeel("Nimbus");
        
        this.ventana.setVisible(true);
    }

    
    /**
     * Configura la ventana según el autor
     */
//    private void configurarVentana() {                
//        if (this.publicacion == null) { //nueva publicación                                    
//            this.ventana.verTxtTitulo().requestFocus();
//        }
//        else { //modificación de publicación
//            this.ventana.verTxtTitulo().setText(this.publicacion.verTitulo());
//            this.ventana.verTxtTitulo().setEnabled(false);
//            this.ventana.verTxtEnlace().setText(this.publicacion.verEnlace());
//            this.ventana.verTxtResumen().setText(this.publicacion.verResumen());
//            this.ventana.verDateChooserFecha().setEnabled(this.publicacion.puedeSerModificadaPor(this.autor));
            
            //sólo puede modificar una publicación su autor
//            this.ventana.verTxtEnlace().setEditable(this.publicacion.puedeSerModificadaPor(this.autor));
//            this.ventana.verBtnAbrir().setEnabled(this.publicacion.puedeSerModificadaPor(this.autor));
//            this.ventana.verComboGrupos().setEnabled(this.publicacion.puedeSerModificadaPor(this.autor));
//            this.ventana.verComboLugares().setEnabled(this.publicacion.puedeSerModificadaPor(this.autor));
//            this.ventana.verComboTipos().setEnabled(this.publicacion.puedeSerModificadaPor(this.autor));
//            this.ventana.verComboIdiomas().setEnabled(this.publicacion.puedeSerModificadaPor(this.autor));
//            this.ventana.verTablaPalabrasClaves().setEnabled(this.publicacion.puedeSerModificadaPor(this.autor));
//            this.ventana.verBtnTodas().setEnabled(this.publicacion.puedeSerModificadaPor(this.autor));
//            this.ventana.verBtnNinguna().setEnabled(this.publicacion.puedeSerModificadaPor(this.autor));
//            this.ventana.verTxtResumen().setEditable(this.publicacion.puedeSerModificadaPor(this.autor));
//            this.ventana.verBtnGuardar().setEnabled(this.publicacion.puedeSerModificadaPor(this.autor));
//        }
//              
//    }
    
    /**
     * Configura la tabla de palabras claves asignándole un modelo y seleccionando la primera fila (si hay filas)
     * @param mtpc modelo para la tabla de palabras claves
     */
    private void configurarTablaPalabrasClaves(ModeloTablaPalabrasClaves mtpc) {
        JTable tablaPalabrasClaves = this.ventana.verTablaPalabrasClaves();
        tablaPalabrasClaves.setModel(mtpc);
        
        if (this.publicacion != null) {
            tablaPalabrasClaves.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //permite seleccionar múltiples filas no contiguas

            //se seleccionan las filas correspondientes a las palabras claves de la publicación
            ListSelectionModel modeloSeleccion = tablaPalabrasClaves.getSelectionModel();        
            for(PalabraClave palabraClave : this.publicacion.verPalabrasClaves()) {
                for(int fila = 0; fila < mtpc.getRowCount(); fila++) {
                    PalabraClave pc = mtpc.verPalabraClave(fila);
                    if (palabraClave.equals(pc)) {
                        modeloSeleccion.addSelectionInterval(fila, fila);
                        break;
                    }
                }
            }
        }        
    } 
    
    /**
     * Configura el combo de grupos asignándole un modelo y seleccionando el primero (si hay grupos)
     * @param mcg modelo para el combo de grupos
     */
    private void configurarComboGrupos(ModeloComboGrupos mcg) {
        JComboBox comboGrupos = this.ventana.verComboGrupos();
        comboGrupos.setModel(mcg);
        
        if (this.publicacion != null) {
            mcg.seleccionarGrupo(this.publicacion.verMiembroEnGrupo().verGrupo());
        }
    }
    
    /**
     * Configura el combo de tipos asignándole un modelo y seleccionando el primero (si hay tipos)
     * @param mct modelo para el combo de tipos
     */
    private void configurarComboTipos(ModeloComboTipos mct) {
        JComboBox comboTipos = this.ventana.verComboTipos();
        comboTipos.setModel(mct);
        
        if (this.publicacion != null) {
            mct.seleccionarTipo(this.publicacion.verTipo());
        }
    }
    
    /**
     * Configura el combo de idiomas asignándole un modelo y seleccionando el primero (si hay idiomas)
     * @param mci modelo para el combo de idiomas
     */
    private void configurarComboIdiomas(ModeloComboIdiomas mci) {
        JComboBox comboIdiomas = this.ventana.verComboIdiomas();
        comboIdiomas.setModel(mci);
        
        if (this.publicacion != null) {
            mci.seleccionarIdioma(this.publicacion.verIdioma());
        }        
    }
    
    /**
     * Configura el combo de lugares asignándole un modelo y seleccionando el primero (si hay lugares)
     * @param mcl modelo para el combo de lugares
     */
    private void configurarComboLugares(ModeloComboLugares mcl) {
        JComboBox comboLugares = this.ventana.verComboLugares();
        comboLugares.setModel(mcl);
        
        if (this.publicacion != null) {
            mcl.seleccionarLugar(this.publicacion.verLugar());
        }        
    }
    
    /**
     * Configura el dateChooser fecha de publicación
     */
    private void configurarDateChooserFecha() {
        JDateChooser dateChooserFecha = this.ventana.verDateChooserFecha();
        GregorianCalendar fecha;        
        if (this.publicacion == null)
            fecha = ManejoDeFechas.transformarLocalDateEnGregorianCalendar(LocalDate.now());
        else
            fecha = ManejoDeFechas.transformarLocalDateEnGregorianCalendar(this.publicacion.verFechaPublicacion());
        dateChooserFecha.setCalendar(fecha);
    }
            
    @Override
    public void btnGuardarClic(ActionEvent evt) {
        String titulo = this.ventana.verTxtTitulo().getText().trim();
        
        Grupo grupo = this.verGrupo();
        MiembroEnGrupo meg = this.armarMiembroEnGrupo(grupo);
                            
        Tipo tipo = this.verTipo();
        
        Idioma idioma = this.verIdioma();
        
        Lugar lugar = this.verLugar();
        
        LocalDate fechaPublicacion = this.verFecha();
        
        String enlace = this.ventana.verTxtEnlace().getText().trim();
        
        List<PalabraClave> palabrasClaves = this.verPalabrasClaves();
        
        String resumen = this.ventana.verTxtResumen().getText().trim();
        
        IGestorPublicaciones gp = GestorPublicaciones.instanciar();        
        if (this.publicacion == null) { //nueva publicación
            String resultado = gp.nuevaPublicacion(titulo, meg, fechaPublicacion, tipo, idioma, lugar, palabrasClaves, enlace, resumen);
            if (!resultado.equals(IGestorPublicaciones.EXITO))
                JOptionPane.showOptionDialog(null, resultado, TITULO_NUEVA, JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null, new Object[] {"Aceptar"}, this);       
            else
                this.ventana.dispose();             
        }
        else { //modificación de publicación
            String resultado = gp.modificarPublicacion(this.publicacion, meg, fechaPublicacion, tipo, idioma, lugar, palabrasClaves, enlace, resumen);
            if (!resultado.equals(IGestorPublicaciones.EXITO))
                JOptionPane.showOptionDialog(null, resultado, TITULO_MODIFICAR, JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null, new Object[] {"Aceptar"}, this);       
            else
                this.ventana.dispose();                         
        }
        
    }
        
    /**
     * Devuelve la fecha del campo de fecha de publicación
     * @return LocalDate  - fecha de publicación
     */
    private LocalDate verFecha() {
        JDateChooser dateChooserFecha = this.ventana.verDateChooserFecha();
        return ManejoDeFechas.obtenerFechaDeJDateChooser(dateChooserFecha);        
    }
    
    /**
     * Devuelve el grupo del combo de grupos
     * @return Grupo  - grupo del autor de la publicación
     */
    private Grupo verGrupo() {
        JComboBox comboGrupos = this.ventana.verComboGrupos();
        ModeloComboGrupos mcg = (ModeloComboGrupos)comboGrupos.getModel();
        return mcg.obtenerGrupo();
    }
    
    /**
     * Arma el objeto MiembroEnGrupo según el grupo especificado
     * @param grupo grupo con el cual se arma el objeto MiembroEnGrupo
     * @return MiembroEnGrupo  - objeto MiembroEnGrupo correspondiente al grupo especificado, null en caso que el grupo no se especifique
     */
    private MiembroEnGrupo armarMiembroEnGrupo(Grupo grupo) {
        if (grupo != null) {
            for(MiembroEnGrupo meg : this.autor.verGrupos()) {
                if (meg.verGrupo().equals(grupo))
                    return meg;
            }
        }
        return null;
    }
    
    /**
     * Devuelve el tipo del combo de tipos
     * @return Tipo  - tipo de publicación
     */
    private Tipo verTipo() {
        JComboBox comboTipos = this.ventana.verComboTipos();
        ModeloComboTipos mct = (ModeloComboTipos)comboTipos.getModel();
        return mct.obtenerTipo();
    }
    
    /**
     * Devuelve el idioma del combo de idiomas
     * @return Idioma  - idioma de publicación
     */
    private Idioma verIdioma() {
        JComboBox comboIdiomas = this.ventana.verComboIdiomas();
        ModeloComboIdiomas mci = (ModeloComboIdiomas)comboIdiomas.getModel();
        return mci.obtenerIdioma();
    }
    
    /**
     * Devuelve el lugar del combo de lugares
     * @return Lugar  - lugar de la publicación
     */
    private Lugar verLugar() {
        JComboBox comboLugares = this.ventana.verComboLugares();
        ModeloComboLugares mcl = (ModeloComboLugares)comboLugares.getModel();
        return mcl.obtenerLugar();
    }
        
    /**
     * Devuelve las palabras claves seleccionadas de la tabla de plabras claves
     * @return List<PalabraClave>  - lista de palabras claves seleccionadas
     */
    private List<PalabraClave> verPalabrasClaves() {
        JTable tablaPalabrasClaves = this.ventana.verTablaPalabrasClaves();
        ModeloTablaPalabrasClaves modelo = (ModeloTablaPalabrasClaves)tablaPalabrasClaves.getModel();
        List<PalabraClave> palabrasClaves = new ArrayList<>();
        int[] filasSeleccionadas = tablaPalabrasClaves.getSelectedRows();
        for (int i = 0; i < filasSeleccionadas.length; i++) {
            PalabraClave palabraClave = modelo.verPalabraClave(filasSeleccionadas[i]);
            palabrasClaves.add(palabraClave);            
        }
        return palabrasClaves;
    }
    
    @Override
    public void btnCancelarClic(ActionEvent evt) {
        this.ventana.dispose();
    }

    @Override
    public void txtTituloPresionarTecla(KeyEvent evt) {
        this.presionarTecla(evt);
    }
        
    /**
     * Acción a ejecutar cuando se presiona una tecla en los campos txtNombre y txtDescripcion
     * @param evt evento
     */
    private void presionarTecla(KeyEvent evt) {
        char c = evt.getKeyChar();            
        if (!Character.isLetter(c)) { //sólo se aceptan letras, Enter, Del, Backspace y espacio
            switch(c) {
                case KeyEvent.VK_ENTER: 
                    this.btnGuardarClic(null); //no importa el evento en este caso
                    break;
                case KeyEvent.VK_BACK_SPACE:    
                case KeyEvent.VK_DELETE:
                case KeyEvent.VK_SPACE:
                    break;
                default:
                    evt.consume(); //consume el evento para que no sea procesado por la fuente
            }
        }
    }    

    @Override
    public void btnTodasLasPalabrasClavesClic(ActionEvent evt) {
        JTable tablaPalabrasClaves = this.ventana.verTablaPalabrasClaves();
        ModeloTablaPalabrasClaves mtpc = (ModeloTablaPalabrasClaves)tablaPalabrasClaves.getModel();
        
        tablaPalabrasClaves.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        //permite seleccionar múltiples filas no contiguas
        
        //se seleccionan todas las filas
        ListSelectionModel modeloSeleccion = tablaPalabrasClaves.getSelectionModel(); 
        
        modeloSeleccion.addSelectionInterval(0, mtpc.getRowCount() - 1);        
    }

    @Override
    public void btnNingunaPalabraClaveClic(ActionEvent evt) {
        JTable tablaPalabrasClaves = this.ventana.verTablaPalabrasClaves();
        
        ListSelectionModel modeloSeleccion = tablaPalabrasClaves.getSelectionModel(); 
        
        modeloSeleccion.clearSelection();        
    }
    
    @Override
    public void btnAbrirClic(ActionEvent evt) {
        //Se ponen en español los nombres de los botones de la ventana de diálogo
        UIManager.put("FileChooser.openButtonText","Abrir");
        UIManager.put("FileChooser.openButtonToolTipText", "Abrir");
        UIManager.put("FileChooser.cancelButtonText","Cancelar");
        UIManager.put("FileChooser.cancelButtonToolTipText", "Cancelar");
        UIManager.put("FileChooser.lookInLabelText", "Buscar en:");
        UIManager.put("FileChooser.fileNameLabelText", "Archivo:");
        UIManager.put("FileChooser.filesOfTypeLabelText", "Archivos del tipo:");
        UIManager.put("FileChooser.upFolderToolTipText", "Subir un nivel");
        UIManager.put("FileChooser.homeFolderToolTipText", "Inicio");
        UIManager.put("FileChooser.newFolderToolTipText", "Carpeta nueva");
        UIManager.put("FileChooser.listViewButtonToolTipText", "Lista");
        UIManager.put("FileChooser.detailsViewButtonToolTipText", "Detalles");
        
        JFileChooser selector = new JFileChooser();
        selector.setCurrentDirectory(new File(System.getProperty("user.home")));
        //se establece la carpeta personal del usuario para empezar la búsqueda
        selector.setDialogTitle(IControladorPrincipal.TITULO);
        selector.setAcceptAllFileFilterUsed(false); //no se muestra el filtro de todos los archivos
        
        int resultado = selector.showOpenDialog(null);
        
        if (resultado == JFileChooser.APPROVE_OPTION) { //se selecciona un archivo
            File selectedFile = selector.getSelectedFile();
            this.ventana.verTxtEnlace().setText(selectedFile.getAbsolutePath());
        }        
    }
}
