package pss.trabalhofinal.bancodeimagens.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import pss.trabalhofinal.bancodeimagens.factory.ConnectionSQLite;

public abstract class HistoricoFiltroDAO {

    public static void createTableHistorico() {
        var query = "create table if not exists historicoFiltro ("
                + "id integer primary key autoincrement, "
                + "path varchar not null, "
                + "filter varchar not null, "
                + "date date not null)";

        try {
            Connection conn = ConnectionSQLite.connect();
            Statement stmt = conn.createStatement();

            stmt.execute(query);

            stmt.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar tabela Historico Filtro! " + e.getMessage());
        }
    }

    public static void insertHistorico(String imagem, String filtro) {
        var query = "insert into historicoFiltro(path, filter, date) "
                + "values (?, ?, ?)";

        try {
            Connection conn = ConnectionSQLite.connect();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, imagem);
            ps.setString(2, filtro);
            ps.setDate(3, Date.valueOf(LocalDate.now()));

            ps.executeUpdate();

            ps.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir aplicação de filtro no hisórico! " + e.getMessage());
        }

    }

}
