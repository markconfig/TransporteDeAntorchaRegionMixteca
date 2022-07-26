package classes;

import javax.swing.JOptionPane;

/**
 *
 * @author marcos
 */
public class security {
    // login log = new login();
    ;

    JDialogs.jDLogin log = new JDialogs.jDLogin(null, true);
    String res;

    public void ValidarUsuario(String usuarios[], String user, String Pass, int intentos) {

        boolean encontrado = false;

        try {

            for (int i = 0; i < usuarios.length; i++) {

                if (usuarios[i].equalsIgnoreCase(user) && usuarios[i + 1].equals(Pass)) {
                    res = "Bienvenido " + user;
                    encontrado = true;
                    JOptionPane.showMessageDialog(null, res, "Inicio De Sesión", JOptionPane.INFORMATION_MESSAGE);
                    intentos = 0;
                    log.setIntentos(intentos);
                    log.setEstado(true);
                    break;
                    // frames.jfMain a = new frames.jfMain();

                    // log.dispose();
                }
            } // Fin del For
        } catch (Exception e) {
        }

        if (encontrado == false) {
            res = "Clave y/o usuario erroneos, van: " + intentos + "Fallidos";
            JOptionPane.showMessageDialog(null, res, "Inicio De Sesión", JOptionPane.ERROR_MESSAGE);
            if (intentos > 2) {
                JOptionPane.showMessageDialog(null, "Tres Intentos Fallidos, esto se cerrara", "Inicio De Sesión",
                        JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }

        }

    }
}
