package pss.trabalhofinal.bancodeimagens.presenter;

import pss.trabalhofinal.bancodeimagens.model.Admin;
import pss.trabalhofinal.bancodeimagens.model.UserModel;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObserver;
import pss.trabalhofinal.bancodeimagens.view.PrincipalView;

public class PrincipalPresenter implements IObserver {
    /* ATTRIBUTES */
    private final PrincipalView view;
    private UserModel user;

    /* CONSTRUCTOR */
    public PrincipalPresenter() {
        view = new PrincipalView();
        user = null;

        new LoginPresenter(view.getDesktop()).registerObserver(this);

        view.setVisible(true);
    }

    /* METHODS */
    @Override
    public void update(Object obj) {
        user = (UserModel) obj;

        var isAdmin = Admin.class.isInstance(obj);

        updateFooter(isAdmin);

        if (isAdmin) {
            // TODO: SETTAR ESTADO ADMIN LOGADO
        } else {
            // TODO: SETTAR ESTADO USUARIO NORMAL LOGADO
        }
    }

    private void updateFooter(boolean isAdmin) {

        if (isAdmin) {
            view.getTxtUser().setText("Administrador - " + user.getName());
        } else {
            view.getTxtUser().setText("Usuário - " + user.getName());
        }
    }
}
