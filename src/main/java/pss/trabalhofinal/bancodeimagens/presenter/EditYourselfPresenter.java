package pss.trabalhofinal.bancodeimagens.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

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

        try {

            preencherCampos(user);

            view.getBtnRegister().setText("Salvar");
            view.getCheckAdministrador().setVisible(false);
            view.getLblPermissions().setVisible(false);
            view.getBoxPermissions().setVisible(false);

            view.getBtnClose().addActionListener(l -> {
                view.dispose();
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
    private void save(UserModel user) {
        var name = view.getTxtName().getText();
        var email = view.getTxtEmail().getText();
        var username = view.getTxtUsername().getText();
        var password = String.valueOf(view.getTxtPassword().getPassword());

        try {
            UserModel newer = null;

            if (Admin.class.isInstance(user)) {
                newer = new Admin(user.getId(), name, email, username, password, user.getRegistrationDate(), true);
            } else {
                newer = new NormalUser(user.getId(), name, email, username, password, user.getRegistrationDate(), true,
                        user.getPermissions(), true);
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
        view.getBoxPermissions().setSelectedIndex(user.getPermissions());
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