/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autores.vistas;

import interfaces.IControladorAMProfesor;
import java.awt.Dialog;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;

public class VentanaAMProfesor extends JDialog {
    private IControladorAMProfesor controlador;

    /**
     * Constructor
     * @param controlador controlador de la ventana
     * @param ventanaPadre ventana padre (VentanaAutores en este caso)
     */
    public VentanaAMProfesor(IControladorAMProfesor controlador, Dialog ventanaPadre) {
        super(ventanaPadre, true);
        initComponents();
        this.controlador = controlador;        
    }

    /**
     * Devuelve el campo txtApellidos
     * @return String  - campo txtApellidos
     */    
    public JTextField verTxtApellidos() {
        return this.txtApellidos;
    }
    
    /**
     * Devuelve el campo txtNombres
     * @return JTextField  - campo txtNombres
     */
    public JTextField verTxtNombres() {
        return this.txtNombres;
    }
    
    /**
     * Devuelve el campo txtDNI
     * @return JTextField  - campo txtDNI
     */
    public JTextField verTxtDNI() {
        return this.txtDNI;
    }        
    
    /**
     * Devuelve el combo Cargo
     * @return JComboBox  - combo Cargo
     */        
    public JComboBox verComboCargo() {
        return this.comboCargo;
    }
    
    /**
     * Devuelve el campo passClave
     * @return JPasswordField  - campo passClave
     */    
    public JPasswordField verPassClave() {
        return this.passClave;
    }
     
    /**
     * Devuelve el campo passRepetirClave
     * @return JPasswordField  - campo passRepetirClave
     */    
    public JPasswordField verPassRepetirClave() {
        return this.passRepetirClave;
    }
    
    
    /**
     * Devuelve la tabla tablaGrupos
     * @return JTable  - tabla tablaGrupos
     */ 
    public JTable verTablaGrupos() {
        return this.tablaGrupos;
    }    
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtApellidos = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNombres = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        comboCargo = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtDNI = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaGrupos = new javax.swing.JTable();
        passClave = new javax.swing.JPasswordField();
        passRepetirClave = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                ventanaObtenerFoco(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jLabel1.setText("Apellidos:");

        txtApellidos.setToolTipText("Apellidos del profesor");
        txtApellidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidosPresionarTecla(evt);
            }
        });

        jLabel2.setText("Nombres:");

        txtNombres.setToolTipText("Nombres del profesor");
        txtNombres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombresPresionarTecla(evt);
            }
        });

        btnGuardar.setMnemonic('G');
        btnGuardar.setText("Guardar");
        btnGuardar.setToolTipText("Guarda el profesor");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClic(evt);
            }
        });

        btnCancelar.setMnemonic('C');
        btnCancelar.setText("Cancelar");
        btnCancelar.setToolTipText("Cancela la operación");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarClic(evt);
            }
        });

        jLabel3.setText("Cargo:");

        comboCargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboCargo.setToolTipText("Cargo del profesor");

        jLabel4.setText("Documento:");

        txtDNI.setToolTipText("Documento del profesor");
        txtDNI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDNIPresionarTecla(evt);
            }
        });

        jLabel5.setText("Grupos:");

        tablaGrupos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaGrupos);

        passClave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                passClavePresionarTecla(evt);
            }
        });

        passRepetirClave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                passRepetirClavePresionarTecla(evt);
            }
        });

        jLabel6.setText("Clave:");

        jLabel7.setText("Repetir clave:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(409, 409, 409))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnGuardar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombres)
                            .addComponent(txtDNI)
                            .addComponent(txtApellidos)
                            .addComponent(comboCargo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(passClave)
                            .addComponent(passRepetirClave))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passRepetirClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnCancelar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarClic(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarClic
        this.controlador.btnCancelarClic(evt);
    }//GEN-LAST:event_btnCancelarClic

    private void btnGuardarClic(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClic
        this.controlador.btnGuardarClic(evt);
    }//GEN-LAST:event_btnGuardarClic

    private void txtApellidosPresionarTecla(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidosPresionarTecla
        this.controlador.txtApellidosPresionarTecla(evt);
    }//GEN-LAST:event_txtApellidosPresionarTecla

    private void txtDNIPresionarTecla(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDNIPresionarTecla
        this.controlador.txtDocumentoPresionarTecla(evt);
    }//GEN-LAST:event_txtDNIPresionarTecla

    private void txtNombresPresionarTecla(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombresPresionarTecla
        this.controlador.txtNombresPresionarTecla(evt);
    }//GEN-LAST:event_txtNombresPresionarTecla

    private void ventanaObtenerFoco(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_ventanaObtenerFoco
        this.controlador.ventanaObtenerFoco(evt);
    }//GEN-LAST:event_ventanaObtenerFoco

    private void passClavePresionarTecla(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passClavePresionarTecla
        this.controlador.passClavePresionarTecla(evt);
    }//GEN-LAST:event_passClavePresionarTecla

    private void passRepetirClavePresionarTecla(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passRepetirClavePresionarTecla
        this.controlador.passRepetirClavePresionarTecla(evt);
    }//GEN-LAST:event_passRepetirClavePresionarTecla


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> comboCargo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPasswordField passClave;
    private javax.swing.JPasswordField passRepetirClave;
    private javax.swing.JTable tablaGrupos;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtDNI;
    private javax.swing.JTextField txtNombres;
    // End of variables declaration//GEN-END:variables
}
