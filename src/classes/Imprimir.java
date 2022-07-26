package classes;

import java.awt.Desktop;
import java.io.File;

/**
 *
 * @author marcos
 */
public class Imprimir {
    // public static void print(String rutaDoc) {
    // PrinterJob job = PrinterJob.getPrinterJob();
    // job.printDialog();
    // String impresora = job.getPrintService().getName();
    //
    // //ESTE ES TU CÓDIGO
    // java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
    // java.io.File fichero = new java.io.File(rutaDoc);
    // if (desktop.isSupported(Desktop.Action.PRINT)) {
    // try {
    // try {
    // Process pr = Runtime.getRuntime().exec("Rundll32 printui.dll,PrintUIEntry /y
    // /n \"" + impresora + "\"");
    // } catch (Exception ex) {
    // System.out.println("Ha ocurrido un error al ejecutar el comando. Error: " +
    // ex);
    // }
    // desktop.print(fichero);
    // } catch (Exception e) {
    // System.out.print("El sistema no permite imprimir usando la clase Desktop");
    // e.printStackTrace();
    // }
    // } else {
    // System.out.print("El sistema no permite imprimir usando la clase Desktop");
    // }
    // }

    public static void print(String rutaDoc) {
        try {

            File file = new File(rutaDoc);
            Desktop.getDesktop().open(file);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

}
