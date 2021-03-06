package pss.trabalhofinal.bancodeimagens.presenter;

import java.io.File;
import java.time.LocalDate;

import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import pss.trabalhofinal.bancodeimagens.utils.ThumbnailFileChooser;
import pss.trabalhofinal.bancodeimagens.dao.LixeiraDAO;
import pss.trabalhofinal.bancodeimagens.dao.PermissaoDAO;
import pss.trabalhofinal.bancodeimagens.model.Admin;
import pss.trabalhofinal.bancodeimagens.model.AdminLogadoState;
import pss.trabalhofinal.bancodeimagens.model.Image;
import pss.trabalhofinal.bancodeimagens.model.Lixeira;
import pss.trabalhofinal.bancodeimagens.model.UserDeslogadoState;
import pss.trabalhofinal.bancodeimagens.model.UserLogadoState;
import pss.trabalhofinal.bancodeimagens.model.UserModel;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObserver;
import pss.trabalhofinal.bancodeimagens.utils.RelativePath;
import pss.trabalhofinal.bancodeimagens.view.PrincipalView;

public class PrincipalPresenter implements IObserver {

    /* ATTRIBUTES */
    private final PermissaoDAO permissaoDAO;
    private final LixeiraDAO lixeiraDAO;
    private final PrincipalView view;
    private UserModel user;

    /* CONSTRUCTOR */
    public PrincipalPresenter() {
        permissaoDAO = new PermissaoDAO();
        lixeiraDAO = new LixeiraDAO();
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

        view.getMenuDesfazerExlusoes().addActionListener(l -> {
            new ExclusoesPresenter(user, view.getDesktop());
        });

        view.getMenuExcluirMultiplos().addActionListener(l -> {
            excluirMultiplos();
        });

        view.setSize(1280, 720);

        new UserDeslogadoState(this);

        view.setLocationRelativeTo(view.getParent());
        view.setVisible(true);
    }

    /* METHODS */
    @Override
    public void update(Object obj) {

        if (obj == null) {

            closeAllTabs();
            updateFooter(null);
            new UserDeslogadoState(this);

        } else {
            user = (UserModel) obj;

            var isAdmin = Admin.class.isInstance(obj);

            if (isAdmin) {
                new AdminLogadoState(this);
            } else {
                new UserLogadoState(this);
            }

            updateFooter(isAdmin);
        }

    }

    private void login() {
        new LoginPresenter(view.getDesktop()).registerObserver(this);
    }

    private void logout(boolean confirmar) {

        var resposta = 0;

        if (confirmar) {
            String[] options = { "Sim", "N??o" };

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
            new UserDeslogadoState(this);
            user = null;

        }
    }

