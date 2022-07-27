
package JDialogs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

import classes.validate;

/**
 *
 * @author marcos
 */
public class jDNewConductorRegister extends javax.swing.JDialog {
    /**
     * Método que devuelve el separador de archivos dependiendo el sistema operativo
     * 
     *
     * @return String con el separador de archivos dependiendo el sistema operativo
     */

    public static String getSeparador() {
        String separador = File.pathSeparator; // obtenemos ";" o ":"
        if (separador.equals(";")) { // si es Windows
            separador = "\\";
        }
        if (separador.equals(":")) { // Si es mac o linux
            separador = "/";
        }
        return separador;
    }

    String barra = getSeparador();

    /**
     * Creates new form jDNewConductorRegister
     */
    public jDNewConductorRegister(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        validate validacion = new validate();
        validacion.limitarCaracteres(txtCURP, 18);
        validacion.validarSoloLetras(txtNombre);
        validacion.validarSoloLetras(txtApellidos);
        validacion.validarSoloNumeros(txtNoTel);
        validacion.validarSoloNumeros(txtNoContacto);
        validacion.limitarCaracteres(txtNombre, 30);
        validacion.limitarCaracteres(txtApellidos, 30);
        validacion.limitarCaracteres(txtNoLicencia, 10);
        validacion.limitarCaracteres(txtDireccion, 100);
        validacion.limitarCaracteres(txtNoTel, 10);
        validacion.limitarCaracteres(txtNoContacto, 10);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCURP = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtApellidos = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnGuardarCambios = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtNoLicencia = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNoTel = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtNoContacto = new javax.swing.JTextField();
        lblEditarContraseña = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registro de Conductores");

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel1.setText("CURP:");

        jLabel2.setText("Nombre: ");

        jLabel3.setText("Apellidos: ");

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSave.setText("Guardar");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnGuardarCambios.setText("Guardar Cambios");
        btnGuardarCambios.setEnabled(false);
        btnGuardarCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCambiosActionPerformed(evt);
            }
        });

        jLabel4.setText("No. Licencia:");

        jLabel5.setText("No. Teléfono: ");

        jLabel6.setText("Dirección:");

        jLabel7.setText("Número Tel. Contacto:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                jPanel1Layout.createSequentialGroup()
                                                        .addGap(0, 0, Short.MAX_VALUE)
                                                        .addComponent(btnGuardarCambios)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(btnCancelar)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(btnSave)
                                                        .addGap(12, 12, 12))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel5)
                                                                .addGap(6, 6, 6)
                                                                .addComponent(txtNoTel))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING,
                                                                        false)
                                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                .addComponent(jLabel1)
                                                                                .addPreferredGap(
                                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(txtCURP,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        176,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                .addComponent(jLabel2)
                                                                                .addGap(37, 37, 37)
                                                                                .addComponent(txtNombre)))
                                                                .addGap(0, 0, Short.MAX_VALUE)))
                                                .addGap(18, 18, Short.MAX_VALUE)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel4)
                                                        .addComponent(jLabel3))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtApellidos,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 122,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtNoLicencia,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 124,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout
                                                .createSequentialGroup()
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel6)
                                                                .addGap(32, 32, 32)
                                                                .addComponent(txtDireccion))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel7)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(txtNoContacto,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 178,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(140, 140, 140)))
                                                .addContainerGap()))));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(txtCURP, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3)
                                        .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21,
                                        Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(txtNoLicencia, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5)
                                        .addComponent(txtNoTel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(38, 38, 38)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(22, 22, 22)
                                                .addComponent(jLabel7))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(txtNoContacto, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41,
                                        Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnCancelar)
                                        .addComponent(btnSave)
                                        .addComponent(btnGuardarCambios))
                                .addGap(21, 21, 21)));

        lblEditarContraseña.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblEditarContraseña.setForeground(new java.awt.Color(204, 51, 0));
        lblEditarContraseña.setText("Nuevo Registro");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblEditarContraseña)
                                .addGap(197, 197, 197)));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblEditarContraseña)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap()));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(0, 0, 0)));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }// GEN-LAST:event_btnCancelarActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSaveActionPerformed

        // Aun no vlaida si el usuario ya ha sido registrado con anteriroridad
        boolean contador = false;
        if ("".equals(txtCURP.getText()) || "".equals(txtNombre.getText()) || "".equals(txtApellidos.getText())
                || "".equals(txtNoLicencia.getText()) || "".equals(txtNoTel.getText())
                || "".equals(txtDireccion.getText()) || "".equals(txtNoContacto.getText())) {
            JOptionPane.showMessageDialog(null, "Favor de llenar todos los campos solicitados", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            for (int i = 0; i < jDConductorRegister.jtConductors.getRowCount(); i++) {
                String a = jDConductorRegister.jtConductors.getValueAt(i, 0).toString();
                if (a.equals(txtCURP.getText().toUpperCase())) {
                    contador = false;
                    break;
                } else {
                    contador = true;
                }
            }

            if (contador) {

                String ruta = "src" + barra + "logs" + barra + "Conductores" + barra + "Conductores.txt";

                jDConductorRegister.AgregarRegistroTabla(new Object[] {
                        txtCURP.getText().toUpperCase(),
                        txtNombre.getText().toUpperCase(),
                        txtApellidos.getText().toUpperCase(),
                        txtNoLicencia.getText(),
                        txtNoTel.getText(),
                        txtDireccion.getText().toUpperCase(),
                        txtNoContacto.getText().toUpperCase() });
                try {
                    FileWriter fw = new FileWriter(ruta);

                    BufferedWriter bw = new BufferedWriter(fw);

                    for (int i1 = 0; i1 < jDConductorRegister.jtConductors.getRowCount(); i1++) {// rows
                        for (int j = 0; j < jDConductorRegister.jtConductors.getColumnCount(); j++) {// columns
                            bw.write(jDConductorRegister.jtConductors.getValueAt(i1, j).toString() + "_");
                        }
                        bw.newLine();
                    }

                    bw.close();
                    fw.close();

                } catch (IOException ex) {
                    Logger.getLogger(jDNewConductorRegister.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showMessageDialog(null, "Registro exitoso", "Nuevo Registro",
                        JOptionPane.INFORMATION_MESSAGE);
                this.dispose();

            } else {
                JOptionPane.showMessageDialog(null, "CURP del conductor ya registrado con anterioridad",
                        "Nuevo Registro", JOptionPane.ERROR_MESSAGE);
            }

        }
    }// GEN-LAST:event_btnSaveActionPerformed

    private void btnGuardarCambiosActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnGuardarCambiosActionPerformed
        if ("".equals(txtCURP.getText()) || "".equals(txtNombre.getText()) || "".equals(txtApellidos.getText())
                || "".equals(txtNoLicencia.getText()) || "".equals(txtNoTel.getText())
                || "".equals(txtDireccion.getText()) || "".equals(txtNoContacto.getText())) {
            JOptionPane.showMessageDialog(null, "Favor de llenar todos los campos solicitados", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {

            try {
                int indice = jDConductorRegister.jtConductors.getSelectedRow();
                String ruta = "src" + barra + "logs" + barra + "Conductores" + barra + "Conductores.txt";
                TableModel Modelo = jDConductorRegister.jtConductors.getModel();
                Modelo.setValueAt(txtCURP.getText().toUpperCase(), jDConductorRegister.jtConductors.getSelectedRow(),
                        0);
                Modelo.setValueAt(txtNombre.getText().toUpperCase(), jDConductorRegister.jtConductors.getSelectedRow(),
                        1);
                Modelo.setValueAt(txtApellidos.getText().toUpperCase(),
                        jDConductorRegister.jtConductors.getSelectedRow(), 2);
                Modelo.setValueAt(txtNoLicencia.getText(), jDConductorRegister.jtConductors.getSelectedRow(), 3);
                Modelo.setValueAt(txtNoTel.getText(), jDConductorRegister.jtConductors.getSelectedRow(), 4);
                Modelo.setValueAt(txtDireccion.getText().toUpperCase(),
                        jDConductorRegister.jtConductors.getSelectedRow(), 5);
                Modelo.setValueAt(txtNoContacto.getText(), jDConductorRegister.jtConductors.getSelectedRow(), 6);

                FileWriter fw = new FileWriter(ruta);

                BufferedWriter bw = new BufferedWriter(fw);

                for (int i = 0; i < jDConductorRegister.jtConductors.getRowCount(); i++) {// rows
                    for (int j = 0; j < jDConductorRegister.jtConductors.getColumnCount(); j++) {// columns
                        bw.write(jDConductorRegister.jtConductors.getValueAt(i, j).toString() + "_");
                    }
                    bw.newLine();
                }

                bw.close();
                fw.close();
                this.dispose();

            } catch (IOException ex) {
                Logger.getLogger(jDConductorRegister.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }// GEN-LAST:event_btnGuardarCambiosActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(jDNewConductorRegister.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jDNewConductorRegister.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jDNewConductorRegister.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jDNewConductorRegister.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                jDNewConductorRegister dialog = new jDNewConductorRegister(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    public static javax.swing.JButton btnGuardarCambios;
    public static javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JLabel lblEditarContraseña;
    public javax.swing.JTextField txtApellidos;
    public javax.swing.JTextField txtCURP;
    public javax.swing.JTextField txtDireccion;
    public javax.swing.JTextField txtNoContacto;
    public javax.swing.JTextField txtNoLicencia;
    public javax.swing.JTextField txtNoTel;
    public javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
