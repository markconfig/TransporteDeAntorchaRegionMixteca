
package JDialogs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import classes.Data;
import classes.validate;

/**
 *
 * @author marcos
 */
public class jDPrivateTravels extends javax.swing.JDialog {
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
     * Creates new form jDPrivateTravels
     */
    public jDPrivateTravels(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        Calendar c2 = new GregorianCalendar();
        jDCFecha.setCalendar(c2);
        getListjComboBoxHoras();
        getListValidatedjComboBoxHoras();
        classes.validate validacion = new validate();
        validacion.limitarCaracteres(txtCliente, 50);
        validacion.limitarCaracteres(txtDestino, 30);
        validacion.limitarCaracteres(txtDestino, 30);
        validacion.limitarCaracteres(txtPrecio, 5);
        validacion.validarSoloNumeros(txtPrecio);

    }

    public void DesCajas() {
        jCBHora.setEnabled(false);
        // jDCFecha.setEnabled(false);
        txtOrigen.setEnabled(false);
        txtDestino.setEnabled(false);
        txtCliente.setEnabled(false);
        jcbNoAsientoOcupar.setEnabled(false);
        btnGenerarVenta.setEnabled(false);
        jCBUnidadAsignada.setEnabled(false);
        txtPrecio.setEnabled(false);
    }

    public void HabCajas() {
        jCBHora.setEnabled(true);
        // jDCFecha.setEnabled(true);
        txtOrigen.setEnabled(true);
        txtDestino.setEnabled(true);
        txtCliente.setEnabled(true);
        jcbNoAsientoOcupar.setEnabled(true);
        btnGenerarVenta.setEnabled(true);
        jCBUnidadAsignada.setEnabled(true);
        txtPrecio.setEnabled(true);
    }

    private void getListjComboBoxHoras() {
        jCBHora.removeAllItems();
        for (int i = 0; i <= 23; i++) {
            jCBHora.addItem(String.valueOf(i + ":00"));
        }
    }

    private void getListjCBAsientos(int leng) {
        // Limpiamos lo que tenga en el jComboBox
        jcbNoAsientoOcupar.removeAllItems();
        // Hacemos un ciclo para agregar los asientos a paratir de los paramtros dados
        jcbNoAsientoOcupar.addItem("-No definido-");
        for (int i = 1; i <= leng; i++) {
            jcbNoAsientoOcupar.addItem(String.valueOf(i));
        }
    }

    private void saveData() {
        Data.setTipoTransporte(jCBTipoTransporte.getSelectedItem().toString());
        Data.setHora(jCBHora.getSelectedItem().toString());
        // Con estos codigos obtenemos correctamente el formato de fecha
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String fecha = sdf.format(jDCFecha.getDate());
        Data.setDate(fecha);
        Data.setCostoBoleto(Float.parseFloat(txtPrecio.getText()));
        Data.setOrigen(txtOrigen.getText());
        Data.setDestino(txtDestino.getText());
        Data.setNombreCliente(txtCliente.getText());
        Data.setNoUnidad(jCBUnidadAsignada.getSelectedItem().toString());
        Data.setNoPersonas(jcbNoAsientoOcupar.getSelectedItem().toString());
    }

