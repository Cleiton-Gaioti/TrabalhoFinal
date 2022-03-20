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

public abstract class PermissaoDAO {

    public static void createTablePermissoes() {
        var query = "CREATE TABLE IF NOT EXISTS permissoes ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "idUser INTEGER NOT NULL, "
                + "idAdminGranted INTEGER NOT NULL, "
                + "tipo VARCHAR NOT NULL, "
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

    public static void insert(Permissao permissao) {
        var query = "INSERT INTO permissoes (idUser, idAdminGranted, tipo, path, date) VALUES (?, ?, ?, ?, ?)";

        try {

            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, permissao.getIdUser());
            ps.setInt(2, permissao.getAdminWhoGranted());
            ps.setString(3, permissao.getTipo());
            ps.setString(4, permissao.getPath());
            ps.setDate(5, Date.valueOf(permissao.getDate()));

            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir permissão: " + e.getMessage());
        }
    }

    public static List<Permissao> getPermissionsByUser(int idUser) {
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

                permissoes.add(new Permissao(id, idUser, idAdminGranted, tipo, path, date));
            }

            rs.close();
            ps.close();
            conn.close();

            return permissoes;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir permissão: " + e.getMessage());
        }
    }

    public static List<Permissao> getPermissionsByUser(Connection conn, int idUser) {
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

                permissoes.add(new Permissao(id, idUser, idAdminGranted, tipo, path, date));
            }

            rs.close();
            ps.close();

            return permissoes;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir permissão: " + e.getMessage());
        }
    }

    public static void removeByPath(Connection conn, String path) {
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

    public static void removeById(int id) {
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
}
