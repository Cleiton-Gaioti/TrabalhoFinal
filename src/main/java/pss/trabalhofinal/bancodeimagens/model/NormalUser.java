package pss.trabalhofinal.bancodeimagens.model;

import java.time.LocalDate;

public class NormalUser extends UserModel {
    /* ATTRIBUTES */
    private boolean authorized;

    /* CONSTRUCTORS */
    public NormalUser(int id, String name, String email, String username, String password, LocalDate registrationDate,
            boolean authorized, boolean encryptPassword) {
        super(id, name, email, username, password, registrationDate, encryptPassword);
        setAuthorized(authorized);
    }

    public NormalUser(String name, String email, String username, String password, LocalDate registrationDate,
            boolean authorized, boolean encryptPassword) {
        this(-1, name, email, username, password, registrationDate, authorized, encryptPassword);
    }

    /* GETTERS AND SETTERS */
    public boolean wasAuthorized() {
        return this.authorized;
    }

    private void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

}
