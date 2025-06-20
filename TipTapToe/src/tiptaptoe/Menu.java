
package tiptaptoe;

import javax.swing.JOptionPane;


public class Menu extends javax.swing.JFrame {

    /**
     * Creates new form Menu
     */
    
    public static GameSystem gameSystem = new GameSystem();

    public Menu() {
        initComponents();
    }
   
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        Salir = new javax.swing.JButton();
        IniciarSecion = new javax.swing.JButton();
        Registro = new javax.swing.JButton();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("MENU INICIO");

        Salir.setText("Salir");
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });

        IniciarSecion.setText("Iniciar secion");
        IniciarSecion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IniciarSecionActionPerformed(evt);
            }
        });

        Registro.setText("Registro");
        Registro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegistroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Salir)
                            .addComponent(Registro))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addComponent(IniciarSecion)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel1)
                .addGap(42, 42, 42)
                .addComponent(IniciarSecion)
                .addGap(39, 39, 39)
                .addComponent(Registro)
                .addGap(36, 36, 36)
                .addComponent(Salir)
                .addContainerGap(77, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void IniciarSecionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IniciarSecionActionPerformed
        String username = JOptionPane.showInputDialog(this, "Usuario:");
        if(username==null) return;
        String password = JOptionPane.showInputDialog(this, "Contraseña:");
        if(password==null) return;
        Player p = gameSystem.authenticate(username, password);

        if (p != null) {
            gameSystem.loggedInPlayer = p;
            PanelJuego dialog = new PanelJuego(this,true);
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
      
    }//GEN-LAST:event_IniciarSecionActionPerformed
    
    
    private void RegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegistroActionPerformed
        String nombre = JOptionPane.showInputDialog(this, "Nombre completo:");
        if (nombre==null) return;
        String username = JOptionPane.showInputDialog(this, "Nombre de usuario:");
        if(username==null) return;
        String password = JOptionPane.showInputDialog(this, "Contraseña (5 caracteres):");
        if(password==null) return;
        String points = JOptionPane.showInputDialog(this,"Puntuacion inicial: ");
        int point = Integer.parseInt(points);
        if(points == null) return;
        if (gameSystem.authenticate(username, password) != null) {
            JOptionPane.showMessageDialog(this, "El usuario ya existe.");
            return;
        }
        
        if (password.length() != 5) {
            JOptionPane.showMessageDialog(this, "La contraseña debe tener 5 caracteres.");
            return;
        }

        gameSystem.registerPlayer(username, password, nombre, point);
        JOptionPane.showMessageDialog(this, "Registro exitoso.");
    }//GEN-LAST:event_RegistroActionPerformed

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_SalirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
                
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton IniciarSecion;
    private javax.swing.JButton Registro;
    private javax.swing.JButton Salir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuItem jMenuItem1;
    // End of variables declaration//GEN-END:variables
}
