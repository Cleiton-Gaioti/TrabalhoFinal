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
                + "authorized INT DEFAULT 0, "
                + "permission INT DEFAULT 0 "
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

    public static void insertUser(UserModel user) {
        var query = "INSERT INTO user(name, email, username, password, dateRegister, admin, authorized, permission) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());
            ps.setDate(5, Date.valueOf(user.getRegistrationDate()));
            ps.setInt(6, Admin.class.isInstance(user) ? 1 : 0);
            ps.setInt(7, Admin.class.isInstance(user) ? 1 : 0);
            ps.setInt(8, user.getPermissions());

            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuario: " + e.getMessage());
        }
    }

    public static void insertUser(int id, UserModel user) {
        var query = "INSERT INTO user(id, name, email, username, password, dateRegister, admin, authorized, permission) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            var admin = Admin.class.isInstance(user) ? 1 : 0;

            ps.setInt(1, id);
            ps.setString(2, user.getName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getUsername());
            ps.setString(5, user.getPassword());
            ps.setDate(6, Date.valueOf(user.getRegistrationDate()));
            ps.setInt(7, admin);
            ps.setInt(8, admin);
            ps.setInt(9, user.getPermissions());

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
                var permission = rs.getInt("permission");

                if (admin) {
                    users.add(new Admin(id, name, email, username, password, dateRegister, false));
                } else {
                    users.add(new NormalUser(id, name, email, username, password, dateRegister, authorized, permission,
                            false));
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
        var query = "UPDATE user SET admin = 1 WHERE id = (SELECT id FROM user ORDER BY dateRegister ASC LIMIT 1)";

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

    public static boolean verifyUsername(String username) {
        var query = "SELECT COUNT(1) AS count FROM user WHERE username = ?";

        try {
            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            var count = 0;

            if (rs.next()) {
                count = rs.getInt("count");
            }

            rs.close();
            ps.close();
            conn.close();

            return count == 0;
        } catch (SQLException e) {

            throw new RuntimeException("Erro ao verificar senha: " + e.getMessage());
        }
    }

    public static boolean verifyEmail(String email) {
        var query = "SELECT COUNT(1) AS count FROM user WHERE email = ?";

        try {
            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            var count = 0;

            if (rs.next()) {
                count = rs.getInt("count");
            }

            rs.close();
            ps.close();
            conn.close();

            return count == 0;
        } catch (SQLException e) {

            throw new RuntimeException("Erro ao verificar email: " + e.getMessage());
        }
    }

    public static UserModel login(String username, String password) {
        var query = "SELECT * FROM user WHERE LOWER(username) = LOWER(?) AND password = ?";

        try {
            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            UserModel user = null;

            if (rs.next()) {
                var id = rs.getInt("id");
                var name = rs.getString("name");
                var email = rs.getString("email");
                username = rs.getString("username");
                password = rs.getString("password");
                var dataRegister = rs.getDate("dateRegister").toLocalDate();
                var admin = rs.getInt("admin") == 1;
                var authorized = rs.getInt("authorized") == 1;
                var permission = rs.getInt("permission");

                if (!authorized) {
                    throw new RuntimeException(
                            "Usuário não autorizado. Espere até que um administrador autorize sua criação de usuário.");

                } else if (admin) {
                    user = new Admin(id, name, email, username, password, dataRegister, false);
                } else {
                    user = new NormalUser(id, name, email, username, password, dataRegister, authorized, permission,
                            false);
                }
            }

            rs.close();
            ps.close();
            conn.close();

            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao realizar login: " + e.getMessage());
        }
    }

    public static List<UserModel> search(String query, String substr) {

        try {
            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, "%" + substr + "%");

            ResultSet rs = ps.executeQuery();

            List<UserModel> users = new ArrayList<>();

            while (rs.next()) {
                var id = rs.getInt("id");
                var name = rs.getString("name");
                var email = rs.getString("email");
                var username = rs.getString("username");
                var password = rs.getString("password");
                var dataRegister = rs.getDate("dateRegister").toLocalDate();
                var administrator = rs.getInt("admin") == 1;
                var authorized = rs.getInt("authorized") == 1;
                var permission = rs.getInt("permission");

                if (administrator) {

                    users.add(new Admin(id, name, email, username, password, dataRegister, false));

                } else {

                    users.add(new NormalUser(id, name, email, username, password, dataRegister, authorized, permission,
                            false));

                }
            }

            rs.close();
            ps.close();
            conn.close();

            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário: " + e.getMessage());
        }
    }

    public static void update(UserModel newer) {
        var query = "UPDATE user SET "
                + "name = ?, "
                + "email = ?, "
                + "username = ?, "
                + "password = ?, "
                + "admin = ?, "
                + "permission = ? "
                + "WHERE id = ?";

        try {
            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            var admin = Admin.class.isInstance(newer) ? 1 : 0;

            ps.setString(1, newer.getName());
            ps.setString(2, newer.getEmail());
            ps.setString(3, newer.getUsername());
            ps.setString(4, newer.getPassword());
            ps.setInt(5, admin);
            ps.setInt(6, newer.getPermissions());
            ps.setInt(7, newer.getId());

            ps.executeUpdate();

            ps.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário: " + e.getMessage());
        }

    }

}
