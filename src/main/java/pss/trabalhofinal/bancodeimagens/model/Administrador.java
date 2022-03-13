package pss.trabalhofinal.bancodeimagens.model;

import java.time.LocalDateTime;

public class Administrador extends UserModel {
    /* CONSTRUCTORS */
    public Administrador(int id, String name, String email, String username, String password,
            LocalDateTime registrationDate, boolean encryptPassword) {
        super(id, name, email, username, password, registrationDate, encryptPassword);
    }

    public Administrador(String name, String email, String username, String password,
            LocalDateTime registrationDate, boolean encryptPassword) {
        super(-1, name, email, username, password, registrationDate, encryptPassword);
    }
}
