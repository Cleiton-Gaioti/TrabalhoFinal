package pss.trabalhofinal.bancodeimagens.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import pss.trabalhofinal.bancodeimagens.dao.NotificationDAO;
import pss.trabalhofinal.bancodeimagens.dao.UserDAO;
import pss.trabalhofinal.bancodeimagens.model.UserModel;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObservable;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObserver;
import pss.trabalhofinal.bancodeimagens.view.ShowNotificationsView;

public class ShowNotificationsPresenter implements IObservable {

    /* ATTRIBUTES */
    private final List<IObserver> observers;
    private final ShowNotificationsView view;
    private DefaultTableModel tableModel;
    private UserModel user;

    /* CONSTRUCTOR */
    public ShowNotificationsPresenter(JDesktopPane desktop, UserModel user) {
        tableModel = new DefaultTableModel();
        view = new ShowNotificationsView();
        observers = new ArrayList<>();
        this.user = user;

        tableModel = new DefaultTableModel(
                new Object[][] {}, new String[] { "Id", "Notificação" }) {
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
            setRead();
        });

        view.setLocation((desktop.getWidth() - view.getWidth()) / 2, (desktop.getHeight() - view.getHeight()) / 2);
        desktop.add(view);
        view.setVisible(true);
    }

    private void setRead() {
        var row = view.getListNotifications().getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Selecione uma linha.");
        } else {

            try {

                var id = Integer.valueOf(view.getListNotifications().getValueAt(row, 0).toString());

                var notification = NotificationDAO.getNotificationById(id);

                view.getListNotifications().setValueAt(notification.getContent(), row, 1);

                NotificationDAO.setReadNotification(notification.getId());

                user = UserDAO.getUserById(user.getId());

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
                    new String[] {
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
