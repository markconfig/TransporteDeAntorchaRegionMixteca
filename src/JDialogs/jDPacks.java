package JDialogs;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

import classes.Data;
import classes.validate;

public class jDPacks extends javax.swing.JDialog {
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
    // La carpeta donde se guardara el archivo
    String Ubicacion = System.getProperty("User.dir") + barra + "Datos" + barra;

    /**
     * Creates new form jDPacks
     */
    public jDPacks(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        Calendar c2 = new GregorianCalendar();
        jDCFecha.setCalendar(c2);
        validate val = new validate();
        val.validarSoloNumeros(txtKilogramos);
        val.validarSoloNumeros(txtPreciU);
        val.validarSoloLetras(txtDestinatario);
        val.validarSoloLetras(txtOrigen);
        val.validarSoloLetras(txtRemitente);
        val.limitarCaracteres(txtDescripcion, 25);
        val.limitarCaracteres(txtKilogramos, 2);
        val.limitarCaracteres(txtPreciU, 3);
        val.limitarCaracteres(txtRemitente, 30);
        val.limitarCaracteres(txtDestinatario, 30);
        this.setResizable(false);
    }

    //// private void Eliminar() {
    //// File URL = new File(Ubicacion + txtFolio.getText() + ".txt");
    ////
    //// String ResBoton[] = {"Eliminar", "Cancelar"};
    ////
    //// if (txtFolio.getText().equals("")) {
    ////
    //// JOptionPane.showMessageDialog(this, "Indique el folio" + "a eliminar");
    ////
    //// } else {
    //// if (URL.exists()) {
    //// try {
    //// FileInputStream Borrar = new FileInputStream(URL);
    //// Borrar.close();
    //// System.gc();
    ////
    //// int Respuesta = JOptionPane.showOptionDialog(
    //// rootPane, "Estas seguro de borrar:" + txtFolio.getText(),
    //// "ELIMINAR", WIDTH, HEIGHT, null, ResBoton, null);
    ////
    //// if (Respuesta == JOptionPane.YES_OPTION) {
    ////
    //// URL.delete();
    //// JOptionPane.showMessageDialog(this, "Archivo borrado");
    ////
    //// }
    ////
    //// this.txtDescripcion.setText(null);
    //// this.txtPreciU.setText(null);
    //// this.txtKilogramos.setText(null);
    //// this.txtRemitente.setText(null);
    //// this.txtDestinatario.setText(null);
    //// this.jDCFecha.setCalendar(null);
    //// jDPacks.txtFolio.setText(null);
    //// this.lblTotal.setText(null);
    ////
    //// } catch (HeadlessException | IOException msj) {
    ////
    //// JOptionPane.showMessageDialog(this, "Error:" + msj);
    ////
    //// }
    //// }
    //// }
    //// }
    ////
    //// private void CrearArchivo() throws IOException {
    ////
    //// String nombreArchivo = txtFolio.getText() + ".txt";
    //// File Direccion = new File(Ubicacion);
    //// File CrearArchivo = new File(Ubicacion + nombreArchivo);
    ////
    //// if (jDPacks.txtFolio.getText().equals("")) {
    ////
    //// JOptionPane.showMessageDialog(this, " No hay datos");
    ////
    //// } else {
    //// try {
    //// if (CrearArchivo.exists() == true) {
    //// JOptionPane.showMessageDialog(this, "Archivo ya existe");
    //// } else {
    ////
    //// Direccion.mkdirs();
    //// try (Formatter Crear = new Formatter(Ubicacion + nombreArchivo)) {
    ////
    //// String fecha = String.valueOf(jDCFecha.getDate());
    ////
    //// Crear.format("%s\r\n%s\r\n%s\r\n%s\r\n%s\r\n%s\r\n%s\r\n%s\r\n%s\r\n%s\r\n%s\r\n",
    //// "Folio =" + txtFolio.getText(),
    //// "Origen =" + txtOrigen.getText(),
    //// "Destino =" + jCBDestino.getSelectedItem(),
    //// "Remitente =" + txtRemitente.getText(),
    //// "Destinatario =" + txtDestinatario.getText(),
    //// "Fecha =" + fecha,
    //// "Total =" + lblTotal.getText(),
    //// "Kilogramos =" + txtKilogramos.getText(),
    //// "Precio Unitario =" + txtPreciU.getText(),
    //// "Descripcion =" + txtDescripcion.getText()
    //// );
    //// }
    //// JOptionPane.showMessageDialog(this, "Archivo creado");
    ////
    //// }
    ////
    //// } catch (HeadlessException | FileNotFoundException error) {
    //// JOptionPane.showMessageDialog(this, "Error: " + error);
    //// }
    ////
    //// }
    //// }
    ////
    //// private void LimpiarCajas() {
    //// this.txtOrigen.setText("");
    ////
    //// this.txtRemitente.setText("");
    //// this.txtDestinatario.setText("");
    //// this.jDCFecha.setCalendar(null);
    //// jDPacks.txtFolio.setText("");
    //// this.lblTotal.setText("");
    //// }
    ////
    //// private void Modificar() {
    ////
    //// File URL = new File(Ubicacion + txtFolio.getText() + ".txt");
    ////
    //// if (txtFolio.getText().equals("")) {
    ////
    //// JOptionPane.showMessageDialog(this, "Indique el folio");
    ////
    //// } else {
    //// if (URL.exists() == true) {
    //// try {
    ////
    //// String fecha = String.valueOf(jDCFecha.getDate());
    ////
    //// FileWriter Escribir = new FileWriter(Ubicacion + txtFolio.getText() +
    //// ".txt");
    ////
    //// String folio = "Folio= ";
    //// String origen = "Origen= ";
    //// String destino = "Destino= ";
    //// String remitente = "Remitente= ";
    //// String destinatario = "Destinatario=";
    //// String fecha1 = "Fecha=";
    //// String importe = "Importe";
    //// String total = "Total";
    //// String kilos = "Kilos";
    //// String precio = "Precio";
    //// String descripcion = "Descripcion";
    ////
    //// PrintWriter Guardar = new PrintWriter(Escribir);
    //// Guardar.println(origen + txtOrigen.getText());
    //// Guardar.println(destino + jCBDestino.getSelectedItem());
    //// Guardar.println(remitente + txtRemitente.getText());
    //// Guardar.println(destinatario + txtDestinatario.getText());
    //// Guardar.println(fecha1 + fecha);
    //// Guardar.println(total + lblTotal.getText());
    //// Guardar.println(kilos + txtKilogramos.getText());
    //// Guardar.println(precio + txtPreciU.getText());
    //// Guardar.println(descripcion + txtDescripcion.getText());
    //// Guardar.close();
    ////
    //// JOptionPane.showMessageDialog(this, "Archivo modificado!!");
    ////
    //// } catch (Exception Bug) {
    ////
    //// JOptionPane.showMessageDialog(this, "Error al modificar los datos" +
    //// Bug.getMessage());
    ////
    //// }
    //// } else {
    ////
    //// JOptionPane.showMessageDialog(this, "Archivo no existe");
    ////
    //// }
    ////
    //// }
    //// }

