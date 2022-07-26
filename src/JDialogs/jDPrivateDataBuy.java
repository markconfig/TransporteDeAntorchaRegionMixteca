
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
public class jDPrivateDataBuy extends javax.swing.JDialog {
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
    generarPDF generar = new generarPDF();
    float c;

    /**
     * Creates new form jDPrivateDataBuy
     */
    public jDPrivateDataBuy(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        classes.validate validacion = new validate();
        validacion.limitarCaracteres(txtpagocon, 5);
        validacion.validarSoloNumeros(txtpagocon);
        showData();
    }

    private void showData() {
        lblNombreCompleto.setText(Data.getNombreCliente().toUpperCase());
        lblUnidad.setText(Data.getNoUnidad());
        lblDestino.setText(Data.getDestino());
        lblOrigen.setText(Data.getOrigen());
        lblNoAsiento.setText(Data.getNoPersonas());
        lblTipodeTransporte.setText(Data.getTipoTransporte());
        lblNombreEncargado.setText(Data.getNombreUsuario());
        lblPrecio.setText(String.valueOf(Data.getCostoBoleto()));
        float cincuentaporcent = Data.getCostoBoleto() / 2;
        lblAnticipo.setText(String.valueOf(cincuentaporcent) + "0");
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

    public void generarVenta(String tipotrans) {
        if (lblNombreCompleto.getText().equals("") || txtpagocon.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Favor de llenar los campos solicitados");
        } else {
            if (c <= -1) {
                JOptionPane.showMessageDialog(null, "No se ha cubierto el total a cobrar", "Error",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                // TableModel Modelo = jtable.getModel();
                // jDticketsBuy.AgregarRegistroTabla(new Object[]{Data.getNoAsiento(),
                // Data.getTipoTransporte(), Data.getHora(),"Ocupado"});
                Data.setNombreCliente(lblNombreCompleto.getText().toUpperCase());

                int res = JOptionPane.showConfirmDialog(this,
                        "¿Esta seguro de realizar esta venta a: " + Data.getNombreCliente(), "Venta de boletos",
                        JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (res == 0) {
                    // Con este metodo enviamos los datos del precio para que aparesca en la
                    // pantalla
                    sendData(this.lblAnticipo.getText());
                    /**
                     * Metodo para generar el PDF*
                     */
                    Date d = new Date();
                    // SimpleDateFormat s = new SimpleDateFormat("hh:mm:ss");
                    SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aaaa");
                    // String rutaImagen = "src" + barra + "Imagenes" + barra + "user1.png";
                    String rutaImagen = "src" + barra + "Imagenes" + barra + "LogoTicket.png";
                    String rutaGuardarPDF = "src" + barra + "logs" + barra + "tickets" + barra + "ViajesPrivados"
                            + barra + Data.getDate() + "-" + Data.getHora() + "-" + Data.getNoAsiento() + ".pdf";
                    generar.generarPDF("Transporte de Antorcha, Region Mixteca",
                            rutaImagen,
                            "Nombre Del pasajero: .............. " + Data.getNombreCliente() + "\n"
                                    + "Origen: ........................... " + Data.getOrigen() + "\n"
                                    + "Destino: .......................... " + Data.getDestino() + "\n"
                                    + "Hora de Salida: ................... " + Data.getHora() + "\n"
                                    + "Fecha de Salida: .................. " + Data.getDate() + "\n"
                                    + "Tipo de transporte: ............... " + Data.getTipoTransporte() + "\n"
                                    + "No. Asientos: ..................... " + Data.getNoPersonas() + "\n"
                                    + "Unidad Asignada: .................. " + Data.getNoUnidad() + "\n"
                                    + "Expedido: ......................... " + s.format(d) + "\n",
                            "Subtotal: .......... " + Data.getCostoBoleto() + "\n"
                                    + "IVA: .......... " + "0.00" + "\n"
                                    + "Total: .......... " + Data.getCostoBoleto() + "\n"
                                    + "Anticipo del 50% .......... " + Data.getCostoBoleto() / 2,
                            "VALIDO PARA FECHA Y HOARA INDICADA UNICAMENTE\n"
                                    + "Lo atendio: " + Data.getNombreUsuario() + "\n"
                                    + "Gracias por su preferencia"
                                    + "\n\n",
                            "PSOCIETY",
                            "________________________________________________",
                            rutaGuardarPDF);
                    generateFiletoReport();
                    this.dispose();
                    Imprimir.print(rutaGuardarPDF);
                    jDPrivateTravels a = new jDPrivateTravels(null, rootPaneCheckingEnabled);
                    a.show();

                }
            }

        }
    }

    public void generateFiletoReport() {

        Date fechaActual = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");

        String fechaSistema = formateador.format(fechaActual);
        String ruta = "src" + barra + "logs" + barra + "Reportes" + barra + fechaSistema + ".txt";

        File file = new File(ruta);
        // if (file.exists()) {
        // JOptionPane.showMessageDialog(this, "Lo sentimos, ya hay un usuario con el
        // mismo nombre.");
        // } else {
        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);

            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(Data.getNoPersonas() + "_" + Data.getOrigen() + "_" + Data.getDestino() + "_" + Data.getHora()
                    + "_" + Data.getTipoTransporte() + "_" + Data.getNombreCliente() + "_" + Data.getCostoBoleto() + "_"
                    + "Vendido" + "_" + "Privado");
            bw.newLine();

            bw.close();
            fw.close();
            // JOptionPane.showMessageDialog(null, "Nuevo usuario registrado correctamente",
            // "Nuevo Registro", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Archivo reporte generado" + ruta);
        } catch (IOException ex) {
            Logger.getLogger(jDUserRegister.class.getName()).log(Level.SEVERE, null, ex);
        }

        // }
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
        jLabel6 = new javax.swing.JLabel();
        lblAnticipo = new javax.swing.JLabel();
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
        lblNoAsiento = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblTipodeTransporte = new javax.swing.JLabel();
        lblUnidad = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblNombreCompleto = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Viajes Privados");

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

        jLabel6.setText("Cantidad a cobrar: $");

        lblAnticipo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblAnticipo.setForeground(new java.awt.Color(153, 51, 0));
        lblAnticipo.setText("Anticipo");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel9)
                                                        .addComponent(jLabel8)
                                                        .addComponent(jLabel10))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel2Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblNombreEncargado)
                                                        .addComponent(lblSucambio)
                                                        .addComponent(txtpagocon,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 80,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblAnticipo)))
                                .addContainerGap(89, Short.MAX_VALUE)));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(lblAnticipo))
                                .addGap(18, 18, 18)
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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39,
                                        Short.MAX_VALUE)
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
        lblOrigen.setText("Aqui se muestra el origen");

        jLabel5.setText("Destino: ");

        lblDestino.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblDestino.setForeground(new java.awt.Color(0, 0, 102));
        lblDestino.setText("Aqui se muestra el Destino");

        jLabel4.setText("Precio: ");

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Transporte de Antorcha Región Mixteca");

        lblPrecio.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblPrecio.setForeground(new java.awt.Color(153, 0, 0));
        lblPrecio.setText("Aqui se muestra Precio");

        jLabel3.setText("Origen: ");

        jLabel11.setText("No. Asientos a ocupar: ");

        lblNoAsiento.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblNoAsiento.setForeground(new java.awt.Color(153, 0, 0));
        lblNoAsiento.setText("Asi a ocupar");

        jLabel12.setText("Tipo de transporte: ");

        lblTipodeTransporte.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblTipodeTransporte.setForeground(new java.awt.Color(153, 0, 0));
        lblTipodeTransporte.setText("TipodeTransporte");

        lblUnidad.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUnidad.setForeground(new java.awt.Color(0, 102, 0));
        lblUnidad.setText("Unidad");

        jLabel7.setText("Nombre Cliente:");

        jLabel13.setText("Unidad Asignada:");

        lblNombreCompleto.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblNombreCompleto.setForeground(new java.awt.Color(0, 51, 0));
        lblNombreCompleto.setEnabled(false);

        jLabel2.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText("**Nota solo se combrara el 50% de anticipo");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addGroup(jPanel4Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel12)
                                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                                .addComponent(jLabel13)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(lblUnidad))
                                                        .addComponent(jLabel3)
                                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                                .addGroup(jPanel4Layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel5)
                                                                        .addComponent(jLabel4))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel4Layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(lblPrecio)
                                                                        .addComponent(lblDestino)
                                                                        .addComponent(lblOrigen)))
                                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                                .addComponent(jLabel7)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(lblNombreCompleto,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 156,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addGroup(jPanel4Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                                .addComponent(jLabel11)
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel4Layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(lblTipodeTransporte)
                                                                        .addComponent(lblNoAsiento)))
                                                        .addComponent(jLabel1)
                                                        .addComponent(jLabel2))
                                                .addGap(0, 0, Short.MAX_VALUE)))));
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7)
                                        .addComponent(lblNombreCompleto, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel11)
                                        .addComponent(lblNoAsiento))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel12)
                                        .addComponent(lblTipodeTransporte))
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
                                        .addComponent(jLabel4)
                                        .addComponent(lblPrecio))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblUnidad)
                                        .addComponent(jLabel13))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap()));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtpagoconKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtpagoconKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (Data.getTipoTransporte().equals("Taxi")) {
                generarVenta("Taxi");

            } else if (Data.getTipoTransporte().equals("Van")) {
                generarVenta("Van");
            } else {
                generarVenta("Urban");
            }
        }
    }// GEN-LAST:event_txtpagoconKeyPressed

    private void txtpagoconKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtpagoconKeyReleased
        if (txtpagocon.getText().equals("")) {
            lblSucambio.setText("$ " + "0.00");
        } else {
            float a = Float.parseFloat(txtpagocon.getText());
            float b = Float.parseFloat(lblAnticipo.getText());
            c = a - b;
            System.out.println(c);
            lblSucambio.setText("$ " + Float.toString(c));
        }
    }// GEN-LAST:event_txtpagoconKeyReleased

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSaveActionPerformed
        if (Data.getTipoTransporte().equals("Taxi")) {
            generarVenta("Taxi");
        } else if (Data.getTipoTransporte().equals("Van")) {
            generarVenta("Van");
        } else {
            generarVenta("Urban");
        }

    }// GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
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
            java.util.logging.Logger.getLogger(jDPrivateDataBuy.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jDPrivateDataBuy.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jDPrivateDataBuy.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jDPrivateDataBuy.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        }
        // </editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                jDPrivateDataBuy dialog = new jDPrivateDataBuy(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JLabel lblAnticipo;
    private javax.swing.JLabel lblDestino;
    private javax.swing.JLabel lblNoAsiento;
    private javax.swing.JTextField lblNombreCompleto;
    private javax.swing.JLabel lblNombreEncargado;
    private javax.swing.JLabel lblOrigen;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblSucambio;
    private javax.swing.JLabel lblTipodeTransporte;
    private javax.swing.JLabel lblUnidad;
    private javax.swing.JTextField txtpagocon;
    // End of variables declaration//GEN-END:variables
}
