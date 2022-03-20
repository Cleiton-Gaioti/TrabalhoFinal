package pss.trabalhofinal.bancodeimagens.presenter;

import java.time.format.DateTimeFormatter;

import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import pss.trabalhofinal.bancodeimagens.collection.UsersCollection;
import pss.trabalhofinal.bancodeimagens.dao.UserDAO;
import pss.trabalhofinal.bancodeimagens.model.Admin;
import pss.trabalhofinal.bancodeimagens.model.UserModel;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObserver;
import pss.trabalhofinal.bancodeimagens.view.ListarUsuariosView;

public class ListarUsuariosPresenter implements IObserver {

    /* ATTRIBUTES */
    private final ListarUsuariosView view;
    private UsersCollection users;
    private final Admin admin;

    /* CONSTRUCTOR */
    public ListarUsuariosPresenter(JDesktopPane desktop, Admin admin) {
        view = new ListarUsuariosView();
        this.admin = admin;

        reloadUsersList();

        loadTable();

        view.getBtnSearchUser().addActionListener(l -> {
            searchUser();
        });

        view.getBtnClose().addActionListener(l -> {
            view.dispose();
        });

        view.getBtnAddUser().addActionListener(l -> {
            new CadastrarUsuarioPresenter(desktop, false, true).registerObserver(this);
        });

        view.getBtnView().addActionListener(l -> {
            viewUser(desktop, admin);
        });

        view.getBtnRemoveUser().addActionListener(l -> {
            removeUser();
        });

        view.getBtnPermissoes().addActionListener(l -> {
            permissoes(desktop);
        });

        view.setLocation((desktop.getWidth() - view.getWidth()) / 2, (desktop.getHeight() - view.getHeight()) / 2);

        desktop.add(view);
        view.setVisible(true);
    }

    /* METHODS */
    private void reloadUsersList() {
        try {
            users = new UsersCollection();
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
        }
    }

    private void loadTable() {

        view.getTblUsuarios().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        var tableModel = new DefaultTableModel(
                new Object[][]{}, new String[]{"Id", "Tipo", "Nome", "Usuário", "Permissão", "Data de Cadastro"}) {
            @Override
            public boolean isCellEditable(final int row, final int column) {
                return false;
            }
        };

        tableModel.setNumRows(0);

        var dataFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (UserModel u : users.getAllUsers()) {
            var tipo = Admin.class.isInstance(u) ? "Administrador" : "Usuário";

            tableModel.addRow(
                    new Object[]{
                        u.getId(),
                        tipo,
                        u.getName(),
                        u.getUsername(),
                        u.getRegistrationDate().format(dataFormat)
                    });
        }

        view.getTblUsuarios().setModel(tableModel);

        /*
         * DefaultTableCellRenderer center = new DefaultTableCellRenderer();
         * center.setHorizontalAlignment(SwingConstants.CENTER);
         */
    }

    private void searchUser() {
        var substr = view.getTxtSearch().getText();

        if (substr.isBlank() || substr.isEmpty()) {

            users = new UsersCollection();

        } else {

            try {

                users.searchUser(substr, view.getBoxSearchFor().getSelectedIndex());

            } catch (RuntimeException e) {

                JOptionPane.showMessageDialog(view, e.getMessage());

            }
        }

        loadTable();
    }

    private void viewUser(JDesktopPane desktop, Admin admin) {

        var row = view.getTblUsuarios().getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(view, "Selecione uma linha!");

        } else {

            var id = Integer.valueOf(view.getTblUsuarios().getValueAt(row, 0).toString());

            try {

                var user = users.getUserById(id);

                new EditByAdminPresenter(desktop, admin, user).registerObserver(this);

            } catch (RuntimeException e) {

                JOptionPane.showMessageDialog(view, e.getMessage());

            }
        }
    }

    private void permissoes(JDesktopPane desktop) {
        var row = view.getTblUsuarios().getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Selecione uma linha!");

        } else {
            var id = Integer.valueOf(view.getTblUsuarios().getValueAt(row, 0).toString());
            var tipo = view.getTblUsuarios().getValueAt(row, 1).toString();
            if (tipo.equals("Administrador")) {
                JOptionPane.showMessageDialog(view,
                        "Administradores não precisam de permissões");
            } else {
                new PermissoesUserPresenter(UserDAO.getUserById(id), admin, desktop);
            }
        }
    }

    private void removeUser() {

        var row = view.getTblUsuarios().getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(view, "Selecione uma linha!");

        } else {

            var id = Integer.valueOf(view.getTblUsuarios().getValueAt(row, 0).toString());
            var name = view.getTblUsuarios().getValueAt(row, 2).toString();
            var username = view.getTblUsuarios().getValueAt(row, 3).toString();

            if (username.equals(admin.getUsername())) {

                JOptionPane.showMessageDialog(view,
                        "Para excluir sua conta, vá no menu Usuário -> Alterar Cadastro.");

            } else {

                String[] options = {"Sim", "Não"};

                int resposta = JOptionPane.showOptionDialog(
                        view,
                        "Tem certeza que deseja remover o usuário " + name + "?",
                        "Remover usuário",
                        JOptionPane.YES_OPTION,
                        JOptionPane.NO_OPTION,
                        null,
                        options,
                        options[1]);

                if (resposta == 0) {

                    try {
                        users.remove(id);

                        reloadUsersList();
                        loadTable();
                    } catch (RuntimeException e) {

                        JOptionPane.showMessageDialog(view, e.getMessage());

                    }
                }
            }
        }
    }

    @Override
    public void update(Object obj) {
        reloadUsersList();
        loadTable();
    }

}
