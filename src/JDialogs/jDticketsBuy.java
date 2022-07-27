package JDialogs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import classes.Data;
import classes.Precios;

/**
 *
 * @author marcos
 */
public class jDticketsBuy extends javax.swing.JDialog {
    //
    // jDDataBuy NewRegs = new jDDataBuy(null, rootPaneCheckingEnabled);
    // DefaultTableModel modelo = new DefaultTableModel();
    // Object[] Registros = new Object[4];

    /**
     * Creates new form jDticketsSale
     */
    public jDticketsBuy(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        // Agregar la fecha al componente JDChooser
        Calendar c2 = new GregorianCalendar();
        jDCFecha.setCalendar(c2);
        // llamamos al Metodo con el cual realizamos la operacion de guardar datos y de
        // mostrar viajes Disponibles
        getListjComboBoxUnites(jCBUnidadesTaxi, "Taxi");
        getListjComboBoxUnites(jCBUnidadesUrban, "Urban");
        getListjComboBoxUnites(jCBUnidadesVan, "Van");
        validateFechas();
        SaveData();
    }
    // int ID;

    // public static void AgregarRegistroTabla(Object[] Registro) {
    //
    // DefaultTableModel datos = (DefaultTableModel) jtTaxi.getModel();
    // //datos.removeTableModelListener(tableUsers);
    // datos.addRow(Registro);
    // //System.getProperty("file.separator");
    // }
    /**
     * Método que devuelve el separador de archivos dependiendo el sistema
     * operativo
     *
     *
     * @return String con el separador de archivos dependiendo el sistema
     * operativo
     */
    public String getSeparador() {

        String separador = "";
                  

        separador = File.pathSeparator; // obtenemos ";" o ":"
          System.out.println("Separador antes" +separador);
        if (separador.equals(";")) { // si es Windows
            separador = "\\";
            System.out.println("Separador despues" +separador);
        }
        if (separador.equals(":")) { // Si es mac o linux
            separador = "/";
        }
        return separador;
    }

    String barra = getSeparador(); // "/";
    String taxi = "Taxi";
    String urban = "Urban";
    String van = "Van";
    // Creamos el metodo con el cual realizamos la operacion de guardar datos y de
    // mostrar viajes Disponibles

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

    private void generarVenta(JTable jtable) {
        String Tipotrans;
        if (jtable.equals(jtTaxi)) {
            System.out.println("Esto es un taxi");
            Tipotrans = "Taxi";
        } else if (jtable.equals(jtVan)) {
            System.out.println("Esto es una Van");
            Tipotrans = "Van";
        } else {
            System.out.println("Esto es una Urban");
            Tipotrans = "Urban";
        }
        Data.setNoUnidad(jCBUnidadesTaxi.getSelectedItem().toString());
        Data.setTipoTransporte(Tipotrans);
        int indice = jtable.getSelectedRow();
        TableModel Modelo = jtable.getModel();
        if (jtable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un asiento", "Error", JOptionPane.ERROR_MESSAGE);

        } else if (Modelo.getValueAt(indice, 3).equals("Ocupado")) {
            JOptionPane.showMessageDialog(null, "Asiento ya asignado");
        } else {
            // Metodo que guarda los datos ya sea el origen destino hora, etc.
            // SaveData();

            // Data.setTipoTransporte(Tipotrans/*"Taxi"*/);
            String ID = Modelo.getValueAt(indice, 0).toString();
            Data.setNoAsiento(Integer.parseInt(ID));
            // System.out.println(Data.getNoAsiento());

            jDDataBuy datosDeVenta = new jDDataBuy(null, rootPaneCheckingEnabled);
            // Modelo.setValueAt("Ocupado",Data.getNoAsiento()-1, 3);
            datosDeVenta.setVisible(true);

        }
    }

