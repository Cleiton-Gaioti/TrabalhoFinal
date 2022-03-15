package pss.trabalhofinal.bancodeimagens.model;

import pss.trabalhofinal.bancodeimagens.presenter.PrincipalPresenter;

public abstract class LoginState {
    /* ATTRIBUTES */
    private PrincipalPresenter principalPresenter;

    /* CONSTRUCTOR */
    public LoginState(PrincipalPresenter principalPresenter) {
        setPrincipalPresenter(principalPresenter);
    }

    /* METHODS */
    public void logar(UserModel userType) {
        throw new RuntimeException("Erro ao realizar login.");
    }

    public void deslogar() {
        throw new RuntimeException("Erro ao deslogar.");
    }

    public PrincipalPresenter getPrincipalPresenter() {
        return this.principalPresenter;
    }

    private void setPrincipalPresenter(PrincipalPresenter principalPresenter) {
        this.principalPresenter = principalPresenter;
    }

}