    private void excluirMultiplos() {
        /*
         * Apenas usu??rios administradores poder??o excluir m??ltiplas imagens
         * pois um usu??rio n??o administrador pode tentar excluir imagens que n??o tem
         * acesso
         */
        try {
            ThumbnailFileChooser chooser = new ThumbnailFileChooser(new File("./images/"));
            //JFileChooser chooser = new JFileChooser(new File("./images/"));
            chooser.setDialogTitle("Excluir arquivos!");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setMultiSelectionEnabled(true);
            chooser.setFileFilter(new FileNameExtensionFilter("JPG images", "jpg"));

            int res = chooser.showOpenDialog(view);
            if (res == JFileChooser.APPROVE_OPTION) {
                File escolhidos[] = chooser.getSelectedFiles();

                String[] options = { "Sim", "N??o" };

                int resposta = JOptionPane.showOptionDialog(
                        view,
                        "Deseja excluir " + escolhidos.length + " imagens?",
                        "Apagar imagem",
                        JOptionPane.YES_OPTION,
                        JOptionPane.NO_OPTION,
                        null,
                        options,
                        options[1]);

                if (resposta == 0) {
                    try {
                        for (File f : escolhidos) {

                            var fileName = f.getName();
                            var path = RelativePath.toRelativePath(f);
                            f.renameTo(new File("./images/.lixeira/" + fileName));

                            lixeiraDAO.insert(new Lixeira(user.getId(), path, fileName, LocalDate.now()));

                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(view, "Erro ao excluir multiplos arquivos: " + e.getMessage());
                    }
                }

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao excluir multiplos arquivos: " + e.getMessage());
        }
    }

    private void abrirArquivo() {
        try {
            ThumbnailFileChooser chooser = new ThumbnailFileChooser(new File("./images/"));
            //JFileChooser chooser = new JFileChooser(new File("./images/"));
            chooser.setDialogTitle("Escolha os arquivos");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setFileFilter(new FileNameExtensionFilter("JPG images", "jpg"));
            var res = chooser.showOpenDialog(view);
            if (res == JFileChooser.APPROVE_OPTION) {
                File escolhido = chooser.getSelectedFile();

                if (Admin.class.isInstance(user)) {
                    new VisualizarImagemPresenter(new Image(RelativePath.toRelativePath(escolhido)), view.getDesktop(),
                            user);
                } else {

                    if (permissaoDAO.canVisualizar(user.getId(), RelativePath.toRelativePath(escolhido))
                            || permissaoDAO.isAuthorizedFolder(user.getId())) {
                        new VisualizarImagemPresenter(new Image(RelativePath.toRelativePath(escolhido)),
                                view.getDesktop(), user);
                    } else {
                        new NaoAutorizadoPresenter(user, view.getDesktop(),
                                new Image(RelativePath.toRelativePath(escolhido)), "Visualizar");
                    }
                    /*
                     * var auth = false;
                     * for (Permissao p : permissoes) {
                     * if (p.getPath().startsWith(RelativePath.toRelativePath(escolhido))) {
                     * auth = true;
                     * }
                     * }
                     * if (auth) {
                     * new VisualizarImagemPresenter(new
                     * Image(RelativePath.toRelativePath(escolhido)),
                     * view.getDesktop(), user);
                     * } else {
                     * new NaoAutorizadoPresenter(user, view.getDesktop(),
                     * new Image(RelativePath.toRelativePath(escolhido)));
                     * }
                     */ {

                    }
                }
            }

            /*
             * JFileChooser chooser = new JFileChooser(new File("./images/"));
             * chooser.setDialogTitle("Selecione a pasta");
             * chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
             * 
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

        view.getTxtUser().setText("");
        view.getBtnNotifications().setText("0 notifica????es");

        login();
    }

    public void userLogadoLayout() {
        /*
         * Caso o usu??rio logado seja um usu??rio normal, adapta a tela principal para o
         * modo de usu??rio
         */
        view.getjMenuAdministrador().setVisible(false);
        view.getjMenuUsuario().setVisible(true);
        view.getMenuLogin().setVisible(false);
        view.getMenuLogout().setVisible(true);
        view.getMenuUpdate().setVisible(true);
        view.getBtnSolicitacao().setVisible(false);
        view.getjMenuArquivo().setVisible(true);
        view.getMenuExcluirMultiplos().setVisible(false);

    }

    public void adminLayout() {
        /*
         * Caso o usu??rio logado seja um administrador, adapta a tela principal para o
         * modo de administrador
         */
        view.getjMenuAdministrador().setVisible(true);
        view.getjMenuUsuario().setVisible(true);
        view.getMenuLogin().setVisible(false);
        view.getMenuLogout().setVisible(true);
        view.getMenuUpdate().setVisible(true);
        view.getBtnSolicitacao().setVisible(true);
        view.getjMenuArquivo().setVisible(true);
        view.getMenuExcluirMultiplos().setVisible(true);

    }

    private void updateFooter(Boolean isAdmin) {
        int not = 0;

        if (isAdmin == null) {

            view.getTxtUser().setText("");
        } else {

            if (isAdmin) {
                view.getTxtUser().setText("Administrador - " + user.getName());
            } else {
                view.getTxtUser().setText("Usu??rio - " + user.getName());
            }

            not = user.getNotifications().getUnreadNotifications().size();

        }

        view.getBtnNotifications().setText(not + " Notifica????es");
    }

    private void closeAllTabs() {
        for (JInternalFrame f : view.getDesktop().getAllFrames()) {
            f.dispose();
        }
    }

}
