package pss.trabalhofinal.bancodeimagens.view;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ListarUsuariosView extends javax.swing.JInternalFrame {

    public ListarUsuariosView() {
        initComponents();
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        btnRemoveUser = new javax.swing.JButton();
        btnUpdateUser = new javax.swing.JButton();
        btnAddUser = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        btnSendNotification = new javax.swing.JButton();
        checkSelectAll = new javax.swing.JCheckBox();
        txtSearch = new javax.swing.JTextField();
        btnSearchUser = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Listar Usuários");

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblUsuarios);

        btnRemoveUser.setText("Remover");

        btnUpdateUser.setText("Atualizar");

        btnAddUser.setText("Novo");

        btnClose.setText("Fechar");

        btnSendNotification.setText("Enviar Notificação");

        checkSelectAll.setText("Selecionar Todos");

        txtSearch.setToolTipText("Busca por id, nome, usuário ou email");

        btnSearchUser.setText("Buscar Usuário");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnClose)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAddUser)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUpdateUser)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoveUser))
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearchUser)
                        .addGap(18, 18, 18)
                        .addComponent(checkSelectAll)
                        .addGap(18, 18, 18)
                        .addComponent(btnSendNotification)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSendNotification)
                    .addComponent(checkSelectAll)
                    .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(btnSearchUser))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRemoveUser)
                    .addComponent(btnUpdateUser)
                    .addComponent(btnAddUser)
                    .addComponent(btnClose))
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddUser;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnRemoveUser;
    private javax.swing.JButton btnSearchUser;
    private javax.swing.JButton btnSendNotification;
    private javax.swing.JButton btnUpdateUser;
    private javax.swing.JCheckBox checkSelectAll;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables

    public JButton getBtnAddUser() {
        return btnAddUser;
    }

    public JButton getBtnClose() {
        return btnClose;
    }

    public JButton getBtnRemoveUser() {
        return btnRemoveUser;
    }

    public JButton getBtnSearchUser() {
        return btnSearchUser;
    }

    public JButton getBtnSendNotification() {
        return btnSendNotification;
    }

    public JButton getBtnUpdateUser() {
        return btnUpdateUser;
    }

    public JCheckBox getCheckSelectAll() {
        return checkSelectAll;
    }

    public JTable getTblUsuarios() {
        return tblUsuarios;
    }

    public JTextField getTxtSearch() {
        return txtSearch;
    }
}
