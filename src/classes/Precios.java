package classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marcos
 */
public class Precios {

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

    private static float Tarifa;

    public static float LeerarchivoPrecio(String Destino) {
        FileReader fr = null;
        try {
            String ruta = "src" +  getSeparador() + "logs" +  getSeparador() + "Precios" +  getSeparador() + "precios.txt";
            String lineaActual;
            Scanner sc = new Scanner(new File(ruta));
            File f = new File(ruta);

            fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            try {
                while ((lineaActual = br.readLine()) != null) {
                    StringTokenizer token = new StringTokenizer(lineaActual);
                    String a = token.nextToken(",");
                    String b = token.nextToken(",");
                    String c = token.nextToken(",");
                    if (Destino.equals(b)) {
                        Tarifa = Float.valueOf(c);
                    }

                }
            } catch (IOException ex) {
                Logger.getLogger(Precios.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Precios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(Precios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return Tarifa;
    }
}
