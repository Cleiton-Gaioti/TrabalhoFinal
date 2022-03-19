package pss.trabalhofinal.bancodeimagens.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

import pss.trabalhofinal.bancodeimagens.collection.UsersCollection;
import pss.trabalhofinal.bancodeimagens.factory.PasswordEncryptor;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObservable;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObserver;
import pss.trabalhofinal.bancodeimagens.view.LoginView;

public class LoginPresenter implements IObservable {
    /* ATTRIBUTES */
    private final List<IObserver> observers;
    private UsersCollection users;
    private final LoginView view;

    /* CONSTRUCTOR */
    public LoginPresenter(JDesktopPane desktop) {
        observers = new ArrayList<>();
        view = new LoginView();

        try {
            users = new UsersCollection();
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
        }

        view.getCheckShowPassword().addActionListener(l -> {
            if (view.getCheckShowPassword().isSelected()) {
                view.getTxtPassword().setEchoChar((char) 0);
            } else {
                view.getTxtPassword().setEchoChar('*');
            }
        });

        view.getBtnLogin().addActionListener(l -> {
            login();
        });

        view.getBtnRegister().addActionListener(l -> {
            if (users.getCountUsers() == 0) {
                new CadastrarUsuarioPresenter(desktop, true, false);
            } else {
                new CadastrarUsuarioPresenter(desktop, false, false);
            }
        });

        view.setLocation((desktop.getWidth() - view.getWidth()) / 2, (desktop.getHeight() - view.getHeight()) / 2);

        desktop.add(view);
        view.setVisible(true);
    }

    private void login() {
        var username = view.getTxtUsername().getText();
        var password = String.valueOf(view.getTxtPassword().getPassword());

        if (username.isBlank() || username.isEmpty()) {

            JOptionPane.showMessageDialog(view, "Informe o nome de usuário.");

        } else if (password.isBlank() || password.isEmpty()) {

            JOptionPane.showMessageDialog(view, "Informe uma senha.");

        } else {

            try {
                var user = users.login(username, PasswordEncryptor.encrypt(password));

                if (user == null) {

                    JOptionPane.showMessageDialog(view, "Credenciais incorretas. Verifique seu email e senha.");

                    view.getTxtPassword().setText("");

                } else {

                    notifyObservers(user);

                    view.dispose();
                }
            } catch (RuntimeException e) {

                JOptionPane.showMessageDialog(view, e.getMessage());

                view.getTxtPassword().setText("");

            }
        }
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
