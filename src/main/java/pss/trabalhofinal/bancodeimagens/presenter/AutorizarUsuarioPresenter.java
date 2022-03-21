package pss.trabalhofinal.bancodeimagens.presenter;

import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import pss.trabalhofinal.bancodeimagens.collection.UsersCollection;
import pss.trabalhofinal.bancodeimagens.view.AutorizarUsuarioView;

public class AutorizarUsuarioPresenter {

    /* ATTRIBUTES */
    private final DefaultTableModel tableModel;
    private final AutorizarUsuarioView view;
    private UsersCollection users;

    /* CONSTRUCTOR */
    public AutorizarUsuarioPresenter(JDesktopPane desktop) {
        view = new AutorizarUsuarioView();
        users = new UsersCollection();

        tableModel = new DefaultTableModel(
                new Object[][] {}, new String[] { "Nome", "Usuário", "Email" }) {
            @Override
            public boolean isCellEditable(final int row, final int column) {
                return false;
            }
        };

        loadTable();

        view.getBtnClose().addActionListener(l -> {
            view.dispose();
        });

        view.getBtnAuthorize().addActionListener(l -> {
            authorize();
        });

        view.setLocation((desktop.getWidth() - view.getWidth()) / 2, (desktop.getHeight() - view.getHeight()) / 2);
        desktop.add(view);
        view.setVisible(true);
    }

    /* METHODS */
    private void loadTable() {

        tableModel.setNumRows(0);

        view.getTblUsers().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        try {

            var usersList = users.getUsersUnauthorizeds();

            usersList.forEach(u -> {
                tableModel.addRow(
                        new String[] {
                                u.getName(),
                                u.getUsername(),
                                u.getEmail()
                        });
            });

            view.getTblUsers().setModel(tableModel);
        } catch (RuntimeException e) {

            JOptionPane.showMessageDialog(view, "Erro ao carregar solicitações: " + e.getMessage());

        }
    }

    private void authorize() {
        var rows = view.getTblUsers().getSelectedRows();

        if (rows.length == 0) {

            JOptionPane.showMessageDialog(view, "Selecione uma linha.");

        } else {

            for (int row : rows) {
                var username = view.getTblUsers().getValueAt(row, 1).toString();

                try {
                    users.approveSolicitation(username);

                    loadTable();
                } catch (RuntimeException e) {
                    JOptionPane.showMessageDialog(view, "Erro ao aprovar solicitações: " + e.getMessage());
                }
            }
        }
    }

}
