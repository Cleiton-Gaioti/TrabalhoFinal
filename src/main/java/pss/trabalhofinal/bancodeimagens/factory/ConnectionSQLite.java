package pss.trabalhofinal.bancodeimagens.factory;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionSQLite {

    private static final String URL = "jdbc:sqlite:db/users.sqlite";

    public static Connection connect() throws SQLException {

        return DriverManager.getConnection(URL);

    }

    public static void checkDiretorioDb() {
        File diretorio = new File("db/");

        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }
    }
}