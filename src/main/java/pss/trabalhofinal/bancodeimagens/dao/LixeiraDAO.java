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
import pss.trabalhofinal.bancodeimagens.model.Lixeira;

public abstract class LixeiraDAO {

    public static void createTableLixeira() {
        var query = "CREATE TABLE IF NOT EXISTS lixeira ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "idUser INTEGER NOT NULL REFERENCES user (id), "
                + "caminhoDeOrigem VARCHAR NOT NULL, "
                + "nomeDoArquivo VARCHAR NOT NULL, "
                + "dataDeExclusao DATE NOT NULL"
                + ")";

        try {
            Connection conn = ConnectionSQLite.connect();
            Statement stmt = conn.createStatement();

            stmt.execute(query);

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar tabela lixeira: " + e.getMessage());
        }
    }

    public static void insert(Lixeira lixeira) {
        var query = "INSERT INTO lixeira (idUser, caminhoDeOrigem, nomeDoArquivo, dataDeExclusao) VALUES (?, ?, ?, ?)";

        try {
            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, lixeira.getIdUser());
            ps.setString(2, lixeira.getCaminhoDeOrigem());
            ps.setString(3, lixeira.getNomeDoArquivo());
            ps.setDate(4, Date.valueOf(lixeira.getDataDeExclusao()));

            ps.execute();

            PermissaoDAO.removeByPath(conn, lixeira.getCaminhoDeOrigem());

            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir na tabela lixeira:" + e.getMessage());
        }
    }

    public static void remove(String nomeDoArquivo) {
        var query = "DELETE FROM lixeira WHERE nomeDoArquivo = ?";

        try {
            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, nomeDoArquivo);

            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover da tabela lixeira:" + e.getMessage());
        }
    }

    public static List<Lixeira> getLixeiraByUser(int idUser) {
        var query = "select * from lixeira where idUser = ? ";

        try {

            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, idUser);

            ResultSet rs = ps.executeQuery();

            List<Lixeira> lixeira = new ArrayList<>();

            while (rs.next()) {
                var id = rs.getInt("id");
                var idUser1 = rs.getInt("idUser");
                var caminhoDeOrigem = rs.getString("caminhoDeOrigem");
                var nomeDoArquivo = rs.getString("nomeDoArquivo");
                var dataDeExclusao = rs.getDate("dataDeExclusao").toLocalDate();

                lixeira.add(new Lixeira(id, idUser1, caminhoDeOrigem, nomeDoArquivo, dataDeExclusao));
            }

            rs.close();
            ps.close();
            conn.close();
            return lixeira;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar exclusões: " + e.getMessage());
        }

    }

}
