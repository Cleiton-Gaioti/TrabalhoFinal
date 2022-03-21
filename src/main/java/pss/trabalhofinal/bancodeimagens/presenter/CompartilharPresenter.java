package pss.trabalhofinal.bancodeimagens.presenter;

import java.time.LocalDate;

import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import pss.trabalhofinal.bancodeimagens.collection.UsersCollection;
import pss.trabalhofinal.bancodeimagens.dao.NotificationDAO;
import pss.trabalhofinal.bancodeimagens.dao.PermissaoDAO;
import pss.trabalhofinal.bancodeimagens.model.Image;
import pss.trabalhofinal.bancodeimagens.model.NormalUser;
import pss.trabalhofinal.bancodeimagens.model.Notification;
import pss.trabalhofinal.bancodeimagens.model.Permissao;
import pss.trabalhofinal.bancodeimagens.model.UserModel;
import pss.trabalhofinal.bancodeimagens.view.CompartilharView;

public class CompartilharPresenter {

    private final NotificationDAO notificationDAO;
    private final PermissaoDAO permissaoDAO;
    private final UsersCollection users;
    private CompartilharView view;
    private final UserModel user;
    private Image imagem;

    public CompartilharPresenter(UserModel user, Image imagem, JDesktopPane desktop) {
        notificationDAO = new NotificationDAO();
        permissaoDAO = new PermissaoDAO();
        view = new CompartilharView();
        users = new UsersCollection();
        this.imagem = imagem;
        this.user = user;
        loadTable();

        view.getBtnFechar().addActionListener(l -> {
            view.dispose();
        });

        view.getBtnCompartilhar().addActionListener(l -> {
            compartilhar();
        });

        desktop.add(view);
        view.setVisible(true);
    }

    private void compartilhar() {
        var row = view.getTblUsers().getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Selecione uma linha!");
        } else {
            var id = Integer.parseInt(view.getTblUsers().getValueAt(row, 0).toString());

            UserModel um = users.getUserById(id);

            permissaoDAO.insert(new Permissao(um.getId(), user.getId(), "imagem", imagem.getPath(), LocalDate.now()));
            notificationDAO.insert(new Notification(user.getId(), um.getId(), "Acesso concedido a " + imagem.getPath(),
                    false, LocalDate.now()));

            loadTable();
        }
    }

    private void loadTable() {
        view.getTblUsers().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        var tableModel = new DefaultTableModel(
                new Object[][] {}, new String[] { "Id", "Username" }) {
            @Override
            public boolean isCellEditable(final int row, final int column) {
                return false;
            }
        };

        tableModel.setNumRows(0);

        for (NormalUser u : users.getNormalUsers()) {
            if (user.getId() != u.getId() && !permissaoDAO.isAuthorized(u.getId(), imagem.getPath())) {
                tableModel.addRow(new Object[] {
                        u.getId(),
                        u.getUsername() });
            }
        }

        view.getTblUsers().setModel(tableModel);
    }
}
