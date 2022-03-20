package pss.trabalhofinal.bancodeimagens.model;

import java.time.LocalDate;

public class Permissao {

    /* ATTRIBUTES */
    private int id;
    private int idUser;
    private int idAdminGranted;
    private String tipo;
    private String path;
    private LocalDate date;

    /* CONSTRUCTOR */
    public Permissao(int id, int idUser, int idAdminGranted, String tipo, String path, LocalDate date) {
        setId(id);
        setIdUser(idUser);
        setIdAdminGranted(idAdminGranted);
        setTipo(tipo);
        setPath(path);
        setDate(date);
    }

    public Permissao(int idUser, int idAdminGranted, String tipo, String path, LocalDate date) {
        this(-1, idUser, idAdminGranted, tipo, path, date);
    }

    /* METHODS */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Permissao other = (Permissao) obj;
        return this.id == other.id;
    }

    /* GETTERS AND SETTERS */
    public int getId() {
        return this.id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return this.idUser;
    }

    private void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getAdminWhoGranted() {
        return this.idAdminGranted;
    }

    private void setIdAdminGranted(int idAdminGranted) {
        this.idAdminGranted = idAdminGranted;
    }

    public String getTipo() {
        return this.tipo;
    }

    private void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPath() {
        return this.path;
    }

    private void setPath(String path) {
        this.path = path;
    }

    public LocalDate getDate() {
        return this.date;
    }

    private void setDate(LocalDate date) {
        this.date = date;
    }

}
