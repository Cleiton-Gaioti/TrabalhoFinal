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
import pss.trabalhofinal.bancodeimagens.model.Permissao;

public class PermissaoDAO {

    public static void createTablePermissoes() {
        var query = "CREATE TABLE IF NOT EXISTS permissoes ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "idUser INTEGER NOT NULL, "
                + "idAdminGranted INTEGER NOT NULL, "
                + "tipo VARCHAR NOT NULL,"
                + "visualizar INT DEFAULT 0, "
                + "compartilhar INT DEFAULT 0,"
                + "excluir INT DEFAULT 0, "
                + "path VARCHAR NOT NULL, "
                + "date DATE NOT NULL "
                + ")";

        try {
            Connection conn = ConnectionSQLite.connect();
            Statement stmt = conn.createStatement();

            stmt.execute(query);

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar tabela permissões: " + e.getMessage());
        }
    }

    public void insert(Permissao permissao) {
        var query = "INSERT INTO permissoes (idUser, idAdminGranted, tipo, visualizar, compartilhar, excluir, path, date) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {

            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, permissao.getIdUser());
            ps.setInt(2, permissao.getAdminWhoGranted());
            ps.setString(3, permissao.getTipo());
            ps.setInt(4, permissao.isVisualizar() ? 1 : 0);
            ps.setInt(5, permissao.isCompartilhar() ? 1 : 0);
            ps.setInt(6, permissao.isExcluir() ? 1 : 0);
            ps.setString(7, permissao.getPath());
            ps.setDate(8, Date.valueOf(permissao.getDate()));

            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir permissão: " + e.getMessage());
        }
    }

    public List<Permissao> getPermissionsByUser(int idUser) {
        var query = "SELECT * FROM permissoes WHERE idUser = ?";

        try {

            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, idUser);

            ResultSet rs = ps.executeQuery();

            List<Permissao> permissoes = new ArrayList<>();

            while (rs.next()) {
                var id = rs.getInt("id");
                var idAdminGranted = rs.getInt("idAdminGranted");
                var tipo = rs.getString("tipo");
                var path = rs.getString("path");
                var date = rs.getDate("date").toLocalDate();
                var visualizar = rs.getInt("visualizar") == 1;
                var excluir = rs.getInt("excluir") == 1;
                var compartilhar = rs.getInt("compartilhar") == 1;

                permissoes.add(new Permissao(id, idUser, idAdminGranted, tipo, path, visualizar, compartilhar, excluir, date));
            }

            rs.close();
            ps.close();
            conn.close();

            return permissoes;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir permissão: " + e.getMessage());
        }
    }

    public List<Permissao> getPermissionsByUser(Connection conn, int idUser) {
        var query = "SELECT * FROM permissoes WHERE idUser = ?";

        try {

            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, idUser);

            ResultSet rs = ps.executeQuery();

            List<Permissao> permissoes = new ArrayList<>();

            while (rs.next()) {
                var id = rs.getInt("id");
                var idAdminGranted = rs.getInt("idAdminGranted");
                var tipo = rs.getString("tipo");
                var path = rs.getString("path");
                var date = rs.getDate("date").toLocalDate();
                var visualizar = rs.getInt("visualizar") == 1;
                var excluir = rs.getInt("excluir") == 1;
                var compartilhar = rs.getInt("compartilhar") == 1;

                permissoes.add(new Permissao(id, idUser, idAdminGranted, tipo, path, visualizar, compartilhar, excluir, date));
            }

            rs.close();
            ps.close();

            return permissoes;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir permissão: " + e.getMessage());
        }
    }

    public void removeByPath(Connection conn, String path) {
        var query = "delete from permissoes "
                + "where path = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, path);

            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover permissões: " + e.getMessage());
        }

    }

    public void removeById(int id) {
        var query = "delete from permissoes "
                + "where id = ?";

        try {
            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, id);

            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover permissões: " + e.getMessage());
        }

    }

    public boolean isAuthorized(int idUser, String path) {
        var query = "select count(1) as num from permissoes where "
                + "idUser = ? and path = ?";

        try {
            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, idUser);
            ps.setString(2, path);

            boolean auth = false;
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                auth = rs.getInt("num") > 0;
            }

            rs.close();
            ps.close();
            conn.close();

            return auth;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar autorização: " + e.getMessage());
        }

    }

    public boolean isAuthorizedFolder(int idUser) {
        var query = "select count(1) as num from permissoes where "
                + "idUser = ? and path = 'images/'";

        try {
            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, idUser);

            boolean auth = false;
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                auth = rs.getInt("num") > 0;
            }

            rs.close();
            ps.close();
            conn.close();

            return auth;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar autorização: " + e.getMessage());
        }

    }

    public void autorizarCompartilhar(int idUser, String path) {
        var query = "update permissoes "
                + "set compartilhar = 1 "
                + "where idUser = ? and path = ?";

        try {
            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, idUser);
            ps.setString(2, path);

            ps.executeUpdate();

            ps.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao autorizar compartilhar: " + e.getMessage());
        }
    }

    public void autorizarExcluir(int idUser, String path) {
        var query = "update permissoes "
                + "set excluir = 1 "
                + "where idUser = ? and path = ?";

        try {
            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, idUser);
            ps.setString(2, path);

            ps.executeUpdate();

            ps.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao autorizar compartilhar: " + e.getMessage());
        }
    }

    public boolean canVisualizar(int idUser, String path) {
        var query = "select count(1) as num from permissoes where "
                + "idUser = ? and path = ? and visualizar = 1";

        try {
            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, idUser);
            ps.setString(2, path);

            boolean auth = false;
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                auth = rs.getInt("num") > 0;
            }

            rs.close();
            ps.close();
            conn.close();

            return auth;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar autorização: " + e.getMessage());
        }

    }

    public boolean canCompartilhar(int idUser, String path) {
        var query = "select count(1) as num from permissoes where "
                + "idUser = ? and path = ? and compartilhar = 1";

        try {
            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, idUser);
            ps.setString(2, path);

            boolean auth = false;
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                auth = rs.getInt("num") > 0;
            }

            rs.close();
            ps.close();
            conn.close();

            return auth;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar autorização: " + e.getMessage());
        }

    }

    public boolean canExcluir(int idUser, String path) {
        var query = "select count(1) as num from permissoes where "
                + "idUser = ? and path = ? and excluir = 1";

        try {
            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, idUser);
            ps.setString(2, path);

            boolean auth = false;
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                auth = rs.getInt("num") > 0;
            }

            rs.close();
            ps.close();
            conn.close();

            return auth;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar autorização: " + e.getMessage());
        }

    }
}
