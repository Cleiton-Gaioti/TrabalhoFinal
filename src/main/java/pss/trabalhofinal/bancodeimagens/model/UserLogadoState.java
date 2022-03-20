package pss.trabalhofinal.bancodeimagens.model;

import pss.trabalhofinal.bancodeimagens.presenter.PrincipalPresenter;

public class UserLogadoState extends LoginState {

    /* CONSTRUCTOR */
    public UserLogadoState(PrincipalPresenter principalPresenter) {
        super(principalPresenter);
        principalPresenter.userLogadoLayout();
    }

    /* METHODS */
    @Override
    public void deslogar() {
        getPrincipalPresenter().userDeslogadoLayout();
    }
}
