package pss.trabalhofinal.bancodeimagens.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pss.trabalhofinal.bancodeimagens.factory.ConnectionSQLite;
import pss.trabalhofinal.bancodeimagens.model.Notification;

public abstract class NotificationDAO {

    public static void createTableNotification() {
        var query = "CREATE TABLE IF NOT EXISTS notification("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "idUserWhoSent INTEGER NOT NULL REFERENCES user (id), "
                + "idUser INTEGER NOT NULL REFERENCES user (id), "
                + "content VARCHAR NOT NULL, "
                + "read INTEGER NOT NULL DEFAULT 0, "
                + "date DATE NOT NULL "
                + ")";

        try {
            Connection conn = ConnectionSQLite.connect();
            Statement stmt = conn.createStatement();

            stmt.execute(query);

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar tabela notificações: " + e.getMessage());
        }
    }

    public static void insert(Notification notification) {
        var query = "INSERT INTO notification (idUserWhoSent, idUser, content, read, date) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, notification.getidUserWhoSent());
            ps.setInt(2, notification.getIdUser());
            ps.setString(3, notification.getContent());
            ps.setInt(4, notification.getRead() ? 1 : 0);
            ps.setDate(5, Date.valueOf(notification.getDate()));

            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir notificação: " + e.getMessage());
        }
    }

    public static List<Notification> getNotificationsByUser(int idUser) {
        var query = "SELECT * FROM notification WHERE idUser = ?";

        try {
            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, idUser);

            ResultSet rs = ps.executeQuery();

            List<Notification> notifications = new ArrayList<>();

            while (rs.next()) {
                var id = rs.getInt("id");
                var idAdminWhoSent = rs.getInt("idUserWhoSent");
                var content = rs.getString("content");
                var read = rs.getInt("read") == 1;
                var date = rs.getDate("date").toLocalDate();

                notifications.add(new Notification(id, idAdminWhoSent, idUser, content, read, date));
            }

            rs.close();
            ps.close();
            conn.close();

            return notifications;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar notificações: " + e.getMessage());
        }
    }

    public static List<Notification> getNotificationsByUser(Connection conn, int idUser) {
        var query = "SELECT * FROM notification WHERE idUser = ?";

        try {
            conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, idUser);

            ResultSet rs = ps.executeQuery();

            List<Notification> notifications = new ArrayList<>();

            while (rs.next()) {
                var id = rs.getInt("id");
                var idAdminWhoSent = rs.getInt("idUserWhoSent");
                var content = rs.getString("content");
                var read = rs.getInt("read") == 1;
                var date = rs.getDate("date").toLocalDate();

                notifications.add(new Notification(id, idAdminWhoSent, idUser, content, read, date));
            }

            rs.close();
            ps.close();

            return notifications;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar notificações: " + e.getMessage());
        }
    }

    public static Notification getNotificationById(int id) {
        var query = "SELECT * FROM notification WHERE id = ?";

        try {
            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            Notification notification = null;

            if (rs.next()) {
                var idUser = rs.getInt("idUser");
                var idAdminWhoSent = rs.getInt("idUserWhoSent");
                var content = rs.getString("content");
                var read = rs.getInt("read") == 1;
                var date = rs.getDate("date").toLocalDate();

                notification = new Notification(id, idAdminWhoSent, idUser, content, read, date);
            }

            rs.close();
            ps.close();
            conn.close();

            return notification;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar notificação: " + e.getMessage());
        }
    }

    public static void setReadNotification(int id) {
        var query = "UPDATE notification set read = 1 WHERE id = ?";

        try {
            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, id);

            ps.executeUpdate();

            ps.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar notificação: " + e.getMessage());
        }
    }
}
