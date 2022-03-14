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
import pss.trabalhofinal.bancodeimagens.model.Admin;
import pss.trabalhofinal.bancodeimagens.model.NormalUser;
import pss.trabalhofinal.bancodeimagens.model.UserModel;

public abstract class UserDAO {

    public static void createTableUsers() {
        var query = "CREATE TABLE IF NOT EXISTS user("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name VARCHAR NOT NULL, "
                + "email VARCHAR NOT NULL UNIQUE, "
                + "username VARCHAR NOT NULL UNIQUE, "
                + "password VARCHAR NOT NULL, "
                + "dateRegister DATE NOT NULL, "
                + "admin INT DEFAULT 0, "
                + "authorized INT DEFAULT 0 "
                + ")";

        try {
            Connection conn = ConnectionSQLite.connect();
            Statement stmt = conn.createStatement();

            stmt.execute(query);

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar tabela usuario: " + e.getMessage());
        }
    }

    public static int insertUser(UserModel user) {
        var query = "INSERT INTO user(name, email, username, password, dateRegister, admin, authorized) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());
            ps.setDate(5, Date.valueOf(user.getRegistrationDate()));
            ps.setInt(6, Admin.class.isInstance(user) ? 1 : 0);
            ps.setInt(7, Admin.class.isInstance(user) ? 1 : 0);

            ps.executeQuery();

            ResultSet rs = ps.getGeneratedKeys();

            var id = -1;

            if (rs.next()) {
                id = rs.getInt("id");
            }

            rs.close();
            ps.close();
            conn.close();

            return id;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuario: " + e.getMessage());
        }
    }

    public static void insertUser(int id, UserModel user) {
        var query = "INSERT INTO user(id, name, email, username, password, dateRegister, admin, authorized) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, id);
            ps.setString(2, user.getName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getUsername());
            ps.setString(5, user.getPassword());
            ps.setDate(6, Date.valueOf(user.getRegistrationDate()));
            ps.setInt(7, Admin.class.isInstance(user) ? 1 : 0);
            ps.setInt(8, Admin.class.isInstance(user) ? 1 : 0);

            ps.executeQuery();

            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuario: " + e.getMessage());
        }
    }

    public static void removeUser(int id) {
        var query = "DELETE FROM user WHERE id = ?";

        try {
            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, id);

            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuario: " + e.getMessage());
        }
    }

    public static List<UserModel> getAllUsers() {
        List<UserModel> users = new ArrayList<>();

        var query = "SELECT * FROM user";

        try {
            Connection conn = ConnectionSQLite.connect();
            Statement ps = conn.createStatement();

            ResultSet rs = ps.executeQuery(query);

            while (rs.next()) {
                var id = rs.getInt("id");
                var name = rs.getString("name");
                var email = rs.getString("email");
                var username = rs.getString("username");
                var password = rs.getString("password");
                var dateRegister = rs.getDate("dateRegister").toLocalDate();
                var admin = rs.getInt("admin") == 1;
                var authorized = rs.getInt("authorized") == 1;

                if (admin) {
                    users.add(new Admin(id, name, email, username, password, dateRegister, false));
                } else {
                    users.add(new NormalUser(id, name, email, username, password, dateRegister, authorized, false));
                }
            }

            rs.close();
            ps.close();
            conn.close();

            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuario: " + e.getMessage());
        }
    }

    public static void appointNewAdmin() {
        var query = "UPDATE user SET admin = 1 WHERE dateRegister = SELECT MIN(dateRegister) FROM user";

        try {
            Connection conn = ConnectionSQLite.connect();
            Statement ps = conn.createStatement();

            ps.executeUpdate(query);

            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao apontar novo administrador: " + e.getMessage());
        }
    }
}
