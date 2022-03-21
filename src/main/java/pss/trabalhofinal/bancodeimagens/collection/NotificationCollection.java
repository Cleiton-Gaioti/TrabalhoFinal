package pss.trabalhofinal.bancodeimagens.collection;

import java.util.ArrayList;
import java.util.List;

import pss.trabalhofinal.bancodeimagens.dao.NotificationDAO;
import pss.trabalhofinal.bancodeimagens.model.Notification;

public class NotificationCollection {
    // ATTRIBUTES
    private List<Notification> notifications;
    private final NotificationDAO notificationDAO;

    // CONSTRUCTOR
    public NotificationCollection(List<Notification> notifications) {
        notificationDAO = new NotificationDAO();
        setNotifications(notifications);
    }

    public NotificationCollection(int idUser) {
        notificationDAO = new NotificationDAO();
        setNotifications(notificationDAO.getNotificationsByUser(idUser));
    }

    public NotificationCollection() {
        this(new ArrayList<>());
    }

    // METHODS
    public void add(Notification notification, int idUser) {
        if (notification == null) {
            throw new RuntimeException("Notificação nula.");
        } else {
            notifications.add(notification);

            notificationDAO.insert(notification);
            setNotifications(notificationDAO.getNotificationsByUser(idUser));
        }
    }

    public int countNotifications() {
        return notifications.size();
    }

    public int countReadNotifications() {
        var count = 0;

        for (Notification n : notifications) {
            if (n.wasRead()) {
                count++;
            }
        }

        return count;
    }

    public int countUnreadNotifications() {
        var count = 0;

        for (Notification n : notifications) {
            if (!n.wasRead()) {
                count++;
            }
        }

        return count;
    }

    public List<Notification> getUnreadNotifications() {
        List<Notification> unreads = new ArrayList<>();

        for (Notification n : notifications) {
            if (!n.wasRead()) {
                unreads.add(n);
            }
        }

        return unreads;
    }

    @Override
    public String toString() {
        var s = "";

        for (Notification n : notifications) {
            s += n.toString() + ", ";
        }

        return s;
    }

    // GETTERS AND SETTERS
    public List<Notification> getNotifications() {
        return this.notifications;
    }

    private void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
