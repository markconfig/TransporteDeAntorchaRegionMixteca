
package JDialogs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import classes.Data;

/**
 *
 * @author marcos
 */
public class JDVehicleRegister extends javax.swing.JDialog {
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
    jDNewVehicleRegister NewRegs = new jDNewVehicleRegister(null, rootPaneCheckingEnabled);
    DefaultTableModel modelo = new DefaultTableModel();
    Object[] Registros = new Object[6];
    int ID = 00;

    /**
     * Creates new form JDVehicleRegister
     */
    public JDVehicleRegister(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        MostrarDatos(jtTaxi, "Taxi");
        MostrarDatos(jtVan, "Van");
        MostrarDatos(jtUrban, "Urban");

    }

    public static void AgregarRegistroTabla(Object[] Registro, JTable jtable) {

        DefaultTableModel datos = (DefaultTableModel) jtable.getModel();
        // datos.removeTableModelListener(tableUsers);
        datos.addRow(Registro);
    }

    public void MostrarDatos(JTable jtable, String tipoTrans) {
        String Ruta = "src" + barra + "logs" + barra + "vehiculos" + barra + tipoTrans + ".txt";
        File file = new File(Ruta);

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            DefaultTableModel model = (DefaultTableModel) jtable.getModel();
            Object[] lines = br.lines().toArray();

            for (int i = 0; i < lines.length; i++) {
                String[] row = lines[i].toString().split("_");
                model.addRow(row);
            }
            String count = lines[lines.length - 1].toString();
            StringTokenizer token = new StringTokenizer(count);
            ID = Integer.parseInt(token.nextToken("_"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JDVehicleRegister.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void nuevoVehiculo(String TipoTrans) {
        jDNewVehicleRegister Newregister = new jDNewVehicleRegister(null, rootPaneCheckingEnabled);
        ID = 0;
        // ID = ID + 01;
        Data.setTipoTransporte(TipoTrans);
        if (TipoTrans.equals("Taxi")) {
            Newregister.jCBVehiculo.setSelectedIndex(0);
            int indice = jtTaxi.getRowCount() - 1;
            ID = Integer.parseInt(jtTaxi.getValueAt(indice, 0).toString());
            ID = ID + 1;

        } else if (TipoTrans.equals("Van")) {
            Newregister.jCBVehiculo.setSelectedIndex(1);
            int indice = jtVan.getRowCount() - 1;
            ID = Integer.parseInt(jtVan.getValueAt(indice, 0).toString());
            ID = ID + 1;
        } else {
            Newregister.jCBVehiculo.setSelectedIndex(2);
            int indice = jtUrban.getRowCount() - 1;
            ID = Integer.parseInt(jtUrban.getValueAt(indice, 0).toString());
            ID = ID + 1;
        }
        Newregister.jCBVehiculo.enable(false);
        Newregister.txtUnidad.setText(Integer.toString(ID));
        Newregister.show();
    }

    public void editVehiculo(JTable jtable, int ValorJComboBox, String tipotrans) {
        int indice = jtable.getSelectedRow();
        if (jtable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un conductor a editar", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Data.setTipoTransporte(tipotrans);
            TableModel Modelo = jtable.getModel();
            String noUnidad = Modelo.getValueAt(indice, 0).toString();
            String noplaca = Modelo.getValueAt(indice, 1).toString();
            String tipoVehiculo = Modelo.getValueAt(indice, 2).toString();
            String Dueño = Modelo.getValueAt(indice, 3).toString();
            String CURPOperador = Modelo.getValueAt(indice, 4).toString();
            NewRegs.lblEditarContraseña.setText("Editar Vehículo");
            NewRegs.jCBVehiculo.setSelectedIndex(ValorJComboBox);
            NewRegs.jCBVehiculo.enable(false);
            NewRegs.txtUnidad.setText(noUnidad);
            NewRegs.txtPlacas.setText(noplaca);
            // NewRegs.txt.setText(apellido);
            NewRegs.txtDueñoVehiculo.setText(Dueño);
            NewRegs.btnSave.setEnabled(false);
            NewRegs.btnGuardarCambios.setEnabled(true);

            NewRegs.pack();
            NewRegs.setLocationRelativeTo(null);
            NewRegs.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            NewRegs.show();
        }
    }

    public void deleteVehiculo(JTable jtable, String tipoTrans) {
        int indice = jtable.getSelectedRow();
        if (jtable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un vehículo a eliminar", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {

            int res = JOptionPane.showConfirmDialog(null, "¿Usted está seguro de eliminar este vehículo?", "Alerta",
                    JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (res == 0) {
                DefaultTableModel m = (DefaultTableModel) jtable.getModel();
                m.removeRow(jtable.getSelectedRow());

                // Guardar el contenido de la tabloas tras haber borrado un usuario
                String ruta = "src" + barra + "logs" + barra + "vehiculos" + barra + tipoTrans + ".txt";
                try {
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

                } catch (IOException ex) {
                    Logger.getLogger(jDConductorRegister.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btnNewTaxi = new javax.swing.JButton();
        btnEditTaxi = new javax.swing.JButton();
        btnDeleteTaxi = new javax.swing.JButton();
        btnExit2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtTaxi = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        btnNewVan = new javax.swing.JButton();
        btnEditVan = new javax.swing.JButton();
        btnDeleteVan = new javax.swing.JButton();
        btnExit3 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtVan = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnNewUrban = new javax.swing.JButton();
        btnEditUrban = new javax.swing.JButton();
        btnDeleteUrban = new javax.swing.JButton();
        btnExit1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtUrban = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registro de Vehículos");

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        btnNewTaxi.setText("Nuevo");
        btnNewTaxi.setToolTipText("Agregar un nuevo vehículo");
        btnNewTaxi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewTaxiActionPerformed(evt);
            }
        });

        btnEditTaxi.setText("Editar");
        btnEditTaxi.setToolTipText("Editar un vehículo");
        btnEditTaxi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditTaxiActionPerformed(evt);
            }
        });

        btnDeleteTaxi.setText("Eliminar");
        btnDeleteTaxi.setToolTipText("Eliminar vehículo");
        btnDeleteTaxi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteTaxiActionPerformed(evt);
            }
        });