    private void getListjComboBoxHoras() {
        // Limpiamos lo que tenga en el jComboBox
        jcHora.removeAllItems();
        // Creamos un objeto de Date
        Date dateActual = new Date();
        // Damos formato al objeto date para que devuelva solo la hora del sistema
        SimpleDateFormat sdfHora = new SimpleDateFormat("HH");
        String horaSistema = sdfHora.format(dateActual);
        int h = Integer.parseInt(horaSistema) + 1;
        if (h < 5) {
            for (int j = 5; j <= 17; j++) {
                jcHora.addItem(String.valueOf(j + ":00"));
            }
        } else {
            for (int i = h; i <= 17; i++) {
                jcHora.addItem(String.valueOf(i + ":00"));
            }
        }
    }

    private void SaveData() {
        // Empieza obtencion de datos
        // Obtenemos los datos de los campos Origen, destino, hora
        Data.setOrigen(jcOrigen.getSelectedItem().toString());
        Data.setDestino(jcDestino.getSelectedItem().toString());
        System.out.println(Data.getDestino());
        if (jcHora.getSelectedIndex() != -1) {
            Data.setHora(jcHora.getSelectedItem().toString());
        }

        // Con estos codigos obtenemos correctamente el formato de fecha
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String fecha = sdf.format(jDCFecha.getDate());
        Data.setDate(fecha);
        // Termina obtencion de datos

        /**
         * Obtenemos la fecha del sistema y la convertirmos al String con el
         * formato en el que vamos a trabajar
         */
        Date fechaActual = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        String fechaSistema = formateador.format(fechaActual);
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
        String dateOnJDDataChooser = s.format(jDCFecha.getDate());
        // Guardanmos en una variable el resultado que nos da al comparar las fechas,
        // reliazados por el metodo ComparateDate
        String resultadoFecha = comparateDate(dateOnJDDataChooser, fechaSistema);

        if (jDCFecha.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Seleciona una fecha");
        } else if ("antes".equals(resultadoFecha)) { // Si la fecha es anterior a la de hoy, borra las tablas y manda el
            // mensaje de error
            borrarTabbla(jtTaxi);
            borrarTabbla(jtUrban);
            borrarTabbla(jtVan);
            jcHora.removeAllItems();
            JOptionPane.showMessageDialog(null, "No se puede vender boletos de dias anteriores al de hoy.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            if ("hoy".equals(resultadoFecha)) {// Si la fecha es la actual el combo box solo mostrara una hora despues
                // de la nuestra
                Date dateActual = new Date();
                // Damos formato al objeto date para que devuelva solo la hora del sistema
                SimpleDateFormat sdfHora = new SimpleDateFormat("HH");
                SimpleDateFormat sdfFecha = new SimpleDateFormat("dd");
                SimpleDateFormat sdfMes = new SimpleDateFormat("MM");
                String horaSistema = sdfHora.format(dateActual);
                String diaSistema = sdfFecha.format(dateActual);
                String MesSistema = sdfMes.format(dateActual);
                int h = Integer.parseInt(horaSistema) + 1;
                int d = Integer.parseInt(diaSistema);
                int m = Integer.parseInt(MesSistema);
                System.out.println(d);
                if (h >= 17) {
                    Calendar c2 = new GregorianCalendar();
                    // c2.set(d + 1, d + 4);
                    // c2.set(d + 1, d -6);
                    c2.set(m, d + 1);

                    jDCFecha.setCalendar(c2);

                    jcHora.removeAllItems();
                    for (int i = 5; i <= 17; i++) {
                        jcHora.addItem(String.valueOf(i + ":00"));
                    }
                } else {
                    getListjComboBoxHoras(); // Asi no mostrara horas anteriores para vender
                }
            } else if ("despues".equals(resultadoFecha)) {
                // Limpiamos lo que tenga en el jComboBox
                jcHora.removeAllItems();
                for (int i = 5; i <= 17; i++) {
                    jcHora.addItem(String.valueOf(i + ":00"));
                }
            }
            // Estos metodos hacen que se borre el contenido de la tabla
            borrarTabbla(jtTaxi);
            borrarTabbla(jtUrban);
            borrarTabbla(jtVan);

            //// //Obtenemos los datos de los campos Origen, destino, hora
            //// Data.setOrigen(jcOrigen.getSelectedItem().toString());
            //// Data.setDestino(jcDestino.getSelectedItem().toString());
            //// System.out.println(Data.getDestino());
            //// Data.setHora(jcHora.getSelectedItem().toString());
            ////
            //// //Con estos codigos obtenemos correctamente el formato de fecha
            //// SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            //// String fecha = sdf.format(jDCFecha.getDate());
            //// Data.setDate(fecha);
            // System.out.println(fecha);
            // System.out.println(Data.getOrigen() + "," + Data.getDestino() + "," +
            //// Data.getHora() + "," + Data.getDate());
            // jDTicketBuyNext siguiente = new jDTicketBuyNext(null,
            //// rootPaneCheckingEnabled);
            // siguiente.setVisible(true);
            // usamos los metodos de para buscar y crear los archivos que contienen los
            //// datos de unidades y hora de salida
            // Asi como su disponiblidad
            String fe = Data.getDate();
            String ho = Data.getHora();
            Buscar(fe, ho, taxi, jtTaxi);
            Buscar(fe, ho, urban, jtUrban);
            Buscar(fe, ho, van, jtVan);
            // System.out.print(Data.getDate());
            // System.out.println(Data.getHora());

        }
    }

