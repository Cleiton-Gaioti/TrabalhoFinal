package pss.trabalhofinal.bancodeimagens.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pss.trabalhofinal.bancodeimagens.dao.UserDAO;
import pss.trabalhofinal.bancodeimagens.model.Admin;
import pss.trabalhofinal.bancodeimagens.model.NormalUser;
import pss.trabalhofinal.bancodeimagens.model.UserModel;

public class UsersCollection {
    /* ATTRIBUTES */
    private List<UserModel> users;
    private int countUsers;

    /* CONSTRUCTORS */
    public UsersCollection(List<UserModel> users) {
        setUsers(users);
        setCountUsers(users.size());
    }

    public UsersCollection() {
        this(new ArrayList<>());
    }

    /* METHODS */
    public void add(UserModel user) {
        /*
         * Caso o novo usuário seja válido, ele é inserido na lista de usuários e no
         * banco.
         */

        if (user == null) {

            throw new RuntimeException("Usuário nulo.");

        } else if (users.contains(user)) {

            throw new RuntimeException("Usuário já cadastrado.");

        } else if (!UserDAO.verifyUsername(user.getUsername())) {

            throw new RuntimeException("Nome de usuário já em uso.");

        } else if (!UserDAO.verifyEmail(user.getEmail())) {

            throw new RuntimeException("Email já em uso.");

        } else {

            if (user.getId() == -1) {
                UserDAO.insertUser(user);
            } else {
                UserDAO.insertUser(user.getId(), user);
            }

            setUsers(UserDAO.getAllUsers());
            setCountUsers(users.size());
        }
    }

    public void remove(UserModel user) {
        /*
         * Remove um usuário da lista, e caso esse usuário seja o único administrador
         * cadastrado,chama a função que nomeia o usuário com a data de cadastro mais
         * antiga do banco como novo administrador.
         */

        if (user == null) {

            throw new RuntimeException("Usuário nulo.");

        } else if (!users.contains(user)) {

            throw new RuntimeException("Usuário não encontrado.");

        } else {

            UserDAO.removeUser(user.getId());
            appointNewAdmin(user);
            setUsers(UserDAO.getAllUsers());
            setCountUsers(users.size());
        }
    }

    public void update(UserModel older, UserModel newer) {
        /*
         * Valida os novos dados do usuário e caso seja válido, remove o antigo registro
         * do banco e insere movamente com as novas informações do usuário e mesmo id.
         */

        if (older == null || newer == null) {

            throw new RuntimeException("Usuário nulo.");

        } else if (!users.contains(older)) {

            throw new RuntimeException("Usuário não encontrado.");

        } else {
            newer.setId(older.getId());
            remove(older);
            add(newer);
        }
    }

    private void appointNewAdmin(UserModel user) {
        /* Nomeia um novo administrador */

        if (countAdmins() == 1) {
            if (Admin.class.isInstance(user)) {
                UserDAO.appointNewAdmin();
            }
        }
    }

    private int countAdmins() {
        var count = 0;

        for (UserModel u : users) {
            if (Admin.class.isInstance(u)) {
                count++;
            }
        }

        return count;
    }

    /* GETTERS AND SETTERS */
    public int getCountUsers() {
        return this.countUsers;
    }

    private void setCountUsers(int count) {
        this.countUsers = count;
    }

    public List<UserModel> getAllUsers() {
        return Collections.unmodifiableList(users);
    }

    public List<Admin> getAdmins() {

        List<Admin> admins = new ArrayList<>();

        users.forEach(u -> {
            if (Admin.class.isInstance(u)) {
                admins.add((Admin) u);
            }
        });

        return Collections.unmodifiableList(admins);
    }

    public List<NormalUser> getNormalUsers() {

        List<NormalUser> nUsers = new ArrayList<>();

        users.forEach(u -> {
            if (NormalUser.class.isInstance(u)) {
                nUsers.add((NormalUser) u);
            }
        });

        return Collections.unmodifiableList(nUsers);
    }

    private void setUsers(List<UserModel> users) {
        if (users == null) {
            throw new RuntimeException("Lista de usuários nula.");
        } else {
            this.users = users;
        }
    }
}
