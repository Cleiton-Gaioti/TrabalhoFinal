package pss.trabalhofinal.bancodeimagens.presenter;

import java.time.LocalDate;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

import pss.trabalhofinal.bancodeimagens.collection.UsersCollection;
import pss.trabalhofinal.bancodeimagens.dao.NotificationDAO;
import pss.trabalhofinal.bancodeimagens.model.Image;
import pss.trabalhofinal.bancodeimagens.model.Notification;
import pss.trabalhofinal.bancodeimagens.model.UserModel;
import pss.trabalhofinal.bancodeimagens.view.NaoAutorizadoView;

public class NaoAutorizadoPresenter {

    private final NotificationDAO notificationDAO;
    private NaoAutorizadoView view;

    public NaoAutorizadoPresenter(UserModel user, JDesktopPane desktop, Image imagem, String msg) {
        UsersCollection users = new UsersCollection();
        UserModel admin = users.getAdmins().get(0);
        notificationDAO = new NotificationDAO();
        view = new NaoAutorizadoView();

        view.setTitle(user.getUsername() + " não autorizado!");
        view.getLblAcessoNaoAutorizado().setText(msg.substring(0, 1).toUpperCase() + msg.substring(1).toLowerCase() + " não autorizado!");
        view.getBtnSolicitar().setText("Solicitar " + admin.getUsername());

        loadIcon(imagem);

        view.getBtnFechar().addActionListener(l -> {
            view.dispose();
        });

        view.getBtnSolicitar().addActionListener(l -> {
            solicitar(admin, user, imagem, msg.toLowerCase());
        });

        desktop.add(view);
        view.setVisible(true);

    }

    private void solicitar(UserModel admin, UserModel user, Image imagem, String acao) {
        try {
            notificationDAO.insert(new Notification(user.getId(), admin.getId(),
                    "USER:" + user.getUsername() + ",IMAGEM:" + imagem.getPath() + ",AÇÃO:" + acao,
                    false, LocalDate.now()));
            JOptionPane.showMessageDialog(view, "Solicitação enviada!");
            view.dispose();
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(view, "Erro ao enviar solicitação: " + e.getMessage());
        }
    }

    private void loadIcon(Image imagem) {
        try {
            String iconPath = imagem.getPath().replace("images/", "images/.thumbnails/");
            Image icon = new Image(iconPath);
            view.getLblIcone().setIcon(new ImageIcon(icon.getImagem()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao carregar Icone: " + e.getMessage());
        }
    }

}
