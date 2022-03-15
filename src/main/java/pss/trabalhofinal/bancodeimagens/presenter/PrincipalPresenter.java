package pss.trabalhofinal.bancodeimagens.presenter;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import pss.trabalhofinal.bancodeimagens.model.Admin;
import pss.trabalhofinal.bancodeimagens.model.AdminLogadoState;
import pss.trabalhofinal.bancodeimagens.model.LoginState;
import pss.trabalhofinal.bancodeimagens.model.UserDeslogadoState;
import pss.trabalhofinal.bancodeimagens.model.UserLogadoState;
import pss.trabalhofinal.bancodeimagens.model.UserModel;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObserver;
import pss.trabalhofinal.bancodeimagens.view.PrincipalView;

public class PrincipalPresenter implements IObserver {
    /* ATTRIBUTES */
    private final PrincipalView view;
    private LoginState state;
    private UserModel user;

    /* CONSTRUCTOR */
    public PrincipalPresenter() {
        view = new PrincipalView();
        userDeslogadoLayout();
        user = null;

        view.getMenuLogin().addActionListener(l -> {
            login();
        });

        view.getMenuLogout().addActionListener(l -> {
            logout(true);
        });

        view.setSize(1280, 720);

        login();

        view.setLocationRelativeTo(view.getParent());
        view.setVisible(true);
    }

    /* METHODS */
    @Override
    public void update(Object obj) {
        user = (UserModel) obj;

        var isAdmin = Admin.class.isInstance(obj);

        updateFooter(isAdmin);

        if (isAdmin) {
            adminLayout();
        } else {
            userLogadoLayout();
        }
    }

    private void login() {
        new LoginPresenter(view.getDesktop()).registerObserver(this);
    }

    private void logout(boolean confirmar) {

        var resposta = 0;

        if (confirmar) {
            String[] options = { "Sim", "Não" };

            resposta = JOptionPane.showOptionDialog(
                    view,
                    "Tem certeza que deseja sair da sua conta?",
                    "Sair da conta",
                    JOptionPane.YES_OPTION,
                    JOptionPane.NO_OPTION,
                    null,
                    options,
                    options[1]);
        }

        if (resposta == 0) {
            closeAllTabs();
            userDeslogadoLayout();
            user = null;
            view.getTxtUser().setText("");
            view.getBtnNotifications().setText("0 notificações");
            userDeslogadoLayout();
            login();
        }
    }

    public void userDeslogadoLayout() {
        view.getMenuLogout().setVisible(false);
        view.getMenuUpdate().setVisible(false);
        view.getjMenuUsuario().setVisible(false);
        view.getjMenuAdministrador().setVisible(false);
        view.getBtnSolicitacao().setVisible(false);

        setState(new UserDeslogadoState(this));
    }

    public void userLogadoLayout() {
        /*
         * Caso o usuário logado seja um usuário normal, adapta a tela principal para o
         * modo de usuário
         */
        view.getjMenuAdministrador().setVisible(false);
        view.getjMenuUsuario().setVisible(true);
        view.getMenuLogin().setVisible(false);
        view.getMenuLogout().setVisible(true);
        view.getMenuUpdate().setVisible(true);
        view.getBtnSolicitacao().setVisible(false);

        setState(new UserLogadoState(this));
    }

    public void adminLayout() {
        /*
         * Caso o usuário logado seja um administrador, adapta a tela principal para o
         * modo de administrador
         */
        view.getjMenuAdministrador().setVisible(true);
        view.getjMenuUsuario().setVisible(true);
        view.getMenuLogin().setVisible(false);
        view.getMenuLogout().setVisible(true);
        view.getMenuUpdate().setVisible(true);
        view.getBtnSolicitacao().setVisible(true);

        setState(new AdminLogadoState(this));
    }

    private void updateFooter(boolean isAdmin) {

        if (isAdmin) {
            view.getTxtUser().setText("Administrador - " + user.getName());
        } else {
            view.getTxtUser().setText("Usuário - " + user.getName());
        }
    }

    private void closeAllTabs() {
        for (JInternalFrame f : view.getDesktop().getAllFrames()) {
            f.dispose();
        }
    }

    /* GETTERS AND SETTERS */
    private void setState(LoginState state) {
        if (state == null) {
            JOptionPane.showMessageDialog(view, "Estado de usuário nulo.");
            System.exit(1);
        } else {
            this.state = state;
        }
    }
}