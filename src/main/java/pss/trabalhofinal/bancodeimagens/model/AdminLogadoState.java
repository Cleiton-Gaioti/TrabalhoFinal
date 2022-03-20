package pss.trabalhofinal.bancodeimagens.model;

import pss.trabalhofinal.bancodeimagens.presenter.PrincipalPresenter;

public class AdminLogadoState extends LoginState {

    /* CONSTRUCTOR */
    public AdminLogadoState(PrincipalPresenter principalPresenter) {
        super(principalPresenter);
        principalPresenter.adminLayout();
    }

    /* METHODS */
    @Override
    public void deslogar() {
        getPrincipalPresenter().userDeslogadoLayout();
    }
}
