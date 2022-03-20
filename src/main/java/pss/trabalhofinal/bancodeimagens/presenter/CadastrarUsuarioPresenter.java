package pss.trabalhofinal.bancodeimagens.presenter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

import pss.trabalhofinal.bancodeimagens.collection.UsersCollection;
import pss.trabalhofinal.bancodeimagens.model.Admin;
import pss.trabalhofinal.bancodeimagens.model.NormalUser;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObservable;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObserver;
import pss.trabalhofinal.bancodeimagens.view.CadastrarUsuarioView;

public class CadastrarUsuarioPresenter implements IObservable {
    /* ATTRIBUTES */
    private final CadastrarUsuarioView view;
    private final List<IObserver> observers;
    private UsersCollection users;

    /* CONSTRUCTOR */
    public CadastrarUsuarioPresenter(JDesktopPane desktop, boolean firstUser, boolean isAdmin) {
        view = new CadastrarUsuarioView();
        observers = new ArrayList<>();

        try {

            users = new UsersCollection();

            view.getBtnClose().addActionListener(l -> {
                view.dispose();
            });

            view.getBtnRegister().addActionListener(l -> {
                register(isAdmin);
            });

            view.getCheckShowPassword().addActionListener(l -> {
                if (view.getCheckShowPassword().isSelected()) {
                    view.getTxtPassword().setEchoChar((char) 0);
                } else {
                    view.getTxtPassword().setEchoChar('*');
                }
            });

            /*
             * Verifica se é o primeiro usuário do sistema ou se esta sendo cadastrado por
             * um administrador.
             */

            if (firstUser) {
                view.getCheckAdministrador().setSelected(true);
                view.getCheckAdministrador().setEnabled(false);

            } else if (!isAdmin) {
                view.getCheckAdministrador().setVisible(false);
            }

            view.setLocation((desktop.getWidth() - view.getWidth()) / 2, (desktop.getHeight() - view.getHeight()) / 2);

            desktop.add(view);
            view.setVisible(true);

        } catch (RuntimeException e) {

            JOptionPane.showMessageDialog(view, e.getMessage());

        }

    }

    /* METHODS */
    private void register(boolean isAdmin) {
        var name = view.getTxtName().getText();
        var email = view.getTxtEmail().getText();
        var username = view.getTxtUsername().getText();
        var password = String.valueOf(view.getTxtPassword().getPassword());
        var admin = view.getCheckAdministrador().isSelected();

        try {
            if (admin) {
                users.add(new Admin(name, email, username, password, LocalDate.now(), true), true);
            } else {
                users.add(new NormalUser(name, email, username, password, LocalDate.now(), isAdmin, true), isAdmin);
            }

            notifyObservers(null);

            view.dispose();
        } catch (RuntimeException e) {

            JOptionPane.showMessageDialog(view, e.getMessage());

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
