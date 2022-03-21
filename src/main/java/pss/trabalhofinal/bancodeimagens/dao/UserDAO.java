package pss.trabalhofinal.bancodeimagens.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pss.trabalhofinal.bancodeimagens.collection.NotificationCollection;
import pss.trabalhofinal.bancodeimagens.collection.PermissaoCollection;
import pss.trabalhofinal.bancodeimagens.factory.ConnectionSQLite;
import pss.trabalhofinal.bancodeimagens.model.Admin;
import pss.trabalhofinal.bancodeimagens.model.NormalUser;
import pss.trabalhofinal.bancodeimagens.model.UserModel;

public class UserDAO {

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

    public void insertUser(UserModel user, boolean authorized) {
        var query = "INSERT INTO user(name, email, username, password, dateRegister, admin, authorized) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());
            ps.setDate(5, Date.valueOf(user.getRegistrationDate()));
            ps.setInt(6, Admin.class.isInstance(user) ? 1 : 0);
            ps.setInt(7, authorized ? 1 : 0);

            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuario: " + e.getMessage());
        }
    }

    public void removeUser(int id) {
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

    public List<UserModel> getAllUsers() {
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

                var notifications = new NotificationCollection(id);
                var permissoes = new PermissaoCollection(id);

                if (admin) {
                    users.add(new Admin(id, name, email, username, password, dateRegister, notifications, false));
                } else {
                    users.add(new NormalUser(id, name, email, username, password, dateRegister, notifications,
                            authorized, permissoes, false));
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

    public void appointNewAdmin() {
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

    public boolean verifyUsername(String username) {
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

    public boolean verifyEmail(String email) {
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

    public UserModel login(String username, String password) {
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

                var notifications = new NotificationCollection(id);
                var permissoes = new PermissaoCollection(id);

                if (!authorized) {
                    rs.close();
                    ps.close();
                    conn.close();

                    throw new RuntimeException(
                            "Usuário não autorizado. Espere até que um administrador autorize sua criação de usuário.");

                } else if (admin) {
                    user = new Admin(id, name, email, username, password, dataRegister, notifications, false);
                } else {
                    user = new NormalUser(id, name, email, username, password, dataRegister, notifications, authorized,
                            permissoes, false);
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

    public List<UserModel> search(String query, String substr) {

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

                var notifications = new NotificationCollection(id);
                var permissoes = new PermissaoCollection(id);

                if (administrator) {

                    users.add(new Admin(id, name, email, username, password, dataRegister, notifications, false));

                } else {

                    users.add(new NormalUser(id, name, email, username, password, dataRegister, notifications,
                            authorized, permissoes,
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

    public void update(UserModel newer) {
        var query = "UPDATE user SET "
                + "name = ?, "
                + "email = ?, "
                + "username = ?, "
                + "password = ?, "
                + "admin = ? "
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
            ps.setInt(6, newer.getId());

            ps.executeUpdate();

            ps.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário: " + e.getMessage());
        }

    }

    public List<NormalUser> getUsersUnauthorizeds() {
        var query = "SELECT * FROM user WHERE authorized = 0";

        try {
            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            List<NormalUser> users = new ArrayList<>();

            while (rs.next()) {
                var id = rs.getInt("id");
                var name = rs.getString("name");
                var email = rs.getString("email");
                var username = rs.getString("username");
                var password = rs.getString("password");
                var dataRegister = rs.getDate("dateRegister").toLocalDate();
                var authorized = rs.getInt("authorized") == 1;

                var notifications = new NotificationCollection(id);
                var permissoes = new PermissaoCollection(id);

                users.add(new NormalUser(id, name, email, username, password, dataRegister, notifications, authorized,
                        permissoes, false));
            }

            rs.close();
            ps.close();
            conn.close();

            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuários.");
        }
    }

    public void approveSolicitation(String username) {
        var query = "UPDATE user SET authorized = 1 WHERE username = ?";

        try {
            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, username);

            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (SQLException e) {

            throw new RuntimeException("Erro ao editar usuário: " + e.getMessage());

        }
    }

    public UserModel getUserById(int id) {

        var query = "SELECT * FROM user WHERE id = ?";

        try {
            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            UserModel user = null;

            if (rs.next()) {
                var name = rs.getString("name");
                var email = rs.getString("email");
                var username = rs.getString("username");
                var password = rs.getString("password");
                var dateRegister = rs.getDate("dateRegister").toLocalDate();
                var admin = rs.getInt("admin") == 1;
                var authorized = rs.getInt("authorized") == 1;

                var notifications = new NotificationCollection(id);
                var permissoes = new PermissaoCollection(id);

                if (admin) {
                    user = new Admin(id, name, email, username, password, dateRegister, notifications, false);
                } else {
                    user = new NormalUser(id, name, email, username, password, dateRegister, notifications, authorized,
                            permissoes, false);
                }
            }

            rs.close();
            ps.close();
            conn.close();

            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuario: " + e.getMessage());
        }
    }
}
