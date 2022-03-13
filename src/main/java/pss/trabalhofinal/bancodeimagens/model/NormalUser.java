package pss.trabalhofinal.bancodeimagens.model;

import java.time.LocalDateTime;

public class NormalUser extends UserModel {
    /* ATTRIBUTES */
    private boolean authorized;

    /* CONSTRUCTORS */
    public NormalUser(int id, String name, String email, String username, String password,
            LocalDateTime registrationDate, boolean encryptPassword) {
        super(id, name, email, username, password, registrationDate, encryptPassword);
        setAuthorized(authorized);
    }

    public NormalUser(String name, String email, String username, String password, LocalDateTime registrationDate,
            boolean encryptPassword) {
        this(-1, name, email, username, password, registrationDate, encryptPassword);
    }

    /* GETTERS AND SETTERS */
    public boolean wasAuthorized() {
        return this.authorized;
    }

    private void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

}
