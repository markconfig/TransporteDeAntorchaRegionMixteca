
package JDialogs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import classes.Data;
import classes.validate;

/**
 *
 * @author marcos
 */
public class jDNewVehicleRegister extends javax.swing.JDialog {
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

    String barra = getSeparador();

    /**
     * Creates new form jDNewVehicleRegister
     */
    public jDNewVehicleRegister(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        getListjComboBoxCURPS();
        setLocationRelativeTo(null);
        validate a = new validate();
        a.limitarCaracteres(txtPlacas, 10);
        a.validarSoloLetras(txtDueñoVehiculo);
        a.limitarCaracteres(txtDueñoVehiculo, 50);

    }

    private void getListjComboBoxCURPS() {
        // Limpiamos lo que tenga en el jComboBox
        jCBCURPOperador.removeAllItems();
        String ruta = "src" + barra + "logs" + barra + "Conductores" + barra + "Conductores.txt";

        FileReader fr = null;

        try {
            String lineaActual;
            Scanner sc = new Scanner(new File(ruta));
            File f = new File(ruta);

            fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            try {
                while ((lineaActual = br.readLine()) != null) {
                    StringTokenizer token = new StringTokenizer(lineaActual);
                    String CURP = token.nextToken("_");
                    jCBCURPOperador.addItem(String.valueOf(CURP));
                }

            } catch (IOException ex) {
                Logger.getLogger(jDLogin.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(jDLogin.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(jDLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void Save(String TipoTrans, JTable jtable) {
        boolean contador = false;
        if ("".equals(txtUnidad.getText()) || "".equals(txtPlacas.getText()) || "".equals(jCBVehiculo.getSelectedItem())
                || "".equals(txtDueñoVehiculo.getText()) || "".equals(jCBCURPOperador.getSelectedItem())) {
            JOptionPane.showMessageDialog(null, "Favor de llenar todos los campos solicitados", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            // for (int i = 0; i < JDVehicleRegister.jtUrban.getRowCount(); i++) {
            // String a = JDVehicleRegister.jtUrban.getValueAt(i, 0).toString();
            // if (a.equals(txtUnidad.getText().toUpperCase())) {
            // contador = false;
            // break;
            // } else {
            // contador = true;
            // }
            // }

            // if (contador) {

            String ruta = "src" + barra + "logs" + barra + "vehiculos" + barra + TipoTrans + ".txt";

            JDVehicleRegister.AgregarRegistroTabla(new Object[] {
                    txtUnidad.getText().toUpperCase(),
                    txtPlacas.getText().toUpperCase(),
                    jCBVehiculo.getSelectedItem(),
                    txtDueñoVehiculo.getText().toUpperCase(),
                    jCBCURPOperador.getSelectedItem() }, jtable);
            try {
                FileWriter fw = new FileWriter(ruta);

                BufferedWriter bw = new BufferedWriter(fw);

                for (int i1 = 0; i1 < jtable.getRowCount(); i1++) {// rows
                    for (int j = 0; j < jtable.getColumnCount(); j++) {// columns
                        bw.write(jtable.getValueAt(i1, j).toString() + "_");
                    }
                    bw.newLine();
                }

                bw.close();
                fw.close();

            } catch (IOException ex) {
                Logger.getLogger(jDNewVehicleRegister.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "Registro exitoso", "Nuevo Registro", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();

            // } else {
            // JOptionPane.showMessageDialog(null, "CURP del conductor ya registrado con
            // anterioridad", "Nuevo Registro", JOptionPane.ERROR_MESSAGE);
            // }
            //
        }

    }

    public void savechanges(JTable jtable, String TipoTrans) {
        if ("".equals(txtUnidad.getText()) || "".equals(txtPlacas.getText()) || "".equals(jCBVehiculo.getSelectedItem())
                || "".equals(txtDueñoVehiculo.getText()) || "".equals(jCBCURPOperador.getSelectedItem())) {
            JOptionPane.showMessageDialog(null, "Favor de llenar todos los campos solicitados", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {

            try {
                int indice = jtable.getSelectedRow();
                String ruta = "src" + barra + "logs" + barra + "vehiculos" + barra + TipoTrans + ".txt";
                TableModel Modelo = jtable.getModel();
                Modelo.setValueAt(txtUnidad.getText().toUpperCase(), jtable.getSelectedRow(), 0);
                Modelo.setValueAt(txtPlacas.getText().toUpperCase(), jtable.getSelectedRow(), 1);
                Modelo.setValueAt(jCBVehiculo.getSelectedItem(), jtable.getSelectedRow(), 2);
                Modelo.setValueAt(txtDueñoVehiculo.getText().toUpperCase(), jtable.getSelectedRow(), 3);
                Modelo.setValueAt(jCBCURPOperador.getSelectedItem(), jtable.getSelectedRow(), 4);

                FileWriter fw = new FileWriter(ruta);

                BufferedWriter bw = new BufferedWriter(fw);

                for (int i = 0; i < jtable.getRowCount(); i++) {// rows
                    for (int j = 0; j < jtable.getColumnCount(); j++) {// columns
                        bw.write(jtable.getValueAt(i, j).toString() + "_");
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
        txtUnidad = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtPlacas = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnGuardarCambios = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtDueñoVehiculo = new javax.swing.JTextField();
        jCBVehiculo = new javax.swing.JComboBox<>();
        jCBCURPOperador = new javax.swing.JComboBox<>();
        lblEditarContraseña = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registro de Vehículos");

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel1.setText("No. Unidad:");

        txtUnidad.setEditable(false);

        jLabel2.setText("Placa:");

        jLabel3.setText("Tipo Vehículo:");

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

        jLabel4.setText("CURP Operador:");

        jLabel5.setText("Dueño Vehículo:");

        jCBVehiculo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Taxi", "Van", "Urban" }));

        jCBCURPOperador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CURM990525HOCRYR09" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                jPanel1Layout.createSequentialGroup()
                                                        .addComponent(btnGuardarCambios)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(btnCancelar)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(btnSave)
                                                        .addGap(12, 12, 12))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout
                                                .createSequentialGroup()
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jLabel1))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addComponent(txtUnidad)
                                                        .addComponent(jCBVehiculo, 0,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(33, 33, 33)
                                                                .addComponent(jLabel5))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                jPanel1Layout.createSequentialGroup()
                                                                        .addGap(103, 103, 103)
                                                                        .addComponent(jLabel2)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addComponent(txtPlacas, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                129, Short.MAX_VALUE)
                                                        .addComponent(txtDueñoVehiculo))
                                                .addContainerGap())
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addGap(21, 21, 21)
                                                .addComponent(jCBCURPOperador, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)))));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(txtUnidad, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2)
                                        .addComponent(txtPlacas, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jCBVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5)
                                        .addComponent(txtDueñoVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(jCBCURPOperador, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(46, 46, 46)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnCancelar)
                                        .addComponent(btnSave)
                                        .addComponent(btnGuardarCambios))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        lblEditarContraseña.setText("Nuevo Registro");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblEditarContraseña)
                                .addGap(210, 210, 210))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap()));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblEditarContraseña)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

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
        if (Data.getTipoTransporte().equals("Taxi")) {
            Save("Taxi", JDVehicleRegister.jtTaxi);
        } else if (Data.getTipoTransporte().equals("Van")) {
            Save("Van", JDVehicleRegister.jtVan);
        } else {
            Save("Urban", JDVehicleRegister.jtUrban);
        }

    }// GEN-LAST:event_btnSaveActionPerformed

    private void btnGuardarCambiosActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnGuardarCambiosActionPerformed
        if (Data.getTipoTransporte().equals("Taxi")) {
            savechanges(JDVehicleRegister.jtTaxi, "Taxi");
        } else if (Data.getTipoTransporte().equals("Van")) {
            savechanges(JDVehicleRegister.jtVan, "Van");
        } else {
            savechanges(JDVehicleRegister.jtUrban, "Urban");
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
            java.util.logging.Logger.getLogger(jDNewVehicleRegister.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jDNewVehicleRegister.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jDNewVehicleRegister.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jDNewVehicleRegister.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        }
        // </editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                jDNewVehicleRegister dialog = new jDNewVehicleRegister(new javax.swing.JFrame(), true);
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
    public javax.swing.JComboBox<String> jCBCURPOperador;
    public javax.swing.JComboBox<String> jCBVehiculo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JLabel lblEditarContraseña;
    public javax.swing.JTextField txtDueñoVehiculo;
    public javax.swing.JTextField txtPlacas;
    public javax.swing.JTextField txtUnidad;
    // End of variables declaration//GEN-END:variables
}
