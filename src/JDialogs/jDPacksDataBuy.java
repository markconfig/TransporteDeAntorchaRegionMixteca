
package JDialogs;

import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import classes.Data;
import classes.Imprimir;
import classes.generarPDF;
import classes.validate;
import jssc.SerialPort;
import jssc.SerialPortException;

/**
 *
 * @author marcos
 */
public class jDPacksDataBuy extends javax.swing.JDialog {
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
    float c;
    generarPDF generar = new generarPDF();

    /**
     * Creates new form jDPacksDataBuy
     */
    public jDPacksDataBuy(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        validate val = new validate();
        val.limitarCaracteres(txtDescripcion, 25);
        showdata();
        setLocationRelativeTo(null);
    }
    // Este metodo va en jDDataBuypacks

    public void generateFiletoReport() {

        Date fechaActual = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        String fechaSistema = formateador.format(fechaActual);
        String ruta = "src" + barra + "logs" + barra + "Reportes" + barra + "ReportesPaquteria" + barra + fechaSistema
                + ".txt";

        File file = new File(ruta);
        // if (file.exists()) {
        // JOptionPane.showMessageDialog(this, "Lo sentimos, ya hay un usuario con el
        // mismo nombre.");
        // } else {
        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);

            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(Data.getOrigen() + "_" + Data.getDestino() + "_" + Data.getKilogramos() + "_"
                    + Data.getDescripcion() + "_" + Data.getRemitente() + "_" + Data.getDestinatario() + "_"
                    + Data.getPrecioKilogramo() + "_" + Data.getTotal());
            bw.newLine();

