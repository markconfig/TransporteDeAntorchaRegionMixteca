
package JDialogs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import classes.Data;

/**
 *
 * @author marcos
 */
public final class jDReporteDiario extends javax.swing.JDialog {
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

    Date fechaActual = new Date();
    SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");

    String fechaSistema = formateador.format(fechaActual);

    /**
     * Creates new form jDReporteDiario
     */
    public jDReporteDiario(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        // Agregar la fecha al componente JDChooser
        Calendar c2 = new GregorianCalendar();
        jDCFecha.setCalendar(c2);
        setLocationRelativeTo(null);
        ShowData();
    }

    public void borrarTabla(JTable tabla) {
        DefaultTableModel modeloTabla = (DefaultTableModel) tabla.getModel();
        modeloTabla.setRowCount(0);
    }

    public void ShowData() {

        // Con estos codigos obtenemos correctamente el formato de fecha
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String fecha = sdf.format(jDCFecha.getDate());
        Data.setDate(fecha);

        String ruta = "src" + barra + "logs" + barra + "Reportes" + barra + fecha + ".txt";

        File file = new File(ruta);
        if (!file.exists()) {
            borrarTabla(jtReporteDiario);
            JOptionPane.showMessageDialog(null, "No hay reportes en este dia.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            borrarTabla(jtReporteDiario);
            try {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);

                DefaultTableModel model = (DefaultTableModel) jtReporteDiario.getModel();
                Object[] lines = br.lines().toArray();

                for (int i = 0; i < lines.length; i++) {
                    String[] row = lines[i].toString().split("_");
                    model.addRow(row);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(jDReporteDiario.class.getName()).log(Level.SEVERE, null, ex);
            }
            TableModel Modelo = jtReporteDiario.getModel();
            float Precios = 0;

            for (int i = 0; i < Modelo.getRowCount(); i++) {

                String a = String.valueOf(Modelo.getValueAt(i, 6));
                float b = Float.valueOf(a);
                Precios = Precios + b;
            }
            lblTotalCaja.setText(String.valueOf("$" + Precios + "0"));
        }

    }

    public void generarCancelacion(JTable jtable) {
        int indice = jtable.getSelectedRow();
        TableModel Modelo = jtable.getModel();
        if (jtable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un registro a cancelar.", "Error",
                    JOptionPane.ERROR_MESSAGE);

        } else if (Modelo.getValueAt(indice, 8).equals("Colectivo")) {
            JOptionPane.showMessageDialog(null, "En este aparpado solo puedes cancelar viajes privados/especiales.",
                    "Error", JOptionPane.INFORMATION_MESSAGE);
        } else {
            int res = JOptionPane.showConfirmDialog(this, "¿Esta seguro de cancelar esta recervación?", "Reportes",
                    JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (res == 0) {
                // Con este metodo enviamos los datos del precio para que aparesca en la
                // pantalla
                // sendData(this.lblPrecio.getText());
                System.out.println("Usted acaba de cancelar");

                Modelo.setValueAt("Cancelado", indice, 7);

                String a = String.valueOf(Modelo.getValueAt(indice, 6));
                float b = Float.valueOf(a);
                Modelo.setValueAt(0.0, indice, 6);

                SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");

                String fechaSistema = formateador.format(jDCFecha.getDate());

                try {
                    String filePath = "src" + barra + "logs" + barra + "Reportes" + barra + fechaSistema + ".txt";
                    File ruta = new File(filePath);
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
                    // Actualizar los datos
                    ShowData();

                } catch (IOException ex) {
                    Logger.getLogger(jDticketsBuy.class.getName()).log(Level.SEVERE, null, ex);
                }

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

        jScrollPane1 = new javax.swing.JScrollPane();
        jtReporteDiario = new javax.swing.JTable();
        lblTotalCaja = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnCancelarRecervacion = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jDCFecha = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jtReporteDiario.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "No. Asiento", "Origen", "Destino", "Hora", "Tipo de transporte", "Nombre Cliente", "Precio",
                        "Status", "Tipo Servicio"
                }) {
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane1.setViewportView(jtReporteDiario);
        if (jtReporteDiario.getColumnModel().getColumnCount() > 0) {
            jtReporteDiario.getColumnModel().getColumn(0).setPreferredWidth(100);
            jtReporteDiario.getColumnModel().getColumn(3).setPreferredWidth(50);
            jtReporteDiario.getColumnModel().getColumn(4).setPreferredWidth(200);
            jtReporteDiario.getColumnModel().getColumn(5).setPreferredWidth(50);
            jtReporteDiario.getColumnModel().getColumn(7).setPreferredWidth(150);
        }

        lblTotalCaja.setText("No hay saldo en caja");

        jLabel1.setText("Total en caja:");

        btnCancelarRecervacion.setText("Cancelar Reservación");
        btnCancelarRecervacion.setEnabled(false);
        btnCancelarRecervacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarRecervacionActionPerformed(evt);
            }
        });

        jLabel2.setText("Reportes del dia:");

        jButton1.setText("Actualizar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Salir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                .createSequentialGroup()
                                                .addComponent(btnCancelarRecervacion)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        464, Short.MAX_VALUE)
                                                .addComponent(jLabel1)
                                                .addGap(18, 18, 18)
                                                .addComponent(lblTotalCaja))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jDCFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 135,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 125,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 99,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jDCFecha, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jButton1)
                                                .addComponent(jButton2)))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                .addGap(53, 53, 53)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(lblTotalCaja)
                                        .addComponent(btnCancelarRecervacion))
                                .addGap(38, 38, 38)));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarRecervacionActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCancelarRecervacionActionPerformed
        generarCancelacion(jtReporteDiario);
    }// GEN-LAST:event_btnCancelarRecervacionActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        lblTotalCaja.setText("No hay saldo en caja");
        ShowData();
    }// GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }// GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(jDReporteDiario.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jDReporteDiario.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jDReporteDiario.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jDReporteDiario.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        }
        // </editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                jDReporteDiario dialog = new jDReporteDiario(new javax.swing.JFrame(), true);
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
    public javax.swing.JButton btnCancelarRecervacion;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private com.toedter.calendar.JDateChooser jDCFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jtReporteDiario;
    private javax.swing.JLabel lblTotalCaja;
    // End of variables declaration//GEN-END:variables
}
