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
    private int permissions; /* 0 - only read, 1 - read and manipulate, 3 - read, manipulate and share */

    /* CONSTRUCTORS */
    public UserModel(int id, String name, String email, String username, String password, LocalDate registrationDate,
            int permission, boolean encryptPassword) {
        setId(id);
        setName(name);
        setEmail(email);
        setUsername(username);
        setPassword(password, encryptPassword);
        setRegistrationDate(registrationDate);
        setPermissions(permission);
    }

    public UserModel(String name, String email, String username, String password, LocalDate registrationDate,
            int permission, boolean encryptPassword) {
        this(-1, name, email, username, password, registrationDate, permission, encryptPassword);
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

        } else if (username.contains(" ") || !username.matches("^[A-z]{1,}[A-z0-9|-]*")) {

            throw new RuntimeException(
                    "O nome de usuário não deve conter espaços em branco e deve começar obrigatoriamente por uma letra.");
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

    public int getPermissions() {
        return this.permissions;
    }

    public String getPermissionsDescription() {
        if (permissions == 0) {
            return "Apenas Visualizar";
        } else if (permissions == 1) {
            return "Visualizar e Manipular a Imagem";
        } else {
            return "Visualizar, Manipular e Compartilhar a Imagem";
        }
    }

    private void setPermissions(int permission) {
        if (permission < 0 || permission > 2) {
            throw new RuntimeException("Permissão inválida");
        } else {
            this.permissions = permission;
        }
    }
}