    // public void mostarUnidades(){
    //
    // String filePath =
    // "src"+barra+"logs"+barra+"Conductores"+barra+"Conductores.txt";
    // File URL = new File(filePath);
    // if (URL.exists()) {
    // try {
    // FileInputStream A = new FileInputStream(URL);
    // Properties Abrir = new Properties();
    // Abrir.load(A);
    // //Mostrar los datos obtenidos
    // FileReader fr = new FileReader(URL);
    // BufferedReader br = new BufferedReader(fr);
    //
    // DefaultTableModel model = (DefaultTableModel) jtable.getModel();
    // Object[] lines = br.lines().toArray();
    //
    // for (int i = 0; i < lines.length; i++) {
    // String[] row = lines[i].toString().split("_");
    // model.addRow(row);
    // }
    // //System.out.println("Archivo leido");
    // } catch (Exception e) {
    // JOptionPane.showMessageDialog(this, "Erros al abrir: " + e.getMessage());
    // }
    // }
    // }
    private void borrarTabbla(JTable tabla) {
        DefaultTableModel modeloTabla = (DefaultTableModel) tabla.getModel();
        modeloTabla.setRowCount(0);
    }

    private void Buscar(String fecha, String hora, String tipoTra, JTable jtable) {

        String filePath = "src" + barra + "logs" + barra + tipoTra + barra + fecha + "." + hora + ".txt";
        File URL = new File(filePath);

        if (fecha.equals("")) {
            JOptionPane.showMessageDialog(this, "Error,no hay ninguna fecha asocieda");
        } else {
            if (URL.exists()) {
                try {
                    FileInputStream A = new FileInputStream(URL);
                    Properties Abrir = new Properties();
                    Abrir.load(A);
                    // Mostrar los datos obtenidos
                    FileReader fr = new FileReader(URL);
                    BufferedReader br = new BufferedReader(fr);

                    DefaultTableModel model = (DefaultTableModel) jtable.getModel();
                    Object[] lines = br.lines().toArray();

                    for (int i = 0; i < lines.length; i++) {
                        String[] row = lines[i].toString().split("_");
                        model.addRow(row);
                    }
                    // System.out.println("Archivo leido");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Erros al abrir: " + e.getMessage());
                }

            } else {
                if (URL.exists() == false) {
                    CrearArchivo(tipoTra);
                }

                if (URL.exists()) {
                    Buscar(fecha, hora, tipoTra, jtable);
                }

            }

        }
    }

