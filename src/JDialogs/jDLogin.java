
package JDialogs;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import classes.Data;

/**
 *
 * @author marcos
 */
public class jDLogin extends javax.swing.JDialog {
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

    jDticketsBuy buy = new jDticketsBuy(null, rootPaneCheckingEnabled);// Instancia del JDialog correspondiente proceso
                                                                       // de venta
    private static Scanner sc;
    private static int intentos;
    private static String user;
    private static String pass;
    private static boolean estado = false;

    String barra = getSeparador();
    String ruta = "src" + barra + "Users" + barra + "Users.txt";
    frames.jfMain prin = new frames.jfMain();

    /**
     * Creates new form jDLogin
     */
    public jDLogin(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
    }

    public static void setIntentos(int intentos) {
        jDLogin.intentos = intentos;
    }

    public static void setEstado(boolean estado) {
        jDLogin.estado = estado;
    }

    public void ProcesoValidacion() {
        if (("".equals(txtUser.getText()) || "".equals(txtPassword.getText()))) {
            JOptionPane.showMessageDialog(null, "Introduce tu usuario y/o contraseña", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            FileReader fr = null;
            String res;
            boolean encontrado = false;
            try {
                String lineaActual;
                sc = new Scanner(new File(ruta));
                File f = new File(ruta);

                fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                user = txtUser.getText();
                pass = txtPassword.getText();

                try {
                    while ((lineaActual = br.readLine()) != null) {
                        StringTokenizer token = new StringTokenizer(lineaActual);
                        String ID = token.nextToken(",");
                        String Usuario = token.nextToken(",");
                        String Contra = token.nextToken(",");
                        if (txtUser.getText().equalsIgnoreCase(Usuario) && txtPassword.getText().equals(Contra)) {

                            Data.setNombreUsuario(user);
                            res = "Bienvenido " + user;
                            encontrado = true;
                            JOptionPane.showMessageDialog(null, res, "Inicio De Sesión",
                                    JOptionPane.INFORMATION_MESSAGE);
                            setIntentos(-1);
                            if (txtUser.getText().equals("administrador")) {
                                prin.jmiRegisterUser.setEnabled(true);
                                prin.jmiEditPrices.setEnabled(true);
                                prin.jMOthers.setEnabled(true);

                            }

                            prin.setVisible(true);
                            prin.jmiLogIn.setEnabled(false);
                            // prin.jmiRegisterUser.setEnabled(true);
                            prin.jmiLogOut.setEnabled(true);
                            prin.jMbuyTicket.setEnabled(true);
                            prin.jMbuyTicket.setEnabled(true);
                            prin.jMReport.setEnabled(true);
                            prin.jMpack.setEnabled(true);
                            // prin.jMOthers.setEnabled(true);

                            setEstado(false);
                            // Muestra el cuadro de dialogo para empexar la venta de boletos

                            this.dispose();
                            buy.show();

                        } // Fin del if

                    }
                    intentos++;
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
            if (encontrado == false) {
                res = "Clave y/o usuario erroneos, van: " + intentos + "Fallidos";
                JOptionPane.showMessageDialog(null, res, "Inicio De Sesión", JOptionPane.ERROR_MESSAGE);
                txtPassword.setText("");
                txtUser.setText("");
                txtUser.requestFocus();
                if (intentos > 2) {
                    JOptionPane.showMessageDialog(null, "Tres Intentos fallidos, esto se cerrará", "Inicio De Sesión",
                            JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
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
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        btnLogIn = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Iniciar Sesión");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setText("Contraseña: ");

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/status_online.png"))); // NOI18N
        jLabel1.setText("Usuario: ");

        txtUser.setToolTipText("Ingrese su nombre de usuario");

        btnLogIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/accept.png"))); // NOI18N
        btnLogIn.setText("Iniciar");
        btnLogIn.setToolTipText("Iniciar sesión");
        btnLogIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogInActionPerformed(evt);
            }
        });

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cross.png"))); // NOI18N
        btnCancel.setText("Cancelar");
        btnCancel.setToolTipText("Cerrar cuadro de diálogo");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        txtPassword.setToolTipText("Ingrese su contraseña");
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPasswordKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtPassword))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(28, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCancel)
                                .addGap(32, 32, 32)
                                .addComponent(btnLogIn, javax.swing.GroupLayout.PREFERRED_SIZE, 122,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43,
                                        Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnLogIn)
                                        .addComponent(btnCancel))
                                .addContainerGap()));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogInActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnLogInActionPerformed
        ProcesoValidacion();
    }// GEN-LAST:event_btnLogInActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCancelActionPerformed
        frames.jfMain x = new frames.jfMain();
        x.setVisible(true);
        this.dispose();
    }// GEN-LAST:event_btnCancelActionPerformed

    private void txtPasswordKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtPasswordKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            ProcesoValidacion();
        }
    }// GEN-LAST:event_txtPasswordKeyPressed

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
            java.util.logging.Logger.getLogger(jDLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jDLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jDLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jDLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                jDLogin dialog = new jDLogin(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnLogIn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
