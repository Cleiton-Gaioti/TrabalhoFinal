package pss.trabalhofinal.bancodeimagens;

import javax.swing.JOptionPane;
import pss.trabalhofinal.bancodeimagens.dao.HistoricoFiltroDAO;
import pss.trabalhofinal.bancodeimagens.dao.LixeiraDAO;
import pss.trabalhofinal.bancodeimagens.dao.NotificationDAO;
import pss.trabalhofinal.bancodeimagens.dao.PermissaoDAO;
import pss.trabalhofinal.bancodeimagens.dao.UserDAO;
import pss.trabalhofinal.bancodeimagens.factory.ConnectionSQLite;
import pss.trabalhofinal.bancodeimagens.presenter.PrincipalPresenter;

public class Main {

    public static void main(String[] args) {
        ConnectionSQLite.checkDiretorioDb();

        try {
            UserDAO.createTableUsers();
            HistoricoFiltroDAO.createTableHistorico();
            PermissaoDAO.createTablePermissoes();
            NotificationDAO.createTableNotification();
            LixeiraDAO.createTableLixeira();
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.exit(1);
        }

        new PrincipalPresenter();
    }
}