    private void CrearArchivo(String trans) {
        String filePath ="src" + barra + "logs" + barra + trans + barra;
        
        String nombreArchivo = Data.getDate() + "." + Data.getHora() + ".txt";
        // File Direccion = new File(filePath);
        File CrearArchivo = new File(filePath + nombreArchivo);

        if (Data.getDate().equals("")) {
            JOptionPane.showMessageDialog(this, "No hay datos");
        } else {
            try {
                if (CrearArchivo.exists() == true) {
                    JOptionPane.showMessageDialog(this, "Archivo ya existe");
                } else {
                    // Direccion.mkdirs();
                    if (trans.equals(taxi)) {
                        Formatter Crear = new Formatter(filePath + nombreArchivo);
                        Crear.format("%s\r\n%s\r\n%s\r\n%s\r\n",
                                "1_" + trans + "_" + Data.getHora() + "_Disponible",
                                "2_" + trans + "_" + Data.getHora() + "_Disponible",
                                "3_" + trans + "_" + Data.getHora() + "_Disponible",
                                "4_" + trans + "_" + Data.getHora() + "_Disponible");
                        Crear.close();
                        // JOptionPane.showMessageDialog(this, "Archivo creado");
                        System.out.println("Archivo Creado");
                    } else if (trans.equals(van)) {
                        Formatter Crear = new Formatter(filePath + nombreArchivo);
                        Crear.format("%s\r\n%s\r\n%s\r\n%s\r\n%s\r\n%s\r\n",
                                "1_" + trans + "_" + Data.getHora() + "_Disponible",
                                "2_" + trans + "_" + Data.getHora() + "_Disponible",
                                "3_" + trans + "_" + Data.getHora() + "_Disponible",
                                "4_" + trans + "_" + Data.getHora() + "_Disponible",
                                "5_" + trans + "_" + Data.getHora() + "_Disponible",
                                "6_" + trans + "_" + Data.getHora() + "_Disponible");
                        Crear.close();
                        // JOptionPane.showMessageDialog(this, "Archivo creado");
                        System.out.println("Archivo Creado");
                    } else if (trans.equals(urban)) {
                        Formatter Crear = new Formatter(filePath + nombreArchivo);
                        Crear.format(
                                "%s\r\n%s\r\n%s\r\n%s\r\n%s\r\n%s\r\n%s\r\n%s\r\n%s\r\n%s\r\n%s\r\n%s\r\n%s\r\n%s\r\n",
                                "1_" + trans + "_" + Data.getHora() + "_Disponible",
                                "2_" + trans + "_" + Data.getHora() + "_Disponible",
                                "3_" + trans + "_" + Data.getHora() + "_Disponible",
                                "4_" + trans + "_" + Data.getHora() + "_Disponible",
                                "5_" + trans + "_" + Data.getHora() + "_Disponible",
                                "6_" + trans + "_" + Data.getHora() + "_Disponible",
                                "7_" + trans + "_" + Data.getHora() + "_Disponible",
                                "8_" + trans + "_" + Data.getHora() + "_Disponible",
                                "9_" + trans + "_" + Data.getHora() + "_Disponible",
                                "10_" + trans + "_" + Data.getHora() + "_Disponible",
                                "11_" + trans + "_" + Data.getHora() + "_Disponible",
                                "12_" + trans + "_" + Data.getHora() + "_Disponible",
                                "13_" + trans + "_" + Data.getHora() + "_Disponible",
                                "14_" + trans + "_" + Data.getHora() + "_Disponible");
                        Crear.close();
                        // JOptionPane.showMessageDialog(this, "Archivo creado");
                        System.out.println("Archivo creado");
                    }

                }
            } catch (Exception error) {
                JOptionPane.showMessageDialog(this, "Error:" + error);
                System.out.println("Error:" + filePath);
                System.out.println();
            }
        }
    }

