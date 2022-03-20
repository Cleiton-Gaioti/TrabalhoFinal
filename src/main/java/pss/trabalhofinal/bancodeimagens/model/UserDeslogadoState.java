package pss.trabalhofinal.bancodeimagens.model;

import pss.trabalhofinal.bancodeimagens.presenter.PrincipalPresenter;

public class UserDeslogadoState extends LoginState {

    /* CONSTRUCTOR */
    public UserDeslogadoState(PrincipalPresenter principalPresenter) {
        super(principalPresenter);
        principalPresenter.userDeslogadoLayout();
    }

    /* METHODS */
    @Override
    public void logar(UserModel userType) {
        if (Admin.class.isInstance(userType)) {
            getPrincipalPresenter().adminLayout();
        } else {
            getPrincipalPresenter().userLogadoLayout();
        }
    }
}
