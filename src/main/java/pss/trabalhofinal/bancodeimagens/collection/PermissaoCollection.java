package pss.trabalhofinal.bancodeimagens.collection;

import java.util.ArrayList;
import java.util.List;

import pss.trabalhofinal.bancodeimagens.dao.PermissaoDAO;
import pss.trabalhofinal.bancodeimagens.model.Permissao;

public class PermissaoCollection {
    /* ATTRIBUTES */
    private List<Permissao> permissoes;

    /* CONSTRUCTOR */
    public PermissaoCollection(List<Permissao> permissoes) {
        setPermissoes(permissoes);
    }

    public PermissaoCollection() {
        setPermissoes(new ArrayList<>());
    }

    /* METHODS */
    public void add(Permissao permissao) {
        /*
         * Caso a permissão seja válido, ele é inserido na lista de permissões e no
         * banco.
         */

        if (permissao == null) {

            throw new RuntimeException("Permissão nula.");

        } else if (permissoes.contains(permissao)) {

            throw new RuntimeException("Permissão já cadastrada.");

        } else {

            PermissaoDAO.insert(permissao);

            setPermissoes(PermissaoDAO.getPermissionsByUser(permissao.getIdUser()));
        }
    }

    /* GETTERS AND SETTERS */
    public List<Permissao> getPermissoes() {
        return this.permissoes;
    }

    private void setPermissoes(List<Permissao> permissoes) {
        if (permissoes == null) {
            throw new RuntimeException("Lista de permissões nula.");
        } else {

            this.permissoes = permissoes;
        }
    }
}