    public void generarCancelacion(JTable jtable) {
        String Tipotrans;
        if (jtable.equals(jtTaxi)) {
            System.out.println("Esto es un taxi");
            Tipotrans = "Taxi";
        } else if (jtable.equals(jtVan)) {
            System.out.println("Esto es una Van");
            Tipotrans = "Van";
        } else {
            System.out.println("Esto es una Urban");
            Tipotrans = "Urban";
        }

        Data.setTipoTransporte(Tipotrans);
        // para obtener el precio del destino
        Data.setCostoBoleto(Precios.LeerarchivoPrecio(jcDestino.getSelectedItem().toString()));

        int indice = jtable.getSelectedRow();
        TableModel Modelo = jtable.getModel();
        if (jtable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un asiento", "Error", JOptionPane.ERROR_MESSAGE);

        } else if (Modelo.getValueAt(indice, 3).equals("Disponible")) {
            JOptionPane.showMessageDialog(null, "Asiento no recervado");
        } else {
            int res = JOptionPane.showConfirmDialog(this, "¿Esta seguro de cancelar esta recervación?",
                    "Venta de boletos", JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (res == 0) {
                // Con este metodo enviamos los datos del precio para que aparesca en la
                // pantalla
                // sendData(this.lblPrecio.getText());
                System.out.println("Usted acaba de cancelar");
                String ID = Modelo.getValueAt(indice, 0).toString();
                Data.setNoAsiento(Integer.parseInt(ID));
                // System.out.println(Data.getNoAsiento());
                Modelo.setValueAt("Disponible", Data.getNoAsiento() - 1, 3);

                /**
                 * //Creamos un modelo para la tabla de reportes diarios
                 * TableModel ModellogDay = jtReporteDiario.getModel(); String
                 * Noasi; String Origen; String destino; String Ttrans; for (int
                 * i = 0; i < 10; i++) {
                 *
                 * Noasi = ModellogDay.getValueAt(i,i).toString(); Origen =
                 * ModellogDay.getValueAt(i,i+1).toString(); destino =
                 * ModellogDay.getValueAt(i,i+2).toString(); Ttrans =
                 * ModellogDay.getValueAt(i,i+3).toString(); if
                 * (Data.getNoAsiento()==Integer.parseInt(Noasi)&&Data.getOrigen()==Origen
                 * &&
                 * Data.getDestino()==destino&&Data.getTipoTransporte()==Ttrans)
                 * { ModellogDay.setValueAt("Cancelado", i, 6); } }
                 *
                 */
                try {
                    String filePath = "src" + barra + "logs" + barra + Data.getTipoTransporte() + barra + Data.getDate()
                            + "." + Data.getHora() + ".txt";
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

                } catch (IOException ex) {
                    Logger.getLogger(jDticketsBuy.class.getName()).log(Level.SEVERE, null, ex);
                }
                generateFiletoReport();
            }
        }
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

    public void validateFechas() {
        /**
         * Obtenemos la fecha del sistema y la convertirmos al String con el
         * formato en el que vamos a trabajar
         */
        Date fechaActual = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        String fechaSistema = formateador.format(fechaActual);
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
        String dateOnJDDataChooser = s.format(jDCFecha.getDate());
        // Guardanmos en una variable el resultado que nos da al comparar las fechas,
        // reliazados por el metodo ComparateDate
        String resultadoFecha = comparateDate(dateOnJDDataChooser, fechaSistema);

        if (jDCFecha.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Seleciona una fecha");
        } else if ("antes".equals(resultadoFecha)) { // Si la fecha es anterior a la de hoy, borra las tablas y manda el
            // mensaje de error
            borrarTabbla(jtTaxi);
            borrarTabbla(jtUrban);
            borrarTabbla(jtVan);
            jcHora.removeAllItems();
            JOptionPane.showMessageDialog(null, "No se puede vender boletos de dias anteriores al de hoy.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            if ("hoy".equals(resultadoFecha)) {// Si la fecha es la actual el combo box solo mostrara una hora despues
                // de la nuestra
                Date dateActual = new Date();
                // Damos formato al objeto date para que devuelva solo la hora del sistema
                SimpleDateFormat sdfHora = new SimpleDateFormat("HH");
                SimpleDateFormat sdfFecha = new SimpleDateFormat("dd");
                SimpleDateFormat sdfMes = new SimpleDateFormat("MM");
                String horaSistema = sdfHora.format(dateActual);
                String diaSistema = sdfFecha.format(dateActual);
                String MesSistema = sdfMes.format(dateActual);
                int h = Integer.parseInt(horaSistema) + 1;
                int d = Integer.parseInt(diaSistema);
                int m = Integer.parseInt(MesSistema);
                System.out.println(d);
                if (h >= 17) {
                    Calendar c2 = new GregorianCalendar();
                    // c2.set(d + 1, d + 4);
                    // c2.set(d + 1, d -6);
                    c2.set(m, d + 1);

                    jDCFecha.setCalendar(c2);

                    jcHora.removeAllItems();
                    for (int i = 5; i <= 17; i++) {
                        jcHora.addItem(String.valueOf(i + ":00"));
                    }
                } else {
                    getListjComboBoxHoras(); // Asi no mostrara horas anteriores para vender
                }
            } else if ("despues".equals(resultadoFecha)) {
                // Limpiamos lo que tenga en el jComboBox
                jcHora.removeAllItems();
                for (int i = 5; i <= 17; i++) {
                    jcHora.addItem(String.valueOf(i + ":00"));
                }
            }
        }
        if (jcHora.getSelectedIndex() != -1) {
            Data.setHora(jcHora.getSelectedItem().toString());
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

            bw.write(Data.getNoAsiento() + "_" + Data.getOrigen() + "_" + Data.getDestino() + "_" + Data.getHora() + "_"
                    + Data.getTipoTransporte() + "_" + "*********" + "_" + "-" + Data.getCostoBoleto() + "_"
                    + "Cancelado" + "_" + "Colectivo");
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
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jcOrigen = new javax.swing.JComboBox<>();
        btnMostrarDisponibles = new javax.swing.JButton();
        jDCFecha = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jcDestino = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jcHora = new javax.swing.JComboBox<>();
        btnCerrar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtTaxi = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        btnGenerarVentaTaxi = new javax.swing.JButton();
        btncancelarRecervacion = new javax.swing.JButton();
        jCBUnidadesTaxi = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jtUrban = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        btnGenerarVentaUrban = new javax.swing.JButton();
        btncancelarReservacion3 = new javax.swing.JButton();
        jCBUnidadesUrban = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        btnGenerarVentaVan = new javax.swing.JButton();
        btncancelarReservacion4 = new javax.swing.JButton();
        jCBUnidadesVan = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jtVan = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Venta de boletos");
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        jLabel4.setText("Destino:");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        jLabel3.setText("Origen:");

        jcOrigen.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jcOrigen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Acatlán" }));

        btnMostrarDisponibles.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnMostrarDisponibles.setText("Mostrar disponibles");
        btnMostrarDisponibles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarDisponiblesActionPerformed(evt);
            }
        });

        jDCFecha.setDateFormatString("dd/MM/yyyy");
        jDCFecha.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        jLabel1.setText("Fecha:");

        jcDestino.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jcDestino.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[] { "Puebla", "Tehuitzingo", "Izucar de Matamoros" }));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        jLabel2.setText("Hora:");

        jcHora.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jcHora.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5:00", "6:00", "7:00", "8:00", "9:00",
                "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00" }));

        btnCerrar.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout
                                                                .createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING,
                                                                        false)
                                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                        .addComponent(jLabel3)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                        .addComponent(jcOrigen, 0,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE))
                                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                        .addGap(1, 1, 1)
                                                                        .addComponent(jLabel4)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(jcDestino,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel2)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jcHora,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel1)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jDCFecha,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 135,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(btnCerrar)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnMostrarDisponibles)
                                                .addGap(28, 28, 28)))));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jDCFecha, javax.swing.GroupLayout.Alignment.TRAILING,
                                                javax.swing.GroupLayout.PREFERRED_SIZE, 36,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel1)
                                                .addComponent(jLabel2)
                                                .addComponent(jcHora, javax.swing.GroupLayout.PREFERRED_SIZE, 38,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(52, 52, 52)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jcOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(57, 57, 57)
                                                .addComponent(jLabel4))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                jPanel1Layout.createSequentialGroup()
                                                        .addGap(40, 40, 40)
                                                        .addComponent(jcDestino, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnMostrarDisponibles)
                                        .addComponent(btnCerrar))
                                .addContainerGap()));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jtTaxi.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jtTaxi.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "No. Asiento", "Tipo de transporte", "Hora", ""
                }) {
            boolean[] canEdit = new boolean[] {
                    false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jtTaxi.setGridColor(new java.awt.Color(255, 255, 255));
        jtTaxi.setRowHeight(30);
        jtTaxi.setSelectionBackground(new java.awt.Color(204, 102, 0));
        jtTaxi.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jScrollPane4.setViewportView(jtTaxi);

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        btnGenerarVentaTaxi.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnGenerarVentaTaxi.setText("Generar venta");
        btnGenerarVentaTaxi.setToolTipText("Generar Venta o recervación");
        btnGenerarVentaTaxi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarVentaTaxiActionPerformed(evt);
            }
        });

        btncancelarRecervacion.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btncancelarRecervacion.setText("Cancelar reservación");
        btncancelarRecervacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelarRecervacionActionPerformed(evt);
            }
        });

        jCBUnidadesTaxi.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[] { "-No asignado-", "U1", "U2", "U3", "U4", "U5", "U6", "U7" }));

        jLabel5.setText("Unidad Asignada: ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel4Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(btncancelarRecervacion, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnGenerarVentaTaxi, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jCBUnidadesTaxi, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5))
                                .addGap(46, 46, 46)));
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCBUnidadesTaxi, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnGenerarVentaTaxi)
                                .addGap(18, 18, 18)
                                .addComponent(btncancelarRecervacion)
                                .addContainerGap(145, Short.MAX_VALUE)));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 511,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 294,
                                                Short.MAX_VALUE)
                                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jTabbedPane1.addTab("Taxi", jPanel3);

        jtUrban.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jtUrban.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "No. Asiento", "Tipo de transporte", "Hora", ""
                }) {
            boolean[] canEdit = new boolean[] {
                    false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jtUrban.setGridColor(new java.awt.Color(255, 255, 255));
        jtUrban.setRowHeight(30);
        jtUrban.setSelectionBackground(new java.awt.Color(204, 102, 0));
        jtUrban.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jScrollPane5.setViewportView(jtUrban);

        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        btnGenerarVentaUrban.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnGenerarVentaUrban.setText("Generar venta");
        btnGenerarVentaUrban.setToolTipText("Generar Venta o recervación");
        btnGenerarVentaUrban.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarVentaUrbanActionPerformed(evt);
            }
        });

        btncancelarReservacion3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btncancelarReservacion3.setText("Cancelar reservación");
        btncancelarReservacion3.setToolTipText("Cancelar venta o reservación");
        btncancelarReservacion3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelarReservacion3ActionPerformed(evt);
            }
        });

        jCBUnidadesUrban.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[] { "-No asignado-", "U1", "U2", "U3", "U4", "U5", "U6", "U7" }));

        jLabel6.setText("Unidad Asignada: ");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
                jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel9Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jPanel9Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addComponent(btnGenerarVentaUrban,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btncancelarReservacion3)))
                                        .addGroup(jPanel9Layout.createSequentialGroup()
                                                .addGap(45, 45, 45)
                                                .addGroup(jPanel9Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel6)
                                                        .addComponent(jCBUnidadesUrban,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel9Layout.setVerticalGroup(
                jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCBUnidadesUrban, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnGenerarVentaUrban)
                                .addGap(18, 18, 18)
                                .addComponent(btncancelarReservacion3)
                                .addContainerGap(142, Short.MAX_VALUE)));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 511,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap()));
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0,
                                                Short.MAX_VALUE))
                                .addContainerGap()));

        jTabbedPane1.addTab("Urban", jPanel5);

        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        btnGenerarVentaVan.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnGenerarVentaVan.setText("Generar venta");
        btnGenerarVentaVan.setToolTipText("Generar Venta o recervación");
        btnGenerarVentaVan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarVentaVanActionPerformed(evt);
            }
        });

        btncancelarReservacion4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btncancelarReservacion4.setText("Cancelar reservación");
        btncancelarReservacion4.setToolTipText("Cancelar venta o reservación");
        btncancelarReservacion4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelarReservacion4ActionPerformed(evt);
            }
        });

        jCBUnidadesVan.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[] { "-No asignado-", "U1", "U2", "U3", "U4", "U5", "U6", "U7" }));

        jLabel7.setText("Unidad Asignada: ");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
                jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel10Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout
                                                .createSequentialGroup()
                                                .addGroup(jPanel10Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addComponent(btnGenerarVentaVan,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btncancelarReservacion4))
                                                .addContainerGap())
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout
                                                .createSequentialGroup()
                                                .addGroup(jPanel10Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel10Layout.createSequentialGroup()
                                                                .addComponent(jLabel7)
                                                                .addGap(2, 2, 2))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                jPanel10Layout.createSequentialGroup()
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                5,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jCBUnidadesVan,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(47, 47, 47)))));
        jPanel10Layout.setVerticalGroup(
                jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCBUnidadesVan, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnGenerarVentaVan)
                                .addGap(18, 18, 18)
                                .addComponent(btncancelarReservacion4)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jtVan.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jtVan.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "No. Asiento", "Tipo de transporte", "Hora", ""
                }) {
            boolean[] canEdit = new boolean[] {
                    false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jtVan.setGridColor(new java.awt.Color(255, 255, 255));
        jtVan.setRowHeight(30);
        jtVan.setSelectionBackground(new java.awt.Color(204, 102, 0));
        jtVan.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jScrollPane8.setViewportView(jtVan);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 511,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(347, 347, 347)));
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.LEADING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                jPanel6Layout.createSequentialGroup()
                                                        .addGap(0, 0, Short.MAX_VALUE)
                                                        .addComponent(jScrollPane8,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 294,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(42, 42, 42)));

        jTabbedPane1.addTab("Van", jPanel6);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 764, Short.MAX_VALUE));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 342,
                                javax.swing.GroupLayout.PREFERRED_SIZE));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMostrarDisponiblesActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnMostrarDisponiblesActionPerformed
        SaveData();
    }// GEN-LAST:event_btnMostrarDisponiblesActionPerformed

    private void btnGenerarVentaTaxiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnGenerarVentaTaxiActionPerformed
        generarVenta(jtTaxi);
    }// GEN-LAST:event_btnGenerarVentaTaxiActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }// GEN-LAST:event_btnCerrarActionPerformed

    private void btnGenerarVentaUrbanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnGenerarVentaUrbanActionPerformed
        generarVenta(jtUrban);
    }// GEN-LAST:event_btnGenerarVentaUrbanActionPerformed

    private void btnGenerarVentaVanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnGenerarVentaVanActionPerformed
        generarVenta(jtVan);
    }// GEN-LAST:event_btnGenerarVentaVanActionPerformed

    private void btncancelarRecervacionActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btncancelarRecervacionActionPerformed
        generarCancelacion(jtTaxi);
    }// GEN-LAST:event_btncancelarRecervacionActionPerformed

    private void btncancelarReservacion3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btncancelarReservacion3ActionPerformed
        generarCancelacion(jtUrban);
    }// GEN-LAST:event_btncancelarReservacion3ActionPerformed

    private void btncancelarReservacion4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btncancelarReservacion4ActionPerformed
        generarCancelacion(jtVan);
    }// GEN-LAST:event_btncancelarReservacion4ActionPerformed

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
            java.util.logging.Logger.getLogger(jDticketsBuy.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jDticketsBuy.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jDticketsBuy.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jDticketsBuy.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        }
        // </editor-fold>
        // </editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                jDticketsBuy dialog = new jDticketsBuy(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnGenerarVentaTaxi;
    private javax.swing.JButton btnGenerarVentaUrban;
    private javax.swing.JButton btnGenerarVentaVan;
    private javax.swing.JButton btnMostrarDisponibles;
    private javax.swing.JButton btncancelarRecervacion;
    private javax.swing.JButton btncancelarReservacion3;
    private javax.swing.JButton btncancelarReservacion4;
    private javax.swing.JComboBox<String> jCBUnidadesTaxi;
    private javax.swing.JComboBox<String> jCBUnidadesUrban;
    private javax.swing.JComboBox<String> jCBUnidadesVan;
    private com.toedter.calendar.JDateChooser jDCFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JComboBox<String> jcDestino;
    private javax.swing.JComboBox<String> jcHora;
    private javax.swing.JComboBox<String> jcOrigen;
    public static javax.swing.JTable jtTaxi;
    public static javax.swing.JTable jtUrban;
    public static javax.swing.JTable jtVan;
    // End of variables declaration//GEN-END:variables
}
