package pss.trabalhofinal.bancodeimagens.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import pss.trabalhofinal.bancodeimagens.factory.PasswordEncryptor;

public abstract class UserModel {

    /* ATTRIBUTES */
    public int id;
    private String name;
    private String email;
    private String username;
    private String password;
    private LocalDate registrationDate;

    /* CONSTRUCTORS */
    public UserModel(int id, String name, String email, String username, String password,
            LocalDate registrationDate, boolean encryptPassword) {
        setId(id);
        setName(name);
        setEmail(email);
        setUsername(username);
        setPassword(password, encryptPassword);
        setRegistrationDate(registrationDate);
    }

    public UserModel(String name, String email, String username, String password, LocalDate registrationDate,
            boolean encryptPassword) {
        this(-1, name, email, username, password, registrationDate, encryptPassword);
    }

    /* METHODS */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserModel)) {
            return false;
        }
        UserModel userModel = (UserModel) o;
        return Objects.equals(name, userModel.name) && Objects.equals(email, userModel.email)
                && Objects.equals(username, userModel.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, username);
    }

    /* GETTERS AND SETTERS */
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    private void setName(String name) {
        if (name == null) {
            throw new RuntimeException("Nome nulo.");
        } else if (name.isEmpty() || name.isBlank()) {
            throw new RuntimeException("Nome inválido.");
        } else {
            this.name = name;
        }
    }

    public String getEmail() {
        return this.email;
    }

    private void setEmail(String email) {
        if (email == null) {

            throw new RuntimeException("Email nulo.");

        } else if (email.isBlank() || email.isEmpty()) {

            throw new RuntimeException("Email inválido.");

        } else {

            try {
                InternetAddress emailAddr = new InternetAddress(email);
                emailAddr.validate();

                this.email = email;
            } catch (AddressException e) {

                throw new RuntimeException("Email inválido.");
            }
        }
    }

    public String getUsername() {
        return this.username;
    }

    private void setUsername(String username) {
        if (username == null) {

            throw new RuntimeException("Nome de usuário nulo.");

        } else if (username.isBlank() || username.isEmpty()) {

            throw new RuntimeException("Nome de usuário inválido.");

        } else {
            this.username = username;
        }
    }

    public String getPassword() {
        return this.password;
    }

    private void setPassword(String password, boolean encrypt) {
        if (password == null) {

            throw new RuntimeException("Senha nula.");

        } else if (password.isBlank() || password.isEmpty()) {

            throw new RuntimeException("Senha inválida.");

        } else {

            if (encrypt) {
                this.password = PasswordEncryptor.encrypt(password);
            } else {
                this.password = password;
            }
        }

    }

    public LocalDate getRegistrationDate() {
        return this.registrationDate;
    }

    private void setRegistrationDate(LocalDate registrationDate) {
        if (registrationDate == null) {

            throw new RuntimeException("Data de registro nula.");

        } else {

            this.registrationDate = registrationDate;
        }
    }

}
