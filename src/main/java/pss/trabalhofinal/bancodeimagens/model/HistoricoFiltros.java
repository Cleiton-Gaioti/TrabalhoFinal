package pss.trabalhofinal.bancodeimagens.model;

import java.time.LocalDate;

public class HistoricoFiltros {

    private int id;
    private String path;
    private String filter;
    private LocalDate date;

    public HistoricoFiltros(int id, String path, String filter, LocalDate date) {
        this.id = id;
        this.path = path;
        this.filter = filter;
        this.date = date;
    }

    public HistoricoFiltros(String path, String filter, LocalDate date) {
        this(-1, path, filter, date);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