    public void SaveData() {
        // Con estos codigos obtenemos correctamente el formato de fecha
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String fecha = sdf.format(jDCFecha.getDate());
        // Data.setFolio(Integer.parseInt(txtFolio.getText()));
        Data.setDate(fecha);
        Data.setOrigen(txtOrigen.getText());
        Data.setDestino(jCBDestino.getSelectedItem().toString());
        Data.setKilogramos(Float.parseFloat(txtKilogramos.getText()));
        Data.setPrecioKilogramo(Float.parseFloat(txtPreciU.getText()));
        Data.setTotal(Float.parseFloat(lblTotal.getText()));
        Data.setDescripcion(txtDescripcion.getText().toUpperCase());
        Data.setDestinatario(txtDestinatario.getText().toUpperCase());
        Data.setRemitente(txtRemitente.getText().toUpperCase());

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

        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        btnCotizar = new javax.swing.JButton();
        btngenerarOrden = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtOrigen = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtRemitente = new javax.swing.JTextField();
        txtDestinatario = new javax.swing.JTextField();
        jCBDestino = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jDCFecha = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        txtPreciU = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtKilogramos = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Paquetería ");

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel9.setText("Total: $");

        lblTotal.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblTotal.setText("0.00");

        btnCotizar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnCotizar.setText("Cotizar");
        btnCotizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCotizarActionPerformed(evt);
            }
        });

        btngenerarOrden.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btngenerarOrden.setText("Generar Orden");
        btngenerarOrden.setEnabled(false);
        btngenerarOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btngenerarOrdenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel9)
                                                .addGap(42, 42, 42)
                                                .addComponent(lblTotal))
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(btngenerarOrden, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnCotizar)
                                .addGap(51, 51, 51)));
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(btnCotizar)
                                .addGap(34, 34, 34)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel9)
                                        .addComponent(lblTotal))
                                .addGap(30, 30, 30)
                                .addComponent(btngenerarOrden)
                                .addContainerGap(55, Short.MAX_VALUE)));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        jLabel1.setText("Origen:");

        txtOrigen.setText("Acatlán de Osorio");

        jLabel2.setText("Destino:");

        jLabel3.setText("Remitente(s):");

        jLabel4.setText("Destinatario(s):");

        jCBDestino.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[] { "Tehuitzingo", "Izúcar de Matamoros", "Puebla" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtDestinatario))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout
                                                        .createSequentialGroup()
                                                        .addComponent(jLabel3)
                                                        .addPreferredGap(
                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(txtRemitente,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 134,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout
                                                        .createSequentialGroup()
                                                        .addGroup(jPanel2Layout
                                                                .createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jLabel2)
                                                                .addComponent(jLabel1))
                                                        .addPreferredGap(
                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(jPanel2Layout
                                                                .createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING,
                                                                        false)
                                                                .addComponent(jCBDestino,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(txtOrigen,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 170,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(txtOrigen, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(jCBDestino, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(22, 22, 22)
                                                .addComponent(jLabel3))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(txtRemitente)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(txtDestinatario, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        jLabel8.setText("Fecha:");

        txtPreciU.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPreciUKeyPressed(evt);
            }
        });

        jLabel12.setText("Kilogramos:");

        txtKilogramos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtKilogramosKeyReleased(evt);
            }
        });

        jLabel11.setText("Precio por Kg: ");

        jLabel13.setText("Descripcion:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel11)
                                        .addComponent(jLabel12)
                                        .addComponent(jLabel13)
                                        .addComponent(jLabel8))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout
                                                .createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(223, 223, 223))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGroup(jPanel3Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel3Layout
                                                                .createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.TRAILING,
                                                                        false)
                                                                .addComponent(txtKilogramos,
                                                                        javax.swing.GroupLayout.Alignment.LEADING,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE, 60,
                                                                        Short.MAX_VALUE)
                                                                .addComponent(txtPreciU,
                                                                        javax.swing.GroupLayout.Alignment.LEADING))
                                                        .addComponent(jDCFecha, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                143, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        Short.MAX_VALUE)))));
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jDCFecha, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel8))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(txtKilogramos, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel3Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(txtPreciU, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel11)))
                                        .addComponent(jLabel12))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel13)
                                        .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jButton1.setText("Cerrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap()));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jButton1)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 345,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPreciUKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtPreciUKeyPressed

    }// GEN-LAST:event_txtPreciUKeyPressed

    private void txtKilogramosKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtKilogramosKeyReleased

    }// GEN-LAST:event_txtKilogramosKeyReleased

    private void btnCotizarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCotizarActionPerformed
        if (/* txtFolio.getText().equals("") || */ txtOrigen.getText().equals("")
                || txtDestinatario.getText().equals("") || txtDescripcion.getText().equals("")
                || txtOrigen.getText().equals("") || txtPreciU.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Rellena los campos solicitados", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            float a = Float.parseFloat(txtKilogramos.getText());
            float b = Float.parseFloat(txtPreciU.getText());
            float c = a * b;
            lblTotal.setText(String.valueOf(c) + "0");
            btngenerarOrden.setEnabled(true);

        }
    }// GEN-LAST:event_btnCotizarActionPerformed

    private void btngenerarOrdenActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btngenerarOrdenActionPerformed
        SaveData();
        jDPacksDataBuy DatosdeVenta = new jDPacksDataBuy(null, rootPaneCheckingEnabled);
        this.dispose();
        DatosdeVenta.show();
    }// GEN-LAST:event_btngenerarOrdenActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }// GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(jDPacks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jDPacks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jDPacks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jDPacks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>
        // </editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                jDPacks dialog = new jDPacks(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCotizar;
    private javax.swing.JButton btngenerarOrden;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jCBDestino;
    private com.toedter.calendar.JDateChooser jDCFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtDestinatario;
    private javax.swing.JTextField txtKilogramos;
    private javax.swing.JTextField txtOrigen;
    private javax.swing.JTextField txtPreciU;
    private javax.swing.JTextField txtRemitente;
    // End of variables declaration//GEN-END:variables
}
