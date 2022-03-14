package pss.trabalhofinal.bancodeimagens.model;

import java.time.LocalDate;

public class Admin extends UserModel {
    /* CONSTRUCTORS */
    public Admin(int id, String name, String email, String username, String password, LocalDate registrationDate,
            boolean encryptPassword) {
        super(id, name, email, username, password, registrationDate, encryptPassword);
    }

    public Admin(String name, String email, String username, String password, LocalDate registrationDate,
            boolean encryptPassword) {
        super(-1, name, email, username, password, registrationDate, encryptPassword);
    }
}
