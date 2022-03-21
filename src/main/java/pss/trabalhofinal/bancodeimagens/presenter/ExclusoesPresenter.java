package pss.trabalhofinal.bancodeimagens.presenter;

import java.io.File;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import pss.trabalhofinal.bancodeimagens.dao.LixeiraDAO;
import pss.trabalhofinal.bancodeimagens.model.Lixeira;
import pss.trabalhofinal.bancodeimagens.model.UserModel;
import pss.trabalhofinal.bancodeimagens.view.ExclusoesView;

public class ExclusoesPresenter {

    private final LixeiraDAO lixeiraDAO;
    private ExclusoesView view;
    private UserModel user;

    public ExclusoesPresenter(UserModel user, JDesktopPane desktop) {
        lixeiraDAO = new LixeiraDAO();
        view = new ExclusoesView();
        this.user = user;

        loadTable();

        view.getBtnFechar().addActionListener(l -> {
            view.dispose();
        });

        view.getBtnRestaurar().addActionListener(l -> {
            restaurar();
        });

        desktop.add(view);
        view.setVisible(true);
    }

    private void restaurar() {
        var row = view.getTblExclusoes().getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Selecione uma linha!");
        } else {
            var fileName = view.getTblExclusoes().getValueAt(row, 2).toString();
            var caminho = view.getTblExclusoes().getValueAt(row, 1).toString();
            try {
                File temp = new File("images/.lixeira/" + fileName);
                temp.renameTo(new File(caminho));
                lixeiraDAO.remove(fileName);
                loadTable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, "Erro ao restaurar imagem: " + e.getMessage());
            }
        }
    }

    private void loadTable() {

        view.getTblExclusoes().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        var tableModel = new DefaultTableModel(
                new Object[][] {}, new String[] { "ID", "Origem", "Arquivo", "Data de Exclusão" }) {
            @Override
            public boolean isCellEditable(final int row, final int column) {
                return false;
            }
        };
        tableModel.setNumRows(0);

        List<Lixeira> lista = lixeiraDAO.getLixeiraByUser(user.getId());

        if (!lista.isEmpty()) {
            for (Lixeira l : lista) {
                tableModel.addRow(new Object[] {
                        l.getId(),
                        l.getCaminhoDeOrigem(),
                        l.getNomeDoArquivo(),
                        l.getDataDeExclusao()
                });
            }
        }

        view.getTblExclusoes().setModel(tableModel);
    }

}