        btnExit2.setText("Salir");
        btnExit2.setToolTipText("Salir del Registro de vehículos");
        btnExit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExit2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnNewTaxi, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditTaxi)
                                .addGap(18, 18, 18)
                                .addComponent(btnDeleteTaxi)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnExit2)
                                .addContainerGap()));
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnNewTaxi)
                                        .addComponent(btnEditTaxi)
                                        .addComponent(btnDeleteTaxi)
                                        .addComponent(btnExit2))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jtTaxi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jtTaxi.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "No. Unidad", "No. Placa", "Tipo Vehiculo", "Dueño Vehículo", "CURP Operador"
                }) {
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jtTaxi.setRowHeight(30);
        jScrollPane2.setViewportView(jtTaxi);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 679,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap()));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap()));

        jTabbedPane1.addTab("Taxi", jPanel2);

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        btnNewVan.setText("Nuevo");
        btnNewVan.setToolTipText("Agregar un nuevo vehículo");
        btnNewVan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewVanActionPerformed(evt);
            }
        });

        btnEditVan.setText("Editar");
        btnEditVan.setToolTipText("Editar un vehículo");
        btnEditVan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditVanActionPerformed(evt);
            }
        });

        btnDeleteVan.setText("Eliminar");
        btnDeleteVan.setToolTipText("Eliminar vehículo");
        btnDeleteVan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteVanActionPerformed(evt);
            }
        });

        btnExit3.setText("Salir");
        btnExit3.setToolTipText("Salir del Registro de vehículos");
        btnExit3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExit3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnNewVan, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditVan)
                                .addGap(18, 18, 18)
                                .addComponent(btnDeleteVan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnExit3)
                                .addContainerGap()));
        jPanel7Layout.setVerticalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnNewVan)
                                        .addComponent(btnEditVan)
                                        .addComponent(btnDeleteVan)
                                        .addComponent(btnExit3))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jtVan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jtVan.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "No. Unidad", "No. Placa", "Tipo Vehiculo", "Dueño Vehículo", "CURP Operador"
                }) {
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jtVan.setRowHeight(30);
        jScrollPane3.setViewportView(jtVan);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 679,
                                                Short.MAX_VALUE)
                                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap()));
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap()));

        jTabbedPane1.addTab("Van", jPanel4);

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        btnNewUrban.setText("Nuevo");
        btnNewUrban.setToolTipText("Agregar un nuevo vehículo");
        btnNewUrban.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewUrbanActionPerformed(evt);
            }
        });

        btnEditUrban.setText("Editar");
        btnEditUrban.setToolTipText("Editar un vehículo");
        btnEditUrban.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditUrbanActionPerformed(evt);
            }
        });

        btnDeleteUrban.setText("Eliminar");
        btnDeleteUrban.setToolTipText("Eliminar vehículo");
        btnDeleteUrban.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteUrbanActionPerformed(evt);
            }
        });

        btnExit1.setText("Salir");
        btnExit1.setToolTipText("Salir del Registro de vehículos");
        btnExit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExit1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnNewUrban, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditUrban)
                                .addGap(18, 18, 18)
                                .addComponent(btnDeleteUrban)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnExit1)
                                .addContainerGap()));
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnNewUrban)
                                        .addComponent(btnEditUrban)
                                        .addComponent(btnDeleteUrban)
                                        .addComponent(btnExit1))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jtUrban.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jtUrban.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "No. Unidad", "No. Placa", "Tipo Vehiculo", "Dueño Vehículo", "CURP Operador"
                }) {
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jtUrban.setRowHeight(30);
        jScrollPane1.setViewportView(jtUrban);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 679,
                                                Short.MAX_VALUE)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap()));
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap()));

        jTabbedPane1.addTab("Urban", jPanel5);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 51, 0));
        jLabel1.setText("Registro de Vehículos");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane1)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(201, 201, 201)
                                .addComponent(jLabel1)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 435,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(46, Short.MAX_VALUE)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewUrbanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnNewUrbanActionPerformed
        nuevoVehiculo("Urban");
    }// GEN-LAST:event_btnNewUrbanActionPerformed

    private void btnEditUrbanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnEditUrbanActionPerformed
        editVehiculo(jtUrban, 2, "Urban");
    }// GEN-LAST:event_btnEditUrbanActionPerformed

    private void btnDeleteUrbanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDeleteUrbanActionPerformed
        deleteVehiculo(jtUrban, "Urban");
    }// GEN-LAST:event_btnDeleteUrbanActionPerformed

    private void btnExit1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnExit1ActionPerformed
        this.dispose();
    }// GEN-LAST:event_btnExit1ActionPerformed

    private void btnNewTaxiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnNewTaxiActionPerformed
        nuevoVehiculo("Taxi");
    }// GEN-LAST:event_btnNewTaxiActionPerformed

    private void btnEditTaxiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnEditTaxiActionPerformed
        editVehiculo(jtTaxi, 0, "Taxi");
    }// GEN-LAST:event_btnEditTaxiActionPerformed

    private void btnDeleteTaxiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDeleteTaxiActionPerformed
        deleteVehiculo(jtTaxi, "Taxi");
    }// GEN-LAST:event_btnDeleteTaxiActionPerformed

    private void btnExit2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnExit2ActionPerformed
        this.dispose();
    }// GEN-LAST:event_btnExit2ActionPerformed

    private void btnNewVanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnNewVanActionPerformed
        nuevoVehiculo("Van");
    }// GEN-LAST:event_btnNewVanActionPerformed

    private void btnEditVanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnEditVanActionPerformed
        editVehiculo(jtVan, 1, "Van");
    }// GEN-LAST:event_btnEditVanActionPerformed

    private void btnDeleteVanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDeleteVanActionPerformed
        deleteVehiculo(jtVan, "Van");
    }// GEN-LAST:event_btnDeleteVanActionPerformed

    private void btnExit3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnExit3ActionPerformed
        this.dispose();
    }// GEN-LAST:event_btnExit3ActionPerformed

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
            java.util.logging.Logger.getLogger(JDVehicleRegister.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDVehicleRegister.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDVehicleRegister.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDVehicleRegister.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        }
        // </editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDVehicleRegister dialog = new JDVehicleRegister(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnDeleteTaxi;
    private javax.swing.JButton btnDeleteUrban;
    private javax.swing.JButton btnDeleteVan;
    private javax.swing.JButton btnEditTaxi;
    private javax.swing.JButton btnEditUrban;
    private javax.swing.JButton btnEditVan;
    private javax.swing.JButton btnExit1;
    private javax.swing.JButton btnExit2;
    private javax.swing.JButton btnExit3;
    private javax.swing.JButton btnNewTaxi;
    private javax.swing.JButton btnNewUrban;
    private javax.swing.JButton btnNewVan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    public static javax.swing.JTable jtTaxi;
    public static javax.swing.JTable jtUrban;
    public static javax.swing.JTable jtVan;
    // End of variables declaration//GEN-END:variables
}
