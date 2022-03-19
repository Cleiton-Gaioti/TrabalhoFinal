package pss.trabalhofinal.bancodeimagens.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

import pss.trabalhofinal.bancodeimagens.collection.NotificationCollection;
import pss.trabalhofinal.bancodeimagens.collection.PermissaoCollection;
import pss.trabalhofinal.bancodeimagens.collection.UsersCollection;
import pss.trabalhofinal.bancodeimagens.model.Admin;
import pss.trabalhofinal.bancodeimagens.model.NormalUser;
import pss.trabalhofinal.bancodeimagens.model.UserModel;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObservable;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObserver;
import pss.trabalhofinal.bancodeimagens.view.CadastrarUsuarioView;

public class EditByAdminPresenter implements IObservable {
    /* ATTRIBUTES */
    private final CadastrarUsuarioView view;
    private final List<IObserver> observers;
    private UsersCollection users;

    /* CONSTRUCTOR */
    public EditByAdminPresenter(JDesktopPane desktop, Admin admin, UserModel user) {
        view = new CadastrarUsuarioView();
        observers = new ArrayList<>();

        try {

            users = new UsersCollection();

            layoutVisualizar(user, admin);

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
    private void save(UserModel user, Admin administrador) {
        var name = view.getTxtName().getText();
        var email = view.getTxtEmail().getText();
        var username = view.getTxtUsername().getText();
        var admin = view.getCheckAdministrador().isSelected();

        try {
            UserModel newer = null;

            if (admin) {
                newer = new Admin(user.getId(), name, email, username, user.getPassword(), user.getRegistrationDate(),
                        new NotificationCollection(), false);
            } else {
                newer = new NormalUser(user.getId(), name, email, username, user.getPassword(),
                        user.getRegistrationDate(), new NotificationCollection(), true, new PermissaoCollection(),
                        false);
            }

            users.update(newer);

            JOptionPane.showMessageDialog(view, "Dados editados com sucesso!");

            notifyObservers(null);
            layoutVisualizar(newer, administrador);

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

    private void clearActionListeners(JButton btn) {
        for (var al : btn.getActionListeners()) {
            btn.removeActionListener(al);
        }
    }

    private void enableFields(boolean enabled) {
        view.getTxtName().setEditable(enabled);
        view.getTxtEmail().setEditable(enabled);
        view.getTxtUsername().setEditable(enabled);
        view.getTxtPassword().setEditable(false);
        view.getCheckShowPassword().setEnabled(false);
        view.getCheckAdministrador().setEnabled(enabled);
    }

    private void layoutUpdate(UserModel user, Admin admin) {
        if (admin.equals(user)) {
            System.out.println("1");
            JOptionPane.showMessageDialog(view,
                    "Para editar suas informações acesse o menu Usuário -> Atualizar Cadastro.");

        } else {

            view.getBtnRegister().setText("Salvar");
            view.getBtnClose().setText("Cancelar");

            clearActionListeners(view.getBtnClose());
            view.getBtnClose().addActionListener(l -> {
                layoutVisualizar(user, admin);
                preencherCampos(user);
            });

            clearActionListeners(view.getBtnRegister());
            view.getBtnRegister().addActionListener(l -> {
                save(user, admin);
            });

            enableFields(true);
        }
    }

    private void layoutVisualizar(UserModel user, Admin admin) {
        view.getBtnRegister().setText("Editar");
        view.getBtnClose().setText("Fechar");

        clearActionListeners(view.getBtnClose());
        view.getBtnClose().addActionListener(l -> {
            view.dispose();
        });

        clearActionListeners(view.getBtnRegister());
        view.getBtnRegister().addActionListener(l -> {
            layoutUpdate(user, admin);
        });

        preencherCampos(user);

        enableFields(false);
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
