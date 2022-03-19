package pss.trabalhofinal.bancodeimagens.model;

import java.time.LocalDate;

public class Notification {
    /* ATTRIBUTES */
    private int id;
    private int idUser;
    private int idUserWhoSent;
    private String content;
    private boolean read;
    private LocalDate date;

    /* CONSTRUCTOR */
    public Notification(int id, int idUserWhoSent, int idUser, String content, boolean read, LocalDate date) {
        setId(id);
        setidUserWhoSent(idUserWhoSent);
        setIdUser(idUser);
        setContent(content);
        setRead(read);
        setDate(date);
    }

    public Notification(int idUserWhoSent, int idUser, String content, boolean read, LocalDate date) {
        this(-1, idUserWhoSent, idUser, content, read, date);
    }

    /* GETTERS AND SETTERS */
    public int getId() {
        return this.id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public int getidUserWhoSent() {
        return this.idUserWhoSent;
    }

    private void setidUserWhoSent(int idUserWhoSent) {
        this.idUserWhoSent = idUserWhoSent;
    }

    public int getIdUser() {
        return this.idUser;
    }

    private void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getContent() {
        return this.content;
    }

    private void setContent(String content) {
        if (content == null) {
            throw new RuntimeException("Mensagem nulo.");
        } else if (content.isEmpty() || content.isBlank()) {
            throw new RuntimeException("Entre com uma mensagem.");
        } else {
            this.content = content;
        }
    }

    public boolean wasRead() {
        return this.read;
    }

    private void setRead(boolean read) {
        this.read = read;
    }

    public boolean getRead() {
        return this.read;
    }

    public LocalDate getDate() {
        return this.date;
    }

    private void setDate(LocalDate date) {
        this.date = date;
    }
}
