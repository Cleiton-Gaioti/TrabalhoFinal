package pss.trabalhofinal.bancodeimagens.view;

import javax.swing.JButton;
import javax.swing.JLabel;

public class NaoAutorizadoView extends javax.swing.JInternalFrame {

    public NaoAutorizadoView() {
        initComponents();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lblIcone = new javax.swing.JLabel();
        lblAcessoNaoAutorizado = new javax.swing.JLabel();
        btnFechar = new javax.swing.JButton();
        lblAguarde = new javax.swing.JLabel();
        btnSolicitar = new javax.swing.JButton();

        jScrollPane1.setViewportView(lblIcone);

        lblAcessoNaoAutorizado.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        lblAcessoNaoAutorizado.setText("Acesso não autorizado!");

        btnFechar.setText("Fechar");

        lblAguarde.setText("Aguarde autorização!");

        btnSolicitar.setText("Solicitar Acesso");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAguarde)
                            .addComponent(lblAcessoNaoAutorizado))
                        .addGap(0, 105, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSolicitar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnFechar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lblAcessoNaoAutorizado, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblAguarde)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFechar)
                    .addComponent(btnSolicitar))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public JButton getBtnFechar() {
        return btnFechar;
    }

    public JButton getBtnSolicitar() {
        return btnSolicitar;
    }

    public JLabel getLblAguarde() {
        return lblAguarde;
    }

    public JLabel getLblIcone() {
        return lblIcone;
    }

    public JLabel getLblAcessoNaoAutorizado() {
        return lblAcessoNaoAutorizado;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnSolicitar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAcessoNaoAutorizado;
    private javax.swing.JLabel lblAguarde;
    private javax.swing.JLabel lblIcone;
    // End of variables declaration//GEN-END:variables
}
