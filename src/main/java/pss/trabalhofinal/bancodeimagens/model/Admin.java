package pss.trabalhofinal.bancodeimagens.model;

import java.time.LocalDate;

import pss.trabalhofinal.bancodeimagens.collection.NotificationCollection;

public class Admin extends UserModel {
    /* CONSTRUCTORS */
    public Admin(int id, String name, String email, String username, String password, LocalDate registrationDate,
            NotificationCollection notifications, boolean encryptPassword) {
        super(id, name, email, username, password, registrationDate, notifications, encryptPassword);
    }

    public Admin(int id, String name, String email, String username, String password, LocalDate registrationDate,
            boolean encryptPassword) {
        this(id, name, email, username, password, registrationDate, new NotificationCollection(), encryptPassword);
    }

    public Admin(String name, String email, String username, String password, LocalDate registrationDate,
            boolean encryptPassword) {
        this(-1, name, email, username, password, registrationDate, encryptPassword);
    }
}
