package pss.trabalhofinal.bancodeimagens.presenter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import pss.trabalhofinal.bancodeimagens.collection.UsersCollection;
import pss.trabalhofinal.bancodeimagens.dao.NotificationDAO;
import pss.trabalhofinal.bancodeimagens.dao.PermissaoDAO;
import pss.trabalhofinal.bancodeimagens.dao.UserDAO;
import pss.trabalhofinal.bancodeimagens.model.Admin;
import pss.trabalhofinal.bancodeimagens.model.Notification;
import pss.trabalhofinal.bancodeimagens.model.Permissao;
import pss.trabalhofinal.bancodeimagens.model.UserModel;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObservable;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObserver;
import pss.trabalhofinal.bancodeimagens.view.ShowNotificationsView;

public class ShowNotificationsPresenter implements IObservable {

    /* ATTRIBUTES */
    private final NotificationDAO notificationDAO;
    private final ShowNotificationsView view;
    private final PermissaoDAO permissaoDAO;
    private final List<IObserver> observers;
    private DefaultTableModel tableModel;
    private final UserDAO userDAO;
    private UserModel user;

    /* CONSTRUCTOR */
    public ShowNotificationsPresenter(JDesktopPane desktop, UserModel user) {
        notificationDAO = new NotificationDAO();
        tableModel = new DefaultTableModel();
        view = new ShowNotificationsView();
        permissaoDAO = new PermissaoDAO();
        observers = new ArrayList<>();
        userDAO = new UserDAO();
        this.user = user;

        tableModel = new DefaultTableModel(
                new Object[][]{}, new String[]{"Id", "Notificação"}) {
            @Override
            public boolean isCellEditable(final int row, final int column) {
                return false;
            }
        };

        loadList();

        view.getBtnClose().addActionListener(l -> {
            view.dispose();
        });

        view.getListNotifications().getSelectionModel().addListSelectionListener((ListSelectionEvent l) -> {
            if (Admin.class.isInstance(user)) {
                permissao();
            } else {
                setRead();
            }
        });

        view.setLocation((desktop.getWidth() - view.getWidth()) / 2, (desktop.getHeight() - view.getHeight()) / 2);
        desktop.add(view);
        view.setVisible(true);
    }

    private void permissao() {

        var row = view.getListNotifications().getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Selecione uma linha.");
        } else {
            try {

                var notificacao = view.getListNotifications().getValueAt(row, 1).toString();
                if (notificacao.startsWith("<html><b>")) {
                    notificacao = notificacao.replace("<html><b>", "").replace("</b></html>", "");
                    if (notificacao.startsWith("USER:")) {
                        UsersCollection users = new UsersCollection();
                        var dados = notificacao.split(",");
                        var user = users.getUserByUsername(dados[0].replace("USER:", "").strip());
                        var path = dados[1].replace("IMAGEM:", "").strip();
                        var acao = dados[2].replace("AÇÃO:", "");

                        String[] options = {"Sim", "Não"};

                        int resposta = JOptionPane.showOptionDialog(
                                view,
                                "Autorizar " + user.getUsername() + " a " + acao + "?",
                                "Autorizar usuário",
                                JOptionPane.YES_OPTION,
                                JOptionPane.NO_OPTION,
                                null,
                                options,
                                options[1]);

                        if (resposta == 0) {
                            if (permissaoDAO.isAuthorizedFolder(user.getId())) {
                                permissaoDAO.insert(new Permissao(user.getId(), this.user.getId(), "arquivo", path, LocalDate.now()));
                            }
                            if (permissaoDAO.isAuthorized(user.getId(), path)) {
                                if (acao.equalsIgnoreCase("excluir")) {
                                    permissaoDAO.autorizarExcluir(user.getId(), path);
                                    notificationDAO.insert(new Notification(this.user.getId(), user.getId(),
                                            "Aceso autorizado a " + acao.toLowerCase() + " " + path, false, LocalDate.now()));
                                } else if (acao.equalsIgnoreCase("compartilhar")) {
                                    permissaoDAO.autorizarCompartilhar(user.getId(), path);
                                    notificationDAO.insert(new Notification(this.user.getId(), user.getId(),
                                            "Aceso autorizado a " + acao.toLowerCase() + " " + path, false, LocalDate.now()));
                                }
                            } else if (acao.equalsIgnoreCase("visualizar") && !permissaoDAO.isAuthorizedFolder(user.getId())) {
                                permissaoDAO.insert(
                                        new Permissao(user.getId(), this.user.getId(), "arquivo", path, LocalDate.now()));
                                notificationDAO.insert(new Notification(this.user.getId(), user.getId(),
                                        "Aceso autorizado a " + acao.toLowerCase() + " " + path, false, LocalDate.now()));
                            }
                        } else {
                            notificationDAO.insert(new Notification(this.user.getId(), user.getId(),
                                    "Aceso negado a " + path, false, LocalDate.now()));
                        }
                        setRead();
                    }
                }

            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(view, e.getMessage());

            }
        }

    }

    private void setRead() {
        var row = view.getListNotifications().getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Selecione uma linha.");
        } else {

            try {

                var id = Integer.valueOf(view.getListNotifications().getValueAt(row, 0).toString());

                var notification = notificationDAO.getNotificationById(id);

                view.getListNotifications().setValueAt(notification.getContent(), row, 1);

                notificationDAO.setReadNotification(notification.getId());

                user = userDAO.getUserById(user.getId());

                notifyObservers(user);

            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(view, e.getMessage());

            }
        }
    }

    /* METHODS */
    private void loadList() {
        tableModel.setNumRows(0);

        view.getListNotifications().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        user.getNotifications().getUnreadNotifications().forEach(n -> {
            tableModel.addRow(
                    new String[]{
                        String.valueOf(n.getId()),
                        "<html><b>" + n.getContent() + "</b></html>"
                    });
        });

        view.getListNotifications().setModel(tableModel);
    }

    @Override
    public void registerObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Object obj) {
        observers.forEach(o -> {
            o.update(user);
        });
    }

}