            bw.close();
            fw.close();
            // JOptionPane.showMessageDialog(null, "Nuevo usuario registrado correctamente",
            // "Nuevo Registro", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Archivo reporte generado: " + ruta);
        } catch (IOException ex) {
            Logger.getLogger(jDUserRegister.class.getName()).log(Level.SEVERE, null, ex);
        }

        // }
    }

    public void showdata() {
        lblRemitente.setText(Data.getRemitente());
        lblDestinatario.setText(Data.getDestinatario());
        lblOrigen.setText(Data.getOrigen());
        lblDestino.setText(Data.getDestino());
        lblKilogramo.setText(String.valueOf(Data.getKilogramos()));
        lblPrecioporkilogramo.setText(String.valueOf(Data.getPrecioKilogramo()));
        lblPrecio.setText(String.valueOf(String.valueOf(Data.getTotal())));
        txtDescripcion.setText(Data.getDescripcion());
        lblNombreEncargado.setText(Data.getNombreUsuario());

    }

    public void generarVenta() {
        if (txtpagocon.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Favor de llenar los campos solicitados");
        } else {
            if (c <= -1) {
                JOptionPane.showMessageDialog(null, "No se ha cubierto el total a cobrar", "Error",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                // TableModel Modelo = jtable.getModel();
                // jDticketsBuy.AgregarRegistroTabla(new Object[]{Data.getNoAsiento(),
                // Data.getTipoTransporte(), Data.getHora(),"Ocupado"});
                // Data.setNombreCliente(lblNombreCompleto.getText().toUpperCase());

                int res = JOptionPane.showConfirmDialog(this, "¿Esta seguro de realizar esta venta?",
                        "Venta de boletos", JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (res == 0) {
                    // Con este metodo enviamos los datos del precio para que aparesca en la
                    // pantalla
                    sendData(this.lblPrecio.getText());
                    /**
                     * Metodo para generar el PDF*
                     */
                    Date d = new Date();
                    // SimpleDateFormat s = new SimpleDateFormat("hh:mm:ss");
                    SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aaaa");
                    // String rutaImagen = "src" + barra + "Imagenes" + barra + "user1.png";
                    String rutaImagen = "src" + barra + "Imagenes" + barra + "LogoTicket.png";
                    String rutaGuardarPDF = "src" + barra + "logs" + barra + "tickets" + barra + "Paqueteria" + barra
                            + Data.getRemitente() + ".pdf";
                    generar.generarPDF("Transporte de Antorcha, Region Mixteca",
                            rutaImagen,
                            "Remitente: .............. " + Data.getRemitente() + "\n"
                                    + "Destinatario: ........... " + Data.getDestinatario() + "\n"
                                    + "Origen: ................. " + Data.getOrigen() + "\n"
                                    + "Destino: ................ " + Data.getDestino() + "\n"
                                    + "Kilogramos: ............. " + Data.getKilogramos() + "\n"
                                    + "Precio/Kg: .............. " + Data.getPrecioKilogramo() + "\n"
                                    + "Precio total: ........... " + Data.getTotal() + "\n"
                                    + "Dscripción: ............. " + Data.getDescripcion() + "\n"
                                    + "Expedido: ............... " + s.format(d) + "\n",
                            "Subtotal: .......... " + Data.getTotal() + "\n"
                                    + "IVA: .......... " + "0.00" + "\n"
                                    + "Total: .......... " + Data.getTotal(),
                            "Lo atendio: " + Data.getNombreUsuario() + "\n"
                                    + "Gracias por su preferencia"
                                    + "\n\n",
                            "PSOCIETY",
                            "________________________________________________",
                            rutaGuardarPDF);
                    generateFiletoReport();

                    this.dispose();
                    Imprimir.print(rutaGuardarPDF);
                    jDPacks a = new jDPacks(null, rootPaneCheckingEnabled);
                    a.setVisible(true);
                }
            }

        }
    }

    public void sendData(String dato) {
        SerialPort serialPort = new SerialPort("/dev/ttyACM0");

        try {
            serialPort.openPort();
            serialPort.setParams(9600, 8, 1, 0);
            Thread.sleep(3000);
            serialPort.writeBytes(dato.getBytes());
            serialPort.closePort();
        } catch (SerialPortException | InterruptedException ex) {
            Logger.getLogger(jDDataBuy.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("String wrote to port, waiting for response..");
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
        jLabel8 = new javax.swing.JLabel();
        txtpagocon = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        lblSucambio = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblNombreEncargado = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        lblOrigen = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblDestino = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblPrecio = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblDestinatario = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblRemitente = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        lblKilogramo = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        lblPrecioporkilogramo = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Paquetería");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel8.setText("Recibí:");

        txtpagocon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtpagoconKeyPressed(evt);
            }

            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtpagoconKeyReleased(evt);
            }
        });

        jLabel9.setText("El cambio es: ");

        lblSucambio.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblSucambio.setForeground(new java.awt.Color(204, 0, 0));
        lblSucambio.setText("0.00");

        jLabel10.setText("Lo atendio: ");

        lblNombreEncargado.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblNombreEncargado.setText("VendedorNombre");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblNombreEncargado)
                                        .addComponent(lblSucambio)
                                        .addComponent(txtpagocon, javax.swing.GroupLayout.PREFERRED_SIZE, 80,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel8)
                                        .addComponent(txtpagocon, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel9)
                                        .addComponent(lblSucambio))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel10)
                                        .addComponent(lblNombreEncargado))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        btnSave.setText("Realizar esta operación");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnCancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSave)
                                .addContainerGap()));
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnSave)
                                        .addComponent(btnCancelar))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        lblOrigen.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblOrigen.setForeground(new java.awt.Color(0, 0, 102));
        lblOrigen.setText("Aqui va el destino");

        jLabel5.setText("Destino: ");

        lblDestino.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblDestino.setForeground(new java.awt.Color(0, 0, 102));
        lblDestino.setText("Aqui se muestra el Des");

        jLabel4.setText("Precio Total: ");

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Transporte de Antorcha Región Mixteca");

        lblPrecio.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblPrecio.setForeground(new java.awt.Color(153, 0, 0));
        lblPrecio.setText("Precio");

        jLabel3.setText("Origen: ");

        jLabel11.setText("Destinatario: ");

        lblDestinatario.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblDestinatario.setForeground(new java.awt.Color(153, 0, 0));
        lblDestinatario.setText("Destinatario");

        jLabel7.setText("Remitente: ");

        lblRemitente.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblRemitente.setForeground(new java.awt.Color(0, 51, 0));
        lblRemitente.setEnabled(false);

        jLabel12.setText("Kilogramos: ");

        lblKilogramo.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblKilogramo.setForeground(new java.awt.Color(153, 0, 0));
        lblKilogramo.setText("KG");

        jLabel13.setText("Descripción: ");

        txtDescripcion.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        txtDescripcion.setForeground(new java.awt.Color(153, 0, 0));
        txtDescripcion.setText("Descripcion");

        lblPrecioporkilogramo.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblPrecioporkilogramo.setForeground(new java.awt.Color(153, 0, 0));
        lblPrecioporkilogramo.setText("Precio/kilo");

        jLabel6.setText("Precio/kg");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtDescripcion)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(jLabel11)
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel4Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblRemitente)
                                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                                .addGroup(jPanel4Layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(lblOrigen)
                                                                        .addComponent(lblDestinatario)
                                                                        .addComponent(lblDestino)
                                                                        .addComponent(lblKilogramo))
                                                                .addGap(0, 22, Short.MAX_VALUE))))
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addGroup(jPanel4Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jLabel7)
                                                        .addComponent(jLabel12)
                                                        .addComponent(jLabel5)
                                                        .addComponent(jLabel1)
                                                        .addComponent(jLabel6)
                                                        .addComponent(jLabel13)
                                                        .addComponent(jLabel4)
                                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                                .addGap(111, 111, 111)
                                                                .addGroup(jPanel4Layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(lblPrecioporkilogramo)
                                                                        .addComponent(lblPrecio))))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap()));
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7)
                                        .addComponent(lblRemitente, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel11)
                                        .addComponent(lblDestinatario))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(lblOrigen))
                                .addGap(21, 21, 21)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(lblDestino))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel12)
                                        .addComponent(lblKilogramo))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(lblPrecioporkilogramo))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(lblPrecio))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(24, Short.MAX_VALUE)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap()));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap()));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap()));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtpagoconKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtpagoconKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            generarVenta();
        }
    }// GEN-LAST:event_txtpagoconKeyPressed

    private void txtpagoconKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtpagoconKeyReleased
        if (txtpagocon.getText().equals("")) {
            lblSucambio.setText("$ " + "0.00");
        } else {
            float a = Float.parseFloat(txtpagocon.getText());
            float b = Float.parseFloat(lblPrecio.getText());
            c = a - b;
            System.out.println(c);
            lblSucambio.setText("$ " + Float.toString(c));
        }
    }// GEN-LAST:event_txtpagoconKeyReleased

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSaveActionPerformed
        generarVenta();
    }// GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
        jDPacks a = new jDPacks(null, rootPaneCheckingEnabled);
        a.setVisible(true);
    }// GEN-LAST:event_btnCancelarActionPerformed

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
            java.util.logging.Logger.getLogger(jDPacksDataBuy.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jDPacksDataBuy.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jDPacksDataBuy.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jDPacksDataBuy.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        }
        // </editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                jDPacksDataBuy dialog = new jDPacksDataBuy(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblDestinatario;
    private javax.swing.JLabel lblDestino;
    private javax.swing.JLabel lblKilogramo;
    private javax.swing.JLabel lblNombreEncargado;
    private javax.swing.JLabel lblOrigen;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblPrecioporkilogramo;
    private javax.swing.JTextField lblRemitente;
    private javax.swing.JLabel lblSucambio;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtpagocon;
    // End of variables declaration//GEN-END:variables
}
