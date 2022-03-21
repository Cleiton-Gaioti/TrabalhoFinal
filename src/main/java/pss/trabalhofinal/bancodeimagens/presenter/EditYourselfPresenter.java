package pss.trabalhofinal.bancodeimagens.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

import pss.trabalhofinal.bancodeimagens.collection.NotificationCollection;
import pss.trabalhofinal.bancodeimagens.collection.PermissaoCollection;
import pss.trabalhofinal.bancodeimagens.collection.UsersCollection;
import pss.trabalhofinal.bancodeimagens.dao.UserDAO;
import pss.trabalhofinal.bancodeimagens.model.Admin;
import pss.trabalhofinal.bancodeimagens.model.NormalUser;
import pss.trabalhofinal.bancodeimagens.model.UserModel;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObservable;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObserver;
import pss.trabalhofinal.bancodeimagens.view.CadastrarUsuarioView;

public class EditYourselfPresenter implements IObservable {
    /* ATTRIBUTES */
    private final CadastrarUsuarioView view;
    private final List<IObserver> observers;

    /* CONSTRUCTOR */
    public EditYourselfPresenter(JDesktopPane desktop, UserModel user) {
        view = new CadastrarUsuarioView();
        observers = new ArrayList<>();

        var users = new UsersCollection();

        try {

            preencherCampos(user);

            view.getBtnRegister().setText("Salvar");
            view.getCheckAdministrador().setVisible(false);

            view.getBtnClose().addActionListener(l -> {
                view.dispose();
            });

            view.getBtnExcluir().addActionListener(l -> {
                excluir(user, users);
            });

            view.getBtnRegister().addActionListener(l -> {
                save(user);
            });

            view.getCheckShowPassword().addActionListener(l -> {
                if (view.getCheckShowPassword().isSelected()) {
                    view.getTxtPassword().setEchoChar((char) 0);
                } else {
                    view.getTxtPassword().setEchoChar('*');
                }
            });

            view.setLocation((desktop.getWidth() - view.getWidth()) / 2, (desktop.getHeight() - view.getHeight()) / 2);

            desktop.add(view);
            view.setVisible(true);

        } catch (RuntimeException e) {

            JOptionPane.showMessageDialog(view, e.getMessage());

        }

    }

    /* METHODS */
    private void excluir(UserModel user, UsersCollection users) {
        String[] options = { "Sim", "Não" };

        int resposta = JOptionPane.showOptionDialog(
                view,
                "Tem certeza que deseja deletar sua conta?",
                "Deletar conta",
                JOptionPane.YES_OPTION,
                JOptionPane.NO_OPTION,
                null,
                options,
                options[1]);

        if (resposta == 0) {

            try {
                users.remove(user);
                notifyObservers(null);
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(view, e.getMessage());

            }
        }
    }

    private void save(UserModel user) {
        var name = view.getTxtName().getText();
        var email = view.getTxtEmail().getText();
        var username = view.getTxtUsername().getText();
        var password = String.valueOf(view.getTxtPassword().getPassword());

        try {
            UserModel newer = null;

            if (Admin.class.isInstance(user)) {
                newer = new Admin(user.getId(), name, email, username, password, user.getRegistrationDate(),
                        new NotificationCollection(), true);
            } else {
                newer = new NormalUser(user.getId(), name, email, username, password, user.getRegistrationDate(),
                        new NotificationCollection(), true, new PermissaoCollection(), true);
            }

            UserDAO.update(newer);

            JOptionPane.showMessageDialog(view, "Dados editados com sucesso!");

            notifyObservers(newer);

            view.dispose();
        } catch (RuntimeException e) {

            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(view, e.getMessage());

        }
    }

    private void preencherCampos(UserModel user) {
        view.getTxtName().setText(user.getName());
        view.getTxtEmail().setText(user.getEmail());
        view.getTxtUsername().setText(user.getUsername());
        view.getCheckAdministrador().setSelected(Admin.class.isInstance(user));
    }

    @Override
    public void registerObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Object obj) {
        observers.forEach(o -> {
            o.update(obj);
        });
    }

}