    private void getListjComboBoxUnites(JComboBox jcombobox, String TipoTrans) {
        // Limpiamos lo que tenga en el jComboBox
        jcombobox.removeAllItems();
        String ruta = "src" + barra + "logs" + barra + "vehiculos" + barra + TipoTrans + ".txt";

        FileReader fr = null;

        try {
            String lineaActual;
            Scanner sc = new Scanner(new File(ruta));
            File f = new File(ruta);

            fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            try {
                jcombobox.addItem(String.valueOf("-No definido-"));
                while ((lineaActual = br.readLine()) != null) {
                    StringTokenizer token = new StringTokenizer(lineaActual);
                    String unidad = token.nextToken("_");
                    jcombobox.addItem(String.valueOf(unidad));
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

    private String comparateDate(String fecha1, String fechaActual) {
        String resultado = "";
        try {
            /**
             * Obtenemos las fechas enviadas en el formato a comparar
             */
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaDate1 = formateador.parse(fecha1);
            Date fechaDate2 = formateador.parse(fechaActual);

            if (fechaDate1.before(fechaDate2)) {
                resultado = "antes";
            } else if (fechaDate1.equals(fechaDate2)) {
                resultado = "hoy";
            } else {
                resultado = "despues";
            }

        } catch (ParseException e) {
            System.out.println("Se Produjo un Error!!!  " + e.getMessage());
        }
        return resultado;
    }

    private void getListValidatedjComboBoxHoras() {
        Date fechaActual = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        String fechaSistema = formateador.format(fechaActual);
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
        String dateOnJDDataChooser = s.format(jDCFecha.getDate());
        // Guardanmos en una variable el resultado que nos da al comparar las fechas,
        // reliazados por el metodo ComparateDate
        String resultadoFecha = comparateDate(dateOnJDDataChooser, fechaSistema);

        // Limpiamos lo que tenga en el jComboBox
        jCBHora.removeAllItems();
        // Creamos un objeto de Date
        Date dateActual = new Date();
        // Damos formato al objeto date para que devuelva solo la hora del sistema
        SimpleDateFormat sdfHora = new SimpleDateFormat("HH");
        String horaSistema = sdfHora.format(dateActual);
        int h = Integer.parseInt(horaSistema) + 1;
        if (resultadoFecha.equals("hoy")) {
            for (int i = h; i <= 23; i++) {
                jCBHora.addItem(String.valueOf(i + ":00"));
            }
        } else if (resultadoFecha.equals("despues")) {
            getListjComboBoxHoras();
        } else if (resultadoFecha.equals("antes")) {
            jCBHora.removeAllItems();
            DesCajas();
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
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtOrigen = new javax.swing.JTextField();
        txtDestino = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jCBHora = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        jCBUnidadAsignada = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jcbNoAsientoOcupar = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jCBTipoTransporte = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jDCFecha = new com.toedter.calendar.JDateChooser();
        jPanel5 = new javax.swing.JPanel();
        btnGenerarVenta = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Viajes Privados");

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 51, 0));
        jLabel1.setText("Registro de Viajes Privados/Especiales");

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        txtOrigen.setEnabled(false);

        txtDestino.setEnabled(false);

        jLabel2.setText("Hora:");

        jCBHora.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        jLabel4.setText("Destino:");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        jLabel5.setText("Origen:");

        jLabel9.setText("Precio:");

        txtPrecio.setEnabled(false);

        jCBUnidadAsignada.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-No asignado-" }));
        jCBUnidadAsignada.setEnabled(false);

        jLabel10.setText("Unidad Asignada");

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
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addGap(17, 17, 17)
                                                                .addComponent(jLabel5)
                                                                .addGap(85, 85, 85)
                                                                .addComponent(jLabel4))
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                                .addGap(36, 36, 36)
                                                                                .addComponent(jLabel2))
                                                                        .addComponent(jCBHora,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                108,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(txtOrigen,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                108,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(36, 36, 36)
                                                                .addComponent(txtDestino,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 128,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel10)
                                                        .addComponent(jCBUnidadAsignada,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel2Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                jPanel2Layout.createSequentialGroup()
                                                                        .addComponent(jLabel9)
                                                                        .addGap(58, 58, 58))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                jPanel2Layout.createSequentialGroup()
                                                                        .addComponent(txtPrecio,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                70,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(45, 45, 45)))))));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCBHora, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel4))
                                .addGap(9, 9, 9)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtOrigen, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtDestino, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jCBUnidadAsignada, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        jLabel6.setText("Nombre Cliente:");

        txtCliente.setEnabled(false);

        jLabel7.setText("Numero de asientos a ocupar:");

        jcbNoAsientoOcupar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-No definido-" }));
        jcbNoAsientoOcupar.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(42, 42, 42)
                                                .addComponent(jLabel6)
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGroup(jPanel3Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtCliente,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 187,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel7))
                                                .addGap(0, 0, Short.MAX_VALUE))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jcbNoAsientoOcupar, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)));
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(jcbNoAsientoOcupar, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jCBTipoTransporte.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "-Selecciona uno-", "Taxi", "Van", "Urban" }));
        jCBTipoTransporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBTipoTransporteActionPerformed(evt);
            }
        });

        jLabel8.setText("Tipo de transporte: ");

        jLabel3.setText("Fecha:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jDCFecha, javax.swing.GroupLayout.Alignment.TRAILING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addGroup(jPanel4Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addComponent(jCBTipoTransporte, 0,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addGap(62, 62, 62)));
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jDCFecha, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCBTipoTransporte, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(68, 68, 68)));

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        btnGenerarVenta.setText("Genenrar Venta");
        btnGenerarVenta.setEnabled(false);
        btnGenerarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarVentaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addContainerGap(40, Short.MAX_VALUE)
                                .addComponent(btnGenerarVenta)
                                .addGap(34, 34, 34)));
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnGenerarVenta)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

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
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout
                                                .createSequentialGroup()
                                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jPanel3,
                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                jPanel1Layout.createSequentialGroup()
                                                        .addGap(0, 0, Short.MAX_VALUE)
                                                        .addComponent(jLabel1)
                                                        .addGap(58, 58, 58)
                                                        .addComponent(jButton1)))
                                .addContainerGap()));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1)
                                        .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap()));

        jMenu1.setText("Archivo");

        jMenuItem2.setText("Cancelar venta");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem1.setText("Cerrar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCBTipoTransporteActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jCBTipoTransporteActionPerformed
        if (jCBTipoTransporte.getSelectedItem().equals("-Selecciona uno-")) {
            DesCajas();
        } else if (jCBTipoTransporte.getSelectedItem().equals("Taxi")) {

            getListjComboBoxUnites(jCBUnidadAsignada, "Taxi");
            HabCajas();
            getListjCBAsientos(4);
            getListValidatedjComboBoxHoras();
        } else if (jCBTipoTransporte.getSelectedItem().equals("Van")) {

            getListjComboBoxUnites(jCBUnidadAsignada, "Van");
            HabCajas();

            getListjCBAsientos(6);

            getListValidatedjComboBoxHoras();
        } else {

            getListjComboBoxUnites(jCBUnidadAsignada, "Urban");
            HabCajas();
            getListjCBAsientos(17);

            getListValidatedjComboBoxHoras();
        }
    }// GEN-LAST:event_jCBTipoTransporteActionPerformed

    private void btnGenerarVentaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnGenerarVentaActionPerformed
        Date fechaActual = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        String fechaSistema = formateador.format(fechaActual);
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
        String dateOnJDDataChooser = s.format(jDCFecha.getDate());
        // Guardanmos en una variable el resultado que nos da al comparar las fechas,
        // reliazados por el metodo ComparateDate
        String resultadoFecha = comparateDate(dateOnJDDataChooser, fechaSistema);

        if (txtCliente.getText().equals("") || txtDestino.getText().equals("") || txtOrigen.getText().equals("")
                || txtPrecio.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Rellena los campos solicitados", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (resultadoFecha.equals("antes")) {
            JOptionPane.showMessageDialog(null, "No se puede vender boletos de dias anteriores al de hoy.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            saveData();
            this.dispose();
            jDPrivateDataBuy datoviajesPrivados = new jDPrivateDataBuy(null, rootPaneCheckingEnabled);
            datoviajesPrivados.show();

        }

    }// GEN-LAST:event_btnGenerarVentaActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem1ActionPerformed
        this.dispose();
    }// GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem2ActionPerformed
        jDReporteDiario reportediario = new jDReporteDiario(null, rootPaneCheckingEnabled);
        reportediario.btnCancelarRecervacion.setEnabled(true);
        reportediario.show();
        this.dispose();
    }// GEN-LAST:event_jMenuItem2ActionPerformed

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
            java.util.logging.Logger.getLogger(jDPrivateTravels.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jDPrivateTravels.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jDPrivateTravels.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jDPrivateTravels.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        }
        // </editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                jDPrivateTravels dialog = new jDPrivateTravels(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnGenerarVenta;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jCBHora;
    private javax.swing.JComboBox<String> jCBTipoTransporte;
    private javax.swing.JComboBox<String> jCBUnidadAsignada;
    private com.toedter.calendar.JDateChooser jDCFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JComboBox<String> jcbNoAsientoOcupar;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtDestino;
    private javax.swing.JTextField txtOrigen;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}
