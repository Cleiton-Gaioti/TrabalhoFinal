package pss.trabalhofinal.bancodeimagens.model;

import java.time.LocalDate;

import pss.trabalhofinal.bancodeimagens.collection.NotificationCollection;
import pss.trabalhofinal.bancodeimagens.collection.PermissaoCollection;

public class NormalUser extends UserModel {
    /* ATTRIBUTES */
    private boolean authorized;
    private PermissaoCollection permissoes;

    /* CONSTRUCTORS */
    public NormalUser(int id, String name, String email, String username, String password, LocalDate registrationDate,
            NotificationCollection notifications, boolean authorized, PermissaoCollection permissoes,
            boolean encryptPassword) {
        super(id, name, email, username, password, registrationDate, notifications, encryptPassword);
        setAuthorized(authorized);
        setPermissoes(permissoes);
    }

    public NormalUser(String name, String email, String username, String password, LocalDate registrationDate,
            NotificationCollection notifications, boolean authorized, PermissaoCollection permissoes,
            boolean encryptPassword) {
        this(-1, name, email, username, password, registrationDate, notifications, authorized, permissoes,
                encryptPassword);
    }

    /* GETTERS AND SETTERS */
    public boolean wasAuthorized() {
        return this.authorized;
    }

    private void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public PermissaoCollection getPermissoes() {
        return permissoes;
    }

    private void setPermissoes(PermissaoCollection permissoes) {
        this.permissoes = permissoes;
    }
}
