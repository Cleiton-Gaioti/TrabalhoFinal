package pss.trabalhofinal.bancodeimagens.presenter;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import pss.trabalhofinal.bancodeimagens.model.Image;
import pss.trabalhofinal.bancodeimagens.model.UserModel;
import pss.trabalhofinal.bancodeimagens.view.NaoAutorizadoView;

public class NaoAutorizadoPresenter {

    private NaoAutorizadoView view;
    private UserModel user;
    private Image imagem;

    public NaoAutorizadoPresenter(UserModel user, JDesktopPane desktop, Image imagem) {
        view = new NaoAutorizadoView();
        this.user = user;
        this.imagem = imagem;
        view.setTitle(user.getUsername() + " não autorizado!");

        loadIcon();

        view.getBtnFechar().addActionListener(l -> {
            view.dispose();
        });

        desktop.add(view);
        view.setVisible(true);

    }

    private void loadIcon() {
        try {
            String iconPath = imagem.getPath().replace("images/", "images/.thumbnails/");
            Image icon = new Image(iconPath);
            view.getLblIcone().setIcon(new ImageIcon(icon.getImagem()));
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(view, "Erro ao carregar Icone: " + e.getMessage());
        }
    }

}
