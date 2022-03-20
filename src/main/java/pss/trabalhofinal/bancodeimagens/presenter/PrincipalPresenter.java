package pss.trabalhofinal.bancodeimagens.presenter;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import pss.trabalhofinal.bancodeimagens.dao.PermissaoDAO;

import pss.trabalhofinal.bancodeimagens.model.Admin;
import pss.trabalhofinal.bancodeimagens.model.AdminLogadoState;
import pss.trabalhofinal.bancodeimagens.model.Image;
import pss.trabalhofinal.bancodeimagens.model.Permissao;
import pss.trabalhofinal.bancodeimagens.model.UserDeslogadoState;
import pss.trabalhofinal.bancodeimagens.model.UserLogadoState;
import pss.trabalhofinal.bancodeimagens.model.UserModel;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObserver;
import pss.trabalhofinal.bancodeimagens.view.PrincipalView;

public class PrincipalPresenter implements IObserver {

    /* ATTRIBUTES */
    private final PrincipalView view;
    // private LoginState state;
    private UserModel user;
    private List<Permissao> permissoes;

    /* CONSTRUCTOR */
    public PrincipalPresenter() {
        view = new PrincipalView();
        user = null;

        view.getMenuUpdate().addActionListener(l -> {
            new EditYourselfPresenter(view.getDesktop(), user).registerObserver(this);
        });

        view.getMenuLogin().addActionListener(l -> {
            login();
        });

        view.getMenuLogout().addActionListener(l -> {
            logout(true);
        });

        view.getMenuListarUsuarios().addActionListener(l -> {
            new ListarUsuariosPresenter(view.getDesktop(), (Admin) user);
        });

        view.getMenuAbrir().addActionListener(l -> {
            abrirArquivo();
        });

        view.getBtnSolicitacao().addActionListener(l -> {
            new AutorizarUsuarioPresenter(view.getDesktop());
        });

        view.getBtnNotifications().addActionListener(l -> {
            new ShowNotificationsPresenter(view.getDesktop(), user).registerObserver(this);
        });

        view.setSize(1280, 720);

        new UserDeslogadoState(this);

        login();

        view.setLocationRelativeTo(view.getParent());
        view.setVisible(true);
    }

    /* METHODS */
    @Override
    public void update(Object obj) {
        user = (UserModel) obj;

        var isAdmin = Admin.class.isInstance(obj);

        if (isAdmin) {
            new AdminLogadoState(this);
            permissoes = null;
        } else {
            new UserLogadoState(this);
            permissoes = PermissaoDAO.getPermissionsByUser(user.getId());
        }

        updateFooter(isAdmin);

    }

    private void login() {
        new LoginPresenter(view.getDesktop()).registerObserver(this);
    }

    private void logout(boolean confirmar) {

        var resposta = 0;

        if (confirmar) {
            String[] options = {"Sim", "Não"};

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

    private void abrirArquivo() {
        try {
            JFileChooser chooser = new JFileChooser(new File("./images/"));
            chooser.setDialogTitle("Escolha os arquivos");
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            var res = chooser.showOpenDialog(view);
            if (res == JFileChooser.APPROVE_OPTION) {
                File escolhido = chooser.getSelectedFile();
                if (escolhido.isDirectory()) {
                    System.out.println("Pasta: "
                            + Paths.get(System.getProperty("user.dir")).relativize(escolhido.toPath()).toString());
                } else {
                    new VisualizarImagemPresenter(new Image(
                            Paths.get(System.getProperty("user.dir")).relativize(escolhido.toPath()).toString()),
                            view.getDesktop(),
                            user);
                    System.out.println("Imagem: "
                            + Paths.get(System.getProperty("user.dir")).relativize(escolhido.toPath()).toString());
                }
            }

            /*
            JFileChooser chooser = new JFileChooser(new File("./images/"));
            chooser.setDialogTitle("Selecione a pasta");
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            
             */
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao abrir arquivo! " + e.getMessage());
        }
    }

    public void userDeslogadoLayout() {
        view.getMenuLogout().setVisible(false);
        view.getMenuUpdate().setVisible(false);
        view.getjMenuUsuario().setVisible(false);
        view.getjMenuAdministrador().setVisible(false);
        view.getBtnSolicitacao().setVisible(false);
        view.getjMenuArquivo().setVisible(false);

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
        view.getjMenuArquivo().setVisible(true);

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
        view.getjMenuArquivo().setVisible(true);

    }

    private void updateFooter(boolean isAdmin) {

        if (isAdmin) {
            view.getTxtUser().setText("Administrador - " + user.getName());
        } else {
            view.getTxtUser().setText("Usuário - " + user.getName());
        }

        var not = user.getNotifications().getUnreadNotifications().size();

        view.getBtnNotifications().setText(not + " Notificações");
    }

    private void closeAllTabs() {
        for (JInternalFrame f : view.getDesktop().getAllFrames()) {
            f.dispose();
        }
    }

}
