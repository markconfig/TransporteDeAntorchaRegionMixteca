
package JDialogs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author marcos
 */
public class jDConductorRegister extends javax.swing.JDialog {

    jDNewConductorRegister NewRegs = new jDNewConductorRegister(null, rootPaneCheckingEnabled);
    DefaultTableModel modelo = new DefaultTableModel();
    Object[] Registros = new Object[7];

    /**
     * Creates new form jDConductorRegister
     */
    public jDConductorRegister(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        MostrarDatos();
    }

    /**
     * Método que devuelve el separador de archivos dependiendo el sistema operativo
     * 
     *
     * @return String con el separador de archivos dependiendo el sistema operativo
     */

    public static String getSeparador() {
        String separador = File.pathSeparator; // obtenemos ";" o ":"
        if (separador.equals(";")) { // si es Windows
            separador = "/";
        }
        if (separador.equals(":")) { // Si es mac o linux
            separador = "\\";
        }
        return separador;
    }

    public static void AgregarRegistroTabla(Object[] Registro) {

        DefaultTableModel datos = (DefaultTableModel) jtConductors.getModel();
        // datos.removeTableModelListener(tableUsers);
        datos.addRow(Registro);
    }

    public void MostrarDatos() {
        String Ruta = "src" + getSeparador() + "logs" + getSeparador() + "Conductores" + getSeparador()
                + "Conductores.txt";
        File file = new File(Ruta);

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            DefaultTableModel model = (DefaultTableModel) jtConductors.getModel();
            Object[] lines = br.lines().toArray();

            for (int i = 0; i < lines.length; i++) {
                String[] row = lines[i].toString().split("_");
                model.addRow(row);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(jDConductorRegister.class.getName()).log(Level.SEVERE, null, ex);
        }
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnNew = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtConductors = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registro de Conductores");

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        btnNew.setText("Nuevo");
        btnNew.setToolTipText("Agregar un nuevo conductor");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnEdit.setText("Editar");
        btnEdit.setToolTipText("Editar un conductor");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setText("Eliminar");
        btnDelete.setToolTipText("Eliminar Conductor");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnExit.setText("Salir");
        btnExit.setToolTipText("Salir del Registro de conductores");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEdit)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelete)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 517,
                                        Short.MAX_VALUE)
                                .addComponent(btnExit)
                                .addContainerGap()));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnNew)
                                        .addComponent(btnEdit)
                                        .addComponent(btnDelete)
                                        .addComponent(btnExit))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jtConductors.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jtConductors.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "CURP", "Nombre ", "Apellidos", "No. Licencia", "No. Teléfono", "Dirección",
                        "Número De Contacto"
                }) {
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jtConductors.setRowHeight(30);
        jScrollPane1.setViewportView(jtConductors);
        if (jtConductors.getColumnModel().getColumnCount() > 0) {
            jtConductors.getColumnModel().getColumn(0).setPreferredWidth(80);
            jtConductors.getColumnModel().getColumn(1).setPreferredWidth(50);
            jtConductors.getColumnModel().getColumn(2).setPreferredWidth(60);
            jtConductors.getColumnModel().getColumn(3).setPreferredWidth(50);
            jtConductors.getColumnModel().getColumn(4).setPreferredWidth(50);
            jtConductors.getColumnModel().getColumn(5).setPreferredWidth(80);
            jtConductors.getColumnModel().getColumn(6).setPreferredWidth(50);
        }

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 51, 0));
        jLabel1.setText("Registro de Conductores");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                jPanel1Layout.createSequentialGroup()
                                                        .addComponent(jLabel1)
                                                        .addGap(314, 314, 314))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jScrollPane1,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 919,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(46, 46, 46)))));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap()));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 940,
                                javax.swing.GroupLayout.PREFERRED_SIZE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnNewActionPerformed
        jDNewConductorRegister Newregister = new jDNewConductorRegister(null, rootPaneCheckingEnabled);
        Newregister.show();
    }// GEN-LAST:event_btnNewActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnEditActionPerformed
        int indice = jtConductors.getSelectedRow();
        if (jtConductors.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un conductor a editar", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            TableModel Modelo = jtConductors.getModel();
            String curp = Modelo.getValueAt(indice, 0).toString();
            String Nombre = Modelo.getValueAt(indice, 1).toString();
            String apellido = Modelo.getValueAt(indice, 2).toString();
            String Nolicencia = Modelo.getValueAt(indice, 3).toString();
            String NoTelefono = Modelo.getValueAt(indice, 4).toString();
            String Direccion = Modelo.getValueAt(indice, 5).toString();
            String Nocontacto = Modelo.getValueAt(indice, 6).toString();
            NewRegs.lblEditarContraseña.setText("Editar Conductores");

            NewRegs.txtCURP.setText(curp);
            NewRegs.txtNombre.setText(Nombre);
            NewRegs.txtApellidos.setText(apellido);
            NewRegs.txtNoLicencia.setText(Nolicencia);
            NewRegs.txtNoTel.setText(NoTelefono);
            NewRegs.txtDireccion.setText(Direccion);
            NewRegs.txtNoContacto.setText(Nocontacto);
            NewRegs.btnSave.setEnabled(false);
            NewRegs.btnGuardarCambios.setEnabled(true);

            NewRegs.pack();
            NewRegs.setLocationRelativeTo(null);
            NewRegs.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            NewRegs.show();
        }
    }// GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDeleteActionPerformed
        int indice = jtConductors.getSelectedRow();
        if (jtConductors.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario a eliminar", "Error", JOptionPane.ERROR_MESSAGE);
        } else {

            int res = JOptionPane.showConfirmDialog(null, "¿Usted está seguro de eliminar a este usuario?", "Alerta",
                    JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (res == 0) {
                DefaultTableModel m = (DefaultTableModel) jtConductors.getModel();
                m.removeRow(jtConductors.getSelectedRow());

                // Guardar el contenido de la tabloas tras haber borrado un usuario
                String ruta = "src" + getSeparador() + "logs" + getSeparador() + "Conductores" + getSeparador()
                        + "Conductores.txt";
                try {
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

                } catch (IOException ex) {
                    Logger.getLogger(jDConductorRegister.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }// GEN-LAST:event_btnDeleteActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnExitActionPerformed
        this.dispose();
    }// GEN-LAST:event_btnExitActionPerformed

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
            java.util.logging.Logger.getLogger(jDConductorRegister.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jDConductorRegister.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jDConductorRegister.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jDConductorRegister.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        }
        // </editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                jDConductorRegister dialog = new jDConductorRegister(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnNew;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jtConductors;
    // End of variables declaration//GEN-END:variables
}