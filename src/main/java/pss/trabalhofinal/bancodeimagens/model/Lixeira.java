package pss.trabalhofinal.bancodeimagens.model;

import java.time.LocalDate;

public class Lixeira {
    /* ATTRIBUTES */
    private final int id;
    private final int idUser;
    private final String caminhoDeOrigem;
    private final String nomeDoArquivo;
    private final LocalDate dataDeExclusao;

    /* CONSTRUCTOR */
    public Lixeira(int id, int idUser, String caminhoDeOrigem, String nomeDoArquivo, LocalDate dataDeExclusao) {
        this.id = id;
        this.idUser = idUser;
        this.caminhoDeOrigem = caminhoDeOrigem;
        this.nomeDoArquivo = nomeDoArquivo;
        this.dataDeExclusao = dataDeExclusao;
    }

    public Lixeira(int idUser, String caminhoDeOrigem, String nomeDoArquivo, LocalDate dataDeExclusao) {
        this(-1, idUser, caminhoDeOrigem, nomeDoArquivo, dataDeExclusao);
    }

    /* GETTERS AND SETTERS */
    public int getId() {
        return this.id;
    }

    public int getIdUser() {
        return this.idUser;
    }

    public String getCaminhoDeOrigem() {
        return this.caminhoDeOrigem;
    }

    public String getNomeDoArquivo() {
        return this.nomeDoArquivo;
    }

    public LocalDate getDataDeExclusao() {
        return this.dataDeExclusao;
    }

}
